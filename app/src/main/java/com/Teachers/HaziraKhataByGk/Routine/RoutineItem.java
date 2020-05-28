package com.Teachers.HaziraKhataByGk.Routine;

import androidx.room.Entity;

import com.alamkanak.weekview.WeekViewEvent;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class RoutineItem extends WeekViewEvent implements Serializable {
    private Date dateIfTemporary;
    private String type;
    private boolean isPermanent = true;
    private String details;
    private List<Integer> selectedDayList;


    public List<Integer> getSelectedDayList() {
        return selectedDayList;
    }

    public void setSelectedDayList(List<Integer> selectedDayList) {
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

    public Date getDateIfTemporary() {
        return dateIfTemporary;
    }

    public boolean isPermanent() {
        return isPermanent;
    }

    public void setPermanent(boolean permanent) {
        isPermanent = permanent;
    }

    public void setDateIfTemporary(Date date)  {
            this.dateIfTemporary = date;
    }

}
