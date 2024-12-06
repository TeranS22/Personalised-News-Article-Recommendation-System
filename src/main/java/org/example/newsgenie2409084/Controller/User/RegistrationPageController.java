package org.example.newsgenie2409084.Controller.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.newsgenie2409084.Database.DatabaseUsers;
import org.example.newsgenie2409084.Model.User;
import org.example.newsgenie2409084.Util.AlertUtils;
import org.example.newsgenie2409084.Util.SceneLoader;

import java.io.IOException;
import java.util.*;

public class RegistrationPageController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private RadioButton healthRadio, technologyRadio, politicsRadio, businessRadio,
            scienceRadio, sportRadio, entertainmentRadio, environmentRadio,
            crimeRadio, educationRadio, weatherRadio, otherRadio;

    // Database instance for user operations
    private final DatabaseUsers databaseUsers = new DatabaseUsers();

    @FXML
    private void handleSignUp(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validate input fields
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            AlertUtils.showError("Error", "All fields must be filled.");
            return;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            AlertUtils.showError("Error", "Passwords do not match.");
            return;
        }

        // Check if username is already taken
        if (databaseUsers.getUserByUsername(username) != null) {
            AlertUtils.showError("Error", "Username is already taken.");
            return;
        }

        // Initialize and populate preferred categories
        Map<String, Integer> preferredCategories = initializePreferredCategories();

        // Get selected preferences
        List<String> preferences = getUserPreferences();
        for (String category : preferences) {
            preferredCategories.put(category, 10);
        }

        // Create and save the new user
        User user = new User(username, password, preferences, preferredCategories, null);
        databaseUsers.saveUser(user);

        AlertUtils.showSuccess("Success", "Registration successful!");
        SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/WelcomePage.fxml");
    }

    // Gets the selected preferences from the radio buttons
    private List<String> getUserPreferences() {
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
        return preferences;
    }

    // Initializes the preferred categories with default scores.
    private Map<String, Integer> initializePreferredCategories() {
        Map<String, Integer> preferredCategories = new HashMap<>();
        String[] categories = {"Health", "Technology", "Politics", "Business", "Science", "Sport",
                "Entertainment", "Environment", "Crime", "Education", "Weather", "Other"};
        for (String category : categories) {
            preferredCategories.put(category, 0);
        }
        return preferredCategories;
    }


    @FXML
    private void handleResetRegistration(ActionEvent event) {
        // Resets all fields and radio button selections on the registration form.
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
