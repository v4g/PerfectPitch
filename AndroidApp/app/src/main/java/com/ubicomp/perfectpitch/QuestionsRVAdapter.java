package com.ubicomp.perfectpitch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestionsRVAdapter extends RecyclerView.Adapter<QuestionsRVAdapter.QuestionsRVViewHolder> {
    private MusicNote[] mNotes;
    private int[] mColors;
    private Context mContext;
    private int[] mAnswers;

    public class QuestionsRVViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView mContentView;
        public ImageButton mPlayButton;
        public RadioGroup mRadioGroup;
        public MusicNote mNote;

        public QuestionsRVViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.questionTextView);
            mPlayButton = (ImageButton) view.findViewById(R.id.playButton);
            mRadioGroup = (RadioGroup) view.findViewById(R.id.answerChoices);
        }
    }

    public QuestionsRVAdapter(Context myContext, MusicNote[] myNotes, int[] myColors) {
        mContext = myContext;
        mNotes = myNotes;
        mColors = myColors;
        mAnswers = new int[mNotes.length];
    }

    public QuestionsRVAdapter(Quiz myQuiz) {
        mContext = myQuiz.getContext();
        mNotes = myQuiz.getNotes();
        mColors = myQuiz.getColorChoices();
        mAnswers = new int[mNotes.length];
    }

    @Override
    public QuestionsRVAdapter.QuestionsRVViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        //create new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_card_rv, parent, false);
        return new QuestionsRVAdapter.QuestionsRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final QuestionsRVAdapter.QuestionsRVViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mNote = mNotes[position];
        holder.mContentView.setText(mNotes[position].getName());
        holder.mRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
        for (int i = 0; i < mColors.length; i++) {
            RadioButton button = new RadioButton(mContext);
            button.setId(View.generateViewId());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RadioButton selected = (RadioButton) view;
                    int index = ((int)selected.getId() - 1) / mColors.length;
                    int color = getColorInt((String)selected.getText());
                    mAnswers[index] = color;
                }
            });
            button.setText(getColorName(i));
            holder.mRadioGroup.addView(button);
        }

        // play note when play button is clicked
        holder.mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.mNote.play();
            }
        });
    }
    @Override
    public int getItemCount() {
        return mNotes.length;
    }

    public int[] getAnswers() {
        return mAnswers;
    }

    private String getColorName(int i) {
        if (mColors[i] == Color.WHITE) {
            return "White";
        } else if (mColors[i] == Color.MAGENTA) {
            return "Magenta";
        } else if (mColors[i] == Color.RED) {
            return "Red";
        } else if (mColors[i] == Color.YELLOW) {
            return "Yellow";
        } else if (mColors[i] == Color.GREEN) {
            return "Green";
        } else if (mColors[i] == Color.CYAN) {
            return "Cyan";
        } else if (mColors[i] == Color.BLUE) {
            return "Blue";
        } else if (mColors[i] == Color.BLACK) {
            return "Black";
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
        } else if (s.toLowerCase().equals("black")) {
            return Color.BLACK;
        }
        return -1;
    }
}
