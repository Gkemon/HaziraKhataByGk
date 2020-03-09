package com.Teachers.HaziraKhataByGk.Routine.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.Teachers.HaziraKhataByGk.Routine.RoutineItem;

import java.util.List;

@Dao
public interface RoutineDao {
    @Query("SELECT * FROM RoutineItem")
    LiveData<List<RoutineItem>> getAllRoutines();

    @Query("SELECT * FROM RoutineItem WHERE id IN (:userIds)")
    LiveData<List<RoutineItem>> findAllByIds(int[] userIds);


    @Query("SELECT * FROM RoutineItem WHERE id = (:routineID)")
    RoutineItem getRoutineByID(long routineID);

    @Query("SELECT * FROM RoutineItem WHERE type = (:type)")
    LiveData<List<RoutineItem>> getAllRoutines(String type);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(RoutineItem routineItem);

    @Update
    void updateRoutine(RoutineItem... routineItems);

    @Update
    void updateRoutine(RoutineItem routineItem);

    @Insert
    void insertAll(RoutineItem... routineItems);

    @Delete
    void delete(RoutineItem user);
}
