package com.Teachers.HaziraKhataByGk.Tabs.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.ActActivity;
import com.Teachers.HaziraKhataByGk.ClassRoom_activity;
import com.Teachers.HaziraKhataByGk.MainActivity;
import com.Teachers.HaziraKhataByGk.adapter.ContactListAdapter;
import com.Teachers.HaziraKhataByGk.listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.model.class_item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.Teachers.HaziraKhataByGk.MainActivity.databaseReference;
import static com.Teachers.HaziraKhataByGk.MainActivity.mUserId;
import static com.Teachers.HaziraKhataByGk.MainActivity.view;
import static com.Teachers.HaziraKhataByGk.MainActivity.view1;


public class classRoomFragments extends Fragment implements RecyclerItemClickListener{
    private View rootView;

    public static RecyclerView recyclerViewForClass;
    public FloatingActionButton btnAdd;
    private Context context;
    public static ContactListAdapter contactListAdapter;
    public GridLayoutManager gridLayoutManager;


    public classRoomFragments() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =inflater.inflate(R.layout.fragment_one, container, false);
        view=rootView.findViewById(R.id.EmptyImage);
        view1=rootView.findViewById(R.id.EmptyText);

        //THIS MAKES THE EMPTY IMAGE AND EMPTY DESCRIPTION
        if(MainActivity.isClassListEmpty){
            view.setVisibility(View.VISIBLE);
            view1.setVisibility(View.VISIBLE);
        }
        else {
            view.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
        }


        //VIEWS
        recyclerViewForClass = (RecyclerView) rootView.findViewById(R.id.recycleViewFromFragmentOne);
        btnAdd = (FloatingActionButton) rootView.findViewById(R.id.add);
        context = MainActivity.context;
        gridLayoutManager = new GridLayoutManager(context,2);
        recyclerViewForClass.setLayoutManager(gridLayoutManager);
        contactListAdapter = new ContactListAdapter(context);
        contactListAdapter.setOnItemClickListener(this);

        //Class click listener
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActActivity.start(MainActivity.activity);
            }
        });



        //For loading class_room from Server
        databaseReference.child("Users").child(mUserId).child("Class").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<class_item> class_items=new ArrayList<class_item>();
                  for(DataSnapshot classData:dataSnapshot.getChildren()){
                    class_item class_item=new class_item();
                    class_item=classData.getValue(class_item.class);
                    class_items.add(class_item);
                }
                MainActivity.TotalClassItems=new ArrayList<class_item>();
                MainActivity.TotalClassItems=class_items;
                    //IT MAKES THE INSTRUCTION ON CLASS FRAGMENT WHEN THERE IS NO CLASS
                    Query queryReforSeeTheDataIsEmptyOrNot = databaseReference.child("Users").child(mUserId).child("Class");
                    queryReforSeeTheDataIsEmptyOrNot.addListenerForSingleValueEvent( new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){MainActivity.isClassListEmpty=false;}
                            else {MainActivity.isClassListEmpty=true;}

                            //THIS MAKES THE EMPTY IMAGE AND EMPTY DESCRIPTION
                            if(MainActivity.isClassListEmpty){
                                view.setVisibility(View.VISIBLE);
                                view1.setVisibility(View.VISIBLE);
                            }
                            else {
                                view.setVisibility(View.GONE);
                                view1.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {}});


                    //SET ADAPTER
                    classRoomFragments.contactListAdapter.clear();
                    classRoomFragments.contactListAdapter.addAll(MainActivity.TotalClassItems);
                    classRoomFragments.recyclerViewForClass.setAdapter(contactListAdapter);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onItemClick(int position, View view) {
        ClassRoom_activity.start(MainActivity.activity, contactListAdapter.getItem(position));
    }

    @Override
    public void onItemLongPressed(int position, View view) {
        ActActivity.start(MainActivity.activity,contactListAdapter.getItem(position));
    }
}
