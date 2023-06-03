package GUI.orders;

import GUI.JavaConnector;
import GUI.ViewModel;
import GUI.orders.Order;
import GUI.customers.Customer;
import GUI.customers.removeCustomer.RemoveCustomerApplication;
import GUI.orders.addOrder.AddOrderApplication;
import GUI.orders.removeOrder.RemoveOrderApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * The type Orders controller.
 */
public class OrdersController {

    private ViewModel viewModel;

    @FXML
    private TableView<Order> displayTable;
    @FXML
    private TableColumn<Order, Integer> orderIDColumn;
    @FXML
    private TableColumn<Order, String> addressColumn;
    @FXML
    private TableColumn<Order, Boolean> isShippedColumn;
    @FXML
    private TableColumn<Order, String> customerIDColumn;
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
    @FXML
    private void initialize() {
        // Initialize table columns
        orderIDColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("Address"));
        isShippedColumn.setCellValueFactory(new PropertyValueFactory<>("isShipped"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }

    /**
     * Handles the View All button click event.
     * Populates the orders table with data.
     */
    @FXML
    private void handleViewAllButtonClick() {
        populateOrdersTable();
    }

    /**
     * Populates the orders table with data retrieved from the database.
     */
    public void populateOrdersTable() {
        // Retrieve order data from the database
        List<Order> orders = JavaConnector.getAllOrders();

        // Clear existing data from the table
        displayTable.getItems().clear();

        // Populate the table with order data
        displayTable.getItems().addAll(orders);
    }

}