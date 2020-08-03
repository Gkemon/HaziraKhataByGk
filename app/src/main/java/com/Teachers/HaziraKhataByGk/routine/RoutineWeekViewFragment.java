package com.Teachers.HaziraKhataByGk.routine;

import android.content.Context;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.Teachers.HaziraKhataByGk.HelperClassess.CustomArrayList;
import com.Teachers.HaziraKhataByGk.HelperClassess.DialogUtils;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsDateTime;
import com.Teachers.HaziraKhataByGk.Listener.CommonCallback;
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


public class RoutineWeekViewFragment extends Fragment implements MonthLoader.MonthChangeListener,
        WeekView.EventClickListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener {
    View root;
    Context context;
    LiveData<List<RoutineItem>> liveEvents;
    CustomArrayList<RoutineItem> events;
    private WeekView mWeekView;
    private String routineType;
    private RoutineViewModel routineViewModel;

    public RoutineWeekViewFragment() {

    }

    public static Calendar getCurrentMonthCalendar(int newMonth, int year) {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.MONTH, newMonth);
        c.set(Calendar.YEAR, year);

        return c;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_routine_week_view, container, false);
        initData();

        return root;
    }

    private void initData() {

        mWeekView = root.findViewById(R.id.weekView);
        mWeekView.setOnEventClickListener(this);
        mWeekView.setMonthChangeListener(this);
        mWeekView.setEventLongPressListener(this);
        mWeekView.setEmptyViewLongPressListener(this);
        setupDateTimeInterpreter(false);

        routineViewModel = new ViewModelProvider(getActivity()).get(RoutineViewModel.class);

        events = new CustomArrayList<>();
        if (getArguments() != null) {
            routineType = getArguments().getString(RoutineConstant.routineType);
            if (UtilsCommon.isValideString(routineType))
                liveEvents = routineViewModel.getAllRoutines(routineType);
            if (liveEvents != null) {
                liveEvents.observe(getViewLifecycleOwner(), routineItems -> {
                    if(routineItems!=null){

                        //FOR ADD: If an item is newly created then it needs to scroll into this item.
                        //Or if it is just open then then scroll to current time only.
                        //FOR EDIT: Also if getSelectedRoutineItem is not null then that means that
                        // it is going for edit or update


                        if(InputOperation.EDIT.equals(routineViewModel.inputOperation)){
                            if(routineViewModel.getSelectedRoutineItem()!=null)//Edited
                                mWeekView.post(() -> mWeekView.goToHour(routineViewModel.getSelectedRoutineItem()
                                    .getStartTime().get(Calendar.HOUR_OF_DAY)));
                            else mWeekView.post(() -> mWeekView.goToHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)));
                        }
                        else if(InputOperation.ADD.equals(routineViewModel.inputOperation)){
                            RoutineItem lastRoutineItem=!routineItems.isEmpty() ?
                                    routineItems.get(routineItems.size() - 1):null;
                            if(routineItems.size() > events.size())//That means new item is added
                                mWeekView.post(() -> mWeekView.goToHour(lastRoutineItem.getStartTime().get(Calendar.HOUR_OF_DAY)));
                            else mWeekView.post(() -> mWeekView.goToHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)));
                        }
                        else
                            mWeekView.post(() -> mWeekView.goToHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)));

                        events.clear();
                        events.addAll(routineItems);
                        mWeekView.notifyDatasetChanged();
                    }


                });
            }
        }


    }

    private RoutineItem getRoutineByID(long id) {
        for (RoutineItem routineItem : events) {
            if (routineItem.id == id) return routineItem;
        }
        return null;
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
                return hour > 11 ?(hour==12?"12 PM":(hour - 12) + " PM" ): (hour == 0 ? "12 AM" : hour + " AM");
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
        if (getRoutineByID(event.id) != null) {
            routineViewModel.setSelectedRoutineItem(getRoutineByID(event.id));
            routineViewModel.inputOperation=InputOperation.EDIT;
            if (getActivity() != null) {
                RoutineInputDialog.showDialog(getActivity().getSupportFragmentManager());
            }
        }
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

        DialogUtils.showInfoAlertDialog("সতর্কতা", "আপনি কি রুটিনটি ডিলেট করতে চান?",
                getContext(), new CommonCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean response) {
                        if(response)routineViewModel.deleteByID(event.id);
                    }
                });

    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {


        if (events != null) {
            List<WeekViewEvent> newEvents = new ArrayList<>();

            for (RoutineItem event : events) {

                if (event == null || event.getStartTime() == null || event.getEndTime() == null)
                    continue;

                Calendar dateStartTime = event.getStartTime();
                Calendar dateEndTime = event.getEndTime();


                Calendar monCal = getCurrentMonthCalendar(newMonth - 1, newYear);


                int hday = dateStartTime.get(Calendar.HOUR_OF_DAY);
                int mday = dateStartTime.get(Calendar.MINUTE);

                int ehday = dateEndTime.get(Calendar.HOUR_OF_DAY);
                int emday = dateEndTime.get(Calendar.MINUTE);


                for (int k = 1; k <= monCal.getActualMaximum(Calendar.DAY_OF_MONTH); k += 1) {


                    //Get day of this date and check the day is exist in day list of the event or not
                    if (!hasEventForThisDay(event, newMonth, k)) continue;

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


                    WeekViewEvent newEvent = new WeekViewEvent(event.id, event.getName(), startTime, endTime);
                    newEvent.setStartTime(startTime);
                    newEvent.setEndTime(endTime);
                    newEvent.setColor(event.getColor());
                    newEvents.add(newEvent);
                }

            }
            return newEvents;
        }
        return null;

    }

    private boolean hasEventForThisDay(RoutineItem event, int newMonth, int noOfDayInMonth) {

        if (event != null) {


            Calendar currentCal = Calendar.getInstance();
            currentCal.set(Calendar.MONTH, newMonth - 1);
            currentCal.set(Calendar.DAY_OF_MONTH, noOfDayInMonth);


            //If it is temporary routine
            if (!event.isPermanent()) {
                return event.getDateIfTemporary() != null && UtilsDateTime.isDateEqualIgnoringTime(
                        event.getDateIfTemporary().getTime(), currentCal.getTime());
            } else {
                int day = currentCal.get(Calendar.DAY_OF_WEEK);
                return event.getSelectedDayList().contains(day);
            }


        } else return false;
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {

    }

}
