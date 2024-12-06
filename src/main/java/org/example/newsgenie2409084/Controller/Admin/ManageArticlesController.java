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
    private TableColumn<Article, Double> ArticleRating;

    @FXML
    private TextField confirmTextField;

    // Database handler and observable list for articles
    private final DatabaseArticles databaseArticle = new DatabaseArticles();
    private ObservableList<Article> articleList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set table columns to Article model properties
        ArticleID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ArticleHeadline.setCellValueFactory(new PropertyValueFactory<>("name"));
        ArticlePreview.setCellValueFactory(new PropertyValueFactory<>("preview"));
        ArticleCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        ArticleLink.setCellValueFactory(new PropertyValueFactory<>("link"));
        ArticleRating.setCellValueFactory(new PropertyValueFactory<>("averageRating"));

        // Load articles into the table
        loadArticles();
    }

    private void loadArticles() {
        articleList.clear();
        articleList.addAll(databaseArticle.getAllArticles());
        ManageArticlesTableView.setItems(articleList);
    }

    @FXML
    public void deleteArticle(ActionEvent event) {
        // Get the selected article from the table
        Article selectedArticle = ManageArticlesTableView.getSelectionModel().getSelectedItem();

        // Check if an article is selected
        if (selectedArticle == null) {
            AlertUtils.showError("Error", "Please select an article to delete.");
            return;
        }

        // Validate the confirmation text field
        String confirmation = confirmTextField.getText();
        if (!confirmation.equalsIgnoreCase("CONFIRM")) {
            AlertUtils.showError("Error", "Please type 'CONFIRM' to delete the article.");
            return;
        }

        // Delete the article from the database and update the table
        databaseArticle.deleteArticle(selectedArticle.getId());
        articleList.remove(selectedArticle); // From list
        AlertUtils.showSuccess("Success", "Article deleted successfully.");

        // Clear the confirmation text field
        confirmTextField.clear();
    }

    @FXML
    public void backToAdminMenu(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/Admin/AdminMenu.fxml");
    }
}