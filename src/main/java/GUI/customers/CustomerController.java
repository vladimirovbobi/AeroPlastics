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
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import GUI.JavaConnector;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

/**
 * The type Customer controller.
 */
public class CustomerController {
    @FXML
    private TextField customerTextField;
    @FXML
    private Button logoutButton;
    private ViewModel viewModel;
    @FXML
    private TableView<Customer> displayTable;
    @FXML
    private TableColumn<Customer, String> firstNameColumn;
    @FXML
    private TableColumn<Customer, String> lastNameColumn;
    @FXML
    private TableColumn<Customer, String> affiliationColumn;
    @FXML
    private TableColumn<Customer, Integer> customerIDColumn;


    /**
     * Set view model.
     *
     * @param viewModel the view model
     */
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

    @FXML
    private void initialize() {
        // Initialize table columns
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        affiliationColumn.setCellValueFactory(new PropertyValueFactory<>("affiliation"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }

    /**
     * Handles the View All button click event.
     * Populates the customers table with data.
     */
    @FXML
    private void handleViewAllButtonClick() {
        populateCustomersTable();
    }

    /**
     * Populates the customers table with data retrieved from the database.
     */
    public void populateCustomersTable() {
        // Retrieve customer data from the database
        List<Customer> customers = JavaConnector.getAllCustomers();

        // Clear existing data from the table
        displayTable.getItems().clear();

        // Populate the table with customer data
        displayTable.getItems().addAll(customers);
    }

    /**
     * Logout and return to the login window
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void handleLogOutButtonClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();

        viewModel.showLoginWindow();
    }

    /**
     * Method to search customer from the database
     *
     * @param actionEvent the action event
     */
    public void handleSingleSearchButtonClicked(ActionEvent actionEvent) {
        String name = customerTextField.getText();
        List<Customer> customers = JavaConnector.searchCustomersByNameOrID(name);

        // Clear existing data from the table
        displayTable.getItems().clear();

        // Populate the table with customer data
        displayTable.getItems().addAll(customers);
    }
}

