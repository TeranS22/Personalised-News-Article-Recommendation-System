package org.example.newsgenie2409084.Controller.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.newsgenie2409084.Database.DatabaseArticles;
import org.example.newsgenie2409084.Model.Article;
import org.example.newsgenie2409084.Util.AlertUtils;
import org.example.newsgenie2409084.Util.SceneLoader;

import java.io.IOException;

public class ManageArticlesController {

    @FXML
    public Button BackToAdminMenu;

    @FXML
    public Button DeleteArticleButton;

    @FXML
    private TableView<Article> ManageArticlesTableView;

    @FXML
    private TableColumn<Article, Integer> ArticleID;
    @FXML
    private TableColumn<Article, String> ArticleHeadline;
    @FXML
    private TableColumn<Article, String> ArticlePreview;
    @FXML
    private TableColumn<Article, String> ArticleCategory;
    @FXML
    private TableColumn<Article, String> ArticleLink;
    @FXML
    private TableColumn<Article, Integer> ArticleThumbsUp;
    @FXML
    private TableColumn<Article, Integer> ArticleThumbsDown;

    @FXML
    private TextField confirmTextField;

    private final DatabaseArticles databaseArticle = new DatabaseArticles();
    private ObservableList<Article> articleList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        ArticleID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ArticleHeadline.setCellValueFactory(new PropertyValueFactory<>("name"));
        ArticlePreview.setCellValueFactory(new PropertyValueFactory<>("preview"));
        ArticleCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        ArticleLink.setCellValueFactory(new PropertyValueFactory<>("link"));

        loadArticles();
    }

    private void loadArticles() {
        articleList.clear();
        articleList.addAll(databaseArticle.getAllArticles());
        ManageArticlesTableView.setItems(articleList);
    }

    @FXML
    public void deleteArticle(ActionEvent event) {
        Article selectedArticle = ManageArticlesTableView.getSelectionModel().getSelectedItem();

        if (selectedArticle == null) {
            AlertUtils.showError("Error", "Please select an article to delete.");
            return;
        }

        String confirmation = confirmTextField.getText();
        if (!confirmation.equalsIgnoreCase("CONFIRM")) {
            AlertUtils.showError("Error", "Please type 'CONFIRM' to delete the article.");
            return;
        }

        databaseArticle.deleteArticle(selectedArticle.getId());
        articleList.remove(selectedArticle);
        AlertUtils.showSuccess("Success", "Article deleted successfully.");

        confirmTextField.clear();
    }

    @FXML
    public void backToAdminMenu(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/Admin/AdminMenu.fxml");
    }
}
