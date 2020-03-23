package com.ubicomp.perfectpitch;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PitchConstants {
    public static int COLORS[] = {Color.MAGENTA,Color.WHITE,Color.BLUE,Color.CYAN,Color.GREEN,Color.YELLOW, Color.RED};
    public static String NOTES[] = {"C", "D", "E", "F", "G", "A", "B"};
    public static String DEFAULT_PLAYABLE_OPTIONS[] = {"Chromatic Scale", "C-G", "C-E-G", "Custom"};
    public static List<String[]> DEFAULT_PLAYABLE_NOTES = new ArrayList<String[]>(){{add(new String[]{"C", "D", "E", "F", "G", "A", "B"});
            add(new String[]{"C", "G"});
            add(new String[]{"C", "E", "G"});
    }};

    public static final Map<String, Integer> NOTE_TO_SOUND = new HashMap<String, Integer>();
    static {
        NOTE_TO_SOUND.put("C", R.raw.c3t);
        NOTE_TO_SOUND.put("D", R.raw.d3t);
        NOTE_TO_SOUND.put("E", R.raw.e3t);
        NOTE_TO_SOUND.put("F", R.raw.f3t);
        NOTE_TO_SOUND.put("G", R.raw.g3t);
        NOTE_TO_SOUND.put("A", R.raw.a3t);
        NOTE_TO_SOUND.put("B", R.raw.b3t);
    }
    public static int DEFAULT_PLAYABLE_COLORS[][] = {{0, 1, 2, 3, 4, 5, 6},
            {2, 6},
            {2,0,6}};
}
