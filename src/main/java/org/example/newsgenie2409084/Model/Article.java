package org.example.newsgenie2409084.Model;

import org.bson.Document;

public class Article {
    private int id;
    private String name;
    private String preview;
    private String category;
    private String link;
    private double averageRating;
    private int ratingCount;

    public Article(int id, String name, String preview, String category, String link, double averageRating, int ratingCount) {
        this.id = id;
        this.name = name;
        this.preview = preview;
        this.category = category;
        this.link = link;
        this.averageRating = averageRating;
        this.ratingCount = ratingCount;
    }

    public Article(int id, String name, String preview, String category, String link) {
        this.id = id;
        this.name = name;
        this.preview = preview;
        this.category = category;
        this.link = link;
        this.averageRating = 0.0;
        this.ratingCount = 0;
    }

    public static Article fromDocument(Document doc) {
        int id = doc.getInteger("id");
        String name = doc.getString("name");
        String preview = doc.getString("preview");
        String category = doc.getString("category");
        String link = doc.getString("link");
        double averageRating = doc.getDouble("averageRating");
        int ratingCount = doc.getInteger("ratingCount");

        return new Article(id, name, preview, category, link, averageRating, ratingCount);
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Preview: " + preview + ", Category: " + category + ", Link: " + link + ", AverageRating: " + averageRating + ", RatingCount: " + ratingCount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPreview() {
        return preview;
    }

    public String getCategory() {
        return category;
    }

    public String getLink() {
        return link;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public int getRatingCount() {
        return ratingCount;
    }
}