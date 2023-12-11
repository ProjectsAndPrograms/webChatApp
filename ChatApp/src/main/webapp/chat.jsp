<%@page import="mypackage.Const"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="mypackage.DatabaseConfig"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title><%= Const.app_name %></title>
	<link rel="stylesheet" href="cssFiles/Style.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css"/>
</head>
<body>
	<%
		if(session.getAttribute("unique_id") == null){
			request.getRequestDispatcher("login").forward(request, response);
		}
	
	
		DatabaseConfig	db ;
		String fname = "John";
		String lname = "Doe";
		String imgName = "default.png";
		String status = ""; 
		String param = "" + request.getParameter("user_id");
		try{		
			db = new DatabaseConfig();
			Connection conn = db.getConnection();
			
			String sql = "SELECT * FROM users WHERE unique_id=?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
		
			pstmt.setString(1, param);			
			ResultSet set = pstmt.executeQuery();
			
			if(set.next()){
				fname = set.getString("fname");
				lname = set.getString("lname");
				imgName = set.getString("img");
				status = set.getString("status");
				
			}
			
			
		}catch(ClassNotFoundException | SQLException e){
		
			out.write("Connection not found due to : " + e.getMessage());
		}
		
		response.setContentType("text/html");
	%>
	<div class="wrapper">
		<section class="chat-area">
		<header>			
				<a href="user-chatbox" class="back-icon"><i class="fas fa-arrow-left"></i></a>
				<img alt="" src='<% String imgPath = "uploads/" + imgName ; out.print(imgPath);%>'>
				
				<div class="details">
					<span><%= fname + " " + lname %></span>
					<p><%= status %></p>
				</div>
		</header>
		<div class="chat-box">
			
			
			
			
		</div>
		
		<form action="#" id="message_box" class="typing-area" autocomplete="off">
			<input type="hidden" name="outgoing_id" id="outgoing_id" value=<%= session.getAttribute("unique_id") %> >
			<input type="hidden" name="incoming_id" id="incoming_id" value=<%= param %> >			
			<input type="text" name="message" id="message" class="input-field" placeholder="Type a message here...">
			<button type="button" onclick="submitForm()"><i class="fab fa-telegram-plane"></i></button>
		</form>
	</section>
	</div>
	
	<script type="text/javascript" src="js/chat.js"></script>

</body>
</html>