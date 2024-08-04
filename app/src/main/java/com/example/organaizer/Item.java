package com.example.organaizer;

public class Item {
    private String title;
    private String description;
    private int id;

    public Item(int id ,String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Item(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getId(){
        return id;
    }
}
