package com.ubicomp.perfectpitch;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuizNotesActivity extends AppCompatActivity implements View.OnClickListener {

    private Quiz quiz;
    private int currentQuestion;
    private int questionCountTotal;
    private int[] colorChoices;
    private int[] answers;
    private int selectedColor;

    private Button submitButton;
    private ImageButton playButton;
    private TextView questionTitle;
    private RadioGroup answerChoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_notes);

        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setText(getString(R.string.next_question));
        submitButton.setOnClickListener(this);

        playButton = (ImageButton) findViewById(R.id.playButton);
        playButton.setBackgroundColor(Color.TRANSPARENT);
        playButton.setOnClickListener(this);

        // create quiz content
        Intent intent = getIntent();
        String[] noteNames = intent.getStringArrayExtra("selectedNotes");
        quiz = new Quiz(getApplicationContext(), noteNames);
        currentQuestion = 1;
        questionCountTotal = quiz.getQuizLength();
        colorChoices = quiz.getColorChoices();
        answers = new int[questionCountTotal];

        questionTitle = (TextView) findViewById(R.id.questionLabel);
        questionTitle.setText("Question " + currentQuestion + " " + quiz.getQuestion(currentQuestion).getName());

        answerChoices = (RadioGroup) findViewById(R.id.answerChoices);
        answerChoices.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(10, 10, 10, 10);
        selectedColor = 1;
        for (int i = 0; i < colorChoices.length; i++) {
            AppCompatRadioButton button = new AppCompatRadioButton(this);
            button.setId(View.generateViewId());
            button.setContentDescription("" + colorChoices[i]);  // saving the button color here for later access
            button.setText(getColorName(colorChoices[i]));
            // set the circle color
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{  new int[]{android.R.attr.state_enabled}  },
                    new int[] {  Color.BLACK  }
            );
            button.setButtonTintList(colorStateList);//set the color tint list
            // set the background color and round the corners
            Color c = Color.valueOf(colorChoices[i]);
            if (colorChoices[i] == Color.WHITE) {
                c = Color.valueOf(Color.LTGRAY);
            } else if (colorChoices[i] == Color.TRANSPARENT) {
                c = Color.valueOf(Color.WHITE);
            }
            GradientDrawable shape = new GradientDrawable();
            shape.setColor(Color.argb(0.3f, c.red(), c.green(), c.blue()));
            shape.setCornerRadius(40);
            button.setBackground(shape);
            // set up listener
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatRadioButton button = (AppCompatRadioButton) view;
                    // set selected color to current button's color
                    selectedColor = Integer.parseInt("" + button.getContentDescription());
                    answers[currentQuestion - 1] = selectedColor;
                }
            });
            answerChoices.addView(button, layoutParams);
        }

    }

    @Override
    public void onClick(View view) {
        if (view == submitButton) {
            if (currentQuestion < questionCountTotal) {
                showNextQuestion();
            } else {
                submitQuiz();
            }
        }
        if (view == playButton) {
            MusicNote n = quiz.getQuestion(currentQuestion);
            n.play();
        }
    }

    private void showNextQuestion() {
        if (selectedColor == 1) {  // haven't selected answer choice yet
            //TODO: put error message here
        } else {  // next question
            answers[currentQuestion - 1] = selectedColor;
            currentQuestion++;
            selectedColor = 1;
            if (currentQuestion == questionCountTotal) {
                submitButton.setText(getString(R.string.submit_quiz));
            }
            questionTitle.setText("Question " + currentQuestion + " " + quiz.getQuestion(currentQuestion).getName());
            answerChoices.clearCheck();
        }

    }

    private void submitQuiz() {
        System.out.println(quiz.getScore(answers));
    }

    private String getColorName(int color) {
        if (color == Color.WHITE) {
            return "White";
        } else if (color == Color.MAGENTA) {
            return "Magenta";
        } else if (color == Color.RED) {
            return "Red";
        } else if (color == Color.YELLOW) {
            return "Yellow";
        } else if (color == Color.GREEN) {
            return "Green";
        } else if (color == Color.CYAN) {
            return "Cyan";
        } else if (color == Color.BLUE) {
            return "Blue";
        } else if (color == Color.TRANSPARENT) {
            return "Other";
        }
        return "Error: Unknown Color";
    }

    private int getColorInt(String s) {
        if (s.toLowerCase().equals("white")) {
            return Color.WHITE;
        } else if (s.toLowerCase().equals("magenta")) {
            return Color.MAGENTA;
        } else if (s.toLowerCase().equals("red")) {
            return Color.RED;
        } else if (s.toLowerCase().equals("yellow")) {
            return Color.YELLOW;
        } else if (s.toLowerCase().equals("green")) {
            return Color.GREEN;
        } else if (s.toLowerCase().equals("cyan")) {
            return Color.CYAN;
        } else if (s.toLowerCase().equals("blue")) {
            return Color.BLUE;
        } else if (s.toLowerCase().equals("other")) {
            return Color.TRANSPARENT;
        }
        return -1;
    }
}
