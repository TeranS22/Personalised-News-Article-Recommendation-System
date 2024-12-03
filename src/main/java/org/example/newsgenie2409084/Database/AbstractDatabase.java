package org.example.newsgenie2409084.Database;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public abstract class AbstractDatabase {
    protected static final MongoDatabase database;

    static {
        database = MongoClients.create("mongodb://localhost:27017").getDatabase("newsGenie");
    }
}