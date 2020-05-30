package com.afeka.sm.Minesweeper;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mineswipper.R;
public class InputFragment extends Fragment {
    SharedPreferences sharedPref;
    Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        button=view.findViewById(R.id.Ok_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showInput(v);
            }
        });
    }
    public String showInput(View view){
        EditText text=this.getActivity().findViewById(R.id.Text_Input);
        String name=text.getText().toString();
        return name;
    }
}