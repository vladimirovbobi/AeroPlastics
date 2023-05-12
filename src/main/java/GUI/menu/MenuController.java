package GUI.menu;

import GUI.customers.CustomerApplication;
import GUI.orders.OrdersApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    @FXML
    private Button customersButton;

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
}
