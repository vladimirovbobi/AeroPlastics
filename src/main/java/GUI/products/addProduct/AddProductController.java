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

                if (inventoryLevel >= 3 * quantity) {
                    // Subtract 3 times the quantity from the inventoryLevel in materials table
                    String updateMaterialsQuery = "UPDATE materials SET inventoryLevel = inventoryLevel - ? WHERE materialName = ?";
                    PreparedStatement updateMaterialsStatement = con.prepareStatement(updateMaterialsQuery);
                    updateMaterialsStatement.setInt(1, 3 * quantity);
                    updateMaterialsStatement.setString(2, materialName);
                    updateMaterialsStatement.executeUpdate();

                    // Add the quantity to the inventoryLevel in products table
                    String updateProductsQuery = "UPDATE products SET inventoryLevel = inventoryLevel + ? WHERE productID = ?";
                    PreparedStatement updateProductsStatement = con.prepareStatement(updateProductsQuery);
                    updateProductsStatement.setInt(1, quantity);
                    updateProductsStatement.setInt(2, productID);
                    updateProductsStatement.executeUpdate();

                    con.close();

                    valid.successBox("Product added successfully!");
                } else {
                    error.errorBox("Insufficient inventory for material: " + materialName + ". Please order at least " + (3 * quantity - inventoryLevel) + " more.");
                }
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
