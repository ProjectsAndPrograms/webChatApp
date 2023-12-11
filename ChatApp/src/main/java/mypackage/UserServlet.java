package mypackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		String output = "";
		DatabaseConfig db ;
		try {
		   db = new DatabaseConfig();
		   Connection con = db.getConnection();
		   String sql = "SELECT * FROM users WHERE NOT unique_id='"+session.getAttribute("unique_id") + "'";

		   ResultSet set = con.prepareStatement(sql).executeQuery();
 
		   ResultSet countSet = con.prepareStatement(sql).executeQuery();
		   int row_count = 0;
		   while(countSet.next()) {
			   row_count += 1;
		   }
		
		   if(row_count==0) {
			   output += "No users are available to chat";
		   }
		   else if(row_count>0){
			   while(set.next()) {
				   
				   String reciever_id = set.getString("unique_id");
					String sender_id = req.getSession().getAttribute("unique_id")+"";
					ResultSet latestMsgSet = getMessagesResultSet(reciever_id, sender_id, con);
					int numRow = numOfRows(reciever_id, sender_id, con);
					
					String result="No message available.";

					String pre = "";
					
					if(numRow > 0) {
					
						if(latestMsgSet.next()) {						
							result = latestMsgSet.getString("msg");
							
							
							if(sender_id.equalsIgnoreCase(latestMsgSet.getString("outgoing_msg_id"))) {
								pre = "You : ";
							}else {
								pre = "";
							}
							
						}
						
					}else {
						result = "No message available.";
					}
					String msg;
					if(result.length() > 26) {
						msg = result.substring(0, 25) + "...";
					}else {
						msg = result;
					}
					
					// check user in online or offline
					String offline;
				
					if(set.getString("status").equalsIgnoreCase("Offline now")) {
						offline = "offline";
					}else {
						offline = "";
					}
				
					
				   output += "<a href=\"chats?user_id="+ set.getString("unique_id") +"\">\n"
					   		+ "					<div class=\"content\">\n"
					   		+ "						<img alt=\"\" src=\"uploads/"+ set.getString("img")+"\">\n"
					   		+ "						<div class=\"details\">\n"
					   		+ "							<span>"+ set.getString("fname") + " " + set.getString("lname") +"</span>\n"
					   		+ "							<p>"+pre+msg+"</p>\n"
					   		+ "						</div>\n"
					   		+ "					</div>\n"
					   		+ "				<div class=\"status-dot "+ offline +" \"><i class=\"fas fa-circle\"></i></div>\n"
					   		+ "				</a>";
			   }
			 
		   }

		   resp.getWriter().write(output);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

private int numOfRows(String incoming_id, String outgoing_id, Connection conn) throws SQLException {
		
		ResultSet set = getMessagesResultSet(incoming_id, outgoing_id, conn);
		int i = 0;
		while(set.next()) {
			i += 1;
		}
		return i;
	}

	private ResultSet getMessagesResultSet(String incoming_id, String outgoing_id, Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM messages "
				+ " WHERE (outgoing_msg_id=? AND incoming_msg_id=?)"
				+ " OR (outgoing_msg_id=? AND incoming_msg_id=?) "
				+ "ORDER BY msg_id DESC LIMIT 1;";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, outgoing_id);
		pstmt.setString(2, incoming_id);
		pstmt.setString(3, incoming_id);
		pstmt.setString(4, outgoing_id);
		
		ResultSet set = pstmt.executeQuery();
		
		return set;
		
	}
	
	
}


