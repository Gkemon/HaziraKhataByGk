package com.Teachers.HaziraKhataByGk.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Gk Emon on 5/31/2020.
 */
public class  TimeChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
            Calendar cal = Calendar.getInstance();
            String time =""+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE);
            Toast.makeText(context,time,Toast.LENGTH_LONG).show();
        }
    }

}
