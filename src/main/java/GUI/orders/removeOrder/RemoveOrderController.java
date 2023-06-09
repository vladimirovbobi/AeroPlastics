package GUI.orders.removeOrder;

import GUI.utility.Shared;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import GUI.JavaConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Remove order controller.
 */
public class RemoveOrderController {
    @FXML
    private TextField orderIdTextField;
    @FXML
    private TextField productIdTextField;
    @FXML
    private Button removeButton;
    @FXML
    private Button cancelButton;
    Shared error = new Shared();
    Shared valid = new Shared();

    /**
     * Handles the button click event for removing a new order.
     *
     */
    @FXML
    private void handleRemoveButtonClick() {
        String orderId = orderIdTextField.getText();
        String productId = productIdTextField.getText();

        if (orderId.isEmpty() || productId.isEmpty()) {
            String errorMessage = "Please fill in all the fields.";
            error.errorBox(errorMessage);
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
                boolean isShipped = resultSet.getBoolean("isShipped");

                if (isShipped) {
                    int quantity = resultSet.getInt("quantity");
                    int productIdToRemove = resultSet.getInt("productID");

                    // Remove the order from the database
                    String removeQuery = "DELETE FROM orders WHERE orderID = ? AND productID = ?";
                    PreparedStatement removeStatement = con.prepareStatement(removeQuery);
                    removeStatement.setInt(1, Integer.parseInt(orderId));
                    removeStatement.setInt(2, Integer.parseInt(productId));
                    int rowsRemoved = removeStatement.executeUpdate();

                    if (rowsRemoved > 0) {
                        // Update the product inventory
                        String updateQuery = "UPDATE products SET inventoryLevel = inventoryLevel + ? WHERE productID = ?";
                        PreparedStatement updateStatement = con.prepareStatement(updateQuery);
                        updateStatement.setInt(1, quantity);
                        updateStatement.setInt(2, productIdToRemove);
                        int rowsAffected = updateStatement.executeUpdate();

                        // Close the database connection and resources
                        updateStatement.close();
                        checkStatement.close();
                        con.close();

                        if (rowsAffected > 0) {
                            String successMessage = "Order removed successfully.";
                            valid.successBox(successMessage);
                        } else {
                            String errorMessage = "Failed to update the product inventory.";
                            error.errorBox(errorMessage);
                        }
                    } else {
                        String errorMessage = "Failed to remove the order.";
                        error.errorBox(errorMessage);
                    }
                } else {
                    // Remove the order from the database
                    String removeQuery = "DELETE FROM orders WHERE orderID = ? AND productID = ?";
                    PreparedStatement removeStatement = con.prepareStatement(removeQuery);
                    removeStatement.setInt(1, Integer.parseInt(orderId));
                    removeStatement.setInt(2, Integer.parseInt(productId));
                    int rowsRemoved = removeStatement.executeUpdate();

                    // Close the database connection and resources
                    removeStatement.close();
                    checkStatement.close();
                    con.close();

                    if (rowsRemoved > 0) {
                        String successMessage = "Order removed successfully.";
                        valid.successBox(successMessage);
                    } else {
                        String errorMessage = "Failed to remove the order.";
                        error.errorBox(errorMessage);
                    }
                }
            } else {
                String errorMessage = "Order not found.";
                error.errorBox(errorMessage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            error.errorBox("Error occurred while removing the order.");
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
