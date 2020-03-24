package com.ubicomp.perfectpitch;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static Map<Note, Integer> map = new HashMap<Note, Integer>();
    private static SoundManager instance = null;
    private static Note currentNote = Note.C3;
    private static SoundPool soundPool;
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public static void initialize(Context context) {
        getInstance();
        soundPool = new SoundPool.Builder().setMaxStreams(16).build();
        for (Note note: Note.values()) {
            map.put(note, soundPool.load(context, note.getID(), 0));
        }
    }

    SoundManager() {
    }

    public void play(Note note) {
        soundPool.play(map.get(note), 1, 1, 1, 0, 1);
        currentNote = note;
    }
    public void stop() {
        if (map.get(currentNote) != null)
        soundPool.pause(map.get(currentNote));
    }
}
