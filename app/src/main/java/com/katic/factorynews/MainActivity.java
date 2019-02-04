package com.katic.factorynews;

import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.katic.factorynews.adapters.ArticlesAdapter;
import com.katic.factorynews.models.Article;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements GetArticlesAsyncTask.Listener, ArticlesAdapter.Listener {

    public static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    private Unbinder mUnBinder;
    private ArticlesAdapter mArticlesAdapter;
    private GetArticlesAsyncTask mGetArticlesAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnBinder = ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, RecyclerView.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mArticlesAdapter = new ArticlesAdapter(this);
        mRecyclerView.setAdapter(mArticlesAdapter);

        new GetArticlesAsyncTask(this).execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        cancelGetArticlesTask();
    }

    private void cancelGetArticlesTask() {
        if (mGetArticlesAsyncTask != null) {
            mGetArticlesAsyncTask.cancel(true);
            mGetArticlesAsyncTask = null;
        }
    }

    //
    // GetArticlesAsyncTask.Listener
    //

    @Override
    public void onArticles(List<Article> articles) {
        mArticlesAdapter.swapData(articles);
    }

    @Override
    public void onError(Exception e) {
        Log.e(TAG, e.getMessage());
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_title)
                .setMessage(R.string.error_message)
                .setPositiveButton(R.string.ok, null)
                .create()
                .show();
    }

    //
    // ArticlesAdapter.Listener
    //

    @Override
    public void onArticleClicked(Article article) {
        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtra(ArticleActivity.EXTRA_ARTICLE, article);
        startActivity(intent);
    }
}
