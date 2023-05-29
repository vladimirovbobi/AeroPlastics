package GUI.customers;
import GUI.JavaConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Customer {
    final int customerID;
    final String firstName;
    final String lastName;
    final String company;

    public Customer(String firstName, String lastName, String affiliation) {
        this.customerID = getLastCustomerId() + 1;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = affiliation;
    }

    public static int getLastCustomerId() {
        int max = 0;
        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "SELECT customerID FROM customers";
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

    public int getCustomerID() {
        return customerID;
    }

   // public void setCustomerID(int customerID) {
   //     this.customerID = customerID;
   // }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompany() {
        return company;
    }
}
