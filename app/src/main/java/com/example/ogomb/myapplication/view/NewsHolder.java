package com.example.ogomb.myapplication.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.ogomb.myapplication.controller.DetailNewsActivity;
import com.example.ogomb.myapplication.R;
import com.example.ogomb.myapplication.model.Article;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private Article article;

    private TextView news_title_text;
    private TextView news_description_text;
    private TextView news_author;
    private CircleImageView news_image;

    public NewsHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        news_title_text = itemView.findViewById(R.id.news_title);
        news_description_text = itemView.findViewById(R.id.news_description);
        news_author = itemView.findViewById(R.id.news_author);
        news_image = itemView.findViewById(R.id.news_image);

    }

    public void bindArticle(Article article){
        this.article = article;
        news_title_text.setText(article.getTitle());
        news_description_text.setText(article.getDescription());
        news_author.setText(article.getAuthor());

        Picasso.with(itemView.getContext())
                .load(article.getImageUrl())
                .into(news_image);


    }

    @Override
    public void onClick(View view) {
        Context context = view.getContext();
        Intent intent = DetailNewsActivity.newIntent(context, article.getUrlStory());
        context.startActivity(intent);
    }
}
