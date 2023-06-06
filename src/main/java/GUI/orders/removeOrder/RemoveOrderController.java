package GUI.orders.removeOrder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import GUI.JavaConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class RemoveOrderController {
    @FXML
    private TextField orderIdTextField;
    @FXML
    private TextField productIdTextField;
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
        String productId = productIdTextField.getText();

        if (orderId.isEmpty() || productId.isEmpty()) {
            String errorMessage = "Please fill in all the fields.";
            errorBox(errorMessage);
            return;
        }

        try {
            // Establish a connection to the database
            JavaConnector connection = new JavaConnector();
            Connection con = connection.getConnection();

            // Check if the order exists
            String checkQuery = "SELECT * FROM orders WHERE orderID = ? AND productID = ?";
            PreparedStatement checkStatement = con.prepareStatement(checkQuery);
            checkStatement.setInt(1, Integer.parseInt(orderId));
            checkStatement.setInt(2, Integer.parseInt(productId));
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                int quantity = resultSet.getInt("quantity");
                int productIdToRemove = resultSet.getInt("productID");

                // Update the product inventory
                String updateQuery = "UPDATE products SET inventoryLevel = inventoryLevel + ? WHERE productID = ?";
                PreparedStatement updateStatement = con.prepareStatement(updateQuery);
                updateStatement.setInt(1, quantity);
                updateStatement.setInt(2, productIdToRemove);
                int rowsAffected = updateStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // Remove the order from the database
                    String removeQuery = "DELETE FROM orders WHERE orderID = ? AND productID = ?";
                    PreparedStatement removeStatement = con.prepareStatement(removeQuery);
                    removeStatement.setInt(1, Integer.parseInt(orderId));
                    removeStatement.setInt(2, Integer.parseInt(productId));
                    int rowsRemoved = removeStatement.executeUpdate();

                    // Close the database connection and resources
                    removeStatement.close();
                    updateStatement.close();
                    checkStatement.close();
                    con.close();

                    if (rowsRemoved > 0) {
                        String successMessage = "Order removed successfully.";
                        successBox(successMessage);
                    } else {
                        String errorMessage = "Failed to remove the order.";
                        errorBox(errorMessage);
                    }
                } else {
                    String errorMessage = "Failed to update the product inventory.";
                    errorBox(errorMessage);
                }
            } else {
                String errorMessage = "Order not found.";
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
