package GUI.orders.addOrder;

import GUI.Date;
import GUI.products.Product;
import GUI.supplies.Material;
import GUI.utility.Shared;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    Shared error = new Shared();
    Shared valid = new Shared();

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
            error.errorBox(errorMessage);
            return;
        }

        try {
            // Establish a connection to the database
            JavaConnector connection = new JavaConnector();
            Connection con = connection.getConnection();

            // Retrieve the product details
            String getProductQuery = "SELECT productName, inventoryLevel FROM products WHERE productID = ?";
            PreparedStatement getProductStatement = con.prepareStatement(getProductQuery);
            getProductStatement.setInt(1, Integer.parseInt(productId));
            ResultSet productResult = getProductStatement.executeQuery();

            if (productResult.next()) {
                String productName = productResult.getString("productName");
                int inventoryLevel = productResult.getInt("inventoryLevel");

                if (inventoryLevel >= Integer.parseInt(quantity)) {
                    // Prepare the SQL statement
                    String query = "INSERT INTO orders (orderID, address, isShipped, customerID, productID, quantity, arrivalDate, OrderDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = con.prepareStatement(query);
                    statement.setInt(1, getLastOrderID() + 1);
                    statement.setString(2, address);
                    statement.setBoolean(3, true);
                    statement.setInt(4, Integer.parseInt(customerId));
                    statement.setInt(5, Integer.parseInt(productId));
                    statement.setInt(6, Integer.parseInt(quantity));
                    statement.setString(7, Date.changeTodaysDateByDays(5));
                    statement.setString(8, Date.todaysDate());

                    // Execute the SQL statement
                    int rowsAffected = statement.executeUpdate();

                    // Close the database connection and resources
                    statement.close();
                    con.close();

                    if (rowsAffected > 0) {
                        String successMessage = "Order added successfully.";
                        valid.successBox(successMessage);
                    } else {
                        String errorMessage = "Order not added.";
                        error.errorBox(errorMessage);
                    }
                } else {
                    // Calculate the required quantity to fulfill the order
                    int quantityNeeded = Integer.parseInt(quantity) - inventoryLevel;

                    // Prepare the SQL statement
                    String query = "INSERT INTO orders (orderID, address, isShipped, customerID, productID, quantity, arrivalDate, OrderDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = con.prepareStatement(query);
                    statement.setInt(1, getLastOrderID() + 1);
                    statement.setString(2, address);
                    statement.setBoolean(3, false);
                    statement.setInt(4, Integer.parseInt(customerId));
                    statement.setInt(5, Integer.parseInt(productId));
                    statement.setInt(6, Integer.parseInt(quantity));
                    statement.setString(7, "");
                    statement.setString(8, Date.todaysDate());

                    // Execute the SQL statement
                    int rowsAffected = statement.executeUpdate();

                    // Close the database connection and resources
                    statement.close();
                    con.close();

                    String errorMessage = "Insufficient inventory for product: " + productName + ". Order added but cannot be shipped. Please produce at least " + quantityNeeded + " more.";
                    error.errorBox(errorMessage);
                }
            } else {
                error.errorBox("Product not found: " + productId);
            }
        } catch (NumberFormatException e) {
            error.errorBox("Invalid input. Please enter numeric values for customer ID, product ID, and quantity.");
        } catch (SQLException e) {
            e.printStackTrace();
            error.errorBox("Error occurred while adding the order.");
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
