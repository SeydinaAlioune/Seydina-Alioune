package com.zypher.shortnotes;

public class Note {
    private int id;
    private String title;
    private String content;
    private String category;
    private static int idCounter = 0; // Static counter for generating unique IDs

    public Note(String title, String content, String category) {
        this.id = idCounter++;
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
