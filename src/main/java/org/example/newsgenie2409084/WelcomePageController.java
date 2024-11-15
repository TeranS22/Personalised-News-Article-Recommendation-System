package org.example.newsgenie2409084;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WelcomePageController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}