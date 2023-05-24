package GUI.supplies.removeSupply;

import GUI.customers.addCustomer.AddCustomerApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The type Remove supply controller.
 */
public class RemoveSupplyController {
    @FXML
    TextField orderIdTextField;
    @FXML
    TextField materialIdTextField;
    public void removeSupplyOrderButtonClicked(ActionEvent actionEvent) {

    }

    public void cancelSupplyOrderButtonClicked(ActionEvent actionEvent) {
        orderIdTextField.setText("");
        materialIdTextField.setText("");
    }
}
