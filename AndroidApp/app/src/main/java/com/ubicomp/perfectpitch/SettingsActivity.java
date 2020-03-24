package com.ubicomp.perfectpitch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ubicomp.perfectpitch.dummy.PlayContent;

public class SettingsActivity extends AppCompatActivity implements PlayableItemFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    public void onListFragmentInteraction(Note item) {
    }
}
