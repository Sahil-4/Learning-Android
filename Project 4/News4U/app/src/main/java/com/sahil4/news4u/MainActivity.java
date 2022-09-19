package com.sahil4.news4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView top_headlines, business, science, entertainment, technology, health, sports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        top_headlines = findViewById(R.id.top_headlines);
        business = findViewById(R.id.business);
        science = findViewById(R.id.science);
        entertainment = findViewById(R.id.entertainment);
        technology = findViewById(R.id.technology);
        health = findViewById(R.id.health);
        sports = findViewById(R.id.sports);

        top_headlines.setOnClickListener(view -> goToNewsPage("top-headlines"));
        business.setOnClickListener(view -> goToNewsPage("business"));
        science.setOnClickListener(view -> goToNewsPage("science"));
        entertainment.setOnClickListener(view -> goToNewsPage("entertainment"));
        technology.setOnClickListener(view -> goToNewsPage("technology"));
        health.setOnClickListener(view -> goToNewsPage("health"));
        sports.setOnClickListener(view -> goToNewsPage("sports"));
    }

    public void goToNewsPage(String type) {
        Intent intent = new Intent(this, NewsActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

}