package org.example.newsgenie2409084.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.newsgenie2409084.Database.DatabaseUsers;
import org.example.newsgenie2409084.Model.User;
import org.example.newsgenie2409084.Service.ThreadPoolExecutorService;
import org.example.newsgenie2409084.Util.AlertUtils;
import org.example.newsgenie2409084.Util.SceneLoader;
import org.example.newsgenie2409084.Util.SessionManager;

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
    private void handleSignIn(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            AlertUtils.showError("Error", "Username or password cannot be empty.");
            return;
        }

        ThreadPoolExecutorService.getExecutorService().submit(() -> {
            boolean isAdmin = "Admin".equals(username) && "admin123".equals(password);
            boolean isValidUser = checkUserCredentials(username, password);

            if (isAdmin || isValidUser) {
                SessionManager.setUsername(username);

                Platform.runLater(() -> {
                    try {
                        if (isAdmin) {
                            SessionManager.setUsername(username);
                            SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/Admin/AdminMenu.fxml");
                        } else {
                            SessionManager.setUsername(username);
                            SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/User/UserMenu.fxml");
                        }
                    } catch (IOException e) {
                        AlertUtils.showError("Error", "Failed to load the scene.");
                        e.printStackTrace();
                    }
                });
            } else {
                Platform.runLater(() -> AlertUtils.showError("Error", "Invalid username or password."));
            }
        });
    }

    private boolean checkUserCredentials(String username, String password) {
        User user = databaseUser.getUserByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

    @FXML
    private void goToRegistrationPage(ActionEvent event) {
        try {
            SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/User/RegistrationPage.fxml");
        } catch (IOException e) {
            AlertUtils.showError("Error", "Failed to load the registration page.");
            e.printStackTrace();
        }
    }
}
