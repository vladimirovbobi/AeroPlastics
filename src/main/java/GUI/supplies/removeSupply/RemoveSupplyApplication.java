package GUI.supplies.removeSupply;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The type Remove supply application.
 */
public class RemoveSupplyApplication extends Application {

    @Override
    public void start(Stage removeSupplyStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("removeSupplies.fxml"));
        Parent root = loader.load();
        RemoveSupplyController controller = loader.getController();

        Scene scene = new Scene(root);
        removeSupplyStage.setScene(scene);
        removeSupplyStage.setTitle("Remove Supply");
        removeSupplyStage.show();

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
