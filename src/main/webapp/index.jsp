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
<jsp:useBean id="contacts" scope="request" type="java.util.List<App.Contact>" />
<html>
<head>
    <title>Contacts</title>
</head>
<body>
    <div>
        <h3>Welcome</h3>
    </div>

    <div>
        <c:forEach items="${contacts}" var="contact">
            <div>
                <span>
                        ${contact.firstName} ${contact.lastName}
                </span>
                <span>
                    ${contact.phoneNumber}
                </span>
                <span>
                    ${contact.email}
                </span>
                <span>
                    <a href="edit.jsp?action=edit&resourceName=${contact.resourceName}"><button>Edit</button></a>

                    <form method="post" action="ContactServlet" style="display: inline;">
                        <input type="hidden" name="resourceName" value="${contact.resourceName}" />
                        <button name="action" value="delete"">Delete</a>
                    </forM>
                </span>
            </div>
        </c:forEach>
    </div>

    <div>
        <a href="add.jsp">Add Contact</button>
    </div>
</body>
</html>
