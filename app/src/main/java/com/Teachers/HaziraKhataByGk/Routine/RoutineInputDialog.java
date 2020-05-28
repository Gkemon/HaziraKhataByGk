package com.Teachers.HaziraKhataByGk.Routine;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.Teachers.HaziraKhataByGk.HelperClassess.DialogUtils;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsDateTime;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.Widget.BaseFullScreenDialog;
import com.gk.emon.android.BanglaDaysPicker;

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
    private RoutineViewModel routineViewModel;

    public static void showDialog(FragmentManager manager) {

        RoutineInputDialog dialog = new RoutineInputDialog();
        FragmentTransaction ft = manager.beginTransaction();
        dialog.show(ft, RoutineInputDialog.class.getSimpleName());

    }

    @OnClick(R.id.bt_date_select)
    void showDate(AppCompatButton btn) {
        DialogUtils.showDateDialog(null, getContext(), (datePicker, year, month, dayOfMonth) -> {
            btn.setText(UtilsDateTime.getSimpleDateText(year, month, dayOfMonth));
            try {
                routineItem.setDateIfTemporary(UtilsDateTime.getDate(year, month, dayOfMonth));
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    @OnClick(R.id.rb_temporary_routine)
    void showDateSelectButton() {
        routineItem.setPermanent(false);
        banglaDaysPicker.setVisibility(View.GONE);
        btnDateSelect.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.rb_permanent_routine)
    void hideDateSelectButton() {
        routineItem.setPermanent(true);
        banglaDaysPicker.setVisibility(View.VISIBLE);
        btnDateSelect.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_tutorial)
    void showTutorial() {

    }

    @OnClick(R.id.btn_delete_routine)
    void deleteRoutine() {

    }

    @OnClick(R.id.cancel)
    void cancel() {
        dismiss();
    }

    @OnClick(R.id.bt_from_time)
    void showStartTimeDialog() {

        DialogUtils.showTimeDialog(0, 0, getContext(),
                (timePicker, hourOfDay, min) -> {

                    btnFromTime.setText(UtilsDateTime.getAMPMTimeFromCalender(UtilsDateTime.
                            getUnixTimeStampFromHourMin(hourOfDay, min)));
                    routineItem.setStartTime(UtilsDateTime.getUnixTimeStampFromHourMin(hourOfDay, min));
                });

    }

    @OnClick(R.id.bt_to_time)
    void showEndTimeDialog() {
        DialogUtils.showTimeDialog(0, 0, getContext(),
                (timePicker, hourOfDay, min) -> {
                    btnToTime.setText(UtilsDateTime.getAMPMTimeFromCalender(UtilsDateTime.
                            getUnixTimeStampFromHourMin(hourOfDay, min)));
                    routineItem.setEndTime(UtilsDateTime.getUnixTimeStampFromHourMin(hourOfDay, min));
                });
    }

    @OnClick(R.id.bt_color_select)
    void selectColor(AppCompatButton btnColorSelect) {

        if (getActivity() != null)
            colorPicker = new ColorPicker(getActivity());
        if (colorPicker == null) return;

        colorPicker
                .setDefaultColorButton(Color.RED)
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    public void onChooseColor(int position, int color) {
                        routineItem.setColor(color);

                        btnColorSelect.getBackground().mutate().setColorFilter(new
                                PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
                    }

                    @Override
                    public void onCancel() {
                        routineItem.setColor(Color.RED);
                    }
                })
                .show();
    }

    @OnClick(R.id.save)
    void saveRoutine() {

        if (rbClassRoutine.isSelected()) {
            routineItem.setType(RoutineConstant.ROUTINE_TYPE_CLASS);
        } else if (rbExamRoutine.isSelected()) {
            routineItem.setType(RoutineConstant.ROUTINE_TYPE_EXAM);
        } else routineItem.setType(RoutineConstant.ROUTINE_TYPE_ADMINISTRATION);


        routineItem.setName(etSubject.getText().toString());
        routineItem.setDetails(etDetails.getText().toString());
        routineItem.setLocation((etRoom.getText().toString()));

        if(routineItem.isPermanent())
        routineItem.setSelectedDayList(banglaDaysPicker.getSelectedDays());

        //if (isValidated())
        routineViewModel.insert(routineItem);
        dismiss();


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.dialog_add_routine_item, container, false);
        ButterKnife.bind(this, view);
        rbClassRoutine.setSelected(true);

        initData();

        return view;
    }

    private boolean isValidated() {
        if (!UtilsCommon.isValideString(routineItem.getName())) {
            etSubject.setError(getString(R.string.please_input_subject));
            return false;
        }


        if (routineItem.getStartTime() == null ||
                btnFromTime.getText().toString().equals(getString(R.string.text_click))) {
            UtilsCommon.showToast(getString(R.string.please_select_start_time));
            btnToTime.requestFocus();
            return false;
        }

        if (routineItem.getEndTime() == null ||
                btnToTime.getText().toString().equals(getString(R.string.text_click))) {
            UtilsCommon.showToast(getString(R.string.please_select_end_time));
            btnToTime.requestFocus();
            return false;
        }

        if(routineItem.getEndTime().getTime().getTime()
                <routineItem.getStartTime().getTime().getTime()){
            UtilsCommon.showToast("শুরুর সময় শেষের সময় থেকে ছোট");
            return false;
        }

        return true;


    }

    private void initData() {
        routineViewModel = new ViewModelProvider(this).get(RoutineViewModel.class);
        routineItem = new RoutineItem();
    }

}
