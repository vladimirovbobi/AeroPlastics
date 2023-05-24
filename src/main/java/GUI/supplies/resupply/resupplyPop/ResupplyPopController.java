package GUI.supplies.resupply.resupplyPop;

import GUI.JavaConnector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

/**
 * The type ResupplyPop controller.
 */
public class ResupplyPopController {
    @FXML
    TextField materialTextField;
    @FXML
    TextField materialIdTextField;
    @FXML
    TextField quantityField;
}
