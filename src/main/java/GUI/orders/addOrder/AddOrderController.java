package GUI.orders.addOrder;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.CheckBox;
import GUI.orders.Order;

import GUI.JavaConnector;
/**
 * The controller class for the "Add Order" functionality.
 */

public class AddOrderController {

    @FXML
    private TextField customerIdTextField;
    @FXML
    private TextField orderIdTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private CheckBox shippedCheckBox;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;

    /**
     * Displays an error dialog box with message.
     *
     * @param text The error message to display.
     */
    private void errorBox(String text){
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
    private void successBox(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    /**
     * Handles the button click event for adding a new order.
     *
     * @throws IOException If an I/O exception occurs.
     */
    @FXML
    private void handleAddButtonClick() throws IOException {
        String customerId = customerIdTextField.getText();
        String orderId = orderIdTextField.getText();
        String address = addressTextField.getText();
        boolean isShipped = shippedCheckBox.isSelected();

        if (customerId.isEmpty() || orderId.isEmpty() || address.isEmpty()) {
            String errorMessage = "Please fill in all the fields.";
            errorBox(errorMessage);
            return;
        }

        try {
            // Create an Order object
            Order order = new Order(Integer.parseInt(orderId), address, isShipped, Integer.parseInt(customerId));

            // Establish a connection to the database
            JavaConnector connection = new JavaConnector();
            Connection con = connection.getConnection();

            // Prepare the SQL statement
            String query;
            PreparedStatement statement;

            // Prepare the SQL statement
            query = "INSERT INTO orders (orderID, address, isShipped, customerID) VALUES (?, ?, ?, ?)";
            statement = con.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(orderId));
            statement.setString(2, address);
            statement.setBoolean(3, isShipped);
            statement.setInt(4, Integer.parseInt(customerId));

            // Execute the SQL statement
            int rowsAffected = statement.executeUpdate();

            // Close the database connection and resources
            statement.close();
            con.close();

            if (rowsAffected > 0) {
                String successMessage = "Order Added Successfully.";
                successBox(successMessage);
            } else {
                String errorMessage = "Order not added.";
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

}
