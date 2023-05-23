package GUI.supplies.addSupply;

import GUI.supplies.removeSupply.RemoveSupplyController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The type Add supply application.
 */
public class AddSupplyApplication extends Application {

    @Override
    public void start(Stage addSupplyStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addSupplies.fxml"));
        Parent root = loader.load();
        AddSupplyController controller = loader.getController();

        Scene scene = new Scene(root);
        addSupplyStage.setScene(scene);
        addSupplyStage.setTitle("Add Supply");
        addSupplyStage.show();

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
