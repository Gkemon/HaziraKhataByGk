package com.Teachers.HaziraKhataByGk.Model;

import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;

import java.io.Serializable;

/**
 * Created by wim on 4/26/16.
 */
public class ClassItem implements Serializable {

    private String name;
    private String section;

    public ClassItem() {
    }

    public ClassItem(String name, String section) {
        this.name = name;
        this.section = section;
    }

    public String getName() {
        return UtilsCommon.isValideString(name)? name: "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSection() {
        return UtilsCommon.isValideString(section)? section: "";
    }

    public void setSection(String section) {
        this.section = section;
    }


    @Override
    public String toString() {
        return "ClassItem{" +
                "name='" + name + '\'' +
                ", section='" + section + '\'' +
                '}';
    }
}