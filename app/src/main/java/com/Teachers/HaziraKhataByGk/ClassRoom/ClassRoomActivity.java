package com.Teachers.HaziraKhataByGk.ClassRoom;

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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.Teachers.HaziraKhataByGk.Adapter.NoteListAdapter;
import com.Teachers.HaziraKhataByGk.Attendance.AttendanceActivity;
import com.Teachers.HaziraKhataByGk.Constant.Constant;
import com.Teachers.HaziraKhataByGk.Constant.StaticData;
import com.Teachers.HaziraKhataByGk.FeesAcitvity;
import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.HelperClassess.GlobalContext;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.Listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.MarkSheetHomeActivity;
import com.Teachers.HaziraKhataByGk.Model.AttendenceData;
import com.Teachers.HaziraKhataByGk.Model.ClassItem;
import com.Teachers.HaziraKhataByGk.Model.Notes;
import com.Teachers.HaziraKhataByGk.Model.Student;
import com.Teachers.HaziraKhataByGk.NoteAddActivity;
import com.Teachers.HaziraKhataByGk.PrinterActivity;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.StudentListShowActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon.DateFormate;

public class ClassRoomActivity extends AppCompatActivity  implements RecyclerItemClickListener {

    public static void start(Context context, ClassItem classitem) {
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
    public  ClassItem classitem;
    public static List<Notes> notesList;
    public Button feesButton,DailyAndMontlyRecord,marksheetButton;
    View emptyView;

    public static String FLAG_OF_CLASSROOM_ACTIVITY="class_room";

    //for class activity toTime class record
    public static List<Student> studentListFromAttendenceActivity;
    public static List<Student> studentListForPrintActiviyFromAttendenceActivity;
    public static HashMap<String,ArrayList<AttendenceData>> perStudentTotalAttendenceData;//for creating month wise data sheet;
    public static ArrayList<AttendenceData> attendenceDataArrayListForPerStudent;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_class_room);
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();

        loadNotes();
        loadData();
        clickListenerForDailyAndMonthlyRecord();

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
            final EditText Subject = v.findViewById(R.id.periodID);
            builder.setView(v).setPositiveButton("উপস্থিতি নেয়া শুরু করুন", (dialog, id) -> {
                {
                    final DatePicker datePicker = v.findViewById(R.id.datePicker);
                    int day = datePicker.getDayOfMonth();
                    int month = datePicker.getMonth();
                    int year = datePicker.getYear() - 1900;
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormate);
                    String formatedDate = simpleDateFormat.format(new Date(year, month, day));
                    String subject = Subject.getText().toString();
                    Intent launchinIntent = new Intent( v.getContext(), AttendanceActivity.class);
                    launchinIntent.putExtra("DATE", formatedDate);
                    launchinIntent.putExtra("SUBJECT", subject);
                    launchinIntent.putExtra(FLAG_OF_CLASSROOM_ACTIVITY,
                            UtilsCommon.getCurrentClass(v.getContext()));
                    v.getContext().startActivity(launchinIntent);
                }
            }).setNegativeButton("বাদ দিন", (dialog, id) -> dialog.dismiss());
            return builder.create();
        }
    }


    void loadNotes(){
        FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).
                child("Class").child(classitem.getName()+ classitem.getSection()).
                child("Notes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Notes> NotesList=new ArrayList<>();
                for(DataSnapshot NoteData:dataSnapshot.getChildren()){
                    Notes Notes=new Notes();
                    Notes=NoteData.getValue(Notes.class);
                    NotesList.add(Notes);
                }
                notesList=NotesList;
                noteListAdapter.clear();
                noteListAdapter.addAll(NotesList);
                if(NotesList.size()==0){
                    emptyView.setVisibility(View.VISIBLE);
                }
                else{
                    emptyView.setVisibility(View.GONE);
                }
                NOTES.setAdapter(noteListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    void clickListenerForDailyAndMonthlyRecord(){
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
    void initView(){
        activity = this;
        context = getApplicationContext();
        NOTES =  findViewById(R.id.notes);
        emptyView = findViewById(R.id.toDoEmptyView);
        notesList=new ArrayList<>();
        btnAdd =  findViewById(R.id.fabForNotes);
        feesButton=findViewById(R.id.feesButton);
        feesButton.setVisibility(View.GONE);

        marksheetButton =findViewById(R.id.marksheet);


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
        classitem = (ClassItem)getIntent().getSerializableExtra(ClassRoomActivity.class.getSimpleName());
        UtilsCommon.setCurrentClass(classitem,this);



        feesButton.setOnClickListener(view -> {
            Intent intent=new Intent(ClassRoomActivity.this, FeesAcitvity.class);
            activity.startActivity(intent);
        });

        marksheetButton.setOnClickListener(view -> {
            Intent intent=new Intent(ClassRoomActivity.this, MarkSheetHomeActivity.class);
            intent.putExtra(Constant.CLASS_NAME,classitem.getName());
            intent.putExtra(Constant.CLASS_SECTION,classitem.getSection());
            activity.startActivity(intent);
        });


        if (linearLayoutForPresent != null) {
            linearLayoutForPresent.setOnClickListener(v -> {
                FragmentManager fm = activity.getFragmentManager();
                createRequest request = new createRequest();
                request.show(fm, "Select");
            });
        }

        //FOR STUDENT PROFILE
        if (linearLayoutForStudentProfile != null) {
            linearLayoutForStudentProfile.setOnClickListener(v -> {
                Intent launchinIntent = new Intent(activity, StudentListShowActivity.class);
                launchinIntent.putExtra(ClassRoomActivity.class.getSimpleName(), classitem);
                activity.startActivity(launchinIntent);
            });
        }

        //FAB TO NOTE ACTIVITY
        btnAdd.setOnClickListener(v -> NoteAddActivity.start(ClassRoomActivity.this));

    }


    void loadData(){

        FirebaseCaller.getFirebaseDatabase().child("Users");
        FirebaseCaller.getFirebaseDatabase().child(FirebaseCaller.getUserID());
        FirebaseCaller.getFirebaseDatabase().child("Class");
        FirebaseCaller.getFirebaseDatabase().child(classitem.getName()+classitem.getSection());
        FirebaseCaller.getFirebaseDatabase().child("Student");
        FirebaseCaller.getFirebaseDatabase().addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                perStudentTotalAttendenceData = new HashMap<>();
                studentListFromAttendenceActivity.clear();

                for (DataSnapshot studentData : dataSnapshot.getChildren()) {
                    Student student;
                    student = studentData.getValue(Student.class);
                    studentListFromAttendenceActivity.add(student);
                }

                studentListForPrintActiviyFromAttendenceActivity = studentListFromAttendenceActivity;

                Student student;
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







