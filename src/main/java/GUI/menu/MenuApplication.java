package GUI.menu;

import GUI.ViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The type Menu application.
 *
 */
public class MenuApplication extends Application {
    @Override
    public void start(Stage menuStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        Parent root = loader.load();
        MenuController controller = loader.getController();
        ViewModel viewModel = new ViewModel();
        controller.setViewModel(viewModel);

        Scene scene = new Scene(root);
        menuStage.setScene(scene);
        menuStage.setTitle("AeroPlastics - Menu");
        menuStage.show();



        viewModel.setCurrentStage(menuStage);
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
