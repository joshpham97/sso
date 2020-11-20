package App;

public class GoogleSSOManager extends SSOManagerFactory {
    @Override
    public boolean login() {
        return false;
    }

    @Override
    public String getAuthorizationURL() {
        return null;
    }

    @Override
    public void getAccessToken(String code) {

    }

    @Override
    public boolean getContacts() {
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
