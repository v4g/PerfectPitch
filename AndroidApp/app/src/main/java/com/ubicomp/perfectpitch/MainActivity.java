package com.ubicomp.perfectpitch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button quizButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quizButton = (Button) findViewById(R.id.quizButton);

        quizButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == quizButton) {
            // navigate to quiz screen
            Intent intentQuiz = new Intent(getApplicationContext(), QuizActivity.class);
            startActivity(intentQuiz);
        } else
        if (view == quizButton) {
            // navigate to quiz screen
            Intent intentQuiz = new Intent(getApplicationContext(), QuizActivity.class);
            startActivity(intentQuiz);
        }
    }
    public void onSettingsClick(View view) {
        // navigate to quiz screen
        Intent intentSettings = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intentSettings);
    }
}
