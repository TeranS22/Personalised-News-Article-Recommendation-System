package org.example.newsgenie2409084.Controller.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.example.newsgenie2409084.Util.SessionManager;
import org.example.newsgenie2409084.Util.SceneLoader;

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
        SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/Admin/ArticleFetcher.fxml");

    }

    public void directToManageArticles(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/Admin/ManageArticles.fxml");
    }

    public void directToManageAccounts(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/Admin/ManageUsers.fxml");
    }

    public void LogOut(ActionEvent event) throws IOException {
        SessionManager.clear();  // this is to clear session data
        SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/WelcomePage.fxml");

    }
}