package App;

public class Contact {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public Contact()
    {
        firstName = "";
        lastName = "";
    }
    public Contact(String firstName,String lastName, String email, String phoneNumber)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    public Contact(String firstName,String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
