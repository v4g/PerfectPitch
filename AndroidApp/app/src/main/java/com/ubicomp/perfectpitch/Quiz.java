package com.ubicomp.perfectpitch;

import android.content.Context;
import android.graphics.Color;

public class Quiz {

    private MusicNote[] noteOrder;  // the notes for each question of the quiz
    private int[] colorChoices;  // the colors that will be available as answer choices

    public Quiz(Note[] notes, int[] colorChoices, Context context) {
        noteOrder = new MusicNote[notes.length];
        for (int i = 0; i < notes.length; i++) {
            noteOrder[i] = new MusicNote(notes[i], context);
        }
        this.colorChoices = colorChoices;
    }

    public Quiz(String[] notes, int[] colorChoices, Context context) {
        noteOrder = new MusicNote[notes.length];
        for (int i = 0; i < notes.length; i++) {
            noteOrder[i] = new MusicNote(notes[i], context);
        }
        this.colorChoices = colorChoices;
    }

    public Quiz(Note[] notes, Context context) {
        this(notes, new int[]{Color.WHITE, Color.MAGENTA, Color.RED,
                Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE}, context);
    }

    public Quiz(String[] notes, Context context) {
        this(notes, new int[]{Color.WHITE, Color.MAGENTA, Color.RED,
                Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE}, context);
    }

    public MusicNote[] getNotes() {
        return noteOrder;
    }

    public int[] getColorChoices() {
        return colorChoices;
    }

    public int getLength() {
        return noteOrder.length;
    }

    public int getNumChoices() {
        return colorChoices.length;
    }

    // Given the colors selected for each quiz question, calculate the score
    public int getScore(int[] answers) {
        if (answers.length != noteOrder.length) {
            throw new IllegalArgumentException("Length of quiz answers not equal to length of quiz questions");
        }
        int correct = 0;
        for (int i = 0; i < answers.length; i++) {
            if (answers[i] == noteOrder[i].getColor()){
                correct++;
            }
        }
        return (int) ((((double) correct) / answers.length) * 100);
    }
}
