package com.Teachers.HaziraKhataByGk.HelperClasses;

import java.util.ArrayList;

public class CustomArrayList<T> extends ArrayList<T> {

    @Override
    public int size() {

        if (this == null) {
            return 0;
        } else
            return super.size();
    }


    @Override
    public boolean add(T a) {

        if (a == null) return false;
        else return super.add(a);

    }
}
