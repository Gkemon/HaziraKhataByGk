package com.Teachers.HaziraKhataByGk.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by uy on 6/14/2018.
 */

public class SubjectMarkSheet implements Serializable {

    public String subjectName;
    public Integer totalNumber;
    public ArrayList<DistributionVSnumberTable> distributionVSnumberTable;// example: "home work" <--> 15

    public ArrayList<StudentVsDistributionTable> getStudentVsDistributionTableArrayList() {
        return studentVsDistributionTableArrayList;
    }

    public void setStudentVsDistributionTableArrayList(ArrayList<StudentVsDistributionTable> studentVsDistributionTableArrayList) {
        this.studentVsDistributionTableArrayList = studentVsDistributionTableArrayList;
    }

    public ArrayList<StudentVsDistributionTable> studentVsDistributionTableArrayList;


    public ArrayList<DistributionVSnumberTable> getDistributionVSnumberTable() {
        return distributionVSnumberTable;
    }
    public void setDistributionVSnumberTable(ArrayList<DistributionVSnumberTable> distributionVSnumberTable) {
        this.distributionVSnumberTable = distributionVSnumberTable;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }


    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }





//    public void setStudentVsDistributionTable(HashMap<String, HashMap<String, Integer>> studentVsDistributionTable) {
//        this.studentVsDistributionTable = studentVsDistributionTable;
//    }

    public String getSubjectName() {
        return subjectName;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }




//    public HashMap<String, HashMap<String, Integer>> getStudentVsDistributionTable() {
//        return studentVsDistributionTable;
//    }
}
