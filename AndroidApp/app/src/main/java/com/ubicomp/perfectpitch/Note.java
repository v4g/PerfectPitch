package com.ubicomp.perfectpitch;

import android.graphics.Color;

public enum Note {
    C3(Color.BLUE, R.raw.c3t),
    D3(Color.CYAN, R.raw.d3t),
    E3(Color.GREEN, R.raw.e3t),
    F3(Color.YELLOW, R.raw.f3t),
    G3(Color.RED, R.raw.g3t),
    A3(Color.MAGENTA, R.raw.a3t),
    B3(Color.WHITE, R.raw.b3t);

    private int color;
    private int id;
    private static Note[] vals = values();

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

    public Note getNext() {
        return vals[(this.ordinal() + 1) % vals.length];
    }

    public Note getThird() {
        return vals[(this.ordinal() + 2) % vals.length];
    }

    public Note getFifth() {
        return vals[(this.ordinal() + 4) % vals.length];
    }
}
