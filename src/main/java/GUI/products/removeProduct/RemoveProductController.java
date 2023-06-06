package GUI.products.removeProduct;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import GUI.JavaConnector;

/**
 * The type Remove product controller.
 */
public class RemoveProductController {

    @FXML
    private TextField idTextField;
    @FXML
    private Button removeButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField materialNameTextField;
    @FXML
    private TextField quantityTextField;

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

    @FXML
    private void handleRemoveButtonClick() throws IOException {
        String productID = idTextField.getText();
        String materialName = materialNameTextField.getText();
        String quantityText = quantityTextField.getText();

        if (productID.isEmpty() || materialName.isEmpty() || quantityText.isEmpty()) {
            String errorMessage = "Please enter the product ID, material name, and quantity.";
            errorBox(errorMessage);
            return;
        }

        try {
            // Parse the specified quantity
            int specifiedQuantity = Integer.parseInt(quantityText);

            // Establish a connection to the database
            JavaConnector connection = new JavaConnector();
            Connection con = connection.getConnection();

            // Retrieve the current inventory level for the specified product
            String getQuantityQuery = "SELECT inventoryLevel FROM products WHERE productID = ?";
            PreparedStatement getQuantityStatement = con.prepareStatement(getQuantityQuery);
            getQuantityStatement.setInt(1, Integer.parseInt(productID));
            ResultSet quantityResult = getQuantityStatement.executeQuery();

            if (quantityResult.next()) {
                int currentQuantity = quantityResult.getInt("inventoryLevel");

                if (currentQuantity >= specifiedQuantity) {
                    // Subtract the specified quantity from the inventoryLevel in products table
                    String updateProductsQuery = "UPDATE products SET inventoryLevel = inventoryLevel - ? WHERE productID = ?";
                    PreparedStatement updateProductsStatement = con.prepareStatement(updateProductsQuery);
                    updateProductsStatement.setInt(1, specifiedQuantity);
                    updateProductsStatement.setInt(2, Integer.parseInt(productID));
                    updateProductsStatement.executeUpdate();

                    // Add 3 times the specified quantity to the inventoryLevel in materials table
                    String updateMaterialsQuery = "UPDATE materials SET inventoryLevel = inventoryLevel + ? WHERE materialName = ?";
                    PreparedStatement updateMaterialsStatement = con.prepareStatement(updateMaterialsQuery);
                    updateMaterialsStatement.setInt(1, 3 * specifiedQuantity);
                    updateMaterialsStatement.setString(2, materialName);
                    updateMaterialsStatement.executeUpdate();

                    con.close();

                    String successMessage = "Product removed successfully!";
                    successBox(successMessage);
                } else {
                    String errorMessage = "Insufficient inventory for product with ID: " + productID + " and material: " + materialName + ". Please specify a lower quantity.";
                    errorBox(errorMessage);
                }
            } else {
                String errorMessage = "Product not found.";
                errorBox(errorMessage);
            }
        } catch (NumberFormatException e) {
            errorBox("Invalid quantity specified!");
        } catch (SQLException e) {
            e.printStackTrace();
            errorBox("Error occurred while removing the product!");
        }
    }

    @FXML
    private void handleCancelButtonClick() {
        cancelButton.getScene().getWindow().hide();
    }
}