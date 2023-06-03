package GUI.orders.removeOrder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import GUI.JavaConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class RemoveOrderController {
    @FXML
    private TextField orderIdTextField;
    @FXML
    private TextField customerIdTextField;
    @FXML
    private Button removeButton;
    @FXML
    private Button cancelButton;

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
     * Handles the button click event for removing a new order.
     *
     * @throws IOException If an I/O exception occurs.
     */
    @FXML
    private void handleRemoveButtonClick()throws IOException {
        String orderId = orderIdTextField.getText();
        String customerId = customerIdTextField.getText();

        if (orderId.isEmpty() || customerId.isEmpty()) {
            String errorMessage = "Please fill in all the fields.";
            errorBox(errorMessage);
            return;
        }

        try {
            // Establish a connection to the database
            JavaConnector connection = new JavaConnector();
            Connection con = connection.getConnection();

            // Prepare the SQL statement
            String query = "DELETE FROM orders WHERE orderID = ? AND customerID = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(orderId));
            statement.setInt(2, Integer.parseInt(customerId));

            // Execute the SQL statement
            int rowsAffected = statement.executeUpdate();

            // Close the database connection and resources
            statement.close();
            con.close();

            if (rowsAffected > 0) {
                String successMessage = "Order removed successfully.";
                successBox(successMessage);
            } else {
                String errorMessage = "No order found with the specified order ID and customer ID.";
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
    private void handleCancelButtonClick() throws IOException{
        cancelButton.getScene().getWindow().hide();
    }
}
