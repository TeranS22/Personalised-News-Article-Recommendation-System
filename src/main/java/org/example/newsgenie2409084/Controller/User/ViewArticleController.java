package org.example.newsgenie2409084.Controller.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.controlsfx.control.Rating;
import org.example.newsgenie2409084.Database.DatabaseArticles;
import org.example.newsgenie2409084.Database.DatabaseUsers;
import org.example.newsgenie2409084.Model.Article;
import org.example.newsgenie2409084.Util.SessionManager;
import org.example.newsgenie2409084.Util.SceneLoader;

import java.io.IOException;

public class ViewArticleController {

    @FXML
    private WebView ArticleWebView;
    @FXML
    private Rating UserRating;
    @FXML
    private Button BackToAllArticles;

    // Current article being viewed
    private static Article currentArticle;
    private Integer finalRating = 0;

    private final DatabaseUsers databaseUsers = new DatabaseUsers();
    private final DatabaseArticles databaseArticles = new DatabaseArticles();

    @FXML
    public void initialize() {
        if (currentArticle != null) {
            WebEngine webEngine = ArticleWebView.getEngine();
            webEngine.load(currentArticle.getLink());
        }
        BackToAllArticles.setDisable(true);
    }

    @FXML
    public void ChangeRating() {
        finalRating = (int) UserRating.getRating();

        BackToAllArticles.setDisable(finalRating == 0);
    }

    public static void loadArticle(Article article) {
        currentArticle = article;
    }

    private void saveRating() {
        String username = SessionManager.getUsername();
        if (username != null && finalRating > 0) {
            databaseUsers.updateUserReadHistory(
                username,
                currentArticle.getId(),
                currentArticle.getName(),
                currentArticle.getPreview(),
                finalRating,
                currentArticle.getCategory()
            );

            double currentAverageRating = currentArticle.getAverageRating();
            int currentRatingCount = currentArticle.getRatingCount();

            double newAverageRating = ((currentAverageRating * currentRatingCount) + finalRating) / (currentRatingCount + 1);

            databaseArticles.updateArticleRating(currentArticle.getId(), newAverageRating, currentRatingCount + 1);
        }
    }

    public void backToAllArticles(ActionEvent event) throws IOException {
        saveRating();
        SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/User/ViewAllArticles.fxml");
    }
}
