package com.emon.haziraKhata.HelperClasses;

import java.text.ParseException;
import java.util.Date;

public class ComparableDate implements Comparable<ComparableDate> {
    private Date dateTime;

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) throws ParseException {
        this.dateTime = UtilsDateTime.getDateObjFromDateFormate(dateTime);
    }

    @Override
    public int compareTo(ComparableDate o) {
        if (getDateTime() == null || o.getDateTime() == null)
            return 0;
        return getDateTime().compareTo(o.getDateTime());
    }
}
