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
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <meta name="google-signin-client_id"
          content="197941991652-v7s5e4k1bsnesk1ln1mu5glrlk3dpioi.apps.googleusercontent.com">
    <title>Title</title>
</head>
<body>
    <div class="g-signin2" data-onsuccess="onSignIn"></div>
    <a href="LoginServlet?sso=Microsoft">Login with Office</a>

    <script>
        function onSignIn(googleUser) {
            var profile = googleUser.getBasicProfile();

            var redirectUrl = 'oAuthServlet';
            //using jquery to post data dynamically
            var form = $('<form action="' + redirectUrl + '" method="post">' +
                '<input type="text" name="id_token" value="' +
                googleUser.getAuthResponse().id_token + '" />' +
                '</form>');
            $('body').append(form);
            form.submit();

        }
    </script>
</body>
</html>
