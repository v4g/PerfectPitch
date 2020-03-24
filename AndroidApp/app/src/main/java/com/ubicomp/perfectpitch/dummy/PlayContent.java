package com.ubicomp.perfectpitch.dummy;

import android.graphics.Color;
import android.util.Log;

import com.ubicomp.perfectpitch.Note;
import com.ubicomp.perfectpitch.NoteToColorMap;
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

    public static final List<PlayableItem> ITEMS = new ArrayList<PlayableItem>();
    public static final Map<String, PlayableItem> ITEM_MAP = new HashMap<String, PlayableItem>();


    private static final int COUNT = 25;

    static {
        // Add some sample items.
        loadDefaultOption(0);
    }

    private static void addItem(PlayableItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
    public static void add() {
        addItem(new PlayContent.PlayableItem("e", "C", Color.RED));
    }
    public static void addAtPosition( PlayableItem item, int position) {
        ITEMS.add(position, item);
        ITEM_MAP.put(item.id, item);
    }
    public static PlayableItem remove(int position) {
        PlayableItem item = ITEMS.get(position);
        ITEM_MAP.remove(ITEMS.get(position));
        ITEMS.remove(position);
        return item;
    }
    private static PlayableItem createDummyItem(int position) {
        return new PlayableItem(String.valueOf(position), PitchConstants.NOTES[(position%PitchConstants.NOTES.length)], PitchConstants.COLORS[position%PitchConstants.COLORS.length]);
    }

    public static void loadDefaultOption(int position) {
        if (position >= DEFAULT_PLAYABLE_OPTIONS.length - 1) {
            return;
        }
        ITEMS.clear();
        ITEM_MAP.clear();
        String[] l = PitchConstants.DEFAULT_PLAYABLE_NOTES.get(position);
        NoteToColorMap m = NoteToColorMap.getInstance();
        for (int i = 0; i < l.length; i++) {
            PlayableItem item = new PlayableItem(new Integer(i).toString(), l[i], m.color(l[i]) );
            Log.d("PlayContent",l[i]);
            ITEMS.add(item);
            ITEM_MAP.put(item.id, item);
        }
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class PlayableItem {
        public final String id;
        public String name ;
        public int color;

        public PlayableItem(String id, String name, int color) {
            this.id = id;
            this.name = name;
            this.color = color;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
