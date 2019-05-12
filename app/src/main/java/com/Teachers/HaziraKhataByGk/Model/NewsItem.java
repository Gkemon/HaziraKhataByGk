package com.Teachers.HaziraKhataByGk.Model;


import android.os.Parcel;
import android.os.Parcelable;

public class NewsItem implements Parcelable {
    private String heading;
    private String URL;
    private String Date;

   public NewsItem(){
    }

    protected NewsItem(Parcel in) {
        this.heading = in.readString();
        this.URL = in.readString();
        this.Date=in.readString();
    }


    public static final Parcelable.Creator<NewsItem> CREATOR = new Parcelable.Creator<NewsItem>() {
        @Override
        public NewsItem createFromParcel(Parcel source) {
            return new NewsItem(source);
        }

        @Override
        public NewsItem[] newArray(int size) {
            return new NewsItem[size];
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
