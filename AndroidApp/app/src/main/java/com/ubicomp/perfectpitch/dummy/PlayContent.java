package com.ubicomp.perfectpitch.dummy;

import android.content.Context;
import android.os.ParcelFileDescriptor;

import com.ubicomp.perfectpitch.Note;
import com.ubicomp.perfectpitch.PitchConstants;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
        if (position == 0) {
            return;
        }
        ITEMS.clear();
        ITEM_MAP.clear();
        for (Note n : PitchConstants.DEFAULT_PLAYABLE_NOTES.get(position)) {
            addItem(n);
        }
    }

    public static String serialize() {
        StringBuffer str = new StringBuffer();
        for (Note note: ITEMS) {
            str.append(note.name()+" ");
        }
        return str.toString();
    }

    public static void deserialize(String str) {
        ITEMS.clear();
        ITEM_MAP.clear();
        String strNotes[] = str.split(" ");
        for (String s: strNotes) {
            addItem(Note.valueOf(s));
        }
    }

    public static void save(Context context) {
        try {
            FileOutputStream fOut = context.openFileOutput("playlist.txt",
                    Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            osw.write(PlayContent.serialize());
            osw.flush();
            osw.close();
            fOut.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void load(Context context) {
        try {
            FileInputStream fIn = context.openFileInput("playlist.txt");
            InputStreamReader isr = new InputStreamReader(fIn);
            char buf[] = new char[1024];
            isr.read(buf);
            String str = new String(buf);
            deserialize(str);
            isr.close();
            fIn.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
