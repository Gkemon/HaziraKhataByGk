package com.Teachers.HaziraKhataByGk.HelperClassess;

import java.util.ArrayList;

public class MyArrayList <T>extends ArrayList {

    @Override
    public int size() {

        if(this==null){
            return 0;
        }
        else
        return super.size();
    }
}
