package com.Teachers.HaziraKhataByGk.service;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;

import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsDateTime;

public class GenericEventShowingService extends BaseForeGroundService implements BaseForeGroundServiceNavigator {

    public static int REQUEST_CODE_FOR_EVENT_SHOWING = 11;
    public static int NOTIFICATION_ID_FOR_EVENT_SHOWING = 11;
    public static String SHOW_ROUTINE = "showRouting";
    private TimeChangeReceiver timeChangeReceiver;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        baseForeGroundServiceNavigator = this;
        super.onStartCommand(intent, flags, startId);

        if (intent.getExtras() != null && intent.getExtras().getBoolean(SHOW_ROUTINE)) {
            notifyNotificationContent(UtilsDateTime.getHHMMformattedTime());
        }

        return START_STICKY;
    }


    @Override
    public void setUpForegroundBuilder() {
        foregroundServiceBuilder = ForegroundServiceBuilder.newInstance()
                .autoCancel(false)
                .channelID("RoutineChannel")
                .channelName("আপনার প্রতিদিনের কাজ")
                .importance(NotificationManager.IMPORTANCE_MAX)
                .requestCode(REQUEST_CODE_FOR_EVENT_SHOWING)
                .notificationTitle("পরবর্তী কাজ")
                .notificationContent("EMON")
                .notificationID(NOTIFICATION_ID_FOR_EVENT_SHOWING)
                .build();
    }

    @Override
    public void notifyNotificationContent(String content) {
        notificationBuilder.setContentText(content);
        notificationManager.notify(NOTIFICATION_ID_FOR_EVENT_SHOWING, notificationBuilder.build());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerTimeChangeReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterTimeChangeReceiver();
    }

    private void registerTimeChangeReceiver() {
        timeChangeReceiver = new TimeChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_BOOT_COMPLETED);
        registerReceiver(timeChangeReceiver, filter);
    }

    private void unregisterTimeChangeReceiver() {
        try {
            if (timeChangeReceiver != null) {
                unregisterReceiver(timeChangeReceiver);
            }
        } catch (IllegalArgumentException e) {
        }
    }

}
