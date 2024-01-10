<%@ include file="header.jsp"%>

<%
String htmlResult = (String) request.getAttribute("htmlResult");
%>


<div
	style="max-width: 1500px; margin: 20px; margin-left: 50px; margin-right: 50px; margin-top: 50px;">
	${htmlResult}</div>
<%@ include file="footer.jsp"%>