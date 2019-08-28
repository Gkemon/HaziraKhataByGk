package com.Teachers.HaziraKhataByGk;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Teachers.HaziraKhataByGk.Constant.Constant;
import com.Teachers.HaziraKhataByGk.Constant.StaticData;
import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.Model.AttendenceData;
import com.Teachers.HaziraKhataByGk.Model.Student;
import com.Teachers.HaziraKhataByGk.SingleStudentAllInformation.StudentAlIInfoShowActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.Teachers.HaziraKhataByGk.StudentListShowActivity.contactofSA;


public class StudentAddActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText personName;
    private EditText phone;
    private EditText rollNumber;
    private EditText parentName;
    private EditText parentPhoneNumber;
    private Button btnAdd, btnEdit, btnDelete,btnClassRecord;
    private Student student;
    public   String previousId,currentId;
    public  static Activity activity;



    public static List<AttendenceData> attendenceDataListBeforeEdit;
    public static void start(Context context){
        Intent intent = new Intent(context, StudentAddActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, Student student){
        Intent intent = new Intent(context, StudentAddActivity.class);
        intent.putExtra(StudentAddActivity.class.getSimpleName(), student);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_student_act);
        activity=this;


        personName = findViewById(R.id.personTextFromStudentAct);
        rollNumber= findViewById(R.id.RollNumberFromStudentAct);
        //ADD TEXT CHANGE LISTENER
        personName.addTextChangedListener(new MyTextWatcher(personName));
        rollNumber.addTextChangedListener(new MyTextWatcher(rollNumber));

        phone = findViewById(R.id.phoneNumbersFromStudentAct);
        parentName=findViewById(R.id.personParentTextFromStudentAct);
        parentPhoneNumber=findViewById(R.id.ParentsphoneNumbersFromStudentAct);


        btnAdd = findViewById(R.id.btnAddFromStudentAct);
        btnEdit =findViewById(R.id.btnEditFromStudentAct);
        btnDelete =findViewById(R.id.btnDeleteFromStudentAct);
        btnClassRecord=findViewById(R.id.classRecord);


        btnAdd.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClassRecord.setOnClickListener(this);


        student=getIntent().getParcelableExtra(StudentAddActivity.class.getSimpleName());


        attendenceDataListBeforeEdit=new ArrayList<>();

        if(student != null){
            btnAdd.setVisibility(View.GONE);
            personName.setText(student.getStudentName().trim());
            rollNumber.setText(student.getId().trim());
            parentName.setText(student.getParentName().trim());
            parentPhoneNumber.setText(student.getParentContact().trim());
            phone.setText(student.getPhone().trim());
            previousId=student.getId().trim();
        }else{
            btnClassRecord.setVisibility(View.GONE);
            btnEdit.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        if(v == btnAdd) {

            student = new Student();
            student.setStudentName(personName.getText().toString().trim());
            student.setId(rollNumber.getText().toString().trim());
            student.setPhone(phone.getText().toString().trim());
            student.setParentName(parentName.getText().toString().trim());
            student.setParentContact(parentPhoneNumber.getText().toString().trim());
            student.setStudentClass(StaticData.currentClass.getName());
            student.setStudentSection(StaticData.currentClass.getSection());


            //CHECK THAT THE ITEM IS UNIQUE
            for (int i = 0; i < StudentListShowActivity.studentList.size(); i++) {
                if (StudentListShowActivity.studentList.get(i).getId().equals(student.getId())){
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("সতর্কীকরণ");
                    alertDialog.setIcon(R.drawable.warning_for_add);
                    alertDialog.setMessage("এই একই রোল ইতিমধ্যে  এই ক্লাসের  ডাটাবেজে রয়েছে।নতুন রোল ইনপুট দিন,ধন্যবাদ।");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "ওকে",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    return;
                }
            }

            if (student.getId() != null) {
                if(submitForm()){


                    String section="";
                    String className="";

                    if(StaticData.currentClass!=null) {
                        section =StaticData.currentClass.getSection();
                        className = StaticData.currentClass.getName();
                    }
                    else if(contactofSA!=null){
                        section=contactofSA.getSection();
                        className=contactofSA.getName();
                    }


                    FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(className+section).child("Student").child(student.getId()).setValue(student);
                Toast.makeText(this, "নতুন শিক্ষার্থীর তথ্য ডাটাবেজে যুক্ত হয়েছে ,ধন্যবাদ।", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        }
        else if(v == btnEdit){
            student.setStudentName(personName.getText().toString().trim());
            student.setId(rollNumber.getText().toString().trim());
            student.setPhone(phone.getText().toString().trim());
            student.setParentName(parentName.getText().toString().trim());
            student.setParentContact(parentPhoneNumber.getText().toString().trim());
            currentId=rollNumber.getText().toString().trim();


            //CHECK THAT THE ITEM IS UNIQUE
            for (int i = 0; i < StudentListShowActivity.studentList.size(); i++) {
                if (StudentListShowActivity.studentList.get(i).getId().equals(student.getId())&&!previousId.equals(student.getId()))
                {AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("সতর্কীকরণ");
                alertDialog.setIcon(R.drawable.warning_for_add);
                alertDialog.setMessage("এই একই রোল ইতিমধ্যে এই ক্লাসের ডাটাবেজে রয়েছে।নতুন রোল ইনপুট দিন,ধন্যবাদ।");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "ওকে",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {dialog.dismiss();
                                }
                            });
                alertDialog.show();
                return;
                }
            }

            //FOR VALIDATION
            if(submitForm()){
                //First GETTING ITS ATTENDANCE DATA

                String section="";
                String className="";

                if(StaticData.currentClass!=null) {
                    section =StaticData.currentClass.getSection();
                    className = StaticData.currentClass.getName();
                }
                else if(contactofSA!=null){
                    section=contactofSA.getSection();
                    className=contactofSA.getName();
                }

                FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(className+section).child("Student").child(previousId).child("Attendance").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                      final  ArrayList<AttendenceData> attendenceDatalistInFB=new ArrayList<AttendenceData>();


                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            AttendenceData attendenceData;
                            attendenceData=dataSnapshot1.getValue(AttendenceData.class);
                            attendenceDatalistInFB.add(attendenceData);
                        }

                        attendenceDataListBeforeEdit=attendenceDatalistInFB;

                        //Then first reinstall previous Student data;
                        FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(contactofSA.getName()+ contactofSA.getSection()).child("Student").child(student.getId()).setValue(student);


                        //Then add attendance list of the specific Student before edit.This is an operation fromTime Student act activity;
                        if (StudentAddActivity.attendenceDataListBeforeEdit != null) {
                            for (int i = 0; i < StudentAddActivity.attendenceDataListBeforeEdit.size(); i++) {
                                FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(contactofSA.getName() + contactofSA.getSection()).child("Student").child(currentId).child("Attendance").push().setValue(StudentAddActivity.attendenceDataListBeforeEdit.get(i));
                            }
                            currentId=null;

                        }
                        else {

                        }


                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });



                //Then remove the old Student data
                FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(contactofSA.getName()+ contactofSA.getSection()).child("Student").child(previousId).removeValue();

                Toast.makeText(this,"শিক্ষার্থীর নতুন ডাটা এডিট হয়েছে,ধন্যবাদ ",Toast.LENGTH_SHORT).show();
                    previousId=null;

                finish();
            }

        }else if(v == btnDelete) {
            student.setStudentName(personName.getText().toString());
            student.setId(rollNumber.getText().toString());
            student.setPhone(phone.getText().toString());
            student.setParentName(parentName.getText().toString());
            student.setParentContact(parentPhoneNumber.getText().toString());

            //FOR AVOID SQL INJECTION
            for(int i = 0; i< StudentListShowActivity.studentList.size(); i++){
                if(StudentListShowActivity.studentList.get(i).getId().equals(student.getId())&&!(previousId.equals(rollNumber.getText().toString()))){
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("সতর্কীকরণ");
                    alertDialog.setIcon(R.drawable.warnig_for_delete);
                    alertDialog.setMessage("আপনি শিক্ষার্থীর নাম অংশ পরিবর্তন করে যে নাম ইনপুট করেছেন তা অন্য আরেকটি শিক্ষার্থীর ডাটাবেজের নামের সাথে মিলে যায় ।তাই আপনাকে সেই শিক্ষার্থীর ডাটা ডিলেট করতে হলে অবশ্যই সেই শিক্ষার্থীর ডাটাবেজে যেতে হবে।ধন্যবাদ ");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"ওকে",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    return;
                }
            }


            deleteDialogForStudent();
        }
        else if(v==btnClassRecord){
                    Intent launchinIntent = new Intent(this, StudentAlIInfoShowActivity.class);
                    String roll = previousId;
                    StaticData.currentClassName=contactofSA.getName();
                    StaticData.currentSection=contactofSA.getSection();
                    StaticData.currentRoll=roll;
                    launchinIntent.putExtra("Student",student);
                    launchinIntent.putExtra("Roll", roll);
                    launchinIntent.putExtra("classItem", contactofSA);
                    StudentAddActivity.activity.startActivity(launchinIntent);
        }
    }

    private boolean submitForm() {
        if (!validateStudentName()) {
            Toast.makeText(getApplicationContext(), "দয়া করে শিক্ষার্থীর নাম অংশের ভুল সংশোধণ করুন,ধন্যবাদ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!validateRoll()){
            Toast.makeText(getApplicationContext(), "দয়া করে শিক্ষার্থীর রোল অংশের ভুল সংশোধণ করুন,ধন্যবাদ", Toast.LENGTH_SHORT).show();
            return false;
        }

        Toast.makeText(getApplicationContext(), "সঠিক তথ্য ইনপুট দেয়ার জন্য আপনাকে ধন্যবাদ", Toast.LENGTH_SHORT).show();
        return true;
    }
    private boolean validateStudentName() {
        if (personName.getText().toString().trim().isEmpty()) {

            personName.setError(getString(R.string.error_massege_for_input));
            requestFocus(personName);
            return false;
        }
        return true;
    }
    private boolean validateRoll() {
        if (rollNumber.getText().toString().trim().isEmpty()) {
            rollNumber.setError(getString(R.string.error_massege_for_input));
            requestFocus(rollNumber);
            return false;
        }
        return true;
    }

    private void requestFocus(View view) {
        view.requestFocus();
    }
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {

                case R.id.personTextFromStudentAct:
                    validateStudentName();
                    break;
                case R.id.RollNumberFromStudentAct:
                    validateRoll();
                    break;
            }
        }
    }


    public void deleteDialogForStudent() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_delete_dialauge, null);
        dialogBuilder.setView(dialogView);
        final EditText edt = (EditText) dialogView.findViewById(R.id.custom_delete_dialauge_text);
        dialogBuilder.setTitle("আপনি কি আসলেই এই শিক্ষার্থীর সকল তথ্য ডিলিট করতে চান?");
        dialogBuilder.setMessage("ডিলিট করার আগে ইংরেজীতে \"DELETE\" শব্দটি লিখুন।");
        dialogBuilder.setPositiveButton("ডিলিট করুন", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(edt.getText().toString().trim().equals("DELETE")){

                    FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(contactofSA.getName()+ contactofSA.getSection()).child("Student").child(student.getId()).removeValue();

                    Toast.makeText(StudentAddActivity.this,"এই শিক্ষার্থীর যাবতীয় সব তথ্য ডাটাবেজ থেকে ডিলেট হয়েছে,ধন্যবাদ।",Toast.LENGTH_LONG).show();
                    previousId=null;
                    finish();
                }
            }
        });
        dialogBuilder.setNegativeButton("বাদ দিন", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

}
