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

public class ManageUsersController {

    @FXML
    public Button BackToAdminMenu;
    @FXML
    public Button DeleteUserButton;

    @FXML
    private TableView<User> ManageUsersTableView;
    @FXML
    private TableColumn<User, String> Username;
    @FXML
    private TableColumn<User, String> UserPreferences;
    @FXML
    private TableColumn<User, String> UserReadHistory;

    @FXML
    private TextField confirmTextField;

    private static final MongoCollection<Document> usersCollection;

    static {
        MongoDatabase database = MongoClients.create("mongodb://localhost:27017").getDatabase("newsGenie");
        usersCollection = database.getCollection("users");
    }

    private ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        Username.setCellValueFactory(new PropertyValueFactory<>("username"));
        UserPreferences.setCellValueFactory(new PropertyValueFactory<>("preferences"));
        UserReadHistory.setCellValueFactory(new PropertyValueFactory<>("readHistory"));

        loadUsers();
    }

    private void loadUsers() {
        userList.clear();

        for (Document doc : usersCollection.find()) {
            User user = new User(
                    doc.getString("username"),
                    null,
                    doc.getString("preferences") != null ? doc.getString("preferences") : "No Preferences",
                    doc.getString("readHistory") != null ? doc.getString("readHistory") : "No History"
            );
            userList.add(user);
        }

        ManageUsersTableView.setItems(userList);
    }

    // Handle Back to Admin Menu button
    @FXML
    public void backToAdminMenu(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "AdminMenu.fxml");
    }

    @FXML
    public void deleteUser(ActionEvent event) {
        User selectedUser = ManageUsersTableView.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a user to delete.");
            return;
        }

        String confirmation = confirmTextField.getText();
        if (!confirmation.equalsIgnoreCase("CONFIRM")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please type 'CONFIRM' to proceed with deletion.");
            return;
        }

        try {
            usersCollection.deleteOne(Filters.eq("username", selectedUser.getUsername()));
            userList.remove(selectedUser);
            showAlert(Alert.AlertType.INFORMATION, "Success", "User deleted successfully.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while deleting the user.");
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
