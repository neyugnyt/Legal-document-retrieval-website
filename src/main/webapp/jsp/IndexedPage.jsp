
<%@ include file="adminHeader.jsp"%>
<%
    String notificationMessage = (String) request.getAttribute("noti");
    if (notificationMessage != null) {
%>
    <div class="notification">
        <%= notificationMessage %>
    </div>
<%
    }
%>
<%@ include file="adminFooter.jsp"%>