package com.Teachers.HaziraKhataByGk.service;

import android.content.Intent;

import com.Teachers.HaziraKhataByGk.routine.room.RoutineRepository;

public class GenericEventShowingService extends BaseForeGroundService {

    RoutineRepository routineRepository = new RoutineRepository(getApplication());

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        RoutineRepository routineRepository = new RoutineRepository(getApplication());

        routineRepository.getAllRoutineItems();


        //do heavy work on a background thread
        //stopSelf();
        return START_NOT_STICKY;
    }


}
