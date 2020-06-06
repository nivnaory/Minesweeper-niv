package com.afeka.sm.Minesweeper;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.mineswipper.R;

import java.util.Objects;


public class GameOverActivity extends AppCompatActivity implements Finals, InputFragment.OnDataPass {
    TextView TextResult;
    boolean win;
    SharedPreferences sharedPref;
    int timePassed;
    int level;
    String userName;
    Fragment fragmentInput;
    Fragment fragmentAnimation;
    boolean hasTheUserBrokenARecord;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_layout);
        Intent activity = getIntent();
        sharedPref = GameOverActivity.this.getSharedPreferences(APP_CHOSEN_NAME, Context.MODE_PRIVATE);

        TextResult = findViewById(R.id.GameResult);
        win = Objects.requireNonNull(activity.getExtras()).getBoolean(GAME_RESULT);
        if (win) {
            level = activity.getExtras().getInt(LEVEL_ACTIVITY_KEY);
            timePassed = activity.getExtras().getInt(TIME_PASSED);
            boolean hasTheUserBrokenARecord = hasTheUserBrokenARecord(level, timePassed);
            if (hasTheUserBrokenARecord)
                StartInputFragment();

            TextResult.setText(R.string.Win);
        } else
            TextResult.setText(R.string.Lose);

        StartAnimationFragment();
        Button exitButton = findViewById(R.id.ExitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void StartNewGame(View view) {
        hasTheUserBrokenARecord = hasTheUserBrokenARecord(level, timePassed);
        Intent intent = new Intent(this, DifficultyChooserActivity.class);
        if (hasTheUserBrokenARecord) {
            MineSweeperRecord newRecord = new MineSweeperRecord(userName, timePassed);
            updateRecords(newRecord, level);
        }
        this.startActivity(intent);
    }

    private void updateRecords(MineSweeperRecord newRecord, int level) {
        MineSweeperRecord[] currentLevelRecords = getCurrentLevelRecords(level);
        if (newRecord.getTime() < currentLevelRecords[0].getTime()) { // if first place - move all the elements one index away from the first place
            for (int i = NUM_OF_RECORDS_TO_SAVE - 1; i > 0; i--)
                currentLevelRecords[i] = currentLevelRecords[i - 1];
            currentLevelRecords[0] = newRecord;
        } else if (newRecord.getTime() < currentLevelRecords[1].getTime()) { // same for second place
            for (int i = NUM_OF_RECORDS_TO_SAVE - 1; i > 1; i--)
                currentLevelRecords[i] = currentLevelRecords[i - 1];
            currentLevelRecords[1] = newRecord;
        } else
            currentLevelRecords[2] = newRecord;
        setCurrentLevelRecords(level, currentLevelRecords);
    }

    private MineSweeperRecord[] getCurrentLevelRecords(int level) {
        MineSweeperRecord[] currentLevelRecords = createEmptyRecords();
        switch (level) {
            case EASY_LEVEL:
                currentLevelRecords[0].setTime(sharedPref.getInt(String.valueOf(R.id.EasyFirstPlaceTime), 0));
                currentLevelRecords[0].setName(sharedPref.getString(String.valueOf(R.id.EasyFirstPlaceName), ""));
                currentLevelRecords[1].setTime(sharedPref.getInt(String.valueOf(R.id.EasySecondPlaceTime), 0));
                currentLevelRecords[1].setName(sharedPref.getString(String.valueOf(R.id.EasySecondPlaceName), ""));
                currentLevelRecords[2].setTime(sharedPref.getInt(String.valueOf(R.id.EasyThirdPlaceTime), 0));
                currentLevelRecords[2].setName(sharedPref.getString(String.valueOf(R.id.EasyThirdPlaceName), ""));
                break;
            case MEDIUM_LEVEL:
                currentLevelRecords[0].setTime(sharedPref.getInt(String.valueOf(R.id.MediumFirstPlaceTime), 0));
                currentLevelRecords[0].setName(sharedPref.getString(String.valueOf(R.id.MediumFirstPlaceName), ""));
                currentLevelRecords[1].setTime(sharedPref.getInt(String.valueOf(R.id.MediumSecondPlaceTime), 0));
                currentLevelRecords[1].setName(sharedPref.getString(String.valueOf(R.id.MediumSecondPlaceName), ""));
                currentLevelRecords[2].setTime(sharedPref.getInt(String.valueOf(R.id.MediumThirdPlaceTime), 0));
                currentLevelRecords[2].setName(sharedPref.getString(String.valueOf(R.id.MediumThirdPlaceName), ""));
                break;
            default: // which is HARD_LEVEL
                currentLevelRecords[0].setTime(sharedPref.getInt(String.valueOf(R.id.HardFirstPlaceTime), 0));
                currentLevelRecords[0].setName(sharedPref.getString(String.valueOf(R.id.HardFirstPlaceName), ""));
                currentLevelRecords[1].setTime(sharedPref.getInt(String.valueOf(R.id.HardSecondPlaceTime), 0));
                currentLevelRecords[1].setName(sharedPref.getString(String.valueOf(R.id.HardSecondPlaceName), ""));
                currentLevelRecords[2].setTime(sharedPref.getInt(String.valueOf(R.id.HardThirdPlaceTime), 0));
                currentLevelRecords[2].setName(sharedPref.getString(String.valueOf(R.id.HardThirdPlaceName), ""));
                break;
        }
        return currentLevelRecords;
    }

    private MineSweeperRecord[] createEmptyRecords() {
        MineSweeperRecord[] currentLevelRecords = new MineSweeperRecord[NUM_OF_RECORDS_TO_SAVE];
        for (int i = 0; i < NUM_OF_RECORDS_TO_SAVE; i++)
            currentLevelRecords[i] = new MineSweeperRecord();
        return currentLevelRecords;
    }

    private boolean hasTheUserBrokenARecord(int level, int time) {
        if (!win)
            return false;
        MineSweeperRecord[] currentLevelRecords = getCurrentLevelRecords(level);
        return time < currentLevelRecords[NUM_OF_RECORDS_TO_SAVE - 1].getTime(); // if smaller than last place
    }

    private void setCurrentLevelRecords(int level, MineSweeperRecord[] currentLevelRecords) {
        SharedPreferences.Editor editor = sharedPref.edit();
        switch (level) {
            case EASY_LEVEL:
                editor.putInt(String.valueOf(R.id.EasyFirstPlaceTime), currentLevelRecords[0].getTime());
                editor.putString(String.valueOf(R.id.EasyFirstPlaceName), currentLevelRecords[0].getName());
                editor.putInt(String.valueOf(R.id.EasySecondPlaceTime), currentLevelRecords[1].getTime());
                editor.putString(String.valueOf(R.id.EasySecondPlaceName), currentLevelRecords[1].getName());
                editor.putInt(String.valueOf(R.id.EasyThirdPlaceTime), currentLevelRecords[2].getTime());
                editor.putString(String.valueOf(R.id.EasyThirdPlaceName), currentLevelRecords[2].getName());
                break;
            case MEDIUM_LEVEL:
                editor.putInt(String.valueOf(R.id.MediumFirstPlaceTime), currentLevelRecords[0].getTime());
                editor.putString(String.valueOf(R.id.MediumFirstPlaceName), currentLevelRecords[0].getName());
                editor.putInt(String.valueOf(R.id.MediumSecondPlaceTime), currentLevelRecords[1].getTime());
                editor.putString(String.valueOf(R.id.MediumSecondPlaceName), currentLevelRecords[1].getName());
                editor.putInt(String.valueOf(R.id.MediumThirdPlaceTime), currentLevelRecords[2].getTime());
                editor.putString(String.valueOf(R.id.MediumThirdPlaceName), currentLevelRecords[2].getName());
                break;
            default:
                editor.putInt(String.valueOf(R.id.HardFirstPlaceTime), currentLevelRecords[0].getTime());
                editor.putString(String.valueOf(R.id.HardFirstPlaceName), currentLevelRecords[0].getName());
                editor.putInt(String.valueOf(R.id.HardSecondPlaceTime), currentLevelRecords[1].getTime());
                editor.putString(String.valueOf(R.id.HardSecondPlaceName), currentLevelRecords[1].getName());
                editor.putInt(String.valueOf(R.id.HardThirdPlaceTime), currentLevelRecords[2].getTime());
                editor.putString(String.valueOf(R.id.HardThirdPlaceName), currentLevelRecords[2].getName());
                break;
        }
        editor.apply();
    }

    public void StartInputFragment() {
        FragmentManager fm = getSupportFragmentManager();
        InputFragment alertDialog = InputFragment.newInstance(String.valueOf(R.string.CongratulationsMessage));
        alertDialog.show(fm, FRAGMENT_ALERT);
    }

    public void StartAnimationFragment() {
        fragmentAnimation = new AnimationFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(GAME_RESULT, win);
        fragmentAnimation.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.Animation_container, fragmentAnimation);
        transaction.commit();
    }

    @Override
    public void onDataPass(String data) {
        userName = data;
    }
}