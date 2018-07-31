package com.example.ogomb.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class Article {
    @SerializedName("author") private String author;
    @SerializedName("description") private String description;
    @SerializedName("title") private String title;
    @SerializedName("urlToImage") private String imageUrl;
    @SerializedName("url") private String urlStory;


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrlStory() {
        return urlStory;
    }

    public void setUrlStory(String urlStory) {
        this.urlStory = urlStory;
    }
}
