package com.katic.factorynews.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.katic.factorynews.Article;
import com.katic.factorynews.ArticleActivity;
import com.katic.factorynews.GetBitmap;
import com.katic.factorynews.R;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ArticleViewHolder> {

    class ArticleViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        TextView itemText;
        LinearLayout linearLayout;

        ArticleViewHolder(final View itemView) {
            super(itemView);
            itemImage = (ImageView) itemView.findViewById(R.id.itemImg);
            itemText = (TextView) itemView.findViewById(R.id.itemText);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.layout);

            Shader myShader = new LinearGradient(
                    0, 0, 0, itemText.getLineHeight()*2,
                    Color.BLACK, Color.GRAY,
                    Shader.TileMode.CLAMP );
            itemText.getPaint().setShader( myShader );

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), ArticleActivity.class);
                    intent.putParcelableArrayListExtra("articles", articles);
                    intent.putExtra("position", getAdapterPosition());
                itemView.getContext().startActivity(intent);
                }
            });
        }

    }

    private static ArrayList<Article> articles;

    public RVAdapter(ArrayList<Article> articles){
        this.articles = articles;
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        ArticleViewHolder avh = new ArticleViewHolder(v);
        return avh;
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        new GetBitmap(articles.get(position).getUrlToImage()).execute(holder.itemImage);
        holder.itemText.setText(articles.get(position).getTitle());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
