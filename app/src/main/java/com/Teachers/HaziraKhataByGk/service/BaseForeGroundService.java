package com.Teachers.HaziraKhataByGk.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.CallSuper;
import androidx.core.app.NotificationCompat;

import com.Teachers.HaziraKhataByGk.MainActivity;
import com.Teachers.HaziraKhataByGk.R;

/**
 * Created by Gk Emon on 5/31/2020.
 */
public class BaseForeGroundService extends BaseService {


    public static final String NOTIFICATION_CONTENT = "NotificationContent";
    public static final String CHANNEL_ID = "ChannelID";
    public static final String CHANNEL_NAME = "channelName";
    public static final String NOTIFICATION_TITLE = "NotificationTitle";
    public static final String REQUEST_CODE = "RequestCode";


    public NotificationManager notificationManager;
    public ForegroundServiceBuilder foregroundServiceBuilder;
    public NotificationCompat.Builder notificationBuilder;
    public BaseForeGroundServiceNavigator baseForeGroundServiceNavigator;

    @CallSuper
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        baseForeGroundServiceNavigator.setUpForegroundBuilder();

        initNotificationManager();
        createNotificationChannel();
        initNotification();


        Notification notification = notificationBuilder.build();
        startForeground(foregroundServiceBuilder.notificationID, notification);

        return super.onStartCommand(intent, flags, startId);
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel serviceChannel = new NotificationChannel(
                    foregroundServiceBuilder.channelID,
                    foregroundServiceBuilder.channelName,
                    foregroundServiceBuilder.importance);

            if (notificationManager != null)
                notificationManager.createNotificationChannel(serviceChannel);

        }
    }

    private void initNotificationManager() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            notificationManager = getSystemService(NotificationManager.class);
        } else notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private void initNotification() {

        //Set for going to main activity
        Intent intentToMainActivity = new Intent(this, MainActivity.class);
        PendingIntent pendingIntentToMainActivity = PendingIntent.getActivity(this,
                foregroundServiceBuilder.requestCode, intentToMainActivity, 0);


        notificationBuilder = new NotificationCompat.Builder(this, foregroundServiceBuilder.channelID)
                .setContentTitle(foregroundServiceBuilder.notificationTitle)
                .setContentText(foregroundServiceBuilder.notificationContent)
                .setSmallIcon(R.mipmap.main_icon_hd_half)
                .setOnlyAlertOnce(true)//To prevent showing notification while "notify()" is called.
                .setContentIntent(pendingIntentToMainActivity);

    }


}
