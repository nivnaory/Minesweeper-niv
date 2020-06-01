package com.afeka.sm.Minesweeper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.content.Intent;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import com.example.mineswipper.R;

public class GameActivity extends AppCompatActivity implements Finals {
    Game game;
    GridView gridView;
    TileAdapter tileAdapter;
    TextView numOfFlagsView;
    TextView levelView;
    TextView timerView;
    Timer timer;
    boolean haveTheUserClickedForTheFirstTime = false;
    int timeSoFar;
    int currentTime;
    int level;
    SharedPreferences sharedPref;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);

        Intent activityCalled = getIntent();
        sharedPref = GameActivity.this.getSharedPreferences(APP_CHOSEN_NAME, Context.MODE_PRIVATE);
        level = Objects.requireNonNull(activityCalled.getExtras()).getInt(LEVEL_ACTIVITY_KEY);

//        initiateRecords(); // for testing only!! run once

        game = new Game(level);
        handleUpperLayout(level);
        handleGridView();
    }

    private void handleUpperLayout(int level) {
        handleNumOfFlagsView();
        handleLevelView(level);
    }

    private void handleNumOfFlagsView() {
        numOfFlagsView = findViewById(R.id.NumOfFlags);
        String numOfFlags = Integer.toString(game.getBoard().getNumberOfFlags());
        String numOfFlagsLabel = getResources().getString(R.string.NumOfFlags);
        numOfFlagsView.setText(String.format("%s %s", numOfFlagsLabel, numOfFlags));
    }

    private void handleLevelView(int level) {
        levelView = findViewById(R.id.Level);
        switch (level) {
            case EASY_LEVEL:
                levelView.setText(R.string.EasyLevel);
                break;
            case MEDIUM_LEVEL:
                levelView.setText(R.string.MediumLevel);
                break;
            default:
                levelView.setText(R.string.HardLevel);
                break;
        }
    }

    private void handleGridView() {
        timerView = findViewById(R.id.Timer);
        gridView = findViewById(R.id.GridView);
        tileAdapter = new TileAdapter(this, game.getBoard());
        gridView.setAdapter(tileAdapter);
        gridView.setNumColumns(game.getBoard().getSize());

        handleShortClick();

        handleLongClick();
    }

    private void handleShortClick() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!haveTheUserClickedForTheFirstTime) {
                    haveTheUserClickedForTheFirstTime = true;
                    timeSoFar = 0;
                    runTimer();
                }
                String result = game.playTile(position);
                if (!result.equals(GAME_STATUS_PLAY)) // Which means the user has won or lost
                    initiateGameOverActivity(result);
                updateNumOfFlagsView(game.getBoard().getNumberOfFlags());
                tileAdapter.notifyDataSetChanged();
            }
        });
    }

    private void handleLongClick() { // put\remove a flag
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                game.getBoard().updateNumberOfFlags(position);
                tileAdapter.notifyDataSetChanged();
                updateNumOfFlagsView(game.getBoard().getNumberOfFlags());
                return true;
            }
        });
    }

    private void runTimer() {
        timer = new Timer();
        timer.schedule(new mineSweeperTimerTask(), 0, 1000);
    }

    class mineSweeperTimerTask extends TimerTask {
        private long firstClickTime = System.currentTimeMillis();

        @Override
        public void run() {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    currentTime = (int) ((System.currentTimeMillis() - firstClickTime) / 1000 + timeSoFar);
                    timerView.setText(String.format("Timer:\n  %03d", currentTime));
                }
            });
        }
    }

    private void updateNumOfFlagsView(int numberOfFlags) {
        numOfFlagsView.setText("Num Of Flags: " + Integer.toString(numberOfFlags));
    }

    public void initiateGameOverActivity(String status) {
        Intent intent = new Intent(this, GameOverActivity.class);
        boolean hasWon = status.equals(GAME_STATUS_WIN);
        intent.putExtra(GAME_RESULT, hasWon);
        intent.putExtra(TIME_PASSED, currentTime);
        intent.putExtra(LEVEL_ACTIVITY_KEY, level);
        finishAffinity();
        this.startActivity(intent);
    }

    public void initiateRecords() { // TODO for testing only!! run once
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(String.valueOf(R.id.EasyFirstPlaceTime), INITIAL_RECORD_VALUE);
        editor.putInt(String.valueOf(R.id.EasySecondPlaceTime), INITIAL_RECORD_VALUE);
        editor.putInt(String.valueOf(R.id.EasyThirdPlaceTime), INITIAL_RECORD_VALUE);
        editor.putInt(String.valueOf(R.id.MediumFirstPlaceTime), INITIAL_RECORD_VALUE);
        editor.putInt(String.valueOf(R.id.MediumSecondPlaceTime), INITIAL_RECORD_VALUE);
        editor.putInt(String.valueOf(R.id.MediumThirdPlaceTime), INITIAL_RECORD_VALUE);
        editor.putInt(String.valueOf(R.id.HardFirstPlaceTime), INITIAL_RECORD_VALUE);
        editor.putInt(String.valueOf(R.id.HardSecondPlaceTime), INITIAL_RECORD_VALUE);
        editor.putInt(String.valueOf(R.id.HardThirdPlaceTime), INITIAL_RECORD_VALUE);

        editor.putString(String.valueOf(R.id.EasyFirstPlaceName), "Initial");
        editor.putString(String.valueOf(R.id.EasySecondPlaceName), "Initial");
        editor.putString(String.valueOf(R.id.EasyThirdPlaceName), "Initial");
        editor.putString(String.valueOf(R.id.MediumFirstPlaceName), "Initial");
        editor.putString(String.valueOf(R.id.MediumSecondPlaceName), "Initial");
        editor.putString(String.valueOf(R.id.MediumThirdPlaceName), "Initial");
        editor.putString(String.valueOf(R.id.HardFirstPlaceName), "Initial");
        editor.putString(String.valueOf(R.id.HardSecondPlaceName), "Initial");
        editor.putString(String.valueOf(R.id.HardThirdPlaceName), "Initial");
        editor.apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timeSoFar = currentTime;
        timer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (haveTheUserClickedForTheFirstTime) // so the timer will run only after the user clicks
            runTimer();
    }
}
