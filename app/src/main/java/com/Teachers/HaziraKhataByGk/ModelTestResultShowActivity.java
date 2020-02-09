package com.Teachers.HaziraKhataByGk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.Teachers.HaziraKhataByGk.ModelTestActivity.wrong_Answer_correct_list;

public class ModelTestResultShowActivity extends AppCompatActivity {
    public static FirebaseAuth auth;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public static FirebaseUser mFirebaseUser;
    String wrong_answer_text, score_text;
    TextView wrong_answer;
    Button score, share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        wrong_answer_text = "নিচে আপনার ভুল উত্তরগুলোর সঠিক উত্তরসমূহ দেয়া হল।\n\n";


        for (int i = 0; i < wrong_Answer_correct_list.size(); i++) {
            String[] temp = wrong_Answer_correct_list.get(i);
            wrong_answer_text = wrong_answer_text + " " + temp[0] + "\n" + temp[1] + "\n\n";
        }
        Log.d("GK", wrong_Answer_correct_list.size() + " wrong_Answer_correct_list in result activity");


        wrong_answer = (TextView) findViewById(R.id.wrong_answer);
        score = (Button) findViewById(R.id.score);

        String total = getIntent().getStringExtra("total");

        String temp = "মোট নম্বর ছিল " + total + " ।আপনি পেয়েছেন " + ModelTestActivity.correct_answer + "।\nঅভিনন্দন আপনাকে।";
        score.setText(temp);
        wrong_answer.setText(wrong_answer_text);
        share = (Button) findViewById(R.id.result_share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "শিক্ষক নিবন্ধন মডেল টেষ্টে মোট ২৫ নম্বরের পরীক্ষায় " + "আমার স্কোর হল " + ModelTestActivity.correct_answer + "\n\n আয়োজনে : #হাজিরা_খাতা \n বাংলাভাষায় প্রথম ক্লাস ম্যানেজমেন্ট মোবাইল এপ ।\n ডাউনলোড লিংক : ");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "শেয়ার করুন।"));

            }
        });


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
        super.onBackPressed();
    }


}
