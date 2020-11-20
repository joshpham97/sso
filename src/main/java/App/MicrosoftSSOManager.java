package App;

import Servlet.LoginServlet;
import com.github.scribejava.apis.MicrosoftAzureActiveDirectory20Api;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.microsoft.graph.auth.confidentialClient.AuthorizationCodeProvider;
import com.microsoft.graph.models.extensions.IGraphServiceClient;
import com.microsoft.graph.requests.extensions.GraphServiceClient;
import com.microsoft.graph.requests.extensions.IContactCollectionPage;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MicrosoftSSOManager extends SSOManagerFactory {
    private String appId;
    private String appSecret;
    private String appScopes;
    private String redirectUri = "";

    private AuthorizationCodeProvider authProvider;

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
        } catch (ParseException e) {
            System.out.println("Unable to read OAuth configuration. Make sure you have a properly formatted oAuth.properties file. See README for details.");
        }
    }

    @Override
    public String getAuthorizationURL(){
        OAuth20Service service = new ServiceBuilder(appId)
                .apiSecret(appSecret)
                .defaultScope(appScopes)
                .build(MicrosoftAzureActiveDirectory20Api.instance());

        return service.getAuthorizationUrl();
    }

    @Override
    public void getAccessToken(String authorizationCode) {
        try{
            List<String> listScopes = new ArrayList<String>(Arrays.asList(appScopes.split(",")));

            authProvider = new AuthorizationCodeProvider(
                    appId,
                    listScopes,
                    authorizationCode,
                    redirectUri,
                    appSecret);
        }catch (Exception ex){
            System.out.println("There was an error getting the access token.");
        }
    }

    @Override
    public boolean login() {
        return false;
    }

    @Override
    public boolean getContacts() {
        //Sample for getting contacts
        IGraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).buildClient();

        IContactCollectionPage contacts = graphClient.me().contacts()
                .buildRequest()
                .get();

        return false;
    }

    @Override
    public boolean createContact(Contact contact) {
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
}
