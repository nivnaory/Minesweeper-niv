package com.afeka.sm.Minesweeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mineswipper.R;


public class DifficultyChooserActivity extends AppCompatActivity implements Finals {
    int level;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.difficulty_chooser_layout);
        sharedPref = DifficultyChooserActivity.this.getSharedPreferences(APP_CHOSEN_NAME, Context.MODE_PRIVATE);

        int lastLevelThatWasChosen = sharedPref.getInt(String.valueOf(R.integer.LastChosenLevel), 0);
        boolean hasTheUserAlreadyChoseALevel = lastLevelThatWasChosen != INITIAL_VALUE_OF_CHOSEN_LEVEL_BY_THE_USER;
        if (hasTheUserAlreadyChoseALevel)
            markLastChosenLevel(lastLevelThatWasChosen);
    }


    public void changeFragment(View view){
        Log.d("IN ChangeFragment: ","Niv naory ");
        Fragment fragment;

        fragment=new HighlightsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.Fhighlight,fragment)
                .commit();
    }

    private void markLastChosenLevel(int chosenLevel) {
        Button button;
        switch (chosenLevel) {
            case EASY_LEVEL:
                button = findViewById(R.id.Easy);
                break;
            case MEDIUM_LEVEL:
                button = findViewById(R.id.Medium);
                break;
            default: // which is Hard level
                button = findViewById(R.id.Hard);
                break;
        }
        button.setBackgroundResource(R.color.ChosenButtonColor);
    }

    public void startGame(View view) {
        Intent getNameScreenIntent = new Intent(this, GameActivity.class);
        switch (view.getId()) {
            case R.id.Easy:
                level = EASY_LEVEL;
                break;
            case R.id.Medium:
                level = MEDIUM_LEVEL;
                break;
            default: // which is Hard
                level = HARD_LEVEL;
                break;
        }
        getNameScreenIntent.putExtra(LEVEL_ACTIVITY_KEY, level);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(String.valueOf(R.integer.LastChosenLevel), level);
        editor.apply();
        startActivity(getNameScreenIntent);
    }
}