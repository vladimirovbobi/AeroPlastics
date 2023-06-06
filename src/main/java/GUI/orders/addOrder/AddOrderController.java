package GUI.orders.addOrder;

import GUI.Date;
import GUI.products.Product;
import GUI.supplies.Material;
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
 * The controller class for the "Add Order" functionality.
 */
public class AddOrderController {

    @FXML
    private TextField customerIdTextField;
    @FXML
    private TextField productIdTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField quantityTextField ;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;

    /**
     * Displays an error dialog box with message.
     *
     * @param text The error message to display.
     */
    private void errorBox(String text){
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
    private void successBox(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    /**
     * Handles the button click event for adding a new order.
     *
     */
    @FXML
    private void handleAddButtonClick() {
        String customerId = customerIdTextField.getText();
        String productId = productIdTextField.getText();
        String address = addressTextField.getText();
        String quantity = quantityTextField.getText();


        if (customerId.isEmpty() || productId.isEmpty() || address.isEmpty()) {
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
            PreparedStatement statement = null;
            boolean success = false;
            if(Product.productIsInStockAndShipped(Integer.parseInt(productId),Integer.parseInt(quantity))){
                success = true;
                successBox("Your order was successful!The estimated delivery date is: " + Date.changeTodaysDateByDays(5));
            }else {
                String material =  Product.getMaterialUsedForProduct(Integer.parseInt(productId));
                if(Material.isInStockToProduce(material,Integer.parseInt(quantity))){
                    success = true;
                    successBox("Your order was successful!The estimated delivery date is: " + Date.changeTodaysDateByDays(5));
                }else{
                    errorBox("No sufficient material or products in inventory. By more "+ material+ " to fulfill the order!");
                }
            }
            int rowsAffected = 0;

                // Prepare the SQL statement
                query = "INSERT INTO orders (orderID, address, isShipped, customerID,productID, quantity, arrivalDate,OrderDate) VALUES (?, ?, ?, ?,?,?,?,?)";
                statement = con.prepareStatement(query);
                statement.setInt(1, getLastOrderID() + 1);
                statement.setString(2, address);
                statement.setBoolean(3, success);
                statement.setInt(4, Integer.parseInt(customerId));
                statement.setInt(5, Integer.parseInt(productId));
                statement.setInt(6, Integer.parseInt(quantity));
                statement.setString(7,Date.changeTodaysDateByDays(5));
                statement.setString(8, Date.todaysDate());

            // Execute the SQL statement
             rowsAffected = statement.executeUpdate();

            // Close the database connection and resources
            statement.close();
            con.close();

            if (rowsAffected > 0) {
                String successMessage = "Order Added Successfully.";
                successBox(successMessage);
            } else {
                String errorMessage = "Order not added.";
                errorBox(errorMessage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve the last order ID
     *
     * @return last ID
     */
    public static int getLastOrderID(){
        int max =0;
        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "Select orderID from orders";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet result = statement.executeQuery();


            while (result.next()) {
                if(result.getInt("orderID")> max){
                    max = result.getInt("orderID");
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
     * Handles the button click event for canceling operation.
     */
    @FXML
    private void handleCancelButtonClick() {
        cancelButton.getScene().getWindow().hide();
    }

}
