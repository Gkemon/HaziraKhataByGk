package com.Teachers.HaziraKhataByGk.Model;
import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {
    private String roll;
    private String Name;
    private String ParentContact;
    private String StudentsContactNumber;
    private String ParentName;
    private String StudentClass;
    private String StudentSection;
    public Student() {
    }
    protected Student(Parcel in) {
        this.roll = in.readString();
        this.Name = in.readString();
        this.ParentContact = in.readString();
        this.StudentsContactNumber =in.readString();
        this.ParentName=in.readString();
    }
    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
    public String getId() {
        return roll;
    }

    public void setId(String id) {
        this.roll = id;
    }

    public String getStudentName() {
        return Name;
    }

    public void setStudentName(String name) {
        this.Name = name;
    }

    public String getParentName(){
        return ParentName;
    }
    public void setParentName(String name){
        this.ParentName=name;
    }
    public String getParentContact() {
        return ParentContact;
    }

    public void setParentContact(String phone) {
        this.ParentContact = phone;
    }

    public String getPhone() {
        return StudentsContactNumber;
    }

    public void setPhone(String phone) {
        this.StudentsContactNumber = phone;
    }

    public void setStudentClass(String studentClass){
        this.StudentClass=studentClass;
    }
    public void setStudentSection(String studentSection){
        this.StudentSection=studentSection;
    }
    public String getStudentClass(){
        return  StudentClass;
    }
    public String getStudentSection(){
        return  StudentSection;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.roll);
        dest.writeString(this.Name);
        dest.writeString(this.ParentContact);
        dest.writeString(this.StudentsContactNumber);
        dest.writeString(this.ParentName);
    }


}
