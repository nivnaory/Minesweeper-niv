package com.afeka.sm.Minesweeper;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.mineswipper.R;

import java.util.Objects;

public class HighlightsFragment extends Fragment implements Finals {
    SharedPreferences sharedPref;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        sharedPref = this.getActivity().getSharedPreferences(APP_CHOSEN_NAME, Context.MODE_PRIVATE);
        return inflater.inflate(R.layout.highlights_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateRecords(view);
    }

    private void updateRecords(View view) {
        updateEasyLevelRecords(view);
        updateMediumLevelRecords(view);
        updateHardLevelRecords(view);
    }


    private void updateEasyLevelRecords(View view) {
        MineSweeperRecord[] currentRecords = getCurrentLevelRecords(EASY_LEVEL);
        TextView easyFirstPlaceName = view.findViewById(R.id.EasyFirstPlaceName);
        TextView easyFirstPlaceTime = view.findViewById(R.id.EasyFirstPlaceTime);
        TextView easySecondPlaceName = view.findViewById(R.id.EasySecondPlaceName);
        TextView easySecondPlaceTime = view.findViewById(R.id.EasySecondPlaceTime);
        TextView easyThirdPlaceName = view.findViewById(R.id.EasyThirdPlaceName);
        TextView easyThirdPlaceTime = view.findViewById(R.id.EasyThirdPlaceTime);

        easyFirstPlaceName.setText(currentRecords[0].getName());
        easyFirstPlaceTime.setText("" + currentRecords[0].getTime());
        easySecondPlaceName.setText(currentRecords[1].getName());
        easySecondPlaceTime.setText("" + currentRecords[1].getTime());
        easyThirdPlaceName.setText(currentRecords[2].getName());
        easyThirdPlaceTime.setText("" + currentRecords[2].getTime());
    }

    private void updateMediumLevelRecords(View view) {
        MineSweeperRecord[] currentRecords = getCurrentLevelRecords(MEDIUM_LEVEL);
        TextView mediumFirstPlaceName = view.findViewById(R.id.MediumFirstPlaceName);
        TextView mediumFirstPlaceTime = view.findViewById(R.id.MediumFirstPlaceTime);
        TextView mediumSecondPlaceName = view.findViewById(R.id.MediumSecondPlaceName);
        TextView mediumSecondPlaceTime = view.findViewById(R.id.MediumSecondPlaceTime);
        TextView mediumThirdPlaceName = view.findViewById(R.id.MediumThirdPlaceName);
        TextView mediumThirdPlaceTime = view.findViewById(R.id.MediumThirdPlaceTime);

        mediumFirstPlaceName.setText(currentRecords[0].getName());
        mediumFirstPlaceTime.setText("" + currentRecords[0].getTime());
        mediumSecondPlaceName.setText(currentRecords[1].getName());
        mediumSecondPlaceTime.setText("" + currentRecords[1].getTime());
        mediumThirdPlaceName.setText(currentRecords[2].getName());
        mediumThirdPlaceTime.setText("" + currentRecords[2].getTime());
    }

    private void updateHardLevelRecords(View view) {
        MineSweeperRecord[] currentRecords = getCurrentLevelRecords(HARD_LEVEL);
        TextView hardFirstPlaceName = view.findViewById(R.id.HardFirstPlaceName);
        TextView hardFirstPlaceTime = view.findViewById(R.id.HardFirstPlaceTime);
        TextView hardSecondPlaceName = view.findViewById(R.id.HardSecondPlaceName);
        TextView hardSecondPlaceTime = view.findViewById(R.id.HardSecondPlaceTime);
        TextView hardThirdPlaceName = view.findViewById(R.id.HardThirdPlaceName);
        TextView hardThirdPlaceTime = view.findViewById(R.id.HardThirdPlaceTime);

        hardFirstPlaceName.setText(currentRecords[0].getName());
        hardFirstPlaceTime.setText("" + currentRecords[0].getTime());
        hardSecondPlaceName.setText(currentRecords[1].getName());
        hardSecondPlaceTime.setText("" + currentRecords[1].getTime());
        hardThirdPlaceName.setText(currentRecords[2].getName());
        hardThirdPlaceTime.setText("" + currentRecords[2].getTime());
    }


    public MineSweeperRecord[] getCurrentLevelRecords(int level) {
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

    public MineSweeperRecord[] createEmptyRecords() {
        MineSweeperRecord[] currentLevelRecords = new MineSweeperRecord[NUM_OF_RECORDS_TO_SAVE];
        for (int i = 0; i < NUM_OF_RECORDS_TO_SAVE; i++)
            currentLevelRecords[i] = new MineSweeperRecord();
        return currentLevelRecords;
    }
}