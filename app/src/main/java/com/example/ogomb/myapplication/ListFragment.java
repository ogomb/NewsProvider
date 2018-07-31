package com.example.ogomb.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ogomb.myapplication.listener.NewsSearchListener;
import com.example.ogomb.myapplication.model.Article;
import com.example.ogomb.myapplication.view.NewsAdapter;
import com.example.ogomb.myapplication.web.NewsSourcesManager;
import java.util.Collections;
import java.util.List;

public class ListFragment extends Fragment implements NewsSearchListener {
    private RecyclerView recyclerView;
    private List<Article> articles;
    private NewsAdapter newsAdapter;
    private NewsSourcesManager newsSourcesManager;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancestate){
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = view.findViewById(R.id.news_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        newsAdapter = new NewsAdapter(Collections.EMPTY_LIST);
        recyclerView.setAdapter(newsAdapter);
        return  view;
    }

    @Override
    public void onStart(){
        super.onStart();
        newsSourcesManager = NewsSourcesManager.get(getContext());
        newsSourcesManager.addNewsSearchListener(this);
        newsSourcesManager.fetchNewsData();
    }

    @Override
    public void onStop(){
        super.onStop();
        newsSourcesManager.removeNewsSearchListener(this);
    }

    @Override
    public void onNewsSearchFinished() {
       articles = newsSourcesManager.getArticles();
       newsAdapter.setNewsList(articles);
    }



}
