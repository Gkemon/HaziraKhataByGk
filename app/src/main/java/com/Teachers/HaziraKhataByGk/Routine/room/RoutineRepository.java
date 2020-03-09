package com.Teachers.HaziraKhataByGk.Routine.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.Teachers.HaziraKhataByGk.Routine.RoutineItem;

import java.util.List;

public class RoutineRepository {


    private RoutineDao routineDao;
    private LiveData<List<RoutineItem>> routineList;


    public RoutineRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        routineDao = db.routineDao();
        routineList = routineDao.getAllRoutines();
    }

    public LiveData<List<RoutineItem>> getAllRoutineItems() {
        return routineList;
    }

    public LiveData<List<RoutineItem>> getAllRoutineItems(String type) {
        return routineDao.getAllRoutines(type);
    }

    public void insert(RoutineItem routineItem) {
        AppDatabase.databaseWriteExecutor.execute(() -> routineDao.insert(routineItem));
    }

    public void update(RoutineItem routineItem) {
        AppDatabase.databaseWriteExecutor.execute(() -> routineDao.updateRoutine(routineItem));
    }

    public void update(RoutineItem... routineItem) {
        AppDatabase.databaseWriteExecutor.execute(() -> routineDao.updateRoutine(routineItem));
    }
    public void delete(RoutineItem routineItem) {
        AppDatabase.databaseWriteExecutor.execute(() -> routineDao.delete(routineItem));
    }

}
