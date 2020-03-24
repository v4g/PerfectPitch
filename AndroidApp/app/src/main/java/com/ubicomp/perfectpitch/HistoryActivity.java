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
import java.io.InputStreamReader;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {

    private Button homeButton;
    private TextView historyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(this);

        String[] scores = getScoreHistory();
        String res = "Time:Notes:Score\n";
        for (String s: scores) {
            res += s + "\n";
        }

        historyText = (TextView) findViewById(R.id.historyTextView);
        historyText.setText(res);
    }

    @Override
    public void onClick(View view) {
        if (view == homeButton) {
            // navigate to home screen
            Intent intentHome = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intentHome);
        }
    }

    // TODO: correct context?
    private String[] getScoreHistory() {
        String ret = "";
        try {
            FileInputStream inputStream = openFileInput("quizScores.txt");
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (Exception e) {
            Log.e("Exception", e.toString());
        }
        return ret.split(",");
    }
}
