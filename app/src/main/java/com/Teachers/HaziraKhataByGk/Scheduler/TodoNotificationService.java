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

import com.Teachers.HaziraKhataByGk.R;
import com.amulyakhare.textdrawable.util.ColorGenerator;

public class TodoNotificationService extends IntentService {
    public static final String TODOTEXT = "com.avjindersekhon.todonotificationservicetext";
    public static final String TODOUUID = "com.avjindersekhon.todonotificationserviceuuid";
    public static final String IsDailyOrNot ="IsDailyOrNot";
    private String mTodoText;
    private int mTodoUUID;
    public String isDaily="false";
    private Context mContext;
    MediaPlayer mp;

    public TodoNotificationService(){
        super("TodoNotificationService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        mTodoText = intent.getStringExtra(TODOTEXT);
        mTodoUUID = (Integer)intent.getSerializableExtra(TODOUUID);
        isDaily= intent.getStringExtra(IsDailyOrNot);

        Uri defaultRingone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
long [] vibration ={1000,2000,3000};


        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        Intent i = new Intent(this, ReminderActivity.class);
        i.putExtra(TodoNotificationService.TODOUUID, mTodoUUID);




        ColorGenerator generator = ColorGenerator.MATERIAL;
        Intent deleteIntent = new Intent(this, DeleteNotificationService.class);


        deleteIntent.putExtra(TODOUUID, mTodoUUID);



        //if it is on daily so then no need to set delete intert


        if(isDaily.equals("true")){

            Intent intentForGOToSchedule = new Intent(this, scheduleActivity.class);

            //This is actually stop the ringtone because i cannot do this anymore
           PendingIntent pendingIntent = PendingIntent.getService(this, mTodoUUID, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);
           pendingIntent.cancel();

            Log.d("GK", "onHandleIntent called which was daily ");
            Notification notification = new Notification.Builder(this)
                    .setTicker("হাজিরা খাতা")
                    .setContentTitle(mTodoText)
                    .setSmallIcon(R.drawable.ic_schedule_new)
                    .setAutoCancel(true)
                    .setVibrate(vibration)
                 //   .setDeleteIntent(pendingIntent)
                    .setContentIntent(PendingIntent.getActivity(this, mTodoUUID, intentForGOToSchedule, PendingIntent.FLAG_UPDATE_CURRENT))
                   .build();
            manager.notify(100, notification);




        }
        else {
            Log.d("GK", "onHandleIntent called which was not daily");
            Notification notification = new Notification.Builder(this)
                    .setTicker("হাজিরা খাতা")
                    .setContentTitle(mTodoText)
                    .setSmallIcon(R.drawable.ic_schedule_new)
                    .setAutoCancel(true)
                    .setVibrate(vibration)
                    .setDeleteIntent(PendingIntent.getService(this, mTodoUUID, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                    .setContentIntent(PendingIntent.getActivity(this, mTodoUUID, i, PendingIntent.FLAG_UPDATE_CURRENT))
                    .build();
            manager.notify(100, notification);
        }



         mp = new MediaPlayer();
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

    @Override
    public void onDestroy() {
        try {
            mp.reset();
            mp.prepare();
            mp.stop();
            mp.release();
            mp=null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        super.onDestroy();
    }

}
