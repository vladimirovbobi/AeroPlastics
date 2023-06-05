package GUI.supplies.removeSupply;

import GUI.JavaConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Remove supply controller.
 */
public class RemoveSupplyController {
    @FXML
    TextField supplyOrderIdTextField;
    @FXML
    TextField materialNameTextField;
    @FXML
    private Button cancelButton;
    @FXML
    private Button removeButton;

    private void errorBox(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    private void successBox(String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void handleRemoveButtonClick(ActionEvent actionEvent) throws SQLException {
        // Get the values from the text fields
        int supplyOrderId = Integer.parseInt(supplyOrderIdTextField.getText());
        String materialName = materialNameTextField.getText();

        // Create a database connection
        JavaConnector con = new JavaConnector();
        Connection connection = con.getConnection();
        if (connection == null) {
            errorBox("Failed to connect to the database.");
            return;
        }

        try {
            // Start a transaction
            connection.setAutoCommit(false);

            // Prepare the SELECT query to fetch the quantity from the deleted supply order
            String selectQuery = "SELECT quantity FROM supplyorder WHERE supplyOrderID = ? AND rawMaterial = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setInt(1, supplyOrderId);
            selectStatement.setString(2, materialName);

            // Execute the SELECT query
            ResultSet resultSet = selectStatement.executeQuery();
            int quantity = 0;
            if (resultSet.next()) {
                // Get the quantity from the deleted supply order
                quantity = resultSet.getInt("quantity");
            } else {
                errorBox("Failed to retrieve the quantity from the supply order. Please check the entered values.");
                return;
            }

            // Prepare the DELETE query for supplyorder table
            String deleteQuery = "DELETE FROM supplyorder WHERE supplyOrderID = ? AND rawMaterial = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setInt(1, supplyOrderId);
            deleteStatement.setString(2, materialName);

            // Execute the DELETE query
            int rowsAffected = deleteStatement.executeUpdate();

            // Check if the deletion was successful
            if (rowsAffected > 0) {
                // Prepare the UPDATE query for vendor table
                String updateQuery = "UPDATE vendor SET productQuantity = productQuantity + ? WHERE rawMaterial = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setInt(1, quantity * rowsAffected);  // Adding the quantity from each deleted supply order entry
                updateStatement.setString(2, materialName);

                // Execute the UPDATE query for vendor table
                updateStatement.executeUpdate();

                // Prepare the UPDATE query for materials table
                String updateMaterialsQuery = "UPDATE materials SET inventoryLevel = inventoryLevel - ? WHERE materialName = ?";
                PreparedStatement updateMaterialsStatement = connection.prepareStatement(updateMaterialsQuery);
                updateMaterialsStatement.setInt(1, quantity * rowsAffected); // Subtracting the quantity from each deleted supply order entry
                updateMaterialsStatement.setString(2, materialName);

                // Execute the UPDATE query for materials table
                updateMaterialsStatement.executeUpdate();

                // Commit the transaction
                connection.commit();

                successBox("Supply order removed successfully, quantity updated in vendor table, and inventory level updated in materials table.");
            } else {
                errorBox("Failed to remove the supply order. Please check the entered values.");
            }
        } catch (SQLException e) {
            // Rollback the transaction in case of any error
            connection.rollback();
            errorBox("An error occurred while removing the supply order: " + e.getMessage());
        } finally {
            // Reset the auto-commit mode and close the database connection
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    public void cancelSupplyOrderButtonClicked(ActionEvent actionEvent) {
    }

    public void handleCancelButtonClick(ContextMenuEvent contextMenuEvent) {
    }
}
