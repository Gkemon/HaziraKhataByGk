package com.emon.haziraKhata.HelperClasses;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;

import androidx.appcompat.app.AlertDialog;

import com.emon.haziraKhata.Listener.CommonCallback;

import java.util.Calendar;

public class DialogUtils {


    public static void showTimeDialog(int hourOfDay,int minOfDay,Context context,
                                      TimePickerDialog.OnTimeSetListener onTimeSetListener) {

        if(hourOfDay<0){
            hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            minOfDay = Calendar.getInstance().get(Calendar.MINUTE);
        }
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context, (timePicker, hourOfDay1, min) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                onTimeSetListener.onTimeSet(timePicker,timePicker.getHour(),timePicker.getMinute());
            }
            else onTimeSetListener.onTimeSet(timePicker,timePicker.getCurrentMinute(),timePicker.getCurrentMinute());
        }, hourOfDay, minOfDay, false);//Yes for 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    public static void showDateDialog(Calendar calendar, Context context, DatePickerDialog.OnDateSetListener onDateSetListener) {


        //Year,month,date of month
        if (calendar == null)
            calendar = Calendar.getInstance();

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
                            commonCallback.onSuccess(true);

                        dialog.dismiss();
                    }).setNegativeButton(android.R.string.no, (dialogInterface, i) -> {
                        if (commonCallback != null)
                            commonCallback.onSuccess(false);

                        dialogInterface.dismiss();
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
            UtilsCommon.handleError(e);
        }


    }


}
