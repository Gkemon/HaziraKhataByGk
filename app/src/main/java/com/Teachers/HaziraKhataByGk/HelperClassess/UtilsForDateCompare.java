package com.Teachers.HaziraKhataByGk.HelperClassess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.Teachers.HaziraKhataByGk.Constant.Constant.DateFormate;

public class UtilsForDateCompare {


    public static List<String> getSortedDate(List<String> unSortedDate) throws ParseException {

        List<ComparableDate> comparableDates = new ArrayList<>();


        for (String dateText : unSortedDate) {
            ComparableDate comparableDate = new ComparableDate();
            comparableDate.setDateTime(dateText);
            comparableDates.add(comparableDate);
        }

        Collections.sort(comparableDates);


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormate);

        List<String> sortedDateList = new ArrayList<>();
        for (ComparableDate s : comparableDates) {
            String formatedDate = simpleDateFormat.format(s.getDateTime());
            sortedDateList.add(formatedDate);

        }


        return sortedDateList;
    }

    ;

    public static Date getDateObjFromDateFormate(String dateText) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy");
        return format.parse(dateText);

    }
}
