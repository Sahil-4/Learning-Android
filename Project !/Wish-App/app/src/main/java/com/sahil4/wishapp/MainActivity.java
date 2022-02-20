package com.sahil4.wishapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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

    public void askName() {
        Intent intent = new Intent(MainActivity.this, Ask_Name.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                getName();
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                getName();
            }
        }
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