package com.Teachers.HaziraKhataByGk.HelperClassess;

import android.graphics.Color;
import android.util.Log;

import com.Teachers.HaziraKhataByGk.Constant.Constant;
import com.Teachers.HaziraKhataByGk.Routine.RoutineItem;
import com.Teachers.HaziraKhataByGk.Routine.RoutineItmBuilder;
import com.alamkanak.weekview.WeekViewEvent;

import java.util.Calendar;

public class MockObjectsRepository {

    public static RoutineItem getDummyRoutine(int newYear,int newMonth){

        

        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 4);
        endTime.set(Calendar.MONTH, newMonth - 1);

        return RoutineItmBuilder.getInstance()
                .setName("Demo")
                .setId(1)
                .setColor(Color.RED)
                .setStartTime(startTime)
                .setEndTime(endTime)
                .setType(Constant.ROUTINE_TYPE_ADMINISTRATIONAL)
                .setDetails("This is demo")
                .setLocation("Dhaka").build();



    }
}
