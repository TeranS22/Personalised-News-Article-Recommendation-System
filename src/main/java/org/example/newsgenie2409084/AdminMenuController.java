package org.example.newsgenie2409084;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class AdminMenuController {

    @FXML
    public Button FetchArticles;

    @FXML
    public Button ManageArticles;

    @FXML
    public Button ManageAccounts;

    @FXML
    public Button LogOut;

    public void FetchArticles(ActionEvent event) throws IOException {
        // gotta put the fetching scenario here
    }

    public void directToManageArticles(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "ManageArticles.fxml");
    }

    public void directToManageAccounts(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "ManageAccounts.fxml");
    }

    public void LogOut(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "WelcomePage.fxml");

    }
}
