<%--
  Created by IntelliJ IDEA.
  User: thuan
  Date: 2020-11-18
  Time: 5:16 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous" />
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/15f69f89ed.js" crossorigin="anonymous"></script>
    <title>Login</title>
</head>
<body>
    <div class="container mt-4 w-50">
        <div class="card">
            <div class="card-header bg-dark">
                <div class="text-center text-light h4">
                    SOEN 387 POC
                </div>
            </div>

            <div class="card-body">
                <div class="text-center">
                    <a href="LoginServlet?sso=Google">
                        <button class="btn btn-light border form-control">Login with Google</button>
                    </a>
                </div>

                <div class="text-center">
                    <a href="LoginServlet?sso=Microsoft">
                        <button class="btn btn-primary form-control">Login with Outlook</button>
                    </a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
