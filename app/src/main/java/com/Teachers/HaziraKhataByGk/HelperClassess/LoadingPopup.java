package com.Teachers.HaziraKhataByGk.HelperClassess;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.Teachers.HaziraKhataByGk.R;


public class LoadingPopup extends Dialog {

   public static LoadingPopup dialog;


    public LoadingPopup(@NonNull Context context) {
        super(context);
    }

    public static void  showLoadingPopUp(Activity activity) {
           try {
               dialog.show();
           } catch (Exception e) {
               dialog = null;
               getInstance(activity).show();
           }
    }


    @Override
    public void dismiss() {

        Handler handler = new Handler();
        handler.postDelayed(super::dismiss, 3000);

    }

    @Override
    public void show() {
        super.show();
        Handler handler = new Handler();
        handler.postDelayed(() -> setCancelable(true), 3000);
    }

    public static void hideLoadingPopUp(){


        if(dialog!=null)
        {
            dialog.dismiss();
            dialog=null;
        }


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress_bar);

        if(getWindow()!=null)
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    public static LoadingPopup getInstance(Activity activity){

        if(activity==null)
            activity=GlobalContext.getWeakActivity();

        if(dialog==null){
            dialog=new LoadingPopup(activity);
            return dialog;
        }
        else return dialog;
    }


}
