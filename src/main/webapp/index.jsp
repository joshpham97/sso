<%--
  Created by IntelliJ IDEA.
  User: thuan
  Date: 2020-11-18
  Time: 10:09 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<% if (session.getAttribute("userName") == null) {%>
<jsp:forward page="/login.jsp"/>
<% } %>
    <span>Login successfully</span>
<h3>Welcome  ${userName}</h3>
</body>
</html>
