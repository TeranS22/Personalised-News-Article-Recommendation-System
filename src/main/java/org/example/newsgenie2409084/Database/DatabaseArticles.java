package org.example.newsgenie2409084.Database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.example.newsgenie2409084.Model.Article;

import java.util.ArrayList;
import java.util.List;

public class DatabaseArticles extends AbstractDatabase {

    private static final MongoCollection<Document> articlesCollection = database.getCollection("articles");

    public void saveArticle(Article article) {
        Document articleDoc = new Document("id", article.getId())
                .append("name", article.getName())
                .append("preview", article.getPreview())
                .append("category", article.getCategory())
                .append("link", article.getLink())
                .append("averageRating", article.getAverageRating())
                .append("ratingCount", article.getRatingCount());
        articlesCollection.insertOne(articleDoc);
    }

    public List<Article> getAllArticles() {
        List<Article> articles = new ArrayList<>();
        for (Document doc : articlesCollection.find()) {
            articles.add(Article.fromDocument(doc));
        }
        return articles;
    }

    public List<String> getAllArticleUrls() {
        List<String> urls = new ArrayList<>();
        for (Article article : getAllArticles()) {
            urls.add(article.getLink());
        }
        return urls;
    }

    public List<Article> getArticlesByCategory(String category) {
        List<Article> articles = new ArrayList<>();
        for (Document doc : articlesCollection.find(Filters.eq("category", category))) {
            articles.add(Article.fromDocument(doc));
        }
        return articles;
    }

    public void deleteArticle(int id) {
        articlesCollection.deleteOne(Filters.eq("id", id));
    }

    public void updateArticleRating(int id, double newAverageRating, int newRatingCount) {
        articlesCollection.updateOne(
                Filters.eq("id", id),
                Updates.combine(
                        Updates.set("averageRating", newAverageRating),
                        Updates.set("ratingCount", newRatingCount)
                )
        );
    }

    public int getMaxArticleId() {
        Document maxIdDoc = articlesCollection.find()
                .sort(new Document("id", -1))
                .limit(1)
                .first();

        return (maxIdDoc != null) ? maxIdDoc.getInteger("id") : 0;
    }
}