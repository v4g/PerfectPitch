package com.ubicomp.perfectpitch;

import android.content.Context;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Quiz {

    private MusicNote[] noteOrder;  // the notes for each question of the quiz, in order
    private Set<MusicNote> notesTested;  // the set of individual notes that are being tested
    private int[] colorChoices;  // the colors that will be available as answer choices
    private Context context;

    // Quiz constructor where you list all the notes in the order of the desired questions
    public Quiz(Context context, Note[] notes, int[] colorChoices) {
        noteOrder = new MusicNote[notes.length];
        for (int i = 0; i < notes.length; i++) {
            noteOrder[i] = new MusicNote(notes[i], context);
        }
        this.colorChoices = colorChoices;
        // TODO: this is wrong and assumes all entered notes are being tested
        this.notesTested = new HashSet<>(Arrays.asList(noteOrder));
        this.context = context;
    }

    // Quiz constructor where you list all the note names in the order of the desired questions
    public Quiz(Context context, String[] notes, int[] colorChoices) {
        noteOrder = new MusicNote[notes.length];
        for (int i = 0; i < notes.length; i++) {
            noteOrder[i] = new MusicNote(notes[i], context);
        }
        this.colorChoices = colorChoices;
        // TODO: this is wrong and assumes all entered notes are being tested
        this.notesTested = new HashSet<>(Arrays.asList(noteOrder));
        this.context = context;
    }

    public Quiz(Context context, Note[] notes) {
        this(context, notes, new int[]{Color.WHITE, Color.MAGENTA, Color.RED,
                Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE});
    }

    public Quiz(Context context, String[] notes) {
        this(context, notes, new int[]{Color.WHITE, Color.MAGENTA, Color.RED,
                Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE});
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

    public Set<MusicNote> getNotesTested() {
        return notesTested;
    }

    public int getNumNotesTested() {
        return notesTested.size();
    }

    public Context getContext() {
        return context;
    }

    // Given the colors selected for each quiz question, calculate the score
    public int getScore(int[] answers) {
        if (answers.length != noteOrder.length) {
            throw new IllegalArgumentException("Length of quiz answers not equal to length of quiz questions");
        }
        int correct = 0;
        for (int i = 0; i < answers.length; i++) {
            // if the note is being tested and the color is correct, or if it is not being tested and the color is none:
            if ((notesTested.contains(noteOrder[i]) && (answers[i] == noteOrder[i].getColor()))
                    || (!notesTested.contains(noteOrder[i]) && (answers[i] == Color.TRANSPARENT))) {
                correct++;
            }
        }
        return (int) ((((double) correct) / answers.length) * 100);
    }
}
