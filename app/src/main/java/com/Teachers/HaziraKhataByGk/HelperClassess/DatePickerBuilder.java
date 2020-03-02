package com.Teachers.HaziraKhataByGk.HelperClassess;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.DatePicker;

import com.Teachers.HaziraKhataByGk.Listener.CommonCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePickerBuilder {

    public static final String DatePattern_EEE_d_MMM_yyyy = "EEE, d MMM yyyy";
    static DatePickerBuilder datePickerBuilder;
    public String dateString_EEE_d_MMM_yyyy;
    public Activity activity;

    DatePicker datePicker;
    long timestamp = -1;
    private CommonCallback<String> commonCallback;

    public static DatePickerBuilder getBuilder() {
        if (datePickerBuilder == null) {
            datePickerBuilder = new DatePickerBuilder();
            return datePickerBuilder;
        } else return datePickerBuilder;
    }

    public static Date getDateObjFromDateFormate(String dateText, String datePattern_EEE_d_MMM_yyyy) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(datePattern_EEE_d_MMM_yyyy);
        return format.parse(dateText);
    }

    public DatePickerBuilder setUnixTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public DatePickerBuilder setActivity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public DatePickerBuilder setDateString_EEE_d_MMM_yyyy(String datePattern_EEE_d_MMM_yyyy) {
        this.dateString_EEE_d_MMM_yyyy = datePattern_EEE_d_MMM_yyyy;
        return this;
    }

    public DatePickerBuilder setDateCallBack(CommonCallback<String> commonCallback) {
        this.commonCallback = commonCallback;
        return this;
    }

    public void show() {
        Calendar calendar;
        calendar = Calendar.getInstance();

        if (timestamp > 0 && !UtilsCommon.isValideString(dateString_EEE_d_MMM_yyyy)) {
            calendar.setTimeInMillis(timestamp);
        } else if (UtilsCommon.isValideString(dateString_EEE_d_MMM_yyyy)) {
            try {
                calendar.setTime(getDateObjFromDateFormate(dateString_EEE_d_MMM_yyyy, DatePattern_EEE_d_MMM_yyyy));
            } catch (ParseException e) {
                commonCallback.onFailure("Error in date parsing: " + e.getLocalizedMessage());
            }
        }

        DatePickerDialog dialog = new DatePickerDialog(activity, android.R.style.Theme_Dialog,
                (view, year, month, dayOfMonth) -> {
                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.YEAR, year);
                    c.set(Calendar.MONTH, month);
                    c.set(Calendar.DATE, dayOfMonth);

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DatePattern_EEE_d_MMM_yyyy);
                    String formatedDate = simpleDateFormat.format(c.getTime());
                    commonCallback.onSuccess(formatedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        dialog.getDatePicker().setSpinnersShown(true);
        dialog.getDatePicker().setCalendarViewShown(false);
        dialog.show();
    }
}
