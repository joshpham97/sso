package App;

import Servlet.LoginServlet;
import com.github.scribejava.apis.MicrosoftAzureActiveDirectory20Api;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.microsoft.graph.models.extensions.IGraphServiceClient;
import com.microsoft.graph.requests.extensions.GraphServiceClient;
import com.microsoft.graph.auth.confidentialClient.AuthorizationCodeProvider;
import com.microsoft.graph.requests.extensions.IContactCollectionPage;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MicrosoftSSOManager extends SSOManagerFactory {
    private String appId;
    private String appSecret;
    private String appScopes;


    private AuthorizationCodeProvider authProvider;
    private OAuth20Service service;
    private OAuth2AccessToken accessToken;

    public MicrosoftSSOManager(){
        initialize();
    }

    private void initialize() {
        try {
            InputStream inputStream = LoginServlet.class.getClassLoader().getResourceAsStream("oAuth.json");
            Object obj = new JSONParser().parse(new InputStreamReader(inputStream)); // Read file
            JSONObject jo = (JSONObject) obj;
            appId = (String) jo.get("app.id");
            appSecret = (String) jo.get("app.secret");
            appScopes = (String) jo.get("app.scopes");

            service = new ServiceBuilder(appId)
                    .apiSecret(appSecret)
                    .defaultScope(appScopes)
                    .build(MicrosoftAzureActiveDirectory20Api.instance());
        } catch (ParseException e) {
            System.out.println("Unable to read OAuth configuration. Make sure you have a properly formatted oAuth.properties file. See README for details.");
        }
    }

    @Override
    public String getAuthorizationURL(){
        return service.getAuthorizationUrl();
    }

    @Override
    public void getAccessToken(String code) {
        try{
            accessToken = service.getAccessToken(code);
        }catch (Exception ex){
            System.out.println("There was an error getting the access token.");
        }
    }

    @Override
    public boolean login() {
        //Just some sample code to test -- DO NOT use HTTP Request like this, use the JAVA LIBRARY FOR CONTACTS!
        final OAuthRequest request = new OAuthRequest(Verb.GET, "https://graph.microsoft.com/v1.0/me");
        service.signRequest(accessToken, request);
        try (Response response = service.execute(request)) {
            System.out.println("Got it! Lets see what we found...");
            System.out.println();
            System.out.println(response.getCode());
            System.out.println(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Contact> getContacts(int id) {

        IGraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).buildClient();

        IContactCollectionPage contacts = graphClient.me().contacts()
                .buildRequest()
                .get();
        return null;
    }

    @Override
    public boolean createContact(Contact contact) {
        String firstName = contact.getFirstName();
        String lastName = contact.getLastName();
        String phoneNumber = contact.getPhoneNumber();
        String email = contact.getEmail();
        try {

            IGraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider( authProvider ).buildClient();

            Contact contactToCreate = new Contact();

            contactToCreate.setFirstName(firstName);
            contactToCreate.setLastName(lastName);
            contactToCreate.setPhoneNumber(phoneNumber);
            contactToCreate.setEmail(email);

            graphClient.me().contacts()
                    .buildRequest()
                    .post(contactToCreate);

            return true;
        }
        catch (Exception e)
        {
            System.out.println("Error createContact()!!");
            return false;
        }
    }

    @Override
    public Contact getContact(int id, String resourceName) {
        try {


            IGraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).buildClient();

            com.microsoft.graph.models.extensions.Contact contact = graphClient.me().contacts("{id}")
                    .buildRequest()
                    .get();

            Contact person = new Contact();
            person.setFirstName(contact.givenName);
            person.setLastName(contact.surname);
            person.setPhoneNumber(contact.mobilePhone);
            person.setEmail(contact.emailAddresses.get(0).address);
            return person;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public boolean updateContact(Contact contact) {
        try{
            IGraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider( authProvider ).buildClient();

            Contact contactToUpdate = new Contact();


            graphClient.me().contacts("{id}")
                    .buildRequest()
                    .patch(contactToUpdate);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteContact(int id, String resourceName) {

        try{
            IGraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider( authProvider ).buildClient();

            graphClient.me().contacts("{id}")
                    .buildRequest()
                    .delete();
            return true;
        }
       catch (Exception e) {
           return false;
       }
    }
}
