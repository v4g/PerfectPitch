package com.ubicomp.perfectpitch;

import android.content.res.ColorStateList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.ubicomp.perfectpitch.PlayableItemFragment.OnListFragmentInteractionListener;
import com.ubicomp.perfectpitch.dummy.PlayContent;
import com.ubicomp.perfectpitch.dummy.PlayContent.PlayableItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlayableItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyPlayableItemRecyclerViewAdapter extends RecyclerView.Adapter<MyPlayableItemRecyclerViewAdapter.ViewHolder> {

    private final List<PlayableItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    static ArrayAdapter<String> spinnerAdapter;
    private Spinner mSpinner;

    public MyPlayableItemRecyclerViewAdapter(List<PlayableItem> items, OnListFragmentInteractionListener listener) {
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
            spinnerAdapter.addAll(PitchConstants.NOTES);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_playableitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNoteNameView.setText(mValues.get(position).name);
        int[] colors = {NoteToColorMap.getInstance().color(mValues.get(position).name)};
        int[][] states = {new int[] { android.R.attr.state_enabled}};
        ColorStateList cl = new ColorStateList(states, colors);
        holder.mNoteNameView.setBackgroundTintList(cl);
        final int index = position;
        final MyPlayableItemRecyclerViewAdapter adapter = this;

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
        PlayableItem item = PlayContent.remove(from);
        PlayContent.addAtPosition(item, to);
        updateSpinner();
        notifyDataSetChanged();
    }

    public void updateSpinner() {
        mSpinner.setSelection(PitchConstants.DEFAULT_PLAYABLE_OPTIONS.length - 1);
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final Button mNoteNameView;
//        public final TextView mColorView;
//        public final Spinner mSpinnerView;
        public PlayableItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNoteNameView = (Button) view.findViewById(R.id.noteName);
//            mColorView = (TextView) view.findViewById(R.id.color);
//            mSpinnerView = (Spinner) view.findViewById(R.id.spinner);
//            mSpinnerView.setAdapter(spinnerAdapter);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNoteNameView.toString() + "'";
        }
    }
}
