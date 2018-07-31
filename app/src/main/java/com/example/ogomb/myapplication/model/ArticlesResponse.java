package com.example.ogomb.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticlesResponse {
    @SerializedName("articles") List<Article> articleList;

    public List<Article> getArticleList() {
        return articleList;
    }
}
