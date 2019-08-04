package com.Teachers.HaziraKhataByGk.Routine;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.Widget.BaseFullScreenDialog;

public class RoutineShowingFullScreenDialog extends BaseFullScreenDialog {

    public static void showDialog(android.support.v4.app.FragmentManager manager){

        RoutineShowingFullScreenDialog dialog = new RoutineShowingFullScreenDialog();
        FragmentTransaction ft = manager.beginTransaction();
        dialog.show(ft, "RoutineShowingFullScreenDialog");

    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.dialog_show_routine, container, false);

        return view;
    }

}
