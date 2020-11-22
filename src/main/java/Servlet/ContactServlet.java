package Servlet;

import App.Contact;
import App.Context;
import App.SSOManagerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ContactServlet")
public class ContactServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        Context context = (Context) request.getSession().getAttribute("Context");
        SSOManagerFactory ssoManager = context.getSsoManager();

        if(action != null) {
            String resourceName = request.getParameter("resourceName");

            if(action.equals("edit")) {
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String phoneNumber = request.getParameter("phoneNumber");
                String email = request.getParameter("email");
                String etag = request.getParameter("etag");

                boolean edited = ssoManager.updateContact(new Contact(firstName, lastName, phoneNumber, email, resourceName, etag));
                response.sendRedirect("index.jsp");
            }
            else if(action.equals("delete")) {
                ssoManager.deleteContact(-1, resourceName);
                response.sendRedirect("index.jsp");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        Context context = (Context) request.getSession().getAttribute("Context");
        SSOManagerFactory ssoManager = context.getSsoManager();

        if(action == null) {
            List<Contact> contacts = ssoManager.getContacts(-1);
            request.setAttribute("contacts", contacts);
        }
        else if(action.equals("edit")) {
            String resourceName = request.getParameter("resourceName");

            Contact contact = ssoManager.getContact(-1, resourceName);
            request.setAttribute("contact", contact);
        }
    }
}
