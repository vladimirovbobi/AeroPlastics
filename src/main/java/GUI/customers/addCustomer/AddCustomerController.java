package GUI.customers.addCustomer;
import GUI.customers.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import GUI.ViewModel;
import GUI.JavaConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The type Add customer controller.
 */
public class AddCustomerController {
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField affiliationTextField;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;

    public ViewModel viewModel;


    public void setViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void initialize() {
        addButton.setOnAction(event -> handleAddButtonClick());
        cancelButton.setOnAction(event -> handleCancelButtonClick());
    }

    @FXML
    private void handleAddButtonClick() {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String affiliation = affiliationTextField.getText();

        int customerId = Customer.getLastCustomerId() + 1;

        // Create a new Customer object
        Customer newCustomer = new Customer(firstName, lastName, affiliation);

        // Insert the new customer into the database
        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "INSERT INTO customer (customerID, firstName, lastName, affiliation) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, newCustomer.getCustomerID());
            statement.setString(2, newCustomer.getFirstName());
            statement.setString(3, newCustomer.getLastName());
            statement.setString(4, newCustomer.getCompany());
            statement.executeUpdate();
            System.out.println("Added customer successful!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Close the Add Customer window
        cancelButton.getScene().getWindow().hide();
    }


    @FXML
    private void handleCancelButtonClick() {
        cancelButton.getScene().getWindow().hide();
    }
}


