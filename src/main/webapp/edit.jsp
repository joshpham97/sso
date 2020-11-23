<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/ContactServlet" />
<jsp:useBean id="contact" scope="request" type="App.Contact" />
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous" />
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/15f69f89ed.js" crossorigin="anonymous"></script>
    <title>Edit Contact</title>
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
        <a href="LoginServlet?action=logout">
            <button class="btn btn-primary"><i class="fas fa-sign-out-alt mr-2"></i>Sign Out</button>
        </a>
    </nav>

    <div class="container">
        <div class="col-12 mt-2">
            <div class="h4">Edit Contact</div>

            <form method="post" action="ContactServlet">
                <div class="form-group">
                    <label for="firstName">First Name</label>
                    <input type="text" placeholder="First Name"  id="firstName" class="form-control" name="firstName" value="${contact.firstName}" required />
                </div>

                <div class="form-group">
                    <label for="lastName">Last Name</label>
                    <input type="text" placeholder="Last Name"  id="lastName" class="form-control" name="lastName" value="${contact.lastName}" required />
                </div>

                <div class="form-group">
                    <label for="phoneNumber">Phone Number</label>
                    <input type="text" placeholder="Phone Number"  id="phoneNumber" class="form-control" name="phoneNumber" value="${contact.phoneNumber}" required />
                </div>

                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="text" placeholder="Email"  id="email" class="form-control" name="email" value="${contact.email}" required />
                </div>

                <div class="text-center">
                    <input type="hidden" name="resourceName" value="${contact.resourceName}" />
                    <input type="hidden" name="etag" value="${contact.etag}" />
                    <button class="btn btn-primary" type="submit" name="action" value="edit">Save</button>
                    <button class="btn btn-danger" type="submit" name="action" value="delete">Delete</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
