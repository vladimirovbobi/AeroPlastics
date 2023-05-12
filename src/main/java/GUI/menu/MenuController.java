package GUI.menu;

//Import necessary Applications
import GUI.customers.CustomerApplication;
import GUI.orders.OrdersApplication;
import GUI.products.ProductApplication;
import GUI.supplies.SupplyApplication;
//Import libraries
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * This is a controller class for MenuApplication.
 * Controls the Menu Application.
 *
 * @author
 */
public class MenuController {
    @FXML
    private Button customersButton;

    /**
     * Method that handles customer button in menu page.
     * Redirects to CustomerApplication.
     */
    @FXML
    private void handleCustomersButtonClick() {
        try {
            CustomerApplication customerApp = new CustomerApplication();
            Stage stage = new Stage();
            customerApp.start(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button ordersButton;

    /**
     * Method that handles orders button in menu page.
     * Redirects to OrdersApplication.
     */
    @FXML
    private void handleOrdersButtonClick() {
        try {
            OrdersApplication OrdersApp = new OrdersApplication();
            Stage stage = new Stage();
            OrdersApp.start(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that handles products button in menu page.
     * Redirects to ProductApplication
     */
    @FXML
    private Button ProductsButton;

    @FXML
    private void handleProductsButtonClick() {
        try {
            ProductApplication ProductApp = new ProductApplication();
            Stage stage = new Stage();
            ProductApp.start(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that handles supplies button in menu page.
     * Redirects to SupplyApplication
     */
    @FXML
    private Button suppliesButton;

    @FXML
    private void handleSuppliesButtonClick() {
        try {
            SupplyApplication SupplyApp = new SupplyApplication();
            Stage stage = new Stage();
            SupplyApp.start(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
