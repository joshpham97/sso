package Servlet;

import App.Contact;
import App.Context;
import App.GoogleSSOManager;
import App.SSOManagerFactory;
import com.google.api.services.people.v1.model.Person;

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
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        Contact contact = new Contact(firstName, lastName);
        GoogleSSOManager manager = new GoogleSSOManager();
        if(manager.createContact(contact))
        {
            System.out.println("Contact has been created!");
        }
        else
        {
            System.out.println("Error!");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        Context context = (Context) request.getSession().getAttribute("Context");
        SSOManagerFactory ssoManager = context.getSsoManager();
        GoogleSSOManager googleSSOManager = (GoogleSSOManager) ssoManager;

        if(action == null) {
            List<Person> connections = googleSSOManager.getContacts();
            request.setAttribute("connections", connections);
        }
        else if(action.equals("edit")) {
            String resourceName = request.getParameter("resourceName");
            Person person = googleSSOManager.getContact(resourceName);
            request.setAttribute("person", person);
        }
    }
}
