package GUI.login;

import GUI.JavaConnector;
import GUI.ViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Login controller.
 */
public class LoginController {

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;
    private JavaConnector javaConnector;
    private ViewModel viewModel;
    public void setViewModel(ViewModel viewModel){
        this.viewModel = viewModel;
    }

    public LoginController() {
        javaConnector = new JavaConnector();
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        // Check if the username and password are valid
        if (validateCredentials(username, password)) {
            viewModel.showMenuWindow();
        } else {
            showLoginFailedAlert();
        }
    }

    private boolean validateCredentials(String username, String password) {
        String query = "SELECT * FROM employee WHERE username = ? AND password = ?";
        try (Connection connection = javaConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showLoginFailedAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText(null);
        alert.setContentText("Invalid username or password.");
        alert.showAndWait();
    }
}