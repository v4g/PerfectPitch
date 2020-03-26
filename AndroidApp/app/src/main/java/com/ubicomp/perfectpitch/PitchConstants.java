package com.ubicomp.perfectpitch;

import java.util.ArrayList;
import java.util.List;

public class PitchConstants {
    public static String DEFAULT_PLAYABLE_OPTIONS[] = {"Custom","Chromatic Scale", "C-G", "C-E-G"};
    public static List<Note[]> DEFAULT_PLAYABLE_NOTES = new ArrayList<Note[]>(){{
            add(new Note[]{});
            add(new Note[]{Note.C3, Note.D3, Note.E3, Note.F3, Note.G3, Note.A3, Note.B3});
            add(new Note[]{Note.C3, Note.G3});
            add(new Note[]{Note.C3, Note.E3, Note.G3});
    }};
}
