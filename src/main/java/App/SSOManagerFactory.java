package App;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public abstract class SSOManagerFactory {
    String refresh_token;

    public SSOManagerFactory(){}

    /* Sample call:
        App.SSOType ssoType = App.SSOType.Google --> Should be passed by the front-end
        App.SSOManagerFactory ssoManager = SSOManager.getSSOManager(ssoType)

        //Create new contact
        App.Contact c = new App.Contact()
        ..set attributes for c...
        ssoManager.createContact(c)
     */

    public static SSOManagerFactory getSSOManager(SSOType ssoType){
        if (ssoType == SSOType.Google) {
            return new GoogleSSOManager();
        }else if(ssoType == SSOType.Microsoft){
            return new MicrosoftSSOManager();
        }

        return null;
    }

    //Methods to be implemented
    public abstract boolean login();
    public abstract String getAuthorizationURL();
    public abstract void getAccessToken(String code);

    //Not sure if ids are string or int -- you can change the signature as need
    public abstract List<Contact> getContacts(int id);
    public abstract boolean createContact(Contact contact);
    public abstract Contact getContact(int id, String resourceName);
    public abstract boolean updateContact(Contact contact);
    public abstract boolean deleteContact(int id, String resourceName);
}
