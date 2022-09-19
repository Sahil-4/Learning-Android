package com.sahil4.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewNote extends AppCompatActivity {
    ConstraintLayout viewNoteLayout;
    TextView noteTitle, noteDescription;
    Button updateButton, deleteButton, shareNote;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        viewNoteLayout = findViewById(R.id.view_note_layout);

        backButton = findViewById(R.id.back_button);

        noteTitle = findViewById(R.id.note_title);
        noteDescription = findViewById(R.id.note_description);

        updateButton = findViewById(R.id.edit_note);
        shareNote = findViewById(R.id.share_note);
        deleteButton = findViewById(R.id.delete_note);

        Intent intent = getIntent();
        Note note = (Note) intent.getSerializableExtra("note");

        noteTitle.setText(note.getNoteTitle());
        noteDescription.setText(note.getNoteDescription());
        viewNoteLayout.setBackgroundColor(Color.parseColor(note.getNoteColor()));

        backButton.setOnClickListener(view -> finish());

        updateButton.setOnClickListener(view -> {
            Intent intent1 = new Intent();
            intent1.putExtra("note", note);
            setResult(3, intent1);
            finish();
        });

        shareNote.setOnClickListener(view -> {
            Intent intent1 = new Intent(Intent.ACTION_SEND);
            intent1.setType("text/plain");
            String noteTitle = note.getNoteTitle();
            String noteDescription = note.getNoteDescription();
            intent1.putExtra(Intent.EXTRA_TEXT, noteTitle + "\n\n" + noteDescription);
            startActivity(Intent.createChooser(intent1, "Share Note"));
        });

        deleteButton.setOnClickListener(view -> {
            Intent intent1 = new Intent();
            intent1.putExtra("note", note);
            setResult(-1, intent1);
            finish();
        });
    }
}