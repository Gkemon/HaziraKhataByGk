package com.emon.haziraKhata.Model;

import java.io.Serializable;

/**
 * Created by GK on 6/16/2018.
 */

public class DistributionVSnumberTable implements Serializable {

    public String distributionName;
    public Double distributionNumber;

    public Double getDistributionNumber() {
        return distributionNumber;
    }

    public void setDistributionNumber(Double distributionNumber) {
        this.distributionNumber = distributionNumber;
    }

    public String getDistributionName() {
        return distributionName;
    }

    public void setDistributionName(String distributionName) {
        this.distributionName = distributionName;
    }

}
