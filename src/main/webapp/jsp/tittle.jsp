<%@ page import="com.JavaWebApplication.Beans.SearchResult"%>
<%@page import="com.JavaWebApplication.Utility.ServletUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.JavaWebApplication.Model.DataProvider"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="header.jsp"%>
<div style="max-width: 1000px; margin: inherit;">

	<%
	List<SearchResult> searchResults = (List<SearchResult>) request.getAttribute("searchResults");
	%>
	<%
	int pageNumber = (request.getAttribute("pageNumber") != null) ? (int) request.getAttribute("pageNumber") : 0;
	%>
	<%
	int pageSize = 10;
	%>
	<%
	String searchQuery = request.getParameter("searchTerm");
	%>
	<div>
		<h1>
			Total Results:
			<%=(int) request.getAttribute("totalPagesIndex")%></h1>
		<ul style="list-style-type: none; padding: 0;">
			<%
			if (searchResults != null && !searchResults.isEmpty()) {
			%>
			<%
			int pageIndex = Integer.parseInt(request.getParameter("pageNumber"));
			%>
			<%
			for (int i = 0; i < searchResults.size(); i++) {
			%>
			<%
			int index = i + (pageIndex * pageSize) + 1;
			%>
			<li style="margin-bottom: 2px; padding: 2px; border: 1px; border-color: #454646;
    border: outset;"><a
				href="http://localhost:8080/JavaWebApplication/GetHtmlById?vanbanId=<%=searchResults.get(i).getVanbanId()%>">
					<%=index + " - " + searchResults.get(i).getHeading()%>
			</a></li>
			
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
	</div>
</div>


<nav aria-label="Page navigation example">
	<ul class="pagination">
		<%
		// Assuming totalResults is the total number of search results
		int totalResults = (int) request.getAttribute("totalPagesIndex");
		int totalPages = (int) Math.ceil((double) totalResults / pageSize);
		int currentPage = Integer.parseInt(request.getParameter("pageNumber"));
		currentPage = (currentPage == 0) ? 0 : currentPage;
		int maxPagesToShow = 5;
		// Previous button
		if (currentPage > 0) {
		%>
		<li class="page-item"><a class="page-link"
			href="<%=request.getContextPath()%>/GetDocWithTerm?searchTerm=<%=searchQuery%>&pageNumber=<%=currentPage - 1%>">Previous
				</a></li>
		<%
		}

		for (int i = Math.max(0, currentPage - maxPagesToShow / 2); i <= Math.min(totalPages - 1,
				currentPage + maxPagesToShow / 2); i++) {
		String activeClass = (i == currentPage) ? "active" : "";
		%>
		<li class="page-item <%=activeClass%>"><a class="page-link"
			href="<%=request.getContextPath()%>/GetDocWithTerm?searchTerm=<%=searchQuery%>&pageNumber=<%=i%>"><%=i + 1%></a>
		</li>
		<%
		}
		// Next button
		if (currentPage < totalPages - 1) {
		%>
		<li class="page-item"><a class="page-link"
			href="<%=request.getContextPath()%>/GetDocWithTerm?searchTerm=<%=searchQuery%>&pageNumber=<%=currentPage + 1%>">Next
				</a></li>
		<%
		}
		%>

	</ul>
</nav>
<%@ include file="footer.jsp"%>