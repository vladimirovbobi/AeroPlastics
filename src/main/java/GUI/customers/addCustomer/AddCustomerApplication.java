package GUI.customers.addCustomer;

import GUI.ViewModel;
import GUI.customers.CustomerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCustomerApplication extends Application {

    @Override
    public void start(Stage addCustomerStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addCustomers.fxml"));
        Parent root = loader.load();
        AddCustomerController controller = loader.getController();

        Scene scene = new Scene(root);
        addCustomerStage.setScene(scene);
        addCustomerStage.setTitle("Add Customer");
        addCustomerStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}
