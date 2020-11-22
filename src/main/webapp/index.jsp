<%--
  Created by IntelliJ IDEA.
  User: thuan
  Date: 2020-11-18
  Time: 10:09 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/ContactServlet" />
<jsp:useBean id="connections" scope="request" type="java.util.List<com.google.api.services.people.v1.model.Person>" />
<html>
<head>
    <title>Contacts</title>
</head>
<body>
    <div>
        <h3>Welcome</h3>
    </div>

    <div>
        <c:forEach items="${connections}" var="person">
            <div>
                <span>
                        ${person.names.get(0).displayName}
                </span>
                <span>
                    ${person.phoneNumbers.get(0).value}
                </span>
                <span>
                    ${person.resourceName}
                </span>
            </div>
        </c:forEach>
    </div>

    <div>
        <a href="add.jsp">Add Contact</button>
    </div>
</body>
</html>
