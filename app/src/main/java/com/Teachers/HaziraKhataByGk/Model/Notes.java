package com.Teachers.HaziraKhataByGk.Model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by uy on 9/7/2017.
 */

public class Notes implements Parcelable {

    public static final Parcelable.Creator<Notes> CREATOR = new Parcelable.Creator<Notes>() {
        @Override
        public Notes createFromParcel(Parcel source) {
            return new Notes(source);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };
    private String heading;
    private String Content;

    public Notes() {

    }

    public Notes(Parcel parcel) {

        this.heading = parcel.readString();
        this.Content = parcel.readString();
    }

    public String getheading() {
        return heading;
    }

    public void setheading(String heading) {
        this.heading = heading;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.heading);
        dest.writeString(this.Content);
    }

}
