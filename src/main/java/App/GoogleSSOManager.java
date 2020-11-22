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
    public List<Contact> getContacts(int id) {
        try {
            ListConnectionsResponse response = peopleService.people().connections()
                    .list("people/me")
                    .setPersonFields("names,phoneNumbers,emailAddresses")
                    .execute();

            return toContactList(response.getConnections());
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean createContact(Contact contact) {
        return false;
    }

    @Override
    public Contact getContact(int id, String resourceName) {
        try {
            Person person = peopleService.people()
                    .get(resourceName)
                    .setPersonFields("names,phoneNumbers,emailAddresses")
                    .execute();

            return toContact(person);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean updateContact(Contact contact) {
        try {
            Person person = new Person();

            person.setNames(Arrays.asList(new Name().setGivenName(contact.getFirstName()).setFamilyName(contact.getLastName())));
            person.setPhoneNumbers(Arrays.asList(new PhoneNumber().setValue(contact.getPhoneNumber())));
            person.setEmailAddresses(Arrays.asList(new EmailAddress().setValue(contact.getEmail())));
            person.setEtag(contact.getEtag());

            Person createdContact = peopleService.people()
                    .updateContact(contact.getResourceName(), person)
                    .setUpdatePersonFields("names,phoneNumbers,emailAddresses")
                    .execute();

            return toContact(createdContact) != null;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteContact(int id, String resourceName) {
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

    private Contact toContact(Person person) {
        Contact contact = new Contact();

        contact.setFirstName(person.getNames().get(0).getGivenName());
        contact.setLastName(person.getNames().get(0).getFamilyName());
        contact.setPhoneNumber(person.getPhoneNumbers().get(0).getValue());
        contact.setEmail(person.getEmailAddresses().get(0).getValue());
        contact.setResourceName(person.getResourceName());
        contact.setEtag(person.getEtag());

        return contact;
    }

    private List<Contact> toContactList(List<Person> people) {
        ArrayList<Contact> contacts = new ArrayList<>();

        for (Person p: people) {
            contacts.add(toContact(p));
        }

        return contacts;
    }
}
