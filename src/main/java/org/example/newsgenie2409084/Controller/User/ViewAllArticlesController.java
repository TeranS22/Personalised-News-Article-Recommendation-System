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
import org.example.newsgenie2409084.Model.Article;
import org.example.newsgenie2409084.Util.SceneLoader;

import java.io.IOException;
import java.util.List;

public class ViewAllArticlesController {

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

    private static final MongoCollection<Document> articlesCollection;

    static {
        MongoDatabase database = MongoClients.create("mongodb://localhost:27017").getDatabase("newsGenie");
        articlesCollection = database.getCollection("articles");
    }

    @FXML
    public void initialize() {
        ArticleHeadline.setCellValueFactory(new PropertyValueFactory<>("name"));
        ArticlePreview.setCellValueFactory(new PropertyValueFactory<>("preview"));
        loadAllArticles();

        setupRadioButtonHandlers();
    }

    private void loadAllArticles() {
        ObservableList<Article> articles = FXCollections.observableArrayList();
        articlesCollection.find().forEach(document -> {
            articles.add(new Article(
                    document.getInteger("id"),
                    document.getString("name"),
                    document.getString("preview"),
                    document.getString("category"),
                    document.getString("link"),
                    document.getDouble("averageRating"), // Pass averageRating
                    document.getInteger("ratingCount")   // Pass ratingCount
            ));
        });
        AllArticlesTable.setItems(articles);
    }

    private void setupRadioButtonHandlers() {
        List<RadioButton> radioButtons = List.of(
                healthRadio, technologyRadio, politicsRadio, businessRadio, scienceRadio,
                sportRadio, entertainmentRadio, environmentRadio, crimeRadio, educationRadio, weatherRadio, otherRadio
        );

        for (RadioButton radioButton : radioButtons) {
            radioButton.setOnAction(event -> {
                clearOtherRadioButtons(radioButton, radioButtons);
                filterArticlesByCategory(radioButton.getText());
            });
        }
    }

    private void clearOtherRadioButtons(RadioButton selectedButton, List<RadioButton> radioButtons) {
        for (RadioButton radioButton : radioButtons) {
            if (radioButton != selectedButton) {
                radioButton.setSelected(false);
            }
        }
    }

    private void filterArticlesByCategory(String category) {
        ObservableList<Article> filteredArticles = FXCollections.observableArrayList();
        articlesCollection.find(new Document("category", category)).forEach(document -> {
            filteredArticles.add(new Article(
                    document.getInteger("id"),
                    document.getString("name"),
                    document.getString("preview"),
                    document.getString("category"),
                    document.getString("link"),
                    document.getDouble("averageRating"), // Add averageRating
                    document.getInteger("ratingCount")   // Add ratingCount
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
            ViewArticleController.loadArticle(selectedArticle);
            SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/User/ViewArticle.fxml");
        } else {
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