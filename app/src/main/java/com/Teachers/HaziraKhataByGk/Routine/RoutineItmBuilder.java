package com.Teachers.HaziraKhataByGk.Routine;

import java.util.Calendar;
import java.util.List;

public class RoutineItmBuilder {
    public String type;
    public String details;
    public long id;
    public Calendar startTime;
    public Calendar endTime;
    public String name;
    public String location;
    public int color;
    public List<Integer> selectedDays;

    public static RoutineItmBuilder getInstance() {
        return new RoutineItmBuilder();
    }

    public RoutineItem build() {

        RoutineItem routineItem = new RoutineItem();
        routineItem.setType(type);
        routineItem.setDetails(details);
        routineItem.setColor(color);
        routineItem.setLocation(location);
        routineItem.setName(name);
        routineItem.setStartTime(startTime);
        routineItem.setEndTime(endTime);
        routineItem.setSelectedDayList(selectedDays);

        return routineItem;

    }

    public String getType() {
        return type;
    }

    public RoutineItmBuilder setType(String type) {
        this.type = type;
        return this;
    }


    public String getDetails() {
        return details;
    }

    public RoutineItmBuilder setSelectedDays(List<Integer> selectedDays){
        this.selectedDays=selectedDays;
        return this;
    }

    public RoutineItmBuilder setDetails(String details) {
        this.details = details;
        return this;
    }

    public long getId() {
        return id;
    }

    public RoutineItmBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public RoutineItmBuilder setStartTime(Calendar startTime) {
        this.startTime = startTime;
        return this;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public RoutineItmBuilder setEndTime(Calendar endTime) {
        this.endTime = endTime;
        return this;
    }

    public String getName() {
        return name;
    }

    public RoutineItmBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public RoutineItmBuilder setLocation(String location) {
        this.location = location;
        return this;
    }

    public int getColor() {
        return color;
    }

    public RoutineItmBuilder setColor(int color) {
        this.color = color;
        return this;
    }
}
