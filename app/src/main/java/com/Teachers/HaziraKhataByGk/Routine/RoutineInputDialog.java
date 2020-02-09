package com.Teachers.HaziraKhataByGk.Routine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.Teachers.HaziraKhataByGk.Constant.Constant;
import com.Teachers.HaziraKhataByGk.HelperClassess.DialogUtils;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.Widget.BaseFullScreenDialog;
import com.gk.emon.android.BanglaDaysPicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    BanglaDaysPicker banglaDaysPicker;
    @BindView(R.id.bt_date_select)
    AppCompatButton btnDateSelect;
    @BindView(R.id.bt_color_select)
    AppCompatButton btnColorSelect;
    @BindView(R.id.et_detail)
    EditText etDetails;

    RoutineItem routineItem;

    public static void showDialog(FragmentManager manager) {

        RoutineInputDialog dialog = new RoutineInputDialog();
        FragmentTransaction ft = manager.beginTransaction();
        dialog.show(ft, "RoutineInputDialog");

    }

    @OnClick(R.id.rb_temporary_routine)
    public void showDateSelectButton() {
        btnDateSelect.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.rb_permanent_routine)
    public void hideDateSelecButton() {
        btnDateSelect.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_tutorial)
    public void showTutorial() {

    }

    @OnClick(R.id.btn_delete_routine)
    public void deleteRoutine() {

    }

    @OnClick(R.id.cancel)
    public void cancel() {
        dismiss();
    }

    @OnClick(R.id.bt_from_time)
    public void showFromTimeDialog() {
        DialogUtils.showDateDialog(null, getContext(), (view, year, month, dayOfMonth) -> {

        });
    }

    @OnClick(R.id.bt_to_time)
    public void showToTimeDialog() {

    }

    @OnClick(R.id.save)
    public void saveRoutine() {


        if (rbClassRoutine.isSelected()) {
            routineItem.setType(Constant.ROUTINE_TYPE_CLASS);
        } else if (rbExamRoutine.isSelected()) {
            routineItem.setType(Constant.ROUTINE_TYPE_EXAM);
        } else routineItem.setType(Constant.ROUTINE_TYPE_ADMINISTRATIONAL);


        routineItem.setName(etSubject.getText().toString());
        routineItem.setLocation((etRoom.getText().toString()));
        ///routineItem.setStartTime();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.dialog_add_routine_item, container, false);
        ButterKnife.bind(this, view);

        initData();

        return view;
    }

    void initData() {
        routineItem = new RoutineItem();
    }

}
