package com.Teachers.HaziraKhataByGk.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.model.AttendenceData;
import com.Teachers.HaziraKhataByGk.model.class_item;
import com.Teachers.HaziraKhataByGk.model.student;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by uy on 9/7/2017.
 */

public class SingleStudentPresentDateListAdaper extends BaseAdapter {
   public ArrayList<String> attendenceListForSingleStudent;
   public Activity activity;
   public Context context;
   public class_item class_item;
   public student student;
   public static String time, subject,yearWithDate,year,month,day;
   public ArrayList<AttendenceData> attendenceDataArrayList;
   public ArrayList<Boolean> absentPresent;//For Creating  Drawable "P" and "A"

    public SingleStudentPresentDateListAdaper(Context context,Activity activity, ArrayList<String> attendenceListForSingleStudent, ArrayList<Boolean> absentPresent,class_item class_item,student student,ArrayList<AttendenceData> attendenceDataArrayList) {
        this.attendenceListForSingleStudent = attendenceListForSingleStudent;
        this.activity = activity;
        this.context=context;
        this.absentPresent=absentPresent;
        this.class_item=class_item;
        this.student=student;
        this.attendenceDataArrayList=attendenceDataArrayList;

        Collections.reverse(this.attendenceDataArrayList);//TO REVERSE THE ATTENDANCE LIST;
        Collections.reverse(this.absentPresent);//TO REVERSE THE BOOLEAN LIST;
        Collections.reverse(this.attendenceListForSingleStudent);//TO REVERSE THE ARRAY LIST;

    }

    @Override
    public int getCount() {
        return attendenceListForSingleStudent.size();
    }

    @Override
    public Object getItem(int position) {
        return attendenceListForSingleStudent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(activity);
            v = vi.inflate(R.layout.single_student_attendence_list_date, null);
        }
        final int pos = position;
        TextView textView = (TextView) v.findViewById(R.id.SingleStudentAttendeceDateList);
        ImageView imageView=(ImageView) v.findViewById(R.id.PresentOrAbsent);



        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = activity.getFragmentManager();
                createRequest request = new createRequest();
                request.addData(student, class_item,attendenceDataArrayList.get(pos));
                request.show(fm, "Select");
            }
        });

        textView.setText((attendenceListForSingleStudent.get(position)));

        if(absentPresent.get(pos)) {

            Glide.with(context)
                    .load((Integer) R.drawable.present)
                    .into(imageView);

                    //imageView.setBackgroundResource(R.drawable.present);
        }
        else {

            Glide.with(context)
                    .load((Integer) R.drawable.absent)
                    .into(imageView);

                    //imageView.setBackgroundResource(R.drawable.absent);
        }


        //How to add set onlickListern in Growable listView adapter

//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent launchinIntent = new Intent(AttendanceActivity.activity, studentAllInfoShowActiviy.class_room);
//                String roll = AttendanceActivity.rolls.get(pos);
//                launchinIntent.putExtra("Roll", roll);
//                AttendanceActivity.activity.startActivity(launchinIntent);
//
//            }
//        });

        return v;
    }

    public static class createRequest extends DialogFragment {
        com.Teachers.HaziraKhataByGk.model.student student;
        com.Teachers.HaziraKhataByGk.model.class_item class_item;
        AttendenceData attendenceData;

        public void addData(student student,class_item class_item,AttendenceData attendenceData){
            this.class_item=class_item;
            this.student=student;
            this.attendenceData=attendenceData;
        }

        public static int StringMonthToIntMonthConvertor(String month){

            if(month.equals("Jan")){

              return 0;
            }
            else if (month.equals("Feb")){
                return 1;
            }
            else if (month.equals("Mar")){
                return 2;
            }
            else if (month.equals("Apr")){
                return 3;
            }
            else if (month.equals("May")){
                return 4;
            }
            else if (month.equals("Jun")){
                return 5;
            }
            else if (month.equals("Jul")){
                return 6;
            }
            else if (month.equals("Aug")) {
                return 7;
            }
            else if (month.equals("Sep")){
                    return 8;
            }
            else if (month.equals("Oct")){
                    return 9;
            }
            else if (month.equals("Nov")){
                    return 10;
            }
            else return 11;


        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View v = inflater.inflate(R.layout.dialoage_for_edit_single_attendence_items, null);
            final EditText Subject = (EditText) v.findViewById(R.id.periodID);
            final CheckBox checkBox = (CheckBox)v.findViewById(R.id.attMarker);//For attendance
            final CheckBox checkBox1 =(CheckBox) v.findViewById(R.id.absentMarker);//For absent
            final DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);
            Date date = new Date();

            time=attendenceData.getDate();
            yearWithDate = time.substring(Math.max(time.length() - 8, 0));
            month = yearWithDate.substring(0,Math.min(yearWithDate.length(), 3));
            year = time.substring(Math.max(time.length() - 4, 0));
            if(time.substring(6,6).equals(" "))
            {
                day = time.substring(5,5);
            }
            else
            {
                day = time.substring(5,7);
            }

            int Month=StringMonthToIntMonthConvertor(month);



            Log.d("GK", year + " year");
            Log.d("GK", month + " month");
            Log.d("GK", yearWithDate + " year With Date");
            Log.d("GK", day + "Day");

            try {

                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy");
                sdf.setLenient(false);

                date=sdf.parse(attendenceData.getDate());
                Log.d("GK","Main Date"+attendenceData.getDate()+" "+String.valueOf(date.getYear()+1900)+ " - "+String.valueOf(date.getMonth())+ " - "+String.valueOf(date.getDay())+ " ");

            }
            catch (java.text.ParseException e){
                e.printStackTrace();
                Log.d("GK","Catch"+attendenceData.getDate());
            }

            datePicker.updateDate(Integer.valueOf(year.trim()),Month,Integer.valueOf(day.trim()));






            //Check and uncheck the check box vice versa
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBox1.setChecked(!checkBox.isChecked());
                }
            });

            checkBox1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBox.setChecked(!checkBox1.isChecked());
                }
            });



            builder.setView(v).setPositiveButton("উপস্থিত", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    {

                        int day = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth();
                        int year = datePicker.getYear()-1900 ;
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
                        String formatedDate = simpleDateFormat.format(new Date(year, month, day));

                        String subject = Subject.getText().toString();
                        if (subject.equals("")) subject = "অনির্ধারিত";

                        //ADD ATTENDANCE
                        AttendenceData attendenceData = new AttendenceData();
                        attendenceData.setStatus(true);
                        attendenceData.setSubject(subject);
                        attendenceData.setDate(formatedDate);

                        //Add to database
//                        MainActivity.databaseReference.child("Users").
//                                child(mUserId).child("Class").
//                                child(class_item.getName()+class_item.getSection())
//                                .child("Student").child(student.getId()).child("Attendance").push()
//                                .setValue(attendenceData);
//
//                        onResume();
//                        singleStudentPresentDateListAdaper.notifyDataSetChanged();

                        dialog.dismiss();
                    }
                }
            }).setNegativeButton("অনুপস্থিত", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    final DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);
                    int day = datePicker.getDayOfMonth();
                    int month = datePicker.getMonth();
                    int year = datePicker.getYear()-1900 ;
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
                    String formatedDate = simpleDateFormat.format(new Date(year, month, day));
                    String date = year + "-" + month + "-" + day;
                    String subject = Subject.getText().toString();
                    if (subject.equals("")) subject = "অনির্ধারিত";


                    //ADD ATTENDANCE
                    AttendenceData attendenceData = new AttendenceData();
                    attendenceData.setStatus(false);
                    attendenceData.setSubject(subject);
                    attendenceData.setDate(formatedDate);


                    //Add to database
//                    MainActivity.databaseReference.child("Users").
//                            child(mUserId).child("Class").
//                            child(class_item.getName()+class_item.getSection())
//                            .child("Student").child(student.getId()).child("Attendance").push()
//                            .setValue(attendenceData);
//
//                    singleStudentPresentDateListAdaper.notifyDataSetChanged();

                    dialog.dismiss();
                }
            });
            return builder.create();
        }
    }



}


