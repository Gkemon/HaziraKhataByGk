package com.Teachers.HaziraKhataByGk.model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by uy on 9/7/2017.
 */

public class Notes  implements Parcelable {

    private String heading;
    private String Content;
    public Notes(){

    }
    public Notes(Parcel parcel){

        this.heading=parcel.readString();
        this.Content=parcel.readString();
    }
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


    public String getheading() {
        return heading;
    }
    public void setheading(String heading) {
        this.heading = heading;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }
    public String getContent() {
        return Content;
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
