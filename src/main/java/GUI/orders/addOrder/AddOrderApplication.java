package GUI.orders.addOrder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The type Add order application.
 */
public class AddOrderApplication extends Application {

    @Override
    public void start(Stage addOrderStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addOrders.fxml"));
        Parent root = loader.load();
        AddOrderController controller = loader.getController();

        Scene scene = new Scene(root);
        addOrderStage.setScene(scene);
        addOrderStage.setTitle("Add Order");
        addOrderStage.show();

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
