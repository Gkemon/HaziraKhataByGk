package com.Teachers.HaziraKhataByGk.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by uy on 10/28/2017.
 */

public class JobItems implements Parcelable {
    private String post;
    private String institute;
    private String place;
    private String URL;
    public JobItems(){
    }

    protected JobItems(Parcel in) {
        this.post = in.readString();
        this.URL = in.readString();
        this.institute=in.readString();
        this.place=in.readString();
    }

    public static final Parcelable.Creator<JobItems> CREATOR = new Parcelable.Creator<JobItems>() {
        @Override
        public JobItems createFromParcel(Parcel source) {
            return new JobItems(source);
        }

        @Override
        public JobItems[] newArray(int size) {
            return new JobItems[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.URL);
        dest.writeString(this.post);
        dest.writeString(this.institute);
        dest.writeString(this.place);
    }


    public String getPost(){
        return post;
    }
    public String getInstitute(){
        return institute;
    }
    public String getPlace(){
        return place;
    }
    public String getURL(){return URL;}


    public void setPost(String post){
        this.post=post;
    }
    public void setInstitute(String institute){
        this.institute=institute;
    }
    public void setPlace(String place){
        this.place=place;
    }
    public void setURL(String URL){this.URL=URL;}

}
