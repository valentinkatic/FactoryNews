package com.katic.factorynews;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.katic.factorynews.models.Article;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ArticleActivity extends AppCompatActivity {

    public static final String TAG = ArticleActivity.class.getSimpleName();
    public static final String EXTRA_ARTICLE = "EXTRA_ARTICLE";

    @BindView(R.id.image) ImageView mImage;
    @BindView(R.id.title) TextView mTitle;
    @BindView(R.id.description) TextView mDescription;

    private Unbinder mUnBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        mUnBinder = ButterKnife.bind(this);

        Article article = getIntent().getParcelableExtra(EXTRA_ARTICLE);
        if (article == null) {
            finish();
            return;
        }

        setArticleDetails(article);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(article.title);
        }
    }

    private void setArticleDetails(Article article) {
        mTitle.setText(article.title);
        mDescription.setText(article.description);
        Glide.with(this)
                .load(article.urlToImage)
                .into(mImage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return false;
        }
    }
}
