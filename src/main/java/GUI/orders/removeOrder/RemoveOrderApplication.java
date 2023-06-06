package GUI.orders.removeOrder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The type Remove order application.
 */
public class RemoveOrderApplication extends Application {

    @Override
    public void start(Stage removeOrderStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("removeOrders.fxml"));
        Parent root = loader.load();
        RemoveOrderController controller = loader.getController();

        Scene scene = new Scene(root);
        removeOrderStage.setScene(scene);
        removeOrderStage.setTitle("Remove Order");
        removeOrderStage.show();

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
