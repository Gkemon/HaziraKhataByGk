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
public abstract class RoutineDao {
    @Query("SELECT * FROM RoutineItem")
   abstract LiveData<List<RoutineItem>> getAllRoutines();

    @Query("SELECT * FROM RoutineItem WHERE id IN (:userIds)")
    abstract LiveData<List<RoutineItem>> findAllByIds(int[] userIds);


    @Query("SELECT * FROM RoutineItem WHERE id = (:routineID)")
    abstract RoutineItem getRoutineByID(long routineID);

    @Query("SELECT * FROM RoutineItem WHERE type = (:type)")
    abstract  LiveData<List<RoutineItem>> getAllRoutines(String type);

    @Query("DELETE FROM RoutineItem WHERE  id = (:routineItemID)")
    abstract void deleteByID(Long routineItemID);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract  void insert(RoutineItem routineItem);

    @Update
    abstract void updateRoutine(RoutineItem... routineItems);

    @Update
    abstract  void updateRoutine(RoutineItem routineItem);

    @Insert
    abstract void insertAll(RoutineItem... routineItems);

    @Delete
    abstract void delete(RoutineItem routineItem);

}