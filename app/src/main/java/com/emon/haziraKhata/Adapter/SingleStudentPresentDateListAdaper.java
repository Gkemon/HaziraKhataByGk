package com.emon.haziraKhata.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.emon.haziraKhata.Model.AttendenceData;
import com.emon.haziraKhata.Model.ClassItem;
import com.emon.haziraKhata.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by GK on 9/7/2017.
 */

public class SingleStudentPresentDateListAdaper extends BaseAdapter {
    public ArrayList<String> attendenceListForSingleStudent;
    public Activity activity;

    public ClassItem ClassIitem;

    public ArrayList<AttendenceData> attendenceDataArrayList;
    public ArrayList<Boolean> absentPresent;//For Creating  Drawable "P" and "A"
    public String roll;


    public SingleStudentPresentDateListAdaper(Activity activity, ArrayList<String> attendenceListForSingleStudent, ArrayList<Boolean> absentPresent, ClassItem ClassIitem, ArrayList<AttendenceData> attendenceDataArrayList, String roll) {
        this.attendenceListForSingleStudent = attendenceListForSingleStudent;
        this.activity = activity;
        this.absentPresent = absentPresent;
        this.ClassIitem = ClassIitem;
        this.attendenceDataArrayList = attendenceDataArrayList;
        this.roll = roll;

        //  Collections.reverse(this.attendenceListKEYForSingleStudent);//TO REVERSE THE ATTENDANCE LIST KEY;
        Collections.reverse(this.attendenceDataArrayList);//TO REVERSE THE ATTENDANCE LIST;
        Collections.reverse(this.absentPresent);//TO REVERSE THE BOOLEAN LIST;
        Collections.reverse(this.attendenceListForSingleStudent);//TO REVERSE THE ARRAY LIST;
        notifyDataSetChanged();

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
        ImageView imageView = (ImageView) v.findViewById(R.id.PresentOrAbsent);

        textView.setText((attendenceListForSingleStudent.get(position)));

        if (absentPresent.get(pos)) {

            Glide.with(activity)
                    .load((Integer) R.drawable.present)
                    .into(imageView);

            //imageView.setBackgroundResource(R.drawable.present);
        } else {

            Glide.with(activity)
                    .load((Integer) R.drawable.absent)
                    .into(imageView);

            //imageView.setBackgroundResource(R.drawable.absent);
        }


        return v;
    }

    public void setAttendenceListForSingleStudent(ArrayList<String> attendenceListForSingleStudent) {
        this.attendenceListForSingleStudent = attendenceListForSingleStudent;
        notifyDataSetChanged();
    }
}


