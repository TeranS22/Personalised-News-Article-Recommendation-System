package org.example.newsgenie2409084.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.newsgenie2409084.Database.DatabaseUsers;
import org.example.newsgenie2409084.Model.User;
import org.example.newsgenie2409084.Util.AlertUtils;
import org.example.newsgenie2409084.Util.SceneLoader;

import java.io.IOException;

public class WelcomePageController {

    @FXML
    public Button SignInButton;

    @FXML
    public Button RegisterNowButtonInWelcome;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private final DatabaseUsers databaseUser = new DatabaseUsers();

    @FXML
    private void handleSignIn(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            AlertUtils.showError("Error", "Username or password cannot be empty.");
            return;
        }

        if ("Admin".equals(username) && "admin123".equals(password)) {
            SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/Admin/AdminMenu.fxml");
        } else if (checkUserCredentials(username, password)) {
            SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/User/UserMenu.fxml");
        } else {
            AlertUtils.showError("Error", "Invalid username or password.");
        }
    }

    private boolean checkUserCredentials(String username, String password) {
        User user = databaseUser.getUserByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

    @FXML
    private void goToRegistrationPage(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/User/RegistrationPage.fxml");
    }
}

