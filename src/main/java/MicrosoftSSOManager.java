public class MicrosoftSSOManager extends SSOManagerFactory {
    @Override
    public boolean login() {
        return false;
    }

    @Override
    public boolean getContacts(int id) {
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
