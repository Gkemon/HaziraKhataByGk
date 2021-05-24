package com.emon.haziraKhata.StudentAdd;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.emon.haziraKhata.Constant.Constant;
import com.emon.haziraKhata.Firebase.FirebaseCaller;
import com.emon.haziraKhata.HelperClasses.DatePickerBuilder;
import com.emon.haziraKhata.HelperClasses.DialogUtils;
import com.emon.haziraKhata.HelperClasses.FirebasePhotoUploader;
import com.emon.haziraKhata.HelperClasses.PermissionActivity;
import com.emon.haziraKhata.HelperClasses.UtilsCommon;
import com.emon.haziraKhata.HelperClasses.ViewUtils.UtilsView;
import com.emon.haziraKhata.Listener.CommonCallback;
import com.emon.haziraKhata.Model.AttendenceData;
import com.emon.haziraKhata.Model.ClassItem;
import com.emon.haziraKhata.Model.Student;
import com.emon.haziraKhata.R;
import com.emon.haziraKhata.SingleStudentAllInformation.StudentAlIInfoShowActivity;
import com.emon.haziraKhata.note.NoteShowingDialog;
import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.emon.haziraKhata.StudentAdd.StudentAddUtils.isRollExisted;


public class StudentAddActivity extends PermissionActivity implements View.OnClickListener {

    public static List<AttendenceData> attendenceDataListBeforeEdit;
    @BindView(R.id.person2ParentTextFromStudentAct)
    public EditText etParent2Name;
    @BindView(R.id.Parents2phoneNumbersFromStudentAct)
    public EditText etParent2Phone;
    @BindView(R.id.et_birth_certificate)
    public EditText etBirthCertificate;
    @BindView(R.id.rb_male)
    public RadioButton rbMale;
    @BindView(R.id.rb_female)
    public RadioButton rbFemale;
    @BindView(R.id.btn_birth_date_select)
    public Button btnBirthDay;
    public String previousId, currentId;
    public Activity activity;
    public Uri imageUri = null;
    private EditText etPersonName;
    private EditText etPhone;
    private EditText etRollNumber;
    private EditText etParentName;
    private EditText etParentPhoneNumber;
    private Button btnAdd, btnEdit, btnDelete, btnClassRecord,btnNotes;
    private ImageView imgProPic;
    private Student student;
    private String birthDay;
    private ArrayList<Student> studentList;

    public static void start(Context context, ClassItem classItem, ArrayList<Student> studentList) {
        Intent intent = new Intent(context, StudentAddActivity.class);
        intent.putExtra("studentList", studentList);
        intent.putExtra(Constant.CLASS, classItem);
        context.startActivity(intent);
    }

    public static void start(Context context, Student student, ArrayList<Student> studentList) {
        Intent intent = new Intent(context, StudentAddActivity.class);
        intent.putExtra("studentList", studentList);
        intent.putExtra(StudentAddActivity.class.getSimpleName(), student);
        context.startActivity(intent);
    }

    @OnClick(R.id.btn_birth_date_select)
    public void selectBirthDate(Button button) {
        DatePickerBuilder datePickerBuilder = DatePickerBuilder.getBuilder();

        if (student != null && UtilsCommon.isValideString(student.getBirthDate()))
            datePickerBuilder.setDateString_EEE_d_MMM_yyyy(student.getBirthDate());
        datePickerBuilder.setActivity(this);

        datePickerBuilder.setDateCallBack(new CommonCallback<String>() {
            @Override
            public void onFailure(String error) {

            }

            @Override
            public void onSuccess(String response) {
                birthDay = response;
                student.setBirthDate(response);
                button.setText("জন্ম তারিখ: " + response);
            }
        }).show();

    }

    @OnClick(R.id.btn_student_pro_pic_upload)
    public void chooseProPic() {

        if (checkHasPermission(RequestCode.PERMISSION_MULTIPLE, new CommonCallback() {
            @Override
            public void onSuccess() {
                chooseProPic();
            }
        }, Manifest.permission
                .READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (validateRoll() && validateStudentName()) {
                Intent intent = CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setActivityTitle("Profile Pic")
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .setCropMenuCropButtonTitle("Done")
                        .setAutoZoomEnabled(true)
                        .setFixAspectRatio(true)
                        .setAspectRatio(6, 6)
                        .setCropMenuCropButtonIcon(R.drawable.check)
                        .getIntent(activity);
                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
            } else {
                DialogUtils.showInfoAlertDialog("সতর্কবানী",
                        "ছবি আপলোডের পূর্বে দয়া করে নাম এবং রোল ইনপুট দিন", this);
            }
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();

                if (imageUri == null) {
                    UtilsCommon.showToast("Error in image selection.");
                    UtilsCommon.debugLog("URL is null.");
                    return;
                }

                Glide.with(this)
                        .load(imageUri)
                        .apply(UtilsView.getLoadingOptionForGlide(this))
                        .into(imgProPic);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                UtilsCommon.debugLog("Error in Photo URL: " + error.getLocalizedMessage());
            }
        }
    }


    public void initView() {
        etPersonName = findViewById(R.id.personTextFromStudentAct);
        etRollNumber = findViewById(R.id.RollNumberFromStudentAct);
        //ADD TEXT CHANGE LISTENER
        etPersonName.addTextChangedListener(new MyTextWatcher(etPersonName));
        etRollNumber.addTextChangedListener(new MyTextWatcher(etRollNumber));

        etPhone = findViewById(R.id.phoneNumbersFromStudentAct);
        etParentName = findViewById(R.id.personParentTextFromStudentAct);
        etParentPhoneNumber = findViewById(R.id.ParentsphoneNumbersFromStudentAct);


        btnAdd = findViewById(R.id.btnAddFromStudentAct);
        btnEdit = findViewById(R.id.btnEditFromStudentAct);
        btnDelete = findViewById(R.id.btnDeleteFromStudentAct);
        btnClassRecord = findViewById(R.id.classRecord);
        btnNotes = findViewById(R.id.btn_note);

        imgProPic = findViewById(R.id.img_pro_pic);


        btnAdd.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClassRecord.setOnClickListener(this);
        btnNotes.setOnClickListener(this);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_student_act);
        activity = this;
        ButterKnife.bind(this);
        initView();
        student = (Student) getIntent().getSerializableExtra(StudentAddActivity.class.getSimpleName());
        studentList = (ArrayList<Student>) getIntent().getSerializableExtra("studentList");


        attendenceDataListBeforeEdit = new ArrayList<>();

        if (student != null) {
            btnAdd.setVisibility(View.GONE);
            etPersonName.setText(student.getStudentName().trim());
            etRollNumber.setText(student.getId().trim());
            etParentName.setText(student.getParentName().trim());
            etParentPhoneNumber.setText(student.getParentContact().trim());
            etPhone.setText(student.getPhone().trim());
            if (student.getGander().equals(Constant.MALE)) {
                rbMale.setChecked(true);
            } else {
                rbFemale.setChecked(true);
            }

            etBirthCertificate.setText(student.getBirthCertificateNo());
            if (UtilsCommon.isValideString(student.getBirthDate())) {
                btnBirthDay.setText("জন্ম তারিখ: " + student.getBirthDate());
                birthDay = student.getBirthDate();
            }

            etParent2Name.setText(student.getParent2Name());
            etParent2Phone.setText(student.getParent2Contact());


            previousId = student.getId().trim();

            Glide.with(this)
                    .load(student.getImageUrl())
                    .apply(UtilsView.getLoadingOptionForGlide(this))
                    .into(imgProPic);
        } else {
            student = new Student();
            btnClassRecord.setVisibility(View.GONE);
            btnEdit.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        if (v == btnAdd) {

            student.setStudentName(etPersonName.getText().toString().trim());
            student.setId(etRollNumber.getText().toString().trim());
            student.setPhone(etPhone.getText().toString().trim());
            student.setParentName(etParentName.getText().toString().trim());
            student.setParentContact(etParentPhoneNumber.getText().toString().trim());

            if (UtilsCommon.getCurrentStudent(this) != null) {
                student.setStudentClass(UtilsCommon.getCurrentClass(this).getName());
                student.setStudentSection(UtilsCommon.getCurrentClass(this).getSection());
            }

            student.setUuid(UUID.randomUUID().toString());
            student.setBirthDate(birthDay);
            student.setParent2Contact(etParent2Phone.getText().toString().trim());
            student.setParent2Name(etParent2Name.getText().toString().trim());
            student.setBirthCertificateNo(etBirthCertificate.getText().toString().trim());
            student.setGander(rbMale.isChecked() ? Constant.MALE : Constant.FEMALE);


            if (student.getId() != null) {
                if (submitForm()) {

                    //CHECK THAT THE ITEM IS UNIQUE
                    if (isRollExisted(studentList, student.getId(), this))
                        return;

                    ClassItem classItem = new ClassItem();

                    if (UtilsCommon.getCurrentClass(this) != null) {
                        classItem = UtilsCommon.getCurrentClass(this);
                    } else return;


                    FirebaseCaller.setStudentToServer(classItem, student, new CommonCallback() {
                        @Override
                        public void onFailure(String r) {
                            UtilsCommon.debugLog("Error in photo uploading - " + r);
                        }

                        @Override
                        public void onSuccess() {
                            uploadProPicToServer();
                            Toast.makeText(StudentAddActivity.this,
                                    "নতুন শিক্ষার্থীর তথ্য ডাটাবেজে যুক্ত হয়েছে ,ধন্যবাদ।", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        } else if (v == btnEdit) {
            student.setStudentName(etPersonName.getText().toString().trim());
            student.setId(etRollNumber.getText().toString().trim());
            student.setPhone(etPhone.getText().toString().trim());
            if (UtilsCommon.getCurrentStudent(this) != null) {
                student.setStudentClass(UtilsCommon.getCurrentClass(this).getName());
                student.setStudentSection(UtilsCommon.getCurrentClass(this).getSection());
            }
            student.setParentName(etParentName.getText().toString().trim());
            student.setParentContact(etParentPhoneNumber.getText().toString().trim());
            if (!UtilsCommon.isValideString(student.getUuid()))
                student.setUuid(UUID.randomUUID().toString());
            currentId = etRollNumber.getText().toString().trim();

            student.setBirthDate(birthDay);
            student.setParent2Contact(etParent2Phone.getText().toString().trim());
            student.setParent2Name(etParent2Name.getText().toString().trim());
            student.setBirthCertificateNo(etBirthCertificate.getText().toString().trim());
            student.setGander(rbMale.isChecked() ? Constant.MALE : Constant.FEMALE);


            //CHECK THAT THE ITEM IS UNIQUE
            if (studentList != null)
                for (int i = 0; i < studentList.size(); i++) {
                    if (studentList.get(i).getId().equals(student.getId())
                            && !previousId.equals(student.getId())) {
                        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                        alertDialog.setTitle("সতর্কীকরণ");
                        alertDialog.setIcon(R.drawable.warning_for_add);
                        alertDialog.setMessage("এই একই রোল ইতিমধ্যে এই ক্লাসের ডাটাবেজে রয়েছে।নতুন রোল ইনপুট দিন,ধন্যবাদ।");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "ওকে",
                                (dialog, which) -> dialog.dismiss());
                        alertDialog.show();
                        return;
                    }
                }

            //FOR VALIDATION
            if (submitForm()) {
                //First GETTING ITS ATTENDANCE DATA


                FirebaseCaller.getAttendanceDataForSingleStudent(student.getStudentClass(),
                        student.getStudentSection(), previousId,
                        new CommonCallback<ArrayList<AttendenceData>>() {
                            @Override
                            public void onFailure(String r) {

                            }

                            @Override
                            public void onSuccess(ArrayList<AttendenceData> attendenceDataList) {


                                attendenceDataListBeforeEdit = attendenceDataList;

                                //Then first reinstall previous Student data;
                                FirebaseCaller.setStudentToServer
                                        (UtilsCommon.getCurrentClass(StudentAddActivity.this),
                                                student, new CommonCallback() {
                                                    @Override
                                                    public void onFailure(String r) {
                                                        DialogUtils.showInfoAlertDialog("Error" +
                                                                " to save the item_student to " +
                                                                "server.", "Log: " + r, StudentAddActivity.this);
                                                    }

                                                    @Override
                                                    public void onSuccess() {
                                                        uploadProPicToServer();
                                                    }
                                                });


                                //Then add attendance list of the specific Student before edit.This is an
                                // operation fromTime Student act activity;
                                if (StudentAddActivity.attendenceDataListBeforeEdit != null) {
                                    for (int i = 0; i < StudentAddActivity.attendenceDataListBeforeEdit.size(); i++) {

                                        FirebaseCaller.
                                                setStudentAttendanceToServer(UtilsCommon.
                                                                getCurrentClass(StudentAddActivity.this), currentId,
                                                        StudentAddActivity.attendenceDataListBeforeEdit.get(i));
                                    }
                                    currentId = null;
                                }
                            }
                        });


                //Then remove the old Student data
                FirebaseCaller.removeStudentToServer(UtilsCommon.getCurrentClass(
                        StudentAddActivity.this), previousId);

                Toast.makeText(this, "শিক্ষার্থীর নতুন ডাটা এডিট হয়েছে,ধন্যবাদ ", Toast.LENGTH_SHORT).show();
                previousId = null;


            }

        } else if (v == btnDelete) {
            student.setStudentName(etPersonName.getText().toString());
            student.setId(etRollNumber.getText().toString());
            student.setPhone(etPhone.getText().toString());
            student.setParentName(etParentName.getText().toString());
            student.setParentContact(etParentPhoneNumber.getText().toString());

            //FOR AVOID SQL INJECTION
            for (int i = 0; i < studentList.size(); i++) {
                if (studentList.get(i).getId().equals(student.getId()) && !(previousId.equals(etRollNumber.getText().toString()))) {
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("সতর্কীকরণ");
                    alertDialog.setIcon(R.drawable.warnig_for_delete);
                    alertDialog.setMessage("আপনি শিক্ষার্থীর নাম অংশ পরিবর্তন করে যে নাম ইনপুট করেছেন তা অন্য আরেকটি শিক্ষার্থীর ডাটাবেজের নামের সাথে মিলে যায় ।তাই আপনাকে সেই শিক্ষার্থীর ডাটা ডিলেট করতে হলে অবশ্যই সেই শিক্ষার্থীর ডাটাবেজে যেতে হবে।ধন্যবাদ ");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "ওকে",
                            (dialog, which) -> dialog.dismiss());
                    alertDialog.show();
                    return;
                }
            }


            deleteDialogForStudent();
        } else if (v == btnClassRecord) {
            Intent launchinIntent = new Intent(this, StudentAlIInfoShowActivity.class);
            String roll = previousId;
            launchinIntent.putExtra("Student", student);
            launchinIntent.putExtra("Roll", roll);
            launchinIntent.putExtra("classItem",
                    UtilsCommon.getCurrentClass(StudentAddActivity.this));
            startActivity(launchinIntent);
        }
        else if(v.getId()==R.id.btn_note){
            String databasePath= "/Users/"+FirebaseCaller.getUserID()+"/Class/"+student.getStudentClass()
                    +student.getStudentSection()
                    +"/Student/"+student.getId()+"/notes";
            NoteShowingDialog.showDialog(getSupportFragmentManager(),databasePath);
        }
    }

    public void uploadProPicToServer() {
        if (imageUri != null && UtilsCommon.isValideString(imageUri.toString()))
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
                            UtilsCommon.debugLog("Error in photo uploading - " + r);
                        }

                        @Override
                        public void onSuccess(Uri response) {
                            finish();
                            UtilsCommon.showToast("Photo is uploaded");
                        }
                    }).build();
        else {
            finish();
            UtilsCommon.debugLog("Student Image URI is null");
        }
    }

    private boolean submitForm() {
        if (!validateStudentName()) {
            Toast.makeText(getApplicationContext(), "দয়া করে শিক্ষার্থীর নাম অংশের ভুল সংশোধণ করুন,ধন্যবাদ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!validateRoll()) {
            Toast.makeText(getApplicationContext(), "দয়া করে শিক্ষার্থীর রোল অংশের ভুল সংশোধণ করুন,ধন্যবাদ", Toast.LENGTH_SHORT).show();
            return false;
        }

        Toast.makeText(getApplicationContext(), "সঠিক তথ্য ইনপুট দেয়ার জন্য আপনাকে ধন্যবাদ", Toast.LENGTH_SHORT).show();
        return true;
    }

    private boolean validateStudentName() {
        if (etPersonName.getText().toString().trim().isEmpty()) {
            etPersonName.setError(getString(R.string.error_massege_for_input));
            requestFocus(etPersonName);
            return false;
        }
        return true;
    }

    private boolean validateRoll() {
        if (etRollNumber.getText().toString().trim().isEmpty()) {
            etRollNumber.setError(getString(R.string.error_massege_for_input));
            requestFocus(etRollNumber);
            return false;
        } else return true;
    }

    private void requestFocus(View view) {
        view.requestFocus();
    }

    @Override
    protected void onPermissionBlocked(String permission) {

    }

    public void deleteDialogForStudent() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_delete_dialauge, null);
        dialogBuilder.setView(dialogView);
        final EditText edt = (EditText) dialogView.findViewById(R.id.custom_delete_dialauge_text);
        dialogBuilder.setTitle("আপনি কি আসলেই এই শিক্ষার্থীর সকল তথ্য ডিলিট করতে চান?");
        dialogBuilder.setPositiveButton("ডিলিট করুন", (dialog, whichButton) -> {

            FirebaseCaller.removeStudentToServer(UtilsCommon.
                    getCurrentClass(StudentAddActivity.this), student.getId());

            Toast.makeText(StudentAddActivity.this, "এই শিক্ষার্থীর যাবতীয় সব তথ্য ডাটাবেজ থেকে ডিলেট হয়েছে,ধন্যবাদ।", Toast.LENGTH_LONG).show();
            previousId = null;
            finish();

        });
        dialogBuilder.setNegativeButton("বাদ দিন", (dialog, whichButton) -> {
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
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

}
