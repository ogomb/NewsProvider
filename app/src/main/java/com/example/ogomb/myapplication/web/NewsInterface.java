package com.example.ogomb.myapplication.web;

import com.example.ogomb.myapplication.model.ArticlesResponse;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.Query;

public interface NewsInterface {
    @GET("/everything")
    void getAllNews(@Query("q") String searchField, Callback<ArticlesResponse> callback);
}
