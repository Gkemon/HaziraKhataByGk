package com.Teachers.HaziraKhataByGk.SingleStudentAllInformation;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Teachers.HaziraKhataByGk.Attendance.AttendanceActivity;
import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.HelperClassess.ComparableDate;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsDateTime;
import com.Teachers.HaziraKhataByGk.Listener.CommonCallback;
import com.Teachers.HaziraKhataByGk.Listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.Model.AttendenceData;
import com.Teachers.HaziraKhataByGk.Model.ClassItem;
import com.Teachers.HaziraKhataByGk.Model.Student;
import com.Teachers.HaziraKhataByGk.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collections;

import static com.Teachers.HaziraKhataByGk.HelperClassess.UtilsDateTime.intMonthToStringMonthConvertor;

public class StudentAlIInfoShowActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, RecyclerItemClickListener {
    public static String time, yearWithDate, year, month, day;
    public Button studentPhoneNumber;
    public Button parentPhoneNumber;
    public ClassItem classItem;
    public RecyclerView rvDataWiseAttendence;
    public Student student;
    public Spinner spinnerMonth;
    public SingleStudentAttendanceAdapter mAdapter;
    public DatabaseReference dbRefSingleStudent;
    public ArrayList<AttendenceData> totalAttendenceDataArrayList;
    ProgressBar progressBar;
    String roll;
    LinearLayout emptyView;
    Activity activity;
    private TextView studentName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    void init() {


        setContentView(R.layout.student_info_show_activity);
        studentName = findViewById(R.id.studentName);
        studentPhoneNumber = findViewById(R.id.studentPhoneNumber);
        parentPhoneNumber = findViewById(R.id.parentPhoneNumber);
        spinnerMonth = findViewById(R.id.spinner_month);
        progressBar = findViewById(R.id.progressBarInSingleStudentActivity);
        spinnerMonth.setOnItemSelectedListener(this);
        emptyView = findViewById(R.id.toDoEmptyView);
        rvDataWiseAttendence = findViewById(R.id.rv_dateWishAttendance);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvDataWiseAttendence.setLayoutManager(layoutManager);
        mAdapter = new SingleStudentAttendanceAdapter(this, this);
        rvDataWiseAttendence.setAdapter(mAdapter);


        totalAttendenceDataArrayList = new ArrayList<>();
        roll = getIntent().getStringExtra("Roll");
        activity = this;

        classItem = (ClassItem) getIntent().getSerializableExtra("classItem");
        student = (Student) getIntent().getSerializableExtra("Student");

        dbRefSingleStudent = FirebaseCaller.getSingleStudentDbRef(student);


        loadDataFromServer();
        initializePhoneNumbers();
    }

    public void loadDataFromServer() {

        FirebaseCaller.getAttendanceDataForSingleStudent(classItem.getName(), classItem.getSection(),
                roll, new CommonCallback<ArrayList<AttendenceData>>() {
                    @Override
                    public void onFailure(String r) {
                        super.onFailure(r);
                        progressBar.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onSuccess(ArrayList<AttendenceData> response) {
                        super.onSuccess(response);

                        totalAttendenceDataArrayList = response;

                        //TODO: Sorting by date
                        Collections.sort(totalAttendenceDataArrayList, (o1, o2) -> {
                            ComparableDate comparableDate1, comparableDate2;
                            comparableDate1 = new ComparableDate();
                            comparableDate2 = new ComparableDate();


                            try {
                                comparableDate1.setDateTime(o1.getDate());
                                comparableDate2.setDateTime(o2.getDate());
                            } catch (Exception c) {
                            }

                            return comparableDate1.compareTo(comparableDate2);
                        });
                        //TODO: Sorting by date

                        Collections.reverse(totalAttendenceDataArrayList);

                        if (totalAttendenceDataArrayList.size() == 0)
                            emptyView.setVisibility(View.VISIBLE);


                        setUpHeader(totalAttendenceDataArrayList);
                        setUpAttandanceList(totalAttendenceDataArrayList);

                        spinnerMonth.setSelection(0);
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.single_student_attendence_delete_data, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.custom_delete_dialauge, null);
                dialogBuilder.setView(dialogView);
                final EditText edt = dialogView.findViewById(R.id.custom_delete_dialauge_text);
                dialogBuilder.setIcon(R.drawable.warnig_for_delete);
                dialogBuilder.setTitle("শিক্ষার্থীর সকল উপস্থিতির ডাটা ডিলেট করতে চায়?");
                dialogBuilder.setMessage("ডিলিট করার আগে ইংরেজীতে \"DELETE\" শব্দটি লিখুন।");
                dialogBuilder.setPositiveButton("ডিলিট করুন", (dialog, whichButton) -> {
                    if (edt.getText().toString().trim().equals("DELETE")) {


                        DatabaseReference databaseReference = FirebaseCaller.getSingleStudentAttendanceDbRef
                                (UtilsCommon.getCurrentClass(StudentAlIInfoShowActivity.
                                                this).getName(),
                                        UtilsCommon.getCurrentClass(
                                                StudentAlIInfoShowActivity.this).getSection(), roll);


                        if (databaseReference != null)
                            databaseReference.removeValue().addOnSuccessListener(aVoid -> {
                                loadDataFromServer();
                                emptyView.setVisibility(View.VISIBLE);
                            });


                    }
                });
                dialogBuilder.setNegativeButton("বাদ দিন", (dialog, whichButton) -> {
                });
                AlertDialog b = dialogBuilder.create();
                b.show();
                return true;
            case R.id.action_add_single_attendence:

                createDialogForAddAttendence();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setUpAttandanceList(ArrayList<AttendenceData> attandanceList) {

        rvDataWiseAttendence.setItemViewCacheSize(attandanceList.size());
        mAdapter.setAttendenceDataArrayList(attandanceList);

    }

    public void setUpHeader(ArrayList<AttendenceData> attendenceDataArrayList) {

        long totalAttendPersenten = 0;
        long totalClass = attendenceDataArrayList.size();


        //For Empty view
        if (totalClass == 0) {
            rvDataWiseAttendence.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            rvDataWiseAttendence.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        long attendClass = 0;


        for (AttendenceData attendenceData : attendenceDataArrayList) {
            if (attendenceData.getStatus()) attendClass++;
        }


        if (totalClass != 0)   //THIS IS FOR AVOID ARITHMETIC EXCEPTION
            totalAttendPersenten = (attendClass * 100) / totalClass;

        String charSequence;
        if (AttendanceActivity.classitemAttendence != null) {
            String charSequence1 = " শিক্ষার্থীর নাম: " + student.getStudentName() + " \n" +
                    " রোল :" + roll + "    ক্লাস :" + AttendanceActivity.classitemAttendence.getName() + "\n মোট ক্লাস:" + totalClass + "  উপস্থিতি :" + attendClass + "   শতকরা :" + totalAttendPersenten + "% ";
            charSequence = charSequence1;
        } else {
            String charSequence2 = " শিক্ষার্থীর নাম: " + student.getStudentName() + " \n" +
                    " রোল :" + roll + "    ক্লাস :" + classItem.getName() + "\n মোট ক্লাস:" + totalClass + "  উপস্থিতি :" + attendClass + "   শতকরা :" + totalAttendPersenten + "% ";
            charSequence = charSequence2;
        }
        studentName.setText(charSequence);

    }

    public void getDatewiseAttendenceList(final String date) {

        ArrayList<AttendenceData> attendenceDataArrayList = new ArrayList<>();
        UtilsCommon.debugLog("SIZE: " + totalAttendenceDataArrayList.size() + " " + date);

        if (date.isEmpty())
            attendenceDataArrayList = totalAttendenceDataArrayList;
        else
            for (int i = 0; i < totalAttendenceDataArrayList.size(); i++) {

                UtilsCommon.debugLog(totalAttendenceDataArrayList.get(i).getDate());
                if (totalAttendenceDataArrayList.get(i).getDate().contains(date))
                    attendenceDataArrayList.add(totalAttendenceDataArrayList.get(i));
            }

        setUpAttandanceList(attendenceDataArrayList);
        setUpHeader(attendenceDataArrayList);

    }

    public void createDialogForAddAttendence() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater1 = this.getLayoutInflater();
        final View v = inflater1.inflate(R.layout.dialoage_for_single_attendence_items, null);
        final EditText Subject = v.findViewById(R.id.periodID);
        builder.setView(v).setPositiveButton("উপস্থিত", (dialog, id) -> {
            {
                final DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);

                String formatedDate = UtilsDateTime.getSimpleDateText(datePicker.getYear(),
                        datePicker.getMonth(), datePicker.getDayOfMonth());

                String subject = Subject.getText().toString();

                //ADD ATTENDANCE
                AttendenceData attendenceData = new AttendenceData();
                attendenceData.setStatus(true);
                attendenceData.setSubject(subject);
                attendenceData.setDate(formatedDate);

                //Add toTime database
                FirebaseCaller.getFirebaseDatabase().child("Users").
                        child(FirebaseCaller.getUserID()).child("Class").
                        child(student.getStudentClass() + student.getStudentSection())
                        .child("Student").child(student.getId()).child("Attendance").push()
                        .setValue(attendenceData).addOnSuccessListener(StudentAlIInfoShowActivity.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loadDataFromServer();
                        dialog.dismiss();
                    }
                });


            }
        }).setNegativeButton("অনুপস্থিত", (dialog, id) -> {
            final DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);

            String formatedDate = UtilsDateTime.getSimpleDateText(datePicker.getYear(),
                    datePicker.getMonth(), datePicker.getDayOfMonth());

            String subject = Subject.getText().toString();
            // if (subject.equals("")) subject = "অনির্ধারিত";


            //ADD ATTENDANCE
            AttendenceData attendenceData = new AttendenceData();
            attendenceData.setStatus(false);
            attendenceData.setSubject(subject);
            attendenceData.setDate(formatedDate);


            //  Add toTime database
            FirebaseCaller.getFirebaseDatabase().child("Users").
                    child(FirebaseCaller.getUserID()).child("Class").
                    child(student.getStudentClass() + student.getStudentSection())
                    .child("Student").child(student.getId()).child("Attendance").push()
                    .setValue(attendenceData).addOnSuccessListener(StudentAlIInfoShowActivity.this, new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    loadDataFromServer();

                    dialog.dismiss();
                }
            });


        }).create().show();
    }

    public void initializePhoneNumbers() {
        studentPhoneNumber.setOnClickListener(arg0 -> {
            //ACTION_DIALER IS THE BEST SOLUTION TO MAKE CALL
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            //The 'tel:' prefix is required  otherwhise the following exception will be thrown: java.lang.IllegalStateException: Could not execute method of the activity.
            callIntent.setData(Uri.parse("tel:" + student.getPhone()));
            startActivity(callIntent);
        });
        parentPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //ACTION_DIALER IS THE BEST SOLUTION TO MAKE CALL
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                //The 'tel:' prefix is required  otherwhise the following exception will be thrown: java.lang.IllegalStateException: Could not execute method of the activity.
                callIntent.setData(Uri.parse("tel:" + student.getParentContact()));
                startActivity(callIntent);
            }
        });
    }

    public void createDialogForEdit(int pos1) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialoage_for_edit_single_attendence_items, null);
        final EditText Subject = v.findViewById(R.id.periodID);
        final CheckBox checkBox = v.findViewById(R.id.attMarker);//For attendance
        final CheckBox checkBox1 = v.findViewById(R.id.absentMarker);//For absent
        final DatePicker datePicker = v.findViewById(R.id.datePicker);


        final AttendenceData attendenceData;

        attendenceData = totalAttendenceDataArrayList.get(pos1);


        time = attendenceData.getDate();
        yearWithDate = time.substring(Math.max(time.length() - 8, 0));
        month = yearWithDate.substring(0, Math.min(yearWithDate.length(), 3));
        year = time.substring(Math.max(time.length() - 4, 0));

        if (time.substring(6, 6).equals(" ")) {
            day = time.substring(5, 5);
        } else {
            day = time.substring(5, 7);
        }

        int Month = UtilsDateTime.StringMonthToIntMonthConvertor(month);


        datePicker.updateDate(Integer.valueOf(year.trim()), Month, Integer.valueOf(day.trim()));

        Subject.setText(attendenceData.getSubject());


        if (attendenceData.getStatus()) {
            checkBox.setChecked(true);
            checkBox1.setChecked(false);
        } else {
            checkBox1.setChecked(true);
            checkBox.setChecked(false);
        }


        //Check and uncheck the check box vice versa
        checkBox.setOnClickListener(v1 -> checkBox1.setChecked(!checkBox.isChecked()));

        checkBox1.setOnClickListener(v12 -> checkBox.setChecked(!checkBox1.isChecked()));


        builder.setView(v).
                setPositiveButton("পরিবর্তন করুন", (dialog, id) -> {
                    {
                        String formatedDate = UtilsDateTime.getSimpleDateText(datePicker.getYear(),
                                datePicker.getMonth(), datePicker.getDayOfMonth());

                        String subject = Subject.getText().toString();


                        attendenceData.setStatus(checkBox.isChecked());
                        attendenceData.setSubject(subject);
                        attendenceData.setDate(formatedDate);


                        //set date
                        if (student != null) {
                            //REMOVE FIRST
                            Log.d("GK", "if " + roll + " " + student.getId());
                            //set subject
                            FirebaseCaller.getFirebaseDatabase().
                                    child("Users").
                                    child(FirebaseCaller.getUserID()).
                                    child("Class").
                                    child(student.getStudentClass() + student.getStudentSection()).
                                    child("Student").child(student.getId()).
                                    child("Attendance").
                                    child(attendenceData.getKey()).
                                    removeValue().addOnSuccessListener(StudentAlIInfoShowActivity.this, new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {


                                    FirebaseCaller.getFirebaseDatabase().child("Users")
                                            .child(FirebaseCaller.getUserID())
                                            .child("Class")
                                            .child(classItem.getName() + classItem.getSection())
                                            .child("Student")
                                            .child(student.getId())
                                            .child("Attendance")
                                            .child(attendenceData.getKey()).setValue(attendenceData).addOnSuccessListener(StudentAlIInfoShowActivity.this, new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            loadDataFromServer();
                                            dialog.dismiss();
                                        }
                                    });
                                    //set True or False;
                                }
                            });


                        }


                    }
                }).setNegativeButton("বাদ দিন", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.dismiss();
            }
        }).setNeutralButton("ডিলিট করুন", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int id) {


                if (student != null) {

                    FirebaseCaller.getFirebaseDatabase()
                            .child("Users")
                            .child(FirebaseCaller.getUserID())
                            .child("Class")
                            .child(classItem.getName() + classItem.getSection())
                            .child("Student")
                            .child(student.getId())
                            .child("Attendance")
                            .child(attendenceData.getKey()).removeValue().addOnSuccessListener(StudentAlIInfoShowActivity.this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            loadDataFromServer();

                            dialog.dismiss();

                        }
                    });

                }

            }
        }).create().show();


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        getDatewiseAttendenceList(intMonthToStringMonthConvertor(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        spinnerMonth.setSelection(0);
    }

    @Override
    public void onItemClick(int position, View view) {
        createDialogForEdit(position);
    }

    @Override
    public void onItemLongPressed(int position, View view) {

    }
}



