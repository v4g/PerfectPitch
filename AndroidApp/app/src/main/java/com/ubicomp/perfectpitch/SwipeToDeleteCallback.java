package com.ubicomp.perfectpitch;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private MyPlayableItemRecyclerViewAdapter mAdapter;

    public SwipeToDeleteCallback(MyPlayableItemRecyclerViewAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        mAdapter.deleteItem(position);
    }

    @Override
    public boolean onMove(RecyclerView view, RecyclerView.ViewHolder viewHolder,RecyclerView.ViewHolder target) {
//        int position = viewHolder.getAdapterPosition();
//        mAdapter.deleteItem(position);
        int from = viewHolder.getAdapterPosition();
        int to = target.getAdapterPosition();
        mAdapter.moveItem(from, to);
        mAdapter.notifyItemMoved(from, to);
        return true;
    }
}