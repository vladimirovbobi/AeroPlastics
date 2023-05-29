package GUI.supplies.resupply;

import GUI.JavaConnector;
import GUI.ViewModel;
import GUI.supplies.Vendor;
import GUI.supplies.resupply.resupplyPop.ResupplyPopController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.*;
import java.util.List;


/**
 * The type Resupply controller.
 */
public class ResupplyController {
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
    public void initialize() {
        // Initialize table columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nameColumn"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idColumn"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("materialColumn"));
        materialColumn.setCellValueFactory(new PropertyValueFactory<>("quantityColumn"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("priceColumn"));
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
        List<Vendor> vendors =  JavaConnector.getAllVendor();
        displayTable.getItems().clear();
        // Populate the table with materials data
        displayTable.getItems().addAll(vendors);
    }
    public void makeOrder(){

        try {

            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "Insert Into supplyOrder (supplyOrderID int, " +
                    "vendorID int, rawMaterial varchar(50),price double, quantity int, orderPlaced int, arrivalDate int)"+
                    "values (1,"+", "+",";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                System.out.println(result.getString(2));
            }
        }catch(NumberFormatException numF1) {

        }catch(Exception e1) {
            e1.printStackTrace();
        }
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


    public void handleViewCartButtonClick(ActionEvent actionEvent) {
    }

    public void handleOrderMaterialButtonClick(ActionEvent actionEvent) throws IOException {
       viewModel.showResupplyPopWindow();
    }

    public void logOutButtonClicked(ActionEvent actionEvent) {

    }

    public void handleBackButtonClick(ActionEvent actionEvent) throws IOException {
        viewModel.showSupplyWindow();
    }

    public void hadleResetCartButtonPress(ActionEvent actionEvent) {
        Cart cart = Cart.getInstance();
        cart.deleteCart();
        ResupplyPopController.setFirstTime(true);
    }

    public void handleViewByMateialClick(ActionEvent actionEvent) {
        List<Vendor> vendors = Vendor.getVendorsByMaterial();
    }

    /*
    public void populateVendorsTable() {
        Vendor vendor = new Vendor();
        // Retrieve Vendors data from the database (example code)
        List<Vendor> vendors = vendor.getVendorsByPrice(); // Replace with your actual code to fetch data from the database

        // Clear existing data from the table
        displayTable.getItems().clear();

        // Populate the table with materials data
        displayTable.getItems().addAll(vendors);
    }

     */
}
