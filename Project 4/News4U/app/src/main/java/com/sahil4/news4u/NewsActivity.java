package com.sahil4.news4u;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewsActivity extends AppCompatActivity implements NewsItemClick {
    RecyclerView recyclerView;
    ArrayList<NewsItem> newsArrayList = new ArrayList<>();
    NewsListAdaptor newsListAdaptor;
    String API_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Intent intent = getIntent();
        if (intent.getStringExtra("type").equals("top-headlines")) {
            API_URL = "https://newsapi.org/v2/top-headlines?language=en&country=in";
        } else {
            API_URL = "https://newsapi.org/v2/top-headlines?language=en&country=in&category=" + intent.getStringExtra("type");
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchNewsItems();
        newsListAdaptor = new NewsListAdaptor(newsArrayList, this);
        recyclerView.setAdapter(newsListAdaptor);
    }

    public void fetchNewsItems() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,
                        API_URL,
                        null,
                        response -> {
                            try {
                                JSONArray newsArray = response.getJSONArray("articles");
                                for (int i = 0; i < newsArray.length(); i++) {
                                    JSONObject jsonObject = newsArray.getJSONObject(i);
                                    NewsItem news = new NewsItem(jsonObject.getString("title"), jsonObject.getString("description"), jsonObject.getString("url"), jsonObject.getString("urlToImage"));
                                    // add news object in array
                                    newsArrayList.add(news);
                                    newsListAdaptor.addNewsItems(newsArrayList);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }, error -> Toast.makeText(NewsActivity.this, error.toString(), Toast.LENGTH_SHORT).show()) {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> customHeaders = new HashMap<>();
                customHeaders.put("User-Agent", "USER_CHROME");
                customHeaders.put("X-Api-Key", "7c3f7591c6d44184be916ad7801c9e5f");
                return customHeaders;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return super.getParams();
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void OnItemClicked(NewsItem item) {
        String url = item.url;
        Intent intent = new Intent(this, ReadNews.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}