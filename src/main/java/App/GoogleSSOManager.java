package App;

import Servlet.LoginServlet;
import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.PeopleServiceRequest;
import com.google.api.services.people.v1.PeopleServiceRequestInitializer;
import com.google.api.services.people.v1.model.*;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GoogleSSOManager extends SSOManagerFactory {
    private final String CREDENTIALS_FILE_PATH  = "/credentials.json";
    private final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private String googleId;
    private String googleSecret;
    private String googleScopes;
    private String appName;

    private OAuth20Service service;
    private OAuth2AccessToken accessToken;
    private PeopleService peopleService;

    public GoogleSSOManager() { initialize(); }

    private void initialize() {
        try {
            InputStream inputStream = LoginServlet.class.getClassLoader().getResourceAsStream("oAuth.json");
            Object obj = new JSONParser().parse(new InputStreamReader(inputStream)); // Read file
            JSONObject jo = (JSONObject) obj;
            googleId = (String) jo.get("google.id");
            googleSecret = (String) jo.get("google.secret");
            googleScopes = (String) jo.get("google.scopes");
            appName = (String) jo.get("app.name");

            service = new ServiceBuilder(googleId)
                    .apiSecret(googleSecret)
                    .defaultScope(googleScopes)
                    .callback("http://localhost:8080/demo_war/AuthorizationServlet")
                    .build(GoogleApi20.instance());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getAuthorizationURL() {
        return service.getAuthorizationUrl();
    }

    @Override
    public void getAccessToken(String code) {
        try{
            accessToken = service.getAccessToken(code);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean login() {
        try {
            Credential credentials = new GoogleCredential().setAccessToken(accessToken.getAccessToken());

            peopleService = new PeopleService
                    .Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credentials)
                    .setApplicationName(appName)
                    .build();

            return true;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Person getSelf() {
        try {
            Person person = peopleService.people()
                    .get("people/me")
                    .setPersonFields("names")
                    .execute();

//        session.setAttribute("displayName", person.getNames().get(0).getDisplayName());
            return person;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean getContacts(int id) {
        return false;
    }

    @Override
    public boolean createContact(Contact contact) {

        String firstName = contact.getFirstName();
        String lastName = contact.getLastName();
        try {
            Person contactToCreate = new Person();
            List names = new ArrayList<>();
            names.add(new Name().setGivenName(firstName).setFamilyName(lastName));
            contactToCreate.setNames(names);

            Person createdContact = peopleService.people().createContact(contactToCreate).execute();
            return true;
        }
        catch (Exception e)
        {
            System.out.println("Error here...");
        }

        return false;
    }

    @Override
    public boolean getContact(int id) {
        return false;
    }

    @Override
    public boolean updateContact(Contact contact) {
        return false;
    }

    @Override
    public boolean deleteContact(int id) {
        return false;
    }

    public List<Person> getContacts() {
        try {
            ListConnectionsResponse response = peopleService.people().connections()
                    .list("people/me")
                    .setPersonFields("names,phoneNumbers")
                    .execute();

            return response.getConnections();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Person getContact(String resourceName) {
        try {
            Person person = peopleService.people()
                    .get(resourceName)
                    .setPersonFields("names")
                    .execute();

            return person;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // TODO: finish
    public boolean addContact(Person person, String name) {
        try {
//            Person newPerson = new Person()
//                    .setNames(Arrays.asList());

            Person personResponse = peopleService.people().createContact(person)
                    .setPersonFields("names,phoneNumbers")
                    .execute();

            return personResponse != null;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // TODO: do
    public Person editContact(Person person) {
        try {

        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean deleteContact(String resourceName) {
        try {
            Empty response = peopleService.people()
                    .deleteContact(resourceName)
                    .execute();

            return response.isEmpty();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
