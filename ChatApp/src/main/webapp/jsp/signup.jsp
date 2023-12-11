
<%@page import="mypackage.Const"%>
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
		try{
		
			if(session.getAttribute("unique_id") != null){
				request.getRequestDispatcher("user.jsp").forward(request, response);	
			}
		}catch(Exception e){
			
		}
	%>

	<div class="wrapper">
		<section class="form signup">
			<header><%= Const.app_name %></header>
			<%
				String error;
				try{
					error = session.getAttribute("error_message")+"";
				}catch(IllegalStateException e){
					error = null;
				}
				
			%>
			<form action="#" method="post" enctype="multipart/form-data">
					<div class="error-txt" style="display:block;"><%= error %></div>
					<div class="name-details">
						<div class="field input">
							<label>First Name</label>
							<input type="text" name="fname" id="fname" placeholder="First Name" required>
						</div>
						<div class="field input">
							<label>Last Name</label>
							<input type="text" name="lname" id="lname" placeholder="Last Name" required>
						</div>
						
					</div>	
					
						<div class="field input">
							<label>Email Address</label>
							<input type="text" name="email" id="email" placeholder="Enter your email" required>
						</div>
						<div class="field input">
							<label>Password</label>
							<input type="password" name="password" id="password" placeholder="Enter new Password" required>
							<i class="fas fa-eye"></i>
						</div>
						<div class="field image">
							<label>Select Image</label>
						<input type="file" name="image" accept="image/*">
						</div>
						<div class="field button">
							<input type="submit" value="Continue to Chat">
						</div>
				</form>
				<div class="link">Already signed up? <a href="login">Login now</a></div>
		</section>
	</div>
	
	<script src="js/pass_show_hide.js"></script>
	
</body>
</html>

