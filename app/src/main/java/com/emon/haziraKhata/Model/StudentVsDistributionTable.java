package com.emon.haziraKhata.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by GK on 6/17/2018.
 */

public class StudentVsDistributionTable implements Serializable {

    public String studentID;
    public ArrayList<DistributionVSnumberTable> distributionVSnumberTableArrayList;

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public ArrayList<DistributionVSnumberTable> getDistributionVSnumberTableArrayList() {
        return distributionVSnumberTableArrayList;
    }

    public void setDistributionVSnumberTableArrayList(ArrayList<DistributionVSnumberTable> distributionVSnumberTableArrayList) {
        this.distributionVSnumberTableArrayList = distributionVSnumberTableArrayList;
    }

}
