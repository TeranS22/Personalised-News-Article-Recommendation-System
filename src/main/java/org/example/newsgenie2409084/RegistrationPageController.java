package org.example.newsgenie2409084;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationPageController {

    public Button resetButton;
    public Button signUpButton;

    @FXML private TextField usernameField;

    @FXML private PasswordField passwordField;

    @FXML private PasswordField confirmPasswordField;

    @FXML private RadioButton healthRadio;
    @FXML private RadioButton technologyRadio;
    @FXML private RadioButton politicsRadio;
    @FXML private RadioButton businessRadio;
    @FXML private RadioButton scienceRadio;
    @FXML private RadioButton sportRadio;
    @FXML private RadioButton entertainmentRadio;
    @FXML private RadioButton environmentRadio;
    @FXML private RadioButton crimeRadio;
    @FXML private RadioButton educationRadio;
    @FXML private RadioButton weatherRadio;
    @FXML private RadioButton otherRadio;



    private static final MongoCollection<Document> usersCollection;

    static {
        MongoDatabase database = MongoClients.create("mongodb://localhost:27017")
                .getDatabase("newsGenie");
        usersCollection = database.getCollection("users");
    }

    @FXML
    private void handleSignUp(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if ("Admin".equals(username)) {
            showAlert("Error", "Username 'Admin' is reserved.");
        } else if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match.");
        } else if (isUsernameTaken(username)) {
            showAlert("Error", "Username is already taken.");
        } else {
            createUser(username, password);
            showAlert("Success", "User registered successfully.");
            SceneLoader.loadScene(event, "WelcomePage.fxml");
        }
    }

    private boolean isUsernameTaken(String username) {
        return usersCollection.find(Filters.eq("username", username)).first() != null;
    }

    private void createUser(String username, String password) {
        Document newUser = new Document("username", username)
                .append("password", password)
                .append("preferences", getUserPreferences());
        usersCollection.insertOne(newUser);
    }

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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(title.equals("Error") ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleResetRegistration(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "RegistrationPage.fxml");
    }

    public void backToSignIn(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "WelcomePage.fxml");
    }
}
