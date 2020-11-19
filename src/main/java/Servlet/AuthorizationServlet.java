package Servlet;

import App.Context;
import App.SSOManagerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "oAuthCodeServlet")
public class AuthorizationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String oAuthCode = request.getParameter("code");

        if (oAuthCode != null){
            Context context = (Context) request.getSession().getAttribute("Context");
            SSOManagerFactory ssoManager = context.getSsoManager();
            ssoManager.getAccessToken(oAuthCode);
            ssoManager.login();

            response.sendRedirect("index.jsp");
        }
    }
}
