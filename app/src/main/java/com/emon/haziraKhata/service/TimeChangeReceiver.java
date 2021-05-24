package com.emon.haziraKhata.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

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
            ServiceUtils.startForegroundService(intentToEventService,context);

        }
    }

}
