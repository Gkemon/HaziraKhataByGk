package com.Teachers.HaziraKhataByGk.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.Teachers.HaziraKhataByGk.routine.RoutineUtils;

/**
 * Created by Gk Emon on 6/10/2020.
 */
public class AutoStartReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // TODO Auto-generated method stub
        if (intent != null && intent.getAction() != null)
            if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
                RoutineUtils.
                        startEventShowingService(context,
                                RoutineUtils.getTotalRoutineItems(context));
            }
    }
}
