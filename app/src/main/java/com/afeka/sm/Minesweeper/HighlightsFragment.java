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
        sharedPref = this.getActivity().getSharedPreferences(APP_CHOSEN_NAME, Context.MODE_PRIVATE);
        return inflater.inflate(R.layout.highlights_fragment, container, false);
    }

}
