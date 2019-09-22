package com.Teachers.HaziraKhataByGk.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wim on 4/26/16.
 */
public class ClassItem implements Parcelable {

    private String name;
    private String section;

    public ClassItem() {
    }
    public ClassItem(String name, String section) {
        this.name=name;
        this.section=section;
    }
    protected ClassItem(Parcel in) {
        this.name = in.readString();
        this.section = in.readString();
    }


    public static final Parcelable.Creator<ClassItem> CREATOR = new Parcelable.Creator<ClassItem>() {
        @Override
        public ClassItem createFromParcel(Parcel source) {
            return new ClassItem(source);
        }

        @Override
        public ClassItem[] newArray(int size) {
            return new ClassItem[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.section);
    }

    @Override
    public String toString() {
        return "ClassItem{" +
                "name='" + name + '\'' +
                ", section='" + section + '\'' +
                '}';
    }
}