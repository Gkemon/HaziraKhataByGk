package com.Teachers.HaziraKhataByGk.Scheduler;

import android.content.Context;
import android.util.Log;

import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.Listener.TodoItemServerListenter;
import com.Teachers.HaziraKhataByGk.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StoreRetrieveData {
    private Context mContext;
    private String mFileName;

    public static FirebaseAuth auth;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public static String mUserId;
    public static FirebaseUser mFirebaseUser;




    public static void saveToServer(List<ToDoItem> items){

        DatabaseReference databaseReference=
                FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Schedule");
        databaseReference.keepSynced(true);
        databaseReference.removeValue();


        for(ToDoItem toDoItem:items) {
            databaseReference.push().setValue(toDoItem);
            UtilsCommon.debugLog(toDoItem.getToDoContent()+" from for loop");
        }


    }

    public static void saveToServer(ToDoItem items){

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
        databaseReference.keepSynced(true);


        databaseReference.child("Users").child(FirebaseCaller.getUserID()).child("Schedule").push().setValue(items);

    }

    public static void loadToDoFromServer(final TodoItemServerListenter todoItemServerListenter){
       final ArrayList<ToDoItem> items = new ArrayList<>();

        //DATABASE CONNECTION
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
        databaseReference.keepSynced(true);



        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("GK","from loadToDoFromServer "+dataSnapshot.getChildrenCount());
                for(DataSnapshot toDoitemData:dataSnapshot.getChildren()){
                    ToDoItem toDoItem=new ToDoItem();
                    toDoItem=toDoitemData.getValue(ToDoItem.class);
                    items.add(toDoItem);
                }



                if(items.isEmpty()){
                    todoItemServerListenter.onError("ToDo is empty");
                }
                else todoItemServerListenter.onGetToDoItem(items);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

                todoItemServerListenter.onError("Database error : "+databaseError.getMessage());
                Log.d("GK","FIREBASE ERROR :"+databaseError.getMessage()+" Details :"+databaseError.getDetails());
            }
        };

        databaseReference.child("Users").child(FirebaseCaller.getUserID()).child("Schedule").removeEventListener(valueEventListener);
        databaseReference.child("Users").child(FirebaseCaller.getUserID()).child("Schedule").addListenerForSingleValueEvent(valueEventListener);




    }



}
