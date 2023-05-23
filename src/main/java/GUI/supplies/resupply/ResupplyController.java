package GUI.supplies.resupply;

import GUI.JavaConnector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

/**
 * The type Resupply controller.
 */
public class ResupplyController {

    @FXML
    TextField materialTextField;
    @FXML
    TextField materialIdTextField;
    @FXML
    TextField quantityField;

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
}
