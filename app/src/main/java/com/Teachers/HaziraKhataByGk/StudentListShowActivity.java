package com.Teachers.HaziraKhataByGk;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Teachers.HaziraKhataByGk.Adapter.StudentListAdapter;
import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.HelperClassess.ViewUtils.BaseActivity;
import com.Teachers.HaziraKhataByGk.Listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.Model.ClassItem;
import com.Teachers.HaziraKhataByGk.Model.Student;
import com.Teachers.HaziraKhataByGk.StudentAdd.StudentAddActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class StudentListShowActivity extends BaseActivity implements RecyclerItemClickListener {

    public ClassItem classItem;
    private RecyclerView rvStudent;
    private FloatingActionButton btnAdd;
    private Context context;
    private StudentListAdapter studentListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout linearLayoutForEmptyView;
    private ArrayList<Student> studentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.student_activity);
        context = getApplicationContext();
        rvStudent = findViewById(R.id.studentFromStudentActivity);
        btnAdd = findViewById(R.id.addFromStudentActivity);
        linearLayoutManager = new LinearLayoutManager(this);
        studentListAdapter = new StudentListAdapter(this);
        studentListAdapter.setOnItemClickListener(this);
        context = this;
        rvStudent.setLayoutManager(linearLayoutManager);
        rvStudent.setAdapter(studentListAdapter);
        classItem = UtilsCommon.getCurrentClass(this);

        //For empty view
        linearLayoutForEmptyView = findViewById(R.id.toDoEmptyView);
        btnAdd.setEnabled(false);
        btnAdd.setOnClickListener(v -> StudentAddActivity.start(context, classItem, studentList));


    }


    @Override
    protected void onResume() {
        super.onResume();


        //FOR GETTING SPECIFIC CLASS'S STUDENTS

        if (classItem.getName() != null && classItem.getSection() != null) {

            FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).
                    child("Class").child(classItem.getName() + classItem.getSection()).child("Student")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            ProgressBar spinner;
                            spinner = findViewById(R.id.progressBarInStudentActivity);
                            spinner.setVisibility(View.GONE);

                            btnAdd.setEnabled(true);

                            final ArrayList<Student> studentListFromServer = new ArrayList<Student>();
                            for (DataSnapshot StudentData : dataSnapshot.getChildren()) {
                                Student student;
                                student = StudentData.getValue(Student.class);
                                studentListFromServer.add(student);
                            }

                            studentList = studentListFromServer;

                            if (studentList.size() == 0) {
                                linearLayoutForEmptyView.setVisibility(View.VISIBLE);
                            } else {
                                linearLayoutForEmptyView.setVisibility(View.GONE);
                            }

                            studentListAdapter.clear();
                            studentListAdapter.addAll(studentList);
                            rvStudent.setAdapter(studentListAdapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }


    }

    @Override
    public void onItemClick(int position, View view) {
        UtilsCommon.setCurrentStudent(studentListAdapter.getItem(position), this);
        //This is for if class name is edited then class and section name in student field should be
        //modified as like that.
        Student student = studentListAdapter.getItem(position);
        student.setStudentClass(classItem.getName());
        student.setStudentSection(classItem.getSection());

        StudentAddActivity.start(this,student, studentList);
    }

    @Override
    public void onItemLongPressed(int position, View view) {

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
