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
import javafx.scene.control.Alert;
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

    private void errorBox(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    private void successBox(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    @FXML
    private void handleRemoveButtonClick() {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String customerId = customerIdTextField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || customerId.isEmpty()) {
            String errorMessage = "Please enter the information";
            errorBox(errorMessage);
            return;
        }

        try {
            // Establish a connection to the database
            JavaConnector connection = new JavaConnector();
            Connection con = connection.getConnection();

            // Prepare the SQL statement
            String query;
            PreparedStatement statement;

            if (!customerId.isEmpty()) {
                query = "DELETE FROM customer WHERE customerID = ?";
                statement = con.prepareStatement(query);
                statement.setInt(1, Integer.parseInt(customerId));
            } else {
                query = "DELETE FROM customer WHERE firstName = ? AND lastName = ?";
                statement = con.prepareStatement(query);
                statement.setString(1, firstName);
                statement.setString(2, lastName);
            }

            // Execute the SQL statement
            int rowsAffected = statement.executeUpdate();

            // Close the database connection and resources
            statement.close();
            con.close();

            if (rowsAffected > 0) {
                String successMessage = "Customer Removed Successfully.";
                successBox(successMessage);
            } else {
                String errorMessage = "Customer not found.";
                errorBox(errorMessage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the button click event for canceling operation.
     */
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
}
