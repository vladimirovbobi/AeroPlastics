package GUI.supplies.resupply;

import GUI.JavaConnector;
import GUI.ViewModel;
import GUI.supplies.Material;
import GUI.supplies.Vendor;
import GUI.supplies.removeSupply.RemoveSupplyApplication;
import GUI.supplies.resupply.resupplyPop.ResupplyPopApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The type Resupply controller.
 */
public class ResupplyController {


    private ViewModel viewModel;
    public void setViewModel(ViewModel viewModel){
        this.viewModel = viewModel;
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
    public void makeOrder(){

        try {

            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "Insert Into supplyOrder (supplyOrderID int, " +
                    "vendorID int, rawMaterial varchar(50),price double, quantity int, orderPlaced int, arrivalDate int)"+
                    "values (" +", "+",";
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


    }
    public void handleRemoveSupplyOrderButtonClick (ActionEvent actionEvent) throws IOException {
        viewModel.showRemoveSupplyOrderPopWindow();
    }


    public void handleViewByIDClick(ActionEvent actionEvent) {

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
