package com.emon.haziraKhata.routine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.emon.haziraKhata.HelperClasses.UtilsDateTime;
import com.emon.haziraKhata.routine.room.RoutineRepository;
import com.emon.haziraKhata.service.GenericEventShowingService;
import com.emon.haziraKhata.service.ServiceUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Gk Emon on 6/1/2020.
 */
public class RoutineUtils {

    public static boolean isRoutineItemInToday(RoutineItem routineItem){
        if((routineItem.isPermanent()&&routineItem.getSelectedDayList().
                contains(UtilsDateTime.getTodayInNumber()))){
            return true;
        }
        else return !routineItem.isPermanent() &&
                UtilsDateTime.getTodayInNumber() ==
                        routineItem.getDateIfTemporary().get(Calendar.DAY_OF_WEEK);
    }
   public static List<RoutineItem> getUpcomingRoutines(List<RoutineItem> routineItems) {

        List<RoutineItem> remainingMinutes=new ArrayList<>();

        for(RoutineItem routineItem:routineItems){
            if(isRoutineItemInToday(routineItem)){
                long remainingSecond = UtilsDateTime.
                        getRemainingSecondFromCalender(routineItem.getStartTime());
                if (remainingSecond > 0)
                    remainingMinutes.add(routineItem);
            }
        }

        return remainingMinutes;
    }

    public static List<RoutineItem> getTotalRoutineItems(Context context){
        return new RoutineRepository(context).getAllRoutineItems();
    }
    public static  List<RoutineItem> getRunningRoutines(List<RoutineItem> routineItems){
        List<RoutineItem> runningRoutine=new ArrayList<>();

        for(RoutineItem routineItem:routineItems){
            if(isRoutineItemInToday(routineItem)) {
                if (UtilsDateTime.isCurrentTimeBetweenPeriod(routineItem.getStartTime(),
                        routineItem.getEndTime()))
                    runningRoutine.add(routineItem);
            }
        }

        return runningRoutine;

    }

    public static void stopEventShowingService(Context context){
        Intent serviceIntent = new Intent(context, GenericEventShowingService.class);
        context.stopService(serviceIntent);
    }




    public static void startEventShowingService(Context context,List<RoutineItem> totalRoutines){
        ArrayList<RoutineItem> totalRoutineArrayList = new ArrayList(totalRoutines);

        Intent serviceIntent = new Intent(context, GenericEventShowingService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(GenericEventShowingService.TOTAL_ROUTINES,totalRoutineArrayList);
        serviceIntent.putExtras(bundle);
        ServiceUtils.startForegroundService(serviceIntent,context);

    }
}
