package GUI.customers;
import GUI.JavaConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Customer class represents a customer entity with properties
 *  such as ID, first name, last name, and company.
 */
public class Customer {
    final int customerID;
    final String firstName;
    final String lastName;
    final String company;

    /**
     * Constructs a Customer object with the provided first name, last name, and affiliation.
     *
     * @param firstName   The first name of the customer.
     * @param lastName    The last name of the customer.
     * @param affiliation The company or affiliation of the customer.
     */
    public Customer( String firstName, String lastName, String affiliation) {
        this.customerID = getLastCustomerId() + 1;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = affiliation;
    }

    /**
     * Retrieves the last customer ID from the database.
     *
     * @return The last customer ID.
     */
    public static int getLastCustomerId() {
        int max = 0;
        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "SELECT customerID FROM customer";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int currentId = result.getInt("customerID");
                if (currentId > max) {
                    max = currentId;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return max;
    }

    /**
     * Returns the customer ID.
     *
     * @return The customer ID.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Returns the first name of the customer.
     *
     * @return The first name of the customer.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of the customer.
     *
     * @return The last name of the customer.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the company of the customer.
     *
     * @return The company of the customer.
     */
    public String getCompany() {
        return company;
    }
}
