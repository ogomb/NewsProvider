package com.example.ogomb.myapplication.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ogomb.myapplication.R;
import com.example.ogomb.myapplication.model.Article;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsHolder>{
    private List<Article> listArticles;

    public NewsAdapter(List<Article> articles){
        listArticles = articles;
    }
    public void setNewsList(List<Article> newsList){
        listArticles = newsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.single_news_item,viewGroup,false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder newsHolder, int i) {
        Article article = listArticles.get(i);
        newsHolder.bindArticle(article);

    }

    @Override
    public int getItemCount() {
        return listArticles.size();
    }
}
