package GUI.products.addProduct;

import GUI.ViewModel;
import GUI.supplies.resupply.ResupplyController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AddProductApplication extends Application {

    @Override
    public void start(Stage addProductStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addProducts.fxml"));
        Parent root = loader.load();
        AddProductController controller = loader.getController();

        Scene scene = new Scene(root);
        addProductStage.setScene(scene);
        addProductStage.setTitle("Add Product");
        addProductStage.show();
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