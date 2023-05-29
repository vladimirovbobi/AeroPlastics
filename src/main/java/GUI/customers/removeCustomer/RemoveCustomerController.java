package GUI.customers.removeCustomer;

import GUI.JavaConnector;
import GUI.customers.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import GUI.ViewModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
public class RemoveCustomerController {
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField customerIdTextField;
    @FXML
    private Button removeButton;
    @FXML
    private Button cancelButton;

    public ViewModel viewModel;

    public void setViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void initialize() {
        removeButton.setOnAction(event -> handleRemoveButtonClick());
        cancelButton.setOnAction(event -> handleCancelButtonClick());
    }

    @FXML
    private void handleRemoveButtonClick() {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        int customerId = Integer.parseInt(customerIdTextField.getText());

        // Find the customer to remove
        Customer customerToRemove = findCustomer(firstName, lastName, customerId);

        if (customerToRemove != null) {
            // Remove the customer from the database
            removeCustomer(customerToRemove);
            System.out.println("Customer removed successfully!");
        } else {
            System.out.println("Customer not found.");
        }
        cancelButton.getScene().getWindow().hide();
    }

    @FXML
    private void handleCancelButtonClick() {
        cancelButton.getScene().getWindow().hide();
    }

    private Customer findCustomer(String firstName, String lastName, int customerId) {
        List<Customer> customers = JavaConnector.getAllCustomers();

        // Find the matching customer based on the provided information
        for (Customer customer : customers) {
            if (customer.getFirstName().equals(firstName) && customer.getLastName().equals(lastName)
                    && customer.getCustomerID() == customerId) {
                return customer;
            }
        }
        // Customer not found
        return null;
    }

    private void removeCustomer(Customer customer) {
        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "DELETE FROM customer WHERE customerID = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, customer.getCustomerID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
