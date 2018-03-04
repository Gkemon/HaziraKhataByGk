package com.Teachers.HaziraKhataByGk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.Teachers.HaziraKhataByGk.model_test.wrong_Answer_correct_list;

public class result_activity extends AppCompatActivity {
    String wrong_answer_text,score_text;
    TextView wrong_answer;
    Button score,share;
    public LinearLayout adlayout;
  //  InterstitialAd mInterstitialAd;
    private Boolean isInterstitalAdEnable;
    public AdView mAdView;



    public static FirebaseAuth auth;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public static String mUserId;
    public static FirebaseUser mFirebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
         wrong_answer_text="নিচে আপনার ভুল উত্তরগুলোর সঠিক উত্তরসমূহ দেয়া হল।\n\n";



        for(int i = 0; i< wrong_Answer_correct_list.size(); i++){
             String[] temp= wrong_Answer_correct_list.get(i);
            wrong_answer_text=wrong_answer_text+" "+temp[0]+"\n"+temp[1]+"\n\n";
        }
        Log.d("GK",wrong_Answer_correct_list.size()+" wrong_Answer_correct_list in result activity");


        wrong_answer=(TextView)findViewById(R.id.wrong_answer);
        score=(Button)findViewById(R.id.score);

        String total=getIntent().getStringExtra("total");

        String temp="মোট নম্বর ছিল "+total+" ।আপনি পেয়েছেন "+model_test.correct_answer+"।\nঅভিনন্দন আপনাকে।";
        score.setText(temp);
        wrong_answer.setText(wrong_answer_text);
        share=(Button)findViewById(R.id.result_share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,"শিক্ষক নিবন্ধন মডেল টেষ্টে মোট ২৫ নম্বরের পরীক্ষায় "+"আমার স্কোর হল "+model_test.correct_answer+"\n\n আয়োজনে : #হাজিরা_খাতা \n বাংলাভাষায় প্রথম ক্লাস ম্যানেজমেন্ট মোবাইল এপ ।\n ডাউনলোড লিংক : ");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent,"শেয়ার করুন।"));

            }
        });


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
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
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
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                .build();


//        if(isInterstitalAdEnable)
//        mInterstitialAd.loadAd(adRequest);
//
//        mInterstitialAd.setAdListener(new AdListener() {
//            public void onAdLoaded() {
//                showInterstitial();
//            }
//
//            @Override
//            public void onAdFailedToLoad(int i) {
//
//                super.onAdFailedToLoad(i);
//            }
//
//            @Override
//            public void onAdClosed() {
//
//                super.onAdClosed();
//            }
//        });
        super.onBackPressed();
    }
    private void showInterstitial() {
//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//        }
    }

}
