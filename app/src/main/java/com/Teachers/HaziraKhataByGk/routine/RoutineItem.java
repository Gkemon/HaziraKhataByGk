package com.Teachers.HaziraKhataByGk.routine;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;

import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.alamkanak.weekview.WeekViewEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
public class RoutineItem extends WeekViewEvent implements Parcelable {
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
        return UtilsCommon.isValideString(details)?details:"";
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
        dest.writeLong(this.dateIfTemporary != null ? this.dateIfTemporary.getTime() : -1);
        dest.writeString(this.type);
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
        long tmpDateIfTemporary = in.readLong();
        this.dateIfTemporary = tmpDateIfTemporary == -1 ? null : new Date(tmpDateIfTemporary);
        this.type = in.readString();
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
