package org.example.newsgenie2409084;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bson.Document;

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

    private static final MongoCollection<Document> articlesCollection;

    static {
        MongoDatabase database = MongoClients.create("mongodb://localhost:27017").getDatabase("newsGenie");
        articlesCollection = database.getCollection("articles");
    }

    private ObservableList<Article> articleList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        ArticleID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ArticleHeadline.setCellValueFactory(new PropertyValueFactory<>("name"));
        ArticlePreview.setCellValueFactory(new PropertyValueFactory<>("preview"));
        ArticleCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        ArticleLink.setCellValueFactory(new PropertyValueFactory<>("link"));
        ArticleThumbsUp.setCellValueFactory(new PropertyValueFactory<>("thumbsUp"));
        ArticleThumbsDown.setCellValueFactory(new PropertyValueFactory<>("thumbsDown"));

        loadArticles();
    }

    private void loadArticles() {
        articleList.clear();

        for (Document doc : articlesCollection.find()) {
            Article article = new Article(
                    doc.getInteger("id"),
                    doc.getString("name"),
                    doc.getString("preview"),
                    doc.getString("category"),
                    doc.getString("link")
            );
            articleList.add(article);
        }

        ManageArticlesTableView.setItems(articleList); // Bind list to TableView
    }

    @FXML
    public void backToAdminMenu(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "AdminMenu.fxml");
    }

    @FXML
    public void deleteArticle(ActionEvent event) {
        Article selectedArticle = ManageArticlesTableView.getSelectionModel().getSelectedItem();

        if (selectedArticle == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select an article to delete.");
            return;
        }

        String confirmation = confirmTextField.getText();
        if (!confirmation.equalsIgnoreCase("CONFIRM")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please type 'CONFIRM' to proceed with deletion.");
            return;
        }

        try {
            articlesCollection.deleteOne(Filters.eq("id", selectedArticle.getId()));
            articleList.remove(selectedArticle); // Remove from TableView
            showAlert(Alert.AlertType.INFORMATION, "Success", "Article deleted successfully.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while deleting the article.");
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
