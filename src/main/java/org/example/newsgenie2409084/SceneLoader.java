package org.example.newsgenie2409084;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneLoader {

    public static void loadScene(ActionEvent event, String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(SceneLoader.class.getResource(fxmlFile));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000.0, 750.0);
        stage.setScene(scene);
        stage.show();
    }
}
