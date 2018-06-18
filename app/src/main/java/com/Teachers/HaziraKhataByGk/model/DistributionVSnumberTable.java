package com.Teachers.HaziraKhataByGk.model;

import java.io.Serializable;

/**
 * Created by uy on 6/16/2018.
 */

public class DistributionVSnumberTable implements Serializable {

    public String distributionName;
    public Integer distributionNumber;

    public void setDistributionName(String distributionName) {
        this.distributionName = distributionName;
    }

    public void setDistributionNumber(Integer distributionNumber) {
        this.distributionNumber = distributionNumber;
    }

    public Integer getDistributionNumber() {
        return distributionNumber;
    }

    public String getDistributionName() {
        return distributionName;
    }

}
