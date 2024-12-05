package org.example.newsgenie2409084.Model;

import org.bson.Document;

import java.util.List;
import java.util.Map;

public class User {

    private String username;
    private String password;
    private List<String> preferences;
    private Map<String, Integer> preferredCategories;
    private Map<String, Object> readHistory;

    public User(String username, String password, List<String> preferences, Map<String, Integer> preferredCategories, Map<String, Object> readHistory) {
        this.username = username;
        this.password = password;
        this.preferences = preferences;
        this.preferredCategories = preferredCategories;
        this.readHistory = readHistory;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' + ", preferences=" + preferences + ", preferredCategories=" + preferredCategories + ", readHistory=" + readHistory + '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public Document toDocument() {
        return new Document("username", username)
                .append("password", password)
                .append("preferences", preferences)
                .append("preferredCategories", preferredCategories)
                .append("readHistory", readHistory);
    }

    public static User fromDocument(Document doc) {
        return new User(
                doc.getString("username"),
                doc.getString("password"),
                doc.getList("preferences", String.class),
                doc.get("preferredCategories", Map.class),
                doc.get("readHistory", Map.class)
        );
    }
}