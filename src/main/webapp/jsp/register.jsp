<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="css/styles.css">
<title>Register</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f4f4f4;
	margin: 0;
	padding: 0;
	display: flex;
	align-items: center;
	justify-content: center;
	height: 100vh;
}

.register-container {
	max-width: 400px; /* Set your desired max-width */
	width: 100%;
	background-color: #ffffff;
	padding: 20px;
	border-radius: 5px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.form-group {
	margin-bottom: 15px;
}

.form-group label {
	display: block;
	margin-bottom: 5px;
}

.form-group input {
	width: 100%;
	padding: 8px;
	box-sizing: border-box;
}

.form-group button {
	background-color: #4c63b7d1;
	color: #fff;
	padding: 10px;
	border: none;
	border-radius: 3px;
	cursor: pointer;
}
</style>
</head>

<body>
	<div class="register-container">
		<form method="post"
			action="http://localhost:8080/JavaWebApplication/Register">

			<h1>Sign Up</h1>
			<p>User name include:</p>
			<ul style="color: red">
				<li>Lower case, number</li>
				<li>No symbols</li>
				<li>User name and password max 20 character</li>
			</ul>
			<hr>

			<div class="form-group">
				<label for="userName"><b>Username</b></label> <input
					type="text" placeholder="Enter user name" name="userName"
					id="userName" required>
			</div>
			<div class="form-group">
				<label for="psw"><b>Password</b></label> <input type="password"
					placeholder="Enter Password" name="psw" id="psw" required>
			</div>
			<div class="form-group">
				<label for="repassword"><b>Repeat Password</b></label> <input
					type="password" placeholder="Repeat Password" name="repassword"
					id="repassword" required>
			</div>



			<hr>
			<div class="form-group">
				<button type="submit">Sign Up</button>
			</div>

		</form>
		<%
		String res = (String) session.getAttribute("resRegister");
		%>
		<%
		if (res != null) {
		%>
		<p style="color: red;"><%=res%></p>
		<%
		}
		%>
		<%
		session.setAttribute("resRegister", null);
		%>
		<div class="form-group">
			<p>
				You already have an account? <a href="login.jsp"><button
						style="background-color: #4caf50;">Sign In</button></a>
			</p>
		</div>

	</div>
</body>
</html>