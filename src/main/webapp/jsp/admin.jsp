<%@ page import="com.JavaWebApplication.Beans.UserResult"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.JavaWebApplication.Model.DataProvider"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>

<%@ include file="adminHeader.jsp"%>

<div style="max-width: 1000px; margin: inherit;">

	<%
	List<UserResult> userResults = (List<UserResult>) request.getAttribute("userResults");
	%>
	<%
	int pageNumber = (request.getAttribute("pageNumber") != null) ? (int) request.getAttribute("pageNumber") : 0;
	%>
	<%
	int pageSize = 10;
	%>
	<%
	int totalpages = (int) request.getAttribute("totalPagesUser");
	%>
	<%
	if (totalpages != 0) {
	%>
	<h1>
		Total Results:
		<%=totalpages%></h1>
	<%
	}
	%>

	<ul
		style="list-style-type: none; padding: 0; display: flex; flex-direction: column;">
		<li
			style="max-width: 100%; width: 700px; margin-bottom: 2px; padding: 2px; border: 1px; border-color: #454646; display: flex; align-items: center;">
			<span>User name </span> <span
			style="margin-left: auto;">Actions</span>
		</li>
		<%
		if (userResults != null && !userResults.isEmpty()) {
		%>
		<%
		int pageIndex = Integer.parseInt(request.getParameter("pageNumber"));
		%>
		<%
		for (int i = 0; i < userResults.size(); i++) {
		%>
		<%
		int index = i + (pageIndex * pageSize) + 1;
		%>
		<li
			style="max-width: 100%; width: 700px; margin-bottom: 2px; padding: 2px; border: 1px; border-color: #454646; display: flex; align-items: center;">
			<span><%=index + ". " + userResults.get(i).getUserName()%></span>
			<%
			String userId = userResults.get(i).getUserId();
			session.setAttribute("userId", userId);
			%>
			<a style="margin-left: auto;"
			href="http://localhost:8080/JavaWebApplication/DeleteUser?username=<%=userResults.get(i).getUserName()%>&pageNumber=<%=pageNumber%>">
				Delete</a> 
			<a style="margin-left: 10px;" href="jsp/update.jsp"> Update</a>
		</li>
		<%
		}
		%>
		<%
		} else {
		%>
		<li>No results found.</li>
		<%
		}
		%>
	</ul>

	<%
	String mess = (String) request.getAttribute("deleteMess");
	%>
	<%
	if (mess != null) {
	%>
	<p><%=mess%></p>
	<%
	}
	%>
	
		<%
	String messUpdate = (String) request.getAttribute("update");
	%>
	<%
	if (messUpdate != null) {
	%>
	<p><%=messUpdate%></p>
	<%
	}
	%>



</div>


<nav aria-label="Page navigation example">
	<ul class="pagination">
		<%
		// Assuming totalResults is the total number of search results
		int totalResults = totalpages;
		int totalPages = (int) Math.ceil((double) totalResults / pageSize);
		int currentPage = Integer.parseInt(request.getParameter("pageNumber"));
		currentPage = (currentPage == 0) ? 0 : currentPage;
		int maxPagesToShow = 5;
		// Previous button
		if (currentPage > 0) {
		%>
		<li class="page-item"><a class="page-link"
			href="<%=request.getContextPath()%>/GetUsers?pageNumber=<%=currentPage - 1%>">Previous</a>
		</li>
		<%
		}

		for (int i = Math.max(0, currentPage - maxPagesToShow / 2); i <= Math.min(totalPages - 1,
				currentPage + maxPagesToShow / 2); i++) {
		String activeClass = (i == currentPage) ? "active" : "";
		%>
		<li class="page-item <%=activeClass%>"><a class="page-link"
			href="<%=request.getContextPath()%>/GetUsers?pageNumber=<%=i%>"><%=i + 1%></a>
		</li>
		<%
		}
		// Next button
		if (currentPage < totalPages - 1) {
		%>
		<li class="page-item"><a class="page-link"
			href="<%=request.getContextPath()%>/GetUsers?pageNumber=<%=currentPage + 1%>">Next</a>
		</li>
		<%
		}
		%>

	</ul>
</nav>

<%@ include file="adminFooter.jsp"%>