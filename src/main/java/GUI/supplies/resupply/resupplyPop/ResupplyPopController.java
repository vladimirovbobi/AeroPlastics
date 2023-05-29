package GUI.supplies.resupply.resupplyPop;

import GUI.JavaConnector;
import GUI.supplies.SupplyOrder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

/**
 * The type ResupplyPop controller.
 */
public class ResupplyPopController {
    @FXML
    TextField  currentSpendingField;
    @FXML
    TextField totalSpendingField;
    @FXML
    TextField materialNameTextField;
    @FXML
    TextField vendorIdTextField;
    @FXML
    TextField quantityField;

    public void setCurrentSpendingText(){

        currentSpendingField.setText("1000");
    }

    public void addButtonPressed(ActionEvent actionEvent) {
        String materialName = materialNameTextField.getText();
        int materialid = 0;
        int quantity = 0;
            try {
                materialid = Integer.parseInt(vendorIdTextField.getText());
                quantity = Integer.parseInt(quantityField.getText());
            } catch (Exception ex) {
                vendorIdTextField.setText("Incorrect Material Information");
                return;
            }
        SupplyOrder supplyOrder = new SupplyOrder();
            supplyOrder.makeSupplyOrder(materialName,materialid,quantity);
    }


    public void cancelButoonPressed(ActionEvent actionEvent) {
        vendorIdTextField.setText("");
        materialNameTextField.setText("");
        quantityField.setText("");
    }

    public void checkSpendingButtonClicked(ActionEvent actionEvent) {

    }
}
