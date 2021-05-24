package com.emon.haziraKhata.service;

import android.app.ActivityManager;
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

    public static boolean isServiceRunning(Class<?> serviceClass,Context context) {
        ActivityManager manager = (ActivityManager) context.
                getSystemService(Context.ACTIVITY_SERVICE);

        if(manager!=null)
        for (ActivityManager.RunningServiceInfo service :
                manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
