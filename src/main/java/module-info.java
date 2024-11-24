module org.example.newsgenie2409084 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;


    opens org.example.newsgenie2409084 to javafx.fxml;
    exports org.example.newsgenie2409084;
}