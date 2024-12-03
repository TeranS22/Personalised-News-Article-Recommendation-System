package org.example.newsgenie2409084.Database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

public class DatabaseFetchCount extends AbstractDatabase {

    private static final String COLLECTION_NAME = "fetchCount";
    private static final String FETCH_COUNT_KEY = "fetchCount";
    private static final MongoCollection<Document> fetchCountCollection = database.getCollection(COLLECTION_NAME);

    public void initializeFetchCount() {
        if (fetchCountCollection.find(Filters.eq("_id", FETCH_COUNT_KEY)).first() == null) {
            fetchCountCollection.insertOne(new Document("_id", FETCH_COUNT_KEY).append("value", 1));
        }
    }

    public int getFetchCount() {
        Document fetchCountDoc = fetchCountCollection.find(Filters.eq("_id", FETCH_COUNT_KEY)).first();
        if (fetchCountDoc == null) {
            initializeFetchCount();
            return 1;
        }
        return fetchCountDoc.getInteger("value");
    }

    public void updateFetchCount(int newCount) {
        fetchCountCollection.updateOne(
                Filters.eq("_id", FETCH_COUNT_KEY),
                Updates.set("value", newCount)
        );
    }
}