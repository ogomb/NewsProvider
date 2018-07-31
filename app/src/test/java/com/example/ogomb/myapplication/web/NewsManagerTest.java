package com.example.ogomb.myapplication.web;

import com.example.ogomb.myapplication.listener.NewsSearchListener;
import com.example.ogomb.myapplication.model.Article;
import com.example.ogomb.myapplication.model.ArticlesResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import retrofit.Callback;
import retrofit.RestAdapter;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class NewsManagerTest {

    @Captor
    private ArgumentCaptor<Callback<ArticlesResponse>> articlesCaptor;
    private NewsSourcesManager newsSourcesManager;
    private static RestAdapter adapter = mock(RestAdapter.class);
    private static NewsInterface newsInterface = mock(NewsInterface.class);
    private static NewsSearchListener newsSearchListener = mock(NewsSearchListener.class);

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        newsSourcesManager = NewsSourcesManager.get(RuntimeEnvironment.application,
                adapter);

        when(adapter.create(NewsInterface.class))
                .thenReturn(newsInterface);

        newsSourcesManager.addNewsSearchListener(newsSearchListener);
    }

    @After
    public void tearDown(){
        reset(adapter,newsInterface,newsSearchListener);
        newsSourcesManager.removeNewsSearchListener(newsSearchListener);
    }

    @Test
    public void newsListenerTriggeredAfterSuccessfulRequest(){
        newsSourcesManager.fetchNewsData();

        verify(newsInterface).getAllNews(anyString(), articlesCaptor.capture());

        ArticlesResponse response = mock(ArticlesResponse.class);
        articlesCaptor.getValue().success(response, null);

        verify(newsSearchListener).onNewsSearchFinished();
    }

    @Test
    public void venueSearchListSavedOnSuccessfulSearch() {
        newsSourcesManager.fetchNewsData();

        verify(newsInterface).getAllNews(anyString(), articlesCaptor.capture());

        String title = "Cool first venue";
        Article firstArticle = mock(Article.class);
        when(firstArticle.getTitle()).thenReturn(title);

        String secondTitle = "awesome second venue";
        Article secondArticle = mock(Article.class);
        when(secondArticle.getTitle()).thenReturn(secondTitle);

        List<Article> articleList = new ArrayList<>();
        articleList.add(firstArticle);
        articleList.add(secondArticle);

        ArticlesResponse response = mock(ArticlesResponse.class);
        when(response.getArticleList()).thenReturn(articleList);

        articlesCaptor.getValue().success(response, null);

        List<Article> newsManagerArticleList = newsSourcesManager.getArticles();
        assertThat(newsManagerArticleList, is(equalTo(articleList)));
    }

}
