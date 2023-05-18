package GUI.supplies.removeSupply;

import GUI.customers.addCustomer.AddCustomerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
    public static void main(String[] args) {
        launch(args);
    }
}
