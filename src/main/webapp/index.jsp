<%@ page import="com.google.api.services.people.v1.PeopleService.People" %>
<%@ page import="com.google.api.services.people.v1.PeopleService.People.Connections" %>
<%--
  Created by IntelliJ IDEA.
  User: thuan
  Date: 2020-11-18
  Time: 10:09 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
    <div>Welcome ${sessionScope.get("displayName")}</div>
</body>
</html>
