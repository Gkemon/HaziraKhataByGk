package com.emon.haziraKhata.Home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.emon.haziraKhata.routine.RoutineItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gk Emon on 6/11/2020.
 */
public class MainViewModel extends AndroidViewModel {
    List<RoutineItem> triggeredRoutines=new ArrayList<>();
    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public List<RoutineItem> getTriggeredRoutines() {
        return triggeredRoutines;
    }

    public void setTriggeredRoutines(List<RoutineItem> triggeredRoutines) {
        this.triggeredRoutines = triggeredRoutines;
    }
}
