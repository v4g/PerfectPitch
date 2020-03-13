package com.ubicomp.perfectpitch;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class QuizSelectNotesActivity extends AppCompatActivity implements View.OnClickListener {

    private Button takeQuizButton;

    private ArrayList<String> selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_select_notes);

        takeQuizButton = (Button) findViewById(R.id.takeQuizButton);
        takeQuizButton.setOnClickListener(this);

        LinearLayout layout = (LinearLayout)findViewById(R.id.selectNotesLayout);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10, 10);

        final Note[] notes = Note.values();
        selected = new ArrayList<>();
        for (int i = 0; i < notes.length; i++) {
            Button button = new Button(this);
            button.setId(View.generateViewId());
            button.setText(notes[i].name());
            button.setBackgroundColor(Color.WHITE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button button = (Button) view;
                    String noteName = "" + button.getText();
                    Note n = Note.valueOf(noteName);
                    if (selected.contains(noteName)) {
                        selected.remove(noteName);
                        button.setBackgroundColor(Color.WHITE);
                    } else {
                        selected.add(noteName);
                        Color c = Color.valueOf(n.getColor());
                        if (n.getColor() == Color.WHITE) {
                            c = Color.valueOf(Color.LTGRAY);
                        }
                        button.setBackgroundColor(Color.argb(0.4f, c.red(), c.green(), c.blue()));
                    }
                }
            });
            layout.addView(button, layoutParams);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == takeQuizButton) {
            if (selected.size() == 0) {
                Toast.makeText(getApplicationContext(),"Please select at least one note",Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(this, QuizNotesActivity.class);
                intent.putExtra("selectedNotes", toStringArray(selected));
                startActivity(intent);
            }
        }
    }

    private String[] toStringArray(ArrayList<String> notes) {
        String[] res = new String[notes.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = notes.get(i);
        }
        return res;
    }
}
