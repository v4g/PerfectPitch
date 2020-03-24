package com.ubicomp.perfectpitch;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static Map<String, Integer> map = new HashMap<String, Integer>();
    private static SoundManager instance = null;
    private static String currentNote = "C";
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

        map.put("C", soundPool.load(context, R.raw.c3t, 0));
        map.put("D", soundPool.load(context, R.raw.d3t, 0));
        map.put("E", soundPool.load(context, R.raw.e3t, 0));
        map.put("F", soundPool.load(context, R.raw.f3t, 0));
        map.put("G", soundPool.load(context, R.raw.g3t, 0));
        map.put("A", soundPool.load(context, R.raw.a3t, 0));
        map.put("B", soundPool.load(context, R.raw.b3t, 0));
    }

    SoundManager() {
    }

    public void play(String note) {
        soundPool.play(map.get(note), 1, 1, 1, 0, 1);
        currentNote = note;
    }
    public void stop() {
        if (map.get(currentNote) != null)
        soundPool.pause(map.get(currentNote));
    }
}
