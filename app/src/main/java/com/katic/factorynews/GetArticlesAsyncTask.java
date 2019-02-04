package com.katic.factorynews;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.katic.factorynews.models.ApiResponse;
import com.katic.factorynews.models.Article;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GetArticlesAsyncTask extends AsyncTask<Void, Void, Void> {

    private static final String TAG = GetArticlesAsyncTask.class.getSimpleName();
    private static final String URL = "https://newsapi.org/v1/articles?apiKey=6946d0c07a1c4555a4186bfcade76398&sortBy=top&source=bbc-news";

    public interface Listener {

        void onArticles(List<Article> articles);

        void onError(Exception e);

    }

    private Listener listener;
    private List<Article> articles;
    private Exception exception;

    public GetArticlesAsyncTask(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL)
                .build();

        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String responseString = responseBody.string();
                Log.d(TAG, "Response: " + responseString);

                Gson gson = new Gson();
                ApiResponse apiResponse = gson.fromJson(responseString, ApiResponse.class);

                if (apiResponse.status.equals("ok")) {
                    articles = apiResponse.articles;
                } else {
                    throw new Exception(apiResponse.message);
                }
            }
        } catch (Exception e) {
            exception = e;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (exception != null) {
            listener.onError(exception);
            return;
        }
        if (articles == null || articles.isEmpty()) {
            listener.onError(new Exception("Articles are null or empty"));
            return;
        }
        listener.onArticles(articles);
    }
}
