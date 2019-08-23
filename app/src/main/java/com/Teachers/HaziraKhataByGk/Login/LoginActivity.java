package com.Teachers.HaziraKhataByGk.Login;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.Teachers.HaziraKhataByGk.MainActivity;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.ResetPasswordActivity;
import com.Teachers.HaziraKhataByGk.SignupActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by uy on 12/2/2017.
 */


//TODO Tutorial for FB loging http://androidbash.com/firebase-facebook-login-tutorial-android/
public class LoginActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    public  FirebaseAuth auth;
    private ProgressBar progressBar;
    public Button btnSignup, btnLogin, btnReset,btnSignOut,btnChangeEmail,help,btnGuest;
    public TextInputLayout passLayout;
    private ImageView logo;
    public  static String email;
    Activity activity;
    private CallbackManager callbackManager;
    public Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        activity=this;
        context=this;

        if(getIntent()!=null&&getIntent().getStringExtra("FLAG")!=null) {
            if (!getIntent().getStringExtra("FLAG").equals("INSIDE")) {
                if (auth.getCurrentUser() != null) {
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



        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        btnChangeEmail=(Button)findViewById(R.id.change_email);
        btnSignOut=(Button)findViewById(R.id.btn_signOut);
        logo=(ImageView)findViewById(R.id.logo);
        help=(Button)findViewById(R.id.help);
        passLayout=(TextInputLayout)findViewById(R.id.pass_layout);
        btnGuest=findViewById(R.id.btn_guest);

        
        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        });


        final Intent resetIntent=new Intent(LoginActivity.this, ResetPasswordActivity.class);
            if(email!=null){
                inputEmail.setText(email);
                btnSignup.setVisibility(View.GONE);
                btnLogin.setVisibility(View.GONE);
                passLayout.setVisibility(View.GONE);
                btnSignOut.setVisibility(View.VISIBLE);
                btnReset.setText("পাসওয়ার্ড পরিবর্তন করুন");
                resetIntent.putExtra("FLAG","INSIDE");
                //loginButton.setVisibility(View.GONE);
            }
            else
            {
                //TODO: FB LOGIN
                FacebookSdk.sdkInitialize(getApplicationContext());
                AppEventsLogger.activateApp(this);
               //  loginButton.setReadPermissions("email");
                callbackManager = CallbackManager.Factory.create();
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        AuthCredential credential= FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
                        auth.signInWithCredential(credential).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {

                                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                                    alertDialog.setMessage("দুঃখিত,ফেসবুক দিয়ে সরাসরি লগিন হতে সমস্যা হচ্ছে।দয়া করে আপনার ইমেইল দিয়ে লগিন করুন।");
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"ওকে",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    alertDialog.show();
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }
                        });


                        Intent intent =new Intent(activity,MainActivity.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


                btnChangeEmail.setVisibility(View.GONE);
                btnSignOut.setVisibility(View.GONE);


                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                alertDialog.setMessage("আপনার তথ্যের সুরক্ষার জন্য আপনাকে অবশ্যই সাইন-ইন অথবা সাইন-আপ করতে হবে ।আগে একাউন্ট না খুলে থাকলে \"একাউন্ট খুলুন \" বাটনে ক্লিক করুন আর একাউন্ট করা থাকলে ইমেইল এবং পাসওয়ার্ড ব্যবহার করে \"লগিন করুন\" বাটন ক্লিক করুন");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"ওকে",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });
                alertDialog.show();

            }

 

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();


        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {

                                                  AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                                                  LayoutInflater inflater = activity.getLayoutInflater();
                                                  final View dialogView = inflater.inflate(R.layout.custom_delete_dialauge, null);
                                                  dialogBuilder.setView(dialogView);
                                                  final EditText edt =  dialogView.findViewById(R.id.custom_delete_dialauge_text);
                                                  dialogBuilder.setIcon(R.drawable.warnig_for_delete);
                                                  dialogBuilder.setTitle("ইমেইল পরিবর্তন");
                                                  dialogBuilder.setMessage("আপনি যদি ইমেইল এড্রেস পরিবর্তন করতে চান তাহলে নিচে নতুন ইমেইলটি ইনপুট দিন");
                                                  dialogBuilder.setPositiveButton("পরিবর্তন করুন ", new DialogInterface.OnClickListener() {
                                                      public void onClick(DialogInterface dialog, int whichButton) {
                                                          if (auth.getCurrentUser() != null && !edt.getText().toString().trim().equals("")) {

                                                              auth.getCurrentUser().updateEmail(edt.getText().toString().trim())
                                                                      .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                          @Override
                                                                          public void onComplete(@NonNull Task<Void> task) {
                                                                              if (task.isSuccessful()) {
                                                                                  auth.signOut();

                                                                                  progressBar.setVisibility(View.GONE);
                                                                              } else {

                                                                                  Toast.makeText(context,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                                                                              }

                                                                          }
                                                                      });
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
                                          });

                btnSignOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //sign out method
                        auth.signOut();
                        email=null;
                        Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                        LoginActivity.this.finish();
                        intent.putExtra("FLAG", "OUTSIDE");
                        startActivity(intent);
                        finish();
                    }
                });
                btnSignup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                        finish();
                    }
                });

                btnReset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(resetIntent);

                    }
                });
                help.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent4 =  getFacebookIntent(context.getString(R.string.help_fb_url),context);

                        startActivity(intent4);


                    }
                });



                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!isOnline()){
                            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                            alertDialog.setMessage("দুঃখিত ,ইন্টারনেট সংযোগ নেই । দয়া করে ইন্টারনেট সংযোগ চালু করুন ,ধন্যবাদ");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"ওকে",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                            return;
                        }


                        String email = inputEmail.getText().toString().trim();
                        final String password = inputPassword.getText().toString().trim();

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
                                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            // If sign in fails, display a message to the user. If sign in succeeds
                                            // the auth state listener will be notified and logic to handle the
                                            // signed in user can be handled in the listener.
                                            progressBar.setVisibility(View.GONE);
                                            if (!task.isSuccessful()) {
                                                // there was an error
                                                if (password.length() < 6) {
                                                    inputPassword.setError(getString(R.string.minimum_password));
                                                } else {
                                                    Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                                }
                                            } else {
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
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
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            Intent intent5 =  getFacebookIntent("https://www.facebook.com/groups/2035798976667483/permalink/2066665843580796/",LoginActivity.this);

                                            try {
                                                startActivity(intent5);
                                            }
                                            catch (Exception e){
                                                Toast.makeText(LoginActivity.this,"ERROR "+e.getMessage(),Toast.LENGTH_LONG).show();
                                            }

                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();

                        }

                    }
                });

            }
    //FaceBook
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //CHECK INTERNET CONNECTIVITY
    public  boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm!=null&&cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnected();
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

    }



