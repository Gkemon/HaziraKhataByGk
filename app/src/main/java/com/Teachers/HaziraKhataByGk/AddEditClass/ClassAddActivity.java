package com.Teachers.HaziraKhataByGk.AddEditClass;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.Login.LoginActivity;
import com.Teachers.HaziraKhataByGk.MainActivity;
import com.Teachers.HaziraKhataByGk.Model.ClassIitem;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.Scheduler.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ClassAddActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText classNameEditText;
    private EditText sectionEditText;
    private Button btnAdd, btnEdit, btnDelete;
    private ClassIitem classitem = null;


    //This is for avoiding Delete SQL injection
    public static String previousClassName;
    public static String previousSectionName;




    public static FirebaseAuth auth;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public static String mUserId;
    public static FirebaseUser mFirebaseUser;

    public static void start(Context context) {
        Intent intent = new Intent(context, ClassAddActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, ClassIitem classitem) {
        Intent intent = new Intent(context, ClassAddActivity.class);
        intent.putExtra(ClassAddActivity.class.getSimpleName(), classitem);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_act);




        classNameEditText = (EditText) findViewById(R.id.classText);
        sectionEditText = (EditText) findViewById(R.id.sectionText);

        classNameEditText.addTextChangedListener(new MyTextWatcher(classNameEditText));
        sectionEditText.addTextChangedListener(new MyTextWatcher(sectionEditText));

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnAdd.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        classitem = getIntent().getParcelableExtra(ClassAddActivity.class.getSimpleName());

        if (classitem != null) {
            btnAdd.setVisibility(View.GONE);
            classNameEditText.setText(classitem.getName());
            sectionEditText.setText(classitem.getSection());
            previousClassName=classitem.getName();
            previousSectionName =classitem.getSection();
        }else {
            btnDelete.setVisibility(View.GONE);
            btnEdit.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnAdd) {
            classitem = new ClassIitem();
            classitem.setName(classNameEditText.getText().toString().trim());
            classitem.setSection(sectionEditText.getText().toString().trim());


            //CHECK THAT THE ITEM IS UNIQUE

            //for avoiding null pointer exeption
            if(MainActivity.TotalClassItems==null)
                startActivity(new Intent(this,MainActivity.class));


            for(int i=0;i<MainActivity.TotalClassItems.size();i++){
                if(MainActivity.TotalClassItems.get(i).getName().equals(classitem.getName())&&MainActivity.TotalClassItems.get(i).getSection().equals(classitem.getSection())){
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("সতর্কীকরণ");
                    alertDialog.setIcon(R.drawable.warning_for_add);
                    alertDialog.setMessage("এই একই নামের আরেকটি ক্লাসের নাম ইতিমধ্যে ডাটাবেজে রয়েছে।নতুন নাম ইনপুট দিন,ধন্যবাদ।");
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

            if(submitForm()){

            ConfirmDialog();

            }

        }
        else if (v == btnDelete) {
            classitem = new ClassIitem();

            classitem.setName(classNameEditText.getText().toString());
            classitem.setSection(sectionEditText.getText().toString());

            //FOR AVOID SQL INJECTION
            for(int i=0;i<MainActivity.TotalClassItems.size();i++){


                if(MainActivity.TotalClassItems.get(i).getName().equals(classitem.getName())&&MainActivity.TotalClassItems.get(i).getSection().equals(classitem.getSection())&&!(previousClassName.equals(classNameEditText.getText().toString())&& previousSectionName.equals(sectionEditText.getText().toString()))){
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("সতর্কীকরণ");
                    alertDialog.setIcon(R.drawable.warnig_for_delete);
                    alertDialog.setMessage("আপনি ক্লাসের নাম অংশ পরিবর্তন করে যে নাম ইনপুট করেছেন তা অন্য আরেকটি ক্লাসের ডাটাবেজের নামের সাথে মিলে যায় ।তাই আপনাকে সেই ক্লাসটি ডিলেট করতে হলে অবশ্যই সেই ক্লাসের ডাটাবেজে যেতে হবে।ধন্যবাদ ");
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

            DeleteDialog();
        }
        else {
editClass();


        }


        }

       public void editClass(){


        //For Random click check
        btnEdit.setOnClickListener(null);

           //FOR AVOID SQL INJECTION
           for(int i=0;i<MainActivity.TotalClassItems.size();i++){


               if(MainActivity.TotalClassItems.get(i).getName().equals(classNameEditText.getText().toString().trim())&&MainActivity.TotalClassItems.get(i).getSection().equals(sectionEditText.getText().toString().trim())&&!(previousClassName.equals(classNameEditText.getText().toString())&& previousSectionName.equals(sectionEditText.getText().toString()))){
                   AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                   alertDialog.setTitle("সতর্কীকরণ");
                   alertDialog.setIcon(R.drawable.warnig_for_delete);
                   alertDialog.setMessage("আপনি ক্লাসের নাম অংশ পরিবর্তন করে যে নাম ইনপুট করেছেন তা অন্য আরেকটি ক্লাসের ডাটাবেজের নামের সাথে মিলে যায় ।তাই আপনাকে সেই ক্লাসটি Edit করতে হলে অবশ্যই সেই ক্লাসের ডাটাবেজে যেতে হবে।ধন্যবাদ ");
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

           DatabaseReference fromDBRef=FirebaseCaller.getFirebaseDatabase().child("Users")
                   .child(FirebaseCaller.getUserID())
                   .child("Class")
                   .child(previousClassName + previousSectionName);

           DatabaseReference toDBRef=FirebaseCaller.getFirebaseDatabase().child("Users")
                   .child(FirebaseCaller.getUserID())
                   .child("Class")
                   .child(classNameEditText.getText().toString().trim() + sectionEditText.getText().toString().trim());


           DatabaseReference dbRefRemove=fromDBRef;
           //Rename the class name and section
           dbRefRemove.child("name").setValue(classNameEditText.getText().toString().trim());
           dbRefRemove.child("section").setValue(sectionEditText.getText().toString().trim());


           copyRecord(fromDBRef,toDBRef);

        }

    private void copyRecord(final DatabaseReference fromPath, final DatabaseReference toPath) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toPath.setValue(dataSnapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            UtilsCommon.debugLog("Copy Class Complete");
                            fromPath.removeValue().addOnSuccessListener(ClassAddActivity.this, new
                                    OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            ClassAddActivity.this.finish();
                                            UtilsCommon.showToast("Class Edited");

                                        }
                                    });

                        } else {
                            UtilsCommon.debugLog("Copy Class Not Complete");
                        }
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



//EDIT TEXT MATERIAL STYLE
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


    public void DeleteDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_delete_dialauge, null);
        dialogBuilder.setView(dialogView);
        final EditText edt = (EditText) dialogView.findViewById(R.id.custom_delete_dialauge_text);
        dialogBuilder.setIcon(R.drawable.warnig_for_delete);
        dialogBuilder.setTitle("আপনি কি আসলেই ক্লাসে সকল তথ্য ডিলিট করতে চান?");
        dialogBuilder.setMessage("ডিলিট করার আগে ইংরেজীতে \"DELETE\" শব্দটি লিখুন।");
        dialogBuilder.setPositiveButton("ডিলিট করুন", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(edt.getText().toString().trim().equals("DELETE")){


                        //FOR DELETE
                    FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(classitem.getName()+classitem.getSection()).removeValue();


                        Toast.makeText(ClassAddActivity.this,"ক্লাসটির যাবতীয় সব ডাটাবেজ সার্ভার থেকে ডিলেট হয়েছে,ধন্যবাদ।",Toast.LENGTH_LONG).show();
                        previousClassName=null;
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


    public void ConfirmDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_class_add_dialauge, null);
        dialogBuilder.setView(dialogView);
        final EditText edt = (EditText) dialogView.findViewById(R.id.custom_class_add_edit_text);

        dialogBuilder.setIcon(R.drawable.warning_for_add);
        dialogBuilder.setTitle("Warning");
        dialogBuilder.setMessage("Are you sure?");
        dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                    try {

                        //FOR DELETE
                        FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Class").child(classitem.getName() + classitem.getSection()).setValue(classitem);

                    }
                     catch (Exception e){


                        AlertDialog alertDialogError = new AlertDialog.Builder(ClassAddActivity.this).create();
                         alertDialogError.setMessage(" আপনার ডিভাইসের সমস্যার কারনে Delete হচ্ছেনা । দয়া করে আবার চেষ্টা করুন অথবা ডেভেলপারকে জানান ফেসবুক গ্রুপে পোস্ট করে,ধন্যবাদ । সমস্যাটি হল : "+e.getMessage());
                         alertDialogError.setButton(AlertDialog.BUTTON_NEUTRAL,"পোস্ট দিন",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intent5 =  LoginActivity.getFacebookIntent("https://www.facebook.com/groups/2035798976667483/permalink/2066665843580796/",getBaseContext());

                                        try {
                                            startActivity(intent5);
                                        }
                                        catch (Exception e){
                                            Toast.makeText(ClassAddActivity.this,"ERROR "+e.getMessage(),Toast.LENGTH_LONG).show();
                                        }

                                        dialog.dismiss();
                                    }
                                });
                         alertDialogError.show();




                    Toast.makeText(ClassAddActivity.this, "নতুন ক্লাসটির জন্য সার্ভারে ডাটাবেজ তৈরি হয়েছে,ধন্যবাদ",                       Toast.LENGTH_SHORT).show();
                    previousClassName=null;



                    AlertDialog alertDialog = new AlertDialog.Builder(ClassAddActivity.this).create();
                    alertDialog.setMessage("আপনি যদি শ্রেণীর সকল ডাটা ডিলিট করতে চান তাহলে পরবর্তীতে শ্রেণীর নামের উপর লং প্রেস করুন।");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"ওকে",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                    finish();

                                }
                            });
                    alertDialog.show();
                }
            }
        });
        dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }




}
