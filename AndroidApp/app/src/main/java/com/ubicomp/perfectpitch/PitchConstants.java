package com.ubicomp.perfectpitch;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PitchConstants {
    public static String DEFAULT_PLAYABLE_OPTIONS[] = {"Chromatic Scale", "C-G", "C-E-G", "Custom"};
    public static List<Note[]> DEFAULT_PLAYABLE_NOTES = new ArrayList<Note[]>(){{add(new Note[]{Note.C3, Note.D3, Note.E3, Note.F3, Note.G3, Note.A3, Note.B3});
            add(new Note[]{Note.C3, Note.G3});
            add(new Note[]{Note.C3, Note.E3, Note.G3});
    }};
}
