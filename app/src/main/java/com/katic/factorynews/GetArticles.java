package com.katic.factorynews;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.katic.factorynews.adapters.RVAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class GetArticles extends AsyncTask<RecyclerView, Void, Boolean> {

    private ProgressDialog pDialog;
    private String url;

    private Context context;
    private String TAG = GetArticles.class.getSimpleName();

    private RecyclerView rv = null;
    private ArrayList<Article> articleList = new ArrayList<>();

    GetArticles(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pDialog = new ProgressDialog(context);
        pDialog.setMessage(context.getText(R.string.wait));
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected Boolean doInBackground(RecyclerView... arg0) {
        this.rv = arg0[0];

        HttpHandler sh = new HttpHandler();

        SharedPreferences prefs = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        String dateTime = "dateTime";
        String apiStr = "apiStr";
        Long time = prefs.getLong(dateTime, 0);
        String jsonStr;
        Long currentTime = System.currentTimeMillis();

        if (currentTime - time > 1000*60*5) {
            jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                articleList = getData(jsonStr);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putLong(dateTime, System.currentTimeMillis());
                editor.putString(apiStr, jsonStr);
                editor.apply();
            }
            return true;
        } else {
            jsonStr = prefs.getString(apiStr, "");
            Log.e(TAG, "Response from SharedPreferences: "+ jsonStr);

            articleList = getData(jsonStr);

            return true;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        if (pDialog.isShowing())
            pDialog.dismiss();

        if(result) {

            RVAdapter adapter = new RVAdapter(articleList);
            rv.setAdapter(adapter);

        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.error_message)
                    .setTitle(R.string.error)
                    .setPositiveButton(R.string.ok, null);

            AlertDialog dialog = builder.create();
            dialog.show();

        }
    }

    private ArrayList<Article> getData(String jsonStr) {
        ArrayList<Article> articleList = new ArrayList<>();

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            JSONArray articles = jsonObj.getJSONArray("articles");

            for (int i = 0; i < articles.length(); i++) {

                JSONObject a = articles.getJSONObject(i);

                String title = a.getString("title");
                String description = a.getString("description");
                String urlToImage = a.getString("urlToImage");

                Article article = new Article(title, description, urlToImage);

                articleList.add(article);
            }
        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }

        return articleList;
    }

}

