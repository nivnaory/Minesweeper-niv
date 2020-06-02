package com.afeka.sm.Minesweeper;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mineswipper.R;

public class AnimationFramgent extends Fragment {
boolean win=true;
View view;
ImageView image;
Context context;
public AnimationFramgent(){

}
 @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     // Inflate the layout for this fragment


     view=inflater.inflate(R.layout.animation_fragment, container, false);
      return view;
    }


    @Override
    public void onStart() {
        win=getArguments().getBoolean("RESULT");
        super.onStart();
        if (win) {
           // ((AnimationDrawable) getView().findViewById(R.id.Lose).getBackground()).start();
            image=getView().findViewById(R.id.animation_id);
            ((AnimationDrawable)getView().findViewById(R.id.animation_id).getBackground()).start();

        }else{
           // ((AnimationDrawable) getView().findViewById(R.id.Lose).getBackground()).start();
            ((AnimationDrawable)getView().findViewById(R.id.animation_id).getBackground()).start();
        }
    }


}
