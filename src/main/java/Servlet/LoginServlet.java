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

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        if(action == null) {
            String strSsoType = request.getParameter("sso");

            if (strSsoType != null) {
                SSOType ssoType = SSOType.valueOf(strSsoType);

                Context context = new Context();
                context.setSsoManager(ssoType);
                session.setAttribute("Context", context);

                SSOManagerFactory ssoManager = context.getSsoManager();
                String authorizationURL = ssoManager.getAuthorizationURL();

                if (authorizationURL != null)
                    response.sendRedirect(authorizationURL);
            }
        }
        else if(action.equals("logout")) {
            session.removeAttribute("context");
            response.sendRedirect("login.jsp");
        }
    }
}
