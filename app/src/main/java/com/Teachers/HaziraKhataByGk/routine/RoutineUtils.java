package com.Teachers.HaziraKhataByGk.routine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsDateTime;
import com.Teachers.HaziraKhataByGk.routine.room.RoutineRepository;
import com.Teachers.HaziraKhataByGk.service.GenericEventShowingService;
import com.Teachers.HaziraKhataByGk.service.ServiceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gk Emon on 6/1/2020.
 */
public class RoutineUtils {
   public static List<RoutineItem> getUpcomingRoutines(List<RoutineItem> routineItems) {

        List<RoutineItem> remainingMinutes=new ArrayList<>();

        for(RoutineItem routineItem:routineItems){
            long remainingMins=UtilsDateTime.
                    getRemainingMinsFromCalender(routineItem.getStartTime());
            if(remainingMins>1)
            remainingMinutes.add(routineItem);
        }

        return remainingMinutes;
    }

    public static List<RoutineItem> getTotalRoutineItems(Context context){
        return new RoutineRepository(context).getAllRoutineItems();
    }
    public static  List<RoutineItem> getRunningRoutines(List<RoutineItem> routineItems){
        List<RoutineItem> runningRoutine=new ArrayList<>();

        for(RoutineItem routineItem:routineItems){
             if(UtilsDateTime.isCurrentTimeBetweenPeriod(routineItem.getStartTime(),
                     routineItem.getEndTime()))
                runningRoutine.add(routineItem);
        }

        return runningRoutine;

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
