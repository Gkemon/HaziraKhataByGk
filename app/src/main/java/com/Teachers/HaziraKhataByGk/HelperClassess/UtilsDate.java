package com.Teachers.HaziraKhataByGk.HelperClassess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilsDate {
    public static Date getDateObjFromDateFormate(String dateText) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy");
        return format.parse (dateText);

    }
}
