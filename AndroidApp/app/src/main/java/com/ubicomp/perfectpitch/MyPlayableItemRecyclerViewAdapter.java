package com.ubicomp.perfectpitch;

import android.content.res.ColorStateList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.ubicomp.perfectpitch.PlayableItemFragment.OnListFragmentInteractionListener;
import com.ubicomp.perfectpitch.dummy.PlayContent;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Note} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyPlayableItemRecyclerViewAdapter extends RecyclerView.Adapter<MyPlayableItemRecyclerViewAdapter.ViewHolder> {

    private final List<Note> mValues;
    private final OnListFragmentInteractionListener mListener;
    static ArrayAdapter<String> spinnerAdapter;
    private Spinner mSpinner;

    public MyPlayableItemRecyclerViewAdapter(List<Note> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public void attachSpinner(Spinner spinner) {
        mSpinner = spinner;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (spinnerAdapter == null) {
            spinnerAdapter = new ArrayAdapter<>(parent.getContext(), android.R.layout.simple_spinner_item);
            for (Note note: Note.values()) {
                spinnerAdapter.add(note.name());
            }
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_playableitem, parent, false);
        return new ViewHolder(view);
    }

    public void setNoteAsItem(final ViewHolder holder, Note note) {
        holder.mItem = note;
        holder.mNoteNameView.setText(note.name());
        int[] colors = {note.getColor()};
        int[][] states = {new int[] { android.R.attr.state_enabled}};
        ColorStateList cl = new ColorStateList(states, colors);
        holder.mNoteNameView.setBackgroundTintList(cl);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Note note = mValues.get(position);
        setNoteAsItem(holder, note);
        holder.mNoteNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNoteAsItem(holder,holder.mItem.getNext());
                SoundManager.getInstance().play(holder.mItem);
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    public void deleteItem(int index) {
        PlayContent.remove(index);
        updateSpinner();
        notifyDataSetChanged();
    }

    public void moveItem(int from, int to) {
        Note item = PlayContent.remove(from);
        PlayContent.addAtPosition(item, to);
        updateSpinner();
    }

    public void updateSpinner() {
        mSpinner.setSelection(0);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final Button mNoteNameView;
        public Note mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNoteNameView = (Button) view.findViewById(R.id.noteName);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNoteNameView.toString() + "'";
        }
    }
}
