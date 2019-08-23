package com.Teachers.HaziraKhataByGk.Firebase;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.Teachers.HaziraKhataByGk.Adapter.SubjectMarkSheetAdaper;
import com.Teachers.HaziraKhataByGk.ClassRoomActivity;
import com.Teachers.HaziraKhataByGk.Listener.CommonCallback;
import com.Teachers.HaziraKhataByGk.Model.AttendenceData;
import com.Teachers.HaziraKhataByGk.Model.Student;
import com.Teachers.HaziraKhataByGk.Model.SubjectMarkSheet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.Teachers.HaziraKhataByGk.MainActivity.calledAlready;

/**
 * Created by uy on 5/28/2018.
 */

public class FirebaseCaller {

    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public static FirebaseAuth auth;

    public FirebaseCaller(){
        initializationFirebase();
    }




    public static void getAttendanceDataForSingleStudent(String className, String sectionName, String roll, final CommonCallback<ArrayList<AttendenceData>> commonCallback){


        getSingleStudentAttendanceDbRef(className,sectionName,roll).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<AttendenceData> attendenceDataList=new ArrayList<>();

                for(DataSnapshot singleDataSnapshot:dataSnapshot.getChildren()){

                    AttendenceData attendenceData = singleDataSnapshot.getValue(AttendenceData.class);

                    if(attendenceData!=null)
                    {
                        //TODO: Here we set key because we need it for edit any attendance data.
                        attendenceData.setKey(singleDataSnapshot.getKey());
                        attendenceDataList.add(attendenceData);
                    }

                }

                commonCallback.onSuccess(attendenceDataList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
               commonCallback.onFailure(databaseError.getMessage());
            }
        });

    }
    public static DatabaseReference getSingleStudentDbRef(Student student){
       return FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).
                child("Class").
                child(student.getStudentClass() + student.getStudentSection()).child("Student").child(student.getId());
    }

    public static DatabaseReference getSingleStudentAttendanceDbRef(String className,String sectionName,String roll){
        return FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).
                child("Class").
                child(className + sectionName).child("Student").child(roll).child("Attendance");
    }


    public static DatabaseReference getFirebaseDatabase(){
        return FirebaseDatabase.getInstance().getReference();
    }
    public  void initializationFirebase(){

        if (!calledAlready) {
            firebaseDatabase = FirebaseDatabase.getInstance();

            try {
                firebaseDatabase.setPersistenceEnabled(true);
            }
            catch (Exception e){
                calledAlready = true;
            }

            databaseReference = firebaseDatabase.getReference();
            calledAlready = true;
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        auth=FirebaseAuth.getInstance();
        databaseReference.keepSynced(true);

    }

    public static String getUserID(){

        if(getCurrentUser()==null){
            return "-11";
        }
        else
        return getCurrentUser().getUid();
    }
    public static FirebaseUser getCurrentUser(){
        return getAuth().getCurrentUser();
    }

    public static FirebaseAuth getAuth(){
       return FirebaseAuth.getInstance();
    }


    public  void pushSubjectToServer(String className, String sectionName,SubjectMarkSheet subjectMarkSheet){

        databaseReference.child("Users").child(getUserID()).child("Class").child(className+sectionName).child("Subject").push().setValue(subjectMarkSheet);

    }
    public  void pushAndRemoveSubjectToServer(String className, String sectionName,SubjectMarkSheet subjectMarkSheet,String key){


        databaseReference.child("Users").child(getUserID()).child("Class").child(className+sectionName).child("Subject").child(key).keepSynced(true);
        databaseReference.child("Users").child(getUserID()).child("Class").child(className+sectionName).child("Subject").child(key).setValue(subjectMarkSheet);

    }


    public static void deleteData(String className,String sectionName,String key){
        databaseReference.child("Users").child(getUserID()).child("Class").child(className+sectionName).child("Subject").child(key).keepSynced(true);
        databaseReference.child("Users").child(getUserID()).child("Class").child(className+sectionName).child("Subject").child(key).removeValue();
    }

    public static void getTotalSubject(final String className,final String sectionName, final RecyclerView recyclerView, final Activity activity,final TextView emptyText) {

        databaseReference.child("Users").child(getUserID()).child("Class").child(className+sectionName).child("Subject").keepSynced(true);
        databaseReference.child("Users").child(getUserID()).child("Class").child(className+sectionName).child("Subject").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<SubjectMarkSheet> subjectList = new ArrayList<SubjectMarkSheet>();
                ArrayList<String> keys = new ArrayList<>();

                for (DataSnapshot subjectData : dataSnapshot.getChildren()) {

                    SubjectMarkSheet subjectMarkSheet = new SubjectMarkSheet();
                    subjectMarkSheet = subjectData.getValue(SubjectMarkSheet.class);
                    subjectList.add(subjectMarkSheet);
                    keys.add(subjectData.getKey());
                    if(subjectMarkSheet!=null){
                        Log.d("GK","subjectMarkSheet");
                    }
                    else {
                        Log.d("GK","null the size of dhaha");
                    }

                }
                if(subjectList.size()==0){
                    emptyText.setVisibility(View.VISIBLE);
                }
                else emptyText.setVisibility(View.GONE);


                if(subjectList.size()>0&&recyclerView!=null) {

                    SubjectMarkSheetAdaper subjectMarkSheetAdaper = new SubjectMarkSheetAdaper(className,sectionName,subjectList,keys,activity);
                    LinearLayoutManager MyLayoutManager = new LinearLayoutManager(activity);
                    MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setAdapter(subjectMarkSheetAdaper);
                    recyclerView.setLayoutManager(MyLayoutManager);

                    Log.d("GK","recyclerView is not null");
                }

                if(subjectList!=null){
                    Log.d("GK", "Episode list size " + String.valueOf(subjectList.size()));
                }

else
                    Log.d("GK", "Episode is null");

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("GK", "error news" + databaseError.getMessage());

            }
        });
    }


    public static DatabaseReference getDatabaseReferenceForGetStudentList(String className,String sectionName){
        return FirebaseDatabase.getInstance().getReference().child("Users").child(getUserID()).child("Class").child(className + sectionName).child("Student");
    }



}


