public abstract class SSOManagerFactory {
    String refresh_token;

    public SSOManagerFactory(){}

    /* Sample call:
        SSOType ssoType = SSOType.Google --> Should be passed by the front-end
        SSOManagerFactory ssoManager = SSOManager.getSSOManager(ssoType)

        //Create new contact
        Contact c = new Contact()
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

    //Not sure if ids are string or int -- you can change the signature as need
    public abstract boolean getContacts(int id);
    public abstract boolean createContact(Contact contact);
    public abstract boolean getContact(int id);
    public abstract boolean updateContact(Contact contact);
    public abstract boolean deleteContact(int id);
}
