package com.ubicomp.perfectpitch.dummy;

import android.graphics.Color;

import com.ubicomp.perfectpitch.Note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PlayContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<PlayableItem> ITEMS = new ArrayList<PlayableItem>();
    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, PlayableItem> ITEM_MAP = new HashMap<String, PlayableItem>();


    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(PlayableItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
    public static void add() {
        addItem(new PlayContent.PlayableItem("e", "C#", 0xdd0000));
    }
    private static PlayableItem createDummyItem(int position) {
        return new PlayableItem(String.valueOf(position), "Item " + position, Color.BLUE);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class PlayableItem {
        public final String id;
        public final String name ;
        public final int color;

        public PlayableItem(String id, String name, int color) {
            this.id = id;
            this.name = name;
            this.color = color;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
