package com.afeka.sm.Minesweeper;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.mineswipper.R;

public class AnimationFragment extends Fragment implements Finals {
    boolean win = true;
    View view;

    public AnimationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.animation_fragment, container, false);
        return view;
    }


    @Override
    public void onStart() {
        win = getArguments().getBoolean(GAME_RESULT);
        super.onStart();

        ImageView ivLoader = (ImageView) getActivity().findViewById(R.id.gameOverAnimation);
        int resource = win ? R.drawable.win : R.drawable.lose;
        ivLoader.setBackgroundResource(resource);
        AnimationDrawable frameAnimation = (AnimationDrawable) ivLoader.getBackground();
        frameAnimation.start();
    }
}