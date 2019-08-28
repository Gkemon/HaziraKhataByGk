package com.Teachers.HaziraKhataByGk.Widget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.Routine.AllRoutineShowingDialog;


public class BaseFullScreenDialog extends DialogFragment {

    public static void showDialog(android.support.v4.app.FragmentManager manager){

        AllRoutineShowingDialog dialog = new AllRoutineShowingDialog();
        FragmentTransaction ft = manager.beginTransaction();
        dialog.show(ft, "AllRoutineShowingDialog");

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
