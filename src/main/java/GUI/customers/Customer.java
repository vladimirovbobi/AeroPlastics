package GUI.customers;
public class Customer {
    private int customerID;
    private String firstName;
    private String lastName;
    private String company;

    public Customer(String firstName, String lastName, String affiliation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = affiliation;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
