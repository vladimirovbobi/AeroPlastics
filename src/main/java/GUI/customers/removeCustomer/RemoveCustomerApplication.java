package GUI.customers.removeCustomer;

import GUI.customers.addCustomer.AddCustomerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The type Remove customer application.
 */
public class RemoveCustomerApplication extends Application {

    @Override
    public void start(Stage removeCustomerStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("removeCustomers.fxml"));
        Parent root = loader.load();
        RemoveCustomerController controller = loader.getController();

        Scene scene = new Scene(root);
        removeCustomerStage.setScene(scene);
        removeCustomerStage.setTitle("Remove Customer");
        removeCustomerStage.show();

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
