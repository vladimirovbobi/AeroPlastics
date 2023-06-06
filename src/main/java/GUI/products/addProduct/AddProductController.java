package GUI.products.addProduct;

import GUI.utility.Shared;
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
 * The type Add product controller.
 */
public class AddProductController {
    @FXML
    public TextField quantityTextField;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField materialTextField;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;
    Shared error = new Shared();
    Shared valid = new Shared();

    /**
     * Decrements supplies and adds to products
     */
    @FXML
    private void handleAddButtonClick() {
        int productID = Integer.parseInt(idTextField.getText());
        String materialName = materialTextField.getText();
        int quantity = Integer.parseInt(quantityTextField.getText());

        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();

            // Retrieve the current inventory level of the material
            String getInventoryLevelQuery = "SELECT inventoryLevel FROM materials WHERE materialName = ?";
            PreparedStatement getInventoryLevelStatement = con.prepareStatement(getInventoryLevelQuery);
            getInventoryLevelStatement.setString(1, materialName);
            ResultSet inventoryLevelResult = getInventoryLevelStatement.executeQuery();

            if (inventoryLevelResult.next()) {
                int inventoryLevel = inventoryLevelResult.getInt("inventoryLevel");

                // Add the quantity to the inventoryLevel in products table
                String updateProductsQuery = "UPDATE products SET inventoryLevel = inventoryLevel + ? WHERE productID = ?";
                PreparedStatement updateProductsStatement = con.prepareStatement(updateProductsQuery);
                updateProductsStatement.setInt(1, quantity);
                updateProductsStatement.setInt(2, productID);
                updateProductsStatement.executeUpdate();

                // Check if there are pending orders for the product and update their status
                String checkOrdersQuery = "SELECT orderID, quantity FROM orders WHERE productID = ? AND isShipped = false";
                PreparedStatement checkOrdersStatement = con.prepareStatement(checkOrdersQuery);
                checkOrdersStatement.setInt(1, productID);
                ResultSet pendingOrdersResult = checkOrdersStatement.executeQuery();

                while (pendingOrdersResult.next()) {
                    int orderID = pendingOrdersResult.getInt("orderID");
                    int orderQuantity = pendingOrdersResult.getInt("quantity");

                    // Retrieve the updated inventory level after adding the ordered quantity
                    String getUpdatedInventoryLevelQuery = "SELECT inventoryLevel FROM products WHERE productID = ?";
                    PreparedStatement getUpdatedInventoryLevelStatement = con.prepareStatement(getUpdatedInventoryLevelQuery);
                    getUpdatedInventoryLevelStatement.setInt(1, productID);
                    ResultSet updatedInventoryLevelResult = getUpdatedInventoryLevelStatement.executeQuery();

                    if (updatedInventoryLevelResult.next()) {
                        int updatedInventoryLevel = updatedInventoryLevelResult.getInt("inventoryLevel");

                        if (updatedInventoryLevel >= orderQuantity) {
                            // Subtract the order quantity from the inventoryLevel in products table
                            String decrementInventoryQuery = "UPDATE products SET inventoryLevel = inventoryLevel - ? WHERE productID = ?";
                            PreparedStatement decrementInventoryStatement = con.prepareStatement(decrementInventoryQuery);
                            decrementInventoryStatement.setInt(1, orderQuantity);
                            decrementInventoryStatement.setInt(2, productID);
                            decrementInventoryStatement.executeUpdate();

                            // Update the order status to isShipped = true
                            String updateOrdersQuery = "UPDATE orders SET isShipped = true WHERE orderID = ?";
                            PreparedStatement updateOrdersStatement = con.prepareStatement(updateOrdersQuery);
                            updateOrdersStatement.setInt(1, orderID);
                            updateOrdersStatement.executeUpdate();
                        } else {
                            error.errorBox("Insufficient inventory for material: " + materialName + " to fulfill the order with ID: " + orderID);
                            valid.successBox("Product could not be added due to insufficient inventory!");
                            return; // Exit the method if there is insufficient inventory
                        }
                    }
                }

                con.close();

                valid.successBox("Product added successfully!");
            } else {
                error.errorBox("Material not found: " + materialName);
            }
        } catch (NumberFormatException e) {
            error.errorBox("Invalid quantity specified!");
        } catch (SQLException e) {
            e.printStackTrace();
            error.errorBox("Error occurred while adding the product!");
        }
    }

    /**
     * Retrieve the last product ID
     *
     * @return last ID
     */
    public static int getLastProductID(){
        int max =0;
        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "Select productID from products";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet result = statement.executeQuery();


            while (result.next()) {
                if(result.getInt("productID")> max){
                    max = result.getInt("productID");
                }
            }

        } catch (NumberFormatException numF1) {
            return 0;
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return max;
    }

    /**
     * Closes window.
     */
    @FXML
    private void handleCancelButtonClick() {
        cancelButton.getScene().getWindow().hide();
    }
}
