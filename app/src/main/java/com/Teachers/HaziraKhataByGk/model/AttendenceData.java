package com.Teachers.HaziraKhataByGk.model;

/**
 * Created by uy on 10/7/2017.
 */


public class AttendenceData {
    private boolean status;
    private String subject;
    private String date;
    private String attendenceClass;
    private String attendenceSection;
    private String studentRollForAttendence;
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
}
