package com.Teachers.HaziraKhataByGk.routine;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.Teachers.HaziraKhataByGk.routine.room.RoutineRepository;

import java.util.List;

public class RoutineViewModel extends AndroidViewModel {
    private RoutineRepository mRepository;
    private LiveData<List<RoutineItem>> mAllRoutines;
    private RoutineItem selectedRoutineItem;

    public RoutineViewModel(Application application) {
        super(application);
        mRepository = new RoutineRepository(application);
        mAllRoutines = mRepository.getAllRoutineItems();

    }

    public RoutineItem getSelectedRoutineItem() {
        return selectedRoutineItem;
    }

    public void setSelectedRoutineItem(RoutineItem selectedRoutineItem) {
        this.selectedRoutineItem = selectedRoutineItem;
    }

    LiveData<List<RoutineItem>> getAllRoutines() {
        return mAllRoutines;
    }

    LiveData<List<RoutineItem>> getAllRoutines(String type) {
        return mRepository.getAllRoutineItems(type);
    }

    void insert(RoutineItem routineItem) {
        mRepository.insert(routineItem);
    }

    void delete(RoutineItem routineItem) {
        mRepository.delete(routineItem);
    }

    void deleteByID(Long routineItemID) {
        mRepository.deleteByID(routineItemID);
    }

    void update(RoutineItem routineItem) {
        mRepository.update(routineItem);
    }

    void update(RoutineItem... routineItem) {
        mRepository.update(routineItem);
    }
}
