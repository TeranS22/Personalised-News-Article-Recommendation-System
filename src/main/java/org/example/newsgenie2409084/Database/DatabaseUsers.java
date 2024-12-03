package org.example.newsgenie2409084.Database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.example.newsgenie2409084.Model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseUsers extends AbstractDatabase {

    private static final MongoCollection<Document> usersCollection = database.getCollection("users");



    public void saveUser(User user) {
        Document userDoc = user.toDocument();
        usersCollection.insertOne(userDoc);
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        for (Document doc : usersCollection.find()) {
            users.add(User.fromDocument(doc));
        }
        return users;
    }

    public void deleteUser(String username) {
        usersCollection.deleteOne(Filters.eq("username", username));
    }

    public User getUserByUsername(String username) {
        Document doc = usersCollection.find(Filters.eq("username", username)).first();
        if (doc != null) {
            return User.fromDocument(doc);
        }
        return null;
    }

    public Document getUserDocumentByUsername(String username) {
        return usersCollection.find(Filters.eq("username", username)).first();
    }

    public void updateUsername(String oldUsername, String newUsername) {
        usersCollection.updateOne(
                Filters.eq("username", oldUsername),
                Updates.set("username", newUsername)
        );
    }

    public void updatePassword(String username, String newPassword) {
        usersCollection.updateOne(
                Filters.eq("username", username),
                Updates.set("password", newPassword)
        );
    }

    public void updatePreferences(String username, List<String> updatedPreferences, Map<String, Integer> preferredCategories) {
        usersCollection.updateOne(
                Filters.eq("username", username),
                Updates.combine(
                        Updates.set("preferences", updatedPreferences),
                        Updates.set("preferredCategories", preferredCategories)
                )
        );
    }

    public void updateUserReadHistory(String username, int articleId, String headline, String preview, int rating, String category) {
        Document userDoc = getUserDocumentByUsername(username);

        if (userDoc != null) {
            Document readHistory = userDoc.get("readHistory", Document.class);
            if (readHistory == null) {
                readHistory = new Document();
            }

            Document articleDetails = new Document();
            articleDetails.append("headline", headline);
            articleDetails.append("preview", preview);
            articleDetails.append("rating", rating);
            readHistory.put(String.valueOf(articleId), articleDetails);

            Map<String, Integer> preferredCategories = userDoc.get("preferredCategories", Map.class);
            if (preferredCategories == null) {
                preferredCategories = new HashMap<>();
            }

            int currentScore = preferredCategories.containsKey(category) ? preferredCategories.get(category) : 0;
            preferredCategories.put(category, currentScore + 1);

            usersCollection.updateOne(
                    Filters.eq("username", username),
                    Updates.combine(
                            Updates.set("readHistory", readHistory),
                            Updates.set("preferredCategories", preferredCategories)
                    )
            );
        }
    }

    public List<Integer> getSkippedArticles(String username) {
        Document userDoc = usersCollection.find(Filters.eq("username", username)).first();
        if (userDoc != null) {
            return userDoc.getList("skippedArticles", Integer.class);
        }
        return new ArrayList<>();
    }

    public void addToSkippedArticles(String username, int articleId) {
        Document userDoc = usersCollection.find(Filters.eq("username", username)).first();
        if (userDoc != null) {
            List<Integer> skippedArticles = userDoc.getList("skippedArticles", Integer.class);
            if (skippedArticles == null) {
                skippedArticles = new ArrayList<>();
            }
            skippedArticles.add(articleId);
            usersCollection.updateOne(
                Filters.eq("username", username),
                Updates.set("skippedArticles", skippedArticles)
            );
        }
    }

    public Map<String, Integer> getPreferredCategories(String username) {
        Document userDoc = getUserDocumentByUsername(username);
        if (userDoc != null) {
            return userDoc.get("preferredCategories", Map.class);
        }
        return new HashMap<>();
    }
}