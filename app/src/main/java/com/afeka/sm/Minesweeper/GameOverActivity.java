package com.afeka.sm.Minesweeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mineswipper.R;

import java.util.Objects;


public class GameOverActivity extends AppCompatActivity implements Finals {
    TextView TextResult;
    boolean Win;
    SharedPreferences sharedPref;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_layout);
        Intent activity = getIntent();
        sharedPref = GameOverActivity.this.getSharedPreferences(APP_CHOSEN_NAME, Context.MODE_PRIVATE);
        Win = Objects.requireNonNull(activity.getExtras()).getBoolean(GAME_RESULT);
        int timePassed = activity.getExtras().getInt(TIME_PASSED);

        if (Win) {
            int level = activity.getExtras().getInt(LEVEL_ACTIVITY_KEY);
            if (hasTheUserBrokenARecord(level, timePassed))
                handleBrokenRecord(level, timePassed);
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
        this.startActivity(intent);
    }

    public void handleBrokenRecord(int level, int time) {
        int[] currentLevelRecords = getCurrentLevelRecords(level);
        if (time < currentLevelRecords[0]) { // if first place - move all the elements one index away from the first place
            System.arraycopy(currentLevelRecords, 0, currentLevelRecords, 1, currentLevelRecords.length - 1);
            currentLevelRecords[0] = time;
        } else if (time < currentLevelRecords[1]) { // same for second place
            System.arraycopy(currentLevelRecords, 0, currentLevelRecords, 1, currentLevelRecords.length - 2);
            currentLevelRecords[1] = time;
        } else {
            currentLevelRecords[2] = time;
        }
        setCurrentLevelRecords(level, currentLevelRecords);
//        //TODO: remove log messages!!
//        Log.d("EasyFirstPlace", "" + currentLevelRecords[0]);
//        Log.d("EasySecondPlace", "" + currentLevelRecords[1]);
//        Log.d("EasyThirdPlace", "" + currentLevelRecords[2]);
    }

    private void setCurrentLevelRecords(int level, int[] currentLevelRecords) {
        SharedPreferences.Editor editor = sharedPref.edit();
        switch (level) {
            case EASY_LEVEL:
                editor.putInt(String.valueOf(R.integer.EasyFirstPlace), currentLevelRecords[0]);
                editor.putInt(String.valueOf(R.integer.EasySecondPlace), currentLevelRecords[1]);
                editor.putInt(String.valueOf(R.integer.EasyThirdPlace), currentLevelRecords[2]);
                break;
            case MEDIUM_LEVEL:
                editor.putInt(String.valueOf(R.integer.MediumFirstPlace), currentLevelRecords[0]);
                editor.putInt(String.valueOf(R.integer.MediumSecondPlace), currentLevelRecords[1]);
                editor.putInt(String.valueOf(R.integer.MediumThirdPlace), currentLevelRecords[2]);
                break;
            default:
                editor.putInt(String.valueOf(R.integer.HardFirstPlace), currentLevelRecords[0]);
                editor.putInt(String.valueOf(R.integer.HardSecondPlace), currentLevelRecords[1]);
                editor.putInt(String.valueOf(R.integer.HardThirdPlace), currentLevelRecords[2]);
                break;
        }
        editor.apply();
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
