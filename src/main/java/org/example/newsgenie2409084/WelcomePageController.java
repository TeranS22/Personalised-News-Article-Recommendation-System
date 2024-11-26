package org.example.newsgenie2409084;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

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

    private final MongoCollection<Document> userCollection;

    public WelcomePageController() {
        MongoDatabase database = com.mongodb.client.MongoClients.create("mongodb://localhost:27017")
                .getDatabase("newsGenie");
        this.userCollection = database.getCollection("users");
    }

    @FXML
    private void handleSignIn(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username or password cannot be empty.");
            return;
        }

        if ("Admin".equals(username) && "admin123".equals(password)) {
            SceneLoader.loadScene(event, "AdminMenu.fxml");
        } else if (checkUserCredentials(username, password)) {
            SceneLoader.loadScene(event, "UserMenu.fxml");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private boolean checkUserCredentials(String username, String password) {
        User user = getUser(username);

        if (user == null) {
            System.out.println("User not found for username: " + username);
            return false;
        }

        return user.getPassword().equals(password);
    }

    private User getUser(String username) {
        Document doc = userCollection.find(Filters.eq("username", username)).first();
        return doc != null ? User.fromDocument(doc) : null;
    }

    @FXML
    private void goToRegistrationPage(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "RegistrationPage.fxml");
    }
}
