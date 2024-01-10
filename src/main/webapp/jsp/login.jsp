<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="css/styles.css">
<title>Login</title>
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

.login-container {
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
	background-color: #4caf50;
	color: #fff;
	padding: 10px;
	border: none;
	border-radius: 3px;
	cursor: pointer;
}
</style>
</head>
<body>

	<div class="login-container">
		<h2>Sign In</h2>
		<form action="http://localhost:8080/JavaWebApplication/Login"
			method="post">
			<p>User name include:</p>
			<ul style="color: red">
				<li>Lower case, number</li>
				<li>No symbols</li>
				<li>User name and password max 20 character</li>
			</ul>
			<div class="form-group">
				<label for="username">Username</label> <input type="text"
					id="username" name="username" required>
			</div>
			<div class="form-group">
				<label for="password">Password</label> <input type="password"
					id="password" name="password" required>
			</div>
			<div class="form-group">
				<button type="submit">Sign In</button>
			</div>
		</form>
		<div class="form-group">
			Create an account <a href="register.jsp"><button
					style="background-color: #4c63b7d1;">Sign Up</button></a>
		</div>
		<%
		String res = (String) session.getAttribute("resLogin");
		%>
		<%
		if (res != null) {
		%>
		<p style="color: red;"><%=res%></p>

		<%
		}
		%>
		<%
		session.setAttribute("resLogin", null);
		%>
	</div>

</body>
</html>