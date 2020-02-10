package com.Teachers.HaziraKhataByGk.Routine;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.List;


public class RoutineItem extends WeekViewEvent {

    private String type;
    private String details;
    private List<Integer> dayList;

    public List<Integer> getDayList() {
        return dayList;
    }

    void setDayList(List<Integer> dayList) {
        this.dayList = dayList;
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


}
