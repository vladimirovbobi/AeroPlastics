package GUI.menu;

//Import necessary Applications
import GUI.customers.CustomerApplication;
import GUI.orders.OrdersApplication;
import GUI.products.ProductApplication;
import GUI.supplies.SupplyApplication;
import GUI.ViewModel;
//Import libraries
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;


/**
 * This is a controller class for MenuApplication.
 * Controls the Menu Application.
 *
 * @author Michael Barry
 */
public class MenuController {

    private ViewModel viewModel;
    public void setViewModel(ViewModel viewModel){
        this.viewModel = viewModel;
    }

    @FXML
    private Button customersButton;
    /**
     * Method that handles customer button in menu page.
     * Redirects to CustomerApplication.
     */
    @FXML
    private void handleCustomersButtonClick() throws IOException {
        viewModel.showCustomersWindow();
    }

    @FXML
    private Button ordersButton;
    /**
     * Method that handles orders button in menu page.
     * Redirects to OrdersApplication.
     */
    @FXML
    private void handleOrdersButtonClick() throws IOException {
        viewModel.showOrderWindow();
    }

    @FXML
    private Button ProductsButton;
    /**
     * Method that handles products button in menu page.
     * Redirects to ProductApplication
     */
    @FXML
    private void handleProductsButtonClick() throws IOException {
        viewModel.showProductWindow();
    }

    @FXML
    private Button suppliesButton;
    /**
     * Method that handles supplies button in menu page.
     * Redirects to SupplyApplication
     */
    @FXML
    private void handleSuppliesButtonClick() throws IOException {
        viewModel.showSupplyWindow();
    }
}
