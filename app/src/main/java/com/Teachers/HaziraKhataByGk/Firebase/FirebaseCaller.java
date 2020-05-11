package com.Teachers.HaziraKhataByGk.Firebase;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Teachers.HaziraKhataByGk.Adapter.SubjectMarkSheetAdaper;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.Listener.CommonCallback;
import com.Teachers.HaziraKhataByGk.Model.AttendenceData;
import com.Teachers.HaziraKhataByGk.Model.ClassItem;
import com.Teachers.HaziraKhataByGk.Model.Student;
import com.Teachers.HaziraKhataByGk.Model.SubjectMarkSheet;
import com.Teachers.HaziraKhataByGk.Routine.RoutineItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * Created by uy on 5/28/2018.
 */

public class FirebaseCaller {

    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public static FirebaseAuth auth;
    private static boolean calledAlready;

    public FirebaseCaller() {
        initializationFirebase();
    }

    public static void setStudentToServer(ClassItem classItem, Student student) {
        FirebaseCaller.getFirebaseDatabase().child("Users")
                .child(FirebaseCaller.getUserID())
                .child("Class")
                .child(classItem.getName() + classItem.getSection())
                .child("Student")
                .child(student.getId()).setValue(student);
    }

    public static void setStudentToServer(ClassItem classItem, Student student, CommonCallback commonCallback) {

        FirebaseCaller.getFirebaseDatabase().child("Users")
                .child(FirebaseCaller.getUserID())
                .child("Class")
                .child(classItem.getName() + classItem.getSection())
                .child("Student")
                .child(student.getId()).setValue(student).addOnSuccessListener(aVoid -> commonCallback.onSuccess())
                .addOnFailureListener(e -> commonCallback.onFailure("Error in getting item_student from server : " + e.getLocalizedMessage()));
    }

    public static void removeStudentToServer(ClassItem classItem, String studentId) {
        FirebaseCaller.getFirebaseDatabase().child("Users")
                .child(FirebaseCaller.getUserID())
                .child("Class")
                .child(classItem.getName() + classItem.getSection())
                .child("Student")
                .child(studentId).removeValue();
    }

    public static void setStudentAttendanceToServer(ClassItem classItem, String studentId, AttendenceData attendenceData) {

        FirebaseCaller.
                getFirebaseDatabase()
                .child("Users").child(FirebaseCaller.getUserID())
                .child("Class")
                .child(classItem.getName() + classItem.getSection())
                .child("Student").child(studentId)
                .child("Attendance").push()
                .setValue(attendenceData);
    }


    public static void getAttendanceDataForSingleStudent(String className, String sectionName, String roll, final CommonCallback<ArrayList<AttendenceData>> commonCallback) {


        DatabaseReference databaseReference = getSingleStudentAttendanceDbRef(className, sectionName, roll);

        if (databaseReference != null)
            databaseReference.
                    addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<AttendenceData> attendenceDataList = new ArrayList<>();

                for (DataSnapshot singleDataSnapshot : dataSnapshot.getChildren()) {

                    AttendenceData attendenceData = singleDataSnapshot.getValue(AttendenceData.class);

                    if (attendenceData != null) {
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

    public static DatabaseReference getSingleStudentDbRef(Student student) {
        if (student != null)
            return FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).
                    child("Class").
                    child(student.getStudentClass() + student.getStudentSection()).child("Student").child(student.getId());
        else {
            UtilsCommon.showToast("Student is null");
            return null;
        }
    }

    public static DatabaseReference getSingleStudentAttendanceDbRef(String className, String sectionName,
                                                                    String roll) {

        try {
            return FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).
                    child("Class").
                    child(className + sectionName).child("Student").child(roll).child("Attendance");
        } catch (Exception e) {
            return null;
        }

    }


    public static DatabaseReference getFirebaseDatabase() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public static void initializationFirebase() {

        if (!calledAlready) {
            firebaseDatabase = FirebaseDatabase.getInstance();

            try {
                firebaseDatabase.setPersistenceEnabled(true);
            } catch (Exception e) {

            }

            databaseReference = firebaseDatabase.getReference();
            calledAlready = true;
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        auth = FirebaseAuth.getInstance();
        databaseReference.keepSynced(true);

    }

    public static String getUserID() {

        if (getCurrentUser() == null) {
            return "-11";
        } else
            return getCurrentUser().getUid();
    }

    public static FirebaseUser getCurrentUser() {
        return getAuth().getCurrentUser();
    }

    public static FirebaseAuth getAuth() {
        return FirebaseAuth.getInstance();
    }

    public static void deleteData(String className, String sectionName, String key) {
        databaseReference.child("Users").child(getUserID()).child("Class").child(className + sectionName).child("Subject").child(key).keepSynced(true);
        databaseReference.child("Users").child(getUserID()).child("Class").child(className + sectionName).child("Subject").child(key).removeValue();
    }

    public static void addRoutine(RoutineItem routineItem,CommonCallback commonCallback){
        databaseReference.child("Users").child(getUserID()).push().setValue(routineItem)
                .addOnSuccessListener(aVoid -> commonCallback.onSuccess())
                .addOnFailureListener(e -> commonCallback.onFailure(e.getMessage()));
    }

    public static void getTotalSubject(final String className, final String sectionName, final RecyclerView recyclerView, final Activity activity, final TextView emptyText) {

        databaseReference.child("Users").child(getUserID()).child("Class").child(className + sectionName).child("Subject").keepSynced(true);
        databaseReference.child("Users").child(getUserID()).child("Class").child(className + sectionName).child("Subject").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<SubjectMarkSheet> subjectList = new ArrayList<SubjectMarkSheet>();
                ArrayList<String> keys = new ArrayList<>();

                for (DataSnapshot subjectData : dataSnapshot.getChildren()) {

                    SubjectMarkSheet subjectMarkSheet = new SubjectMarkSheet();
                    subjectMarkSheet = subjectData.getValue(SubjectMarkSheet.class);
                    subjectList.add(subjectMarkSheet);
                    keys.add(subjectData.getKey());
                    if (subjectMarkSheet != null) {
                        Log.d("GK", "subjectMarkSheet");
                    } else {
                        Log.d("GK", "null the size of dhaha");
                    }

                }
                if (subjectList.size() == 0) {
                    emptyText.setVisibility(View.VISIBLE);
                } else emptyText.setVisibility(View.GONE);


                if (subjectList.size() > 0 && recyclerView != null) {

                    SubjectMarkSheetAdaper subjectMarkSheetAdaper = new SubjectMarkSheetAdaper(className, sectionName, subjectList, keys, activity);
                    LinearLayoutManager MyLayoutManager = new LinearLayoutManager(activity);
                    MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setAdapter(subjectMarkSheetAdaper);
                    recyclerView.setLayoutManager(MyLayoutManager);

                    Log.d("GK", "recyclerView is not null");
                }

                if (subjectList != null) {
                    Log.d("GK", "Episode list size " + String.valueOf(subjectList.size()));
                } else
                    Log.d("GK", "Episode is null");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("GK", "error news" + databaseError.getMessage());

            }
        });
    }

    public static DatabaseReference getDatabaseReferenceForGetStudentList(String className, String sectionName) {
        return FirebaseDatabase.getInstance().getReference().child("Users").child(getUserID()).child("Class").child(className + sectionName).child("Student");
    }

    public void pushSubjectToServer(String className, String sectionName, SubjectMarkSheet subjectMarkSheet) {

        databaseReference.child("Users").child(getUserID()).child("Class").child(className + sectionName).child("Subject").push().setValue(subjectMarkSheet);

    }

    public void pushAndRemoveSubjectToServer(String className, String sectionName, SubjectMarkSheet subjectMarkSheet, String key) {


        databaseReference.child("Users").child(getUserID()).child("Class").child(className + sectionName).child("Subject").child(key).keepSynced(true);
        databaseReference.child("Users").child(getUserID()).child("Class").child(className + sectionName).child("Subject").child(key).setValue(subjectMarkSheet);

    }


}


