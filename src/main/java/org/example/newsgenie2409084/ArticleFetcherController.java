package org.example.newsgenie2409084;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.bson.Document;

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

    private static final MongoCollection<Document> articlesCollection;

    static {
        MongoDatabase database = MongoClients.create("mongodb://localhost:27017").getDatabase("newsGenie");
        articlesCollection = database.getCollection("articles");
    }

    private static final String API_KEY = "35e209b1c0b5403ca29d116c65d7ce44";

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
            if (articleCount < 10 || articleCount > 100) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter a number between 10 and 100.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid input. Please enter a valid number between 10 and 100.");
            return;
        }

        fetchArticlesFromAPI(articleCount);
    }

    private void fetchArticlesFromAPI(int articleCount) {
        String apiUrl = "https://newsapi.org/v2/top-headlines?country=us&pageSize=" + articleCount + "&apiKey=" + API_KEY;

        List<Article> articles = new ArrayList<>();
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                String responseString = response.toString();
                SaveArticles(responseString, articles);

                showAlert(Alert.AlertType.INFORMATION, "Success", articles.size() + " articles were fetched and saved to the database.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to fetch articles. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred. Please try again.");
            e.printStackTrace();
        }
    }

    private void SaveArticles(String response, List<Article> articles) {
        int idCounter = getMaxArticleId() + 1;

        String[] articleBlocks = response.split("\"articles\":\\[")[1].split("\\],")[0].split("\\},\\{");

        for (String block : articleBlocks) {
            String name = extractValue(block, "\"title\":\"");
            String preview = extractValue(block, "\"description\":\"");
            String link = extractValue(block, "\"url\":\"");

            CategoriseArticles nlpProcessor = new CategoriseArticles();
            String category = nlpProcessor.categorize(name, preview);

            Article article = new Article(idCounter++, name, preview, category, link);
            articles.add(article);

            saveToDatabase(article);
        }
    }

    private void saveToDatabase(Article article) {
        Document articleDoc = new Document("id", article.getId())
                .append("name", article.getName())
                .append("preview", article.getPreview())
                .append("category", article.getCategory())
                .append("link", article.getLink());

        articlesCollection.insertOne(articleDoc);

        System.out.println("Saved to database: " + article);
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

    private int getMaxArticleId() {
        Document maxIdDoc = articlesCollection.find()
                .sort(new Document("id", -1)) // We are sorting IDs in descending order
                .limit(1) // And we get the first (which means the highest)
                .first();

        if (maxIdDoc != null && maxIdDoc.containsKey("id")) {
            return maxIdDoc.getInteger("id");
        }
        return 0;
    }

    @FXML
    public void backToAdminMenu(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "AdminMenu.fxml");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
