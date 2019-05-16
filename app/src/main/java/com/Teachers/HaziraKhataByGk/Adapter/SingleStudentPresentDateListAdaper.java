package com.Teachers.HaziraKhataByGk.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.Teachers.HaziraKhataByGk.Model.ClassIitem;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.Listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.Model.AttendenceData;
import com.Teachers.HaziraKhataByGk.Model.student;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by uy on 9/7/2017.
 */

public class SingleStudentPresentDateListAdaper extends BaseAdapter {
   public ArrayList<String> attendenceListForSingleStudent;
   public Activity activity;
   public Context context;
   public ClassIitem ClassIitem;
   public static student student;
   public ArrayList<AttendenceData> attendenceDataArrayList;
   public ArrayList<Boolean> absentPresent;//For Creating  Drawable "P" and "A"
    public String roll;

    public SingleStudentPresentDateListAdaper(Context context, Activity activity, ArrayList<String> attendenceListForSingleStudent, ArrayList<Boolean> absentPresent, ClassIitem ClassIitem, student student, ArrayList<AttendenceData> attendenceDataArrayList, String roll) {
        this.attendenceListForSingleStudent = attendenceListForSingleStudent;
        this.activity = activity;
        this.context=context;
        this.absentPresent=absentPresent;
        this.ClassIitem = ClassIitem;
        this.attendenceDataArrayList=attendenceDataArrayList;
        this.roll=roll;

      //  Collections.reverse(this.attendenceListKEYForSingleStudent);//TO REVERSE THE ATTENDANCE LIST KEY;
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




        return v;
    }

//    public static class createRequest extends DialogFragment {
//       public static    com.Teachers.HaziraKhataByGk.model.student student;
//        com.Teachers.HaziraKhataByGk.model.ClassIitem ClassIitem;
//        AttendenceData attendenceData;
//        int pos;
//        public ArrayList<String> attendenceListKEYForSingleStudent;
//
//
//        public static FirebaseAuth auth;
//        public static FirebaseDatabase firebaseDatabase;
//        public static DatabaseReference databaseReference;
//        public static String FirebaseCaller.getUserID();
//        public static FirebaseUser mFirebaseUser;
//        public String roll;
//
//
//
//        public void addData(student student,ClassIitem ClassIitem,AttendenceData attendenceData,int pos,ArrayList<String> attendenceListKEYForSingleStudent,String roll){
//            this.ClassIitem=ClassIitem;
//            this.student=student;
//            this.attendenceData=attendenceData;
//            this.pos=pos;
//            this.roll=roll;
//            this.attendenceListKEYForSingleStudent=attendenceListKEYForSingleStudent;
//        }
//
//        public void ConnectWithServer(){
//            //TODO:DATABASE CONNECTION
//            firebaseDatabase = FirebaseDatabase.getInstance();
//            databaseReference = firebaseDatabase.getReference();
//            //TODO: USER (for FB logic auth throw null pointer exception)
//            auth = FirebaseAuth.getInstance();
//            mFirebaseUser = auth.getCurrentUser();
//            databaseReference.keepSynced(true);
//            FirebaseCaller.getUserID() = mFirebaseUser.getUid();
//            MainActivity.databaseReference = databaseReference;
//            MainActivity.FirebaseCaller.getUserID() = FirebaseCaller.getUserID();
//        }
//
//        public static int StringMonthToIntMonthConvertor(String month){
//
//            if(month.equals("Jan")){
//
//              return 0;
//            }
//            else if (month.equals("Feb")){
//                return 1;
//            }
//            else if (month.equals("Mar")){
//                return 2;
//            }
//            else if (month.equals("Apr")){
//                return 3;
//            }
//            else if (month.equals("May")){
//                return 4;
//            }
//            else if (month.equals("Jun")){
//                return 5;
//            }
//            else if (month.equals("Jul")){
//                return 6;
//            }
//            else if (month.equals("Aug")) {
//                return 7;
//            }
//            else if (month.equals("Sep")){
//                    return 8;
//            }
//            else if (month.equals("Oct")){
//                    return 9;
//            }
//            else if (month.equals("Nov")){
//                    return 10;
//            }
//            else return 11;
//
//
//        }
//
//
//        @Override
//        public void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//        }
//
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
//            LayoutInflater inflater = getActivity().getLayoutInflater();
//            final View v = inflater.inflate(R.layout.dialoage_for_edit_single_attendence_items, null);
//            final EditText Subject = (EditText) v.findViewById(R.id.periodID);
//            final CheckBox checkBox = (CheckBox)v.findViewById(R.id.attMarker);//For attendance
//            final CheckBox checkBox1 =(CheckBox) v.findViewById(R.id.absentMarker);//For absent
//            final DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);
//
//            time=attendenceData.getDate();
//            yearWithDate = time.substring(Math.max(time.length() - 8, 0));
//            month = yearWithDate.substring(0,Math.min(yearWithDate.length(), 3));
//            year = time.substring(Math.max(time.length() - 4, 0));
//            if(time.substring(6,6).equals(" "))
//            {
//                day = time.substring(5,5);
//            }
//            else
//            {
//                day = time.substring(5,7);
//            }
//
//            int Month=StringMonthToIntMonthConvertor(month);
//
//            //TODO:LOGCAT
////            Log.d("GK", year + " year");
////            Log.d("GK", month + " month");
////            Log.d("GK", yearWithDate + " year With Date");
////            Log.d("GK", day + "Day");
//
//            //TODO: GENERATE DATE FROM EEE,d MMM yyy pattern
////            try {
////
////                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy");
////                sdf.setLenient(false);
////                date=sdf.parse(attendenceData.getDate());
////                Log.d("GK","Main Date"+attendenceData.getDate()+" "+String.valueOf(date.getYear()+1900)+ " - "+String.valueOf(date.getMonth())+ " - "+String.valueOf(date.getDay())+ " ");
////
////            }
////            catch (java.text.ParseException e){
////                e.printStackTrace();
////                Log.d("GK","Catch"+attendenceData.getDate());
////            }
//
//            datePicker.updateDate(Integer.valueOf(year.trim()),Month,Integer.valueOf(day.trim()));
//
//
//
//
//            Subject.setText(attendenceData.getSubject());
//            if(attendenceData.getStatus())
//            {
//                checkBox.setChecked(true);
//                checkBox1.setChecked(false);
//            }
//            else {
//                checkBox1.setChecked(true);
//                checkBox.setChecked(false);
//            }
//
//
//            //Check and uncheck the check box vice versa
//            checkBox.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    checkBox1.setChecked(!checkBox.isChecked());
//                }
//            });
//
//            checkBox1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    checkBox.setChecked(!checkBox1.isChecked());
//                }
//            });
//
//
//
//            builder.setView(v).
//                    setPositiveButton("পরিবর্তন করুন", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int id) {
//                    {
//
//                        int day = datePicker.getDayOfMonth();
//                        int month = datePicker.getMonth();
//                        int year = datePicker.getYear()-1900 ;
//                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
//                        String formatedDate = simpleDateFormat.format(new Date(year, month, day));
//
//                        String subject = Subject.getText().toString();
//                       // if (subject.equals("")) subject = "অনির্ধারিত";
//
//                        //ADD ATTENDANCE
//                        AttendenceData attendenceData = new AttendenceData();
//                        attendenceData.setStatus(checkBox.isChecked());
//                        attendenceData.setSubject(subject);
//                        attendenceData.setDate(formatedDate);
//                        ConnectWithServer();
//
//                        //set date
//                        if(student!=null){
//
////                            MainActivity.databaseReference.child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(student.getId()).child("Attendance").child( attendenceListKEYForSingleStudent.get(pos)).removeValue();
////
////                            MainActivity.databaseReference.child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(student.getId()).child("Attendance").child( attendenceListKEYForSingleStudent.get(pos)).setValue(attendenceData);
////
//
//                            Log.d("GK","if "+roll+" "+student.getId());
//                            MainActivity.databaseReference.child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(student.getId()).child("Attendance").child( attendenceListKEYForSingleStudent.get(pos)).child("date").setValue(formatedDate);
//                            //set True or False
//                            MainActivity.databaseReference.child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(student.getId()).child("Attendance").child( attendenceListKEYForSingleStudent.get(pos)).child("status").setValue(checkBox.isChecked());
//                            //set subject
//                            MainActivity.databaseReference.child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(student.getId()).child("Attendance").child( attendenceListKEYForSingleStudent.get(pos)).child("subject").setValue(subject);
//                        }
//                        else {
//                            Log.d("GK","Else "+roll);
//                            MainActivity.databaseReference.child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(roll).child("Attendance").child( attendenceListKEYForSingleStudent.get(pos)).child("date").setValue(formatedDate);
//                            //set True or False
//                            MainActivity.databaseReference.child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(roll).child("Attendance").child( attendenceListKEYForSingleStudent.get(pos)).child("status").setValue(checkBox.isChecked());
//                            //set subject
//                            MainActivity.databaseReference.child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(roll).child("Attendance").child( attendenceListKEYForSingleStudent.get(pos)).child("subject").setValue(subject);
//
//                        }
//
//
//                        //Add to database
////                        MainActivity.databaseReference.child("Users").
////                                child(FirebaseCaller.getUserID()).child("Class").
////                                child(ClassIitem.getName()+ClassIitem.getSection())
////                                .child("Student").child(student.getId()).child("Attendance").push()
////                                .setValue(attendenceData);
////
////                        onResume();
//      //                 singleStudentPresentDateListAdaper.notifyDataSetChanged();
//
//                       isDialoagDismissed=true;
//                        dialog.dismiss();
//                    }
//                }
//            }).setNegativeButton("বাদ দিন", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int id) {
//                    dialog.dismiss();
//                }
//            }).setNeutralButton("ডিলিট করুন", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int id) {
//                    ConnectWithServer();
//                    if(student!=null){
//
//                        MainActivity.databaseReference.child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(student.getId()).child("Attendance").child( attendenceListKEYForSingleStudent.get(pos)).removeValue();
//
//                    }
//                    else {
//                        Log.d("GK","Else "+roll);
//                        MainActivity.databaseReference.child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(roll).child("Attendance").child( attendenceListKEYForSingleStudent.get(pos)).removeValue();
//                    }
//
//                    dialog.dismiss();
//
//                }
//            });
//            return builder.create();
//        }
//    }





}


