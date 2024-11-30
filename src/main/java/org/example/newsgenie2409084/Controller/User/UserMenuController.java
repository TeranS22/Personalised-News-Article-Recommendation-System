package org.example.newsgenie2409084.Controller.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.example.newsgenie2409084.Util.SceneLoader;

import java.io.IOException;

public class UserMenuController {


    @FXML
    public Button ViewArticles;

    @FXML
    public Button ViewHistory;

    @FXML
    public Button ManageAccount;

    @FXML
    public Button LogOut;


    public void directToViewArticles(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/User/ViewArticles.fxml");
    }

    public void directToViewHistory(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/User/ViewHistory.fxml");
    }

    public void directToManageAccount(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/User/ManageAccount.fxml");
    }

    public void LogOut(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/WelcomePage.fxml");
    }
}
