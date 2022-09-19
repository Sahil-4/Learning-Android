package com.sahil4.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddNote extends AppCompatActivity implements View.OnClickListener {
    ImageView backButton, saveButton, colorButton1, colorButton2, colorButton3, colorButton4, colorButton5, colorButton6, colorButton7, colorButton8;
    ConstraintLayout addNoteActivity;
    EditText title, description;
    Note note = null;
    String color = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        addNoteActivity = findViewById(R.id.add_note_activity);

        backButton = findViewById(R.id.back_button);
        saveButton = findViewById(R.id.save_button);

        title = findViewById(R.id.note_title);
        description = findViewById(R.id.note_description);

        colorButton1 = findViewById(R.id.color1);
        colorButton2 = findViewById(R.id.color2);
        colorButton3 = findViewById(R.id.color3);
        colorButton4 = findViewById(R.id.color4);
        colorButton5 = findViewById(R.id.color5);
        colorButton6 = findViewById(R.id.color6);
        colorButton7 = findViewById(R.id.color7);
        colorButton8 = findViewById(R.id.color8);

        colorButton1.setOnClickListener(this);
        colorButton2.setOnClickListener(this);
        colorButton3.setOnClickListener(this);
        colorButton4.setOnClickListener(this);
        colorButton5.setOnClickListener(this);
        colorButton6.setOnClickListener(this);
        colorButton7.setOnClickListener(this);
        colorButton8.setOnClickListener(this);

        color = Integer.toHexString(getResources().getColor(R.color.white));

        try {
            Intent intent1 = getIntent();
            note = (Note) intent1.getSerializableExtra("note");
            title.setText(note.getNoteTitle());
            description.setText(note.getNoteDescription());
            color = note.getNoteColor();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        backButton.setOnClickListener(view -> finish());

        saveButton.setOnClickListener(view -> {
            if (!title.getText().toString().equals("") && !description.getText().toString().equals("")) {
                Intent intent = new Intent();
                intent.putExtra("note_title", title.getText().toString());
                intent.putExtra("note_description", description.getText().toString());
                intent.putExtra("note_color", "#" + color);

                if (note == null) {
                    setResult(4, intent); // add new note
                } else {
                    intent.putExtra("note", note);
                    setResult(6, intent); // update a note
                }

                finish();
            } else {
                Toast.makeText(this, "Please enter proper Title and Description", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setColor(int resource) {
        addNoteActivity.setBackgroundColor(resource);
        color = Integer.toHexString(resource);
        getWindow().setStatusBarColor(resource);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(@NonNull View view) {
        switch (view.getId()) {
            // change bg color of activity
            case R.id.color1:
                Toast.makeText(this, "button 1", Toast.LENGTH_SHORT).show();
                setColor(getResources().getColor(R.color.white));
                break;

            case R.id.color2:
                Toast.makeText(this, "button 2", Toast.LENGTH_SHORT).show();
                setColor(getResources().getColor(R.color.aqua));
                break;

            case R.id.color3:
                Toast.makeText(this, "button 3", Toast.LENGTH_SHORT).show();
                setColor(getResources().getColor(R.color.crimson));
                break;

            case R.id.color4:
                Toast.makeText(this, "button 4", Toast.LENGTH_SHORT).show();
                setColor(getResources().getColor(R.color.bitter_sweat));
                break;

            case R.id.color5:
                Toast.makeText(this, "button 5", Toast.LENGTH_SHORT).show();
                setColor(getResources().getColor(R.color.celadon));
                break;

            case R.id.color6:
                Toast.makeText(this, "button 6", Toast.LENGTH_SHORT).show();
                setColor(getResources().getColor(R.color.lavender));
                break;

            case R.id.color7:
                Toast.makeText(this, "button 7", Toast.LENGTH_SHORT).show();
                setColor(getResources().getColor(R.color.linen));
                break;

            case R.id.color8:
                Toast.makeText(this, "button 8", Toast.LENGTH_SHORT).show();
                setColor(getResources().getColor(R.color.scarlet));
                break;
        }
    }
}