package GUI.login;

import GUI.ViewModel;
import GUI.menu.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The type Login application.
 */
public class LoginApplication extends Application {
    @Override
    public void start(Stage loginStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        LoginController controller = loader.getController();
        ViewModel viewModel = new ViewModel();
        controller.setViewModel(viewModel);

        Scene scene = new Scene(root);
        loginStage.setScene(scene);
        loginStage.setTitle("AeroPlastics - Login");
        loginStage.show();



        viewModel.setCurrentStage(loginStage);
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