package com.katic.factorynews.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.katic.factorynews.models.Article;
import com.katic.factorynews.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    public interface Listener {

        void onArticleClicked(Article article);

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image) ImageView mImage;
        @BindView(R.id.title) TextView mText;

        private Context mContext;

        ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        private void bind(int position) {
            Article article = mArticles.get(position);
            mText.setText(article.title);
            Glide.with(mContext)
                    .load(article.urlToImage)
                    .into(mImage);
        }

        @OnClick(R.id.layout) void onArticleClicked() {
            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                mListener.onArticleClicked(mArticles.get(getAdapterPosition()));
            }
        }

    }

    private Listener mListener;
    private List<Article> mArticles;

    public ArticlesAdapter(Listener listener){
        this.mListener = listener;
    }

    public void swapData(List<Article> articles) {
        mArticles = articles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mArticles == null ? 0 : mArticles.size();
    }

}
