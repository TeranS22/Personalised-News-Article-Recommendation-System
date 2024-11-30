package org.example.newsgenie2409084.Model;

public class Article {
    private int id;
    private String name;
    private String preview;
    private String category;
    private String link;
    private int thumbsUp;
    private int thumbsDown;

    public Article(int id, String name, String preview, String category, String link) {
        this.id = id;
        this.name = name;
        this.preview = preview;
        this.category = category;
        this.link = link;
        this.thumbsUp = 0;
        this.thumbsDown = 0;
    }

    public Article(int id, String name, String preview, String category, String link, int thumbsUp, int thumbsDown) {
        this.id = id;
        this.name = name;
        this.preview = preview;
        this.category = category;
        this.link = link;
        this.thumbsUp = thumbsUp;
        this.thumbsDown = thumbsDown;
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

    public int getThumbsUp() {
        return thumbsUp;
    }

    public int getThumbsDown() {
        return thumbsDown;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Preview: " + preview +
               ", Category: " + category + ", Link: " + link +
               ", ThumbsUp: " + thumbsUp + ", ThumbsDown: " + thumbsDown;
    }
}
