package org.example.newsgenie2409084.App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.newsgenie2409084.Service.ThreadPoolExecutorService;

public class NewsGenieApplication extends Application {

    @Override
    public void start(Stage mainStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/newsgenie2409084/View/WelcomePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 750);
        mainStage.setTitle("Personalized News Recommendation");
        mainStage.setScene(scene);
        mainStage.setOnCloseRequest(event -> ThreadPoolExecutorService.shutdown());
        mainStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}