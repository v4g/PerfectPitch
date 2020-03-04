package com.ubicomp.perfectpitch;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;

public class MusicNote {

    enum Note {
        A3(Color.MAGENTA, R.raw.a3),
        B3(Color.WHITE, R.raw.b3),
        C3(Color.BLUE, R.raw.c3),
        D3(Color.CYAN, R.raw.d3),
        E3(Color.GREEN, R.raw.e3),
        F3(Color.YELLOW, R.raw.f3),
        G3(Color.RED, R.raw.g3);

        private int color;
        private int id;

        private Note(int color, int id) {
            this.color = color;
            this.id = id;
        }

        public int getColor() {
            return this.color;
        }

        public int getID() {
            return this.id;
        }
    }

    private Note note;
    private MediaPlayer mediaPlayer;

    public MusicNote(String noteName, Context context) {
        this.note = Note.valueOf(noteName.toUpperCase());
        mediaPlayer = MediaPlayer.create(context, this.note.getID());
    }

    public int getColor() {
        return this.note.getColor();
    }

    public void play() {
        this.mediaPlayer.start();
    }
}
