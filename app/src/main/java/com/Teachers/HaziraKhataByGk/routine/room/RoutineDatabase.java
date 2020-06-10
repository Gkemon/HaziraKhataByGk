package com.Teachers.HaziraKhataByGk.routine.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.Teachers.HaziraKhataByGk.routine.RoutineItem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {RoutineItem.class}, version = 1, exportSchema = false)
@TypeConverters({RoomConverter.class})

public abstract class RoutineDatabase extends RoomDatabase {
    public abstract RoutineDao routineDao();

    private static volatile RoutineDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static RoutineDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoutineDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoutineDatabase.class, RoomConstant.ROUTINE_DATABASE)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
