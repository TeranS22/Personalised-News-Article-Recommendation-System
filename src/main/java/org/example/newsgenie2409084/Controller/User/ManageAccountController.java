package org.example.newsgenie2409084.Controller.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.bson.Document;
import org.example.newsgenie2409084.Database.DatabaseUsers;
import org.example.newsgenie2409084.Util.AlertUtils;
import org.example.newsgenie2409084.Util.SessionManager;
import org.example.newsgenie2409084.Util.SceneLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManageAccountController {

    @FXML
    private TextField currentUsername;

    @FXML
    private TextField newUsername;

    @FXML
    private PasswordField newPassword;

    @FXML
    private PasswordField newConfirmPassword;

    @FXML
    private RadioButton healthRadio, technologyRadio, politicsRadio, businessRadio, scienceRadio,
            sportRadio, entertainmentRadio, environmentRadio, crimeRadio, educationRadio, weatherRadio, otherRadio;

    @FXML
    public Button saveChangesButton;

    @FXML
    public Button BackToUserMenu;

    private final DatabaseUsers databaseUsers = new DatabaseUsers();

    private Map<String, Integer> preferredCategories;
    private List<String> preferences;

    @FXML
    public void initialize() {
        String username = SessionManager.getUsername();
        if (username != null) {
            currentUsername.setText(username);

            Document userDoc = databaseUsers.getUserDocumentByUsername(username);
            if (userDoc != null) {
                preferences = userDoc.getList("preferences", String.class);
                preferredCategories = userDoc.get("preferredCategories", Map.class);

                setRadioButtons(preferences);
            }
        }
    }

    private void setRadioButtons(List<String> preferences) {
        healthRadio.setSelected(preferences.contains("Health"));
        technologyRadio.setSelected(preferences.contains("Technology"));
        politicsRadio.setSelected(preferences.contains("Politics"));
        businessRadio.setSelected(preferences.contains("Business"));
        scienceRadio.setSelected(preferences.contains("Science"));
        sportRadio.setSelected(preferences.contains("Sports"));
        entertainmentRadio.setSelected(preferences.contains("Entertainment"));
        environmentRadio.setSelected(preferences.contains("Environment"));
        crimeRadio.setSelected(preferences.contains("Crime"));
        educationRadio.setSelected(preferences.contains("Education"));
        weatherRadio.setSelected(preferences.contains("Weather"));
        otherRadio.setSelected(preferences.contains("Other"));
    }

    @FXML
    public void handleChanges(ActionEvent event) {
        try {
            String updatedUsername = newUsername.getText().trim();
            String updatedPassword = newPassword.getText().trim();
            String confirmPassword = newConfirmPassword.getText().trim();

            List<String> updatedPreferences = new ArrayList<>();
            boolean hasUpdates = false;

            if (!updatedUsername.isEmpty() && !updatedUsername.equals(SessionManager.getUsername())) {
                if (databaseUsers.getUserByUsername(updatedUsername) != null) {
                    AlertUtils.showError("Error", "Username is already taken.");
                    return;
                }
                hasUpdates = true;
            }

            if (!updatedPassword.isEmpty() || !confirmPassword.isEmpty()) {
                if (!updatedPassword.equals(confirmPassword)) {
                    AlertUtils.showError("Error", "Passwords do not match.");
                    return;
                }
                hasUpdates = true;
            }

            if (validatePreferences(updatedPreferences)) {
                hasUpdates = true;
            }

            if (!hasUpdates) {
                AlertUtils.showError("Error", "No changes detected to save.");
                return;
            }

            if (!updatedUsername.isEmpty()) {
                databaseUsers.updateUsername(SessionManager.getUsername(), updatedUsername);
                SessionManager.setUsername(updatedUsername);
            }
            if (!updatedPassword.isEmpty()) {
                databaseUsers.updatePassword(SessionManager.getUsername(), updatedPassword);
            }
            if (!updatedPreferences.isEmpty()) {
                databaseUsers.updatePreferences(SessionManager.getUsername(), updatedPreferences, preferredCategories);
            }

            AlertUtils.showSuccess("Success", "Your account has been updated successfully!");
            SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/User/ManageAccount.fxml");

        } catch (Exception e) {
            AlertUtils.showError("Error", "An unexpected error occurred: " + e.getMessage());
        }
    }

    private boolean validatePreferences(List<String> updatedPreferences) {
        boolean hasChanges = false;

        hasChanges |= checkAndUpdatePreferences(healthRadio, "Health", updatedPreferences);
        hasChanges |= checkAndUpdatePreferences(technologyRadio, "Technology", updatedPreferences);
        hasChanges |= checkAndUpdatePreferences(politicsRadio, "Politics", updatedPreferences);
        hasChanges |= checkAndUpdatePreferences(businessRadio, "Business", updatedPreferences);
        hasChanges |= checkAndUpdatePreferences(scienceRadio, "Science", updatedPreferences);
        hasChanges |= checkAndUpdatePreferences(sportRadio, "Sports", updatedPreferences);
        hasChanges |= checkAndUpdatePreferences(entertainmentRadio, "Entertainment", updatedPreferences);
        hasChanges |= checkAndUpdatePreferences(environmentRadio, "Environment", updatedPreferences);
        hasChanges |= checkAndUpdatePreferences(crimeRadio, "Crime", updatedPreferences);
        hasChanges |= checkAndUpdatePreferences(educationRadio, "Education", updatedPreferences);
        hasChanges |= checkAndUpdatePreferences(weatherRadio, "Weather", updatedPreferences);
        hasChanges |= checkAndUpdatePreferences(otherRadio, "Other", updatedPreferences);

        return hasChanges;
    }

    private boolean checkAndUpdatePreferences(RadioButton radioButton, String category, List<String> updatedPreferences) {
        boolean changed = false;
        if (radioButton.isSelected() && !preferences.contains(category)) {
            updatedPreferences.add(category);
            preferredCategories.put(category, preferredCategories.getOrDefault(category, 0) + 10);
            changed = true;
        } else if (!radioButton.isSelected() && preferences.contains(category)) {
            preferences.remove(category);
            preferredCategories.put(category, preferredCategories.getOrDefault(category, 0) - 10);
            changed = true;
        } else if (preferences.contains(category)) {
            updatedPreferences.add(category);
        }
        return changed;
    }

    @FXML
    public void backToUserMenu(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/User/UserMenu.fxml");
    }
}
