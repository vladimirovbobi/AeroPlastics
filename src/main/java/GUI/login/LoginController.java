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

    /**
     * Set view model.
     *
     * @param viewModel the view model
     */
    public void setViewModel(ViewModel viewModel){
        this.viewModel = viewModel;
    }

    /**
     * Instantiates a new Login controller.
     */
    public LoginController() {
        javaConnector = new JavaConnector();
    }

    /**
     * Handles login button.
     * Shows menu if login is valid. Otherwise, shows login has failed.
     * @param event
     * @throws IOException
     */
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

    /**
     * Validates the username and password.
     *
     * @param username
     * @param password
     * @return
     */
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

    /**
     * Shows alert window when login credentials are invalid.
     */
    private void showLoginFailedAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText(null);
        alert.setContentText("Invalid username or password.");
        alert.showAndWait();
    }

    /**
     * Close the application when click cancel button
     * @param event the event
     */
    @FXML
    private void handleCancelButtonClick(ActionEvent event){
        viewModel.closeCurrentStage();
    }
}