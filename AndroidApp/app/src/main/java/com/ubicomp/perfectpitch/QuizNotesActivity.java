package com.ubicomp.perfectpitch;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class QuizNotesActivity extends AppCompatActivity implements View.OnClickListener {

    private Quiz quiz;

    private Button submitButton;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_notes);

        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);

        // create quiz content
        quiz = new Quiz(getApplicationContext(),
                        new String[]{"c3", "g3", "e3", "c3"},
                        new int[]{Note.C3.getColor(), Note.G3.getColor(), Note.E3.getColor()});

        // set up recycler view to display the quiz
        mRecyclerView = (RecyclerView) findViewById(R.id.questionsList);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new QuestionsRVAdapter(quiz);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onClick(View view) {
        if (view == submitButton) {
            int[] answers = ((QuestionsRVAdapter)mAdapter).getAnswers();
            System.out.println(quiz.getScore(answers));
        }
    }
}
