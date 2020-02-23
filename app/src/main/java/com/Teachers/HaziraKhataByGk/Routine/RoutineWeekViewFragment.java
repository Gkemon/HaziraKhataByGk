package com.Teachers.HaziraKhataByGk.Routine;

import android.content.Context;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.Teachers.HaziraKhataByGk.HelperClassess.MockObjectsRepository;
import com.Teachers.HaziraKhataByGk.R;
import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class RoutineWeekViewFragment extends Fragment implements MonthLoader.MonthChangeListener, WeekView.EventClickListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener {
    View root;
    Context context;
    private WeekView mWeekView;
    private String flag;//This is for choosing the categories of routine

    public RoutineWeekViewFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_routine_week_view, container, false);

        mWeekView = root.findViewById(R.id.weekView);
        mWeekView.setOnEventClickListener(this);
        mWeekView.setMonthChangeListener(this);
        mWeekView.setEventLongPressListener(this);
        mWeekView.setEmptyViewLongPressListener(this);

        setupDateTimeInterpreter(false);
        return root;
    }


    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {

    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {

        List<RoutineItem> events = new ArrayList<>();
        if (getArguments() != null) {
            flag = getArguments().getString("key");
        }

        events.add(MockObjectsRepository.mockRoutineItem);


        List<WeekViewEvent> newEvents= new ArrayList<>();

        for (RoutineItem event : events) {

            if(event==null)continue;

            Calendar dateTime = event.getStartTime();
            Calendar dateEndTime = event.getEndTime();


                Calendar monCal = getFirstDay(newMonth - 1,
                        newYear,dateTime.get(Calendar.DAY_OF_WEEK));
                int hday = dateTime.get(Calendar.HOUR_OF_DAY);
                int mday = dateTime.get(Calendar.MINUTE);
                int ehday = dateEndTime.get(Calendar.HOUR_OF_DAY);
                int emday = dateEndTime.get(Calendar.MINUTE);


                List<Integer> selectedDays=getAllSelectedDaysInMonth(event.getSelectedDayList());
                for (int k = monCal.get(Calendar.DAY_OF_MONTH);
                     k <= monCal.getActualMaximum(Calendar.DAY_OF_MONTH); k += 1) {

                     if(!selectedDays.contains(k))continue;

                    Calendar startTime = Calendar.getInstance();
                    startTime.set(Calendar.MONTH, newMonth - 1);
                    startTime.set(Calendar.DAY_OF_MONTH, k);
                    startTime.set(Calendar.YEAR, newYear);
                    startTime.set(Calendar.HOUR_OF_DAY, hday);
                    startTime.set(Calendar.MINUTE, mday);
                    startTime.set(Calendar.SECOND, 0);
                    startTime.set(Calendar.MILLISECOND, 0);

                    Calendar endTime = (Calendar) startTime.clone();
                    endTime.set(Calendar.HOUR_OF_DAY, ehday);
                    endTime.set(Calendar.MINUTE, emday - 1);
                    endTime.set(Calendar.MONTH, newMonth - 1);
                    endTime.set(Calendar.SECOND, 59);
                    endTime.set(Calendar.MILLISECOND, 999);


                    WeekViewEvent newEvent = new WeekViewEvent(1,event.getName(),startTime,endTime);
                    newEvent.setStartTime(startTime);
                    newEvent.setEndTime(endTime);
                    newEvent.setColor(event.getColor());
                    newEvents.add(newEvent);
                }

        }

        return newEvents;

    }

    public List<Integer> getAllSelectedDaysInMonth(List<Integer> selectedDays){
        //If selected day is 3 means monday which is on 3rd day in month then
        // 10 , 17 will be selected.
        List<Integer> selectedDaysInMonth = new ArrayList<>();
        for(int selectedDay:selectedDays){
            for(int i=0;i<5;i++){
                selectedDaysInMonth.add((selectedDay+i*7)+1);
            }
        }
        return selectedDaysInMonth;
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {

    }

    public static Calendar getFirstDay(int newMonth, int year, int weekday) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, newMonth);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.DAY_OF_MONTH, 1);
        int day = c.get(Calendar.DAY_OF_WEEK);
        while (day != weekday) {
            c.add(Calendar.DAY_OF_MONTH, 1);
            day = c.get(Calendar.DAY_OF_WEEK);
        }
        return c;
    }
}
