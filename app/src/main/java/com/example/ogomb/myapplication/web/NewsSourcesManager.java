package com.example.ogomb.myapplication.web;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import com.example.ogomb.myapplication.listener.NewsSearchListener;
import com.example.ogomb.myapplication.model.Article;
import com.example.ogomb.myapplication.model.ArticlesResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NewsSourcesManager {
    private static final String NEWS_ENDPOINT = "https://newsapi.org/v2";
    private static final String KEY = "77cf0019ddac41acb887527a1c06111c";
    private static final String TAG = "NEWS_SOURCE_MANAGER";

    private List<Article> articles;
    private List<NewsSearchListener> newsListenerList;
    private static NewsSourcesManager newsSourcesManager;
    private RestAdapter restAdapter;
    private Context context;

    public static NewsSourcesManager get(Context context){
        if (newsSourcesManager == null){
            Gson gson = new GsonBuilder().create();
            OkHttpClient client = setUpCache();
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint(NEWS_ENDPOINT)
                    .setConverter(new GsonConverter(gson))
                    .setRequestInterceptor(requestInterceptor)
                    .setClient(new OkClient(client))
                    .build();
            newsSourcesManager = new NewsSourcesManager(context, adapter);
        }
        return newsSourcesManager;
    }

    private NewsSourcesManager(Context context, RestAdapter adapter){
        this.context = context.getApplicationContext();
        newsListenerList = new ArrayList<>();
        restAdapter = adapter;
    }

    public static NewsSourcesManager get(Context context, RestAdapter adapter){
        if (newsSourcesManager == null){
            newsSourcesManager = new NewsSourcesManager(context, adapter);
        }
        return newsSourcesManager;
    }

    private static RequestInterceptor requestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {
            request.addQueryParam("apiKey",KEY);
        }
    };

    public void fetchNewsData(){
        NewsInterface newsInterface = restAdapter.create(NewsInterface.class);
        newsInterface.getAllNews("bitcoin", new Callback<ArticlesResponse>() {
            @Override
            public void success(ArticlesResponse articlesResponse, Response response) {
                articles = articlesResponse.getArticleList();
                notifySearchListeners();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG,"Failed to fetch news data", error);
            }
        });
    }

    public void addNewsSearchListener(NewsSearchListener listener){
        newsListenerList.add(listener);
    }

    public void removeNewsSearchListener(NewsSearchListener listener){
        newsListenerList.remove(listener);
    }

    private void notifySearchListeners(){
        for (NewsSearchListener listener: newsListenerList){
            listener.onNewsSearchFinished();
        }
    }

    public List<Article> getArticles() {
        return articles;
    }

    private static OkHttpClient setUpCache(){
        File cacheDirectory = new File(Environment.getExternalStorageDirectory(), "HttpCache");
        int cacheSize = 10 * 1024 * 1024;
        OkHttpClient client = new OkHttpClient();
        Cache cache = null;
        try{
            cache = new Cache(cacheDirectory, cacheSize);
        } catch (Exception e){
            Log.e(TAG, "out of space", e);
        }
        client.setCache(cache);
        return client;
    }
}
