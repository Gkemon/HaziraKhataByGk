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
    public InputOperation inputOperation;

    public RoutineViewModel(Application application) {
        super(application);
        mRepository = new RoutineRepository(application);
        mAllRoutines = mRepository.getAllLiveRoutineItems();
        selectedRoutineItem=null;
    }

    public RoutineItem getSelectedRoutineItem() {
        return selectedRoutineItem;
    }

    public void setSelectedRoutineItem(RoutineItem selectedRoutineItem) {
        this.selectedRoutineItem = selectedRoutineItem;
    }

   public LiveData<List<RoutineItem>> getAllLiveRoutines() {
        return mAllRoutines;
    }

    List<RoutineItem> getAllRoutineItems() {
        return mRepository.getAllRoutineItems();
    }

    LiveData<List<RoutineItem>> getAllRoutines(String type) {
        return mRepository.getAllLiveRoutineItems(type);
    }

    void insert(RoutineItem routineItem) {
        mRepository.insert(routineItem);
    }

    void delete(RoutineItem routineItem) {
        mRepository.delete(routineItem);
        inputOperation=InputOperation.DELETE;
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
