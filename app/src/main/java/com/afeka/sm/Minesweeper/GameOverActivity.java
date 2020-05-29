package com.afeka.sm.Minesweeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mineswipper.R;

import java.util.Objects;


public class GameOverActivity extends AppCompatActivity implements Finals {
    TextView TextResult;
    boolean win;
    SharedPreferences sharedPref;
    int timePassed;
    int level;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_layout);
        Intent activity = getIntent();
        sharedPref = GameOverActivity.this.getSharedPreferences(APP_CHOSEN_NAME, Context.MODE_PRIVATE);

        win = Objects.requireNonNull(activity.getExtras()).getBoolean(GAME_RESULT);
        if (win) {
            level = activity.getExtras().getInt(LEVEL_ACTIVITY_KEY);
            timePassed = activity.getExtras().getInt(TIME_PASSED);
            TextResult = findViewById(R.id.GameResult);
            TextResult.setText(R.string.Win);
        } else {
            TextResult = findViewById(R.id.GameResult);
            TextResult.setText(R.string.Lose);
        }
        Button exitButton = findViewById(R.id.ExitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void StartNewGame(View view) {
        Intent intent = new Intent(this, DifficultyChooserActivity.class);
        boolean hasTheUserBrokenARecord = hasTheUserBrokenARecord(level, timePassed);
        intent.putExtra(HAS_THE_USER_BROKEN_A_RECORD, hasTheUserBrokenARecord);
        intent.putExtra(LEVEL_ACTIVITY_KEY, level);
        if (hasTheUserBrokenARecord)
            intent.putExtra(TIME_PASSED, timePassed);
        this.startActivity(intent);
    }

    private int[] getCurrentLevelRecords(int level) {
        int[] currentLevelRecords = new int[NUM_OF_RECORDS_TO_SAVE];
        switch (level) {
            case EASY_LEVEL:
                currentLevelRecords[0] = sharedPref.getInt(String.valueOf(R.integer.EasyFirstPlace), 0);
                currentLevelRecords[1] = sharedPref.getInt(String.valueOf(R.integer.EasySecondPlace), 0);
                currentLevelRecords[2] = sharedPref.getInt(String.valueOf(R.integer.EasyThirdPlace), 0);
                break;
            case MEDIUM_LEVEL:
                currentLevelRecords[0] = sharedPref.getInt(String.valueOf(R.integer.MediumFirstPlace), 0);
                currentLevelRecords[1] = sharedPref.getInt(String.valueOf(R.integer.MediumSecondPlace), 0);
                currentLevelRecords[2] = sharedPref.getInt(String.valueOf(R.integer.MediumThirdPlace), 0);
                break;
            default: // which is HARD_LEVEL
                currentLevelRecords[0] = sharedPref.getInt(String.valueOf(R.integer.HardFirstPlace), 0);
                currentLevelRecords[1] = sharedPref.getInt(String.valueOf(R.integer.HardSecondPlace), 0);
                currentLevelRecords[2] = sharedPref.getInt(String.valueOf(R.integer.HardThirdPlace), 0);
                break;
        }
        return currentLevelRecords;
    }

    private boolean hasTheUserBrokenARecord(int level, int time) {
        int[] currentLevelRecords = getCurrentLevelRecords(level);
        return time < currentLevelRecords[2]; // [2] == third place
    }
}
