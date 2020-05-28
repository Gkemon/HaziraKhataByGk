package com.Teachers.HaziraKhataByGk.HelperClassess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class UtilsDateTime {
    public static String DATE_FORMATE_EEE_D_MMM_YYYY="EEE, d MMM yyyy";

    public static SimpleDateFormat getSimpleDateFormate(String dateFormate){
       return new SimpleDateFormat(dateFormate, Locale.ENGLISH);
    }

    public static Date getDateObjFromDateFormate(String dateText) throws ParseException {
        return getSimpleDateFormate(DATE_FORMATE_EEE_D_MMM_YYYY).parse(dateText);
    }

    public static String getSimpleDateText(int year,int month,int dayOfMonth){
        return getSimpleDateFormate(DATE_FORMATE_EEE_D_MMM_YYYY).format(getDate(year,month,dayOfMonth));
    }
    public static Date getDate(int year,int month,int dayOfMonth) {
        return new GregorianCalendar(year, month, dayOfMonth).getTime();
    }

    public static Calendar getUnixTimeStampFromHourMin(int hourOfDay, int min){

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, min);

        return c;
    }

    public static boolean isDateEqualIgnoringTime(Date date1,Date date2){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd",Locale.ENGLISH);
       return sdf.format(date1).equals(sdf.format(date2));
    }

    public static String getAMPMTimeFromCalender(Calendar calendar){

        if(calendar==null)return "No time";

        StringBuilder time=new StringBuilder();

        time.append(calendar.get(Calendar.HOUR)==0?"12":calendar.get(Calendar.HOUR));

        time.append(":");
        int min= calendar.get(Calendar.MINUTE);
        time.append(min <= 9 ?min+"0":min);
        if (calendar.get(Calendar.AM_PM) == Calendar.AM) {
            time.append(" AM");
        } else {
            time.append(" PM");
        }

        return time.toString();
    }

    public static int StringMonthToIntMonthConvertor(String month) {

        if (month.equals("Jan")) {

            return 0;
        } else if (month.equals("Feb")) {
            return 1;
        } else if (month.equals("Mar")) {
            return 2;
        } else if (month.equals("Apr")) {
            return 3;
        } else if (month.equals("May")) {
            return 4;
        } else if (month.equals("Jun")) {
            return 5;
        } else if (month.equals("Jul")) {
            return 6;
        } else if (month.equals("Aug")) {
            return 7;
        } else if (month.equals("Sep")) {
            return 8;
        } else if (month.equals("Oct")) {
            return 9;
        } else if (month.equals("Nov")) {
            return 10;
        } else return 11;


    }

    public static String intMonthToStringMonthConverter(int position) {
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
