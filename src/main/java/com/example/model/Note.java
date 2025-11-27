package com.example.model;

public class Note {
    private int noteId;
    private int inscriptionId;
    private float note;

    public Note() {}

    // Getters and Setters
    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getInscriptionId() {
        return inscriptionId;
    }

    public void setInscriptionId(int inscriptionId) {
        this.inscriptionId = inscriptionId;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", inscriptionId=" + inscriptionId +
                ", note=" + note +
                '}';
    }
}
