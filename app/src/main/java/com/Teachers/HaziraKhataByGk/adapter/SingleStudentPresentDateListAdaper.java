package com.Teachers.HaziraKhataByGk.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.Teachers.HaziraKhataByGk.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by uy on 9/7/2017.
 */

public class SingleStudentPresentDateListAdaper extends BaseAdapter {
   public ArrayList<String> attendenceListForSingleStudent;
    Activity activity;
   public ArrayList<Boolean> absentPresent;//For Creating  Drawable "P" and "A"

    public SingleStudentPresentDateListAdaper(Activity activity, ArrayList<String> attendenceListForSingleStudent,ArrayList<Boolean> absentPresent) {
        this.attendenceListForSingleStudent = attendenceListForSingleStudent;
        this.activity = activity;
        this.absentPresent=absentPresent;

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

        textView.setText((attendenceListForSingleStudent.get(position)));

        if(absentPresent.get(pos)) {
                    imageView.setBackgroundResource(R.drawable.present);
        }
        else {

                    imageView.setBackgroundResource(R.drawable.absent);
        }


        //How to add set onlickListern in Growable listView adapter

//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent launchinIntent = new Intent(attendanceActivity.activity, studentAllInfoShowActiviy.class_room);
//                String roll = attendanceActivity.rolls.get(pos);
//                launchinIntent.putExtra("Roll", roll);
//                attendanceActivity.activity.startActivity(launchinIntent);
//
//            }
//        });

        return v;
    }



}
