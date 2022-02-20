package com.sahil4.wishapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

public class Ask_Name extends AppCompatActivity {
    EditText name;
    Button go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_name);
        name = findViewById(R.id.personName);
        go = findViewById(R.id.go);
        go.setOnClickListener(view -> {
            String Name = name.getText().toString();
            saveName(Name);
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    public void saveName(String Name) {
        try {
            File file = File.createTempFile("Name", ".txt", getCacheDir());
            if (file.canWrite()) {
                try (FileOutputStream fos = this.openFileOutput("Name", MODE_PRIVATE)) {
                    fos.write(Name.getBytes(StandardCharsets.UTF_8));
                }
            }
        } catch (Exception E) {
            Toast.makeText(this, E.toString(), Toast.LENGTH_LONG).show();
        }
    }

}