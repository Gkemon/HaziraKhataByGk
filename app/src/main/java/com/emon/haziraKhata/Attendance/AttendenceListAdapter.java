package com.emon.haziraKhata.Attendance;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.emon.haziraKhata.Firebase.FirebaseCaller;
import com.emon.haziraKhata.HelperClasses.DialogUtils;
import com.emon.haziraKhata.HelperClasses.UtilsCommon;
import com.emon.haziraKhata.HelperClasses.ViewUtils.UtilsView;
import com.emon.haziraKhata.Listener.CommonCallback;
import com.emon.haziraKhata.Model.AttendenceData;
import com.emon.haziraKhata.Model.ClassItem;
import com.emon.haziraKhata.Model.Student;
import com.emon.haziraKhata.R;
import com.emon.haziraKhata.SingleStudentAllInformation.StudentAlIInfoShowActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.emon.haziraKhata.Attendance.AttendanceActivity.checkHash;

public class AttendenceListAdapter extends BaseAdapter {


    public ClassItem classItem;
    public Activity activity;
    public List<Student> studentArrayList;
    private ArrayList<String> nameList;
    private ArrayList<Boolean> attendanceList;

    public AttendenceListAdapter(Activity activity, ArrayList<String> nameList, ClassItem classItem, List<Student> studentArrayList) {
        this.nameList = nameList;
        this.activity = activity;
        this.classItem = classItem;
        attendanceList = new ArrayList<>();
        for (int i = 0; i < nameList.size(); i++) {
            attendanceList.add(new Boolean(true));
        }
        this.studentArrayList = studentArrayList;
    }


    @Override
    public int getCount() {
        return nameList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {

        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(activity);
            v = vi.inflate(R.layout.attendence_item, null);
        }

        final int pos = position;
        Log.d("GK", position + " position");

        Student student = studentArrayList.get(position);

        TextView textView = v.findViewById(R.id.attendanceText);
        ImageView imgProfilePic = v.findViewById(R.id.img_profile);
        String text;
        text = (nameList.get(position));
        textView.setText(Html.fromHtml(text));


        if (UtilsCommon.isValideString(student.getImageUrl()))
            Glide.with(activity)
                    .load(student.getImageUrl())
                    .apply(UtilsView.getLoadingOptionForGlide(activity))
                    .into(imgProfilePic);
        else {
            imgProfilePic.setVisibility(View.GONE);
        }


        final CheckBox checkBox = (CheckBox) v.findViewById(R.id.attMarker);//For attendance
        final CheckBox checkBox1 = (CheckBox) v.findViewById(R.id.absentMarker);//For absent


        if (checkHash.containsKey(pos)) {
            if (checkHash.get(pos)) {
                checkBox.setChecked(true);
                checkBox1.setChecked(false);
                attendanceList.set(pos, checkBox.isChecked());
            } else {

                attendanceList.set(pos, false);
                checkBox.setChecked(false);
                checkBox1.setChecked(true);
            }
        } else {
            checkBox.setChecked(true);
            checkBox1.setChecked(false);
        }


        //How toTime add set onlickListern in Growable listView adapter

        v.findViewById(R.id.attendanceText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchinIntent = new Intent(activity, StudentAlIInfoShowActivity.class);
                String roll = AttendanceActivity.rolls.get(pos);

                launchinIntent.putExtra("Student", studentArrayList.get(position));
                launchinIntent.putExtra("classItem", classItem);
                launchinIntent.putExtra("Roll", roll);
                activity.startActivity(launchinIntent);
            }
        });


        //Check and uncheck the check box vice versa
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("GK", pos + " pos in checkBox of list view");
                attendanceList.set(pos, checkBox.isChecked());
                checkBox1.setChecked(!checkBox.isChecked());
                checkHash.put(pos, checkBox.isChecked());
            }
        });

        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendanceList.set(pos, !checkBox1.isChecked());
                checkBox.setChecked(!checkBox1.isChecked());
                checkHash.put(pos, !checkBox1.isChecked());
            }
        });

        return v;
    }

    public void setCheckHash(HashMap<Integer, Boolean> hash) {
        checkHash = hash;
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public String getItem(int position) {
        return nameList.get(position);
    }

    public void remove(String item) {
        int position = nameList.indexOf(item);
        if (position > -1) {
            nameList.remove(position);

        }
    }

    public int getItemCount() {
        return nameList.size();
    }

    public void saveAll() {


        double totalAttendendStudentNumber = 0;
        double totalAbsentStudentNumberPersentage = 0;
        double totalAbsentStudentNumber = 0;
        for (int i = 0; i < nameList.size(); i++) {
            boolean sts;
            if (attendanceList.get(i)) {
                sts = true;
                totalAttendendStudentNumber++;
            } else sts = false;
            AttendenceData attendenceData = new AttendenceData();
            attendenceData.setStatus(sts);
            attendenceData.setSubject(AttendanceActivity.subject);
            attendenceData.setDate(AttendanceActivity.time);

            FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).
                    child("Class").
                    child(AttendanceActivity.classitemAttendence.getName() +
                            AttendanceActivity.classitemAttendence.getSection()).
                    child("Student").child(AttendanceActivity.rolls.get(i)).
                    child("Attendance").push().setValue(attendenceData);


        }
        totalAbsentStudentNumberPersentage = (totalAttendendStudentNumber / nameList.size()) * 100.00;
        totalAbsentStudentNumber = nameList.size() - totalAttendendStudentNumber;
        String massegeOfDailyPersentage = "আজকের মোট উপস্থিত শিক্ষার্থীর সংখ্যা " + (int) totalAttendendStudentNumber + ",মোট অনুপস্থিত শিক্ষার্থীর সংখ্যা " + (int) totalAbsentStudentNumber + ", এবং শতকরা উপস্থিতির হার " + (int) totalAbsentStudentNumberPersentage + "%";


        DialogUtils.showInfoAlertDialog("", massegeOfDailyPersentage, activity, new CommonCallback() {
            @Override
            public void onSuccess() {
                activity.finish();
            }

        });


    }
}


