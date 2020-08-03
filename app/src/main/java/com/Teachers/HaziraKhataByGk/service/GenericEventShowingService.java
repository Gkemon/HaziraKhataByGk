package com.Teachers.HaziraKhataByGk.service;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;

import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsDateTime;
import com.Teachers.HaziraKhataByGk.Home.SettingsActivity;
import com.Teachers.HaziraKhataByGk.MainActivity;
import com.Teachers.HaziraKhataByGk.routine.RoutineItem;
import com.Teachers.HaziraKhataByGk.routine.RoutineUtils;

import java.util.ArrayList;
import java.util.List;

public class GenericEventShowingService extends BaseForeGroundService implements BaseForeGroundServiceNavigator {

    public static int REQUEST_CODE_FOR_EVENT_SHOWING = 11;
    public static int NOTIFICATION_ID_FOR_EVENT_SHOWING = 11;
    public static String TOTAL_ROUTINES = "total_routines";
    public static String SHOW_ROUTINE = "showRouting";
    public static String TRIGGERED_ROUTINES = "triggeredRoutines";

    private TimeChangeReceiver timeChangeReceiver;
    private List<RoutineItem> totalRoutines = new ArrayList<>();
    private List<RoutineItem> upcomingRoutines = new ArrayList<>();
    private List<RoutineItem> runningRoutines = new ArrayList<>();
    private AutoStartReceiver autoStartReceiver;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        baseForeGroundServiceNavigator = this;

        if (intent!=null&&intent.getExtras() != null) {

            List<RoutineItem> routineItems = intent.getExtras().getParcelableArrayList(TOTAL_ROUTINES);
            if (routineItems != null) {
                totalRoutines = routineItems;
            }
            runningRoutines = RoutineUtils.getRunningRoutines(totalRoutines);
            upcomingRoutines = RoutineUtils.getUpcomingRoutines(totalRoutines);
            triggerAlarm(upcomingRoutines);
            if (intent.getExtras().getBoolean(SHOW_ROUTINE)) {
                notifyNotificationContent
                        (setupNotificationTextRoutine(upcomingRoutines, runningRoutines));
            }
        }

        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    //when a routine is ready to notify user and current time is its trigger time.
    private void triggerAlarm(List<RoutineItem> runningRoutines) {
        if(runningRoutines!=null&&!runningRoutines.isEmpty()){
            String beforeMin = PreferenceManager.getDefaultSharedPreferences(getApplication())
                    .getString(SettingsActivity.ROUTINE_REMINDER_TIME_BEFORE, "10");

            long beforeReminderTime = Long.parseLong(beforeMin);

            //The routines which "start time" is current time and that's why we need to fire them.
            List<RoutineItem> triggerRoutines = new ArrayList<>();

            for (RoutineItem routineItem : runningRoutines) {

                if (routineItem.isTriggerAlarm()&&beforeReminderTime == UtilsDateTime
                        .getRemainingMinsFromCalender(routineItem.getStartTime())) {
                    triggerRoutines.add(routineItem);
                }
            }

            if (triggerRoutines.size() > 0) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(GenericEventShowingService.TRIGGERED_ROUTINES,
                        new ArrayList<>(triggerRoutines));
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }

    }


    @Override
    public void setUpForegroundBuilder() {

        foregroundServiceBuilder = ForegroundServiceBuilder.newInstance()
                .autoCancel(false)
                .channelID("RoutineChannel")
                .channelName("আপনার প্রতিদিনের কাজ")
                .importance(NotificationManager.IMPORTANCE_MAX)
                .requestCode(REQUEST_CODE_FOR_EVENT_SHOWING)
                .notificationContent(setupNotificationTextRoutine(upcomingRoutines, runningRoutines))
                .notificationID(NOTIFICATION_ID_FOR_EVENT_SHOWING)
                .build();

    }

    @Override
    public void notifyNotificationContent(String content) {
        if(notificationBuilder!=null){
            notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(content));
            notificationManager.notify(NOTIFICATION_ID_FOR_EVENT_SHOWING, notificationBuilder.build());
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerTimeChangeReceiver();
        registerBootCompleteReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterTimeChangeReceiver();
        unregisterBootChangeReceiver();
    }

    private void registerTimeChangeReceiver() {
        timeChangeReceiver = new TimeChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(timeChangeReceiver, filter);
    }
    private void registerBootCompleteReceiver() {
        autoStartReceiver = new AutoStartReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BOOT_COMPLETED);
        registerReceiver(autoStartReceiver, filter);
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
        } catch (IllegalArgumentException ignored) {
            UtilsCommon.handleError(ignored);
        }
    }

    private void unregisterBootChangeReceiver() {
        try {
            if (autoStartReceiver != null) {
                unregisterReceiver(autoStartReceiver);
            }
        } catch (IllegalArgumentException ignored) {
            UtilsCommon.handleError(ignored);
        }
    }


}
