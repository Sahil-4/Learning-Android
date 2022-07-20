package com.sahil4.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Objects;

public class AddNote extends AppCompatActivity {
    ImageView backButton, saveButton;
    EditText title, description;
    Note note = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        backButton = findViewById(R.id.back_button);
        saveButton = findViewById(R.id.save_button);
        title = findViewById(R.id.note_title);
        description = findViewById(R.id.note_description);

        try {
            Intent intent1 = getIntent();
            note = (Note) intent1.getSerializableExtra("note");
            title.setText(note.getNoteTitle());
            description.setText(note.getNoteDescription());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        backButton.setOnClickListener(view -> finish());

        saveButton.setOnClickListener(view -> {
            String note_title = title.getText().toString();
            String note_description = description.getText().toString();

            if (!note_title.equals("") && !note_description.equals("")) {
                Intent intent = new Intent();
                intent.putExtra("note_title", note_title);
                intent.putExtra("note_description", note_description);
                if (note == null) {
                    setResult(4, intent);
                } else {
                    intent.putExtra("note", note);
                    setResult(6, intent);
                }
                finish();
            } else {
                Toast.makeText(this, "Please enter proper Title and Description", Toast.LENGTH_SHORT).show();
            }
        });
    }
}