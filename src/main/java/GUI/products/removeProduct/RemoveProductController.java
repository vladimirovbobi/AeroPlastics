package GUI.products.removeProduct;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import GUI.JavaConnector;

public class RemoveProductController {

    @FXML
    private TextField productNameTextField;
    @FXML
    private TextField idTextField;
    @FXML
    private Button removeButton;
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    @FXML
    private void handleRemoveButtonClick() throws IOException {
        String productID = idTextField.getText();
        String productName = productNameTextField.getText();

        if (productID.isEmpty() && productName.isEmpty()) {
            String errorMessage = "Please enter the product ID or product name.";
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

            if (!productID.isEmpty()) {
                query = "DELETE FROM products WHERE productID = ?";
                statement = con.prepareStatement(query);
                statement.setInt(1, Integer.parseInt(productID));
            } else {
                query = "DELETE FROM products WHERE productName = ?";
                statement = con.prepareStatement(query);
                statement.setString(1, productName);
            }

            // Execute the SQL statement
            int rowsAffected = statement.executeUpdate();

            // Close the database connection and resources
            statement.close();
            con.close();

            if (rowsAffected > 0) {
                String successMessage = "Product Removed Successfully.";
                successBox(successMessage);
            } else {
                String errorMessage = "Product not found.";
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