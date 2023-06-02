package GUI.supplies.resupply;

import GUI.JavaConnector;
import GUI.ViewModel;
import GUI.supplies.Material;
import GUI.supplies.Vendor;
import GUI.supplies.resupply.resupplyPop.ResupplyPopController;
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

    public void setViewModel(ViewModel viewModel){
        this.viewModel = viewModel;
    }

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
    public void populateVendorsTable(){
        List<Vendor> vendors =  JavaConnector.getAllVendors();
        displayTable.getItems().clear();
        // Populate the table with materials data
        displayTable.getItems().addAll(vendors);
    }

    public void handleViewByPriceButtonClick(ActionEvent actionEvent) {
        List<Vendor> vendors =  Vendor.getVendorsByPrice();
        displayTable.getItems().clear();
        // Populate the table with materials data
        displayTable.getItems().addAll(vendors);
    }
    public void handleRemoveSupplyOrderButtonClick (ActionEvent actionEvent) throws IOException {
        viewModel.showRemoveSupplyOrderPopWindow();
    }


    public void handleViewCartButtonClick(ActionEvent actionEvent) throws SQLException {
        Cart.updateAmount();
        List<Vendor> vendors =  JavaConnector.getCart();

        displayTable.getItems().clear();
        // Populate the table with materials data
        displayTable.getItems().addAll(vendors);
    }

    public void handleOrderMaterialButtonClick(ActionEvent actionEvent) throws IOException {
       viewModel.showResupplyPopWindow();
    }

    public void logOutButtonClicked(ActionEvent actionEvent) {

    }

    public void handleBackButtonClick(ActionEvent actionEvent) throws IOException {
        viewModel.showSupplyWindow();
    }

    public void handleResetCartButtonPress(ActionEvent actionEvent) {
        Cart cart = Cart.getInstance();
        cart.deleteCart();

    }

    public void handleViewByMaterialClick(ActionEvent actionEvent) {
        List<Vendor> vendors =  Vendor.getVendorsByMaterial();
        displayTable.getItems().clear();
        // Populate the table with materials data
        displayTable.getItems().addAll(vendors);
    }


    public void handleViewButtonClick(ActionEvent actionEvent) {
        String name = materialTextField.getText();

        List<Vendor> vendors =  JavaConnector.searchVendorMaterialByName(name);
        displayTable.getItems().clear();
        // Populate the table with materials data
        displayTable.getItems().addAll(vendors);
    }

    public void handleViewSupplyOrderButtonClick(ActionEvent actionEvent) {
        List<Vendor> vendors =  JavaConnector.getSupplyOrders();
        displayTable.getItems().clear();
        // Populate the table with materials data
        displayTable.getItems().addAll(vendors);
    }
}
