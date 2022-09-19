package com.sahil4.notesapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class NotesAdaptor extends RecyclerView.Adapter<NotesViewHolder> {
    ArrayList<Note> allNotes = new ArrayList<>();
    public Boolean selectionMode = false;
    ArrayList<Note> selectedNotes = new ArrayList<>();
    Context context;
    OnClickEvent onClick;

    NotesAdaptor(Context ctx, OnClickEvent listener) {
        this.context = ctx;
        this.onClick = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NotesViewHolder viewHolder = new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.note_item, parent, false));
        viewHolder.itemView.setOnClickListener(view -> onClick.handleOnclick(allNotes.get(viewHolder.getAdapterPosition())));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note currentNote = allNotes.get(position);
        holder.noteTitle.setText(currentNote.getNoteTitle());
        holder.noteDescription.setText(currentNote.getNoteDescription());
        if (selectedNotes.contains(currentNote)) {
            holder.itemView.setBackgroundResource(R.drawable.rounded_border_active);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.rounded_border_inactive);
        }
        holder.itemView.getBackground().setColorFilter(Color.parseColor(currentNote.getNoteColor()), PorterDuff.Mode.ADD);
    }

    @Override
    public int getItemCount() {
        return allNotes.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void submitList(List<Note> notes) {
        allNotes = (ArrayList<Note>) notes;
        notifyDataSetChanged();
    }
}

class NotesViewHolder extends RecyclerView.ViewHolder {
    TextView noteTitle;
    TextView noteDescription;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        noteTitle = itemView.findViewById(R.id.note_title);
        noteDescription = itemView.findViewById(R.id.note_description);
    }
}

interface OnClickEvent {
    void handleOnclick(Note note);

    void handleDelete(Note note);
}