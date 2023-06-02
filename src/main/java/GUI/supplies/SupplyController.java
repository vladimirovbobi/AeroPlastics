package GUI.supplies;

import GUI.JavaConnector;
import GUI.ViewModel;
import GUI.supplies.removeSupply.RemoveSupplyApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * The type Supply controller.
 */
public class SupplyController {
    @FXML
    private TextField materialTextField;
    private ViewModel viewModel;
    @FXML
    private Button menuButton;
    @FXML
    private Button removeMaterialButton;
    @FXML
    private Button resupplyButton;
    @FXML
    private Button viewAllButton;

    @FXML
    private TableView<Material> displayTable;

    @FXML
    private TableColumn<Material, String> nameColumn;
    @FXML
    private TableColumn<Material, Integer> idColumn;
    @FXML
    private TableColumn<Material, Integer> quantityColumn;
    @FXML
    private TableColumn<Material, Integer> tempColumn;
    @FXML
    private TableColumn<Material, Integer> densityColumn;

    /**
     * Set view model.
     *
     * @param viewModel the view model
     */
    public void setViewModel(ViewModel viewModel){
        this.viewModel = viewModel;
    }

    /**
     * Method that handles menu button in supply page.
     * Redirects to MenuApplication.
     */
    @FXML
    private void handleMenuButtonClick() throws IOException {
        viewModel.showMenuWindow();
    }

    /**
     * Method that handles remove button in supply page.
     * Displays external window that has fields for removing a material.
     */
    @FXML
    private void handleRemoveMaterialButtonClick() throws IOException {
        RemoveSupplyApplication removeSupplyApp = new RemoveSupplyApplication();
        Stage stage = new Stage();
        removeSupplyApp.start(stage);

    }

    /**
     * Method that handles add button in supply page.
     * Displays external window that has fields for adding a material.
     */
    @FXML
    private void handleResupplyMaterialButtonClick() throws IOException {
        viewModel.showSupplyOrderWindow();
        Vendor.sortByPrice = false;
        Vendor.sortByName = false;
    }

    /**
     * Method that handles view all button in supply page.
     * Displays table in GUI.
     */
    @FXML
    private void handleViewAllButtonClick() throws IOException {
        populateMaterialsTable();
    }

    /**
     * Method that initializes table in supply window.
     */
    public void initialize() {
        // Initialize table columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("materialName"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("materialID"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryLevel"));
        tempColumn.setCellValueFactory(new PropertyValueFactory<>("moldTemperature"));
        densityColumn.setCellValueFactory(new PropertyValueFactory<>("plasticDensity"));
    }

    /**
     * Method that populates table in supply window.
     */
    public void populateMaterialsTable() {
        // Retrieve materials data from the database (example code)
        List<Material> materials = JavaConnector.getAllMaterials(); // Replace with your actual code to fetch data from the database

        // Clear existing data from the table
        displayTable.getItems().clear();

        // Populate the table with materials data
        displayTable.getItems().addAll(materials);
    }


    public void logOutButtonClicked(ActionEvent actionEvent) {


    }

    public void handleSingleViewButtonClicked(ActionEvent actionEvent) {
        String name = materialTextField.getText();

        List<Material> materials =  JavaConnector.searchMaterialsByName(name);
        displayTable.getItems().clear();
        // Populate the table with materials data
        displayTable.getItems().addAll(materials);
    }
}
