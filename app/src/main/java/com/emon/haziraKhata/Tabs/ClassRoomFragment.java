package com.emon.haziraKhata.Tabs;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emon.haziraKhata.Adapter.ClassListAdapter;
import com.emon.haziraKhata.AddEditClass.ClassAddActivity;
import com.emon.haziraKhata.ClassRoom.ClassRoomActivity;
import com.emon.haziraKhata.Firebase.FirebaseCaller;
import com.emon.haziraKhata.HelperClasses.CustomArrayList;
import com.emon.haziraKhata.HelperClasses.LoadingPopup;
import com.emon.haziraKhata.HelperClasses.UtilsCommon;
import com.emon.haziraKhata.Home.MainViewModel;
import com.emon.haziraKhata.Listener.RecyclerItemClickListener;
import com.emon.haziraKhata.Model.ClassItem;
import com.emon.haziraKhata.R;
import com.emon.haziraKhata.routine.AllRoutineShowingDialog;
import com.emon.haziraKhata.routine.RoutineItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class ClassRoomFragment extends Fragment implements RecyclerItemClickListener {
    private MainViewModel mainViewModel;
    public RecyclerView recyclerViewForClass;
    public FloatingActionButton btnAdd;
    public ClassListAdapter classListAdapter;
    public GridLayoutManager gridLayoutManager;
    public View emptyView;
    private View rootView;
    private Context context;
    private MediaPlayer mediaPlayer;


    public ClassRoomFragment() {
        // Required empty public constructor
    }

    @OnClick(R.id.btn_make_schedule)
    public void showRoutine() {
        AllRoutineShowingDialog.showDialog(getChildFragmentManager());
    }

    private void showAlarm(){
        if(mainViewModel.getTriggeredRoutines()!=null&&!mainViewModel.getTriggeredRoutines().isEmpty()){

            if (mediaPlayer == null)
                mediaPlayer = UtilsCommon.getMediaPlayer(getContext());
            mediaPlayer.start();

            AlertDialog alertDialog =
                    new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle("বর্তমান কাজ");
            alertDialog.setMessage(getStringFromRoutines(mainViewModel.getTriggeredRoutines()));
            alertDialog.setCancelable(false);

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "রুটিন দেখুন",
                    (dialog, which) -> {
                        stopAlarm();
                        dialog.dismiss();
                        AllRoutineShowingDialog.showDialog(getChildFragmentManager());
                    }
            );
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"এপটি বন্ধ করুন",
                    (dialog, which) -> {
                        stopAlarm();
                        dialog.dismiss();
                        if(getActivity()!=null)
                            getActivity().finish();
                        System.exit(0);
                    });
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"এলার্ম বন্ধ করুন",
                    (dialog, which) -> {
                        stopAlarm();
                    });
            alertDialog.show();
        }

    }
    private void stopAlarm() {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopAlarm();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel=new ViewModelProvider(getActivity()).get(MainViewModel.class);
        showAlarm();
    }

    private String getStringFromRoutines(List<RoutineItem> routineItems){
        StringBuilder stringBuilder =new StringBuilder();
        for(RoutineItem routineItem:routineItems){
            stringBuilder.append("∎ ");
            stringBuilder.append(routineItem.getName());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
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
