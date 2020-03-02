package com.Teachers.HaziraKhataByGk.Routine;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.alamkanak.weekview.WeekViewEvent;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(tableName = "Routine")
public class RoutineItem extends WeekViewEvent implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date date;
    private String type;
    private boolean isPermanent=true;
    private String details;
    private List<Integer> selectedDayList;

    public List<Integer> getSelectedDayList() {
        return selectedDayList;
    }

    void setSelectedDayList(List<Integer> selectedDayList) {
        this.selectedDayList = selectedDayList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getDate() {
        return date;
    }

    public boolean isPermanent() {
        return isPermanent;
    }

    public void setPermanent(boolean permanent) {
        isPermanent = permanent;
    }

    public void setDate(Date date) throws Exception {
        if(!isPermanent)
        this.date = date;
        else {
            throw new Exception("Routine is permanent.So don't need to set date");
        }
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
