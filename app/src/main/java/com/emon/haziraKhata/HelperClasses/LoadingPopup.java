package com.emon.haziraKhata.HelperClasses;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.emon.haziraKhata.R;


public class LoadingPopup extends Dialog {

    public static LoadingPopup dialog;
    public int DELAY=0;


    public LoadingPopup(@NonNull Context context) {
        super(context);
    }

    public static void showLoadingPopUp(Activity activity) {
        try {
            dialog.show();
        } catch (Exception e) {
            dialog = null;
            getInstance(activity).show();
        }
    }

    public static void hideLoadingPopUp() {
        if (dialog != null) {
            try {
                if(dialog.isShowing())
                dialog.dismiss();
            } catch (Exception e) {
                UtilsCommon.handleError(e);
            }
            dialog = null;
        }
    }

    public static LoadingPopup getInstance(Activity activity) {


        if (dialog == null) {
            if (activity == null) dialog = new LoadingPopup(GlobalContext.getWeakActivity());
            else dialog = new LoadingPopup(activity);
            return dialog;
        } else return dialog;
    }

    @Override
    public void dismiss() {

        Handler handler = new Handler();
        try {
            handler.postDelayed(() -> {
                try {
                    super.dismiss();
                }catch (Exception ignored){

                }
            }, DELAY);
        }catch (Exception e){
            hideLoadingPopUp();
        }


    }

    @Override
    public void show() {
        try {
            super.show();
            Handler handler = new Handler();
            handler.postDelayed(() -> setCancelable(true), DELAY);
        }catch (Exception e){
            hideLoadingPopUp();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress_bar);

        if (getWindow() != null)
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }


}
