package com.ubicomp.perfectpitch;

import android.content.res.ColorStateList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.ubicomp.perfectpitch.PlayableItemFragment.OnListFragmentInteractionListener;
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

    public MyPlayableItemRecyclerViewAdapter(List<PlayableItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
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
        holder.mNoteNameView.setText(PitchConstants.NOTES[mValues.get(position).name]);
//        holder.mSpinnerView.setSelection(mValues.get(position).name);
        int[] colors = {PitchConstants.COLORS[mValues.get(position).color]};
        int[][] states = {new int[] { android.R.attr.state_enabled}};
        ColorStateList cl = new ColorStateList(states, colors);
//        holder.mColorView.setBackgroundTintList(cl);

        final MyPlayableItemRecyclerViewAdapter adapter = this;

//        holder.mColorView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.mItem.color = (holder.mItem.color + 1) % PitchConstants.COLORS.length;
//                adapter.notifyDataSetChanged();
//            }
//        });
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

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNoteNameView;
//        public final TextView mColorView;
//        public final Spinner mSpinnerView;
        public PlayableItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNoteNameView = (TextView) view.findViewById(R.id.noteName);
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
