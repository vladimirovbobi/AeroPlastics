package GUI.supplies.resupply.resupplyPop;

import GUI.supplies.SupplyOrder;
import GUI.supplies.Vendor;
import GUI.supplies.resupply.Cart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.sql.SQLException;

/**
 * The type ResupplyPop controller.
 */
public class ResupplyPopController {

    /**
     * The Current spending field.
     */
    @FXML
    TextField  currentSpendingField;
    /**
     * The Total spending field.
     */
    @FXML
    TextField totalSpendingField;
    /**
     * The Material name text field.
     */
    @FXML
    TextField materialNameTextField;
    /**
     * The Vendor id text field.
     */
    @FXML
    TextField vendorIdTextField;
    /**
     * The Quantity field.
     */
    @FXML
    TextField quantityField;

    private Cart cart = Cart.getInstance();

    /**
     * Updates the current spending and updates the vendorID Text Field
     *
     * @throws SQLException MySQL
     */
    public void setCurrentSpendingText() throws SQLException {
        String materialName = materialNameTextField.getText();
        int vendorID;
        int quantity;

        try {
            vendorID = Integer.parseInt(vendorIdTextField.getText());
            quantity = Integer.parseInt(quantityField.getText());
        } catch (Exception ex) {
            vendorIdTextField.setText("Incorrect Material Information");
            return;
        }
        currentSpendingField.setText(Double.toString(Vendor.getPriceByVendorID(vendorID,materialName)*quantity));
    }

    /**
     * Updates the total amount and displays it in the Total Spending Field
     *
     * @throws SQLException the sql exception
     */
    public void setShoppingTotalText() throws SQLException {
        Cart.updateAmount();
        totalSpendingField.setText(Double.toString(cart.getAmount()));
    }

    /**
     * Add button is clicked
     *
     * @param actionEvent listens for a button click
     * @throws SQLException the sql exception
     */
    public void addButtonPressed(ActionEvent actionEvent) throws SQLException {
        try{
                String materialName = materialNameTextField.getText();
                int vendorID = Integer.parseInt(vendorIdTextField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                    if (Vendor.checkAvailability(materialName,quantity,vendorID)) {
                        cart.addToCart(vendorID, materialName, quantity);
                        setShoppingTotalText();
                        setCurrentSpendingText();
                    } else {
                        materialNameTextField.setText("Unavailable");
                    }
        }catch(NumberFormatException exception){
                return;
        }
    }

    /**
     * Clear button is clicked
     *
     * @param actionEvent listens for a button click
     */
    public void cancelButtonPressed(ActionEvent actionEvent) {
        vendorIdTextField.setText("");
        materialNameTextField.setText("");
        quantityField.setText("");
        totalSpendingField.setText("");
        currentSpendingField.setText("");
    }

    /**
     * Submit button is clicked
     *
     * @param actionEvent listens for a button click
     * @throws SQLException the sql exception
     */
    public void submitOrderButtonClicked(ActionEvent actionEvent) throws SQLException {
        String id = vendorIdTextField.getText();
        String quantity = quantityField.getText();
        cart = Cart.getInstance();
        if (!id.isEmpty() && !quantity.isEmpty()) {
            SupplyOrder.submitOrder();
            materialNameTextField.setText("Order Submitted");
            quantityField.setText("");
            vendorIdTextField.setText("");
            currentSpendingField.setText("");
        }else if ( cart.getAmount() > 0){
            SupplyOrder.submitOrder();
            materialNameTextField.setText("Order Submitted");
        }
    }
}
