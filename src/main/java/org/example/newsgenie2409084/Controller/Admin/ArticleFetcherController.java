package org.example.newsgenie2409084.Controller.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.newsgenie2409084.Database.DatabaseArticles;
import org.example.newsgenie2409084.Model.Article;
import org.example.newsgenie2409084.Service.CategoriseArticles;
import org.example.newsgenie2409084.Util.SceneLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ArticleFetcherController {

    @FXML
    public Button BackToAdminMenu;
    @FXML
    public Button FetchAndSaveArticles;

    @FXML
    private TextField NumberOfArticlesToBeFetched;
    @FXML
    private TextField confirmTextField;

    private static final String API_KEY = "35e209b1c0b5403ca29d116c65d7ce44";
    private final DatabaseArticles databaseArticle = new DatabaseArticles();

    @FXML
    public void fetchAndSaveArticles(ActionEvent event) {
        String inputNumber = NumberOfArticlesToBeFetched.getText();
        String confirmation = confirmTextField.getText();

        if (!confirmation.equalsIgnoreCase("CONFIRM")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please type 'CONFIRM' to proceed.");
            return;
        }

        int articleCount;
        try {
            articleCount = Integer.parseInt(inputNumber);
            if (articleCount < 1 || articleCount > 35) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter a number between 1 and 35.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid input. Please enter a valid number.");
            return;
        }

        fetchArticlesFromAPI(articleCount);

        NumberOfArticlesToBeFetched.clear();
        confirmTextField.clear();
    }

    private void fetchArticlesFromAPI(int articleCount) {
        String apiUrl = "https://newsapi.org/v2/top-headlines?country=us&pageSize=" + articleCount + "&apiKey=" + API_KEY;

        List<Article> articles = new ArrayList<>();
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                saveArticles(response.toString(), articles);

                showAlert(Alert.AlertType.INFORMATION, "Success", articles.size() + " articles were fetched and saved.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to fetch articles. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred. Please try again.");
            e.printStackTrace();
        }
    }

    private void saveArticles(String response, List<Article> articles) {
        int idCounter = databaseArticle.getMaxArticleId() + 1;

        String[] articleBlocks = response.split("\"articles\":\\[")[1].split("\\],")[0].split("\\},\\{");

        for (String block : articleBlocks) {
            String name = extractValue(block, "\"title\":\"");
            String preview = extractValue(block, "\"description\":\"");
            String link = extractValue(block, "\"url\":\"");

            CategoriseArticles categoriser = new CategoriseArticles();
            String category = categoriser.categorize(name, preview);

            Article article = new Article(idCounter++, name, preview, category, link);
            articles.add(article);

            databaseArticle.saveArticle(article);
        }
    }

    private String extractValue(String block, String key) {
        try {
            int startIndex = block.indexOf(key) + key.length();
            int endIndex = block.indexOf("\",", startIndex);
            if (endIndex == -1) {
                endIndex = block.indexOf("\"}", startIndex);
            }
            return block.substring(startIndex, endIndex).replace("\\\"", "\"");
        } catch (Exception e) {
            return "N/A";
        }
    }

    @FXML
    public void backToAdminMenu(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/Admin/AdminMenu.fxml");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
