package GUI.supplies.resupply;

import GUI.JavaConnector;
import GUI.ViewModel;
import GUI.supplies.Vendor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.sql.*;
import java.util.List;

/**
 * The type Resupply controller.
 */
public class ResupplyController {
    @FXML
    private TextField materialTextField;
    @FXML

    private TableView<Vendor> displayTable;

    @FXML
    private TableColumn<Vendor, String> nameColumn;
    @FXML
    private TableColumn<Vendor, Integer> idColumn;
    @FXML
    private TableColumn<Vendor, String> materialColumn;
    @FXML
    private TableColumn<Vendor, Integer> quantityColumn;
    @FXML
    private TableColumn<Vendor, Double>  priceColumn;
    private ViewModel viewModel;

    /**
     * Set the view model to show the Resupply window
     * @param viewModel View Model
     */
    public void setViewModel(ViewModel viewModel){
        this.viewModel = viewModel;
    }

    /**
     * Initialize the table with the columns corresponding to the columns from the table
     */
    public void initializeTable() {
        // Initialize table columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("vendorID"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        materialColumn.setCellValueFactory(new PropertyValueFactory<>("rawMaterial"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    private Button menuButton;
    /**
     * Method that handles menu button in product page.
     * Redirects to MenuApplication.
     */
    @FXML
    private void handleMenuButtonClick() throws IOException {
        viewModel.showMenuWindow();
    }

    /**
     * Populate the table with all the products vendors sell
     */
    public void populateVendorsTable(){
        List<Vendor> vendors =  JavaConnector.getAllVendors();
        displayTable.getItems().clear();
        // Populate the table with materials data
        displayTable.getItems().addAll(vendors);
    }

    /**
     * Sorts and displays all items by their price
     * @param actionEvent button listener
     */
    public void handleViewByPriceButtonClick(ActionEvent actionEvent) {
        List<Vendor> vendors =  Vendor.getVendorsByPrice();
        displayTable.getItems().clear();
        // Populate the table with materials data
        displayTable.getItems().addAll(vendors);
    }

    /**
     * Remove supply order button opens a pop-up window
     * @param actionEvent button listener
     * @throws IOException
     */
    public void handleRemoveSupplyOrderButtonClick (ActionEvent actionEvent) throws IOException {
        viewModel.showRemoveSupplyOrderPopWindow();
    }

    /**
     * View Cart - populates the table with all the items from the cart
     * @param actionEvent button listener
     * @throws SQLException MySQL
     */
    public void handleViewCartButtonClick(ActionEvent actionEvent) throws SQLException {
        Cart.updateAmount();
        List<Vendor> vendors =  JavaConnector.getCart();

        displayTable.getItems().clear();
        // Populate the table with materials data
        displayTable.getItems().addAll(vendors);
    }

    /**
     * Order Material - opens a pop-up window
     * @param actionEvent button listener
     * @throws IOException Display Window
     */
    public void handleOrderMaterialButtonClick(ActionEvent actionEvent) throws IOException {
       viewModel.showResupplyPopWindow();
    }

    /**
     * Log out
     * @param actionEvent button listener
     */
    public void logOutButtonClicked(ActionEvent actionEvent) {

    }

    /**
     * Returns to the Inventory window
     * @param actionEvent button listener
     * @throws IOException display inventory window
     */
    public void handleBackButtonClick(ActionEvent actionEvent) throws IOException {
        viewModel.showSupplyWindow();
    }

    /**
     * Resets all the values in the database and the cart object
     * @param actionEvent button listener
     */
    public void handleResetCartButtonPress(ActionEvent actionEvent) {
        Cart cart = Cart.getInstance();
        cart.deleteCart();

    }

    /**
     * Sorts and populates the table with the items sorted in alphabetical order
     * @param actionEvent button listener
     */
    public void handleViewByMaterialClick(ActionEvent actionEvent) {
        List<Vendor> vendors =  Vendor.getVendorsByMaterial();
        displayTable.getItems().clear();
        // Populate the table with materials data
        displayTable.getItems().addAll(vendors);
    }

    /**
     * Gets input from the textField and displays the correct item in the table
     * @param actionEvent button listener
     */
    public void handleViewButtonClick(ActionEvent actionEvent) {
        String name = materialTextField.getText();

        List<Vendor> vendors =  JavaConnector.searchVendorMaterialByName(name);
        displayTable.getItems().clear();
        // Populate the table with materials data
        displayTable.getItems().addAll(vendors);
    }

    /**
     * Displays all supply orders made
     * @param actionEvent button listener
     */
    public void handleViewSupplyOrderButtonClick(ActionEvent actionEvent) {
        List<Vendor> vendors =  JavaConnector.getSupplyOrders();
        displayTable.getItems().clear();
        // Populate the table with materials data
        displayTable.getItems().addAll(vendors);
    }
}
