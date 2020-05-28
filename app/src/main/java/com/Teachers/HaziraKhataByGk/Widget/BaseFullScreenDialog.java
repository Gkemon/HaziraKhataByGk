package com.Teachers.HaziraKhataByGk.Widget;

import android.app.Dialog;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.Routine.AllRoutineShowingDialog;


public class BaseFullScreenDialog extends DialogFragment {

    public static void showDialog(FragmentManager manager) {

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
            if (dialog.getWindow() != null)
                dialog.getWindow().setLayout(width, height);
        }
    }


}