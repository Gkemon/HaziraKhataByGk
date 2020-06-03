package com.Teachers.HaziraKhataByGk.routine;

import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsDateTime;

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
    public static  List<RoutineItem> getRunningRoutines(List<RoutineItem> routineItems){
        List<RoutineItem> runningRoutine=new ArrayList<>();

        for(RoutineItem routineItem:routineItems){
             if(UtilsDateTime.isCurrentTimeBetweenPeriod(routineItem.getStartTime(),
                     routineItem.getEndTime()))
                runningRoutine.add(routineItem);
        }

        return runningRoutine;

    }
}
