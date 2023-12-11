package mypackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.regex.Pattern;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class loginServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();

		resp.setContentType("text/html");

		String email = req.getParameter("email");
		String password = req.getParameter("password");


		if(!email.isBlank() && !password.isBlank()) {
			String regex = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
			Pattern pattern = Pattern.compile(regex);

			PreparedStatement pstmt;
			if(pattern.matcher(email).matches()) {
				
				try {
					String passwordHash = AppSecurity.encript(password); 
					String query = "SELECT * FROM users WHERE email = ? AND password = ?";
					
					
					Connection conn = new DatabaseConfig().getConnection();
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, email);
					pstmt.setString(2, passwordHash);

					ResultSet set = pstmt.executeQuery();

					HashMap<String, String> assocRow = new HashMap<>();
					if(set.next()) {
						 assocRow.put("email", email);
						 assocRow.put("password", password);

						 
						 String status = "Active now";
						 String sql = "UPDATE users SET status=? WHERE unique_id=?;";
							
							PreparedStatement pstmt2 = conn.prepareStatement(sql);
							pstmt2.setString(1, status);
							pstmt2.setString(2, set.getString("unique_id"));
							
							int i = pstmt2.executeUpdate();
						 
						 if(i>0) {
							 
							 HttpSession session = req.getSession();
							 session.setAttribute("unique_id", set.getString("unique_id"));
							 out.write("success");
						 }
						 
						 

					}else {
						out.write("Email or Password is incorrect !");
					}

				} catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException e) {
					e.printStackTrace();
				}



			}else {
				out.println("Invalid Email");
			}

		}else {
			out.println("All field's are required");
		}
	}

}
