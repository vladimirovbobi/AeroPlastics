package GUI.orders;

import GUI.ViewModel;
import GUI.customers.removeCustomer.RemoveCustomerApplication;
import GUI.orders.addOrder.AddOrderApplication;
import GUI.orders.removeOrder.RemoveOrderApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The type Orders controller.
 */
public class OrdersController {

    private ViewModel viewModel;
    public void setViewModel(ViewModel viewModel){
        this.viewModel = viewModel;
    }

    @FXML
    private Button menuButton;
    /**
     * Method that handles menu button in customer page.
     * Redirects to MenuApplication.
     */
    @FXML
    private void handleMenuButtonClick() throws IOException {
        viewModel.showMenuWindow();
    }

    @FXML
    private Button addOrderButton;
    /**
     * Method that handles add order button in order page.
     * Displays external window that has fields for adding an order.
     */
    @FXML
    private void handleAddOrderButtonClick() throws IOException {
        AddOrderApplication addOrderApp = new AddOrderApplication();
        Stage stage = new Stage();
        addOrderApp.start(stage);
    }

    @FXML
    private Button removeOrderButton;
    /**
     * Method that handles remove order button in order page.
     * Displays external window that has fields for removing an order.
     */
    @FXML
    private void handleRemoveOrderButtonClick() throws IOException {
        RemoveOrderApplication removeOrderApp = new RemoveOrderApplication();
        Stage stage = new Stage();
        removeOrderApp.start(stage);
    }
}