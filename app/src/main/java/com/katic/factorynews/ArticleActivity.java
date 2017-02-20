package com.katic.factorynews;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.katic.factorynews.adapters.CustomPagerAdapter;

import java.util.ArrayList;

public class ArticleActivity extends AppCompatActivity {

    CustomPagerAdapter mPagerAdapter;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        ArrayList<Article> articleList = getIntent().getParcelableArrayListExtra("articles");
        int startPos = getIntent().getIntExtra("position", 0);

        ViewPager mPager = (ViewPager) findViewById(R.id.pager);

        mPagerAdapter = new CustomPagerAdapter(this, articleList);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(startPos);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(mPagerAdapter.getPageTitle(startPos));

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setTitle(mPagerAdapter.getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}
