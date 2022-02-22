package com.sahil4.memesharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView showMemes, credits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showMemes = findViewById(R.id.show_memes);
        credits = findViewById(R.id.credits);
        showMemes.setOnClickListener(view -> {
            Intent intent = new Intent(this, ShowMeme.class);
            startActivity(intent);
        });

        credits.setOnClickListener(view -> {
            Intent intent = new Intent(this, Credits.class);
            startActivity(intent);
        });
    }
}