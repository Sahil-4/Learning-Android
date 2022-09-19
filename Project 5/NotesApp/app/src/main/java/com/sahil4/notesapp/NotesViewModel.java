package com.sahil4.notesapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {
    private final NotesRepository repository;
    private final LiveData<List<Note>> allNotes;

    public NotesViewModel(Application application) {
        super(application);
        repository = new NotesRepository(application);
        allNotes = repository.getAllWords();
    }

    LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    void insert(Note word) {
        repository.insert(word);
    }

    void update(String title, String description, String color, int id) {
        repository.update(title, description, color, id);
    }

    void deleteNote(Note note) {
        repository.deleteNote(note);
    }
}