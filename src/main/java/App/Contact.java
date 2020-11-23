package App;

public class Contact implements java.io.Serializable {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    private String resourceName;
    private String etag;

    public Contact() {
        firstName = new String();
        lastName = new String();
        phoneNumber = new String();
        email = new String();
    }

    public Contact(String firstName, String lastName, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Contact(String firstName, String lastName, String phoneNumber, String email, String resourceName, String etag) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.resourceName = resourceName;
        this.etag = etag;
    }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getPhoneNumber() { return phoneNumber; }

    public String getEmail() { return email; }

    public String getResourceName() { return resourceName; }

    public String getEtag() { return etag; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public void setEtag(String etag) { this.etag = etag; }
}
