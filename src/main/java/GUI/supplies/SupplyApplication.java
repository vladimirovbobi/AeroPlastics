package GUI.supplies;

import GUI.ViewModel;
import GUI.products.ProductApplication;
import GUI.products.ProductController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The type Supply application.
 */
public class SupplyApplication extends Application {

    @Override
    public void start(Stage supplyStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("supplies.fxml"));
        Parent root = loader.load();
        SupplyController controller = loader.getController();
        ViewModel viewModel = new ViewModel();
        controller.setViewModel(viewModel);

        controller.populateMaterialsTable();

        Scene scene = new Scene(root);
        supplyStage.setScene(scene);
        supplyStage.setTitle("AeroPlastics - Supplies");
        supplyStage.show();

        viewModel.setCurrentStage(supplyStage);
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