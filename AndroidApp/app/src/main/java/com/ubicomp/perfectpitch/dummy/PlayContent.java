package com.ubicomp.perfectpitch.dummy;

import com.ubicomp.perfectpitch.Note;
import com.ubicomp.perfectpitch.PitchConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ubicomp.perfectpitch.PitchConstants.DEFAULT_PLAYABLE_OPTIONS;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PlayContent {

    public static final List<Note> ITEMS = new ArrayList<Note>();
    public static final Map<String, Note> ITEM_MAP = new HashMap<String, Note>();


    private static final int COUNT = 25;

    static {
        // Add some sample items.
        loadDefaultOption(0);
    }

    private static void addItem(Note item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.name(), item);
    }
    public static void add() {
        addItem(Note.C3);
    }
    public static void addAtPosition( Note note, int position) {
        ITEMS.add(position, note);
        ITEM_MAP.put(note.name(), note);
    }
    public static Note remove(int position) {
        Note item = ITEMS.get(position);
        ITEM_MAP.remove(ITEMS.get(position));
        ITEMS.remove(position);
        return item;
    }
    public static void loadDefaultOption(int position) {
        if (position >= DEFAULT_PLAYABLE_OPTIONS.length - 1) {
            return;
        }
        ITEMS.clear();
        ITEM_MAP.clear();
        for (Note n : PitchConstants.DEFAULT_PLAYABLE_NOTES.get(position)) {
            addItem(n);
        }
    }
}
