package GUI.supplies.resupply;

import GUI.ViewModel;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resupplyMain.fxml"));
        Parent root = loader.load();
        ResupplyController controller = loader.getController();
        ViewModel viewModel = new ViewModel();
        controller.setViewModel(viewModel);
        controller.initializeTable();
        controller.populateVendorsTable();

        Scene scene = new Scene(root);
        resupplyStage.setScene(scene);
        resupplyStage.setTitle("AeroPlastics - Order Supplies");
        resupplyStage.show();

        viewModel.setCurrentStage(resupplyStage);
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
