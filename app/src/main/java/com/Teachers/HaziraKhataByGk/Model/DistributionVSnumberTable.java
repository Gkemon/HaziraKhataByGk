package com.Teachers.HaziraKhataByGk.Model;

import java.io.Serializable;

/**
 * Created by uy on 6/16/2018.
 */

public class DistributionVSnumberTable implements Serializable {

    public String distributionName;
    public Double distributionNumber;

    public void setDistributionName(String distributionName) {
        this.distributionName = distributionName;
    }

    public void setDistributionNumber(Double distributionNumber) {
        this.distributionNumber = distributionNumber;
    }

    public Double getDistributionNumber() {
        return distributionNumber;
    }

    public String getDistributionName() {
        return distributionName;
    }

}
