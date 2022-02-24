package com.sahil4.memesharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONException;

import java.util.ArrayList;

public class ShowMeme extends AppCompatActivity {
    Button shareButton;
    ImageView nextButton, prevButton, Meme;
    ProgressBar progressBar;
    int Index = 0;
    ArrayList<String> MemeURLs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_meme);
        Meme = findViewById(R.id.meme_image);
        nextButton = findViewById(R.id.next_meme_button);
        prevButton = findViewById(R.id.prev_meme_button);
        shareButton = findViewById(R.id.share_button);
        progressBar = findViewById(R.id.progressBar);

        getMeme();

        nextButton.setOnClickListener(view -> {
            if (Index < MemeURLs.size()) {
                Index += 1;
                showMeme();
            } else {
                getMeme();
            }
        });

        prevButton.setOnClickListener(view -> {
            if (Index > 0) {
                Index -= 1;
                showMeme();
            }
        });

        shareButton.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Hey checkout this cool meme : " + MemeURLs.get(Index));
            Intent chooser = Intent.createChooser(intent, "Share using");
            startActivity(chooser);
        });
    }

    public void getMeme() {
        progressBar.setVisibility(View.VISIBLE);
        // Instantiate the RequestQueue.
        String API_URL = "https://meme-api.herokuapp.com/gimme";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, API_URL, null, response -> {
                    String url;
                    try {
                        url = response.getString("url");
                        MemeURLs.add(url);
                        Index = MemeURLs.size() - 1;
                        showMeme();
                    } catch (JSONException e) {
                        Toast.makeText(ShowMeme.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, error -> Toast.makeText(ShowMeme.this, error.toString(), Toast.LENGTH_SHORT).show());

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public void showMeme() {
        if (Index < MemeURLs.size()) {
            Glide.with(this).load(MemeURLs.get(Index)).into(Meme);
            progressBar.setVisibility(View.INVISIBLE);
        } else {
            getMeme();
        }
    }
}