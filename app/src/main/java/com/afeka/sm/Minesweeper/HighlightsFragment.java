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
        View view = inflater.inflate(R.layout.highlights_fragment, container, false);
        sharedPref = this.getActivity().getSharedPreferences(APP_CHOSEN_NAME, Context.MODE_PRIVATE);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boolean hasAnyoneBrokenARecord = getArguments().getBoolean(HAS_THE_USER_BROKEN_A_RECORD);
        if (hasAnyoneBrokenARecord) {
            int level = getArguments().getInt(LEVEL_ACTIVITY_KEY, 0);
            updateFragmentFields(view, level);
        }
    }

    public void updateFragmentFields(View view, int level) {
        MineSweeperRecord[] currentRecords = getCurrentLevelRecords(level);
        TextView easyFirstPlaceName = (TextView) view.findViewById(R.id.EasyFirstPlaceName);
        TextView easyFirstPlaceTime = (TextView) view.findViewById(R.id.EasyFirstPlaceTime);


        easyFirstPlaceName.setText(currentRecords[0].getName());
        easyFirstPlaceTime.setText("" + currentRecords[0].getTime());
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