package org.example.newsgenie2409084;

public class Article {
    private int id;
    private String name;
    private String preview;
    private String category;
    private String link;

    public Article(int id, String name, String preview, String category, String link) {
        this.id = id;
        this.name = name;
        this.preview = preview;
        this.category = category;
        this.link = link;
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

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Preview: " + preview + ", Category: " + category + ", Link: " + link;
    }
}
