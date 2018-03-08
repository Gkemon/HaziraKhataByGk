package com.Teachers.HaziraKhataByGk;

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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.Teachers.HaziraKhataByGk.adapter.SingleStudentPresentDateListAdaper;
import com.Teachers.HaziraKhataByGk.model.AttendenceData;
import com.Teachers.HaziraKhataByGk.model.student;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
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
import java.util.Date;

public class studentAllInfoShowActiviy extends AppCompatActivity {
    private TextView studentName;
    public Button studentPhoneNumber;
    private TextView parentsName;
    public Button parentPhoneNumber;
    private ListView DatewiseAttendence;
    private ArrayList<String> attendenceListForSingleStudent;
    private ArrayList<String> attendenceListKEYForSingleStudent;
    private ArrayList<Boolean> PresentAbsent;
    public static SingleStudentPresentDateListAdaper singleStudentPresentDateListAdaper;
    public static student student;
    public LinearLayout adlayout;
    public static String time, subject,yearWithDate,year,month,day;
    public AdView mAdView;
    public ArrayList<AttendenceData> attendenceDataArrayList;
    ArrayList<String> TempKEYlist;
    ArrayList<AttendenceData> TempDatalist;


    public static FirebaseAuth auth;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public static String mUserId;
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
        attendenceListKEYForSingleStudent = new ArrayList<>();
        PresentAbsent = new ArrayList<>();

        ClassRoom_activity.classitem=getIntent().getParcelableExtra("classItem");

        //Get Main Head's data
        GetHeadingData();

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

        if (mAdView != null) {
            mAdView.resume();
        }
        //Get List
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

                            MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName() + ClassRoom_activity.classitem.getSection()).child("Student").child(roll).child("Attendance").removeValue();

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
//                FragmentManager fm = activity.getFragmentManager();
//                createRequest request = new createRequest();
//                request.addData(student,ClassRoom_activity.classitem,activity);
//                request.show(fm, "Select");
//                GetAttendenceListData();
                CreateDialogForAddAttendence();




            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void GetAttendenceListData(){
        //TODO:DATABASE CONNECTION
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        //TODO: USER (for FB logic auth throw null pointer exception)
        auth = FirebaseAuth.getInstance();
        mFirebaseUser = auth.getCurrentUser();
        databaseReference.keepSynced(true);
        mUserId = mFirebaseUser.getUid();
        MainActivity.databaseReference = databaseReference;
        MainActivity.mUserId = mUserId;

        ClassRoom_activity.classitem=getIntent().getParcelableExtra("classItem");

        MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName() + ClassRoom_activity.classitem.getSection()).child("Student").child(roll).child("Attendance").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                attendenceListForSingleStudent.clear();
                attendenceDataArrayList=new ArrayList<>();
                PresentAbsent.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    AttendenceData attendenceData;

                    attendenceData = dataSnapshot1.getValue(AttendenceData.class);
                    //check if subject is black or exist

                    String subject;

                    if (attendenceData != null) {

                        attendenceDataArrayList.add(attendenceData);
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
                        (studentAllInfoShowActiviy.this, activity, attendenceListForSingleStudent,
                                PresentAbsent,ClassRoom_activity.classitem,student,attendenceDataArrayList,
                                attendenceListKEYForSingleStudent,roll);
                if(attendenceListKEYForSingleStudent.size()==0)
                    linearLayoutForEmptyView.setVisibility(View.VISIBLE);
                DatewiseAttendence.setAdapter(singleStudentPresentDateListAdaper);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public  void GetHeadingData(){
        //TODO:DATABASE CONNECTION
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        //TODO: USER (for FB logic auth throw null pointer exception)
        auth = FirebaseAuth.getInstance();
        mFirebaseUser = auth.getCurrentUser();
        databaseReference.keepSynced(true);
        mUserId = mFirebaseUser.getUid();
        MainActivity.databaseReference = databaseReference;
        MainActivity.mUserId = mUserId;


        MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName() + ClassRoom_activity.classitem.getSection()).child("Student").child(roll).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                student = dataSnapshot.getValue(student.class);
                long totalAttendPersenten = 0;
                long totalClass = dataSnapshot.child("Attendance").getChildrenCount();

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
                attendenceDataArrayList=new ArrayList<>();
                attendenceListKEYForSingleStudent.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.child("Attendance").getChildren()) {
                    attendenceData = dataSnapshot1.getValue(AttendenceData.class);

                    if (attendenceData != null) {
                        attendenceDataArrayList.add(attendenceData);
                        if (attendenceData.getStatus()) attendClass++;
                        attendenceListKEYForSingleStudent.add(dataSnapshot1.getKey());
                        Log.d("GK",dataSnapshot1.getKey()+" KEY");
                    }

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
                            " রোল :" + roll + "    ক্লাস :" + ClassRoom_activity.classitem.getName() + "\n মোট ক্লাস:" + totalClass + "  উপস্থিতি :" + attendClass + "   শতকরা :" + totalAttendPersenten + "% ";
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
                    String date = year + "-" + month + "-" + day;
                    String subject = Subject.getText().toString();
                    // if (subject.equals("")) subject = "অনির্ধারিত";

                    //ADD ATTENDANCE
                    AttendenceData attendenceData = new AttendenceData();
                    attendenceData.setStatus(true);
                    attendenceData.setSubject(subject);
                    attendenceData.setDate(formatedDate);

                    //Add to database
                    MainActivity.databaseReference.child("Users").
                            child(mUserId).child("Class").
                            child(ClassRoom_activity.classitem.getName()+ClassRoom_activity.classitem.getSection())
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
                String date = year + "-" + month + "-" + day;
                String subject = Subject.getText().toString();
                // if (subject.equals("")) subject = "অনির্ধারিত";


                //ADD ATTENDANCE
                AttendenceData attendenceData = new AttendenceData();
                attendenceData.setStatus(false);
                attendenceData.setSubject(subject);
                attendenceData.setDate(formatedDate);


                //  Add to database
                MainActivity.databaseReference.child("Users").
                        child(mUserId).child("Class").
                        child(ClassRoom_activity.classitem.getName()+ClassRoom_activity.classitem.getSection())
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
    @Override
    protected void onStart() {
        super.onStart();
        //ADMOB AD
        AdmobAd();
    }


    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

//    public static class createRequest extends DialogFragment {
//        student student;
//        class_item class_item;
//        Activity activity;
//
//        public void addData(student student,class_item class_item,Activity activity){
//            this.class_item=class_item;
//            this.student=student;
//            this.activity=activity;
//        }
//
//
//        @Override
//        public void onCreate(Bundle savedInstanceState) {
//            //TODO:DATABASE CONNECTION
//            firebaseDatabase = FirebaseDatabase.getInstance();
//            databaseReference = firebaseDatabase.getReference();
//            //TODO: USER (for FB logic auth throw null pointer exception)
//            auth = FirebaseAuth.getInstance();
//            mFirebaseUser = auth.getCurrentUser();
//            databaseReference.keepSynced(true);
//            mUserId = mFirebaseUser.getUid();
//            MainActivity.databaseReference = databaseReference;
//            MainActivity.mUserId = mUserId;
//
//            super.onCreate(savedInstanceState);
//        }
//
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
//            LayoutInflater inflater = getActivity().getLayoutInflater();
//            final View v = inflater.inflate(R.layout.dialoage_for_single_attendence_items, null);
//            final EditText Subject = (EditText) v.findViewById(R.id.periodID);
//            builder.setView(v).setPositiveButton("উপস্থিত", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int id) {
//                    {
//                        final DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);
//                        int day = datePicker.getDayOfMonth();
//                        int month = datePicker.getMonth();
//                        int year = datePicker.getYear()-1900 ;
//
//                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
//                        String formatedDate = simpleDateFormat.format(new Date(year, month, day));
//                        String date = year + "-" + month + "-" + day;
//                        String subject = Subject.getText().toString();
//                       // if (subject.equals("")) subject = "অনির্ধারিত";
//
//                        //ADD ATTENDANCE
//                        AttendenceData attendenceData = new AttendenceData();
//                        attendenceData.setStatus(true);
//                        attendenceData.setSubject(subject);
//                        attendenceData.setDate(formatedDate);
//
//                        //Add to database
//                        MainActivity.databaseReference.child("Users").
//                                child(mUserId).child("Class").
//                                child(class_item.getName()+class_item.getSection())
//                                .child("Student").child(student.getId()).child("Attendance").push()
//                                .setValue(attendenceData);
//
//                        if(singleStudentPresentDateListAdaper!=null)
//                        singleStudentPresentDateListAdaper.notifyDataSetChanged();
//                        dialog.dismiss();
//                    }
//                }
//            }).setNegativeButton("অনুপস্থিত", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int id) {
//                    final DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);
//                    int day = datePicker.getDayOfMonth();
//                    int month = datePicker.getMonth();
//                    int year = datePicker.getYear()-1900 ;
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
//                    String formatedDate = simpleDateFormat.format(new Date(year, month, day));
//                    String date = year + "-" + month + "-" + day;
//                    String subject = Subject.getText().toString();
//                   // if (subject.equals("")) subject = "অনির্ধারিত";
//
//
//                    //ADD ATTENDANCE
//                    AttendenceData attendenceData = new AttendenceData();
//                    attendenceData.setStatus(false);
//                    attendenceData.setSubject(subject);
//                    attendenceData.setDate(formatedDate);
//
//
//                    //Add to database
//                    MainActivity.databaseReference.child("Users").
//                            child(mUserId).child("Class").
//                            child(class_item.getName()+class_item.getSection())
//                            .child("Student").child(student.getId()).child("Attendance").push()
//                            .setValue(attendenceData);
//                    if(singleStudentPresentDateListAdaper!=null)
//                    singleStudentPresentDateListAdaper.notifyDataSetChanged();
//                    dialog.dismiss();
//                }
//            });
//            return builder.create();
//        }
//    }

    public void AdmobAd(){
        //ADMOB
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                .build();
        adlayout = findViewById(R.id.ads);
        mAdView = (AdView) findViewById(R.id.adViewInHome);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed() {
                // Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                adlayout.setVisibility(View.GONE);
                // Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                // Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });
        mAdView.loadAd(adRequest);
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
    public void CreateDialogForEdit(final int pos){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialoage_for_edit_single_attendence_items, null);
        final EditText Subject = (EditText) v.findViewById(R.id.periodID);
        final CheckBox checkBox = (CheckBox)v.findViewById(R.id.attMarker);//For attendance
        final CheckBox checkBox1 =(CheckBox) v.findViewById(R.id.absentMarker);//For absent
        final DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);
        AttendenceData attendenceData;


        TempKEYlist=new ArrayList<>();
        TempDatalist=new ArrayList<>();

        TempKEYlist=attendenceListKEYForSingleStudent;
        TempDatalist=attendenceDataArrayList;

        //TO reverse arraylist
        Collections.reverse(TempKEYlist);
        Collections.reverse(TempDatalist);

        Log.d("GK",String.valueOf(TempKEYlist.size())+" SIZE KEY");
        Log.d("GK",String.valueOf(TempDatalist.size())+" SIZE DATA");

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

        //TODO:LOGCAT
//            Log.d("GK", year + " year");
//            Log.d("GK", month + " month");
//            Log.d("GK", yearWithDate + " year With Date");
//            Log.d("GK", day + "Day");

        //TODO: GENERATE DATE FROM EEE,d MMM yyy pattern
//            try {
//
//                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy");
//                sdf.setLenient(false);
//                date=sdf.parse(attendenceData.getDate());
//                Log.d("GK","Main Date"+attendenceData.getDate()+" "+String.valueOf(date.getYear()+1900)+ " - "+String.valueOf(date.getMonth())+ " - "+String.valueOf(date.getDay())+ " ");
//
//            }
//            catch (java.text.ParseException e){
//                e.printStackTrace();
//                Log.d("GK","Catch"+attendenceData.getDate());
//            }

        datePicker.updateDate(Integer.valueOf(year.trim()),Month,Integer.valueOf(day.trim()));


        Subject.setText(attendenceData.getSubject());
        if(attendenceData.getStatus())
        {
            checkBox.setChecked(true);
            checkBox1.setChecked(false);
        }
        else {
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
                            ConnectWithServer();

                            //set date
                            if(student!=null){

//                            MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName() + ClassRoom_activity.classitem.getSection()).child("Student").child(student.getId()).child("Attendance").child( attendenceListKEYForSingleStudent.get(pos)).removeValue();
//
//                            MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName() + ClassRoom_activity.classitem.getSection()).child("Student").child(student.getId()).child("Attendance").child( attendenceListKEYForSingleStudent.get(pos)).setValue(attendenceData);
//

                                //REMOVE FIRST

                                Log.d("GK","if "+roll+" "+student.getId());
                                MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName() + ClassRoom_activity.classitem.getSection()).child("Student").child(student.getId()).child("Attendance").child( TempKEYlist.get(pos)).child("date").removeValue();
                                //set True or False
                                MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName() + ClassRoom_activity.classitem.getSection()).child("Student").child(student.getId()).child("Attendance").child( TempKEYlist.get(pos)).child("status").removeValue();
                                //set subject
                                MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName() + ClassRoom_activity.classitem.getSection()).child("Student").child(student.getId()).child("Attendance").child( TempKEYlist.get(pos)).child("subject").removeValue();

                                //THEN ADD DATA
                                Log.d("GK","if "+roll+" "+student.getId());
                                MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName() + ClassRoom_activity.classitem.getSection()).child("Student").child(student.getId()).child("Attendance").child( TempKEYlist.get(pos)).child("date").setValue(formatedDate);
                                //set True or False
                                MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName() + ClassRoom_activity.classitem.getSection()).child("Student").child(student.getId()).child("Attendance").child( TempKEYlist.get(pos)).child("status").setValue(checkBox.isChecked());
                                //set subject
                                MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName() + ClassRoom_activity.classitem.getSection()).child("Student").child(student.getId()).child("Attendance").child( TempKEYlist.get(pos)).child("subject").setValue(subject);
                            }

                            else {
                                Log.d("GK","Else "+roll);


                                //REMOVE FIRST

                                MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName() + ClassRoom_activity.classitem.getSection()).child("Student").child(roll).child("Attendance").child( TempKEYlist.get(pos)).child("date").removeValue();
                                MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName() + ClassRoom_activity.classitem.getSection()).child("Student").child(roll).child("Attendance").child( TempKEYlist.get(pos)).child("status").removeValue();
                                MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName() + ClassRoom_activity.classitem.getSection()).child("Student").child(roll).child("Attendance").child( TempKEYlist.get(pos)).child("subject").removeValue();


                                //THEN ADD DATA
                                MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName() + ClassRoom_activity.classitem.getSection()).child("Student").child(roll).child("Attendance").child( TempKEYlist.get(pos)).child("date").setValue(formatedDate);
                                //set True or False
                                MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName() + ClassRoom_activity.classitem.getSection()).child("Student").child(roll).child("Attendance").child( TempKEYlist.get(pos)).child("status").setValue(checkBox.isChecked());
                                //set subject
                                MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName() + ClassRoom_activity.classitem.getSection()).child("Student").child(roll).child("Attendance").child( TempKEYlist.get(pos)).child("subject").setValue(subject);

                            }


                            //Add to database
//                        MainActivity.databaseReference.child("Users").
//                                child(mUserId).child("Class").
//                                child(class_item.getName()+class_item.getSection())
//                                .child("Student").child(student.getId()).child("Attendance").push()
//                                .setValue(attendenceData);
//
//                        onResume();
                            //                 singleStudentPresentDateListAdaper.notifyDataSetChanged();

                            GetHeadingData();
                            GetAttendenceListData();
                            onResume();

                            dialog.dismiss();
                        }
                    }
                }).setNegativeButton("বাদ দিন", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        }).setNeutralButton("ডিলিট করুন", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ConnectWithServer();
                if(student!=null){

                    MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName() + ClassRoom_activity.classitem.getSection()).child("Student").child(student.getId()).child("Attendance").child( TempKEYlist.get(pos)).removeValue();

                }
                else {
                    Log.d("GK","Else "+roll);
                    MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName() + ClassRoom_activity.classitem.getSection()).child("Student").child(roll).child("Attendance").child( TempKEYlist.get(pos)).removeValue();
                }

                GetHeadingData();
                GetAttendenceListData();
                onResume();

                dialog.dismiss();

            }
        }).create().show();


    }
    public void ConnectWithServer(){
        //TODO:DATABASE CONNECTION
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        //TODO: USER (for FB logic auth throw null pointer exception)
        auth = FirebaseAuth.getInstance();
        mFirebaseUser = auth.getCurrentUser();
        databaseReference.keepSynced(true);
        mUserId = mFirebaseUser.getUid();
        MainActivity.databaseReference = databaseReference;
        MainActivity.mUserId = mUserId;
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



