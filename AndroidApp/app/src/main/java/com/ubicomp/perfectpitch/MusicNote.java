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

    public MusicNote(int id, Context context) {
        this.note = null;
        mediaPlayer = MediaPlayer.create(context, id);
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

    public Note getNote() {
        return this.note;
    }

    public Note getThird() {
        return this.note.getThird();
    }

    public Note getFifth() {
        return this.note.getFifth();
    }

    public void play() {
        this.mediaPlayer.start();
    }

    public void stop() {
        if (this.mediaPlayer.isPlaying())
        {
            this.mediaPlayer.pause();
        }
        this.mediaPlayer.seekTo(0);
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
