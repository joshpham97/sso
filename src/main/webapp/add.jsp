<%--
  Created by IntelliJ IDEA.
  User: timmy
  Date: 22/11/2020
  Time: 11:37 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Contact</title>
</head>
<body>
    <div>
        <h3>Add Contact</h3>
    </div>

    <div>
        <form action="ContactServlet" method="post">
            <input id="firstName" class="form-control" name="firstName" placeholder="First Name">
            <input id="lastName" class="form-control" name="lastName" placeholder="Last Name">
            <button type="submit">Submit</button>
        </form>
    </div>
</body>
</html>
