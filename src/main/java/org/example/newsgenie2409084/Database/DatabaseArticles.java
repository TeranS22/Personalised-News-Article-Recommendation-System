package org.example.newsgenie2409084.Database;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.example.newsgenie2409084.Model.Article;

import java.util.ArrayList;
import java.util.List;

public class DatabaseArticles {
    private static final MongoCollection<Document> articlesCollection;

    static {
        MongoDatabase database = MongoClients.create("mongodb://localhost:27017").getDatabase("newsGenie");
        articlesCollection = database.getCollection("articles");
    }

    public void saveArticle(Article article) {
        Document articleDoc = new Document("id", article.getId())
                .append("name", article.getName())
                .append("preview", article.getPreview())
                .append("category", article.getCategory())
                .append("link", article.getLink());
        articlesCollection.insertOne(articleDoc);
    }

    public List<Article> getAllArticles() {
        List<Article> articles = new ArrayList<>();
        for (Document doc : articlesCollection.find()) {
            articles.add(new Article(
                    doc.getInteger("id"),
                    doc.getString("name"),
                    doc.getString("preview"),
                    doc.getString("category"),
                    doc.getString("link")
            ));
        }
        return articles;
    }

    public void deleteArticle(int id) {
        articlesCollection.deleteOne(Filters.eq("id", id));
    }

    public int getMaxArticleId() {
        Document maxIdDoc = articlesCollection.find()
                .sort(new Document("id", -1))
                .limit(1)
                .first();

        if (maxIdDoc != null && maxIdDoc.containsKey("id")) {
            return maxIdDoc.getInteger("id");
        }
        return 0;
    }
}
