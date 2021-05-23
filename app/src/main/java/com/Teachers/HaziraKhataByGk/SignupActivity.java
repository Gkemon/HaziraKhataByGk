package com.Teachers.HaziraKhataByGk;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.HelperClasses.UtilsCommon;
import com.Teachers.HaziraKhataByGk.HelperClasses.ViewUtils.BaseActivity;
import com.Teachers.HaziraKhataByGk.Login.LoginActivity;

/**
 * Created by GK on 12/2/2017.
 */

public class SignupActivity extends BaseActivity {
    public Button btnSignIn, btnSignUp, help, btnGuest;
    public Context context;
    private EditText inputEmail, inputPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_of_signup);
        //Get Firebase auth instance
        btnSignIn = (Button) findViewById(R.id.button_to_sign_in);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.et_email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnGuest = (Button) findViewById(R.id.btn_guest);
        help = (Button) findViewById(R.id.help);
        Log.d("GK", "email ");
        Log.d("GK", "password ");
        context = this;
        btnGuest.setOnClickListener(v -> startActivity(new Intent(SignupActivity.this, MainActivity.class)));

        btnSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            intent.putExtra("FLAG", "OUTSIDE");
            startActivity(intent);
            finish();
        });

        btnSignUp.setOnClickListener(v -> {

            //Check internet connection
            if (!isOnline()) {
                AlertDialog alertDialog = new AlertDialog.Builder(SignupActivity.this).create();
                alertDialog.setMessage("দুঃখিত ,ইন্টারনেট সংযোগ নেই । দয়া করে ইন্টারনেট সংযোগ চালু করুন ,ধন্যবাদ");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "ওকে",
                        (dialog, which) -> dialog.dismiss());
                alertDialog.show();
                return;
            }


            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), "ইমেইল এড্রেস দিন", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "পাসওয়ার্ড দিন", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(getApplicationContext(), R.string.minimum_password, Toast.LENGTH_SHORT).show();
                return;
            }
            Log.d("GK", "email " + email);
            Log.d("GK", "password " + password);

            progressBar.setVisibility(View.VISIBLE);


            try {


                FirebaseCaller.getAuth().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, task -> {

                            progressBar.setVisibility(View.GONE);
                            // If sign in fails, display a message toTime the user. If sign in succeeds
                            // the auth state listener will be notified and logic toTime handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(SignupActivity.this, "সাইন-আপে সমস্যা হচ্ছে : " + task.getException(),
                                        Toast.LENGTH_LONG).show();
                            } else {
                                startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                Log.d("GK", "else");
                                finish();
                            }
                        });
            } catch (Exception e) {


                AlertDialog alertDialog = new AlertDialog.Builder(SignupActivity.this).create();
                alertDialog.setMessage(" আপনার ডিভাইসের সমস্যার কারনে সাইন আপ হচ্ছেনা । দয়া করে আবার চেষ্টা করুন অথবা ডেভেলপারকে জানান ফেসবুক গ্রুপে পোস্ট করে,ধন্যবাদ । সমস্যাটি হল : " + e.getMessage());
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "পোস্ট দিন",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                UtilsCommon.openWithFaceBook("https://www.facebook.com/groups/2035798976667483/permalink/2066665843580796/", SignupActivity.this);

                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }

        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilsCommon.openWithFaceBook("https://www.facebook.com/notes/%E0%A6%B9%E0%A6%BE%E0%A6%9C%E0%A6%BF%E0%A6%B0%E0%A6%BE-%E0%A6%96%E0%A6%BE%E0%A6%A4%E0%A6%BE-%E0%A6%B6%E0%A6%BF%E0%A6%95%E0%A7%8D%E0%A6%B7%E0%A6%95-%E0%A6%B8%E0%A6%BE%E0%A6%AA%E0%A7%8B%E0%A6%B0%E0%A7%8D%E0%A6%9F-%E0%A6%95%E0%A6%AE%E0%A6%BF%E0%A6%89%E0%A6%A8%E0%A6%BF%E0%A6%9F%E0%A6%BF/%E0%A6%B9%E0%A6%BE%E0%A6%9C%E0%A6%BF%E0%A6%B0%E0%A6%BE-%E0%A6%96%E0%A6%BE%E0%A6%A4%E0%A6%BE-%E0%A6%8F%E0%A6%AA%E0%A7%87%E0%A6%B0-%E0%A6%AC%E0%A7%8D%E0%A6%AF%E0%A6%AC%E0%A6%B9%E0%A6%BE%E0%A6%B0%E0%A6%AC%E0%A6%BF%E0%A6%A7%E0%A6%BF/2045598845687496/", SignupActivity.this);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        intent.putExtra("FLAG", "OUTSIDE");
        startActivity(intent);
        finish();
    }

}
