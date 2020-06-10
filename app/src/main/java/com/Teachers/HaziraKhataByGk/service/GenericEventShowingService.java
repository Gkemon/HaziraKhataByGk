package com.Teachers.HaziraKhataByGk.service;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;

import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsDateTime;
import com.Teachers.HaziraKhataByGk.Home.SettingsActivity;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.routine.RoutineItem;
import com.Teachers.HaziraKhataByGk.routine.RoutineUtils;

import java.util.ArrayList;
import java.util.List;

public class GenericEventShowingService extends BaseForeGroundService implements BaseForeGroundServiceNavigator {

    public static int REQUEST_CODE_FOR_EVENT_SHOWING = 11;
    public static int NOTIFICATION_ID_FOR_EVENT_SHOWING = 11;
    public static String TOTAL_ROUTINES = "total_routines";
    public static String SHOW_ROUTINE = "showRouting";
    private TimeChangeReceiver timeChangeReceiver;
    RemoteViews mRemoteViews;
    private List<RoutineItem> totalRoutines = new ArrayList<>();
    private List<RoutineItem> upcomingRoutines = new ArrayList<>();
    private List<RoutineItem> runningRoutines = new ArrayList<>();


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        baseForeGroundServiceNavigator = this;

       String beforeMin = PreferenceManager.getDefaultSharedPreferences(getApplication())
                .getString(SettingsActivity.ROUTINE_REMINDER_TIME_BEFORE,"10");


       Integer.valueOf(beforeMin);





        if (intent.getExtras() != null) {

            List<RoutineItem> routineItems = intent.getExtras().getParcelableArrayList(TOTAL_ROUTINES);
            if (routineItems != null) {
                totalRoutines = routineItems;
                runningRoutines = RoutineUtils.getRunningRoutines(totalRoutines);
                upcomingRoutines = RoutineUtils.getUpcomingRoutines(totalRoutines);
            }
            if (intent.getExtras().getBoolean(SHOW_ROUTINE)) {

                notifyNotificationContent
                        (setupNotificationTextRoutine(upcomingRoutines, runningRoutines));
            }
        }

        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }


    @Override
    public void setUpForegroundBuilder() {
        mRemoteViews = new RemoteViews(getPackageName(), R.layout.notification_routine);

        foregroundServiceBuilder = ForegroundServiceBuilder.newInstance()
                .autoCancel(false)
                .channelID("RoutineChannel")
                .channelName("আপনার প্রতিদিনের কাজ")
                .importance(NotificationManager.IMPORTANCE_MAX)
                .requestCode(REQUEST_CODE_FOR_EVENT_SHOWING)
              //  .notificationTitle("আপনার আজকের কাজ")
                .notificationContent(setupNotificationTextRoutine(upcomingRoutines, runningRoutines))
                .notificationID(NOTIFICATION_ID_FOR_EVENT_SHOWING)
                .build();

    }

    @Override
    public void notifyNotificationContent(String content) {
        notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(content));
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

    private RemoteViews setupNotificationRemoteViewForRoutine(List<RoutineItem> upcomingRoutines,
                                                              List<RoutineItem> runningRoutines) {

        String upcomingRoutine, runningRoutine;

        upcomingRoutine = getRoutinesText(upcomingRoutines);
        runningRoutine = getRoutinesText(runningRoutines);

        mRemoteViews.setTextViewText(R.id.txt_upcoming_task, upcomingRoutine);
        mRemoteViews.setTextViewText(R.id.txt_running_task, runningRoutine);

        return mRemoteViews;
    }

    private String setupNotificationTextRoutine(List<RoutineItem> upcomingRoutines,
                                                List<RoutineItem> runningRoutines) {

        String upcomingRoutine, runningRoutine;

        upcomingRoutine = getRoutinesText(upcomingRoutines);
        runningRoutine = getRoutinesText(runningRoutines);


        return "∎ চলমান কাজ : " +
                runningRoutine + "\n" +
                "∎ সামনের কাজ : " +
                upcomingRoutine;
    }


    private String getRoutinesText(List<RoutineItem> routines) {
        StringBuilder stringBuilder = new StringBuilder();
        for (RoutineItem routineItem : routines) {
            stringBuilder
                    .append(" • ").append(routineItem.getName())
                    .append(" (")
                    .append(UtilsDateTime.getAMPMTimeFromCalender(routineItem.getStartTime()))
                    .append(") \n");
        }
        return UtilsCommon.isValideString(stringBuilder.toString()) ?
                stringBuilder.toString() : " নেই";
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
