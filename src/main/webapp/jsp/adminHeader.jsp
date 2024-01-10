<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.JavaWebApplication.Beans.User"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
	integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
	crossorigin="anonymous"></script>

<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
	integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
	crossorigin="anonymous"></script>

<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
	integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
	crossorigin="anonymous"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<title>Java App</title>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand"
			href="http://localhost:8080/JavaWebApplication/GetData?pageNumber=0">Home Page</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav">
				<li class="nav-item"><a class="nav-link"
					href="http://localhost:8080/JavaWebApplication/GetUsers?pageNumber=0">All users</a></li>
				<li class="nav-item"><a class="nav-link"
					href="http://localhost:8080/JavaWebApplication/Indexing">Index database</a></li>
				<li class="nav-item"><a class="nav-link"
					href="http://localhost:8080/JavaWebApplication/ReIndex">Delete files indexed</a></li>
			</ul>
		</div>
		<%
		// Your condition
		if (session.getAttribute("userName") == null) {
		%>
		<div class="auth">
			<ul class="navbar-nav">
				<li class="nav-item"><a class="nav-link" href="jsp/login.jsp">Sign In</a></li>
				<li class="nav-item"><a class="nav-link"
					href="jsp/register.jsp">Sign Up</a></li>
			</ul>
		</div>
		<%
		} else {
		%>
		<%String username = (String) session.getAttribute("userName"); %>
		<div class="username">
			<ul class="navbar-nav">
				<li class="nav-item"><b><a style="color: black;"
						class="nav-link" href="#"><%=username%></a></b></li>
			</ul>
		</div>
		<div class="logout">
			<ul class="navbar-nav">
				<li class="nav-item"><b><a style="color: red;"
						class="nav-link" href="http://localhost:8080/JavaWebApplication/Logout">Logout</a></b></li>
			</ul>
		</div>
		
		<%
		}
		%>

		<form class="form-inline my-2 my-lg-0"
			action="http://localhost:8080/JavaWebApplication/SearchQuery"
			method="GET">
			<input class="form-control mr-sm-2" type="search"
				placeholder="Search document" aria-label="Search" name="searchQuery">
			<input type="hidden" name="pageNumber" value="0">
			<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
		</form>
	</nav>