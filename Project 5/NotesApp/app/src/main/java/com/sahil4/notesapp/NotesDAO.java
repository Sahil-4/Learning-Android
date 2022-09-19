package com.sahil4.notesapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

// This is Note DAO Data Access Object
@Dao
public interface NotesDAO {
    @Insert
    void insert(Note note);

    @Query("DELETE FROM notes_table")
    void deleteAll();

    @Delete
    void deleteNote(Note note);

    @Query("UPDATE notes_table SET description = :description, title= :title, color= :color WHERE id =:id")
    void update(String title, String description, String color, int id);

    @Query("SELECT * FROM notes_table order by id DESC")
    LiveData<List<Note>> getAllNotes();
    // Later in this codelab, you track data changes via an Observer in MainActivity
}