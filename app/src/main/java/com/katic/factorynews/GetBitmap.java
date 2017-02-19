package com.katic.factorynews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class GetBitmap extends AsyncTask<ImageView, Void, Bitmap> {

    private ImageView imageView = null;
    private String url;

    public GetBitmap(String url) {
        this.url = url;
    }

    @Override
    protected Bitmap doInBackground(ImageView... imageViews) {
        this.imageView = imageViews[0];
        return downloadImage(url);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }

    private Bitmap downloadImage(String url) {
        Bitmap img = null;
        try {
            InputStream in = new URL(url).openStream();
            img = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
}
