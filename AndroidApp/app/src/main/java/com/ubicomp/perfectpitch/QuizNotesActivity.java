package com.ubicomp.perfectpitch;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class QuizNotesActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton playNoteButton;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_notes);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.c3);

        playNoteButton = (ImageButton) findViewById(R.id.playNoteButton);

        playNoteButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == playNoteButton) {
            mediaPlayer.start();
        }
    }
}
