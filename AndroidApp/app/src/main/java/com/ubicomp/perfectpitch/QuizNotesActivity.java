package com.ubicomp.perfectpitch;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class QuizNotesActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton[] playButtons;
    private MediaPlayer mediaPlayer;
    private Quiz quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_notes);

        quiz = new Quiz(new String[]{"c3", "g3", "e3", "c3", "e3", "g3", "e3", "c3", "g3"},
                            new int[]{Note.C3.getColor(), Note.G3.getColor(), Color.BLACK},
                            getApplicationContext());

        //playNoteButton = (ImageButton) findViewById(R.id.playNoteButton);

        //playNoteButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        /*if (view == playNoteButton) {
            //c3.play();
            for (MusicNote n: cgQuiz.getNotes()) {
                n.play();
            }
        }*/
    }
}
