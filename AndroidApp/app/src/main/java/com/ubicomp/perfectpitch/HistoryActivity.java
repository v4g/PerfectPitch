package com.ubicomp.perfectpitch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {

    private Button homeButton;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.quizScoresRecycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ScoreAdapter(getScoreHistory());
        recyclerView.setAdapter(mAdapter);

        String[] scores = getScoreHistory();
        String res = "Time:Notes:Score\n";
        for (int i = scores.length - 1; i > -1; i--) {
            res += scores[i] + "\n";
        }
    }

    @Override
    public void onClick(View view) {
        if (view == homeButton) {
            // navigate to home screen
            Intent intentHome = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intentHome);
        }
    }

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

    public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.MyViewHolder> {
        private String[] mDataset;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public View mView;
            public TextView mContentView;
            public String mEntry;

            public MyViewHolder(View view) {
                super(view);
                mView = view;
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }

        public ScoreAdapter(String[] myDataset) {
            mDataset = myDataset;
        }

        @Override
        public ScoreAdapter.MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
            //create new view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.quiz_score_layout, parent, false);
            return  new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ScoreAdapter.MyViewHolder holder, final int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.mEntry = mDataset[position];
            String[] text = mDataset[position].split(":");
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(Long.parseLong(text[0]));
            String year = ("" + c.get(Calendar.YEAR)).substring(2,4);
            int month = c.get(Calendar.MONTH) + 1;
            int day = c.get(Calendar.DAY_OF_MONTH);
            holder.mContentView.setText(month + "/" + day + "/" + year + ", " + text[1] + ", " + text[2]);
        }

        @Override
        public int getItemCount() {
            return mDataset.length;
        }
    }
}
