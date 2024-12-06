package org.example.newsgenie2409084.Controller.User;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bson.Document;
import org.example.newsgenie2409084.Database.AbstractDatabase;
import org.example.newsgenie2409084.Model.Article;
import org.example.newsgenie2409084.Util.SceneLoader;

import java.io.IOException;
import java.util.List;

public class ViewAllArticlesController extends AbstractDatabase {

    @FXML
    private RadioButton healthRadio, technologyRadio, politicsRadio, businessRadio, scienceRadio,
            sportRadio, entertainmentRadio, environmentRadio, crimeRadio, educationRadio, weatherRadio, otherRadio;

    @FXML
    private TableView<Article> AllArticlesTable;
    @FXML
    private TableColumn<Article, String> ArticleHeadline;
    @FXML
    private TableColumn<Article, String> ArticlePreview;

    @FXML
    public Button ResetFilters;
    @FXML
    public Button ViewArticle;

    // MongoDB's collection for articles
    private static final MongoCollection<Document> articlesCollection = database.getCollection("articles");


    @FXML
    public void initialize() {
        // Set table columns to Article model properties
        ArticleHeadline.setCellValueFactory(new PropertyValueFactory<>("name"));
        ArticlePreview.setCellValueFactory(new PropertyValueFactory<>("preview"));
        loadAllArticles();

        setupRadioButtonHandlers();
    }

    private void loadAllArticles() {
        // Fetch articles from the database and add them to the observable list
        ObservableList<Article> articles = FXCollections.observableArrayList();
        articlesCollection.find().forEach(document -> {
            articles.add(new Article(
                    document.getInteger("id"),
                    document.getString("name"),
                    document.getString("preview"),
                    document.getString("category"),
                    document.getString("link"),
                    document.getDouble("averageRating"),
                    document.getInteger("ratingCount")
            ));
        });
        AllArticlesTable.setItems(articles);
    }

    private void setupRadioButtonHandlers() {
        // List of all category radio buttons
        List<RadioButton> radioButtons = List.of(
                healthRadio, technologyRadio, politicsRadio, businessRadio, scienceRadio,
                sportRadio, entertainmentRadio, environmentRadio, crimeRadio, educationRadio, weatherRadio, otherRadio
        );

        // Add action listeners to each radio button
        for (RadioButton radioButton : radioButtons) {
            radioButton.setOnAction(event -> {
                clearOtherRadioButtons(radioButton, radioButtons);
                filterArticlesByCategory(radioButton.getText());
            });
        }
    }

    // Clears all other radio buttons except the selected one.
    private void clearOtherRadioButtons(RadioButton selectedButton, List<RadioButton> radioButtons) {
        for (RadioButton radioButton : radioButtons) {
            if (radioButton != selectedButton) {
                radioButton.setSelected(false);
            }
        }
    }

    // Filters articles based on the selected category.
    private void filterArticlesByCategory(String category) {
        ObservableList<Article> filteredArticles = FXCollections.observableArrayList();
        // Fetch articles matching the category from the database
        articlesCollection.find(new Document("category", category)).forEach(document -> {
            filteredArticles.add(new Article(
                    document.getInteger("id"),
                    document.getString("name"),
                    document.getString("preview"),
                    document.getString("category"),
                    document.getString("link"),
                    document.getDouble("averageRating"),
                    document.getInteger("ratingCount")
            ));
        });
        AllArticlesTable.setItems(filteredArticles);
    }

    @FXML
    public void resetFilters(ActionEvent event) {
        clearAllRadioButtons();
        loadAllArticles();
    }

    private void clearAllRadioButtons() {
        healthRadio.setSelected(false);
        technologyRadio.setSelected(false);
        politicsRadio.setSelected(false);
        businessRadio.setSelected(false);
        scienceRadio.setSelected(false);
        sportRadio.setSelected(false);
        entertainmentRadio.setSelected(false);
        environmentRadio.setSelected(false);
        crimeRadio.setSelected(false);
        educationRadio.setSelected(false);
        weatherRadio.setSelected(false);
        otherRadio.setSelected(false);
    }

    public void viewArticle(ActionEvent event) throws IOException {
        Article selectedArticle = AllArticlesTable.getSelectionModel().getSelectedItem();
        if (selectedArticle != null) {
            // Load the selected article and navigate to the detailed view
            ViewArticleController.loadArticle(selectedArticle);
            SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/User/ViewArticle.fxml");
        } else {
            // Show a warning if no article is selected
            showAlert("No Article Selected", "Please select an article to view.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void backToUserMenu(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/User/UserMenu.fxml");
    }
}