package Servlet;

import App.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.microsoft.aad.msal4j.HttpRequest;

import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "oAuthServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        try {
            String idToken = request.getParameter("id_token");
            GoogleIdToken.Payload payLoad = IdTokenVerifierAndParser.getPayload(idToken);
            String name = (String) payLoad.get("name");
            String email = payLoad.getEmail();
            System.out.println("User name: " + name);
            System.out.println("User email: " + email);

            HttpSession session = request.getSession(true);
            session.setAttribute("userName", name);
            System.out.println("NAME: " + name);
            request.getServletContext()
                    .getRequestDispatcher("index.jsp").forward(request, response);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strSsoType = request.getParameter("sso");

        if(strSsoType != null){
            SSOType ssoType = SSOType.valueOf(strSsoType);

            HttpSession session = request.getSession();
            Context context = new Context();
            context.setSsoManager(ssoType);
            session.setAttribute("Context", context);

            SSOManagerFactory ssoManager = context.getSsoManager();
            String authorizationURL = ssoManager.getAuthorizationURL();

            if (authorizationURL != null)
                response.sendRedirect(authorizationURL);
        }
    }
}
