package com.Teachers.HaziraKhataByGk;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.Teachers.HaziraKhataByGk.adapter.studentListAdapter;
import com.Teachers.HaziraKhataByGk.listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.model.class_item;
import com.Teachers.HaziraKhataByGk.model.student;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.Teachers.HaziraKhataByGk.MainActivity.mUserId;

public class studentActivity extends AppCompatActivity implements RecyclerItemClickListener {

    private RecyclerView studentItems;
    private FloatingActionButton btnAdd;
    private Context context;
    public static class_item contactofSA;
    private studentListAdapter studentListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout linearLayoutForEmptyView;
    public static List<student> studentList;
    public static Activity Activity;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //HIDING NOTIFICATION BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.student_activity);
        context = getApplicationContext();
        studentItems = (RecyclerView) findViewById(R.id.studentFromStudentActivity);

        btnAdd = (FloatingActionButton) findViewById(R.id.addFromStudentActivity);
        linearLayoutManager = new LinearLayoutManager(this);
        studentListAdapter = new studentListAdapter(this);
        studentListAdapter.setOnItemClickListener(this);
        Activity = this;
        context = this;
        studentItems.setLayoutManager(linearLayoutManager);
        studentItems.setAdapter(studentListAdapter);
        studentList = new ArrayList<>();
        contactofSA = getIntent().getParcelableExtra(ClassRoom_activity.class.getSimpleName());

        //For empty view
        linearLayoutForEmptyView = (LinearLayout) findViewById(R.id.toDoEmptyView);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentActActivity.start(context);
            }
        });





    }


    @Override
    protected void onResume() {
        super.onResume();


        //FOR GETTING SPECIFIC CLASS'S STUDENTS

        contactofSA = getIntent().getParcelableExtra(ClassRoom_activity.class.getSimpleName());

        if(contactofSA.getName()!=null&&contactofSA.getSection()!=null)
        {
            MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(contactofSA.getName() + contactofSA.getSection()).child("Student").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    ProgressBar spinner;
                    spinner = (ProgressBar)findViewById(R.id.progressBarInStudentActivity);
                    spinner.setVisibility(View.GONE);


                    final ArrayList<student> studentListFromServer = new ArrayList<student>();
                    for (DataSnapshot StudentData : dataSnapshot.getChildren()) {
                        student student;
                        student = StudentData.getValue(student.class);
                        studentListFromServer.add(student);
                    }
                    studentActivity.studentList = new ArrayList<>();
                    studentActivity.studentList = studentListFromServer;

                    if (studentList.size() == 0) {
                        linearLayoutForEmptyView.setVisibility(View.VISIBLE);
                    } else {
                        linearLayoutForEmptyView.setVisibility(View.GONE);
                    }

                    studentListAdapter.clear();
                    studentListAdapter.addAll(studentList);
                    studentItems.setAdapter(studentListAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }




    }
        @Override
        public void onItemClick ( int position, View view){
            StudentActActivity.start(this, studentListAdapter.getItem(position));
        }
        @Override
        public void onItemLongPressed ( int position, View view){

        }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    }
