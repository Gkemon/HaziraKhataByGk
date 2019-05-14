package com.Teachers.HaziraKhataByGk.Tabs;

import android.content.Context;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Teachers.HaziraKhataByGk.AddEditClass.ClassAddActivity;
import com.Teachers.HaziraKhataByGk.ClassRoomActivity;
import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.MainActivity;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.Adapter.ClassListAdapter;
import com.Teachers.HaziraKhataByGk.Listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.Model.ClassIitem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.Teachers.HaziraKhataByGk.MainActivity.mUserId;


public class ClassRoomFragments extends Fragment implements RecyclerItemClickListener{
    private View rootView;

    public  RecyclerView recyclerViewForClass;
    public FloatingActionButton btnAdd;
    private Context context;
    public  ClassListAdapter classListAdapter;
    public GridLayoutManager gridLayoutManager;

    public View emptyView;

    public ClassRoomFragments() {
        // Required empty public constructor
    }


    void initiView(){

        emptyView=rootView.findViewById(R.id.toDoEmptyView);
        //THIS MAKES THE EMPTY IMAGE AND EMPTY DESCRIPTION
        if(MainActivity.isClassListEmpty){
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            emptyView.setVisibility(View.GONE);
        }


        //VIEWS
        recyclerViewForClass = (RecyclerView) rootView.findViewById(R.id.recycleViewFromFragmentOne);
        btnAdd = (FloatingActionButton) rootView.findViewById(R.id.add);
        context = getContext();
        gridLayoutManager = new GridLayoutManager(context,2);
        recyclerViewForClass.setLayoutManager(gridLayoutManager);
        classListAdapter = new ClassListAdapter(context);
        classListAdapter.setOnItemClickListener(this);
        recyclerViewForClass.setAdapter(classListAdapter);


        //Class click listener
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassAddActivity.start(getActivity());
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        rootView =inflater.inflate(R.layout.fragment_one, container, false);
        initiView();
        return rootView;
    }

    @Override
    public void onResume() {

        //For loading class_room from Server
        LoadDataFromServer();
        super.onResume();
    }

    @Override
    public void onItemClick(int position, View view) {
        ClassRoomActivity.start(getContext(), classListAdapter.getItem(position));
    }

    @Override
    public void onItemLongPressed(int position, View view) {
        ClassAddActivity.start(getContext(), classListAdapter.getItem(position));
    }

     public  void LoadDataFromServer(){
        //For loading class_room from Server
        FirebaseCaller.getFirebaseDatabase().child("Users").child(mUserId).child("Class").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<ClassIitem> ClassIitems =new ArrayList<ClassIitem>();
                for(DataSnapshot classData:dataSnapshot.getChildren()){
                    ClassIitem ClassIitem =new ClassIitem();
                    ClassIitem =classData.getValue(ClassIitem.class);
                    ClassIitems.add(ClassIitem);
                }
                MainActivity.TotalClassItems=new ArrayList<ClassIitem>();
                MainActivity.TotalClassItems= ClassIitems;
                //IT MAKES THE INSTRUCTION ON CLASS FRAGMENT WHEN THERE IS NO CLASS
                Query queryReforSeeTheDataIsEmptyOrNot = FirebaseCaller.getFirebaseDatabase().child("Users").child(mUserId).child("Class");
                queryReforSeeTheDataIsEmptyOrNot.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        MainActivity.isClassListEmpty = !dataSnapshot.exists();

                        //THIS MAKES THE EMPTY IMAGE AND EMPTY DESCRIPTION
                        if(MainActivity.isClassListEmpty){
                          emptyView.setVisibility(View.VISIBLE);
                        }
                        else {
                            emptyView.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}});


                //SET ADAPTER
                classListAdapter.clear();
                classListAdapter.addAll(MainActivity.TotalClassItems);
                classListAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
