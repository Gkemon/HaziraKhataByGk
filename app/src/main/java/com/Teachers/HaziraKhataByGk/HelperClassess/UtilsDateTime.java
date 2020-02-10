package com.Teachers.HaziraKhataByGk.HelperClassess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UtilsDateTime {
    public static Date getDateObjFromDateFormate(String dateText) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH);
        return format.parse(dateText);
    }

    public static Calendar getUnixTimeStampFromHourMin(int hour, int min){

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, min);

        return c;
    }

    public static String getAMPMTimeFromCalender(Calendar calendar){

        if(calendar==null)return "No time";

        StringBuilder time=new StringBuilder();
        time.append(calendar.get(Calendar.HOUR));
        time.append(":");
        time.append(calendar.get(Calendar.MINUTE));
        if (calendar.get(Calendar.AM_PM) == 0) {
            time.append("AM");
        } else {
            time.append("PM");
        }

        return time.toString();
    }


    public static String intMonthToStringMonthConvertor(int position) {
        if (position == 0) {
            return "";
        } else if (position == 1) {
            return "Jan";
        } else if (position == 2) {
            return "Feb";
        } else if (position == 3) {
            return "Mar";
        } else if (position == 4) {
            return "Apr";
        } else if (position == 5) {
            return "May";
        } else if (position == 6) {
            return "Jun";
        } else if (position == 7) {
            return "Jul";
        } else if (position == 8) {
            return "Aug";
        } else if (position == 9) {
            return "Sep";
        } else if (position == 10) {
            return "Oct";
        } else if (position == 11) {
            return "Nov";
        } else {
            return "Dec";
        }
    }

}
