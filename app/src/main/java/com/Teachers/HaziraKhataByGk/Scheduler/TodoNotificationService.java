package com.Teachers.HaziraKhataByGk.Scheduler;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.Teachers.HaziraKhataByGk.DeleteNotificationService;
import com.Teachers.HaziraKhataByGk.R;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.UUID;

public class TodoNotificationService extends IntentService {
    public static final String TODOTEXT = "com.avjindersekhon.todonotificationservicetext";
    public static final String TODOUUID = "com.avjindersekhon.todonotificationserviceuuid";
    public static final String IsDailyOrNot ="IsDailyOrNot";
    private String mTodoText;
    private UUID mTodoUUID;
    private Boolean isDaily;
    private Context mContext;

    public TodoNotificationService(){
        super("TodoNotificationService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        mTodoText = intent.getStringExtra(TODOTEXT);
        mTodoUUID = (UUID)intent.getSerializableExtra(TODOUUID);
        isDaily= intent.getBooleanExtra(IsDailyOrNot,false);

        Uri defaultRingone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
long [] vibration ={1000,2000,3000};
        Log.d("eee", "onHandleIntent called");
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent i = new Intent(this, ReminderActivity.class);
        i.putExtra(TodoNotificationService.TODOUUID, mTodoUUID);




        ColorGenerator generator = ColorGenerator.MATERIAL;
        Intent deleteIntent = new Intent(this, DeleteNotificationService.class);


        deleteIntent.putExtra(TODOUUID, mTodoUUID);


        //if it is on daily so then no need to set delete intert

        if(!isDaily){
            Notification notification = new Notification.Builder(this)
                    .setTicker("হাজিরা খাতা")
                    .setContentTitle(mTodoText)
                    .setSmallIcon(R.drawable.ic_schedule_new)
                    .setAutoCancel(true)
                    .setVibrate(vibration)
                    .setDeleteIntent(PendingIntent.getService(this, mTodoUUID.hashCode(), deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                    .setContentIntent(PendingIntent.getActivity(this, mTodoUUID.hashCode(), i, PendingIntent.FLAG_UPDATE_CURRENT))
                    .build();
            manager.notify(100, notification);
        }
        else{
            Notification notification = new Notification.Builder(this)
                    .setTicker("হাজিরা খাতা")
                    .setContentTitle(mTodoText)
                    .setSmallIcon(R.drawable.ic_schedule_new)
                    .setAutoCancel(true)
                    .setVibrate(vibration)
                    .setContentIntent(PendingIntent.getActivity(this, mTodoUUID.hashCode(), i, PendingIntent.FLAG_UPDATE_CURRENT))
                    .build();
            manager.notify(100, notification);
        }



        MediaPlayer mp = new MediaPlayer();
        try{
            mp.setDataSource(this, defaultRingone);
            mp.setAudioStreamType(AudioManager.STREAM_ALARM);
            mp.prepare();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
            mp.start();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
