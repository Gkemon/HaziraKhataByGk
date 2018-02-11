package com.Teachers.HaziraKhataByGk;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.Teachers.HaziraKhataByGk.model.class_item;

import static com.Teachers.HaziraKhataByGk.MainActivity.databaseReference;
import static com.Teachers.HaziraKhataByGk.MainActivity.mUserId;


public class ActActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText personName;
    private EditText phone;
    private Button btnAdd, btnEdit, btnDelete;
    private class_item classitem = null;
   public static String previousClassName;
   public static String prviousSectionName;
    public static void start(Context context) {
        Intent intent = new Intent(context, ActActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, class_item classitem) {
        Intent intent = new Intent(context, ActActivity.class);
        intent.putExtra(ActActivity.class.getSimpleName(), classitem);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act);

        personName = (EditText) findViewById(R.id.classText);
        phone = (EditText) findViewById(R.id.sectionText);

        personName.addTextChangedListener(new MyTextWatcher(personName));
        phone.addTextChangedListener(new MyTextWatcher(phone));

        btnAdd = (Button) findViewById(R.id.btnAdd);
       // btnEdit = (Button) findViewById(R.id.btnEdit);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnAdd.setOnClickListener(this);
        //btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        classitem = getIntent().getParcelableExtra(ActActivity.class.getSimpleName());

        if (classitem != null) {
            btnAdd.setVisibility(View.GONE);
            personName.setText(classitem.getName());
            phone.setText(classitem.getSection());
            previousClassName=classitem.getName();
            prviousSectionName=classitem.getSection();
        }else {
            btnDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnAdd) {
            classitem = new class_item();
            classitem.setName(personName.getText().toString().trim());
            classitem.setSection(phone.getText().toString().trim());


            //CHECK THAT THE ITEM IS UNIQUE
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
            classitem = new class_item();

            classitem.setName(personName.getText().toString());
            classitem.setSection(phone.getText().toString());

            //FOR AVOID SQL INJECTION
            for(int i=0;i<MainActivity.TotalClassItems.size();i++){
                if(MainActivity.TotalClassItems.get(i).getName().equals(classitem.getName())&&MainActivity.TotalClassItems.get(i).getSection().equals(classitem.getSection())&&!(previousClassName.equals(personName.getText().toString())&&prviousSectionName.equals(phone.getText().toString()))){
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
//        else if (v == btnEdit) {
//            classitem = new class_item();
//            classitem.setName(personName.getText().toString());
//            classitem.setSection(phone.getText().toString());
//
//            //CHECK THAT THE ITEM IS UNIQUE
//            for(int i=0;i<MainActivity.TotalClassItems.size();i++){
//                if(MainActivity.TotalClassItems.get(i).getName().equals(classitem.getName())&&MainActivity.TotalClassItems.get(i).getSection().equals(classitem.getSection())){
//                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//                    alertDialog.setTitle("");
//                    alertDialog.setMessage("এই একই নামের আরেকটি ক্লাসের নাম ইতিমধ্যে ডাটাবেজে রয়েছে।নতুন নাম ইনপুট দিন,ধন্যবাদ।");
//                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "ওকে",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            });
//                    alertDialog.show();
//                    return;
//                }
//            }
//
//            //FOR VALIDATION
//            if(submitForm()){
//                //FOR MODIFICATION
//    Query queryRef = databaseReference.child("Class").orderByChild("name").equalTo(previousClassName);
//     queryRef.addListenerForSingleValueEvent( new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
//                            snapshot.getRef().child("name").setValue(personName.getText().toString());
//                            snapshot.getRef().child("section").setValue(phone.getText().toString());
//                        }
//                        }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {}});
//                Toast.makeText(this,"নতুন ডাটা এডিট হয়েছে,ধন্যবাদ ",Toast.LENGTH_SHORT).show();
//                previousClassName=null;
//                finish();
//            }

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
        if (personName.getText().toString().trim().isEmpty()) {
            personName.setError(getString(R.string.error_massege_for_input));
            requestFocus(personName);
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
                    MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(classitem.getName()+classitem.getSection()).removeValue();
//                        Query queryRef = databaseReference.child("Class").orderByChild("name").equalTo(previousClassName);
//                        queryRef.addListenerForSingleValueEvent( new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
//                                    snapshot.getRef().removeValue();
//                                }
//                            }
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {}});
                        Toast.makeText(ActActivity.this,"ক্লাসটির যাবতীয় সব ডাটাবেজ সার্ভার থেকে ডিলেট হয়েছে,ধন্যবাদ।",Toast.LENGTH_LONG).show();
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
        dialogBuilder.setTitle("সতর্কীকরণ");
        dialogBuilder.setMessage("ক্লাসের নাম অংশটি সংবেদনশীল,তাই ক্লাসের নাম পরবর্তী পরিবর্তন করা যাবে না।নিশ্চিত হতে ইংরেজীতে \"CONFIRM\" শব্দটি লিখুন করন।");
        dialogBuilder.setPositiveButton("ইনপুট করুন", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(edt.getText().toString().trim().equals("CONFIRM")){
                    //Adding new class_room
                    databaseReference.child("Users").child(mUserId).child("Class").child(classitem.getName()+classitem.getSection()).setValue(classitem);
                    Toast.makeText(ActActivity.this, "নতুন ক্লাসটির জন্য সার্ভারে ডাটাবেজ তৈরি হয়েছে,ধন্যবাদ",                       Toast.LENGTH_SHORT).show();
                    previousClassName=null;



                    AlertDialog alertDialog = new AlertDialog.Builder(ActActivity.this).create();
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
                else
                {
                    //IF USER INPUT WRONG CLASS IN CONFIRMATION
                    AlertDialog alertDialog = new AlertDialog.Builder(ActActivity.this).create();
                    alertDialog.setTitle("ভুল সংশোধণ করুন");
                    alertDialog.setIcon(R.drawable.warnig_for_delete);
                    alertDialog.setMessage("আপনার দেয়া ইনপুটটি \"CONFIRM\" শব্দের সাথে মিলেনি । পুনরায় সঠিকভাবে \"CONFIRM\" শব্দটি ইনপুট করুন।ধন্যবাদ");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"ওকে",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                }
                            });
                    alertDialog.show();

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
