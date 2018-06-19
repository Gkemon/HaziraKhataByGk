package com.Teachers.HaziraKhataByGk.HelperClassess;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.Teachers.HaziraKhataByGk.adapter.SubjectMarkSheetAdaper;
import com.Teachers.HaziraKhataByGk.model.SubjectMarkSheet;
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
    public static FirebaseUser mFirebaseUser;
    public FirebaseCaller(){
        initializationFirebase();
    }

    public  void initializationFirebase(){

        if (!calledAlready) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseDatabase.setPersistenceEnabled(true);
            databaseReference = firebaseDatabase.getReference();
            calledAlready = true;
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        auth=FirebaseAuth.getInstance();
        databaseReference.keepSynced(true);

    }

    public static String getUserID(){
        return getCurrentUser().getUid();
    }
    public static FirebaseUser getCurrentUser(){
        return auth.getCurrentUser();
    }


    public  void pushSubjectToServer(String className, String sectionName,SubjectMarkSheet subjectMarkSheet){

        databaseReference.child("Users").child(getUserID()).child("Class").child(className+sectionName).child("Subject").push().setValue(subjectMarkSheet);

    }
    public  void pushAndRemoveSubjectToServer(String className, String sectionName,SubjectMarkSheet subjectMarkSheet,String key){

//        if(key==null)key="";
//        databaseReference.child("Users").child(getUserID()).child("Class").child(className+sectionName).child("Subject").child(key).removeValue();
        databaseReference.child("Users").child(getUserID()).child("Class").child(className+sectionName).child("Subject").child(key).keepSynced(true);

        databaseReference.child("Users").child(getUserID()).child("Class").child(className+sectionName).child("Subject").push().setValue(subjectMarkSheet);

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


