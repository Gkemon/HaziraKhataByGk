package com.Teachers.HaziraKhataByGk.model;


import android.os.Parcel;
import android.os.Parcelable;

public class news_item implements Parcelable {
    private String heading;
    private String URL;
    private String Date;

   public news_item(){
    }

    protected news_item(Parcel in) {
        this.heading = in.readString();
        this.URL = in.readString();
        this.Date=in.readString();
    }


    public static final Parcelable.Creator<news_item> CREATOR = new Parcelable.Creator<news_item>() {
        @Override
        public news_item createFromParcel(Parcel source) {
            return new news_item(source);
        }

        @Override
        public news_item[] newArray(int size) {
            return new news_item[size];
        }
    };

    public String getHeading(){
        return this.heading;
    }
    public String getURL(){
        return this.URL;
    }
    public void setHeading(String heading){
        this.heading=heading;
    }
    public void setURL(String URL){
        this.URL=URL;
    }
    public void setDate(String Date){
        this.Date=Date;
    }
    public String getDate(){return this.Date;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.URL);
        dest.writeString(this.heading);
        dest.writeString(this.Date);
    }

}
