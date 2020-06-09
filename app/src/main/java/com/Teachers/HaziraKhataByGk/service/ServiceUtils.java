package com.Teachers.HaziraKhataByGk.service;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Created by Gk Emon on 6/5/2020.
 */
public class ServiceUtils {
    public static void startForegroundService(Intent intent, Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        }
        else context.startService(intent);
    }
}
