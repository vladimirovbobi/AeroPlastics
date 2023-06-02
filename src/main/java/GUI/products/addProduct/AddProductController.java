package GUI.products.addProduct;

import GUI.products.removeProduct.RemoveProductApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import GUI.JavaConnector;

public class AddProductController {

    @FXML
    private TextField idTextField;
    @FXML
    private TextField productNameTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField materialTextField;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;

    private void errorBox(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    private void successBox(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
    @FXML
    private void handleAddButtonClick() throws IOException {
        String productID = idTextField.getText();
        String productName = productNameTextField.getText();
        String price = priceTextField.getText();
        String rawMaterial = materialTextField.getText();

        if (productID.isEmpty() || productName.isEmpty() || price.isEmpty() || rawMaterial.isEmpty()) {
            String errorMessage = "Please fill in all the fields.";
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

            // Prepare the SQL statement
            query = "INSERT INTO products (productID, productName, price, rawMaterial, inventoryLevel) VALUES (?, ?, ?, ?, 0)";
            statement = con.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(productID));
            statement.setString(2, productName);
            statement.setDouble(3, Double.parseDouble(price));
            statement.setString(4, rawMaterial);

            // Execute the SQL statement
            int rowsAffected = statement.executeUpdate();

            // Close the database connection and resources
            statement.close();
            con.close();

            if (rowsAffected > 0) {
                String successMessage = "Product Added Successfully.";
                successBox(successMessage);
            } else {
                String errorMessage = "Product not added.";
                errorBox(errorMessage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancelButtonClick() {
        cancelButton.getScene().getWindow().hide();
    }
}
