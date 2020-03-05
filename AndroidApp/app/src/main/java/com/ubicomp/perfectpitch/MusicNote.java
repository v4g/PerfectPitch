package com.ubicomp.perfectpitch;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;

public class MusicNote {

    private Note note;
    private MediaPlayer mediaPlayer;

    public MusicNote(String noteName, Context context) {
        this.note = Note.valueOf(noteName.toUpperCase());
        mediaPlayer = MediaPlayer.create(context, this.note.getID());
    }

    public MusicNote(Note note, Context context) {
        this.note = note;
        mediaPlayer = MediaPlayer.create(context, this.note.getID());
    }

    public int getColor() {
        return this.note.getColor();
    }

    public int getID() {
        return this.note.getID();
    }

    public String getName() {
        return this.note.name();
    }

    public void play() {
        this.mediaPlayer.start();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MusicNote)) {
            return false;
        }
        MusicNote other = (MusicNote) o;
        return this.getName().equals(other.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

}
