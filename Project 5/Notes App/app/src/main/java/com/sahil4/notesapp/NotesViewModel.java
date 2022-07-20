package com.sahil4.notesapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    private final NoteRepository repository;
    private final LiveData<List<Note>> allNotes;

    public NotesViewModel(Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllWords();
    }

    LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    void insert(Note word) {
        repository.insert(word);
    }

    void update(String title, String description, int id) {
        repository.update(title, description, id);
    }

    void deleteNote(Note note) {
        repository.deleteNote(note);
    }
}
