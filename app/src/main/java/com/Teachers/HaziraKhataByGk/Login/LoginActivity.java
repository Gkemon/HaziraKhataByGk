package com.Teachers.HaziraKhataByGk.Login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.Teachers.HaziraKhataByGk.HelperClassess.FirebasePhoneAuthBuilder;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.Listener.CommonCallback;
import com.Teachers.HaziraKhataByGk.MainActivity;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.ResetPasswordActivity;
import com.Teachers.HaziraKhataByGk.SignupActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.OnClick;

/**
 * Created by uy on 12/2/2017.
 */


//TODO Tutorial for FB loging http://androidbash.com/firebase-facebook-login-tutorial-android/
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etEmail, etPassword,etPhone,etPin;
    private View vTimerLayout;
    public  FirebaseAuth auth;
    private ProgressBar progressBar;
    public Button btnSignup, btnEmailLogin, btnReset,btnSignOut,btnChangeEmail,help,btnGuest,btnPhone;
    public TextInputLayout passLayout;
    private ImageView logo;
    public  static String email;
    Activity activity;
    public Context context;
    LinearLayout llMain;
    LinearLayout llBtnLoginPlaceHolderBellow;
    LinearLayout llBtnLoginPlaceHolderUpper;
    TextView tvTimer;


    public void initView(){
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnEmailLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        btnChangeEmail=(Button)findViewById(R.id.change_email);
        btnSignOut=(Button)findViewById(R.id.btn_signOut);
        logo=(ImageView)findViewById(R.id.logo);
        help=(Button)findViewById(R.id.help);
        passLayout=(TextInputLayout)findViewById(R.id.pass_layout);
        btnGuest=findViewById(R.id.btn_guest);
        vTimerLayout=findViewById(R.id.timer_layout);
        etPhone=findViewById(R.id.et_phone);
        etPin=findViewById(R.id.et_code);
        btnPhone=findViewById(R.id.btn_phone);
        tvTimer=findViewById(R.id.tv_timer);
        llMain=findViewById(R.id.ll_main);
        llBtnLoginPlaceHolderBellow =findViewById(R.id.ll_phone_btn_holder);
        llBtnLoginPlaceHolderUpper = findViewById(R.id.ll_phone_btn_holder_upper);
    }

    public void setUpPhoneAuth(){
        FirebasePhoneAuthBuilder.getInstance()
                .setPhoneNumber(etPhone.getText().toString())
                .setActivity(activity)
                .setVerificationCallBack(new CommonCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean response) {
                        UtilsCommon.debugLog("Phone auth response : "+response);
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }

                    @Override
                    public void onFailure(String r) {
                        UtilsCommon.debugLog("Phone auth error: "+r);
                    }
                })
                .setTimerCallBack(new CommonCallback<Long>() {
                    @Override
                    public void onWait(int sec) {
                        tvTimer.setText(String.format("%d", sec));
                    }

                    @Override
                    public void onFailure(String r) {
                        UtilsCommon.debugLog("Phone auth timer error: "+r);
                    }
                })

                .build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        activity=this;
        context=this;

        if(getIntent()!=null&&getIntent().getStringExtra("FLAG")!=null) {
            if (!getIntent().getStringExtra("FLAG").equals("INSIDE")) {
                if (auth.getCurrentUser() == null) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            }
            else{
                if (auth.getCurrentUser() != null) {
                    email=auth.getCurrentUser().getEmail();
                }
            }
        }

        setContentView(R.layout.activity_of_login);

        initView();

        
        btnGuest.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this,MainActivity.class)));


        final Intent resetIntent=new Intent(LoginActivity.this, ResetPasswordActivity.class);
            if(email!=null){
                etEmail.setText(email);
                btnSignup.setVisibility(View.GONE);
                btnEmailLogin.setVisibility(View.GONE);
                passLayout.setVisibility(View.GONE);
                btnSignOut.setVisibility(View.VISIBLE);
                btnReset.setText("পাসওয়ার্ড পরিবর্তন করুন");
                resetIntent.putExtra("FLAG","INSIDE");
            }
            else
            {

                btnChangeEmail.setVisibility(View.GONE);
                btnSignOut.setVisibility(View.GONE);

                try{
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setMessage("আপনার তথ্যের সুরক্ষার জন্য আপনাকে অবশ্যই সাইন-ইন অথবা সাইন-আপ করতে" +
                            " হবে ।আগে একাউন্ট না খুলে থাকলে ফোন নাম্বার দিয়ে অথবা ইমেইল দিয়ে একাউন্ট খুলুন আর একাউন্ট " +
                            "করা থাকলে ইমেইল এবং পাসওয়ার্ড ব্যবহার করে \"লগিন করুন\" বাটন ক্লিক করুন।যদি বুঝতে সমস্যা " +
                            "হয় তাহলে সবার নিচের সাহায্যের বাটনে ক্লিক করুন।");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"ওকে",
                            (dialog, which) -> dialog.dismiss());
                    alertDialog.show();
                }catch (Exception e){

                }


            }

 

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnChangeEmail.setOnClickListener(v -> {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
            LayoutInflater inflater = activity.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.custom_delete_dialauge, null);
            dialogBuilder.setView(dialogView);
            final EditText edt =  dialogView.findViewById(R.id.custom_delete_dialauge_text);
            dialogBuilder.setIcon(R.drawable.warnig_for_delete);
            dialogBuilder.setTitle("ইমেইল পরিবর্তন");
            dialogBuilder.setMessage("আপনি যদি ইমেইল এড্রেস পরিবর্তন করতে চান তাহলে নিচে নতুন ইমেইলটি ইনপুট দিন");
            dialogBuilder.setPositiveButton("পরিবর্তন করুন ", (dialog, whichButton) -> {
                if (auth.getCurrentUser() != null && !edt.getText().toString().trim().equals("")) {

                    auth.getCurrentUser().updateEmail(edt.getText().toString().trim())
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    auth.signOut();

                                    progressBar.setVisibility(View.GONE);
                                } else {

                                    Toast.makeText(context,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                                }

                            });
                    finish();
                }
            });
            dialogBuilder.setNegativeButton("বাদ দিন", (dialog, whichButton) -> {
            });
            AlertDialog b = dialogBuilder.create();
            b.show();

        });

                btnSignOut.setOnClickListener(v -> {
                    //sign out method
                    auth.signOut();
                    email=null;
                    Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                    LoginActivity.this.finish();
                    intent.putExtra("FLAG", "OUTSIDE");
                    startActivity(intent);
                    finish();
                });
                btnSignup.setOnClickListener(v -> {
                    startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                    finish();
                });

                btnReset.setOnClickListener(v -> startActivity(resetIntent));
                help.setOnClickListener(view -> {
                    Intent intent4 =
                            getFacebookIntent(context.getString(R.string.help_fb_url),context);
                    startActivity(intent4);


                });


               btnPhone.setOnClickListener(this);
               btnEmailLogin.setOnClickListener(this);

            }

    //CHECK INTERNET CONNECTIVITY
    public  boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm!=null&&cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnected();
    }

    public void doEmailLogin(){
        if(!isOnline()){
            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
            alertDialog.setMessage("দুঃখিত ,ইন্টারনেট সংযোগ নেই । দয়া করে ইন্টারনেট সংযোগ চালু করুন ,ধন্যবাদ");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"ওকে",
                    (dialog, which) -> dialog.dismiss());
            alertDialog.show();
            return;
        }


        String email = etEmail.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "ইমেইল এড্রেস দিন", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "পাসওয়ার্ড দিন", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);


        try {

            auth = FirebaseAuth.getInstance();
            //authenticate user
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, task -> {

                        // If sign in fails, display a message toTime the user. If sign in succeeds
                        // the auth state listener will be notified and logic toTime handle the
                        // signed in user can be handled in the listener.
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                etPassword.setError(getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("GK","OnFailure "+e.getMessage());
                }
            });
        }
        catch (Exception e){


            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
            alertDialog.setMessage(" আপনার ডিভাইসের সমস্যার কারনে লগিন হচ্ছেনা । দয়া করে আবার চেষ্টা করুন অথবা ডেভেলপারকে জানান ফেসবুক গ্রুপে পোস্ট করে,ধন্যবাদ । সমস্যাটি হল : "+e.getMessage());
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"পোস্ট দিন",
                    (dialog, which) -> {

                        Intent intent5 =  getFacebookIntent("https://www.facebook.com/groups/2035798976667483/permalink/2066665843580796/",LoginActivity.this);

                        try {
                            startActivity(intent5);
                        }
                        catch (Exception e1){
                            Toast.makeText(LoginActivity.this,"ERROR "+ e1.getMessage(),Toast.LENGTH_LONG).show();
                        }

                        dialog.dismiss();
                    });
            alertDialog.show();

        }
    }

    public static Intent getFacebookIntent(String url,Context context) {

        PackageManager pm = context.getPackageManager();
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        }
        catch (PackageManager.NameNotFoundException ignored) {

        }

        return new Intent(Intent.ACTION_VIEW, uri);
    }

    @Override
    public void onClick(View v) {


        if(v.getId()==btnEmailLogin.getId()){
            btnEmailLogin.setAlpha(0.0f);


            hideAllPhoneAuthWidget();
            showEmailAuthWidget();

            llBtnLoginPlaceHolderUpper.removeAllViews();
            llBtnLoginPlaceHolderBellow.removeAllViews();
            llBtnLoginPlaceHolderBellow.addView(btnPhone);




            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                btnEmailLogin.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),
                        R.color.colorDeepRedOrange));
                btnEmailLogin.setText("লগিন করুন");
                btnEmailLogin.animate().alpha(1.0f).setDuration(1500);

                btnPhone.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),
                        R.color.parrot));
                btnPhone.setText("ফোন নাম্বার দিয়ে লগিন করুন");
            }
            doEmailLogin();
        }
        else if(v.getId()==btnPhone.getId()){
            btnPhone.setAlpha(0.0f);

            hideAllEmailAuthWidget();
            showPhoneAuthWidget();

            llBtnLoginPlaceHolderBellow.removeAllViews();
            llBtnLoginPlaceHolderUpper.removeAllViews();
            llBtnLoginPlaceHolderUpper.addView(btnPhone);



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                btnPhone.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),
                        R.color.colorDeepRedOrange));
                btnPhone.setText("লগিন করুন");
                btnPhone.animate().alpha(1.0f).setDuration(1500);

                btnEmailLogin.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),
                        R.color.parrot));
                btnEmailLogin.setText("ইমেইল দিয়ে লগিন করুন");
            }
            setUpPhoneAuth();
        }


    }

    void hideAllPhoneAuthWidget(){
        showInvisibleAnimation(etPhone);
        showInvisibleAnimation(etPin);
        showInvisibleAnimation(vTimerLayout);
    }
    void hideAllEmailAuthWidget(){
        showInvisibleAnimation(passLayout);
        showInvisibleAnimation(etEmail);
        showInvisibleAnimation(etPassword);
        showInvisibleAnimation(btnChangeEmail);
        showInvisibleAnimation(btnReset);
    }
    void showPhoneAuthWidget(){
        showVisibileAnimation(etPhone);
        showVisibileAnimation(etPin);
        showVisibileAnimation(vTimerLayout);
    }
    void showEmailAuthWidget(){

        showVisibileAnimation(passLayout);
        showVisibileAnimation(etEmail);
        showVisibileAnimation(etPassword);
        showVisibileAnimation(btnChangeEmail);
        showVisibileAnimation(btnReset);

    }

    void showVisibileAnimation(View v){
        v.animate()
                .alpha(1.0f)
                .setDuration(1500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        v.setVisibility(View.VISIBLE);
                    }
                });
    }
    void showInvisibleAnimation(View v){
        v.animate()
                .alpha(0.0f)
                .setDuration(1500)
                .setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                v.setVisibility(View.GONE);
            }
        });
    }
}



