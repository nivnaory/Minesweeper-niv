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
import android.widget.TextView;

import com.example.mineswipper.R;

import java.util.Objects;

public class HighlightsFragment extends Fragment implements Finals {
    SharedPreferences sharedPref;

    public HighlightsFragment newInstance() {
        return new HighlightsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.highlights_fragment, container, false);
        sharedPref = this.getActivity().getSharedPreferences(APP_CHOSEN_NAME, Context.MODE_PRIVATE);

//        String firstPlaceTest = sharedPref.getString(R.id.EasyFirstPlaceTime);
//        TextView test = this.getActivity().findViewById(R.id.EasyFirstPlaceTime);
//        test.setText();
//        String test = getArguments().getString("Test");
//        Log.d("test", test);
        return view;
    }
}
