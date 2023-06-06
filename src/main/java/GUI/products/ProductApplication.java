package GUI.products;

import GUI.ViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The type Product application.
 */
public class ProductApplication extends Application {
    @Override
    public void start(Stage productsStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("products.fxml"));
        Parent root = loader.load();
        ProductController controller = loader.getController();
        ViewModel viewModel = new ViewModel();
        controller.setViewModel(viewModel);
        controller.populateProductsTable();

        Scene scene = new Scene(root);
        productsStage.setScene(scene);
        productsStage.setTitle("AeroPlastics - Products");
        productsStage.show();

        viewModel.setCurrentStage(productsStage);
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