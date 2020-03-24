package com.ubicomp.perfectpitch;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ubicomp.perfectpitch.dummy.PlayContent;
import com.ubicomp.perfectpitch.dummy.PlayContent.PlayableItem;

import java.io.Console;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PlayableItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private MyPlayableItemRecyclerViewAdapter adapter;
    private FloatingActionButton addBtn;
    private Spinner defaultOptions;
    private ArrayAdapter<String> optionsAdapter;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PlayableItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PlayableItemFragment newInstance(int columnCount) {
        PlayableItemFragment fragment = new PlayableItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playableitem_list, container, false);

        // Set the adapter
        View rv_view = view.findViewById(R.id.list);
        addBtn = view.findViewById(R.id.addNoteBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayContent.add();
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                defaultOptions.setSelection(PitchConstants.DEFAULT_PLAYABLE_OPTIONS.length - 1);
            }
        });
        if (rv_view instanceof RecyclerView) {
            Context context = rv_view.getContext();
            RecyclerView recyclerView = (RecyclerView) rv_view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new MyPlayableItemRecyclerViewAdapter(PlayContent.ITEMS, mListener);
            recyclerView.setAdapter(adapter);
            ItemTouchHelper itemTouchHelper = new
                    ItemTouchHelper(new SwipeToDeleteCallback(adapter));
            itemTouchHelper.attachToRecyclerView(recyclerView);

        }

        defaultOptions = view.findViewById(R.id.spinnerOptions);
        if (optionsAdapter == null) {
            optionsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
            optionsAdapter.addAll(PitchConstants.DEFAULT_PLAYABLE_OPTIONS);
            optionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }
        defaultOptions.setAdapter((optionsAdapter));
        defaultOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Spinner", parent.getItemAtPosition(position).toString());
                PlayContent.loadDefaultOption(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adapter.attachSpinner(defaultOptions);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(PlayableItem item);
    }

    public void onAddClick(View v) {
        PlayContent.add();
    }
}
