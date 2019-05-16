package com.Teachers.HaziraKhataByGk.SingleStudentAllInformation;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.util.TimeUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.Teachers.HaziraKhataByGk.Adapter.SingleStudentPresentDateListAdaper;
import com.Teachers.HaziraKhataByGk.AttendanceActivity;
import com.Teachers.HaziraKhataByGk.ClassRoomActivity;
import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.HelperClassess.ComparableDate;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.Model.AttendenceData;
import com.Teachers.HaziraKhataByGk.Model.student;
import com.Teachers.HaziraKhataByGk.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class StudentAlIInfoShowActiviy extends AppCompatActivity {
    private TextView studentName;
    public Button studentPhoneNumber;
    private TextView parentsName;
    public Button parentPhoneNumber;
    private ListView DatewiseAttendence;
    private ArrayList<String> attendenceListForSingleStudent;
   // private ArrayList<String> attendenceListKEYForSingleStudent;
    private ArrayList<Boolean> PresentAbsent;
    public  SingleStudentPresentDateListAdaper singleStudentPresentDateListAdaper;
    public static student student;

    public static String time, subject,yearWithDate,year,month,day;

    static boolean TriggeredAlready = true;
    public ArrayList<AttendenceData> attendenceDataArrayList;
    ArrayList<String> TempKEYlist;
    ArrayList<AttendenceData> TempDatalist;


    public static FirebaseAuth auth;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public static FirebaseUser mFirebaseUser;

    String roll;
    LinearLayout linearLayoutForEmptyView;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //HIDING NOTIFICATION BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        setContentView(R.layout.student_info_show_activity);
        studentName = (TextView) findViewById(R.id.studentName);
        // parentsName=(TextView)findViewById(R.id.parentsName);
        //INITIALIZE THE TEXTVIEW
        studentPhoneNumber = (Button) findViewById(R.id.studentPhoneNumber);
        parentPhoneNumber = (Button) findViewById(R.id.parentPhoneNumber);


        //EMPTY VIEW
        linearLayoutForEmptyView = (LinearLayout) findViewById(R.id.toDoEmptyView);

        //INITIALIZE THE BUTTON
        DatewiseAttendence = (ListView) findViewById(R.id.DatewiseAttendence);
        roll = getIntent().getStringExtra("Roll");
        activity = this;
        attendenceListForSingleStudent = new ArrayList<>();
        //attendenceListKEYForSingleStudent = new ArrayList<>();
        PresentAbsent = new ArrayList<>();

        attendenceListForSingleStudent=new ArrayList<>();
        attendenceDataArrayList=new ArrayList<>();

        ClassRoomActivity.classitem=getIntent().getParcelableExtra("classItem");


        DatewiseAttendence.setOnItemClickListener(new AdapterView.OnItemClickListener(){
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


        //Get List
        GetHeadingData();
        GetAttendenceListData();
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

                            linearLayoutForEmptyView.setVisibility(View.VISIBLE);

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


    public void GetAttendenceListData(){



        ClassRoomActivity.classitem=getIntent().getParcelableExtra("classItem");

        FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(roll).child("Attendance").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("GK","GetAttendenceListData");

                attendenceListForSingleStudent.clear();
                attendenceDataArrayList.clear();
                PresentAbsent.clear();




                ArrayList<AttendenceData> attendenceDataArrayList = new ArrayList<>();

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    attendenceDataArrayList.add(dataSnapshot1.getValue(AttendenceData.class));
                }


                Collections.sort(attendenceDataArrayList, new Comparator<AttendenceData>() {
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




                for (AttendenceData att : attendenceDataArrayList) {


                    AttendenceData attendenceData=att;


                    //check if subject is black or exist

                    String subject;

                    if (attendenceData != null) {

                        if (attendenceData.getSubject().equals("")) {
                            subject = "";
                        } else
                            subject = "(" + attendenceData.getSubject() + ")";


                        if (attendenceData.getStatus())
                            attendenceListForSingleStudent.add(attendenceData.getDate() + subject + "  উপস্থিত");
                        else {
                            attendenceListForSingleStudent.add(attendenceData.getDate() + subject + "  অনুপস্থিত");
                        }
                        PresentAbsent.add(attendenceData.getStatus());
                    }
                }
                singleStudentPresentDateListAdaper = new SingleStudentPresentDateListAdaper
                        (StudentAlIInfoShowActiviy.this, activity, attendenceListForSingleStudent,
                                PresentAbsent, ClassRoomActivity.classitem,student,attendenceDataArrayList,roll);
                if(attendenceListForSingleStudent.size()==0)
                    linearLayoutForEmptyView.setVisibility(View.VISIBLE);
                DatewiseAttendence.setAdapter(singleStudentPresentDateListAdaper);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
    }
    public  void GetHeadingData(){



        FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(roll).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("GK","GetHeadingData");
                student = dataSnapshot.getValue(student.class);
                long totalAttendPersenten = 0;
                long totalClass = dataSnapshot.child("Attendance").getChildrenCount();

                ProgressBar spinner;
                spinner = (ProgressBar)findViewById(R.id.progressBarInSingleStudentActivity);
                spinner.setVisibility(View.GONE);

                //For Empty view
                if (totalClass == 0) {
                    DatewiseAttendence.setVisibility(View.GONE);
                    linearLayoutForEmptyView.setVisibility(View.VISIBLE);
                } else {
                    DatewiseAttendence.setVisibility(View.VISIBLE);
                    linearLayoutForEmptyView.setVisibility(View.GONE);
                }
                long attendClass = 0;
                AttendenceData attendenceData;

                attendenceDataArrayList.clear();



                for (DataSnapshot dataSnapshot1 : dataSnapshot.child("Attendance").getChildren()) {
                    attendenceData = dataSnapshot1.getValue(AttendenceData.class);

                    if (attendenceData != null) {

                        attendenceData.setKey(dataSnapshot1.getKey());

                        attendenceDataArrayList.add(attendenceData);
                        if (attendenceData.getStatus()) attendClass++;

                        Log.d("GK",dataSnapshot1.getKey()+" KEY "+attendenceData.getStatus());
                    }

                }

                Collections.sort(attendenceDataArrayList, new Comparator<AttendenceData>() {
                    @Override
                    public int compare(AttendenceData o1, AttendenceData o2) {
                        ComparableDate comparableDate1,comparableDate2;
                        comparableDate1 = new ComparableDate();
                        comparableDate2 = new ComparableDate();

                        UtilsCommon.debugLog("Compare");
                        try {
                            comparableDate1.setDateTime(o1.getDate());
                            comparableDate2.setDateTime(o2.getDate());
                        }catch (Exception c){
                        }

                        return comparableDate1.compareTo(comparableDate2);
                    }
                });



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
                //parentsName.setText(student.getPhone());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void CreateDialogForAddAttendence(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater1 = this.getLayoutInflater();
        final View v = inflater1.inflate(R.layout.dialoage_for_single_attendence_items, null);
        final EditText Subject = (EditText) v.findViewById(R.id.periodID);
        builder.setView(v).setPositiveButton("উপস্থিত", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
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
                            .setValue(attendenceData);

                    if(singleStudentPresentDateListAdaper!=null)
                        singleStudentPresentDateListAdaper.notifyDataSetChanged();

                    GetHeadingData();
                    GetAttendenceListData();

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
                        .setValue(attendenceData);

                if(singleStudentPresentDateListAdaper!=null)
                    singleStudentPresentDateListAdaper.notifyDataSetChanged();

                GetHeadingData();
                GetAttendenceListData();
                dialog.dismiss();
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
        final int pos2=attendenceDataArrayList.size()-1-pos1;
        final int pos=pos1;
        final View v = inflater.inflate(R.layout.dialoage_for_edit_single_attendence_items, null);
        final EditText Subject = (EditText) v.findViewById(R.id.periodID);
        final CheckBox checkBox = (CheckBox)v.findViewById(R.id.attMarker);//For attendance
        final CheckBox checkBox1 =(CheckBox) v.findViewById(R.id.absentMarker);//For absent
        final DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);


        AttendenceData attendenceData;

        TempKEYlist=new ArrayList<>();
        TempDatalist=new ArrayList<>();

        Collections.reverse(attendenceDataArrayList);
        for(AttendenceData data:attendenceDataArrayList)
        {
            TempKEYlist.add(data.getKey());
        }

        TempDatalist.addAll(attendenceDataArrayList);

        attendenceData=TempDatalist.get(pos);




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
                    public void onClick(DialogInterface dialog, int id) {
                        {

                            int day = datePicker.getDayOfMonth();
                            int month = datePicker.getMonth();
                            int year = datePicker.getYear()-1900 ;
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
                            String formatedDate = simpleDateFormat.format(new Date(year, month, day));

                            String subject = Subject.getText().toString();
                            // if (subject.equals("")) subject = "অনির্ধারিত";

                            //ADD ATTENDANCE
                            AttendenceData attendenceData = new AttendenceData();
                            attendenceData.setStatus(checkBox.isChecked());
                            attendenceData.setSubject(subject);
                            attendenceData.setDate(formatedDate);


                            //set date
                            if(student!=null){
                                //REMOVE FIRST
                                Log.d("GK","if "+roll+" "+student.getId());
                                FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(student.getId()).child("Attendance").child(TempKEYlist.get(pos)).child("date").removeValue();
                                //set True or False
                                FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(student.getId()).child("Attendance").child( TempKEYlist.get(pos)).child("status").removeValue();
                                //set subject
                                FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(student.getId()).child("Attendance").child( TempKEYlist.get(pos)).child("subject").removeValue();

                                //THEN ADD DATA
                                Log.d("GK","if "+roll+" "+student.getId());
                                FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(student.getId()).child("Attendance").child( TempKEYlist.get(pos)).child("date").setValue(formatedDate);
                                //set True or False
                                FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(student.getId()).child("Attendance").child( TempKEYlist.get(pos)).child("status").setValue(checkBox.isChecked());

                                //set subject
                                FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(student.getId()).child("Attendance").child( TempKEYlist.get(pos)).child("subject").setValue(subject);


                            }

                            else {
                                Log.d("GK","Else "+roll);


                                //REMOVE FIRST

                                FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(roll).child("Attendance").child( TempKEYlist.get(pos2)).child("date").removeValue();
                                FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(roll).child("Attendance").child( TempKEYlist.get(pos2)).child("status").removeValue();
                                FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(roll).child("Attendance").child( TempKEYlist.get(pos2)).child("subject").removeValue();


                                //THEN ADD DATA
                                FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(roll).child("Attendance").child( TempKEYlist.get(pos2)).child("date").setValue(formatedDate);
                                //set True or False
                                FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(roll).child("Attendance").child( TempKEYlist.get(pos2)).child("status").setValue(checkBox.isChecked());
                                //set subject
                                FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(roll).child("Attendance").child( TempKEYlist.get(pos2)).child("subject").setValue(subject);

                            }


                            GetHeadingData();
                            GetAttendenceListData();
                            dialog.dismiss();
                        }
                    }
                }).setNegativeButton("বাদ দিন", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                GetHeadingData();
                GetAttendenceListData();
                dialog.dismiss();
            }
        }).setNeutralButton("ডিলিট করুন", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int id) {


                if(student!=null){

                    FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(student.getId()).child("Attendance").child( TempKEYlist.get(pos2)).removeValue().addOnSuccessListener(StudentAlIInfoShowActiviy.this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            GetHeadingData();
                            GetAttendenceListData();
                            dialog.dismiss();

                        }
                    });

                }
                else {
                    Log.d("GK","Else "+roll);
                    FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").child(roll).child("Attendance").child( TempKEYlist.get(pos2)).removeValue();
                }


            }
        }).create().show();


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
}



