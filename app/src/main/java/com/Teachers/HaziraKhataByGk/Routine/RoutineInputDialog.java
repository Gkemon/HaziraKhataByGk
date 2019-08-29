package com.Teachers.HaziraKhataByGk.Routine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Teachers.HaziraKhataByGk.Constant.Constant;
import com.Teachers.HaziraKhataByGk.HelperClassess.ViewUtils.CustomViewPager;
import com.Teachers.HaziraKhataByGk.HelperClassess.ViewUtils.ViewPagerAdapter;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.Widget.BaseFullScreenDialog;

public class RoutineInputDialog extends BaseFullScreenDialog {


    public static void showDialog(android.support.v4.app.FragmentManager manager) {

        RoutineInputDialog dialog = new RoutineInputDialog();
        FragmentTransaction ft = manager.beginTransaction();
        dialog.show(ft, "RoutineInputDialog");

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.dialog_add_routine_item, container, false);



        return view;
    }

}
