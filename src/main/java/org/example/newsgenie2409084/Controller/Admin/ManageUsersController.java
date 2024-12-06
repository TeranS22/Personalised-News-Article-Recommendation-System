package org.example.newsgenie2409084.Controller.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.newsgenie2409084.Database.DatabaseUsers;
import org.example.newsgenie2409084.Model.User;
import org.example.newsgenie2409084.Util.AlertUtils;
import org.example.newsgenie2409084.Util.SceneLoader;

import java.io.IOException;

public class ManageUsersController {

    @FXML
    public Button BackToAdminMenu;

    @FXML
    public Button DeleteUserButton;

    @FXML
    private TableView<User> ManageUsersTableView;

    @FXML
    private TableColumn<User, String> Username, UserPreferences, UserReadHistory;

    @FXML
    private TextField confirmTextField;

    // Database handler and observable list for users
    private final DatabaseUsers databaseUser = new DatabaseUsers();
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
        userList.addAll(databaseUser.getAllUsers());
        ManageUsersTableView.setItems(userList);
    }

    @FXML
    public void deleteUser(ActionEvent event) {

        // Get selected user
        User selectedUser = ManageUsersTableView.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            AlertUtils.showError("Error", "Please select a user to delete.");
            return;
        }

        String confirmation = confirmTextField.getText();
        if (!confirmation.equalsIgnoreCase("CONFIRM")) {
            AlertUtils.showError("Error", "Please type 'CONFIRM' to delete the user.");
            return;
        }

        // Delete the user from the database and update the table
        databaseUser.deleteUser(selectedUser.getUsername());
        userList.remove(selectedUser); // From list
        AlertUtils.showSuccess("Success", "User deleted successfully.");

        confirmTextField.clear();
    }

    @FXML
    public void backToAdminMenu(ActionEvent event) throws IOException {
        SceneLoader.loadScene(event, "/org/example/newsgenie2409084/View/Admin/AdminMenu.fxml");
    }
}