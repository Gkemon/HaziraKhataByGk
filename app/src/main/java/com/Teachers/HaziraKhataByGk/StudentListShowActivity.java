package com.Teachers.HaziraKhataByGk;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.Teachers.HaziraKhataByGk.Adapter.StudentListAdapter;
import com.Teachers.HaziraKhataByGk.Constant.StaticData;
import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.Listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.Model.ClassItem;
import com.Teachers.HaziraKhataByGk.Model.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class StudentListShowActivity extends AppCompatActivity implements RecyclerItemClickListener {

    private RecyclerView studentItems;
    private FloatingActionButton btnAdd;
    private Context context;
    public static ClassItem contactofSA;
    private StudentListAdapter studentListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout linearLayoutForEmptyView;
    public static List<Student> studentList;
    public static Activity Activity;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.student_activity);
        context = getApplicationContext();
        studentItems = (RecyclerView) findViewById(R.id.studentFromStudentActivity);

        btnAdd = (FloatingActionButton) findViewById(R.id.addFromStudentActivity);
        linearLayoutManager = new LinearLayoutManager(this);
        studentListAdapter = new StudentListAdapter(this);
        studentListAdapter.setOnItemClickListener(this);
        Activity = this;
        context = this;
        studentItems.setLayoutManager(linearLayoutManager);
        studentItems.setAdapter(studentListAdapter);
        studentList = new ArrayList<>();
        contactofSA = getIntent().getParcelableExtra(ClassRoomActivity.class.getSimpleName());

        //For empty view
        linearLayoutForEmptyView =findViewById(R.id.toDoEmptyView);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentAddActivity.start(context);
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();


        //FOR GETTING SPECIFIC CLASS'S STUDENTS

        contactofSA = getIntent().getParcelableExtra(ClassRoomActivity.class.getSimpleName());

        if(contactofSA.getName()!=null&&contactofSA.getSection()!=null)
        {

            FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(contactofSA.getName() + contactofSA.getSection()).child("Student").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    ProgressBar spinner;
                    spinner = (ProgressBar)findViewById(R.id.progressBarInStudentActivity);
                    spinner.setVisibility(View.GONE);


                    final ArrayList<Student> studentListFromServer = new ArrayList<Student>();
                    for (DataSnapshot StudentData : dataSnapshot.getChildren()) {
                        Student student;
                        student = StudentData.getValue(Student.class);
                        studentListFromServer.add(student);
                    }
                    StudentListShowActivity.studentList = new ArrayList<>();
                    StudentListShowActivity.studentList = studentListFromServer;

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

            StaticData.currentStudent=studentListAdapter.getItem(position);
            StudentAddActivity.start(this, studentListAdapter.getItem(position));

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
