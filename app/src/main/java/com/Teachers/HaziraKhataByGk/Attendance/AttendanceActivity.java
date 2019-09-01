package com.Teachers.HaziraKhataByGk.Attendance;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.Teachers.HaziraKhataByGk.ClassRoomActivity;
import com.Teachers.HaziraKhataByGk.Constant.StaticData;
import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.HelperClassess.DialogUtils;
import com.Teachers.HaziraKhataByGk.Model.AttendenceData;
import com.Teachers.HaziraKhataByGk.Model.ClassItem;
import com.Teachers.HaziraKhataByGk.Model.Student;
import com.Teachers.HaziraKhataByGk.PrinterActivity;
import com.Teachers.HaziraKhataByGk.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.Teachers.HaziraKhataByGk.ClassRoomActivity.FLAG_OF_CLASSROOM_ACTIVITY;

public class AttendanceActivity extends AppCompatActivity {

    public static ClassItem classitemAttendence;
    public ListView listView;

    public  ArrayList<String> names;
    public  static ArrayList<String> rolls;
    public  ArrayList<Integer> attendencePercentage;
    public  List<Student> studentListFromAttendenceActivity;
    public static List<Student> studentListForPrintActiviyFromAttendenceActivity;
    public static List<Student> studentListForDeleteFromAttendenceActivity;
    public AttendenceListAdapter attendenceListAdapter;

    public static boolean previousClassAttendenceStatus;

    public static String time, subject,yearWithDate,year,month;
    private LinearLayout linearLayoutForEmptyView;
    public RelativeLayout mainlayout;
   // public static ArrayList<Integer> checklist ;
    public static HashMap<Integer, Boolean> checkHash ;//For avoiding auto checking
    public static HashMap<String,ArrayList<AttendenceData>> perStudentTotalAttendenceData;//for creating month wise data sheet;
    public ArrayList<AttendenceData> attendenceDataArrayListForPerStudent;


    @OnClick(R.id.rb_absent_all)
    public void absentAll(){

        checkHash.clear();
        for(int i=0;i<studentListFromAttendenceActivity.size();i++){
            checkHash.put(i,false);
        }
        attendenceListAdapter.setCheckHash(checkHash);
        attendenceListAdapter.notifyDataSetChanged();



    }

    @OnClick(R.id.rb_present_all)
    public void presentAll(){
        checkHash.clear();

        for(int i=0;i<studentListFromAttendenceActivity.size();i++){
            checkHash.put(i,true);
        }
        attendenceListAdapter.setCheckHash(checkHash);
        attendenceListAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpUI();
        loadDataFromServer();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.all_student_data_delete_and_print, menu);
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
                dialogBuilder.setTitle("ক্লাসের সকল শিক্ষার্থীর উপস্থিতির ডাটা ডিলেট করতে চায়?");
                dialogBuilder.setMessage("ডিলিট করার আগে ইংরেজীতে \"DELETE\" শব্দটি লিখুন।");
                dialogBuilder.setPositiveButton("ডিলিট করুন", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(edt.getText().toString().trim().equals("DELETE")){

                            FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").addListenerForSingleValueEvent(new ValueEventListener() {

                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    studentListForDeleteFromAttendenceActivity=new ArrayList<>();
                                    for (DataSnapshot StudentData : dataSnapshot.getChildren()){
                                        Student student;
                                        student = StudentData.getValue(Student.class);
                                        studentListForDeleteFromAttendenceActivity.add(student);
                                    }

                                    Student student;
                                    for (int i = 0; i < studentListForDeleteFromAttendenceActivity.size(); i++) {
                                        student = studentListForDeleteFromAttendenceActivity.get(i);
                                        FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName()+ ClassRoomActivity.classitem.getSection()).child("Student").child(student.getId()).child("Attendance").removeValue();
                                        Log.d("GK","REMOVE");

                                    }

                                   Intent intent = new Intent(AttendanceActivity.this,AttendanceActivity.class);
                                    intent.putExtra("DATE", time);
                                    intent.putExtra("SUBJECT", subject);
                                    intent.putExtra(FLAG_OF_CLASSROOM_ACTIVITY, classitemAttendence);
                                    finish();
                                    startActivity(intent);

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });


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


            case R.id.printer:
                Intent intent=new Intent(AttendanceActivity.this, PrinterActivity.class);
                intent.putExtra(FLAG_OF_CLASSROOM_ACTIVITY,classitemAttendence);
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void ShowIntroDialoage(){
        //TODO: For verifying this activity is newly opened or not and showing the instruction dialog

        SharedPreferences pref = AttendanceActivity.this.getSharedPreferences("IsFirst", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        if(pref.getBoolean(classitemAttendence.getName()+classitemAttendence.getSection(),true)){
            final AlertDialog alertDialog = new AlertDialog.Builder(AttendanceActivity.this).create();
            alertDialog.setMessage("আপনি যদি কোন শিক্ষার্থীর সম্পূর্ণ ডাটা দেখতে চান তাহলে এখানে উপস্থাপিত লিস্ট থেকে তার নামের উপর ক্লিক করুন , আপনি যদি ক্লাসের সকল শিক্ষার্থীর উপস্থিতির ডাটা ডিলিট করতে চান তাহলে উপরের ডানপাশের ডিলিট চিহ্ন ক্লিক করুন আর যদি মাসিক উপস্থিতির রেকর্ড দেখতে অথবা প্রিন্ট করতে চান তাহলে উপরের প্রিন্ট চিহ্ন ক্লিক করুন ");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"ওকে",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            DialogUtils.showInfoAlertDialog("", this.getString(R.string.info_attandance_activity_first_time),null);
            editor.putBoolean(classitemAttendence.getName()+classitemAttendence.getSection(),false);
            editor.apply();

        }

    }

    void setUpUI(){

        setContentView(R.layout.activity_attendance);
        ButterKnife.bind(this);

        checkHash = new HashMap<>();
        perStudentTotalAttendenceData= new HashMap<>();

        listView = (ListView) findViewById(R.id.attendanceListViwe);
        linearLayoutForEmptyView=(LinearLayout)findViewById(R.id.toDoEmptyView);
        Button saveAttendenceButton = (Button) findViewById(R.id.buttonSaveAttendance);
        mainlayout=findViewById(R.id.mainLayout);


        classitemAttendence = getIntent().getParcelableExtra(FLAG_OF_CLASSROOM_ACTIVITY);

        ShowIntroDialoage();


        studentListFromAttendenceActivity=new ArrayList<>();
        studentListForPrintActiviyFromAttendenceActivity=new ArrayList<>();

        names = new ArrayList<>();
        rolls = new ArrayList<>();
        attendencePercentage=new ArrayList<Integer>();

        time = getIntent().getStringExtra("DATE");
        //"Wed, 3 Jan 2018"

        yearWithDate = time.substring(Math.max(time.length() - 8, 0));
        month=yearWithDate.substring(0,Math.min(yearWithDate.length(), 3));
        year = time.substring(Math.max(time.length() - 4, 0));
        Log.d("GK", year + " year");
        Log.d("GK", month + " month");
        Log.d("GK", yearWithDate + " year With Date");

        subject = getIntent().getStringExtra("SUBJECT");
        if (subject == null) subject = "অনির্ধারিত";
        attendenceDataArrayListForPerStudent =new ArrayList<AttendenceData>();


        //Log.d("GK", classitemAttendence.getName() + " Class fromTime attendence");
        saveAttendenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendenceListAdapter.saveAll();
            }
        });


    }



    void loadDataFromServer(){
        FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(StaticData.currentClassName+StaticData.currentSection).child("Student").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                names.clear();
                rolls.clear();
                attendencePercentage.clear();
                studentListFromAttendenceActivity.clear();
                if(dataSnapshot.getChildrenCount()==0){
                    listView.setVisibility(View.GONE);
                    findViewById(R.id.rg_present_absent_all).setVisibility(View.GONE);
                    linearLayoutForEmptyView.setVisibility(View.VISIBLE);
                }else {
                    listView.setVisibility(View.VISIBLE);
                    findViewById(R.id.rg_present_absent_all).setVisibility(View.VISIBLE);
                    linearLayoutForEmptyView.setVisibility(View.GONE);
                }

                for (DataSnapshot StudentData : dataSnapshot.getChildren()){
                    Student student;
                    student = StudentData.getValue(Student.class);
                    studentListFromAttendenceActivity.add(student);
                }

                studentListForPrintActiviyFromAttendenceActivity=studentListFromAttendenceActivity;

                long totalClass, attendClass = 0, totalAttendPersenten;
                Student student;
                for (int i = 0; i < studentListFromAttendenceActivity.size(); i++) {
                    student = studentListFromAttendenceActivity.get(i);
                    totalAttendPersenten = 0;
                    totalClass = dataSnapshot.child(student.getId()).child("Attendance").getChildrenCount();
                    AttendenceData attendenceData = null;
                    attendenceDataArrayListForPerStudent =new ArrayList<>();
                    long temp1=0;


                    for (DataSnapshot dataSnapshot1 : dataSnapshot.child(student.getId()).child("Attendance").getChildren()) {

                        attendenceData = dataSnapshot1.getValue(AttendenceData.class);
                        attendenceDataArrayListForPerStudent.add(attendenceData);
                        if(attendenceData!=null) {
                            if (attendenceData.getStatus()) attendClass++;
                            temp1++;
                            if (temp1 == totalClass) {
                                previousClassAttendenceStatus = attendenceData.getStatus();
                            }
                        }
                    }



                    perStudentTotalAttendenceData.put(student.getId(),attendenceDataArrayListForPerStudent);



                    if (totalClass != 0)  //THIS IS FOR AVOID ARITHMETIC EXCEPTION
                        totalAttendPersenten = (attendClass * 100) / totalClass;

                    attendencePercentage.add((int)totalAttendPersenten);//TO GET PERCENTAGE FOR COLOR


                    if(!dataSnapshot.child(student.getId()).child("Attendance").exists())
                        previousClassAttendenceStatus=true;

                    if(previousClassAttendenceStatus){
                        names.add(" " + student.getStudentName() + ".( রোল :" + student.getId() + ")" + "\n" + " মোট ক্লাস :" + totalClass + " উপস্থিতি :"
                                + attendClass + " শতকরা :" + totalAttendPersenten + "%");
                    }
                    else
                    {
                        names.add(" " + student.getStudentName() + ".( রোল :" + student.getId() + ")" + "\n" + " মোট ক্লাস :" + totalClass + " উপস্থিতি :"
                                + attendClass + " শতকরা :" + totalAttendPersenten + "%\n(গতক্লাসে অনুপস্থিত)");
                    }
                    previousClassAttendenceStatus=true;
                    attendClass = 0;
                    rolls.add(student.getId());
                }
                attendenceListAdapter = new AttendenceListAdapter(AttendanceActivity.this, names,classitemAttendence,studentListFromAttendenceActivity);
                listView.setAdapter(attendenceListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }



    }
