package GUI.orders;

import GUI.ViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The type Orders application.
 */
public class OrdersApplication extends Application {
    @Override
    public void start(Stage ordersStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("orders.fxml"));
        Parent root = loader.load();
        OrdersController controller = loader.getController();
        ViewModel viewModel = new ViewModel();
        controller.setViewModel(viewModel);
        controller.populateOrdersTable();
        controller.performInventoryCheck();

        Scene scene = new Scene(root);
        ordersStage.setScene(scene);
        ordersStage.setTitle("AeroPlastics - Orders");
        ordersStage.show();

        viewModel.setCurrentStage(ordersStage);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch();
    }
}
