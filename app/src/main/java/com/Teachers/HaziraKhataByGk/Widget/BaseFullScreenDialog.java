package com.Teachers.HaziraKhataByGk.Widget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.Routine.RoutineShowingFullScreenDialog;


public class BaseFullScreenDialog extends DialogFragment {

    public static void showDialog(android.support.v4.app.FragmentManager manager){

        RoutineShowingFullScreenDialog dialog = new RoutineShowingFullScreenDialog();
        FragmentTransaction ft = manager.beginTransaction();
        dialog.show(ft, "RoutineShowingFullScreenDialog");

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            if(dialog.getWindow()!=null)
            dialog.getWindow().setLayout(width, height);
        }
    }




}
