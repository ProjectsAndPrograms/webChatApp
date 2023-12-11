package mypackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		
		if(session.getAttribute("unique_id") != null) {
			
			try {
				Connection conn = new DatabaseConfig().getConnection();
				String logout_id = MySQLUtils.mysql_real_escape_string(conn, req.getParameter("logout_id"));
				
				
				if(logout_id != null) {
					String status = "Offline now";
					String sql = "UPDATE users SET status=? WHERE unique_id=?;";
					
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, status);
					pstmt.setString(2, logout_id);
					
					int i = pstmt.executeUpdate();
					
					if(i>0) {
						session.removeAttribute("unique_id");
						session.invalidate();
						
						req.getRequestDispatcher("user-login").forward(req, resp);
					}
				}else {
					req.getRequestDispatcher("user-login").forward(req, resp);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			req.getRequestDispatcher("user-login").forward(req, resp);
		}

	}
	
}
