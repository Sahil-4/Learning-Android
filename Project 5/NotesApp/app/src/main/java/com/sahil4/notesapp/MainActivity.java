package com.sahil4.notesapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnClickEvent {
    NotesViewModel notesViewModel;
    RecyclerView recyclerView;
    FloatingActionButton addNewNoteButton;
    NotesAdaptor adapter;
    MaterialToolbar toolbar;
    MaterialToolbar toolbar2;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.toolbar);
        toolbar2 = findViewById(R.id.toolbar2);

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getTitle().equals("Edit Notes")) { // enable selection
                adapter.selectionMode = true;
                toolbar.setVisibility(View.GONE);
                toolbar2.setVisibility(View.VISIBLE);
                addNewNoteButton.setVisibility(View.GONE);
            } else if (item.getTitle().equals("Toggle Dark Mode")) { // Enable dark mode !Need Fixes
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }

            return true;
        });

        toolbar2.setOnClickListener(view -> {
            adapter.selectedNotes.clear();
            adapter.notifyDataSetChanged();

            // wrap in a function - public static void openMenu(int n) { }
            adapter.selectionMode = false;
            toolbar.setVisibility(View.VISIBLE);
            toolbar2.setVisibility(View.GONE);
            addNewNoteButton.setVisibility(View.VISIBLE);
        });

        toolbar2.setOnMenuItemClickListener(item -> {
            if (item.getTitle().equals("Delete")) {
                Toast.makeText(this, "Deleting...", Toast.LENGTH_SHORT).show();
                // get all deletable notes and delete them one by one
                ArrayList<Note> allDeletableNotes = adapter.selectedNotes;
                for (int i = 0; i < allDeletableNotes.size(); i++) {
                    handleDelete(allDeletableNotes.get(i));
                }

                toolbar.setVisibility(View.VISIBLE);
                toolbar2.setVisibility(View.GONE);
                addNewNoteButton.setVisibility(View.VISIBLE);
            }

            return false;
        });

        addNewNoteButton = findViewById(R.id.add_new_note_button);
        addNewNoteButton.setOnClickListener(view -> mStartForResult.launch(new Intent(this, AddNote.class)));

        adapter = new NotesAdaptor(this, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        notesViewModel.getAllNotes().observe(this, adapter::submitList);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == 4) {
                // add a new note
                assert result.getData() != null;
                String title = result.getData().getStringExtra("note_title");
                String description = result.getData().getStringExtra("note_description");
                String color = result.getData().getStringExtra("note_color");
                Note note = new Note(title, description, color);
                notesViewModel.insert(note);
                Toast.makeText(MainActivity.this, "Note saved", Toast.LENGTH_SHORT).show();
            } else if (result.getResultCode() == -1) {
                // delete note
                assert result.getData() != null;
                Note note = (Note) result.getData().getSerializableExtra("note");
                handleDelete(note);
            } else if (result.getResultCode() == 3) {
                // edit note TODO
                assert result.getData() != null;
                Note note = (Note) result.getData().getSerializableExtra("note");
                Intent intent = new Intent(MainActivity.this, AddNote.class);
                intent.putExtra("note", note);
                mStartForResult.launch(intent);
            } else if (result.getResultCode() == 6) {
                // update note
                assert result.getData() != null;
                Note note = (Note) result.getData().getSerializableExtra("note");
                String title = result.getData().getStringExtra("note_title");
                String description = result.getData().getStringExtra("note_description");
                String color = result.getData().getStringExtra("note_color");
                notesViewModel.update(title, description, color, note.getId());
            }
        }
    });

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            handleDelete(adapter.allNotes.get(viewHolder.getAdapterPosition()));
            adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        }
    };

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void handleOnclick(Note note) {
        if (adapter.selectionMode) {
            // select Note Item
            if (adapter.selectedNotes.contains(note)) {
                adapter.selectedNotes.remove(note);
            } else {
                adapter.selectedNotes.add(note);
            }
            adapter.notifyDataSetChanged();
        } else {
            Intent intent = new Intent(MainActivity.this, ViewNote.class);
            intent.putExtra("note", note);
            mStartForResult.launch(intent);
        }
    }

    @Override
    public void handleDelete(Note note) {
        notesViewModel.deleteNote(note);
        Snackbar.make(recyclerView, "Deleting...", BaseTransientBottomBar.LENGTH_SHORT).setAction("Cancel", view -> notesViewModel.insert(note)).show();
    }
}
