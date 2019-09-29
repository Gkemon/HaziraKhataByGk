package com.Teachers.HaziraKhataByGk;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static android.view.Gravity.CENTER;
import static com.Teachers.HaziraKhataByGk.Login.LoginActivity.email;

/**
 * Created by uy on 12/2/2017.
 */

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText inputEmail;
    private Button btnReset, btnBack;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private TextView heading,des;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        inputEmail = (EditText) findViewById(R.id.et_email);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        btnBack = (Button) findViewById(R.id.btn_back);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        heading=(TextView)findViewById(R.id.head);
        des=(TextView)findViewById(R.id.descrition);
        context=this;
        auth = FirebaseAuth.getInstance();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(getIntent().getStringExtra("FLAG")!=null){
            if(getIntent().getStringExtra("FLAG").equals("INSIDE")){
                heading.setText("আপনি কি পাসওয়ার্ড পরিবর্তন করতে চান?");
                heading.setGravity(CENTER);
                des.setText("নিজের ইমেইলটিতে পাসওয়ার্ড পরিবর্তন করার জন্য যাবতীয় নির্দেশনা প্রেরণ করা হবে।");
                inputEmail.setText(email);
                btnReset.setText("পাসওয়ার্ড পরিবর্তন করুন");
            }
        }

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setMessage("রেজিস্টার্ড করা ইমেইল ইনপুট দিন");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"ওকে",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }

                progressBar.setVisibility(View.VISIBLE);
                if(email.length()==0){
                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setMessage("দয়া করে আপনার এই এপের একাউন্ট খোলার জন্য আগে ব্যবহার করা ইমেইল এড্রেসটি দিন।");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"ওকে",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                else
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    AlertDialog alertDialog = new AlertDialog.Builder(ResetPasswordActivity.this).create();
                                    alertDialog.setMessage("পাসওয়ার্ড রিসেট করার জন্য প্রয়োজনীয় নির্দেশনা আপনার ইমেইল পাঠানো হয়েছে।আপনার ইমেইল ইনবক্স চেক করুন।");
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"ওকে",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    alertDialog.show();

                                } else {

                                    AlertDialog alertDialog = new AlertDialog.Builder(ResetPasswordActivity.this).create();
                                    alertDialog.setIcon(R.drawable.warnig_for_delete);
                                    alertDialog.setMessage("আপনার ইমেল অথবা ফোন নম্বরটি রেজিস্টার্ড করা হয়নি অথবা অভ্যন্তরীণ কোন সমস্যা দেখা দিয়েছে এপে। একটু পর আবার চেষ্টা করুন,ধন্যবাদ।");
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"ওকে",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    alertDialog.show();
                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }

}
