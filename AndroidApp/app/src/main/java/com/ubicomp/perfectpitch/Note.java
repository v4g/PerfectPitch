package com.ubicomp.perfectpitch;

import android.graphics.Color;

public enum Note {
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
