package com.ubicomp.perfectpitch;

import android.graphics.Color;

import com.ubicomp.perfectpitch.dummy.PlayContent;

import java.util.HashMap;
import java.util.Map;

public class NoteToColorMap {
    private Map<String, Integer> map = new HashMap<String, Integer>();
    private static NoteToColorMap instance = null;
    public static NoteToColorMap getInstance() {
        if (instance == null) {
            instance = new NoteToColorMap();
        }
        return instance;
    }

    NoteToColorMap() {
        loadMap();
    }

    public int color(String note) {
        return map.get(note);
    }

    // Load from local storage
    public void loadMap(){
        // for now this default
        for (int i = 0; i < PitchConstants.NOTES.length; i++) {
            map.put(PitchConstants.NOTES[i], PitchConstants.COLORS[i]);
        }
    }

    // save to local storage
    public void saveMap(){

    }
}
