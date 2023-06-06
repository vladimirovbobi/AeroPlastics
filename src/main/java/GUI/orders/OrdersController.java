package GUI.orders;

import GUI.JavaConnector;
import GUI.ViewModel;
import GUI.orders.addOrder.AddOrderApplication;
import GUI.orders.removeOrder.RemoveOrderApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.List;

/**
 * The type Orders controller.
 */
public class OrdersController {
    @FXML
    private TableColumn productIDColumn;
    @FXML
    private TableColumn quantityColumn;
    @FXML
    private TableColumn dateOrderedColumn;
    @FXML
    private TableColumn arrivalDateColumn;
    private ViewModel viewModel;
    @FXML
    private Button logoutButton;
    @FXML
    private TextField orderTextField;
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

    /**
     * Initialize the columns in table
     */
    @FXML
    private void initialize() {
        // Initialize table columns
        orderIDColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("Address"));
        isShippedColumn.setCellValueFactory(new PropertyValueFactory<>("isShipped"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        arrivalDateColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
        dateOrderedColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

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

    /**
     * Handles the search button click event.
     * Searches for orders by name or ID and populates the table with the results.
     *
     * @param actionEvent The action event.
     */
    public void handleSingleSearchButtonClicked(ActionEvent actionEvent) {
        String name = orderTextField.getText();
        List<Order> orders = JavaConnector.searchOrdersByNameOrID(name);

        // Clear existing data from the table
        displayTable.getItems().clear();

        // Populate the table with customer data
        displayTable.getItems().addAll(orders);
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

}