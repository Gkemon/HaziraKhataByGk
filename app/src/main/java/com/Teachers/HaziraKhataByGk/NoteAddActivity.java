package com.Teachers.HaziraKhataByGk;

import android.app.Activity;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.Teachers.HaziraKhataByGk.ClassRoom.ClassRoomActivity;
import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.HelperClasses.UtilsCommon;
import com.Teachers.HaziraKhataByGk.HelperClasses.ViewUtils.BaseActivity;
import com.Teachers.HaziraKhataByGk.Model.Notes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uy on 9/7/2017.
 */

public class NoteAddActivity extends BaseActivity implements View.OnClickListener {
    public static String currentTitle, previousTitle, currentContent, previousContent;
    public LinearLayout ButtonLayout;
    Activity activity;
    boolean isEdited;
    private EditText title;
    private EditText content;
    private com.Teachers.HaziraKhataByGk.Model.Notes Notes;
    private Button ADD, Save, btnDelete;

    public static void start(Context context) {
        Intent intent = new Intent(context, NoteAddActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, Notes Notes) {
        Intent intent = new Intent(context, NoteAddActivity.class);
        intent.putExtra(ClassRoomActivity.class.getSimpleName(), Notes);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_note_add);
        activity = this;
        title = (EditText) findViewById(R.id.et_note_title);
        content = (EditText) findViewById(R.id.content);
        isEdited = false;

        //ADD TEXT CHANGE LISTENER
        title.addTextChangedListener(new MyTextWatcher(title));
        content.addTextChangedListener(new MyTextWatcher(content));

        ADD = (Button) findViewById(R.id.btn_add_note);
        Save = (Button) findViewById(R.id.btn_save_note);
        btnDelete = (Button) findViewById(R.id.btn_delete_note);
        ButtonLayout = (LinearLayout) findViewById(R.id.buttomLinearLayout);
        Notes = getIntent().getParcelableExtra(ClassRoomActivity.class.getSimpleName());
        ADD.setOnClickListener(this);
        Save.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        if (Notes != null) {
            ADD.setVisibility(View.GONE);
            title.setText(Notes.getheading());
            content.setText(Notes.getContent());
            previousTitle = Notes.getheading();
            previousContent = Notes.getContent();
        } else {
            Save.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        if (v == ADD) {
            Notes = new Notes();
            Notes.setheading(title.getText().toString().trim());
            Notes.setContent(content.getText().toString().trim());

            //CHECK THAT THE ITEM IS UNIQUE
            for (int i = 0; i < ClassRoomActivity.notesList.size(); i++) {
                if (ClassRoomActivity.notesList.get(i).getheading().equals(Notes.getheading())) {
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("সতর্কীকরণ");
                    alertDialog.setIcon(R.drawable.warning_for_add);
                    alertDialog.setMessage("এই একই শিরোনামের নোট ইতিমধ্যে এই ক্লাসের ডাটাবেজে রয়েছে।নতুন শিরোনাম ইনপুট দিন,ধন্যবাদ।");
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
            if (submitForm()) {
                FirebaseCaller.getFirebaseDatabase().child("Users").
                        child(FirebaseCaller.getUserID()).child("Class").
                        child(UtilsCommon.getCurrentClass(this).getName() +
                                UtilsCommon.getCurrentClass(this).getSection()).
                        child("Notes").child(Notes.getheading()).setValue(Notes);
                Toast.makeText(this, "নোটটি সার্ভারে যুক্ত হয়েছে,ধন্যবাদ ।", Toast.LENGTH_SHORT).show();

                finish();

            }
        } else if (v == Save) {


            Notes.setheading(title.getText().toString().trim());
            Notes.setContent(content.getText().toString().trim());


            FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).
                    child("Class").child(UtilsCommon.getCurrentClass(NoteAddActivity.this).getName() +
                    UtilsCommon.getCurrentClass(NoteAddActivity.this).getSection()).child("Notes")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            List<Notes> NotesList = new ArrayList<Notes>();
                            for (DataSnapshot NoteData : dataSnapshot.getChildren()) {
                                Notes Notes = new Notes();
                                Notes = NoteData.getValue(Notes.class);
                                NotesList.add(Notes);
                            }
                            ClassRoomActivity.notesList = NotesList;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });

            //CHECK THAT THE ITEM IS UNIQUE
            for (int i = 0; i < ClassRoomActivity.notesList.size(); i++) {
                if (ClassRoomActivity.notesList.get(i).getheading().equals(Notes.getheading()) && !previousTitle.equals(title.getText().toString())) {
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("সতর্কীকরণ");
                    alertDialog.setIcon(R.drawable.warning_for_add);
                    alertDialog.setMessage("এই একই শিরোনামের নোট ইতিমধ্যে এই ক্লাসের ডাটাবেজে রয়েছে।নতুন শিরোনাম ইনপুট দিন,ধন্যবাদ।");
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


            if (submitForm()) {
                //Then remove the old Student data
                FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).
                        child("Class").child(UtilsCommon.getCurrentClass(NoteAddActivity.this).getName() +
                        UtilsCommon.getCurrentClass(NoteAddActivity.this).getSection()).child("Notes").child(previousTitle).removeValue();
                //Then first reinstall previous Student data;
                FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).
                        child("Class").child(UtilsCommon.getCurrentClass(NoteAddActivity.this).getName() +
                        UtilsCommon.getCurrentClass(NoteAddActivity.this).getSection()).child("Notes").child(Notes.getheading()).setValue(Notes);

            }

            Toast.makeText(this, "নোট নোটটি সার্ভারে সেভ হচ্ছে", Toast.LENGTH_SHORT).show();
            previousTitle = null;


            finish();


        } else if (v == btnDelete) {
            Notes.setheading(title.getText().toString());
            Notes.setContent(content.getText().toString());

            //FOR AVOID SQL INJECTION
            for (int i = 0; i < ClassRoomActivity.notesList.size(); i++) {
                if (ClassRoomActivity.notesList.get(i).getheading().equals(Notes.getheading()) && !previousTitle.equals(title.getText().toString())) {
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("সতর্কীকরণ");
                    alertDialog.setIcon(R.drawable.warnig_for_delete);
                    alertDialog.setMessage("আপনি নোটের শিরোনাম অংশ পরিবর্তন করে যে শিরোনাম ইনপুট করেছেন তা অন্য আরেকটি নোটের শিরোনাম অংশের সাথে মিলে যায় ।তাই আপনাকে সেই নোটটি ডিলেট করতে হলে  অবশ্যই সেই নোটে যেতে হবে।ধন্যবাদ ");
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
            DeleteDialog();

        }
    }

    boolean validateNoteTitle() {
        if (title.getText().toString().trim().isEmpty()) {
            title.setError(getString(R.string.error_massege_for_input));
            requestFocus(title);
            return false;
        }

        return true;
    }

    private void requestFocus(View view) {
        view.requestFocus();
    }

    private boolean submitForm() {
        if (!validateNoteTitle()) {
            Toast.makeText(getApplicationContext(), "দয়া করে নোটের শিরোনাম অংশের ভুল সংশোধণ করুন,ধন্যবাদ", Toast.LENGTH_SHORT).show();
            return false;
        }

        Toast.makeText(getApplicationContext(), "সঠিক তথ্য ইনপুট দেয়ার জন্য আপনাকে ধন্যবাদ", Toast.LENGTH_SHORT).show();
        return true;
    }

    public void DeleteDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_delete_dialauge, null);
        dialogBuilder.setView(dialogView);
        final EditText edt = (EditText) dialogView.findViewById(R.id.custom_delete_dialauge_text);
        dialogBuilder.setIcon(R.drawable.warnig_for_delete);
        dialogBuilder.setTitle("আপনি কি আসলেই নোটটি ডিলিট করতে চান?");
        dialogBuilder.setMessage("ডিলিট করার আগে ইংরেজীতে \"DELETE\" শব্দটি লিখুন।");
        dialogBuilder.setPositiveButton("ডিলিট করুন", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (edt.getText().toString().trim().equals("DELETE")) {

                    FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).
                            child("Class").child(UtilsCommon.getCurrentClass(NoteAddActivity.this).getName() +
                            UtilsCommon.getCurrentClass(NoteAddActivity.this).getSection()).child("Notes").
                            child(title.getText().toString()).removeValue();
                    Toast.makeText(NoteAddActivity.this, "নোটটি ডিলিট হচ্ছে!", Toast.LENGTH_SHORT).show();


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

    @Override
    protected void onStart() {


        super.onStart();
    }

    @Override
    public void onResume() {


        super.onResume();

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        //TODO: Check the note is edited or not
        currentTitle = title.getText().toString();
        currentContent = content.getText().toString();

        if (previousContent == null || previousTitle == null) {
            isEdited = false;
        } else {
            isEdited = !previousTitle.equals(currentTitle) || !previousContent.equals(currentContent);
        }


        if (isEdited) {

            try {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("সতর্কীকরণ");
                alertDialog.setIcon(R.drawable.warning_for_add);
                alertDialog.setMessage("এই নোটটি পরিবর্তন করা হয়েছে। সেভ করতে চান?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "সেভ করুন",
                        (dialog, which) -> {


                            Notes.setheading(title.getText().toString().trim());
                            Notes.setContent(content.getText().toString().trim());

                            //CHECK THAT THE ITEM IS UNIQUE
                            for (int i = 0; i < ClassRoomActivity.notesList.size(); i++) {
                                if (ClassRoomActivity.notesList.get(i).getheading().equals(Notes.getheading()) && !previousTitle.equals(title.getText().toString())) {
                                    AlertDialog alertDialog1 = new AlertDialog.Builder(NoteAddActivity.this).create();
                                    alertDialog1.setTitle("সতর্কীকরণ");
                                    alertDialog1.setIcon(R.drawable.warning_for_add);
                                    alertDialog1.setMessage("এই একই শিরোনামের নোট ইতিমধ্যে এই ক্লাসের ডাটাবেজে রয়েছে।নতুন শিরোনাম ইনপুট দিন,ধন্যবাদ।");
                                    alertDialog1.setButton(AlertDialog.BUTTON_NEUTRAL, "ওকে",
                                            (dialog1, which1) -> dialog1.dismiss());
                                    alertDialog1.show();
                                    return;
                                }
                            }

                            if (submitForm()) {
                                //Then remove the old Student data
                                FirebaseCaller.getFirebaseDatabase().child("Users").
                                        child(FirebaseCaller.getUserID()).child("Class").
                                        child(UtilsCommon.getCurrentClass(NoteAddActivity.this).getName()
                                                + UtilsCommon.getCurrentClass(NoteAddActivity.this).getSection()).
                                        child("Notes").child(previousTitle).removeValue();
                                //Then first reinstall previous Student data;
                                FirebaseCaller.getFirebaseDatabase().child("Users").
                                        child(FirebaseCaller.getUserID()).child("Class").
                                        child(UtilsCommon.getCurrentClass(NoteAddActivity.this).getName() +
                                                UtilsCommon.getCurrentClass(NoteAddActivity.this).
                                                        getSection()).child("Notes").
                                        child(Notes.getheading()).setValue(Notes);
                            }
                            Toast.makeText(NoteAddActivity.this, "নোট নোটটি সার্ভারে সেভ হচ্ছে", Toast.LENGTH_SHORT).show();
                            finish();
                            previousTitle = null;
                        });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "বের হোন",
                        (dialog, which) -> finish());
                alertDialog.show();
            }catch (Exception e){
                UtilsCommon.handleError(e);
            }



        } else {
            finish();
        }


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

                case R.id.title:
                    validateNoteTitle();
                    break;
                case R.id.content:
                    // validateRoll();
                    break;
            }
        }
    }

}
