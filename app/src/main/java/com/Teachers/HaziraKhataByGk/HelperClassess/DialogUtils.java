package com.Teachers.HaziraKhataByGk.HelperClassess;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;
import android.widget.DatePicker;

import com.Teachers.HaziraKhataByGk.Listener.CommonCallback;

import java.util.Calendar;

public class DialogUtils {


    public static void showTimeDialog(String time, TimePickerDialog.OnTimeSetListener onTimeSetListener){

    }

    public static void showDateDialog(Calendar calendar,Context context ,DatePickerDialog.OnDateSetListener commonCallback){

        if(calendar==null)
            calendar=Calendar.getInstance();

        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


            }
        };


        try {
            new DatePickerDialog(context, onDateSetListener, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        }catch (Exception e){

        }

    }

    public static void showInfoAlertDialog(String title, String message,Context context, final CommonCallback commonCallback){

        try {
            AlertDialog alertDialog = new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {

                        if(commonCallback!=null)
                        commonCallback.onSuccess();

                        dialog.dismiss();
                    }).create();


            alertDialog.show();
        }catch (Exception e){

        }


    }

    public static void showInfoAlertDialog(String title, String message,Context context){

        try {
            AlertDialog alertDialog = new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        dialog.dismiss();
                    }).create();
            alertDialog.show();
        }catch (Exception e){

        }


    }


}
