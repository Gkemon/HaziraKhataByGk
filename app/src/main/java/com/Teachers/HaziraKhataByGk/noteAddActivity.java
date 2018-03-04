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
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.Teachers.HaziraKhataByGk.model.Notes;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uy on 9/7/2017.
 */

public class noteAddActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText title;
    private EditText content;
    private Boolean isInterstitalAdEnable;
    InterstitialAd mInterstitialAd;
    private com.Teachers.HaziraKhataByGk.model.Notes Notes;
    private Button ADD,Save, btnDelete;
    public static String currentTitle,previousTitle,currentContent,previousContent;
    Activity activity;
    boolean isEdited;
    public LinearLayout adlayout,ButtonLayout;
    public AdView mAdView;

    public static FirebaseAuth auth;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public static String mUserId;
    public static FirebaseUser mFirebaseUser;

    public static void start(Context context){
        Intent intent = new Intent(context,noteAddActivity.class);
        context.startActivity(intent);
    }
    public static void start(Context context, Notes Notes){
        Intent intent = new Intent(context, noteAddActivity.class);
        intent.putExtra(ClassRoom_activity.class.getSimpleName(), Notes);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //HIDING NOTIFICATION BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.note_add_activity);
        activity = this;
        title = (EditText) findViewById(R.id.Title);
        content = (EditText) findViewById(R.id.content);
        isEdited=false;


        mInterstitialAd = new InterstitialAd(this);
        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.Interstitial_info_activity));

        //ADD TEXT CHANGE LISTENER
        title.addTextChangedListener(new MyTextWatcher(title));
        content.addTextChangedListener(new MyTextWatcher(content));

        ADD=(Button) findViewById(R.id.ADD);
        Save = (Button) findViewById(R.id.SAVE);
        btnDelete = (Button) findViewById(R.id.DELETE);
        ButtonLayout=(LinearLayout)findViewById(R.id.buttomLinearLayout);
        Notes = getIntent().getParcelableExtra(ClassRoom_activity.class.getSimpleName());
        ADD.setOnClickListener(this);
        Save.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        if (Notes != null) {
            ADD.setVisibility(View.GONE);
            title.setText(Notes.getheading());
            content.setText(Notes.getContent());
            previousTitle=Notes.getheading();
            previousContent=Notes.getContent();
        }
        else{
            Save.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }

    }
    @Override
    public void onClick(View v) {
        if(v==ADD) {
            Notes = new Notes();
            Notes.setheading(title.getText().toString().trim());
            Notes.setContent(content.getText().toString().trim());

            //CHECK THAT THE ITEM IS UNIQUE
            for (int i = 0; i < ClassRoom_activity.notesList.size(); i++) {
                if (ClassRoom_activity.notesList.get(i).getheading().equals(Notes.getheading())) {
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
                MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName() + ClassRoom_activity.classitem.getSection()).child("Notes").child(Notes.getheading()).setValue(Notes);
                Toast.makeText(this, "নোটটি সার্ভারে যুক্ত হয়েছে,ধন্যবাদ ।", Toast.LENGTH_SHORT).show();

                //ADMOB
                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        // Check the LogCat to get your test device ID
                        .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                        .build();

                finish();
                // Load ads into Interstitial Ads
               // mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        finish();
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        finish();
                        super.onAdClosed();
                    }
                });


            }
        }
        else if(v == Save){


               Notes.setheading(title.getText().toString().trim());
               Notes.setContent(content.getText().toString().trim());


            //FIREBASE
            MainActivity.databaseReference=databaseReference;
            MainActivity.mUserId=mUserId;

            MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName()+ClassRoom_activity.classitem.getSection()).child("Notes").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<Notes> NotesList=new ArrayList<Notes>();
                    for(DataSnapshot NoteData:dataSnapshot.getChildren()){
                        Notes Notes=new Notes();
                        Notes=NoteData.getValue(Notes.class);
                        NotesList.add(Notes);
                    }
                    ClassRoom_activity.notesList=NotesList;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            //CHECK THAT THE ITEM IS UNIQUE
            for (int i = 0; i < ClassRoom_activity.notesList.size(); i++) {
                if (ClassRoom_activity.notesList.get(i).getheading().equals(Notes.getheading())&&!previousTitle.equals(title.getText().toString())) {
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


            if(submitForm()){
                //Then remove the old student data
                databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName()+ClassRoom_activity.classitem.getSection()).child("Notes").child(previousTitle).removeValue();
                //Then first reinstall previous student data;
                MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName()+ClassRoom_activity.classitem.getSection()).child("Notes").child(Notes.getheading()).setValue(Notes);

            }

            Toast.makeText(this, "নোট নোটটি সার্ভারে সেভ হচ্ছে", Toast.LENGTH_SHORT).show();
            previousTitle=null;

            //ADMOB
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    // Check the LogCat to get your test device ID
                    .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                    .build();

            finish();

            // Load ads into Interstitial Ads
            //mInterstitialAd.loadAd(adRequest);
            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    showInterstitial();
                }

                @Override
                public void onAdFailedToLoad(int i) {
                    finish();
                    super.onAdFailedToLoad(i);
                }

                @Override
                public void onAdClosed() {
                    finish();
                    super.onAdClosed();
                }
            });

        }else if(v == btnDelete){
            Notes.setheading(title.getText().toString());
            Notes.setContent(content.getText().toString());

            //FOR AVOID SQL INJECTION
            for (int i = 0; i < ClassRoom_activity.notesList.size(); i++) {
                if (ClassRoom_activity.notesList.get(i).getheading().equals(Notes.getheading())&&!previousTitle.equals(title.getText().toString())) {
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

    boolean validateNoteTitle(){
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
                if(edt.getText().toString().trim().equals("DELETE")){

                    MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName()+ClassRoom_activity.classitem.getSection()).child("Notes").child(title.getText().toString()).removeValue();
                    Toast.makeText(noteAddActivity.this, "নোটটি ডিলিট হচ্ছে!", Toast.LENGTH_SHORT).show();

                    //ADMOB
                    AdRequest adRequest = new AdRequest.Builder()
                            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                            // Check the LogCat to get your test device ID
                            .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                            .build();

                    finish();

                    // Load ads into Interstitial Ads
                   // mInterstitialAd.loadAd(adRequest);
                    mInterstitialAd.setAdListener(new AdListener() {
                        public void onAdLoaded() {
                            showInterstitial();
                        }

                        @Override
                        public void onAdFailedToLoad(int i) {
                            finish();
                            super.onAdFailedToLoad(i);
                        }

                        @Override
                        public void onAdClosed() {
                            finish();
                            super.onAdClosed();
                        }
                    });
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


        //ADMOB
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                .build();
        adlayout=findViewById(R.id.ads);
        mAdView = (AdView) findViewById(R.id.adViewInHome);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdClosed() {
                // Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                adlayout.setVisibility(View.GONE);
                // Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAdLeftApplication() {
                // Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });
        mAdView.loadAd(adRequest);


        super.onStart();
    }

    @Override
    public void onResume() {
        //TODO:DATABASE CONNECTION
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();


        //TODO: USER (for FB logic auth throw null pointer exception)
        auth = FirebaseAuth.getInstance();
        mFirebaseUser = auth.getCurrentUser();
        databaseReference.keepSynced(true);
        mUserId=mFirebaseUser.getUid();

        MainActivity.databaseReference=databaseReference;
        MainActivity.mUserId=mUserId;

        if (mAdView != null) {
            mAdView.resume();
        }

        super.onResume();

    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        //TODO: Check the note is edited or not
        currentTitle=title.getText().toString();
        currentContent=content.getText().toString();

        if(previousContent==null||previousTitle==null){
            isEdited=false;
        }
        else {
            if(previousTitle.equals(currentTitle)&&previousContent.equals(currentContent)){
                isEdited=false;
            }
            else
            {
                isEdited=true;
            }
        }



        if(isEdited) {

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("সতর্কীকরণ");
            alertDialog.setIcon(R.drawable.warning_for_add);
            alertDialog.setMessage("এই নোটটি পরিবর্তন করা হয়েছে। সেভ করতে চান?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "সেভ করুন",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {


                            Notes.setheading(title.getText().toString().trim());
                            Notes.setContent(content.getText().toString().trim());

                            //CHECK THAT THE ITEM IS UNIQUE
                            for (int i = 0; i < ClassRoom_activity.notesList.size(); i++) {
                                if (ClassRoom_activity.notesList.get(i).getheading().equals(Notes.getheading()) && !previousTitle.equals(title.getText().toString())) {
                                    AlertDialog alertDialog = new AlertDialog.Builder(noteAddActivity.this).create();
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
                                //Then remove the old student data
                                databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName() + ClassRoom_activity.classitem.getSection()).child("Notes").child(previousTitle).removeValue();
                                //Then first reinstall previous student data;
                                MainActivity.databaseReference.child("Users").child(mUserId).child("Class").child(ClassRoom_activity.classitem.getName() + ClassRoom_activity.classitem.getSection()).child("Notes").child(Notes.getheading()).setValue(Notes);
                            }
                            Toast.makeText(noteAddActivity.this, "নোট নোটটি সার্ভারে সেভ হচ্ছে", Toast.LENGTH_SHORT).show();
                            finish();
                            previousTitle = null;
                        }
                    });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "বের হোন",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {


                            //ADMOB
                            AdRequest adRequest = new AdRequest.Builder()
                                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                                    // Check the LogCat to get your test device ID
                                    .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                                    .build();

                            finish();

                            // Load ads into Interstitial Ads
                           // mInterstitialAd.loadAd(adRequest);
                            mInterstitialAd.setAdListener(new AdListener() {
                                public void onAdLoaded() {
                                    showInterstitial();
                                }

                                @Override
                                public void onAdFailedToLoad(int i) {
                                    finish();
   super.onAdFailedToLoad(i);
                                }

                                @Override
                                public void onAdClosed() {
                                    finish();
                                    super.onAdClosed();
                                }
                            });

                        }
                        });
            alertDialog.show();


        }
        else {
            finish();
        }


    }
    private void showInterstitial() {
//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//        }
    }
}
