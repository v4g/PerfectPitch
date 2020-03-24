package com.ubicomp.perfectpitch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button quizButton;
    private Button historyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quizButton = (Button) findViewById(R.id.quizButton);
        quizButton.setOnClickListener(this);

        historyButton = (Button) findViewById(R.id.historyButton);
        historyButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == quizButton) {
            // navigate to quiz screen
            Intent intentQuiz = new Intent(getApplicationContext(), QuizActivity.class);
            startActivity(intentQuiz);
        }
        if (view == historyButton) {
            // navigate to history screen
            Intent intentHistory = new Intent(getApplicationContext(), HistoryActivity.class);
            startActivity(intentHistory);
        }
    }
}
