module org.example.newsgenie2409084 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.bson;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires org.json;

    opens org.example.newsgenie2409084.Controller to javafx.fxml;
    opens org.example.newsgenie2409084.Model to javafx.base;
    opens org.example.newsgenie2409084.App to javafx.graphics;

    exports org.example.newsgenie2409084.App;
    exports org.example.newsgenie2409084.Controller;
    exports org.example.newsgenie2409084.Model;
    exports org.example.newsgenie2409084.Controller.Admin;
    opens org.example.newsgenie2409084.Controller.Admin to javafx.fxml;
    exports org.example.newsgenie2409084.Controller.User;
    opens org.example.newsgenie2409084.Controller.User to javafx.fxml;
}
