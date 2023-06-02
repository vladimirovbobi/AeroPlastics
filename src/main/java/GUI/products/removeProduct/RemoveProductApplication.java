package GUI.products.removeProduct;

import GUI.ViewModel;
import GUI.products.addProduct.AddProductController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RemoveProductApplication extends Application {

    @Override
    public void start(Stage removeProductStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("removeProducts.fxml"));
        Parent root = loader.load();
        RemoveProductController controller = loader.getController();
        ViewModel viewModel = new ViewModel();

        Scene scene = new Scene(root);
        removeProductStage.setScene(scene);
        removeProductStage.setTitle("Add Product");
        removeProductStage.show();
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
