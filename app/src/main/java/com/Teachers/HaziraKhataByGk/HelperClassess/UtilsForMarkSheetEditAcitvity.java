package com.Teachers.HaziraKhataByGk.HelperClassess;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.Teachers.HaziraKhataByGk.adapter.MarkSheetEditAdapter;
import com.Teachers.HaziraKhataByGk.constant.ContantsForGlobal;
import com.Teachers.HaziraKhataByGk.model.SubjectMarkSheet;
import com.Teachers.HaziraKhataByGk.model.student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.Teachers.HaziraKhataByGk.constant.ContantsForGlobal.GLOBAL_STUDENT_LIST;

/**
 * Created by uy on 6/18/2018.
 */

public class UtilsForMarkSheetEditAcitvity {
    public static int count=0;


    public static void getStudentList(final String className, final String sectionName, final RecyclerView recyclerView, final Activity activity, final SubjectMarkSheet subjectMarkSheet, final String key, final Button saveButton,final Button printButton) {
FirebaseCaller firebaseCaller =new FirebaseCaller();

firebaseCaller.getDatabaseReferenceForGetStudentList(className,sectionName).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GLOBAL_STUDENT_LIST.clear();
                for (DataSnapshot StudentData : dataSnapshot.getChildren()) {
                    student student;
                    student = StudentData.getValue(student.class);
                    GLOBAL_STUDENT_LIST.add(student);
                }
                Log.d("GK","student list size :"+GLOBAL_STUDENT_LIST.size());


                if(ContantsForGlobal.GLOBAL_STUDENT_LIST!=null){
                    Log.d("GK","student list size in oncreate :"+GLOBAL_STUDENT_LIST.size());

                    UtilsCommon.logString(key);

                  final MarkSheetEditAdapter markSheetEditAdapter = new MarkSheetEditAdapter(activity,GLOBAL_STUDENT_LIST,subjectMarkSheet,className,sectionName,key);
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
