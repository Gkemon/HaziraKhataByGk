package com.Teachers.HaziraKhataByGk.SingleStudentAllInformation;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.Teachers.HaziraKhataByGk.Adapter.SingleStudentPresentDateListAdaper;
import com.Teachers.HaziraKhataByGk.Attendance.AttendanceActivity;
import com.Teachers.HaziraKhataByGk.ClassRoomActivity;
import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.HelperClassess.ComparableDate;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.Model.AttendenceData;
import com.Teachers.HaziraKhataByGk.Model.student;
import com.Teachers.HaziraKhataByGk.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class StudentAlIInfoShowActiviy extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView studentName;
    public Button studentPhoneNumber;
    public Button parentPhoneNumber;
    private ListView datewiseAttendenceListView;
    private ArrayList<String> attendenceTextListForSingleStudent;
    private ArrayList<Boolean> isPresentAbsentList;
    public  SingleStudentPresentDateListAdaper singleStudentPresentDateListAdaper;
    public static student student;
    public static String time,yearWithDate,year,month,day;
    public Spinner spinnerMonth;
    public DatabaseReference dbRefSingleStudent;
    public ArrayList<AttendenceData> totalAttendenceDataArrayList;
    ProgressBar progressBar;
    String roll;
    LinearLayout emptyView;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    void setUpMonthSpinner(){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.month_bd));
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to progressBar
        spinnerMonth.setAdapter(dataAdapter);
    }

    void init(){


        setContentView(R.layout.student_info_show_activity);
        studentName =  findViewById(R.id.studentName);
        studentPhoneNumber =findViewById(R.id.studentPhoneNumber);
        parentPhoneNumber = findViewById(R.id.parentPhoneNumber);
        spinnerMonth = findViewById(R.id.spinner_month);
        spinnerMonth.setOnItemSelectedListener(this);

        progressBar =findViewById(R.id.progressBarInSingleStudentActivity);

        //EMPTY VIEW
        emptyView =findViewById(R.id.toDoEmptyView);

        //INITIALIZE THE BUTTON
        datewiseAttendenceListView = findViewById(R.id.DatewiseAttendence);
        roll = getIntent().getStringExtra("Roll");
        activity = this;

        dbRefSingleStudent=FirebaseCaller.getSingleStudentDbRef(ClassRoomActivity.classitem.getName(),ClassRoomActivity.classitem.getSection(),roll);
        attendenceTextListForSingleStudent = new ArrayList<>();
        isPresentAbsentList = new ArrayList<>();

        attendenceTextListForSingleStudent =new ArrayList<>();
        totalAttendenceDataArrayList =new ArrayList<>();

        ClassRoomActivity.classitem=getIntent().getParcelableExtra("classItem");


        datewiseAttendenceListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Log.d("GK",String.valueOf(position)+" POSITION");
                CreateDialogForEdit(position);


            }
        });

        InitializePhoneNumbers();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getHeadingData();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.single_student_attendence_delete_data, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.custom_delete_dialauge, null);
                dialogBuilder.setView(dialogView);
                final EditText edt = (EditText) dialogView.findViewById(R.id.custom_delete_dialauge_text);
                dialogBuilder.setIcon(R.drawable.warnig_for_delete);
                dialogBuilder.setTitle("শিক্ষার্থীর সকল উপস্থিতির ডাটা ডিলেট করতে চায়?");
                dialogBuilder.setMessage("ডিলিট করার আগে ইংরেজীতে \"DELETE\" শব্দটি লিখুন।");
                dialogBuilder.setPositiveButton("ডিলিট করুন", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (edt.getText().toString().trim().equals("DELETE")) {

                            FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(roll).child("Attendance").removeValue();

                            singleStudentPresentDateListAdaper.notifyDataSetChanged();

                            emptyView.setVisibility(View.VISIBLE);

                        }
                    }
                });
                dialogBuilder.setNegativeButton("বাদ দিন", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                AlertDialog b = dialogBuilder.create();
                b.show();
                return true;
            case R.id.action_add_single_attendence:

                CreateDialogForAddAttendence();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void setUpAttandanceList(ArrayList<AttendenceData> attandanceList){


                attendenceTextListForSingleStudent.clear();
                isPresentAbsentList.clear();



                for (AttendenceData att : attandanceList) {

                    String subject="";

                    if (att != null) {

                        if (!att.getSubject().equals("")) {
                            subject = "(" + att.getSubject() + ")";
                        }


                        if (att.getStatus())
                            attendenceTextListForSingleStudent.add(att.getDate() + subject + "  উপস্থিত");
                        else {
                            attendenceTextListForSingleStudent.add(att.getDate() + subject + "  অনুপস্থিত");
                        }
                        isPresentAbsentList.add(att.getStatus());
                    }
                }


                singleStudentPresentDateListAdaper = new SingleStudentPresentDateListAdaper
                        (StudentAlIInfoShowActiviy.this, attendenceTextListForSingleStudent,
                                isPresentAbsentList, ClassRoomActivity.classitem,attandanceList,roll);


                if(attendenceTextListForSingleStudent.size()==0)
                    emptyView.setVisibility(View.VISIBLE);

        UtilsCommon.debugLog("attendenceDataArrayList: "+attendenceTextListForSingleStudent.size());



        singleStudentPresentDateListAdaper.setAttendenceListForSingleStudent(attendenceTextListForSingleStudent);
        datewiseAttendenceListView.setAdapter(singleStudentPresentDateListAdaper);



            }





    public void setUpHeader(ArrayList<AttendenceData> attendenceDataArrayList){

        long totalAttendPersenten = 0;
        long totalClass = attendenceDataArrayList.size();



        //For Empty view
        if (totalClass == 0) {
            datewiseAttendenceListView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            datewiseAttendenceListView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        long attendClass = 0;


        for(AttendenceData attendenceData:attendenceDataArrayList){
            if(attendenceData.getStatus())attendClass++;
        }



        if (totalClass != 0)   //THIS IS FOR AVOID ARITHMETIC EXCEPTION
            totalAttendPersenten = (attendClass * 100) / totalClass;

        String charSequence;
        if (AttendanceActivity.classitemAttendence != null) {
            String charSequence1 = " শিক্ষার্থীর নাম: " + student.getStudentName() + " \n" +
                    " রোল :" + roll + "    ক্লাস :" + AttendanceActivity.classitemAttendence.getName() + "\n মোট ক্লাস:" + totalClass + "  উপস্থিতি :" + attendClass + "   শতকরা :" + totalAttendPersenten + "% ";
            charSequence = charSequence1;
        } else {
            String charSequence2 = " শিক্ষার্থীর নাম: " + student.getStudentName() + " \n" +
                    " রোল :" + roll + "    ক্লাস :" + ClassRoomActivity.classitem.getName() + "\n মোট ক্লাস:" + totalClass + "  উপস্থিতি :" + attendClass + "   শতকরা :" + totalAttendPersenten + "% ";
            charSequence = charSequence2;
        }
        studentName.setText(charSequence);

    }


    public  void getHeadingData(){
        dbRefSingleStudent.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                student = dataSnapshot.getValue(student.class);


                totalAttendenceDataArrayList.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.child("Attendance").getChildren()) {
                   AttendenceData attendenceData = dataSnapshot1.getValue(AttendenceData.class);

                    if (attendenceData != null) {
                        attendenceData.setKey(dataSnapshot1.getKey());
                        totalAttendenceDataArrayList.add(attendenceData);
                    }

                }



                //TODO: Sorting by date
                Collections.sort(totalAttendenceDataArrayList, new Comparator<AttendenceData>() {
                    @Override
                    public int compare(AttendenceData o1, AttendenceData o2) {
                        ComparableDate comparableDate1,comparableDate2;
                        comparableDate1 = new ComparableDate();
                        comparableDate2 = new ComparableDate();


                        try {
                            comparableDate1.setDateTime(o1.getDate());
                            comparableDate2.setDateTime(o2.getDate());
                        }catch (Exception c){
                        }

                        return comparableDate1.compareTo(comparableDate2);
                    }
                });

                //TODO: Sorting by date



                setUpHeader(totalAttendenceDataArrayList);
                setUpAttandanceList(totalAttendenceDataArrayList);


                progressBar.setVisibility(View.GONE);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void getDatewiseAttendenceList(final String date) {

        ArrayList<AttendenceData> attendenceDataArrayList = new ArrayList<>();
        UtilsCommon.debugLog("SIZE: "+totalAttendenceDataArrayList.size()+" "+date);
        for(int i= 0;i<totalAttendenceDataArrayList.size();i++){

            UtilsCommon.debugLog(totalAttendenceDataArrayList.get(i).getDate());
            if(totalAttendenceDataArrayList.get(i).getDate().contains(date))
                attendenceDataArrayList.add(totalAttendenceDataArrayList.get(i));
        }

        setUpAttandanceList(attendenceDataArrayList);

    }

    public void CreateDialogForAddAttendence(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater1 = this.getLayoutInflater();
        final View v = inflater1.inflate(R.layout.dialoage_for_single_attendence_items, null);
        final EditText Subject = (EditText) v.findViewById(R.id.periodID);
        builder.setView(v).setPositiveButton("উপস্থিত", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int id) {
                {
                    final DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);
                    int day = datePicker.getDayOfMonth();
                    int month = datePicker.getMonth();
                    int year = datePicker.getYear()-1900 ;

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
                    String formatedDate = simpleDateFormat.format(new Date(year, month, day));
                  //  String date = year + "-" + month + "-" + day;
                    String subject = Subject.getText().toString();
                    // if (subject.equals("")) subject = "অনির্ধারিত";

                    //ADD ATTENDANCE
                    AttendenceData attendenceData = new AttendenceData();
                    attendenceData.setStatus(true);
                    attendenceData.setSubject(subject);
                    attendenceData.setDate(formatedDate);

                    //Add to database
                    FirebaseCaller.getFirebaseDatabase().child("Users").
                            child(FirebaseCaller.getUserID()).child("Class").
                            child(ClassRoomActivity.classitem.getName()+ ClassRoomActivity.classitem.getSection())
                            .child("Student").child(student.getId()).child("Attendance").push()
                            .setValue(attendenceData).addOnSuccessListener(StudentAlIInfoShowActiviy.this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            if(singleStudentPresentDateListAdaper!=null)
                                singleStudentPresentDateListAdaper.notifyDataSetChanged();

                            getHeadingData();

                            dialog.dismiss();
                        }
                    });


                }
            }
        }).setNegativeButton("অনুপস্থিত", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int id) {
                final DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear()-1900 ;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
                String formatedDate = simpleDateFormat.format(new Date(year, month, day));
                String subject = Subject.getText().toString();
                // if (subject.equals("")) subject = "অনির্ধারিত";


                //ADD ATTENDANCE
                AttendenceData attendenceData = new AttendenceData();
                attendenceData.setStatus(false);
                attendenceData.setSubject(subject);
                attendenceData.setDate(formatedDate);


                //  Add to database
                FirebaseCaller.getFirebaseDatabase().child("Users").
                        child(FirebaseCaller.getUserID()).child("Class").
                        child(ClassRoomActivity.classitem.getName()+ ClassRoomActivity.classitem.getSection())
                        .child("Student").child(student.getId()).child("Attendance").push()
                        .setValue(attendenceData).addOnSuccessListener(StudentAlIInfoShowActiviy.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if(singleStudentPresentDateListAdaper!=null)
                            singleStudentPresentDateListAdaper.notifyDataSetChanged();

                        getHeadingData();

                        dialog.dismiss();
                    }
                });


            }
        }).create().show();
    }



    public void InitializePhoneNumbers(){
        studentPhoneNumber.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //ACTION_DIALER IS THE BEST SOLUTION TO MAKE CALL
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                //The 'tel:' prefix is required  otherwhise the following exception will be thrown: java.lang.IllegalStateException: Could not execute method of the activity.
                callIntent.setData(Uri.parse("tel:" + student.getPhone()));
                startActivity(callIntent);
            }

        });
        parentPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //ACTION_DIALER IS THE BEST SOLUTION TO MAKE CALL
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                //The 'tel:' prefix is required  otherwhise the following exception will be thrown: java.lang.IllegalStateException: Could not execute method of the activity.
                callIntent.setData(Uri.parse("tel:" + student.getParentContact()));
                startActivity(callIntent);
            }
        });
    }
    public void CreateDialogForEdit( int pos1){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final int pos2= totalAttendenceDataArrayList.size()-1-pos1;
        final int pos=pos1;
        final View v = inflater.inflate(R.layout.dialoage_for_edit_single_attendence_items, null);
        final EditText Subject = (EditText) v.findViewById(R.id.periodID);
        final CheckBox checkBox = (CheckBox)v.findViewById(R.id.attMarker);//For attendance
        final CheckBox checkBox1 =(CheckBox) v.findViewById(R.id.absentMarker);//For absent
        final DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);


        final AttendenceData attendenceData;

        attendenceData=totalAttendenceDataArrayList.get(pos);





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



        datePicker.updateDate(Integer.valueOf(year.trim()),Month,Integer.valueOf(day.trim()));

        Subject.setText(attendenceData.getSubject());


        if(attendenceData.getStatus())
        {
            Log.d("GK","TRUE");
            checkBox.setChecked(true);
            checkBox1.setChecked(false);
        }
        else {
            Log.d("GK","FALSE");
            checkBox1.setChecked(true);
            checkBox.setChecked(false);
        }


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


        builder.setView(v).
                setPositiveButton("পরিবর্তন করুন", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int id) {
                        {

                            int day = datePicker.getDayOfMonth();
                            int month = datePicker.getMonth();
                            int year = datePicker.getYear()-1900 ;
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
                            String formatedDate = simpleDateFormat.format(new Date(year, month, day));

                            String subject = Subject.getText().toString();


                            attendenceData.setStatus(checkBox.isChecked());
                            attendenceData.setSubject(subject);
                            attendenceData.setDate(formatedDate);


                            //set date
                            if(student!=null){
                                //REMOVE FIRST
                                Log.d("GK","if "+roll+" "+student.getId());
                                //set subject
                                FirebaseCaller.getFirebaseDatabase().
                                        child("Users").
                                        child(FirebaseCaller.getUserID()).
                                        child("Class").
                                        child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).
                                        child("Student").child(student.getId()).
                                        child("Attendance").
                                        child( attendenceData.getKey()).
                                        removeValue().addOnSuccessListener(StudentAlIInfoShowActiviy.this, new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {



                                        FirebaseCaller.getFirebaseDatabase().child("Users")
                                                .child(FirebaseCaller.getUserID())
                                                .child("Class")
                                                .child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection())
                                                .child("Student")
                                                .child(student.getId())
                                                .child("Attendance")
                                                .child( attendenceData.getKey()).setValue(attendenceData).addOnSuccessListener(StudentAlIInfoShowActiviy.this, new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                getHeadingData();
                                                dialog.dismiss();
                                            }
                                        });
                                        //set True or False;
                                    }
                                });



                            }




                        }
                    }
                }).setNegativeButton("বাদ দিন", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                getHeadingData();

                dialog.dismiss();
            }
        }).setNeutralButton("ডিলিট করুন", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int id) {


                if(student!=null){

                    FirebaseCaller.getFirebaseDatabase()
                            .child("Users")
                            .child(FirebaseCaller.getUserID())
                            .child("Class")
                            .child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection())
                            .child("Student")
                            .child(student.getId())
                            .child("Attendance")
                            .child(attendenceData.getKey()).removeValue().addOnSuccessListener(StudentAlIInfoShowActiviy.this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            getHeadingData();

                            dialog.dismiss();

                        }
                    });

                }

            }
        }).create().show();


    }


    public static String intMonthToStringMonthConvertor(int position){
        if(position==0){
            return "";
        }else if(position==1)
        {
            return "Jan";
        }
        else if(position==2)
        {
            return "Feb";
        }
        else if(position==3)
        {
            return "Mar";
        }
        else if(position==4)
        {
            return "Apr";
        }
        else if(position==5)
        {
            return "May";
        }
        else if(position==6)
        {
            return "Jun";
        }
        else if(position==7)
        {
            return "Jul";
        }
        else if(position==8)
        {
            return "Aug";
        }
        else if(position==9)
        {
            return "Sep";
        }
        else if(position==10)
        {
            return "Oct";
        }
        else if(position==11)
        {
            return "Nov";
        }
        else
        {
            return "Dec";
        }
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        getDatewiseAttendenceList(intMonthToStringMonthConvertor(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    spinnerMonth.setSelection(0);
    }
}



