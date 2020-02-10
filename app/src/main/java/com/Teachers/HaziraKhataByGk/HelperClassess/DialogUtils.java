package com.Teachers.HaziraKhataByGk.HelperClassess;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;

import com.Teachers.HaziraKhataByGk.Listener.CommonCallback;

import java.util.Calendar;

public class DialogUtils {


    public static void showTimeDialog(int hourOfDay,int minOfDay,Context context, TimePickerDialog.OnTimeSetListener onTimeSetListener) {

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context, onTimeSetListener, hourOfDay, minOfDay, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    public static void showDateDialog(Calendar calendar, Context context, DatePickerDialog.OnDateSetListener commonCallback) {

        if (calendar == null)
            calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener onDateSetListener = (view, year, month, dayOfMonth) -> {


        };


        try {
            new DatePickerDialog(context, onDateSetListener, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        } catch (Exception e) {

        }

    }

    public static void showInfoAlertDialog(String title, String message, Context context, final CommonCallback commonCallback) {

        try {
            AlertDialog alertDialog = new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {

                        if (commonCallback != null)
                            commonCallback.onSuccess();

                        dialog.dismiss();
                    }).create();


            alertDialog.show();
        } catch (Exception e) {

        }


    }

    public static void showInfoAlertDialog(String title, String message, Context context) {

        try {
            AlertDialog alertDialog = new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        dialog.dismiss();
                    }).create();
            alertDialog.show();
        } catch (Exception e) {

        }


    }


}
