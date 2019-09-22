package com.Teachers.HaziraKhataByGk.StudentAdd;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.Teachers.HaziraKhataByGk.Constant.StaticData;
import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.HelperClassess.DialogUtils;
import com.Teachers.HaziraKhataByGk.HelperClassess.FirebasePhotoUploader;
import com.Teachers.HaziraKhataByGk.HelperClassess.PermissionActivity;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.Listener.CommonCallback;
import com.Teachers.HaziraKhataByGk.Model.AttendenceData;
import com.Teachers.HaziraKhataByGk.Model.ClassItem;
import com.Teachers.HaziraKhataByGk.Model.Student;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.SingleStudentAllInformation.StudentAlIInfoShowActivity;
import com.Teachers.HaziraKhataByGk.StudentListShowActivity;
import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.Teachers.HaziraKhataByGk.StudentAdd.StudentAddUtils.isRollExisted;
import static com.Teachers.HaziraKhataByGk.StudentListShowActivity.contactofSA;


public class StudentAddActivity extends PermissionActivity implements View.OnClickListener{

    private EditText personName;
    private EditText phone;
    private EditText rollNumber;
    private EditText parentName;
    private EditText parentPhoneNumber;
    private Button btnAdd, btnEdit, btnDelete,btnClassRecord;
    private ImageView imgProPic;
    private Student student;
    public  String previousId,currentId;
    public Activity activity;
    public Uri imageUri=null;

    @OnClick(R.id.btn_student_pro_pic_upload)
    public void uploadProPic(){

        if(checkHasPermission(RequestCode.PERMISSION_MULTIPLE,new CommonCallback() {
            @Override
            public void onSuccess() {
                uploadProPic();
            }
        } ,new String[]{Manifest.permission
                .READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
            if (validateRoll() && validateStudentName()) {
                Intent intent = CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setActivityTitle("Profile Pic")
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .setCropMenuCropButtonTitle("Done")
                        .setRequestedSize(150, 120)
                        .setFixAspectRatio(true)
                        .setAspectRatio(6, 6)
                        .setCropMenuCropButtonIcon(R.drawable.check)
                        .getIntent(activity);
                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
            } else {
                DialogUtils.showInfoAlertDialog("সতর্কবানী", "ছবি আপলোডের পূর্বে দয়া করে নাম এবং রোল ইনপুট দিন");
            }
        }
        else {
            DialogUtils.showInfoAlertDialog("Permission","You didn't allow permission for this");
        }


    }

    @OnClick(R.id.btn_birth_date_select)
    public void setUpBirthdate(){

    }


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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();

                if(imageUri==null)
                {
                    UtilsCommon.showToast("Error in image selection.");
                    UtilsCommon.debugLog("URL is null.");
                    return;
                }

                Glide.with(this)
                        .load(imageUri)
                        .placeholder(R.drawable.ic_profile_pic)
                        .error(R.drawable.ic_profile_pic)
                        .into(imgProPic);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                UtilsCommon.debugLog("Error in Photo URL: "+error.getLocalizedMessage());
            }
        }
    }


    public void initView(){
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
        imgProPic=findViewById(R.id.img_pro_pic);


        btnAdd.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClassRecord.setOnClickListener(this);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_student_act);
        activity=this;
        ButterKnife.bind(this);
        initView();
        student=(Student)getIntent().getSerializableExtra(StudentAddActivity.class.getSimpleName());


        attendenceDataListBeforeEdit=new ArrayList<>();

        if(student != null){
            btnAdd.setVisibility(View.GONE);
            personName.setText(student.getStudentName().trim());
            rollNumber.setText(student.getId().trim());
            parentName.setText(student.getParentName().trim());
            parentPhoneNumber.setText(student.getParentContact().trim());
            phone.setText(student.getPhone().trim());
            previousId=student.getId().trim();
            Glide.with(this)
                    .load(student.getImageUrl())
                    .placeholder(R.drawable.ic_profile_pic)
                    .error(R.drawable.ic_profile_pic)
                    .into(imgProPic);
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
            student.setUuid(UUID.randomUUID().toString());



            if (student.getId() != null) {
                if(submitForm()){

                    //CHECK THAT THE ITEM IS UNIQUE
                    if(isRollExisted(StudentListShowActivity.studentList,student.getId()))
                        return;

                    ClassItem classItem=new ClassItem();

                    if(StaticData.currentClass!=null) {
                        classItem=StaticData.currentClass;
                    }
                    else if(contactofSA!=null){
                        classItem=contactofSA;
                    }


                    FirebaseCaller.setStudentToServer(classItem, student, new CommonCallback() {
                        @Override
                        public void onFailure(String r) {
                            UtilsCommon.debugLog("Error in photo uploading - "+r);
                        }

                        @Override
                        public void onSuccess() {


                            if(imageUri!=null&&UtilsCommon.isValideString(imageUri.toString()))
                            FirebasePhotoUploader.getBuilder()
                                    .setActivity(StudentAddActivity.this)
                                    .setFileName(student.getUuid())
                                    .setUri(imageUri)
                                    .setServerStorageFolderName("StudentProfilePicture")
                                    .setTargetDatabaseRef(FirebaseCaller.getSingleStudentDbRef(student).child("imageUrl"))
                                    .setCallBack(new CommonCallback<Uri>() {
                                        @Override
                                        public void onFailure(String r) {
                                            UtilsCommon.showToast("Error in photo uploading.");
                                            UtilsCommon.debugLog("Error in photo uploading - "+r);
                                        }

                                        @Override
                                        public void onSuccess(Uri response) {
                                            finish();
                                            UtilsCommon.showToast("Photo is uploaded");
                                        }
                                    }).build();
                            else {
                                Toast.makeText(StudentAddActivity.this, "নতুন শিক্ষার্থীর তথ্য ডাটাবেজে যুক্ত হয়েছে ,ধন্যবাদ।", Toast.LENGTH_SHORT).show();
                                finish();
                                UtilsCommon.debugLog("Student Image URI is null");
                            }
                        }
                    });


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

                FirebaseCaller.getAttendanceDataForSingleStudent(className, section, previousId,
                        new CommonCallback<ArrayList<AttendenceData>>() {
                    @Override
                    public void onFailure(String r) {

                    }

                    @Override
                    public void onSuccess(ArrayList<AttendenceData> attendenceDataList) {


                        attendenceDataListBeforeEdit=attendenceDataList;

                        //Then first reinstall previous Student data;
                        FirebaseCaller.setStudentToServer(contactofSA,student);


                        //Then add attendance list of the specific Student before edit.This is an operation fromTime Student act activity;
                        if (StudentAddActivity.attendenceDataListBeforeEdit != null) {
                            for (int i = 0; i < StudentAddActivity.attendenceDataListBeforeEdit.size(); i++) {

                                FirebaseCaller.
                                        setStudentAttendanceToServer(contactofSA,currentId,
                                        StudentAddActivity.attendenceDataListBeforeEdit.get(i));
                            }
                            currentId=null;
                        }
                    }
                });


                //Then remove the old Student data
                FirebaseCaller.removeStudentToServer(contactofSA,previousId);

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
                            (dialog, which) -> dialog.dismiss());
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
                    startActivity(launchinIntent);
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
        else return !isRollExisted(StudentListShowActivity.studentList, rollNumber.getText().toString().trim());
    }

    private void requestFocus(View view) {
        view.requestFocus();
    }

    @Override
    protected void onPermissionBlocked(String permission) {

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
        dialogBuilder.setPositiveButton("ডিলিট করুন", (dialog, whichButton) -> {

                FirebaseCaller.removeStudentToServer(contactofSA,student.getId());

                Toast.makeText(StudentAddActivity.this,"এই শিক্ষার্থীর যাবতীয় সব তথ্য ডাটাবেজ থেকে ডিলেট হয়েছে,ধন্যবাদ।",Toast.LENGTH_LONG).show();
                previousId=null;
                finish();

        });
        dialogBuilder.setNegativeButton("বাদ দিন", (dialog, whichButton) -> {
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

}