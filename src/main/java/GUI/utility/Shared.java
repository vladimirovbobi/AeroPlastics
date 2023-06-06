package GUI.utility;

import javafx.scene.control.Alert;

/**
 * The type Shared.
 */
public class Shared {

    /**
     * Error box.
     *
     * @param text the text
     */
    public void errorBox(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    /**
     * Success box.
     *
     * @param text the text
     */
    public void successBox(String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
