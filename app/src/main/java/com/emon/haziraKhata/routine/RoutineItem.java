package com.emon.haziraKhata.routine;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;

import com.emon.haziraKhata.HelperClasses.UtilsCommon;
import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
public class RoutineItem extends WeekViewEvent implements Parcelable {
    private Calendar dateIfTemporary;
    private String type;
    private boolean triggerAlarm=true;
    private boolean isPermanent = true;
    private String details;
    private List<Integer> selectedDayList;

    public boolean isTriggerAlarm() {
        return triggerAlarm;
    }

    public void setTriggerAlarm(boolean triggerAlarm) {
        this.triggerAlarm = triggerAlarm;
    }

    public static Creator<RoutineItem> getCREATOR() {
        return CREATOR;
    }

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
        return UtilsCommon.isValideString(details)?details:"";
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Calendar getDateIfTemporary() {
        return dateIfTemporary;
    }

    public boolean isPermanent() {
        return isPermanent;
    }

    public void setPermanent(boolean permanent) {
        isPermanent = permanent;
    }

    public void setDateIfTemporary(Calendar date)  {
            this.dateIfTemporary = date;
    }

    @Override
    public String getName() {
        if(UtilsCommon.isValideString(super.getName()))
            return super.getName();
        else return "";
    }

    @Override
    public String getLocation() {
        if(UtilsCommon.isValideString(super.getLocation()))
        return super.getLocation();
        else return "";
    }

    public RoutineItem() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeSerializable(this.dateIfTemporary);
        dest.writeString(this.type);
        dest.writeByte(this.triggerAlarm ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isPermanent ? (byte) 1 : (byte) 0);
        dest.writeString(this.details);
        dest.writeList(this.selectedDayList);
        dest.writeLong(this.id);
        dest.writeSerializable(this.mStartTime);
        dest.writeSerializable(this.mEndTime);
        dest.writeString(this.mName);
        dest.writeString(this.mLocation);
        dest.writeInt(this.mColor);
        dest.writeByte(this.mAllDay ? (byte) 1 : (byte) 0);
    }

    protected RoutineItem(Parcel in) {
        super(in);
        this.dateIfTemporary = (Calendar) in.readSerializable();
        this.type = in.readString();
        this.triggerAlarm = in.readByte() != 0;
        this.isPermanent = in.readByte() != 0;
        this.details = in.readString();
        this.selectedDayList = new ArrayList<Integer>();
        in.readList(this.selectedDayList, Integer.class.getClassLoader());
        this.id = in.readLong();
        this.mStartTime = (Calendar) in.readSerializable();
        this.mEndTime = (Calendar) in.readSerializable();
        this.mName = in.readString();
        this.mLocation = in.readString();
        this.mColor = in.readInt();
        this.mAllDay = in.readByte() != 0;
    }

    public static final Creator<RoutineItem> CREATOR = new Creator<RoutineItem>() {
        @Override
        public RoutineItem createFromParcel(Parcel source) {
            return new RoutineItem(source);
        }

        @Override
        public RoutineItem[] newArray(int size) {
            return new RoutineItem[size];
        }
    };
}
