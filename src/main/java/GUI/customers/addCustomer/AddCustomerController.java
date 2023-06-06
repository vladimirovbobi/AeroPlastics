package GUI.customers.addCustomer;
import GUI.customers.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import GUI.ViewModel;
import GUI.JavaConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Alert;

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

    /**
     * The View model.
     */
    public ViewModel viewModel;


    /**
     * Sets view model.
     *
     * @param viewModel the view model
     */
    public void setViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Displays an error dialog box with message.
     *
     * @param text The error message to display.
     */
    private void errorBox(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    /**
     * Displays a success dialog box with message.
     *
     * @param text The success message to display.
     */
    private void successBox(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
    /**
     * Handles the button click event for adding a new customer.
     *
     * @throws IOException If an I/O exception occurs.
     */
    @FXML
    private void handleAddButtonClick() {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String affiliation = affiliationTextField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || affiliation.isEmpty()) {
            String errorMessage = "Please fill in all the fields.";
            errorBox(errorMessage);
            return;
        }

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
            statement.setString(4, newCustomer.getAffiliation());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Close the Add Customer window
        cancelButton.getScene().getWindow().hide();
    }
    /**
     * Handles the button click event for canceling operation.
     */
    @FXML
    private void handleCancelButtonClick() {
        cancelButton.getScene().getWindow().hide();
    }
}


