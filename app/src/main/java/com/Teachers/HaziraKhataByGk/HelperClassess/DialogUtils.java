package com.Teachers.HaziraKhataByGk.HelperClassess;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

import com.Teachers.HaziraKhataByGk.Listener.CommonCallback;

public class DialogUtils {


    public static void showInfoAlertDialog(String title, String message, final CommonCallback commonCallback){

       AlertDialog alertDialog = new AlertDialog.Builder(GlobalContext.getWeakActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        commonCallback.onSuccess();
                        dialog.dismiss();
                    }
                }).create();


       alertDialog.show();

    }


}
