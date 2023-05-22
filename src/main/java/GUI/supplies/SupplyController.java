package GUI.supplies;

import GUI.ViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.SQLException;

/**
 * The type Supply controller.
 */
public class SupplyController {

    public Button removeMaterialButton;
    public Button resupplyButton;
    public Button addMaterialButton;
    public Button viewAllButton;
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

    public void handleLogOutButtonClick(ActionEvent actionEvent) throws SQLException {
        Vendor vendor = new Vendor(122, "bob");
        vendor.addToDatabase();
    }

    public void handleAddMaterialButtonClick(ActionEvent actionEvent) {
    }

    public void handleResupplyButtonClick(ActionEvent actionEvent) {
    }

    public void handleRemoveMaterialButtonClick(ActionEvent actionEvent) {
    }
}
