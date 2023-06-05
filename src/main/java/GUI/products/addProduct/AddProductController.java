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
import java.sql.ResultSet;
import java.sql.SQLException;

import GUI.JavaConnector;

public class AddProductController {
    @FXML
    public TextField quantityTextField;
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
        String quantity  = quantityTextField.getText();
        String productName = productNameTextField.getText();
        String price = priceTextField.getText();
        String rawMaterial = materialTextField.getText();

        if (productName.isEmpty() || price.isEmpty() || rawMaterial.isEmpty()) {
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
            query = "INSERT INTO products (productID, productName, price, rawMaterial, inventoryLevel) VALUES (?, ?, ?, ?, ?)";
            statement = con.prepareStatement(query);
            statement.setInt(1,getLastProductID()+1);
            statement.setString(2, productName);
            statement.setDouble(3, Double.parseDouble(price));
            statement.setString(4, rawMaterial);
            statement.setString(5,quantity);

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

    /**
     * Retrieve the last product ID
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


    @FXML
    private void handleCancelButtonClick() {
        cancelButton.getScene().getWindow().hide();
    }
}
