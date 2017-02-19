package com.katic.factorynews;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.katic.factorynews.adapters.CustomPagerAdapter;

import java.util.ArrayList;

public class ArticleActivity extends AppCompatActivity {

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        final ArrayList<Article> articleList = getIntent().getParcelableArrayListExtra("articles");
        int startPos = getIntent().getIntExtra("position", 0);

        final ViewPager mPager = (ViewPager) findViewById(R.id.pager);

        final CustomPagerAdapter mPagerAdapter = new CustomPagerAdapter(this, articleList);
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
        /*if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }*/
        super.onBackPressed();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}
