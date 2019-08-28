package com.Teachers.HaziraKhataByGk.Routine;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.Calendar;


public class RoutineItem extends WeekViewEvent {

    public String type;
    public String details;

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



}
