package com.Teachers.HaziraKhataByGk.Routine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.Teachers.HaziraKhataByGk.Constant.Constant;
import com.Teachers.HaziraKhataByGk.HelperClassess.ViewUtils.CustomViewPager;
import com.Teachers.HaziraKhataByGk.HelperClassess.ViewUtils.ViewPagerAdapter;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.Widget.BaseFullScreenDialog;

import butterknife.BindView;

public class RoutineInputDialog extends BaseFullScreenDialog {


    @BindView(R.id.rb_class_routine)
    RadioButton rbClassRoutine;
    @BindView(R.id.rb_exam_routine)
    RadioButton rbExamRoutine;
    @BindView(R.id.rb_admin_routine)
    RadioButton rbAdminRoutine;
    @BindView(R.id.et_subject)
    EditText etSubject;
    @BindView(R.id.et_room)
    EditText etRoom;
    @BindView(R.id.bt_from_time)
    AppCompatButton btnFromTime;
    @BindView(R.id.bt_to_time)
    AppCompatButton btnToTime;
    @BindView(R.id.weekdays)



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
