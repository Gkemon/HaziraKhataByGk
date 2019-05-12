package com.Teachers.HaziraKhataByGk.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wim on 4/26/16.
 */
public class ClassIitem implements Parcelable {

    private String name;
    private String section;

    public ClassIitem() {
    }
    public ClassIitem(String name, String section) {
        this.name=name;
        this.section=section;
    }
    protected ClassIitem(Parcel in) {
        this.name = in.readString();
        this.section = in.readString();
    }


    public static final Parcelable.Creator<ClassIitem> CREATOR = new Parcelable.Creator<ClassIitem>() {
        @Override
        public ClassIitem createFromParcel(Parcel source) {
            return new ClassIitem(source);
        }

        @Override
        public ClassIitem[] newArray(int size) {
            return new ClassIitem[size];
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

}