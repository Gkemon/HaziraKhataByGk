package com.Teachers.HaziraKhataByGk.Attendance;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.Teachers.HaziraKhataByGk.Attendance.AttendanceActivity;
import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.HelperClassess.DialogUtils;
import com.Teachers.HaziraKhataByGk.Listener.CommonCallback;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.Model.AttendenceData;
import com.Teachers.HaziraKhataByGk.Model.ClassIitem;
import com.Teachers.HaziraKhataByGk.SingleStudentAllInformation.StudentAlIInfoShowActiviy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import static com.Teachers.HaziraKhataByGk.Attendance.AttendanceActivity.checkHash;

public class AttendenceListAdapter extends BaseAdapter {


    public static FirebaseAuth auth;
    public ClassIitem ClassIitem;

    private   ArrayList<String> nameList;
    public Activity activity;
    private ArrayList<Boolean> attendanceList;
    public AttendenceListAdapter(Activity activity, ArrayList<String> nameList, ClassIitem ClassIitem) {
        this.nameList = nameList;
        this.activity = activity;
        this.ClassIitem = ClassIitem;
        attendanceList = new ArrayList<>();
        for(int i=0; i<nameList.size(); i++)
        {
            attendanceList.add(new Boolean(true));
        }
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
            Log.d("GK",position + " position");
            TextView textView = (TextView) v.findViewById(R.id.attendanceText);
             String text;
             text=(nameList.get(position));
            textView.setText(text);




            final CheckBox checkBox = (CheckBox)v.findViewById(R.id.attMarker);//For attendance
            final CheckBox checkBox1 =(CheckBox) v.findViewById(R.id.absentMarker);//For absent



        if(checkHash.containsKey(pos)){
            if(checkHash.get(pos)) {
                checkBox.setChecked(true);
                checkBox1.setChecked(false);
                attendanceList.set(pos,checkBox.isChecked());
            }
            else {

                attendanceList.set(pos,false);
                checkBox.setChecked(false);
                checkBox1.setChecked(true);
            }
        }else {
            checkBox.setChecked(true);
            checkBox1.setChecked(false);
        }



            //How to add set onlickListern in Growable listView adapter

        v.findViewById(R.id.attendanceText).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent launchinIntent = new Intent(activity, StudentAlIInfoShowActiviy.class);
                    String roll = AttendanceActivity.rolls.get(pos);
                    Log.d("GK",pos + " pos in click listener of list view");
                    launchinIntent.putExtra("Roll", roll);
                    launchinIntent.putExtra("classItem", ClassIitem);
                    activity.startActivity(launchinIntent);
                }
            });


            //Check and uncheck the check box vice versa
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("GK",pos + " pos in checkBox of list view");
                    attendanceList.set(pos,checkBox.isChecked());
                    checkBox1.setChecked(!checkBox.isChecked());
                    checkHash.put(pos,checkBox.isChecked());
                }
            });

            checkBox1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    attendanceList.set(pos,!checkBox1.isChecked());
                    checkBox.setChecked(!checkBox1.isChecked());
                    checkHash.put(pos,!checkBox1.isChecked());
                }
            });

        return v;
    }

public void setCheckHash(HashMap<Integer,Boolean> hash){
        checkHash=hash;
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
                    child(AttendanceActivity.classitemAttendence.getName() + AttendanceActivity.classitemAttendence.getSection()).
                    child("Student").child(AttendanceActivity.rolls.get(i)).
                    child("Attendance").push().setValue(attendenceData);


        }
        totalAbsentStudentNumberPersentage = (totalAttendendStudentNumber / nameList.size()) * 100.00;
        totalAbsentStudentNumber = nameList.size() - totalAttendendStudentNumber;
        String massegeOfDailyPersentage = "আজকের মোট উপস্থিত শিক্ষার্থীর সংখ্যা " + (int) totalAttendendStudentNumber + ",মোট অনুপস্থিত শিক্ষার্থীর সংখ্যা " + (int) totalAbsentStudentNumber + ", এবং শতকরা উপস্থিতির হার " + (int) totalAbsentStudentNumberPersentage + "%";


        DialogUtils.showInfoAlertDialog("", massegeOfDailyPersentage, new CommonCallback(){
            @Override
            public void onSuccess() {
                activity.finish();
            }

        });







    }}


