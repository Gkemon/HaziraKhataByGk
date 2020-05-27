package com.Teachers.HaziraKhataByGk.Tabs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Teachers.HaziraKhataByGk.Adapter.ClassListAdapter;
import com.Teachers.HaziraKhataByGk.AddEditClass.ClassAddActivity;
import com.Teachers.HaziraKhataByGk.ClassRoom.ClassRoomActivity;
import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.HelperClassess.CustomArrayList;
import com.Teachers.HaziraKhataByGk.HelperClassess.LoadingPopup;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.Listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.Model.ClassItem;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.Routine.AllRoutineShowingDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class ClassRoomFragment extends Fragment implements RecyclerItemClickListener {
    public RecyclerView recyclerViewForClass;
    public FloatingActionButton btnAdd;
    public ClassListAdapter classListAdapter;
    public GridLayoutManager gridLayoutManager;
    public View emptyView;
    private View rootView;
    private Context context;


    public ClassRoomFragment() {
        // Required empty public constructor
    }

    @OnClick(R.id.btn_make_schedule)
    public void showRoutine() {
        AllRoutineShowingDialog.showDialog(getFragmentManager());
    }

    private void initView() {

        ButterKnife.bind(this, rootView);
        emptyView = rootView.findViewById(R.id.toDoEmptyView);

        LoadingPopup.showLoadingPopUp(getActivity());


        rootView.findViewById(R.id.help).setOnClickListener(v -> UtilsCommon.openWithFaceBook
                (getString(R.string.help_fb_url), context));

        //VIEWS
        recyclerViewForClass = rootView.findViewById(R.id.recycleViewFromFragmentOne);
        btnAdd = rootView.findViewById(R.id.add);
        context = getContext();
        gridLayoutManager = new GridLayoutManager(context, 2);
        recyclerViewForClass.setLayoutManager(gridLayoutManager);
        classListAdapter = new ClassListAdapter(context);
        classListAdapter.setOnItemClickListener(this);
        recyclerViewForClass.setAdapter(classListAdapter);


        //Class click listener
        btnAdd.setOnClickListener(v -> ClassAddActivity.start(getActivity()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_class_room, container, false);
        initView();
        return rootView;
    }

    @Override
    public void onResume() {


        //THIS MAKES THE EMPTY IMAGE AND EMPTY DESCRIPTION
        if (FirebaseCaller.getCurrentUser() == null) {
            LoadingPopup.hideLoadingPopUp();
            emptyView.setVisibility(View.VISIBLE);
        } else {
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

    public void loadDataFromServer() {
        //For loading class_room fromTime Server
        FirebaseCaller.getFirebaseDatabase().keepSynced(true);
        FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).
                child("Class").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CustomArrayList<ClassItem> classItems = new CustomArrayList<>();

                for (DataSnapshot classData : dataSnapshot.getChildren()) {
                    ClassItem classItem;
                    try {
                        classItem = classData.getValue(ClassItem.class);
                    } catch (Exception e) {
                        UtilsCommon.handleError(e);
                        continue;
                    }
                    if (classItem == null || classItem.getName() == null) continue;
                    classItems.add(classItem);

                }

                UtilsCommon.setAllClass(classItems, getActivity());

                if (classItems.size() == 0) emptyView.setVisibility(View.VISIBLE);
                else emptyView.setVisibility(View.GONE);


                //SET ADAPTER
                classListAdapter.clear();
                classListAdapter.addAll(classItems);
                classListAdapter.notifyDataSetChanged();
                LoadingPopup.hideLoadingPopUp();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                UtilsCommon.debugLog("Error: " + databaseError.getMessage());
                emptyView.setVisibility(View.VISIBLE);
                LoadingPopup.hideLoadingPopUp();
            }
        });
    }
}
