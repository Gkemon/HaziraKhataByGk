package com.Teachers.HaziraKhataByGk.Model;

import com.Teachers.HaziraKhataByGk.HelperClasses.ComparableDate;

import java.text.ParseException;

/**
 * Created by GK on 10/7/2017.
 */


public class AttendenceData implements Comparable<AttendenceData> {
    public String key;
    private boolean status;
    private String subject = "";
    private String date = "";
    private String attendenceClass = "";
    private String attendenceSection = "";
    private String studentRollForAttendence = "";

    public AttendenceData() {
    }

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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAttendenceClass() {
        return attendenceClass;
    }

    public void setAttendenceClass(String attendenceClass) {
        this.attendenceClass = attendenceClass;
    }

    public String getAttendenceSection() {
        return attendenceSection;
    }

    public void setAttendenceSection(String studentSection) {
        this.attendenceSection = studentSection;
    }

    public String getStudentRollForAttendence() {
        return studentRollForAttendence;
    }

    public void setStudentRollForAttendence(String studentRollForAttendence) {
        this.studentRollForAttendence = studentRollForAttendence;
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
