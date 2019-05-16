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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.Teachers.HaziraKhataByGk.Adapter.NoteListAdapter;
import com.Teachers.HaziraKhataByGk.AddEditClass.ClassAddActivity;
import com.Teachers.HaziraKhataByGk.Constant.ContantsForGlobal;
import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.Listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.Model.AttendenceData;
import com.Teachers.HaziraKhataByGk.Model.ClassIitem;
import com.Teachers.HaziraKhataByGk.Model.Notes;
import com.Teachers.HaziraKhataByGk.Model.student;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ClassRoomActivity extends AppCompatActivity  implements RecyclerItemClickListener {

    public static void start(Context context, ClassIitem classitem) {
        Intent intent = new Intent(context, ClassRoomActivity.class);
        intent.putExtra(ClassRoomActivity.class.getSimpleName(), classitem);
        context.startActivity(intent);
    }

    private RecyclerView NOTES;
    private FloatingActionButton btnAdd;
    public  Activity activity;
    private LinearLayoutManager linearLayoutManager;
    private NoteListAdapter noteListAdapter;
    private Context context;
    private LinearLayout linearLayoutForPresent, linearLayoutForStudentProfile;
    public static ClassIitem classitem;
    public static List<Notes> notesList;
    public Button feesButton,DailyAndMontlyRecord,marksheetButton;
    View EmptyView;

    public static String FLAG_OF_CLASSROOM_ACTIVITY="class_room";

    //for class activity to class record
    public static List<student> studentListFromAttendenceActivity;
    public static List<student> studentListForPrintActiviyFromAttendenceActivity;
    public static HashMap<String,ArrayList<AttendenceData>> perStudentTotalAttendenceData;//for creating month wise data sheet;
    public static ArrayList<AttendenceData> attendenceDataArrayListForPerStudent;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HideNotifiationBar();

        setContentView(R.layout.activity_class_room);

        Initialize();

    }

    @Override
    protected void onResume() {
        super.onResume();


        LoadNotes();
        LoadData();
        ClickListenerForDailyAndMonthlyRecord();

    }

    @Override
    public void onItemClick(int position, View view) {
        NoteAddActivity.start(this, noteListAdapter.getItem(position));
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
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(UtilsCommon.DateFormate);
                        String formatedDate = simpleDateFormat.format(new Date(year, month, day));
                        String date = year + "-" + month + "-" + day;
                        String subject = Subject.getText().toString();
                        Intent launchinIntent = new Intent( v.getContext(), AttendanceActivity.class);
                        launchinIntent.putExtra("DATE", formatedDate);
                        launchinIntent.putExtra("SUBJECT", subject);
                        launchinIntent.putExtra(FLAG_OF_CLASSROOM_ACTIVITY, ClassRoomActivity.classitem);
                        v.getContext().startActivity(launchinIntent);
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
    protected void onStart() {

        classitem = getIntent().getParcelableExtra(ClassRoomActivity.class.getSimpleName());
        super.onStart();

    }

    void LoadNotes(){
        FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName()+ ClassRoomActivity.classitem.getSection()).child("Notes").addListenerForSingleValueEvent(new ValueEventListener() {
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

    void ClickListenerForDailyAndMonthlyRecord(){
        DailyAndMontlyRecord=(Button)findViewById(R.id.AttendenceRecordButtom);
        DailyAndMontlyRecord.setVisibility(View.GONE);
        DailyAndMontlyRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(activity);

                LayoutInflater inflater = activity.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.custom_delete_dialauge, null);
                dialogBuilder.setView(dialogView);



                // final EditText edt = (EditText) dialogView.findViewById(R.id.custom_delete_dialauge_text);
                //  dialogBuilder.setIcon(R.drawable.warnig_for_delete);
                dialogBuilder.setTitle("আপনি কি আসলেই ক্লাসে সকল তথ্য ডিলিট করতে চান?");
                dialogBuilder.setMessage("ডিলিট করার আগে ইংরেজীতে \"DELETE\" শব্দটি লিখুন।");

                dialogBuilder.setPositiveButton("দৈনিক উপস্থিতির রেকর্ড", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
                dialogBuilder.setNegativeButton("মাসিক উপস্থিতির রেকর্ড", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent=new Intent(ClassRoomActivity.this, PrinterActivity.class);
                        startActivity(intent);
                    }
                });
                android.support.v7.app.AlertDialog b = dialogBuilder.create();
                b.show();
            }
        });
    }
    void Initialize(){
        activity = this;
        context = getApplicationContext();
        NOTES = (RecyclerView) findViewById(R.id.notes);
        EmptyView = findViewById(R.id.toDoEmptyView);
        notesList=new ArrayList<Notes>();
        btnAdd = (FloatingActionButton) findViewById(R.id.fabForNotes);
        feesButton=(Button)findViewById(R.id.feesButton);
        feesButton.setVisibility(View.GONE);

        marksheetButton = (Button) findViewById(R.id.marksheet);


        studentListFromAttendenceActivity=new ArrayList<>();
        studentListForPrintActiviyFromAttendenceActivity=new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(this);
        noteListAdapter = new NoteListAdapter(this);
        noteListAdapter.setOnItemClickListener(this);
        NOTES.setLayoutManager(linearLayoutManager);
        NOTES.setAdapter(noteListAdapter);

        //FOR ADD CLICK LISTENER IN ATTENDENCE AND STUDENT PROFILE ACTIVITY
        linearLayoutForStudentProfile = (LinearLayout) findViewById(R.id.CardlayoutForStudentProfile);
        linearLayoutForPresent = (LinearLayout) findViewById(R.id.CardlayoutForAttendence);

        //FOR GETTING THE CURRENT CLASS
        classitem = getIntent().getParcelableExtra(ClassRoomActivity.class.getSimpleName());

        feesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ClassRoomActivity.this,FeesAcitvity.class);
                activity.startActivity(intent);
            }
        });

        marksheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ClassRoomActivity.this,MarkSheetHomeActivity.class);
                intent.putExtra(ContantsForGlobal.CLASS_NAME,classitem.getName());
                intent.putExtra(ContantsForGlobal.CLASS_SECTION,classitem.getSection());
                activity.startActivity(intent);
            }
        });


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
                    Intent launchinIntent = new Intent(activity, StudentListShowActivity.class);
                    launchinIntent.putExtra(ClassRoomActivity.class.getSimpleName(), classitem);
                    activity.startActivity(launchinIntent);
                }
            });
        }

        //FAB TO NOTE ACTIVITY
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteAddActivity.start(ClassRoomActivity.this);
            }
        });

    }
    void  HideNotifiationBar(){
        //HIDING NOTIFICATION BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    void LoadData(){

        FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(ClassRoomActivity.classitem.getName() + ClassRoomActivity.classitem.getSection()).child("Student").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                perStudentTotalAttendenceData=new HashMap<String, ArrayList<AttendenceData>>();
                studentListFromAttendenceActivity.clear();

                for (DataSnapshot StudentData : dataSnapshot.getChildren()){
                    student student;
                    student = StudentData.getValue(student.class);
                    studentListFromAttendenceActivity.add(student);
                }

                studentListForPrintActiviyFromAttendenceActivity=studentListFromAttendenceActivity;

                student student;
                for (int i = 0; i < studentListFromAttendenceActivity.size(); i++) {
                    student = studentListFromAttendenceActivity.get(i);

                    AttendenceData attendenceData = null;
                    attendenceDataArrayListForPerStudent = new ArrayList<AttendenceData>();


                    for (DataSnapshot dataSnapshot1 : dataSnapshot.child(student.getId()).child("Attendance").getChildren()) {
                        attendenceData = dataSnapshot1.getValue(AttendenceData.class);
                        attendenceDataArrayListForPerStudent.add(attendenceData);
                    }

                    Log.d("GK", attendenceDataArrayListForPerStudent.size() + " attendenceDataArrayListForPerStudent.size for roll " + student.getId());


                    perStudentTotalAttendenceData.put(student.getId(), attendenceDataArrayListForPerStudent);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}







