package com.Teachers.HaziraKhataByGk;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.Teachers.HaziraKhataByGk.adapter.SingleStudentPresentDateListAdaper;
import com.Teachers.HaziraKhataByGk.model.AttendenceData;
import com.Teachers.HaziraKhataByGk.model.class_item;
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
import java.util.Date;

public class studentAllInfoShowActiviy extends AppCompatActivity {
    private TextView studentName;
    public Button studentPhoneNumber;
    private TextView parentsName;
    public Button parentPhoneNumber;
    private ListView DatewiseAttendence;
    private ArrayList<String> attendenceListForSingleStudent;
    private ArrayList<Boolean> PresentAbsent;
    public static SingleStudentPresentDateListAdaper singleStudentPresentDateListAdaper;
    public static student student;
    public LinearLayout adlayout;
    public AdView mAdView;
    public ArrayList<AttendenceData> attendenceDataArrayList;


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
        PresentAbsent = new ArrayList<>();

        ClassRoom_activity.classitem=getIntent().getParcelableExtra("classItem");

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
                for (DataSnapshot dataSnapshot1 : dataSnapshot.child("Attendance").getChildren()) {
                    attendenceData = dataSnapshot1.getValue(AttendenceData.class);

                    if (attendenceData != null) {
                        attendenceDataArrayList.add(attendenceData);
                        if (attendenceData.getStatus()) attendClass++;
                    }

                }

                if (totalClass != 0)   //THIS IS FOR AVOID ARITHMETIC EXCEPTION
                    totalAttendPersenten = (attendClass * 100) / totalClass;

                String charSequence;
                if (attendanceActivity.classitemAttendence != null) {
                    String charSequence1 = " শিক্ষার্থীর নাম: " + student.getStudentName() + " \n" +
                            " রোল :" + roll + "    ক্লাস :" + attendanceActivity.classitemAttendence.getName() + "\n মোট ক্লাস:" + totalClass + "  উপস্থিতি :" + attendClass + "   শতকরা :" + totalAttendPersenten + "% ";
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
        studentPhoneNumber.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //ACTION_DIALER IS THE BEST SOLUTION TO MAKE CALL
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                //The 'tel:' prefix is required  otherwhise the following exception will be thrown: java.lang.IllegalStateException: Could not execute method of the activity.
                callIntent.setData(Uri.parse("tel:" + student.getPhone()));
                startActivity(callIntent);
            }

        });
        parentPhoneNumber.setOnClickListener(new OnClickListener() {
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


    @Override
    protected void onResume() {
        super.onResume();

        if (mAdView != null) {
            mAdView.resume();
        }


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


        MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName() + ClassRoom_activity.classitem.getSection()).child("Student").child(roll).child("Attendance").addValueEventListener(new ValueEventListener() {
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
                                PresentAbsent,ClassRoom_activity.classitem,student,attendenceDataArrayList);
                DatewiseAttendence.setAdapter(singleStudentPresentDateListAdaper);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

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
                FragmentManager fm = activity.getFragmentManager();
                createRequest request = new createRequest();
                request.addData(student,ClassRoom_activity.classitem);
                request.show(fm, "Select");

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
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


        super.onStart();
    }


    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    public static class createRequest extends DialogFragment {
        student student;
        class_item class_item;

        public void addData(student student,class_item class_item){
            this.class_item=class_item;
            this.student=student;
        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View v = inflater.inflate(R.layout.dialoage_for_single_attendence_items, null);
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
                        if (subject.equals("")) subject = "অনির্ধারিত";

                        //ADD ATTENDANCE
                        AttendenceData attendenceData = new AttendenceData();
                        attendenceData.setStatus(true);
                        attendenceData.setSubject(subject);
                        attendenceData.setDate(formatedDate);

                        //Add to database
                        MainActivity.databaseReference.child("Users").
                                child(mUserId).child("Class").
                                child(class_item.getName()+class_item.getSection())
                                .child("Student").child(student.getId()).child("Attendance").push()
                                .setValue(attendenceData);


                        singleStudentPresentDateListAdaper.notifyDataSetChanged();

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
                    MainActivity.databaseReference.child("Users").
                            child(mUserId).child("Class").
                            child(class_item.getName()+class_item.getSection())
                            .child("Student").child(student.getId()).child("Attendance").push()
                            .setValue(attendenceData);

                    singleStudentPresentDateListAdaper.notifyDataSetChanged();

                    dialog.dismiss();
                }
            });
            return builder.create();
        }
    }
}



