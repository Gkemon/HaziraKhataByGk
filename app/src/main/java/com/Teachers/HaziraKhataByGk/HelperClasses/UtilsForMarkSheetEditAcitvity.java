package com.Teachers.HaziraKhataByGk.HelperClasses;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Teachers.HaziraKhataByGk.Adapter.MarkSheetEditAdapter;
import com.Teachers.HaziraKhataByGk.Constant.Constant;
import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.Model.Student;
import com.Teachers.HaziraKhataByGk.Model.SubjectMarkSheet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.Teachers.HaziraKhataByGk.Constant.Constant.globalStudentList;

/**
 * Created by uy on 6/18/2018.
 */

public class UtilsForMarkSheetEditAcitvity {
    public static int count = 0;


    public static void getStudentList(final String className, final String sectionName, final RecyclerView recyclerView, final Activity activity, final SubjectMarkSheet subjectMarkSheet, final String key, final Button saveButton, final Button printButton) {
        FirebaseCaller firebaseCaller = new FirebaseCaller();

        firebaseCaller.getDatabaseReferenceForGetStudentList(className, sectionName).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                globalStudentList.clear();
                for (DataSnapshot StudentData : dataSnapshot.getChildren()) {
                    Student student;
                    student = StudentData.getValue(Student.class);
                    globalStudentList.add(student);
                }
                Log.d("GK", "Student list size :" + globalStudentList.size());


                if (Constant.globalStudentList != null) {
                    Log.d("GK", "Student list size in oncreate :" + globalStudentList.size());

                    UtilsCommon.logString(key);

                    final MarkSheetEditAdapter markSheetEditAdapter = new MarkSheetEditAdapter(activity, globalStudentList, subjectMarkSheet, className, sectionName, key);
                    LinearLayoutManager MyLayoutManager = new LinearLayoutManager(activity);
                    MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setAdapter(markSheetEditAdapter);
                    recyclerView.setLayoutManager(MyLayoutManager);

                    saveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            markSheetEditAdapter.saveDataToServer();

                        }
                    });

                    printButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            markSheetEditAdapter.printData();

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

}
