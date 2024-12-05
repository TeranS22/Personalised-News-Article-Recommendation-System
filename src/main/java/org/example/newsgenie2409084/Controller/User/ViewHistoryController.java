package org.example.newsgenie2409084.Controller.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.bson.Document;
import org.example.newsgenie2409084.Database.DatabaseUsers;
import org.example.newsgenie2409084.Util.SessionManager;
import org.example.newsgenie2409084.Util.SceneLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewHistoryController {

    @FXML
    private TableView<Document> UserHistory;

    @FXML
    private TableColumn<Document, String> ArticleName;

    @FXML
    private TableColumn<Document, String> ArticlePreview;

    @FXML
    private TableColumn<Document, String> YourRating;

    @FXML
    public Button BackToUserMenu;

    private final DatabaseUsers databaseUsers = new DatabaseUsers();

    @FXML
    public void initialize() {
        ArticleName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getString("headline")
        ));
        ArticlePreview.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getString("preview")
        ));
        YourRating.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                String.valueOf(data.getValue().get("rating"))
        ));

        loadUserHistory();
    }

    private void loadUserHistory() {
        String username = SessionManager.getUsername();
        if (username != null) {
            Document userDoc = databaseUsers.getUserDocumentByUsername(username);
            if (userDoc != null) {
                Document readHistory = userDoc.get("readHistory", Document.class);
                if (readHistory != null) {
                    List<Document> historyList = new ArrayList<>();
                    for (Map.Entry<String, Object> entry : readHistory.entrySet()) {
                        Document article = (Document) entry.getValue();
                        historyList.add(article);
                    }
                    ObservableList<Document> observableList = FXCollections.observableArrayList(historyList);
                    UserHistory.setItems(observableList);
                }
            }
        }
    }

    @FXML
    public void backToUserMenu(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/User/UserMenu.fxml");
    }
}
