package com.Teachers.HaziraKhataByGk.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by uy on 11/21/2017.
 */

public class blog_item implements Parcelable{
    private String heading;
    private String URL;
    private String Date;
    private String writer;

    public blog_item(){
    }

    protected blog_item(Parcel in) {
        this.heading = in.readString();
        this.URL = in.readString();
        this.Date=in.readString();
        this.writer=in.readString();
    }


    public static final Parcelable.Creator<blog_item> CREATOR = new Parcelable.Creator<blog_item>() {
        @Override
        public blog_item createFromParcel(Parcel source) {
            return new blog_item(source);
        }

        @Override
        public blog_item[] newArray(int size) {
            return new blog_item[size];
        }
    };

    public String getHeading(){
        return this.heading;
    }
    public String getURL(){
        return this.URL;
    }
    public String getWriter(){return this.writer;}
    public String getDate(){return this.Date;}

    public void setHeading(String heading){
        this.heading=heading;
    }
    public void setURL(String URL){
        this.URL=URL;
    }
    public void setDate(String Date){
        this.Date=Date;
    }
    public void setWriter(String writer){this.writer=writer;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.URL);
        dest.writeString(this.heading);
        dest.writeString(this.Date);
        dest.writeString(this.writer);
    }



}
