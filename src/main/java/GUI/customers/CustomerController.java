package GUI.customers;

import GUI.ViewModel;
import GUI.customers.addCustomer.AddCustomerApplication;
import GUI.customers.removeCustomer.RemoveCustomerApplication;
import GUI.products.ProductApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The type Customer controller.
 */
public class CustomerController {

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
    private Button addCustomerButton;
    /**
     * Method that handles Add Customer button in customer page.
     * Displays external window that has fields for adding a customer.
     */
    @FXML
    private void handleAddCustomerButtonClick() throws IOException {
        AddCustomerApplication addCustomerApp = new AddCustomerApplication();
        Stage stage = new Stage();
        addCustomerApp.start(stage);
    }

    @FXML
    private Button removeCustomerButton;
    /**
     * Method that handles Remove Customer button in customer page.
     * Displays external window that has fields for removing a customer.
     */
    @FXML
    private void handleRemoveCustomerButtonClick() throws IOException {
        RemoveCustomerApplication removeCustomerApp = new RemoveCustomerApplication();
        Stage stage = new Stage();
        removeCustomerApp.start(stage);
    }
}
