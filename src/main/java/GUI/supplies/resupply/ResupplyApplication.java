package GUI.supplies.resupply;

import GUI.supplies.removeSupply.RemoveSupplyController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The type Resupply application.
 */
public class ResupplyApplication extends Application {

    @Override
    public void start(Stage resupplyStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resupply.fxml"));
        Parent root = loader.load();
        ResupplyController controller = loader.getController();

        Scene scene = new Scene(root);
        resupplyStage.setScene(scene);
        resupplyStage.setTitle("Resupply");
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
