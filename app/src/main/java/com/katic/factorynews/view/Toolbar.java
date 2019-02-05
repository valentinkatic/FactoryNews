package com.katic.factorynews.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.katic.factorynews.R;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Toolbar extends androidx.appcompat.widget.Toolbar {

    public interface Listener {

        void onBackClicked();

    }

    @BindView(R.id.toolbar_back_button) View mBackButton;
    @BindView(R.id.toolbar_title) TextView mTitle;

    private Listener mListener;

    public Toolbar(Context context) {
        super(context);
        init(null);
    }

    public Toolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public Toolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        inflate(getContext(), R.layout.toolbar, this);
        ButterKnife.bind(this);
        if (isInEditMode()) {
            mTitle.setText(R.string.app_name);
        }
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.Toolbar,
                    0,
                    0
            );
            String title = a.getString(R.styleable.Toolbar_title);
            mTitle.setText(title == null || title.trim().isEmpty() ? getContext().getString(R.string.app_name) : title);
            mBackButton.setVisibility(a.getBoolean(R.styleable.Toolbar_backNavigation, false) ? VISIBLE : GONE);
        }
        setContentInsetStartWithNavigation(0);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    @OnClick(R.id.toolbar_back_button) void onBackClicked() {
        if (mListener != null) {
            mListener.onBackClicked();
        }
    }

    @Override
    public int getContentInsetStart() {
        return 0;
    }
}
