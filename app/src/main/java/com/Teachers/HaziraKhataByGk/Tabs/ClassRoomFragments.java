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
import com.Teachers.HaziraKhataByGk.ClassRoom.ClassRoomActivity;
import com.Teachers.HaziraKhataByGk.Constant.StaticData;
import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.HelperClassess.LoadingPopup;
import com.Teachers.HaziraKhataByGk.HelperClassess.CustomArrayList;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.MainActivity;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.Adapter.ClassListAdapter;
import com.Teachers.HaziraKhataByGk.Listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.Model.ClassItem;
import com.Teachers.HaziraKhataByGk.Routine.AllRoutineShowingDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class ClassRoomFragments extends Fragment implements RecyclerItemClickListener{
    private View rootView;

    public  RecyclerView recyclerViewForClass;
    public FloatingActionButton btnAdd;
    private Context context;
    public  ClassListAdapter classListAdapter;
    public GridLayoutManager gridLayoutManager;

    public View emptyView;


    @OnClick(R.id.btn_make_schedule)
    public void showRoutine(){
        AllRoutineShowingDialog.showDialog(getFragmentManager());
    }
    public ClassRoomFragments() {
        // Required empty public constructor
    }


    void initiView(){

        ButterKnife.bind(this,rootView);
        emptyView=rootView.findViewById(R.id.toDoEmptyView);

        LoadingPopup.showLoadingPopUp(getActivity());


        rootView.findViewById(R.id.help).setOnClickListener(v -> UtilsCommon.openWithFaceBook(getString(R.string.help_fb_url),context));

        //VIEWS
        recyclerViewForClass = rootView.findViewById(R.id.recycleViewFromFragmentOne);
        btnAdd = rootView.findViewById(R.id.add);
        context = getContext();
        gridLayoutManager = new GridLayoutManager(context,2);
        recyclerViewForClass.setLayoutManager(gridLayoutManager);
        classListAdapter = new ClassListAdapter(context);
        classListAdapter.setOnItemClickListener(this);
        recyclerViewForClass.setAdapter(classListAdapter);


        //Class click listener
        btnAdd.setOnClickListener(v -> ClassAddActivity.start(getActivity()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        rootView =inflater.inflate(R.layout.fragment_class_room, container, false);
        initiView();
        return rootView;
    }

    @Override
    public void onResume() {


        //THIS MAKES THE EMPTY IMAGE AND EMPTY DESCRIPTION
        if(FirebaseCaller.getCurrentUser()==null){
            LoadingPopup.hideLoadingPopUp();
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            emptyView.setVisibility(View.GONE);
            loadDataFromServer();
        }

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

     public  void loadDataFromServer(){
        //For loading class_room fromTime Server
        FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CustomArrayList<ClassItem> ClassIitems = new CustomArrayList<>();

                for(DataSnapshot classData:dataSnapshot.getChildren()){
                    ClassIitems.add(classData.getValue(ClassItem.class));
                }

                MainActivity.totalClassItems=ClassIitems;

                if(ClassIitems.size()==0)emptyView.setVisibility(View.VISIBLE);
                else emptyView.setVisibility(View.GONE);


                //SET ADAPTER
                classListAdapter.clear();
                classListAdapter.addAll(ClassIitems);
                classListAdapter.notifyDataSetChanged();
                LoadingPopup.hideLoadingPopUp();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                UtilsCommon.debugLog("Error: "+databaseError.getMessage());
                emptyView.setVisibility(View.VISIBLE);
                LoadingPopup.hideLoadingPopUp();
            }
        });
    }
}
