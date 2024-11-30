package org.example.newsgenie2409084.Database;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.example.newsgenie2409084.Model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseUsers {
    private static final MongoCollection<Document> usersCollection;

    static {
        MongoDatabase database = MongoClients.create("mongodb://localhost:27017").getDatabase("newsGenie");
        usersCollection = database.getCollection("users");
    }

    public void saveUser(User user) {
        Document userDoc = new Document("username", user.getUsername())
                .append("password", user.getPassword())
                .append("preferences", user.getPreferences())
                .append("readHistory", user.getReadHistory());
        usersCollection.insertOne(userDoc);
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        for (Document doc : usersCollection.find()) {
            users.add(new User(
                    doc.getString("username"),
                    doc.getString("password"),
                    doc.getString("preferences"),
                    doc.getString("readHistory")
            ));
        }
        return users;
    }

    public void deleteUser(String username) {
        usersCollection.deleteOne(Filters.eq("username", username));
    }

    public User getUserByUsername(String username) {
        Document doc = usersCollection.find(Filters.eq("username", username)).first();
        if (doc != null) {
            return new User(
                    doc.getString("username"),
                    doc.getString("password"),
                    doc.getString("preferences"),
                    doc.getString("readHistory")
            );
        }
        return null;
    }
}

