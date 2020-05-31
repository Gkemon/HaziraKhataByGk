package com.Teachers.HaziraKhataByGk.service;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.view.WindowManager;

import androidx.annotation.Nullable;

/**
 * Created by Gk Emon on 5/31/2020.
 */
public class BaseService extends Service {
    WindowManager windowManager;
    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            windowManager = getSystemService(WindowManager.class);
        }
        else windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
