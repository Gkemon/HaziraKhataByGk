package com.Teachers.HaziraKhataByGk.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by GK on 6/14/2018.
 */

public class SubjectMarkSheet implements Serializable {

    public String subjectName;
    public Double totalNumber;
    public ArrayList<DistributionVSnumberTable> distributionVSnumberTable;// example: "home work" <--> 15
    public ArrayList<StudentVsDistributionTable> studentVsDistributionTableArrayList;

    public ArrayList<StudentVsDistributionTable> getStudentVsDistributionTableArrayList() {
        return studentVsDistributionTableArrayList;
    }

    public void setStudentVsDistributionTableArrayList(ArrayList<StudentVsDistributionTable> studentVsDistributionTableArrayList) {
        this.studentVsDistributionTableArrayList = studentVsDistributionTableArrayList;
    }

    public ArrayList<DistributionVSnumberTable> getDistributionVSnumberTable() {
        return distributionVSnumberTable;
    }

    public void setDistributionVSnumberTable(ArrayList<DistributionVSnumberTable> distributionVSnumberTable) {
        this.distributionVSnumberTable = distributionVSnumberTable;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }


//    public void setStudentVsDistributionTable(HashMap<String, HashMap<String, Integer>> studentVsDistributionTable) {
//        this.studentVsDistributionTable = studentVsDistributionTable;
//    }

    public Double getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Double totalNumber) {
        this.totalNumber = totalNumber;
    }


//    public HashMap<String, HashMap<String, Integer>> getStudentVsDistributionTable() {
//        return studentVsDistributionTable;
//    }
}
