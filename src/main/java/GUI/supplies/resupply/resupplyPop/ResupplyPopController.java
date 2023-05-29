package GUI.supplies.resupply.resupplyPop;

import GUI.Date;
import GUI.JavaConnector;
import GUI.supplies.Material;
import GUI.supplies.SupplyOrder;
import GUI.supplies.Vendor;
import GUI.supplies.resupply.Cart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

/**
 * The type ResupplyPop controller.
 */
public class ResupplyPopController {
    static boolean firstTime = false;
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

    private Cart cart;

    public void setCurrentSpendingText() throws SQLException {
        String materialName = materialNameTextField.getText();
        int vendorID = 0;
        int quantity = 0;

        try {
            vendorID = Integer.parseInt(vendorIdTextField.getText());
            quantity = Integer.parseInt(quantityField.getText());
        } catch (Exception ex) {
            vendorIdTextField.setText("Incorrect Material Information");
            return;
        }
        currentSpendingField.setText(Double.toString(Vendor.getPriceByVendorID(vendorID,materialName)*quantity));
    }
    public void setShoppingTotalText(){
        totalSpendingField.setText(Double.toString(cart.getAmount()));
    }

    public void addButtonPressed(ActionEvent actionEvent) throws SQLException {
        String materialName = materialNameTextField.getText();
        int vendorID = 0;
        int quantity = 0;

            try {
                vendorID = Integer.parseInt(vendorIdTextField.getText());
                quantity = Integer.parseInt(quantityField.getText());
            } catch (Exception ex) {
                vendorIdTextField.setText("Incorrect Material Information");
                return;
            }
            if(firstTime) {
                cart = cart.createCart();
                firstTime = false;
            }
            cart.addToCart(vendorID,materialName,quantity);
            setShoppingTotalText();
            setCurrentSpendingText();
    }

    public static void setFirstTime(boolean b) {
        firstTime = b;
    }


    public void cancelButtonPressed(ActionEvent actionEvent) {
        vendorIdTextField.setText("");
        materialNameTextField.setText("");
        quantityField.setText("");
    }

    public void checkSpendingButtonClicked(ActionEvent actionEvent) throws SQLException {
        setShoppingTotalText();
        setCurrentSpendingText();
    }

    public void submitOrderButtonClicked(ActionEvent actionEvent) {

        SupplyOrder supplyOrder = new SupplyOrder();
       //supplyOrder.makeSupplyOrder(materialName,materialid,quantity);
    }
}
