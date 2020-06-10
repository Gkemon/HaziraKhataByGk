package com.Teachers.HaziraKhataByGk.routine.room;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.Teachers.HaziraKhataByGk.routine.RoutineItem;

import java.util.List;

public class RoutineRepository {


    private RoutineDao routineDao;
    private LiveData<List<RoutineItem>> routineLiveList;
    private List<RoutineItem> routineList;


    public RoutineRepository(Context context) {
        RoutineDatabase db = RoutineDatabase.getDatabase(context);
        routineDao = db.routineDao();
        routineLiveList = routineDao.getAllLiveRoutines();
    }

    public LiveData<List<RoutineItem>> getAllLiveRoutineItems() {
        return routineLiveList;
    }

    public List<RoutineItem> getAllRoutineItems() {
        return routineDao!=null?routineDao.getAllRoutines():null;
    }

    public LiveData<List<RoutineItem>> getAllLiveRoutineItems(String type) {
        return routineDao.getAllLiveRoutines(type);
    }

    public void insert(RoutineItem routineItem) {
        RoutineDatabase.databaseWriteExecutor.execute(() -> routineDao.insert(routineItem));
    }

    public void update(RoutineItem routineItem) {
        RoutineDatabase.databaseWriteExecutor.execute(() -> routineDao.updateRoutine(routineItem));
    }

    public void update(RoutineItem... routineItem) {
        RoutineDatabase.databaseWriteExecutor.execute(() -> routineDao.updateRoutine(routineItem));
    }
    public void delete(RoutineItem routineItem) {
        RoutineDatabase.databaseWriteExecutor.execute(() -> routineDao.delete(routineItem));
    }
    public void deleteByID(Long routineItemID) {
        RoutineDatabase.databaseWriteExecutor.execute(() -> routineDao.deleteByID(routineItemID));
    }
}
