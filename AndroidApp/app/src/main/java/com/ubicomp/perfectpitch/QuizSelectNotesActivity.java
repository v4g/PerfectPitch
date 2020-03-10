package com.ubicomp.perfectpitch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

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

        Note[] notes = Note.values();
        selected = new ArrayList<>();
        for (int i = 0; i < notes.length; i++) {
            final CheckBox check = new CheckBox(this);
            check.setId(View.generateViewId());
            check.setText(notes[i].name());
            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox checked = (CheckBox) view;
                    String s = "" + checked.getText();
                    if (selected.contains(s)) {
                        selected.remove(s);
                    } else {
                        selected.add(s);
                    }
                }
            });
            layout.addView(check);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == takeQuizButton) {
            Intent intent = new Intent(this, QuizNotesActivity.class);
            intent.putExtra("selectedNotes", toStringArray(selected));
            startActivity(intent);
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
