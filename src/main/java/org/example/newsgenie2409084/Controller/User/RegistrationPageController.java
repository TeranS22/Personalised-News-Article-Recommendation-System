package org.example.newsgenie2409084.Controller.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.newsgenie2409084.Database.DatabaseUsers;
import org.example.newsgenie2409084.Model.User;
import org.example.newsgenie2409084.Util.AlertUtils;
import org.example.newsgenie2409084.Util.SceneLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationPageController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private RadioButton healthRadio;
    @FXML
    private RadioButton technologyRadio;
    @FXML
    private RadioButton politicsRadio;
    @FXML
    private RadioButton businessRadio;
    @FXML
    private RadioButton scienceRadio;
    @FXML
    private RadioButton sportRadio;
    @FXML
    private RadioButton entertainmentRadio;
    @FXML
    private RadioButton environmentRadio;
    @FXML
    private RadioButton crimeRadio;
    @FXML
    private RadioButton educationRadio;
    @FXML
    private RadioButton weatherRadio;
    @FXML
    private RadioButton otherRadio;

    private final DatabaseUsers databaseUser = new DatabaseUsers();

    @FXML
    private void handleSignUp(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            AlertUtils.showError("Error", "All fields must be filled.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            AlertUtils.showError("Error", "Passwords do not match.");
            return;
        }

        if (databaseUser.getUserByUsername(username) != null) {
            AlertUtils.showError("Error", "Username is already taken.");
            return;
        }

        String preferences = getUserPreferences();
        User user = new User(username, password, preferences, null);
        databaseUser.saveUser(user);

        AlertUtils.showSuccess("Success", "Registration successful!");
        SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/WelcomePage.fxml");
    }

    private String getUserPreferences() {
        List<String> preferences = new ArrayList<>();
        if (healthRadio.isSelected()) preferences.add("Health");
        if (technologyRadio.isSelected()) preferences.add("Technology");
        if (politicsRadio.isSelected()) preferences.add("Politics");
        if (businessRadio.isSelected()) preferences.add("Business");
        if (scienceRadio.isSelected()) preferences.add("Science");
        if (sportRadio.isSelected()) preferences.add("Sport");
        if (entertainmentRadio.isSelected()) preferences.add("Entertainment");
        if (environmentRadio.isSelected()) preferences.add("Environment");
        if (crimeRadio.isSelected()) preferences.add("Crime");
        if (educationRadio.isSelected()) preferences.add("Education");
        if (weatherRadio.isSelected()) preferences.add("Weather");
        if (otherRadio.isSelected()) preferences.add("Other");
        return String.join(", ", preferences);
    }

    @FXML
    private void handleResetRegistration(ActionEvent event) {

        usernameField.clear();
        passwordField.clear();
        confirmPasswordField.clear();

        healthRadio.setSelected(false);
        technologyRadio.setSelected(false);
        politicsRadio.setSelected(false);
        businessRadio.setSelected(false);
        scienceRadio.setSelected(false);
        sportRadio.setSelected(false);
        entertainmentRadio.setSelected(false);
        environmentRadio.setSelected(false);
        crimeRadio.setSelected(false);
        educationRadio.setSelected(false);
        weatherRadio.setSelected(false);
        otherRadio.setSelected(false);

        AlertUtils.showSuccess("Reset", "All fields have been cleared.");
    }

    @FXML
    private void backToSignIn(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/WelcomePage.fxml");
    }
}
