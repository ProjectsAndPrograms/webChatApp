<%@page import="mypackage.Const"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="mypackage.DatabaseConfig"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>

<%	

	if (session.getAttribute("unique_id") == null) {
		request.getRequestDispatcher("login.jsp").forward(request, response);
	} else {}
%>


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
		DatabaseConfig	db ;
		String fname = "John";
		String lname = "Doe";
		String imgName = "fake.jpg";
		String status = ""; 
		try{		
			db = new DatabaseConfig();
			Connection conn = db.getConnection();
			
			String sql = "SELECT * FROM users WHERE unique_id=?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			String param = "" + session.getAttribute("unique_id");
			
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
		<section class="users">
		<header>
			<div class="content">
			
			
				<img alt="" src='<% String imgPath = "uploads/" + imgName ; out.print(imgPath);%>'>
				<div class="details">
					<span><%= fname + " " + lname %></span>
					<p><%= status %></p>
				</div>
			</div>
			<a href="<%= "logout?logout_id="+session.getAttribute("unique_id") %>" class="logout">Logout</a>
		</header>
		<div class="search">
				<span class="text">Select an user to start chat...</span>
				<input type="text" placeholder="Enter name to search...">
				<button><i class="fas fa-search"></i></button>
		</div>
			
			<div class="users-list" id="user_list">
	
			</div>
			
		</section>
	</div>
	
	<script type="text/javascript" src="js/users.js"></script>
</body>
</html>