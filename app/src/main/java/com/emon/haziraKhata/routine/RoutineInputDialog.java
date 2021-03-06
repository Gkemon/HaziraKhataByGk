package com.emon.haziraKhata.routine;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.emon.haziraKhata.HelperClasses.DialogUtils;
import com.emon.haziraKhata.HelperClasses.UtilsCommon;
import com.emon.haziraKhata.HelperClasses.UtilsDateTime;
import com.emon.haziraKhata.R;
import com.emon.haziraKhata.Widget.BaseFullScreenDialog;
import com.gk.emon.android.BanglaDaysPicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import petrov.kristiyan.colorpicker.ColorPicker;

public class RoutineInputDialog extends BaseFullScreenDialog {


    @BindView(R.id.rb_class_routine)
    RadioButton rbClassRoutine;
    @BindView(R.id.cb_trigger_alarm)
    CheckBox cbTriggerAlarm;
    @BindView(R.id.rb_exam_routine)
    RadioButton rbExamRoutine;
    @BindView(R.id.rb_admin_routine)
    RadioButton rbAdminRoutine;
    @BindView(R.id.rb_others_routine)
    RadioButton rbOtherRoutine;
    @BindView(R.id.rb_permanent_routine)
    RadioButton rbPermanentRoutine;
    @BindView(R.id.rb_temporary_routine)
    RadioButton rbTemporaryRoutine;
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
    @BindView(R.id.btn_delete_routine)
    FloatingActionButton btnDeleteRoutine;

    private ColorPicker colorPicker;

    private boolean isEditMode = false;
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

    @OnCheckedChanged(R.id.cb_trigger_alarm)
    void handleAlarm(CheckBox checkBox) {
        routineItem.setTriggerAlarm(checkBox != null && checkBox.isChecked());
    }

    @OnClick(R.id.btn_delete_routine)
    void deleteRoutine(FloatingActionButton btn) {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("রুটিনটি ডিলিট করতে চান?");
        alertDialog.setIcon(R.drawable.warnig_for_delete);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ডিলিট", (dialogInterface, i) -> {
            routineViewModel.delete(routineItem);
            dialogInterface.dismiss();
            routineViewModel.setSelectedRoutineItem(null);
            super.dismiss();
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "বাদ",
                (dialogInterface, i) -> dialogInterface.dismiss());
        alertDialog.show();
    }

    @OnCheckedChanged({R.id.rb_admin_routine, R.id.rb_class_routine, R.id.rb_exam_routine,
            R.id.rb_temporary_routine, R.id.rb_permanent_routine, R.id.rb_others_routine})
    public void onCheckedChanged(RadioButton radioButton, boolean isChecked) {
        if (isChecked) {
            switch (radioButton.getId()) {
                case R.id.rb_class_routine:
                    routineItem.setType(RoutineConstant.ROUTINE_TYPE_CLASS);
                    break;
                case R.id.rb_exam_routine:
                    routineItem.setType(RoutineConstant.ROUTINE_TYPE_EXAM);
                    break;
                case R.id.rb_admin_routine:
                    routineItem.setType(RoutineConstant.ROUTINE_TYPE_ADMINISTRATION);
                    break;
                case R.id.rb_others_routine:
                    routineItem.setType(RoutineConstant.ROUTINE_TYPE_OTHER);
                    break;
                case R.id.rb_temporary_routine: {
                    routineItem.setPermanent(false);
                    routineItem.setSelectedDayList(null);
                    banglaDaysPicker.setVisibility(View.GONE);
                    btnDateSelect.setVisibility(View.VISIBLE);
                    break;
                }
                case R.id.rb_permanent_routine: {
                    routineItem.setPermanent(true);
                    routineItem.setSelectedDayList(banglaDaysPicker.getSelectedDays());
                    banglaDaysPicker.setVisibility(View.VISIBLE);
                    btnDateSelect.setVisibility(View.GONE);
                    break;
                }


            }
        }
    }

    @OnClick(R.id.btn_tutorial)
    void showTutorial() {
        UtilsCommon.openWithFaceBook("https://www.facebook.com/comrate.lenin.7/videos/751533605417104/",getContext());
    }

    @OnClick(R.id.cancel)
    void cancel() {
        dismiss();
    }

    @OnClick(R.id.bt_from_time)
    void showStartTimeDialog() {

        int hourOfDay = -1, minOfDay = -1;
        if (routineItem != null && routineItem.getStartTime() != null) {
            hourOfDay = routineItem.getStartTime().get(Calendar.HOUR_OF_DAY);
            minOfDay = routineItem.getStartTime().get(Calendar.MINUTE);
        }

        DialogUtils.showTimeDialog(hourOfDay, minOfDay, getContext(),
                (timePicker, hrOfDay, min) -> {
                    btnFromTime.setText(UtilsDateTime.getAMPMTimeFromCalender(UtilsDateTime.
                            getCalendarFromHourMin(hrOfDay, min)));
                    routineItem.setStartTime(UtilsDateTime.getCalendarFromHourMin(hrOfDay, min));
                });

    }

    @OnClick(R.id.bt_to_time)
    void showEndTimeDialog() {
        int hourOfDay = -1, minOfDay = -1;
        if (routineItem != null) {
            if (routineItem.getEndTime() != null) {
                hourOfDay = routineItem.getEndTime().get(Calendar.HOUR_OF_DAY);
                minOfDay = routineItem.getEndTime().get(Calendar.MINUTE);
            } else if (routineItem.getStartTime() != null) {
                //For adding routine (First time input) increase automatically 5 minutes for
                //inputting "End time"
                hourOfDay = routineItem.getStartTime().get(Calendar.HOUR_OF_DAY);
                minOfDay = routineItem.getStartTime().get(Calendar.MINUTE) + 5;
            } else
            {
                DialogUtils.showInfoAlertDialog("", "দয়া করে শুরুর সময় সিলেক্ট করুন", getContext());
                return;
            }
        }

        DialogUtils.showTimeDialog(hourOfDay, minOfDay, getContext(),
                (timePicker, hrOfDay, min) -> {
                    btnToTime.setText(UtilsDateTime.getAMPMTimeFromCalender(UtilsDateTime.
                            getCalendarFromHourMin(hrOfDay, min)));
                    routineItem.setEndTime(UtilsDateTime.getCalendarFromHourMin(hrOfDay, min));
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


        // 3: Set final value.
        routineItem.setName(etSubject.getText().toString());
        routineItem.setDetails(etDetails.getText().toString());
        routineItem.setLocation((etRoom.getText().toString()));

        if (routineItem.isPermanent())
            routineItem.setSelectedDayList(banglaDaysPicker.getSelectedDays());

        if (isValidated()) {

            if (isEditMode)
                routineViewModel.update(routineItem);
            else
            {
                routineViewModel.insert(routineItem);
                UtilsCommon.sendLogToCrashlytics("New routine added");
            }

            isEditMode = false;
            dismiss();
        }


    }

    @Override
    public void dismiss() {
        routineViewModel.setSelectedRoutineItem(null);
        super.dismiss();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.dialog_add_routine_item, container, false);
        ButterKnife.bind(this, view);

        // 1: Set default value for first time creation.
        initData();

        // 2: Set value if edit mode.
        if (routineViewModel.getSelectedRoutineItem() != null) {
            routineItem = routineViewModel.getSelectedRoutineItem();
            isEditMode = true;
            setUIforEdit();
        }


        return view;
    }

    private void setUIforEdit() {

        switch (routineItem.getType()) {
            case RoutineConstant.ROUTINE_TYPE_CLASS:
                rbClassRoutine.setChecked(true);
                break;
            case RoutineConstant.ROUTINE_TYPE_EXAM:
                rbExamRoutine.setChecked(true);
                break;
            case RoutineConstant.ROUTINE_TYPE_ADMINISTRATION:
                rbAdminRoutine.setChecked(true);
                break;
            default:
                rbOtherRoutine.setChecked(true);
                break;
        }

        etSubject.setText(routineItem.getName());
        etRoom.setText(routineItem.getLocation());


        btnFromTime.setText(UtilsDateTime.getAMPMTimeFromCalender(routineItem.getStartTime()));
        btnToTime.setText(UtilsDateTime.getAMPMTimeFromCalender(routineItem.getEndTime()));

        rbPermanentRoutine.setChecked(routineItem.isPermanent());
        rbTemporaryRoutine.setChecked(!routineItem.isPermanent());

        if (routineItem.getDateIfTemporary() != null)
            btnDateSelect.setText(UtilsDateTime.getSimpleDateText(routineItem.getDateIfTemporary()));

        etDetails.setText(routineItem.getDetails());

        if (routineItem.getSelectedDayList() != null)
            banglaDaysPicker.setSelectedDays(routineItem.getSelectedDayList());

        btnDeleteRoutine.setVisibility(View.VISIBLE);
        cbTriggerAlarm.setChecked(routineItem.isTriggerAlarm());
        btnColorSelect.getBackground().mutate().setColorFilter(new
                PorterDuffColorFilter(routineItem.getColor(), PorterDuff.Mode.SRC));

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

        if (routineItem.getEndTime().getTime().getTime()
                <= routineItem.getStartTime().getTime().getTime()) {
            DialogUtils.showInfoAlertDialog("", "শুরুর সময় শেষের সময় থেকে ছোট বা সমান", getContext());
            return false;
        }

        if (!routineItem.isPermanent() && routineItem.getDateIfTemporary() == null) {
            DialogUtils.showInfoAlertDialog("", "তারিখ সিলেক্ট করুন যেহেতু আপনার রুটিনটি স্থায়ী নয়।বুঝতে " +
                            "অসুবিধা হলে সবার শেষের বাটনে ক্লিক করে ভিডিও টিউটোরিয়াল দেখে আসতে পারেন।",
                    getContext());
            return false;
        }
        return true;

    }

    private void initData() {
        routineViewModel = new ViewModelProvider(getActivity()).get(RoutineViewModel.class);
        routineItem = new RoutineItem();

        //Radio button not word properly while set value via XML so we need to do that
        //by programmatically
        routineItem.setPermanent(true);
        routineItem.setTriggerAlarm(true);
        routineItem.setSelectedDayList(banglaDaysPicker.getSelectedDays());
        routineItem.setType(RoutineConstant.ROUTINE_TYPE_CLASS);
        routineItem.setColor(getResources().getColor(R.color.colorGreen));
    }

}
