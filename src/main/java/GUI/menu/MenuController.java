package GUI.menu;

import GUI.ViewModel;
import javafx.event.ActionEvent;
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
    @FXML
    private Button ordersButton;
    @FXML
    private Button productsButton;
    @FXML
    private Button suppliesButton;
    @FXML
    private Button LogoutButton;


    /**
     * Method that handles customer button in menu page.
     * Redirects to CustomerApplication.
     */
    @FXML
    private void handleCustomersButtonClick() throws IOException {
        viewModel.showCustomersWindow();
    }

    /**
     * Method that handles orders button in menu page.
     * Redirects to OrdersApplication.
     */
    @FXML
    private void handleOrdersButtonClick() throws IOException {
        viewModel.showOrderWindow();
    }

    /**
     * Method that handles products button in menu page.
     * Redirects to ProductApplication
     */
    @FXML
    private void handleProductsButtonClick() throws IOException {
        viewModel.showProductWindow();
    }

    /**
     * Method that handles supplies button in menu page.
     * Redirects to SupplyApplication
     */
    @FXML
    private void handleSuppliesButtonClick() throws IOException {
        viewModel.showSupplyWindow();
    }


    /**
     * Log out and return to the Log In window.
     */
    @FXML
    private void handleLogoutButtonClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) LogoutButton.getScene().getWindow();
        stage.close();

        viewModel.showLoginWindow();
    }
}
