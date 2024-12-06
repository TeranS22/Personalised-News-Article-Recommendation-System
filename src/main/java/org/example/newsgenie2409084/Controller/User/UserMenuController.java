package org.example.newsgenie2409084.Controller.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.example.newsgenie2409084.Util.SessionManager;
import org.example.newsgenie2409084.Util.SceneLoader;

import java.io.IOException;

public class UserMenuController {


    @FXML
    public Button RecommendedArticles;

    @FXML
    public Button AllArticles;

    @FXML
    public Button ReadHistory;

    @FXML
    public Button ManageAccount;

    @FXML
    public Button LogOut;


    public void directToRecommendedArticles(ActionEvent actionEvent) throws IOException {
        SceneLoader.loadScene(actionEvent, "/org/example/newsgenie2409084/View/User/RecommendedArticles.fxml");
    }

    public void directToAllArticles(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/User/ViewAllArticles.fxml");
    }

    public void directToReadHistory(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/User/ViewHistory.fxml");
    }

    public void directToManageAccount(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/User/ManageAccount.fxml");
    }

    public void LogOut(ActionEvent event) throws IOException {
        SessionManager.clear(); // Clear the user's session
        SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/WelcomePage.fxml");
    }
}
