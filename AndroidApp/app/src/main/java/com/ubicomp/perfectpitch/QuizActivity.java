package com.ubicomp.perfectpitch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private Button takeQuizButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        takeQuizButton = (Button) findViewById(R.id.takeQuizButton);

        takeQuizButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == takeQuizButton) {
            // navigate to quiz screen
            Intent intentNotesQuiz = new Intent(getApplicationContext(), QuizNotesActivity.class);
            startActivity(intentNotesQuiz);
        }
    }

}