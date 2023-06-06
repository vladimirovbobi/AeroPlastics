package GUI.customers;

import GUI.ViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The type Customer application.
 */
public class CustomerApplication extends Application {
    @Override
    public void start(Stage customerStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("customers.fxml"));
        Parent root = loader.load();
        CustomerController controller = loader.getController();
        ViewModel viewModel = new ViewModel();
        controller.setViewModel(viewModel);
        controller.populateCustomersTable();

        Scene scene = new Scene(root);
        customerStage.setScene(scene);
        customerStage.setTitle("AeroPlastics - Customers");
        customerStage.show();

        viewModel.setCurrentStage(customerStage);
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
