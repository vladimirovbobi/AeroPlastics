package GUI.supplies.resupply.resupplyPop;

import GUI.supplies.resupply.ResupplyController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;


/**
 * The type Resupply pop application.
 */
public class ResupplyPopApplication {
    /**
     * Start.
     *
     * @param resupplyStage the resupply stage
     * @throws IOException the io exception
     */
    public void start(Stage resupplyStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resupply.fxml"));
        Parent root = loader.load();
        ResupplyPopController controller = loader.getController();

        Scene scene = new Scene(root);
        resupplyStage.setScene(scene);
        resupplyStage.setTitle("Order Material");
        resupplyStage.show();

    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
