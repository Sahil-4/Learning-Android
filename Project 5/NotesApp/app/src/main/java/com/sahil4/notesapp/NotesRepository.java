package com.sahil4.notesapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class NoteRepository {
    private final NotesDAO notesDAO;
    private final LiveData<List<Note>> allNotes;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    NoteRepository(Application application) {
        NotesDatabase db = NotesDatabase.getDatabase(application);
        notesDAO = db.notesDAO();
        allNotes = notesDAO.getAllNotes();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Note>> getAllWords() {
        return allNotes;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Note note) {
        NotesDatabase.databaseWriteExecutor.execute(() -> notesDAO.insert(note));
    }

    void update(String title, String description, int Id) {
        NotesDatabase.databaseWriteExecutor.execute(() -> notesDAO.update(title, description, Id));
    }

    void deleteNote(Note note) {
        NotesDatabase.databaseWriteExecutor.execute(() -> notesDAO.deleteNote(note));
    }
}