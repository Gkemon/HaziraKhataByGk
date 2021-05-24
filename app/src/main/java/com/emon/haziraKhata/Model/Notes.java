package com.emon.haziraKhata.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.emon.haziraKhata.HelperClasses.UtilsCommon;


/**
 * Created by GK on 9/7/2017.
 */

public class Notes implements Parcelable {

    private String heading="";
    private String Content="";
    private String uid="";
    private String date="";

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Notes() {

    }

    public String getheading() {
        return UtilsCommon.isValideString(heading)?heading:"";
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
        dest.writeString(this.uid);
        dest.writeString(this.date);
    }

    protected Notes(Parcel in) {
        this.heading = in.readString();
        this.Content = in.readString();
        this.uid = in.readString();
        this.date = in.readString();
    }

    public static final Creator<Notes> CREATOR = new Creator<Notes>() {
        @Override
        public Notes createFromParcel(Parcel source) {
            return new Notes(source);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };
}
