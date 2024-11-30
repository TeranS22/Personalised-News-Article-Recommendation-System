package org.example.newsgenie2409084.Model;

import org.bson.Document;

public class User {
    private String username;
    private String password;
    private String preferences;
    private String readHistory;

    public User(String username, String password, String preferences, String readHistory) {
        this.username = username;
        this.password = password;
        this.preferences = preferences;
        this.readHistory = readHistory;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPreferences() {
        return preferences;
    }

    public String getReadHistory() {
        return readHistory;
    }

    public Document toDocument() {
        return new Document("username", username)
                .append("password", password)
                .append("preferences", preferences)
                .append("readHistory", readHistory != null ? readHistory : "");
    }

    public static User fromDocument(Document doc) {
        return new User(
                doc.getString("username"),
                doc.getString("password"),
                doc.getString("preferences"),
                doc.getString("readHistory")
        );
    }

    @Override
    public String toString() {
        return "Username: " + username + ", Preferences: " + preferences +
               ", ReadHistory: " + (readHistory != null ? readHistory : "None");
    }
}
