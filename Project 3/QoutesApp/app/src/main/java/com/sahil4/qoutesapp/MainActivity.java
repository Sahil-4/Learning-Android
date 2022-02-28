package com.sahil4.qoutesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    TextView quote, author;
    String Quote, Author;
    ImageButton shareButton;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quote = findViewById(R.id.quote);
        author = findViewById(R.id.author);
        shareButton = findViewById(R.id.share_button);
        refreshLayout = findViewById(R.id.refreshLayout);
        getNewQoute();
        refreshLayout.setOnRefreshListener(this::getNewQoute);
        shareButton.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/html");
            String str = Quote + "\n" + Author;
            intent.putExtra(Intent.EXTRA_TEXT, str);
            Intent chooser = Intent.createChooser(intent, "Share this qoute");
            startActivity(chooser);
        });
        getNewQoute();
    }

    public void getNewQoute() {
        refreshLayout.setRefreshing(true);
        // get qoute from api call
        String API_URL = "https://api.quotable.io/random";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API_URL, null, response -> {
            try {
                Quote = response.getString("content");
                Author = response.getString("author");
                showQoute();
            } catch (JSONException JSEx) {
                Toast.makeText(this, JSEx.toString(), Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show());
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public void showQoute() {
        // show qoute and author name on textview
        refreshLayout.setRefreshing(false);
        quote.setText(Quote);
        author.setText(Author);
    }
}