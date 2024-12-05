package org.example.newsgenie2409084.Controller.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.newsgenie2409084.Database.DatabaseArticles;
import org.example.newsgenie2409084.Database.DatabaseFetchCount;
import org.example.newsgenie2409084.Model.Article;
import org.example.newsgenie2409084.Service.CategoriseArticles;
import org.example.newsgenie2409084.Util.SceneLoader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArticleFetcherController {

    @FXML
    public Button BackToAdminMenu;
    @FXML
    public Button FetchAndSaveArticles;

    @FXML
    private TextField NumberOfArticlesToBeFetched;
    @FXML
    private TextField confirmTextField;

    private static final String API_KEY = "Kws1Nwroq7iA-oEJVgn0JNhM7EHZFy3XR_CHtwCx5T4EsZeK";
    private final DatabaseArticles databaseArticles = new DatabaseArticles();
    private final DatabaseFetchCount databaseFetchCount = new DatabaseFetchCount();

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
            if (articleCount < 1 || articleCount > 40) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter a number between 1 and 40.");
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
        int currentPage = databaseFetchCount.getFetchCount();
        List<Article> articles = new ArrayList<>();
        int articlesFetched = 0;

        try {
            while (articlesFetched < articleCount) {
                String apiUrl = "https://api.currentsapi.services/v1/latest-news?language=en&limit=" +
                                articleCount + "&page_number=" + currentPage + "&apiKey=" + API_KEY;

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

                    articlesFetched += saveArticles(response.toString(), articles, articleCount - articlesFetched);

                    currentPage++;
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to fetch articles. Response Code: " + responseCode);
                    return;
                }
            }

            databaseFetchCount.updateFetchCount(currentPage);

            showAlert(Alert.AlertType.INFORMATION, "Success", articles.size() + " new articles were fetched and saved.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred. Please try again.");
            e.printStackTrace();
        }
    }

    private int saveArticles(String response, List<Article> articles, int remainingCount) {
        int idCounter = databaseArticles.getMaxArticleId() + 1;
        Set<String> existingUrls = new HashSet<>(databaseArticles.getAllArticleUrls());
        int savedCount = 0;

        try {
            JSONObject jsonResponse = new JSONObject(response);

            if (!jsonResponse.has("news")) {
                showAlert(Alert.AlertType.WARNING, "No Articles", "No articles found in the API response.");
                return 0;
            }

            JSONArray newsArray = jsonResponse.getJSONArray("news");

            for (int i = 0; i < newsArray.length() && savedCount < remainingCount; i++) {
                JSONObject articleJson = newsArray.getJSONObject(i);

                String name = articleJson.optString("title", "No Title");
                String preview = articleJson.optString("description", "No Description");
                String link = articleJson.optString("url", "");
                String source = articleJson.optString("source", "");

                if (existingUrls.contains(link) || link.isEmpty() || source.equalsIgnoreCase("einnews")) {
                    continue;
                }

                CategoriseArticles categoriser = new CategoriseArticles();
                String category = categoriser.categorise(name, preview);

                Article article = new Article(idCounter++, name, preview, category, link, 0.0, 0);
                articles.add(article);
                databaseArticles.saveArticle(article);
                savedCount++;
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to process articles from the API response.");
        }

        return savedCount;
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