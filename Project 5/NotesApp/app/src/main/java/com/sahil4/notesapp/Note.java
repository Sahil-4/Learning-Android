package com.sahil4.notesapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

// This is Note Entity
@Entity(tableName = "notes_table")
public class Note implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "title")
    private String noteTitle;

    @ColumnInfo(name = "description")
    private String noteDescription;

    @ColumnInfo(name = "color")
    private String noteColor;

    public Note(String title, String description) {
        this.noteTitle = title;
        this.noteDescription = description;
    }

    public Note(String title, String description, String color) {
        this.noteTitle = title;
        this.noteDescription = description;
        this.noteColor = color;
    }

    public Note() {
        this.noteTitle = null;
        this.noteDescription = null;
        this.noteColor = null;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteTitle() {
        return this.noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return this.noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public String getNoteColor() {
        return noteColor;
    }

    public void setNoteColor(String noteColor) {
        this.noteColor = noteColor;
    }

}