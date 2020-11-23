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
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous" />
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/15f69f89ed.js" crossorigin="anonymous"></script>
    <title>Contacts</title>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a href="index.jsp">
            <span class="navbar-brand mb-0 h1">SOEN 387 POC</span>
        </a>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="mb-0 nav-link active" href="index.jsp"><i class="fas fa-home mr-2"></i>Home</a>
                </li>

                <li class="nav-item">
                    <a class="mb-0 nav-link" href="add.jsp"><i class="fas fa-plus mr-2"></i>Add Contact</a>
                </li>
            </ul>
        </div>
        <div>
            <ul class="navbar-nav mr-auto">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown">
                        <i class="fas fa-user mr-2"></i>Hello, USERNAME
                    </a>
                    <div class="dropdown-menu dropdown-menu-right">
                        <a class="dropdown-item" href="#"><i class="fas fa-key mr-2" ></i>Change Password</a>
                        <a class="dropdown-item" href="#"><i class="fas fa-user-circle mr-2"></i>Update account</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="AuthServlet"><i class="fas fa-sign-out-alt mr-2"></i>Sign Out</a>
                    </div>
                </li>
            </ul>
        </div>
    </nav>

    <div class="container mt-3">
        <c:choose>
            <c:when test="${contacts.size() > 0}">
                <div class="card">
                    <div class="card-header">
                        <div class="row">
                            <div class="col">Name</div>
                            <div class="col">Email</div>
                            <div class="col">Phone Number</div>
                            <div class="col=sm"><a><i class="invisible fas fa-edit mr-2"></i></a></div>
                        </div>
                    </div>

                    <div class="card-body pb-0">
                        <c:forEach items="${contacts}" var="contact" varStatus="loop">
                            <div class="row mb-3">
                                <div class="col">
                                    <span>${contact.firstName} ${contact.lastName}</span>
                                </div>

                                <div class="col">
                                    <span>${contact.email}</span>
                                </div>

                                <div class="col">
                                    <span>${contact.phoneNumber}</span>
                                </div>

                                <div class="col-xs">
                                    <a href="edit.jsp?action=edit&resourceName=${contact.resourceName}"><i class="fas fa-edit mr-2"></i></a>
                                </div>

                            </div>

                            <c:if test="${!loop.last}">
                                <hr />
                            </c:if>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        You have no contacts
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</body>
</html>
