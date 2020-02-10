package com.Teachers.HaziraKhataByGk.Routine;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.util.Log;
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
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsDateTime;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.Widget.BaseFullScreenDialog;
import com.gk.emon.android.BanglaDaysPicker;
import com.google.firebase.database.collection.LLRBNode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import petrov.kristiyan.colorpicker.ColorPicker;

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
    private ColorPicker colorPicker;

    private RoutineItem routineItem;

    public static void showDialog(FragmentManager manager) {

        RoutineInputDialog dialog = new RoutineInputDialog();
        FragmentTransaction ft = manager.beginTransaction();
        dialog.show(ft, RoutineInputDialog.class.getSimpleName());

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
    public void showStartTimeDialog() {

        DialogUtils.showTimeDialog(0, 0, getContext(),
                (timePicker, hour, min) -> {
                    routineItem.setStartTime(UtilsDateTime.getUnixTimeStampFromHourMin(hour, min));
                });

    }

    @OnClick(R.id.bt_to_time)
    public void showEndTimeDialog() {
        DialogUtils.showTimeDialog(0, 0, getContext(),
                (timePicker, hour, min) -> {
                    routineItem.setEndTime(UtilsDateTime.getUnixTimeStampFromHourMin(hour, min));
                });
    }

    @OnClick(R.id.bt_color_select)
    public void selectColor(AppCompatButton btnColorSelect){

        if(getActivity()!=null)
            colorPicker = new ColorPicker(getActivity());
        if(colorPicker==null)return;

        colorPicker
                .setDefaultColorButton(Color.RED)
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            public void onChooseColor(int position,int color) {
                routineItem.setColor(color);

                btnColorSelect.getBackground().mutate().setColorFilter(new
                        PorterDuffColorFilter(color, PorterDuff.Mode.SRC));

            }

            @Override
            public void onCancel(){
                routineItem.setColor(Color.RED);
            }
        })
        .show();
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
        routineItem.setDayList(banglaDaysPicker.getSelectedDays());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.dialog_add_routine_item, container, false);
        ButterKnife.bind(this, view);

        initData();

        return view;
    }

    private void initData() {

        routineItem = new RoutineItem();
    }

}
