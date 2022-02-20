package com.sahil4.wishapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    TextView nameView;
    public StringBuilder stringBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameView = findViewById(R.id.textView);
        getName();
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // There are no request codes
                    getName();
                }
            });

    public void askName() {
        Intent intent = new Intent(MainActivity.this, Ask_Name.class);
        someActivityResultLauncher.launch(intent);
    }

    public void getName() {
        try {
            FileInputStream fis = this.openFileInput("Name");
            InputStreamReader fsr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            stringBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(fsr);
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = reader.readLine();
            }
            String Name = stringBuilder.toString();
            showName(Name);
        } catch (IOException E) {
            askName();
        }
    }

    public void showName(String Name) {
        nameView.setText(getString(R.string.happy_birthday_dash, Name));
    }
}