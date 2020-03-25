package com.ubicomp.perfectpitch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class QuizResultActivity extends AppCompatActivity implements View.OnClickListener {

    private Button homeButton;
    private TextView scoreTextView;

    private int score;
    private String notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(this);

        Intent intent = getIntent();
        score = intent.getIntExtra("score", -1);
        notes = intent.getStringExtra("notes");
        updateScoreHistory(score, notes);

        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        scoreTextView.setText("" + score);
    }

    @Override
    public void onClick(View view) {
        if (view == homeButton) {
            // navigate to home screen
            Intent intentHome = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intentHome);
        }
    }

    private void updateScoreHistory(int score, String notes) {
        try {
            final String scoreString = new String("" + System.currentTimeMillis() + ":" + notes + ":" + score + ",");
            FileOutputStream fOut = openFileOutput("quizScores.txt",
                    MODE_APPEND);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            osw.write(scoreString);
            osw.flush();
            osw.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
