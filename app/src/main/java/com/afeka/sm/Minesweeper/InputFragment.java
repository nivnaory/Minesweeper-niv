package com.afeka.sm.Minesweeper;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mineswipper.R;
public class InputFragment extends DialogFragment {
    SharedPreferences sharedPref;
    private EditText editText;
    private Toast toast;
    OnDataPass dataPasser;

    public interface OnDataPass {
        void onDataPass(String data);
    }

    public InputFragment() {
        // Empty constructor is required for DialogFragment
    }

    public static InputFragment newInstance(String title) {
        InputFragment frag = new InputFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }


    //create A dialog Input
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage("Please Enter Your Name");
        editText = new EditText(this.getActivity());
        alertDialogBuilder.setView(editText);
        //alertDialogBuilder.setIcon(R.drawable.winner);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Pass the Name to GameOverActivity
                dataPasser.onDataPass(editText.getText().toString());

                toast.makeText(getActivity(),
                        "Saved", Toast.LENGTH_LONG)
                        .show();
            }
        });
        return alertDialogBuilder.create();
    }


       @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }


}