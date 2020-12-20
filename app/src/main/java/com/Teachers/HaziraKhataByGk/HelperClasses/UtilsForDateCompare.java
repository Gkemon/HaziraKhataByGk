package com.Teachers.HaziraKhataByGk.HelperClasses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UtilsForDateCompare {


    public static List<String> getSortedDate(List<String> unSortedDate) throws ParseException {

        List<ComparableDate> comparableDates = new ArrayList<>();


        for (String dateText : unSortedDate) {
            ComparableDate comparableDate = new ComparableDate();
            comparableDate.setDateTime(dateText);
            comparableDates.add(comparableDate);
        }

        Collections.sort(comparableDates);


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(UtilsDateTime.DATE_FORMATE_EEE_D_MMM_YYYY);

        List<String> sortedDateList = new ArrayList<>();
        for (ComparableDate s : comparableDates) {
            String formatedDate = simpleDateFormat.format(s.getDateTime());
            sortedDateList.add(formatedDate);
        }

        return sortedDateList;
    }

}
