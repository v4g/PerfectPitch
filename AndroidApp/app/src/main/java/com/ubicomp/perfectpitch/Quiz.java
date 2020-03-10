package com.ubicomp.perfectpitch;

import android.content.Context;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Quiz {

    private MusicNote[] noteOrder;  // the notes for each question of the quiz, in order
    private List<Note> notesTested;  // the set of individual notes that are being tested,
                                     // minus any extras added to avoid too much repetition
    private int[] colorChoices;  // the colors that will be available as answer choices
    private Context context;  // application context where the quiz takes place and notes are played
    private int NUM_QUESTIONS = 10;  // number of questions in each quiz

    // Quiz constructor that takes an array of Notes and creates a NUM_QUESTIONS length quiz
    public Quiz(Context context, Note[] notes) {
        this.context = context;
        createQuestions(notes);
    }

    // Quiz constructor that takes an array of note names and creates a NUM_QUESTIONS length quiz
    public Quiz(Context context, String[] notes) {
        this.context = context;
        createQuestions(notes);
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

    public List<Note> getNotesTested() {
        return notesTested;
    }

    public int getNumNotesTested() {
        return notesTested.size();
    }

    public Context getContext() {
        return context;
    }

    public int getQuizLength() {
        return NUM_QUESTIONS;
    }

    // Given the colors selected for each quiz question, calculate the score
    public int getScore(int[] answers) {
        if (answers.length != noteOrder.length) {
            throw new IllegalArgumentException("Length of quiz answers not equal to length of quiz questions");
        }
        int correct = 0;
        for (int i = 0; i < answers.length; i++) {
            // if the note is being tested and the color is correct, or if it is not being tested and the color is none:
            if ((notesTested.contains(noteOrder[i].getNote()) && (answers[i] == noteOrder[i].getColor()))
                    || (!notesTested.contains(noteOrder[i].getNote()) && (answers[i] == Color.TRANSPARENT))) {
                correct++;
            }
        }
        return (int) ((((double) correct) / answers.length) * 100);
    }

    private void createQuestions(Note[] notes) {
        // create list containing just the Notes the user is interested in testing (no repetitions)
        this.notesTested = new ArrayList<>();
        for (Note n: notes) {
            if (!notesTested.contains(n)) {
                notesTested.add(n);
            }
        }

        // create array to hold note order for questions
        noteOrder = new MusicNote[NUM_QUESTIONS];
        // list of the notes to be used for questions (if # notesTested > 2, same as notesTested)
        List<Note> questionChoices = new ArrayList<>(notesTested);

        // add additional notes for small test cases
        // makes number of unique notes in a quiz always at least 3
        if(this.notesTested.size() < 1) {
            throw new IllegalArgumentException("Must select at least one note for quiz");
        } else if (this.notesTested.size() == 1) {  // add the 3rd and 5th unless already included
            Note n = notesTested.get(0);
            Note new1 = n.getThird();
            while (questionChoices.contains(new1)) {  // make sure this note isn't already included
                new1 = new1.getNext();
            }
            questionChoices.add(new1);
            Note new2 = new1.getThird();
            while (questionChoices.contains(new2)) {  // make sure this note isn't already included
                new2 = new2.getNext();
            }
            questionChoices.add(new2);
        } else if (this.notesTested.size() == 2) {  // add the 3rd of note 2 unless already included
            Note n = notesTested.get(1);
            Note new1 = n.getThird();
            while (questionChoices.contains(new1)) {  // make sure this note isn't already included
                new1 = new1.getNext();
            }
            questionChoices.add(new1);
        }

        // create array of color options for answer choices
        // valid color for each note being tested + transparent for any added extras not being tested
        if (notesTested.size() < questionChoices.size()) {
            colorChoices = new int[notesTested.size() + 1];
            colorChoices[colorChoices.length - 1] = Color.TRANSPARENT;
        } else {
            colorChoices = new int[notesTested.size()];
        }
        for (int i = 0; i < notesTested.size(); i++) {
            colorChoices[i] = questionChoices.get(i).getColor();
        }
        Arrays.sort(colorChoices);

        // fill the noteOrder array with semi-random permutations of note order
        int filled = 0;
        Note last = null;
        // outer loop: number of times a full permutation of notes can fit in 10 Q's
        for (int i = 0; i < NUM_QUESTIONS / questionChoices.size(); i++) {
            ArrayList<Note> remaining = new ArrayList<>(questionChoices);
            // inner loop: add random permutation of notes
            for (int j = 0; j < questionChoices.size(); j++) {
                int rand = ThreadLocalRandom.current().nextInt(0, remaining.size());
                if (j == 0) {  // make sure first note of this permutation is not same as last of previous
                    while (remaining.get(rand).equals(last)) {
                        rand = ThreadLocalRandom.current().nextInt(0, remaining.size());
                    }
                }
                Note n = remaining.get(rand);
                remaining.remove(n);
                noteOrder[(i * questionChoices.size()) + j] = new MusicNote(n, context);
                if (j == questionChoices.size() - 1) {
                    last = n;
                }
                filled++;
            }
        }
        // fill in the rest
        ArrayList<Note> remaining = new ArrayList<>(questionChoices);
        for (int i = filled; i < noteOrder.length; i++) {
            int rand = ThreadLocalRandom.current().nextInt(0, remaining.size());
            if (i == 0) {  // make sure first note of this permutation is not same as last of previous
                while (remaining.get(rand).equals(last)) {
                    rand = ThreadLocalRandom.current().nextInt(0, remaining.size());
                }
            }
            Note n = remaining.get(rand);
            remaining.remove(n);
            noteOrder[i] = new MusicNote(n, context);
        }
    }

    // converts Strings to Notes then calls the other createQuestions method
    private void createQuestions(String[] noteNames) {
        Note[] notes = new Note[noteNames.length];
        for (int i = 0; i < noteNames.length; i++) {
            notes[i] = Note.valueOf(noteNames[i].toUpperCase());
        }
        createQuestions(notes);
    }
}
