package com.Teachers.HaziraKhataByGk.Model;

import com.Teachers.HaziraKhataByGk.HelperClassess.ComparableDate;

import java.text.ParseException;

/**
 * Created by uy on 10/7/2017.
 */


public class AttendenceData implements Comparable<AttendenceData>  {
    private boolean status;
    private String subject="";
    private String date="";
    private String attendenceClass="";
    private String attendenceSection="";
    private String studentRollForAttendence="";

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "AttendenceData{" +
                "status=" + status +
                ", subject='" + subject + '\'' +
                ", date='" + date + '\'' +
                ", attendenceClass='" + attendenceClass + '\'' +
                ", attendenceSection='" + attendenceSection + '\'' +
                ", studentRollForAttendence='" + studentRollForAttendence + '\'' +
                ", key='" + key + '\'' +
                '}';
    }

    public  String key;
    public AttendenceData(){
    }
    public void setStatus(boolean status){
        this.status=status;
    }
    public void setSubject(String subject){
        this.subject=subject;
    }
    public void setDate(String date){
        this.date=date;
    }
    public boolean getStatus(){
        return status;
    }
    public String getSubject(){
        return subject;
    }
    public String getDate()
    {
        return date;
    }
    public void setStudentRollForAttendence(String studentRollForAttendence){
        this.studentRollForAttendence=studentRollForAttendence;
    }
    public void setAttendenceClass(String attendenceClass){
        this.attendenceClass=attendenceClass;
    }
    public void setAttendenceSection(String studentSection){this.attendenceSection=studentSection;}
    public String getAttendenceClass(){
        return  attendenceClass;
    }
    public String getAttendenceSection(){
        return  attendenceSection;
    }
    public String getStudentRollForAttendence(){
        return  studentRollForAttendence;
    }

    @Override
    public int compareTo(AttendenceData o) {

        ComparableDate comparableDate1 = new ComparableDate();
        ComparableDate comparableDate2 = new ComparableDate();

        try {
            comparableDate1.setDateTime(this.getDate());
            comparableDate2.setDateTime(o.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return comparableDate1.compareTo(comparableDate2);

    }
}
