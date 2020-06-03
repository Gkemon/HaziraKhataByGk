package com.Teachers.HaziraKhataByGk.service;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Gk Emon on 5/31/2020.
 */
public class  TimeChangeReceiver extends BroadcastReceiver {

    //This Will triggered every minutes
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentToEventService = new Intent(context, GenericEventShowingService.class);
        if(intent!=null&&intent.getAction()!=null) {
            if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                intentToEventService.putExtra(GenericEventShowingService.SHOW_ROUTINE, true);
            }
            else if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
                intentToEventService.putExtra(GenericEventShowingService.SHOW_ROUTINE, true);
            }

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                context.startForegroundService(intentToEventService);
            } else context.startService(intent);
        }
    }

}
