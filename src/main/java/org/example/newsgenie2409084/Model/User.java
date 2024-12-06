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
                "username='" + username + '\'' +
                ", preferences=" + preferences +
                ", preferredCategories=" + preferredCategories +
                ", readHistory=" + readHistory +
                '}';
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

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<String> preferences) {
        this.preferences = preferences;
    }

    public Map<String, Integer> getPreferredCategories() {
        return preferredCategories;
    }

    public void setPreferredCategories(Map<String, Integer> preferredCategories) {
        this.preferredCategories = preferredCategories;
    }

    public Map<String, Object> getReadHistory() {
        return readHistory;
    }

    public void setReadHistory(Map<String, Object> readHistory) {
        this.readHistory = readHistory;
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