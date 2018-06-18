package com.Teachers.HaziraKhataByGk.HelperClassess;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

//public static boolean ReCallNetWorkForStudentList(final String className, final String sectionName){
//    final FirebaseCaller firebaseCaller = new FirebaseCaller();
//    firebaseCaller.getStudentList(className,sectionName);
//
//    if(GLOBAL_STUDENT_LIST==null){
//        Log.d("GK","GLOBAL_STUDENT_LIST==null");
//
//         Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                count++;
//                Log.d("GK",count + " : count");
//                if(count==6)return;
//                ReCallNetWorkForStudentList(className,sectionName);
//            }
//        }, 500);
//
//    }
//    if(count>=6)
//        return false;
//    else return true;
//
//}


    public static void getStudentList(String className, String sectionName, final RecyclerView recyclerView, final Activity activity, final SubjectMarkSheet subjectMarkSheet) {
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

                    MarkSheetEditAdapter markSheetEditAdapter = new MarkSheetEditAdapter(activity,GLOBAL_STUDENT_LIST,subjectMarkSheet);
                    LinearLayoutManager MyLayoutManager = new LinearLayoutManager(activity);
                    MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setAdapter(markSheetEditAdapter);
                    recyclerView.setLayoutManager(MyLayoutManager);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

}
