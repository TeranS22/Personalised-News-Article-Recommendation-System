package org.example.newsgenie2409084.Database;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

public class DatabaseFetchCount {

    private static final String COLLECTION_NAME = "fetchCount";
    private static final String FETCH_COUNT_KEY = "fetchCount";

    private static final MongoCollection<Document> fetchCountCollection;

    static {
        MongoDatabase database = MongoClients.create("mongodb://localhost:27017").getDatabase("newsGenie");
        fetchCountCollection = database.getCollection(COLLECTION_NAME);
    }

    public void initializeFetchCount() {
        Document existingCount = fetchCountCollection.find(Filters.eq("_id", FETCH_COUNT_KEY)).first();
        if (existingCount == null) {
            Document newCount = new Document("_id", FETCH_COUNT_KEY).append("value", 1);
            fetchCountCollection.insertOne(newCount);
        }
    }

    public int getFetchCount() {
        Document fetchCountDoc = fetchCountCollection.find(Filters.eq("_id", FETCH_COUNT_KEY)).first();
        if (fetchCountDoc != null) {
            return fetchCountDoc.getInteger("value");
        } else {
            initializeFetchCount();
            return 1;
        }
    }

    public void updateFetchCount(int newCount) {
        fetchCountCollection.updateOne(
                Filters.eq("_id", FETCH_COUNT_KEY),
                Updates.set("value", newCount)
        );
    }
}