package com.ubicomp.perfectpitch;

import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ubicomp.perfectpitch.dummy.PlayContent;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class SettingsActivity extends AppCompatActivity implements PlayableItemFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    public void onListFragmentInteraction(Note item) {
    }

    @Override
    protected void onPause() {
        super.onPause();
        PlayContent.save(this);
    }
}
