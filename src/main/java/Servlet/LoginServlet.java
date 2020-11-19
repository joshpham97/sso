package Servlet;

import App.Contact;
import App.Context;
import App.SSOManagerFactory;
import App.SSOType;
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
