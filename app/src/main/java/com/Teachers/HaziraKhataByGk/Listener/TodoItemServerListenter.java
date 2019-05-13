package com.Teachers.HaziraKhataByGk.Listener;

import com.Teachers.HaziraKhataByGk.Scheduler.ToDoItem;

import java.util.ArrayList;
import java.util.List;

public interface TodoItemServerListenter {

    void onGetToDoItem(ArrayList<ToDoItem> toDoItems);
    void onError(String msg);
}
