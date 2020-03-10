package com.ubicomp.perfectpitch;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class PitchConstants {
    public static int COLORS[] = {Color.MAGENTA,Color.WHITE,Color.BLUE,Color.CYAN,Color.GREEN,Color.YELLOW, Color.RED};
    public static String NOTES[] = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    public static int CHROMATIC_SCALE[] = {0, 2, 4, 5, 7, 9, 11};
    public static String DEFAULT_PLAYABLE_OPTIONS[] = {"Chromatic Scale", "C-G", "C-E-G"};
    public static List<int[]> DEFAULT_PLAYABLE_NOTES = new ArrayList<int[]>(){{add(new int[]{0, 2, 4, 5, 7, 9, 11});
            add(new int[]{0, 7});
            add(new int[]{0,4,7});
    }};
    public static int DEFAULT_PLAYABLE_COLORS[][] = {{0, 1, 2, 3, 4, 5, 6},
            {2, 6},
            {2,0,6}};
}
