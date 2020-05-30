package com.afeka.sm.Minesweeper;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mineswipper.R;
public class InputFragment extends DialogFragment {
    SharedPreferences sharedPref;
    private View view;
    private Button button;
    String name;
    private EditText editText;
    private Toast toast;

    @NonNull
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.game_over_layout, container, false);
        return view;
    }

    @Override
    // start only when oncreate of parant is finished
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        AlertDialog.Builder builder = new AlertDialog.Builder(
                getActivity());
        builder.setTitle("You Brake a Record!!");
        builder.setMessage("Please Enter Your Name");
        builder.setIcon(R.drawable.winner);
        editText = new EditText(this.getActivity());
        builder.setView(editText);

        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {

                        toast.makeText(getActivity(),
                                "Saved", Toast.LENGTH_LONG)
                                .show();



                        name = editText.getText().toString();
                        Log.d("Name:", name);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

}