package com.emon.haziraKhata.AddEditClass;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.emon.haziraKhata.Firebase.FirebaseCaller;
import com.emon.haziraKhata.HelperClasses.CustomArrayList;
import com.emon.haziraKhata.HelperClasses.UtilsCommon;
import com.emon.haziraKhata.HelperClasses.ViewUtils.BaseActivity;
import com.emon.haziraKhata.MainActivity;
import com.emon.haziraKhata.Model.ClassItem;
import com.emon.haziraKhata.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class ClassAddActivity extends BaseActivity
        implements View.OnClickListener {


    //This is for avoiding Delete SQL injection
    private ClassItem previousClass;
    CustomArrayList<ClassItem> classItemList;
    private EditText classNameEditText;
    private EditText sectionEditText;
    private Button btnAdd, btnEdit, btnDelete;
    private ClassItem classitem = null;

    public static void start(Context context) {
        Intent intent = new Intent(context, ClassAddActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, ClassItem classitem) {
        Intent intent = new Intent(context, ClassAddActivity.class);
        intent.putExtra(ClassAddActivity.class.getSimpleName(), classitem);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_act);


        classNameEditText = findViewById(R.id.classText);
        sectionEditText = findViewById(R.id.sectionText);

        classNameEditText.addTextChangedListener(new MyTextWatcher(classNameEditText));
        sectionEditText.addTextChangedListener(new MyTextWatcher(sectionEditText));

        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        btnAdd.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        classitem = (ClassItem) getIntent().getSerializableExtra(ClassAddActivity.class.getSimpleName());
        UtilsCommon.setCurrentClass(classitem, this);

        classItemList = new CustomArrayList<>();
        classItemList = UtilsCommon.getAllClass(ClassAddActivity.this);


        if (classitem != null) {
            btnAdd.setVisibility(View.GONE);
            classNameEditText.setText(classitem.getName());
            sectionEditText.setText(classitem.getSection());
            previousClass= new ClassItem(classitem.getName(),classitem.getSection());
        } else {
            btnDelete.setVisibility(View.GONE);
            btnEdit.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnAdd) {
            classitem = new ClassItem();
            classitem.setName(classNameEditText.getText().toString().trim());
            classitem.setSection(sectionEditText.getText().toString().trim());


            if (FirebaseCaller.getCurrentUser() == null) {
                UtilsCommon.showDialogForSignUp(this);
                return;
            }
            //CHECK THAT THE ITEM IS UNIQUE

            //for avoiding null pointer exeption
            if (classItemList == null) {
                startActivity(new Intent(this, MainActivity.class));
                UtilsCommon.handleError(new Exception("Class list in null"));
                return;
            }


            for (int i = 0; i < classItemList.size(); i++) {

                if (classItemList.get(i).getName().equals(classitem.getName()) &&
                        classItemList.get(i).getSection().equals(classitem.getSection())) {
                    try {
                        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                        alertDialog.setTitle("সতর্কীকরণ");
                        alertDialog.setIcon(R.drawable.warning_for_add);
                        alertDialog.setMessage("এই একই নামের আরেকটি ক্লাসের নাম ইতিমধ্যে ডাটাবেজে রয়েছে।নতুন নাম ইনপুট দিন,ধন্যবাদ।");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "ওকে",
                                (dialog, which) -> dialog.dismiss());
                        alertDialog.show();
                    }catch (Exception e){
                        UtilsCommon.handleError(e);
                    }

                    return;
                }
            }

            if (submitForm()) {

                confirmDialog();

            }

        } else if (v == btnDelete) {
            classitem = new ClassItem();

            classitem.setName(classNameEditText.getText().toString());
            classitem.setSection(sectionEditText.getText().toString());

            //FOR AVOID SQL INJECTION

                    if (!isEditable()) {
                        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                        alertDialog.setTitle("সতর্কীকরণ");
                        alertDialog.setIcon(R.drawable.warnig_for_delete);
                        alertDialog.setMessage("আপনি ক্লাসের নাম অংশ পরিবর্তন করে যে নাম ইনপুট করেছেন তা অন্য আরেকটি ক্লাসের ডাটাবেজের নামের সাথে মিলে যায় ।তাই আপনাকে সেই ক্লাসটি ডিলেট করতে হলে অবশ্যই সেই ক্লাসের ডাটাবেজে যেতে হবে।ধন্যবাদ ");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "ওকে",
                                (dialog, which) -> dialog.dismiss());
                        alertDialog.show();
                        return;
                    }



            deleteDialog();
        } else {

            classitem.setName(classNameEditText.getText().toString());
            classitem.setSection(sectionEditText.getText().toString());

            editClass();
        }
    }

    private boolean isEditable(){
        if(classItemList==null||classitem==null||!UtilsCommon.isValideString(previousClass.getName()))
            return false;
        return !(classItemList.contains(previousClass)&&
                classItemList.contains(classitem)&&!previousClass.equals(classitem));

    }




    public void editClass() {


        //For Random click check
        btnEdit.setEnabled(false);

        if (!validateClassName()) {
            btnEdit.setEnabled(true);
            return;
        }

        //FOR AVOID SQL INJECTION
        if(!isEditable()) {


            try {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("সতর্কীকরণ");
                alertDialog.setIcon(R.drawable.warnig_for_delete);
                alertDialog.setMessage("আপনি ক্লাসের নাম অংশ পরিবর্তন করে যে নাম ইনপুট করেছেন তা অন্য আরেকটি ক্লাসের ডাটাবেজের নামের সাথে মিলে যায় ।তাই আপনাকে সেই ক্লাসটি Edit করতে হলে অবশ্যই সেই ক্লাসের ডাটাবেজে যেতে হবে।ধন্যবাদ ");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "ওকে",
                        (dialog, which) -> dialog.dismiss());
                alertDialog.show();
                btnEdit.setEnabled(true);
                return;
            } catch (Exception e) {
                UtilsCommon.handleError(e);
            }

        }


        DatabaseReference fromDBRef = FirebaseCaller.getFirebaseDatabase().child("Users")
                .child(FirebaseCaller.getUserID())
                .child("Class")
                .child(previousClass.getName() + previousClass.getSection());

        DatabaseReference toDBRef = FirebaseCaller.getFirebaseDatabase().child("Users")
                .child(FirebaseCaller.getUserID())
                .child("Class")
                .child(classNameEditText.getText().toString().trim() + sectionEditText.getText().toString().trim());


        //Rename the class name and section
        fromDBRef.child("name").setValue(classNameEditText.getText().toString().trim());
        fromDBRef.child("section").setValue(sectionEditText.getText().toString().trim());

        if (!fromDBRef.toString().equals(toDBRef.toString()))
            copyRecord(fromDBRef, toDBRef);
        else btnEdit.setEnabled(true);

    }

    private void copyRecord(final DatabaseReference fromPath, final DatabaseReference toPath) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toPath.setValue(dataSnapshot.getValue()).addOnCompleteListener(task -> {
                    if (task.isComplete()) {
                        UtilsCommon.debugLog("Copy Class Complete");
                        fromPath.removeValue().addOnSuccessListener(ClassAddActivity.this, aVoid -> ClassAddActivity.this.finish());

                    } else {
                        UtilsCommon.debugLog("Copy Class Not Complete");
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                UtilsCommon.debugLog("Error ");
            }
        };

        fromPath.addListenerForSingleValueEvent(valueEventListener);

    }

    //FOR VALIDATION
    private boolean submitForm() {
        if (!validateClassName()) {
            Toast.makeText(getApplicationContext(), "দয়া করে ক্লাসের নাম অংশের ভুল সংশোধন করুন,ধন্যবাদ", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private boolean validateClassName() {
        if (classNameEditText.getText().toString().trim().isEmpty()) {
            classNameEditText.setError(getString(R.string.error_massege_for_input));
            requestFocus(classNameEditText);
            return false;
        }
        return true;
    }

    private void requestFocus(View view) {
        view.requestFocus();
    }

    public void deleteDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_delete_dialauge, null);
        dialogBuilder.setView(dialogView);
        final EditText edt =dialogView.findViewById(R.id.custom_delete_dialauge_text);
        dialogBuilder.setIcon(R.drawable.warnig_for_delete);
        dialogBuilder.setTitle("আপনি কি আসলেই ক্লাসে সকল তথ্য ডিলিট করতে চান?");
        dialogBuilder.setMessage("ডিলিট করার আগে ইংরেজীতে \"DELETE\" শব্দটি লিখুন।");
        dialogBuilder.setPositiveButton("ডিলিট করুন", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (edt.getText().toString().trim().equals("DELETE")) {


                    //FOR DELETE
                    FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(classitem.getName() + classitem.getSection()).removeValue();


                    Toast.makeText(ClassAddActivity.this, "ক্লাসটির যাবতীয় সব ডাটাবেজ সার্ভার থেকে ডিলেট হয়েছে,ধন্যবাদ।", Toast.LENGTH_LONG).show();
                    previousClass = null;
                    finish();


                }
            }
        });
        dialogBuilder.setNegativeButton("বাদ দিন", (dialog, whichButton) -> {
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void confirmDialog() {


        FirebaseCaller.getFirebaseDatabase()
                .child("Users")
                .child(FirebaseCaller
                        .getUserID())
                .child("Class")
                .child(classitem.getName() + classitem.getSection())
                .setValue(classitem)
                .addOnCompleteListener(ClassAddActivity.this, aVoid -> {

                    AlertDialog alertDialog = new AlertDialog.Builder(ClassAddActivity.this).create();
                    alertDialog.setMessage("আপনি যদি শ্রেণীর সকল ডাটা ডিলিট করতে চান তাহলে পরবর্তীতে শ্রেণীর নামের উপর লং প্রেস করুন।");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "ওকে",
                            (dialog, which) -> {
                                dialog.dismiss();
                                finish();
                            });
                    alertDialog.show();

                });


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

                case R.id.classText:
                    validateClassName();
                    break;
                case R.id.sectionText:
                    //validateSectionName();
                    break;
            }
        }
    }


}
