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
    public static final String CHANNEL_NAME = "ChannelName";
    public static final String CHANNEL_ID = "EventShowingService";
    public static final String NOTIFICATION_TITLE = "NotificationTitle";
    public static final String REQUEST_CODE = "RequestCode";
    public String channelName, notificationTitle;
    public int requestCode;
    public NotificationCompat.Builder builder;

    @CallSuper
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        channelName = intent.getStringExtra(BaseForeGroundService.CHANNEL_NAME);
        notificationTitle = intent.getStringExtra(BaseForeGroundService.NOTIFICATION_TITLE);
        requestCode = intent.getIntExtra(BaseForeGroundService.REQUEST_CODE, 0);

        createNotificationChannel();

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                requestCode, notificationIntent, 0);

        builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(notificationTitle)
                .setContentText(channelName)
                .setSmallIcon(R.mipmap.main_icon_hd_half)
                .setContentIntent(pendingIntent);

        Notification notification = builder.build();
        startForeground(1, notification);

        return super.onStartCommand(intent, flags, startId);
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
