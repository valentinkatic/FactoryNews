package com.katic.factorynews.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.katic.factorynews.Article;
import com.katic.factorynews.GetBitmap;
import com.katic.factorynews.R;

import java.util.ArrayList;

public class CustomPagerAdapter extends PagerAdapter {

    private LayoutInflater mLayoutInflater;
    private ArrayList<Article> articleList;

    public CustomPagerAdapter(Context context, ArrayList<Article> articleList) {
        mLayoutInflater = LayoutInflater.from(context);
        this.articleList = articleList;
    }

    @Override
    public int getCount() {
        return articleList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.article_view, container, false);

        ImageView articleImg = (ImageView) itemView.findViewById(R.id.articleImg);
        TextView articleTitle = (TextView) itemView.findViewById(R.id.articleTitle);
        TextView articleDescription = (TextView) itemView.findViewById(R.id.articleDescription);

        Article current = articleList.get(position);

        new GetBitmap(current.urlToImage).execute(articleImg);
        articleTitle.setText(current.title);
        articleDescription.setText(current.description);

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return articleList.get(position).title;
    }
}
