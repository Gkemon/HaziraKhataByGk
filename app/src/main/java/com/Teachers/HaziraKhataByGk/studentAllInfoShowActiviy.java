package com.Teachers.HaziraKhataByGk;

import android.app.Activity;
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
import android.widget.Button;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.Teachers.HaziraKhataByGk.MainActivity.mUserId;

public class studentAllInfoShowActiviy extends AppCompatActivity {
    private TextView studentName;
    private Button studentPhoneNumber;
    private TextView parentsName;
    private Button parentPhoneNumber;
    private ListView DatewiseAttendence;
    private ArrayList<String> attendenceListForSingleStudent;
    private ArrayList<Boolean> PresentAbsent;
    private SingleStudentPresentDateListAdaper singleStudentPresentDateListAdaper;
    student student;
    public LinearLayout adlayout;
    public AdView mAdView;

    String roll;
    LinearLayout linearLayoutForEmptyView;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_info_show_activity);
         studentName=(TextView) findViewById(R.id.studentName);
        // parentsName=(TextView)findViewById(R.id.parentsName);
        //INITIALIZE THE TEXTVIEW
         studentPhoneNumber=(Button) findViewById(R.id.studentPhoneNumber);
         parentPhoneNumber=(Button) findViewById(R.id.parentPhoneNumber);

         //EMPTY VIEW
        linearLayoutForEmptyView=(LinearLayout)findViewById(R.id.toDoEmptyView);

        //INITIALIZE THE BUTTON
        DatewiseAttendence=(ListView) findViewById(R.id.DatewiseAttendence);
        roll=getIntent().getStringExtra("Roll");
        activity=this;
        attendenceListForSingleStudent=new ArrayList<>();
        PresentAbsent=new ArrayList<>();





        MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName()+ClassRoom_activity.classitem.getSection()).child("Student").child(roll).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                student = dataSnapshot.getValue(student.class);
                long totalAttendPersenten = 0;
                long totalClass = dataSnapshot.child("Attendance").getChildrenCount();

                //For Empty view
                if(totalClass==0){
                    DatewiseAttendence.setVisibility(View.GONE);
                    linearLayoutForEmptyView.setVisibility(View.VISIBLE);
                }
                else{
                    DatewiseAttendence.setVisibility(View.VISIBLE);
                    linearLayoutForEmptyView.setVisibility(View.GONE);
                }
                long attendClass=0;
                AttendenceData attendenceData;
                for(DataSnapshot dataSnapshot1:dataSnapshot.child("Attendance").getChildren()){
                    attendenceData = dataSnapshot1.getValue(AttendenceData.class);
                    if(attendenceData.getStatus())attendClass++;
                }

                if (totalClass != 0)   //THIS IS FOR AVOID ARITHMETIC EXCEPTION
                    totalAttendPersenten = (attendClass * 100) / totalClass;

                String charSequence;
                if(attendanceActivity.classitemAttendence!=null){
                    String charSequence1=" শিক্ষার্থীর নাম: " + student.getStudentName() + " \n" +
                            " রোল :" + roll + "    ক্লাস :" + attendanceActivity.classitemAttendence.getName() + "\n মোট ক্লাস:" + totalClass + "  উপস্থিতি :" + attendClass + "   শতকরা :" + totalAttendPersenten + "% ";
                        charSequence=charSequence1;
                }
                else {
                    String charSequence2=" শিক্ষার্থীর নাম: " + student.getStudentName() + " \n" +
                            " রোল :" + roll + "    ক্লাস :" + ClassRoom_activity.classitem.getName() + "\n মোট ক্লাস:" + totalClass + "  উপস্থিতি :" + attendClass + "   শতকরা :" + totalAttendPersenten + "% ";
                    charSequence=charSequence2;
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
                    callIntent.setData(Uri.parse("tel:"+student.getPhone()));
                        startActivity(callIntent);
                    }

            });
            parentPhoneNumber.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    //ACTION_DIALER IS THE BEST SOLUTION TO MAKE CALL
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    //The 'tel:' prefix is required  otherwhise the following exception will be thrown: java.lang.IllegalStateException: Could not execute method of the activity.
                    callIntent.setData(Uri.parse("tel:"+student.getParentContact()));
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


        MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName()+ClassRoom_activity.classitem.getSection()).child("Student").child(roll).child("Attendance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                attendenceListForSingleStudent.clear();
                PresentAbsent.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    AttendenceData attendenceData;
                    attendenceData=dataSnapshot1.getValue(AttendenceData.class);
                    //check if subject is black or exist


                    String subject;
                    if(attendenceData.getSubject().equals("")){
                        subject="";
                    }else
                        subject="("+attendenceData.getSubject()+")";


                    if(attendenceData.getStatus())attendenceListForSingleStudent.add(attendenceData.getDate()+subject+"  উপস্থিত");
                    else {
                        attendenceListForSingleStudent.add(attendenceData.getDate() + subject + "  অনুপস্থিত");
                    }
                    PresentAbsent.add(attendenceData.getStatus());
                }
                singleStudentPresentDateListAdaper =new  SingleStudentPresentDateListAdaper(activity,attendenceListForSingleStudent,PresentAbsent);

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
                        if(edt.getText().toString().trim().equals("DELETE")){

                            MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName()+ClassRoom_activity.classitem.getSection()).child("Student").child(roll).child("Attendance").removeValue();

                            finish();
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
        adlayout=findViewById(R.id.ads);
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


}



