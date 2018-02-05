package com.Teachers.HaziraKhataByGk;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.Teachers.HaziraKhataByGk.adapter.noteListAdapter;
import com.Teachers.HaziraKhataByGk.listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.model.Notes;
import com.Teachers.HaziraKhataByGk.model.class_item;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.Teachers.HaziraKhataByGk.MainActivity.mUserId;

public class ClassRoom_activity extends AppCompatActivity  implements RecyclerItemClickListener {

    public static void start(Context context, class_item classitem) {
        Intent intent = new Intent(context, ClassRoom_activity.class);
        intent.putExtra(ClassRoom_activity.class.getSimpleName(), classitem);
        context.startActivity(intent);
    }

    private RecyclerView NOTES;
    private FloatingActionButton btnAdd;
    public static Activity activity;
    private LinearLayoutManager linearLayoutManager;
    private noteListAdapter noteListAdapter;
    private Context context;
    private LinearLayout linearLayoutForPresent, linearLayoutForStudentProfile;
    public static class_item classitem;
    public static List<Notes> notesList;
    public Button feesButton;
    View EmptyView;
    public LinearLayout adlayout;
    public AdView mAdView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        context = getApplicationContext();
        setContentView(R.layout.activity_class_room);

        NOTES = (RecyclerView) findViewById(R.id.notes);
        EmptyView = findViewById(R.id.toDoEmptyView);
        notesList=new ArrayList<Notes>();
        btnAdd = (FloatingActionButton) findViewById(R.id.fabForNotes);
        feesButton=(Button)findViewById(R.id.feesButton);

        linearLayoutManager = new LinearLayoutManager(this);
        noteListAdapter = new noteListAdapter(this);
        noteListAdapter.setOnItemClickListener(this);
        NOTES.setLayoutManager(linearLayoutManager);
        NOTES.setAdapter(noteListAdapter);
        
        //FOR ADD CLICK LISTENER IN ATTENDENCE AND STUDENT PROFILE ACTIVITY
        linearLayoutForStudentProfile = (LinearLayout) findViewById(R.id.CardlayoutForStudentProfile);
        linearLayoutForPresent = (LinearLayout) findViewById(R.id.CardlayoutForAttendence);

        //FOR GETTING THE CURRENT CLASS
        classitem = getIntent().getParcelableExtra(ClassRoom_activity.class.getSimpleName());

        feesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ClassRoom_activity.activity,FeesAcitvity.class);
                activity.startActivity(intent);
            }
        });


        //ADMOB
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                .build();
        adlayout=findViewById(R.id.ads);
        mAdView = (AdView) findViewById(R.id.adViewInHome);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed() {
                // Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                adlayout.setVisibility(View.GONE);
                // Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAdLeftApplication() {
                // Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });
        mAdView.loadAd(adRequest);

        //FOR ATTENDENCE
        if (linearLayoutForPresent != null) {
            linearLayoutForPresent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = activity.getFragmentManager();
                    createRequest request = new createRequest();
                    request.show(fm, "Select");
                }
            });
        }

        //FOR STUDENT PROFILE
        if (linearLayoutForStudentProfile != null) {
            linearLayoutForStudentProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent launchinIntent = new Intent(activity, studentActivity.class);
                    launchinIntent.putExtra(ClassRoom_activity.class.getSimpleName(), classitem);
                    activity.startActivity(launchinIntent);
                }
            });
        }

        //FAB TO NOTE ACTIVITY
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteAddActivity.start(ClassRoom_activity.this);
            }
        });


//        MainActivity.databaseReference.child("Class").child(ClassRoom_activity.classitem.getName() + ClassRoom_activity.classitem.getSection()).child("Notes").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                List<Notes> NotesList = new ArrayList<Notes>();
//                for (DataSnapshot NoteData : dataSnapshot.getChildren()) {
//                    Notes Notes = new Notes();
//                    Notes = NoteData.getValue(Notes.class_room);
//                    NotesList.add(Notes);
//                }
//                noteListAdapter.clear();
//                if(NotesList.size()==0){
//                    EmptyView.setVisibility(View.VISIBLE);
//                }
//                else {
//                    EmptyView.setVisibility(View.GONE);
//                }
//                noteListAdapter.addAll(NotesList);
//                NOTES.setAdapter(noteListAdapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        //FOR LOADING ATTENDANCE SHEET

//        Query queryForLoadSpecificClassAttendenceList = FirebaseDatabase.getInstance().getReference().child("Student").orderByChild("studentClass").equalTo(classitem.getName());
//        queryForLoadSpecificClassAttendenceList.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                final ArrayList<student> studentListFromServer = new ArrayList<student>();
//                for (DataSnapshot StudentData : dataSnapshot.getChildren()) {
//                    student student;
//                    student = StudentData.getValue(student.class_room);
//                    Log.d("GK", student.getId() + " FOR LOADING ATTENDANCE SHEET");
//                    studentListFromServer.add(student);
//                }
//                ClassRoom_activity.studentListFromAttendenceActivity = new ArrayList<>();
//                ClassRoom_activity.studentListFromAttendenceActivity = studentListFromServer;
//                forLoadingAttendenceList(studentListFromServer);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });


        //FOR LOADING NOTE OF THE CLASS
    }

    @Override
    protected void onResume() {
        super.onResume();

        //ADS
        if (mAdView != null) {
            mAdView.resume();
        }

        //FIREBASE
        MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName()+ClassRoom_activity.classitem.getSection()).child("Notes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Notes> NotesList=new ArrayList<Notes>();
                for(DataSnapshot NoteData:dataSnapshot.getChildren()){
                    Notes Notes=new Notes();
                    Notes=NoteData.getValue(Notes.class);
                    NotesList.add(Notes);
                }
                notesList=NotesList;
                noteListAdapter.clear();
                noteListAdapter.addAll(NotesList);
                if(NotesList.size()==0){
                    EmptyView.setVisibility(View.VISIBLE);
                }
                else{
                    EmptyView.setVisibility(View.GONE);
                }
                NOTES.setAdapter(noteListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    //    @Override
//    protected void onStart() {
//        super.onStart();
//       // LoadNotes();
//    }
//    private void LoadNotes(){
//        databaseHandler = new databaseHandler(this);
//
//        List<Notes> Notelist = new ArrayList<>();
//
//        Cursor cursor = databaseHandler.retrieveNotes();
//        Notes Notes;
//        cursor.moveToFirst();
//        if (cursor.moveToFirst()) {
//            do {
//                //add here the total info of student in to the STUDENT class_room from DATABASE
//                Notes = new Notes();
//                Notes.setheading(cursor.getString(0));
//                Notes.setContent(cursor.getString(1));
//                Notelist.add(Notes);
//            }while (cursor.moveToNext());
//        }
//
//        noteListAdapter.clear();
//        noteListAdapter.addAll(Notelist);
//
//    }

    @Override
    public void onItemClick(int position, View view) {
        noteAddActivity.start(this, noteListAdapter.getItem(position));
    }

    @Override
    public void onItemLongPressed(int position, View view) {
    }

    public static class createRequest extends DialogFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View v = inflater.inflate(R.layout.pick_period, null);
            final EditText Subject = (EditText) v.findViewById(R.id.periodID);
            builder.setView(v).setPositiveButton("উপস্থিতি নেয়া শুরু করুন", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    {
                        final DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);
                        int day = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth();
                        int year = datePicker.getYear() - 1900;
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
                        String formatedDate = simpleDateFormat.format(new Date(year, month, day));
                        String date = year + "-" + month + "-" + day;
                        String subject = Subject.getText().toString();
                        Intent launchinIntent = new Intent(ClassRoom_activity.activity, attendanceActivity.class);
                        launchinIntent.putExtra("DATE", formatedDate);
                        launchinIntent.putExtra("SUBJECT", subject);
                        launchinIntent.putExtra("class_room", ClassRoom_activity.classitem);
                        ClassRoom_activity.activity.startActivity(launchinIntent);
                    }
                }
            }).setNegativeButton("বাদ দিন", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            return builder.create();
        }
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }



    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        classitem = getIntent().getParcelableExtra(ClassRoom_activity.class.getSimpleName());
    }
}







