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

    // // MongoDB's collection for storing user data
    private static final MongoCollection<Document> usersCollection = database.getCollection("users");

    // Saves a new user to the database
    public void saveUser(User user) {
        Document userDoc = user.toDocument();
        usersCollection.insertOne(userDoc);
    }

    // Retrieves all users from the database.
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        for (Document doc : usersCollection.find()) {
            users.add(User.fromDocument(doc));
        }
        return users;
    }

    // Deletes a user from the database based on their username.
    public void deleteUser(String username) {
        usersCollection.deleteOne(Filters.eq("username", username));
    }

    // Retrieves a user object based on their username.
    public User getUserByUsername(String username) {
        Document doc = usersCollection.find(Filters.eq("username", username)).first();
        if (doc != null) {
            return User.fromDocument(doc);
        }
        return null;
    }

    // Retrieves a user document based on their username
    public Document getUserDocumentByUsername(String username) {
        return usersCollection.find(Filters.eq("username", username)).first();
    }

    // Updates a user's username in the database.
    public void updateUsername(String oldUsername, String newUsername) {
        usersCollection.updateOne(
                Filters.eq("username", oldUsername),
                Updates.set("username", newUsername)
        );
    }

    // Updates a user's password in the database.
    public void updatePassword(String username, String newPassword) {
        usersCollection.updateOne(
                Filters.eq("username", username),
                Updates.set("password", newPassword)
        );
    }

    // Updates a user's preferences and preferred categories in the database.
    public void updatePreferences(String username, List<String> updatedPreferences, Map<String, Integer> preferredCategories) {
        usersCollection.updateOne(
                Filters.eq("username", username),
                Updates.combine(
                        Updates.set("preferences", updatedPreferences),
                        Updates.set("preferredCategories", preferredCategories)
                )
        );
    }

    // Updates a user's reading history in the database.
    public void updateUserReadHistory(String username, int articleId, String headline, String preview, int rating, String category) {
        Document userDoc = getUserDocumentByUsername(username);

        if (userDoc != null) {
            Document readHistory = userDoc.get("readHistory", Document.class); // Retrieve existing read history
            if (readHistory == null) {
                readHistory = new Document();
            }

            Document articleDetails = new Document();
            articleDetails.append("headline", headline);
            articleDetails.append("preview", preview);
            articleDetails.append("rating", rating);
            readHistory.put(String.valueOf(articleId), articleDetails); // Add or update the article details

            Map<String, Integer> preferredCategories = userDoc.get("preferredCategories", Map.class);
            if (preferredCategories == null) {
                preferredCategories = new HashMap<>();
            }

            int currentScore = preferredCategories.containsKey(category) ? preferredCategories.get(category) : 0;
            preferredCategories.put(category, currentScore + (rating/4)); // Update the preferred category score

            // Update the read history and preferred categories in the database
            usersCollection.updateOne(
                    Filters.eq("username", username),
                    Updates.combine(
                            Updates.set("readHistory", readHistory),
                            Updates.set("preferredCategories", preferredCategories)
                    )
            );
        }
    }

    // Retrieves the list of skipped articles for a user.
    public List<Integer> getSkippedArticles(String username) {
        Document userDoc = usersCollection.find(Filters.eq("username", username)).first();
        if (userDoc != null) {
            return userDoc.getList("skippedArticles", Integer.class);
        }
        return new ArrayList<>();
    }

    // Adds an article to the user's skipped articles list.
    public void addToSkippedArticles(String username, int articleId) {
        Document userDoc = usersCollection.find(Filters.eq("username", username)).first();
        if (userDoc != null) {
            List<Integer> skippedArticles = userDoc.getList("skippedArticles", Integer.class);
            if (skippedArticles == null) {
                skippedArticles = new ArrayList<>();
            }
            skippedArticles.add(articleId); // Add the article ID to the skipped articles list
            usersCollection.updateOne(
                Filters.eq("username", username),
                Updates.set("skippedArticles", skippedArticles) // Update the skipped articles list
            );
        }
    }

    // Retrieves a user's preferred categories and their scores.
    public Map<String, Integer> getPreferredCategories(String username) {
        Document userDoc = getUserDocumentByUsername(username);
        if (userDoc != null) {
            return userDoc.get("preferredCategories", Map.class);
        }
        return new HashMap<>();
    }
}