package GUI.supplies;

import GUI.ViewModel;
import GUI.supplies.removeSupply.RemoveSupplyApplication;
import GUI.supplies.resupply.ResupplyApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The type Supply controller.
 */
public class SupplyController {

    private ViewModel viewModel;
    public void setViewModel(ViewModel viewModel){
        this.viewModel = viewModel;
    }

    @FXML
    private Button menuButton;
    /**
     * Method that handles menu button in supply page.
     * Redirects to MenuApplication.
     */
    @FXML
    private void handleMenuButtonClick() throws IOException {
        viewModel.showMenuWindow();
    }

    @FXML
    private Button removeMaterialButton;
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
    @FXML
    private TableView displayTable;
    //@FXML
    //private Button addMaterialButton;
    /**
     * Method that handles add button in supply page.
     * Displays external window that has fields for adding a material.
     */
   /* @FXML
    private void handleAddMaterialButtonClick() throws IOException {
        AddSupplyApplication addSupplyApp = new AddSupplyApplication();
        Stage stage = new Stage();
        addSupplyApp.start(stage);
    }
*/
    @FXML
    private Button resupplyButton;
    /**
     * Method that handles add button in supply page.
     * Displays external window that has fields for adding a material.
     */
    @FXML
    private void handleResupplyMaterialButtonClick() throws IOException {
        ResupplyApplication resupplyApp = new ResupplyApplication();
        Stage stage = new Stage();
        resupplyApp.start(stage);
    }

    @FXML
    private Button viewAllButton;
    /**
     * Method that handles view all button in supply page.
     * Displays table in GUI.
     */
    @FXML
    private void handleViewAllButtonClick() throws IOException {

    }
}
