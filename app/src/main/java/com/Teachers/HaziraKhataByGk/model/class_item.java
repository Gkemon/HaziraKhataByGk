package com.Teachers.HaziraKhataByGk.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wim on 4/26/16.
 */
public class class_item implements Parcelable {

    private String name;
    private String section;

    public class_item() {
    }
    public class_item(String name,String section) {
        this.name=name;
        this.section=section;
    }
    protected class_item(Parcel in) {
        this.name = in.readString();
        this.section = in.readString();
    }


    public static final Parcelable.Creator<class_item> CREATOR = new Parcelable.Creator<class_item>() {
        @Override
        public class_item createFromParcel(Parcel source) {
            return new class_item(source);
        }

        @Override
        public class_item[] newArray(int size) {
            return new class_item[size];
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