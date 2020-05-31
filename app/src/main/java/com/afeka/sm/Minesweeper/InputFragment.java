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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mineswipper.R;
public class InputFragment extends DialogFragment {
    SharedPreferences sharedPref;
    private View view;
    private EditText editText;
    String name;
    private Toast toast;
    OnDataPass dataPasser;

    public interface OnDataPass {
        public void onDataPass(String data);
    }

    public InputFragment(){
        // Empty constructor is required for DialogFragment
    }
    public static InputFragment newInstance(String title) {
        InputFragment frag = new InputFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage("Please Enter Your Name");
        editText = new EditText(this.getActivity());
        alertDialogBuilder.setView(editText);
        alertDialogBuilder.setIcon(R.drawable.winner);
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









/*
    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     //   view = inflater.inflate(R.layout.fragment_edit_name, container, false);\
        editText.setOnEditorActionListener(this);
        return view;
    }
/*
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        editText = (EditText) view.findViewById(R.id.txt_your_name);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        editText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text back to activity through the implemented listener
            EditNameDialogListener listener = (EditNameDialogListener) getActivity();
            listener.onFinishEditDialog(editText.getText().toString());
            // Close the dialog and return back to the parent activity
            dismiss();
            return true;
        }
        return false;
    }


  //  @Override
    // start only when on create of parant is finished
/*
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
                        Bundle  bundle=new Bundle();
                        bundle.putString("NAME_KEY",name);
                        setArguments(bundle);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

        //Pass name to Activity of this fragent

    }

 */


}