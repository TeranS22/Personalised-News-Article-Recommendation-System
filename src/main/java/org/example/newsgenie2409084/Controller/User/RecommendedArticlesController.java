package org.example.newsgenie2409084.Controller.User;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.controlsfx.control.Rating;
import org.example.newsgenie2409084.Model.Article;
import org.example.newsgenie2409084.Service.RecommendArticles;
import org.example.newsgenie2409084.Util.SceneLoader;
import org.example.newsgenie2409084.Util.SessionManager;
import org.example.newsgenie2409084.Database.DatabaseUsers;
import org.example.newsgenie2409084.Database.DatabaseArticles;
import org.example.newsgenie2409084.Util.AlertUtils;

import java.io.IOException;

public class RecommendedArticlesController {

    @FXML
    private TextArea ArticleHeading;

    @FXML
    private TextField ArticleCategory;

    @FXML
    private WebView ArticleWebView;

    @FXML
    private Rating UserRating;

    @FXML
    private Button SkipArticle;

    @FXML
    private Button NextArticle;

    @FXML
    private Button BackToUserMenu;

    private static Article currentArticle;

    private final RecommendArticles recommendArticles = new RecommendArticles();
    private final DatabaseUsers databaseUsers = new DatabaseUsers();
    private final DatabaseArticles databaseArticles = new DatabaseArticles();

    @FXML
    public void initialize() {
        String username = SessionManager.getUsername();

        if (username == null || username.isEmpty()) {
            showError("Error: User not logged in.");
            return;
        }

        Platform.runLater(this::loadNextArticle);
    }

    private void loadNextArticle() {
        String username = SessionManager.getUsername();

        if (username == null) {
            showError("Error: No user logged in.");
            return;
        }

        currentArticle = recommendArticles.getNextRecommendedArticle(username);

        if (currentArticle != null) {
            ArticleHeading.setText(currentArticle.getName());
            ArticleCategory.setText(currentArticle.getCategory());

            WebEngine webEngine = ArticleWebView.getEngine();
            webEngine.load(currentArticle.getLink());

            NextArticle.setDisable(true);
            SkipArticle.setDisable(false);
            UserRating.setRating(0);
        } else {
            ArticleHeading.setText("No articles available.");
            ArticleCategory.setText("");
            ArticleWebView.getEngine().loadContent("<html><body><h1>No Content</h1></body></html>");
            NextArticle.setDisable(true);
            SkipArticle.setDisable(true);
        }
    }

    private void showError(String message) {
        AlertUtils.showError("Error", message);
        ArticleHeading.setText(message);
        ArticleCategory.setText("");
        ArticleWebView.getEngine().loadContent("<html><body><h1>No Content</h1></body></html>");
    }

    @FXML
    public void skipArticle() {
        if (currentArticle != null) {
            String username = SessionManager.getUsername();
            if (username != null) {
                databaseUsers.addToSkippedArticles(username, currentArticle.getId());
            }
            UserRating.setRating(0);
            loadNextArticle();
        }
    }

    @FXML
    public void ChangeRating() {
        NextArticle.setDisable(UserRating.getRating() == 0);
    }

    @FXML
    public void nextArticle() {
        if (currentArticle != null) {
            int userRating = (int) UserRating.getRating();
            String username = SessionManager.getUsername();
            if (username != null) {
                databaseUsers.updateUserReadHistory(
                    username,
                    currentArticle.getId(),
                    currentArticle.getName(),
                    currentArticle.getPreview(),
                    userRating,
                    currentArticle.getCategory()
                );

                double currentAverageRating = currentArticle.getAverageRating();
                int currentRatingCount = currentArticle.getRatingCount();
                double newAverageRating = ((currentAverageRating * currentRatingCount) + userRating) / (currentRatingCount + 1);

                databaseArticles.updateArticleRating(currentArticle.getId(), newAverageRating, currentRatingCount + 1);

                UserRating.setRating(0);
                loadNextArticle();
            }
        }
    }

    @FXML
    public void backToUserMenu(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/User/UserMenu.fxml");
    }
}