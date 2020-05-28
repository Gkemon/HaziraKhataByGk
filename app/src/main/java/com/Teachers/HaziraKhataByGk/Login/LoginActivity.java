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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.Teachers.HaziraKhataByGk.BuildConfig;
import com.Teachers.HaziraKhataByGk.HelperClassess.FirebasePhoneAuthBuilder;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.Listener.CommonCallback;
import com.Teachers.HaziraKhataByGk.MainActivity;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.ResetPasswordActivity;
import com.Teachers.HaziraKhataByGk.SignupActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by uy on 12/2/2017.
 */


//TODO Tutorial for FB loging http://androidbash.com/firebase-facebook-login-tutorial-android/
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static String email;
    public FirebaseAuth auth;
    public Button btnSignup, btnEmailLogin, btnReset, btnSignOut, btnChangeEmail, help, btnGuest, btnPhone;
    public TextInputLayout passLayout;
    public Context context;
    Activity activity;
    LinearLayout llMain;
    LinearLayout llBtnLoginPlaceHolderBellow;
    LinearLayout llBtnLoginPlaceHolderUpper;
    TextView tvTimer;
    @BindView(R.id.tl_phone)
    View tlPhone;
    @BindView(R.id.tl_pin)
    View tlPin;
    @BindView(R.id.tl_email)
    View tlEmail;
    @BindView(R.id.pass_layout)
    View tlPass;
    private EditText etEmail, etPassword, etPhone, etPin;
    private View vTimerLayout;
    private ProgressBar progressBar;
    private ImageView logo;

    public static Intent getFacebookIntent(String url, Context context) {

        PackageManager pm = context.getPackageManager();
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {

        }

        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public void initView() {
        ButterKnife.bind(this);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        btnSignup = findViewById(R.id.btn_signup);
        btnEmailLogin = findViewById(R.id.btn_login);
        btnReset = findViewById(R.id.btn_reset_password);
        btnChangeEmail = findViewById(R.id.change_email);
        btnSignOut = findViewById(R.id.btn_signOut);
        logo = findViewById(R.id.logo);
        help = findViewById(R.id.help);
        passLayout = findViewById(R.id.pass_layout);
        btnGuest = findViewById(R.id.btn_guest);
        vTimerLayout = findViewById(R.id.timer_layout);
        etPhone = findViewById(R.id.et_phone);
        etPin = findViewById(R.id.et_code);
        btnPhone = findViewById(R.id.btn_phone);
        tvTimer = findViewById(R.id.tv_timer);
        llMain = findViewById(R.id.ll_main);
        llBtnLoginPlaceHolderBellow = findViewById(R.id.ll_phone_btn_holder);
        llBtnLoginPlaceHolderUpper = findViewById(R.id.ll_phone_btn_holder_upper);
    }

    public void setUpPhoneAuth() {
        etPhone.setEnabled(false);
        etPhone.setAlpha(0.5f);
        FirebasePhoneAuthBuilder.getInstance()
                .setPhoneNumber(etPhone.getText().toString())
                .setPin(etPin.getText().toString())
                .setActivity(activity)
                .setCodeGettingCallBack(new CommonCallback<Boolean>() {
                    @Override
                    public void onFailure(String r) {
                        etPhone.setEnabled(true);
                        etPhone.setAlpha(1);
                        tlPin.setVisibility(View.VISIBLE);
                        vTimerLayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onSuccess() {
                        tlPin.setVisibility(View.VISIBLE);
                        vTimerLayout.setVisibility(View.VISIBLE);
                    }
                })
                .setVerificationCallBack(new CommonCallback<Boolean>() {
                    @Override
                    public void onSuccess() {
                        etPhone.setEnabled(true);
                        etPhone.setAlpha(1);
                        UtilsCommon.debugLog("Phone auth response : ");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(String r) {
                        etPhone.setEnabled(true);
                        etPhone.setAlpha(1);
                        UtilsCommon.showToast("Phone auth error: " + r);
                        UtilsCommon.debugLog("Phone auth error: " + r);
                    }
                })
                .setTimerCallBack(new CommonCallback<Long>() {
                    @Override
                    public void onWait(int sec) {
                        tvTimer.setText(String.format("%d", sec));
                    }

                    @Override
                    public void onFailure(String r) {
                        etPhone.setEnabled(true);
                        etPhone.setAlpha(1);
                        UtilsCommon.debugLog("Phone auth timer error: " + r);
                    }
                })
                .build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        activity = this;
        context = this;
        setContentView(R.layout.activity_of_login);
        initView();

        if(!BuildConfig.DEBUG){
            etEmail.getText().clear();
            etPassword.getText().clear();
        }

        if (getIntent() != null && getIntent().getStringExtra("FLAG") != null) {
            if (getIntent().getStringExtra("FLAG").equals("OUTSIDE")) {
                if (auth.getCurrentUser() != null) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            } else {
                if (auth.getCurrentUser() != null) {

                    if (UtilsCommon.isValideString(auth.getCurrentUser().getPhoneNumber())) {
                        btnPhone.setVisibility(View.GONE);
                        tlPin.setVisibility(View.GONE);
                        tlPhone.setVisibility(View.VISIBLE);
                        etPhone.setText(auth.getCurrentUser().getPhoneNumber());
                        btnEmailLogin.setVisibility(View.VISIBLE);
                        btnSignOut.setVisibility(View.VISIBLE);
                    } else {
                        email = auth.getCurrentUser().getEmail();
                        tlEmail.setVisibility(View.VISIBLE);
                        tlPass.setVisibility(View.GONE);
                        btnEmailLogin.setVisibility(View.GONE);
                        btnGuest.setVisibility(View.GONE);

                        hideAllPhoneAuthWidget();
                    }
                }
            }
        }


        btnGuest.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, MainActivity.class)));


        final Intent resetIntent = new Intent(LoginActivity.this, ResetPasswordActivity.class);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            try {
                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                alertDialog.setMessage("আপনার তথ্যের সুরক্ষার জন্য আপনাকে অবশ্যই সাইন-ইন অথবা সাইন-আপ করতে" +
                        " হবে ।আগে একাউন্ট না খুলে থাকলে ফোন নাম্বার দিয়ে অথবা ইমেইল দিয়ে একাউন্ট খুলুন আর একাউন্ট " +
                        "করা থাকলে ইমেইল এবং পাসওয়ার্ড ব্যবহার করে \"লগিন করুন\" বাটন ক্লিক করুন।যদি বুঝতে সমস্যা " +
                        "হয় তাহলে সবার নিচের সাহায্যের বাটনে ক্লিক করুন।");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "ওকে",
                        (dialog, which) -> dialog.dismiss());
                alertDialog.show();
            } catch (Exception e) {

            }
        }
        if (email != null) {
            etEmail.setText(email);
            btnSignup.setVisibility(View.GONE);
            btnEmailLogin.setVisibility(View.GONE);
            passLayout.setVisibility(View.GONE);
            btnSignOut.setVisibility(View.VISIBLE);
            btnReset.setText("পাসওয়ার্ড পরিবর্তন করুন");
            resetIntent.putExtra("FLAG", "INSIDE");
        } else {
            btnChangeEmail.setVisibility(View.GONE);
        }


        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnChangeEmail.setOnClickListener(v -> {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
            LayoutInflater inflater = activity.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.custom_delete_dialauge, null);
            dialogBuilder.setView(dialogView);
            final EditText edt = dialogView.findViewById(R.id.custom_delete_dialauge_text);
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
                                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_LONG).show();
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
            email = null;
            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
                    getFacebookIntent(context.getString(R.string.help_fb_url), context);
            startActivity(intent4);


        });


        btnPhone.setOnClickListener(this);
        btnEmailLogin.setOnClickListener(this);

    }

    //CHECK INTERNET CONNECTIVITY
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnected();
    }

    public void doEmailLogin() {
        if (!isOnline()) {
            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
            alertDialog.setMessage("দুঃখিত ,ইন্টারনেট সংযোগ নেই । দয়া করে ইন্টারনেট সংযোগ চালু করুন ,ধন্যবাদ");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "ওকে",
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
                    Log.d("GK", "OnFailure " + e.getMessage());
                }
            });
        } catch (Exception e) {


            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
            alertDialog.setMessage(" আপনার ডিভাইসের সমস্যার কারনে লগিন হচ্ছেনা । দয়া করে আবার চেষ্টা করুন অথবা ডেভেলপারকে জানান " +
                    "ফেসবুক গ্রুপে পোস্ট করে,ধন্যবাদ । সমস্যাটি হল : " + e.getMessage());
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "পোস্ট দিন",
                    (dialog, which) -> {

                        Intent intent5 = getFacebookIntent
                                (getString(R.string.help_fb_url),
                                        LoginActivity.this);

                        try {
                            startActivity(intent5);
                        } catch (Exception e1) {
                            Toast.makeText(LoginActivity.this, "ERROR " + e1.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        dialog.dismiss();
                    });
            alertDialog.show();

        }
    }

    @Override
    public void onClick(View v) {


        if (v.getId() == btnEmailLogin.getId()) {
            btnEmailLogin.setAlpha(0.0f);


            hideAllPhoneAuthWidget();
            showEmailAuthWidget();

            llBtnLoginPlaceHolderUpper.removeAllViews();
            llBtnLoginPlaceHolderBellow.removeAllViews();
            llBtnLoginPlaceHolderBellow.addView(btnPhone);


            if (btnEmailLogin.getText().toString().equals("লগিন করুন"))
                doEmailLogin();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                btnEmailLogin.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),
                        R.color.colorDeepRedOrange));
                btnEmailLogin.setText("লগিন করুন");
                btnEmailLogin.animate().alpha(1.0f).setDuration(1500);

                btnPhone.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),
                        R.color.parrot));
                btnPhone.setText("ফোন নাম্বার দিয়ে লগিন করুন");
                showVisibileAnimation(btnPhone);
            }

        } else if (v.getId() == btnPhone.getId()) {
            btnPhone.setAlpha(0.0f);
            hideAllEmailAuthWidget();
            showPhoneAuthWidget();

            llBtnLoginPlaceHolderBellow.removeAllViews();
            llBtnLoginPlaceHolderUpper.removeAllViews();
            showInvisibleAnimation(btnPhone);
            llBtnLoginPlaceHolderUpper.addView(btnPhone);


            if (btnPhone.getText().toString().equals("লগিন করুন")) {
                showVisibileAnimation(tlPin);
                showVisibileAnimation(vTimerLayout);
                setUpPhoneAuth();
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                btnPhone.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),
                        R.color.colorDeepRedOrange));
                btnPhone.setText("লগিন করুন");
                showVisibileAnimation(btnPhone);

                btnEmailLogin.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),
                        R.color.parrot));
                btnEmailLogin.setText("ইমেইল দিয়ে লগিন করুন");
                showVisibileAnimation(btnEmailLogin);
            }


        }


    }

    void hideAllPhoneAuthWidget() {

        showInvisibleAnimation(tlPhone);
        showInvisibleAnimation(tlPin);
        showInvisibleAnimation(vTimerLayout);
        showInvisibleAnimation(btnSignup);
        showInvisibleAnimation(btnReset);

    }

    void hideAllEmailAuthWidget() {
        showInvisibleAnimation(tlEmail);
        showInvisibleAnimation(tlPass);
        showInvisibleAnimation(btnChangeEmail);
        showInvisibleAnimation(btnReset);
        showInvisibleAnimation(btnSignOut);
    }

    void showPhoneAuthWidget() {
        showVisibileAnimation(tlPhone);
    }

    void showEmailAuthWidget() {

        showVisibileAnimation(tlPass);
        showVisibileAnimation(tlEmail);
        showVisibileAnimation(etEmail);
        showVisibileAnimation(etPassword);
        showVisibileAnimation(tlPass);
        showVisibileAnimation(btnChangeEmail);
        showVisibileAnimation(btnReset);
        showVisibileAnimation(btnSignup);
        showVisibileAnimation(btnSignOut);

    }

    void showVisibileAnimation(View v) {
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

    void showInvisibleAnimation(View v) {
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



