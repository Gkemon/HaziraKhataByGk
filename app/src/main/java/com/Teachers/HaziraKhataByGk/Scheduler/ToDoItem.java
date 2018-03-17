package com.Teachers.HaziraKhataByGk.Scheduler;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class ToDoItem implements Serializable {
    private String mToDoText;
    private String mToDoContent;
    private boolean mHasReminder;
    private int mTodoColor;
    private Date mToDoDate;
    private boolean isDaily=false;
    private UUID mTodoIdentifier;

    public ToDoItem(String todoBody, boolean hasReminder, Date toDoDate,String content){
        mToDoText = todoBody;
        mHasReminder = hasReminder;
        mToDoDate = toDoDate;
        mTodoColor = 1677725;
        mTodoIdentifier = UUID.randomUUID();
        mToDoContent=content;
    }

    public  boolean isDaily(){
        return this.isDaily;
    }
    public void setDaily(boolean isDaily){
        this.isDaily=isDaily;
    }
    public ToDoItem(){
       this("",true, null,"");
    }
    public String getToDoText() {
        return mToDoText;
    }

    public void setToDoText(String mToDoText) {
        this.mToDoText = mToDoText;
    }

    public boolean hasReminder() {
        return mHasReminder;
    }

    public void setHasReminder(boolean mHasReminder) {
        this.mHasReminder = mHasReminder;
    }

    public Date getToDoDate() {
        return mToDoDate;
    }

    public int getTodoColor() {
        return mTodoColor;
    }

    public void setTodoColor(int mTodoColor) {
        this.mTodoColor = mTodoColor;
    }

    public void setToDoDate(Date mToDoDate) {
        this.mToDoDate = mToDoDate;
    }
    public  void setToDoContent(String mToDoContent){
        this.mToDoContent=mToDoContent;
    }
    public String getToDoContent(){
        return mToDoContent;
    }


    public UUID getIdentifier(){
        return mTodoIdentifier;
    }
}

