package com.Teachers.HaziraKhataByGk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.Teachers.HaziraKhataByGk.widget.text_show_activity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by uy on 12/1/2017.
 */

public class previous_question_activity extends AppCompatActivity {
    Activity activity;
    Button button1,button2,button3,button4,button5;
    public LinearLayout adlayout;
    public AdView mAdView;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        //setContentView(R.layout.text_view_with_share_fab_and_header_image);
        setContentView(R.layout.previous_year_questions);
        button1=(Button)findViewById(R.id.no1);
        button2=(Button)findViewById(R.id.no2);
        button3=(Button)findViewById(R.id.no3);
        button4=(Button)findViewById(R.id.no4);
        button5=(Button)findViewById(R.id.no5);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        // Check the LogCat to get your test device ID
                        .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                        .build();

                // Load ads into Interstitial Ads
                mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {

                        Intent intent=new Intent(activity,text_show_activity.class);
                        intent.putExtra("title","2");
                        startActivity(intent);
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        Intent intent=new Intent(activity,text_show_activity.class);
                        intent.putExtra("title","2");
                        startActivity(intent);
                        super.onAdClosed();
                    }
                });

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        // Check the LogCat to get your test device ID
                        .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                        .build();

                // Load ads into Interstitial Ads
                mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {

                        Intent intent=new Intent(activity,text_show_activity.class);
                        intent.putExtra("title","3");
                        startActivity(intent);
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        Intent intent=new Intent(activity,text_show_activity.class);
                        intent.putExtra("title","3");
                        startActivity(intent);
                        super.onAdClosed();
                    }
                });

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        // Check the LogCat to get your test device ID
                        .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                        .build();

                // Load ads into Interstitial Ads
                mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {

                        Intent intent=new Intent(activity,text_show_activity.class);
                        intent.putExtra("title","4");
                        startActivity(intent);
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        Intent intent=new Intent(activity,text_show_activity.class);
                        intent.putExtra("title","4");
                        startActivity(intent);
                        super.onAdClosed();
                    }
                });

            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        // Check the LogCat to get your test device ID
                        .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                        .build();

                // Load ads into Interstitial Ads
                mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {

                        Intent intent=new Intent(activity,text_show_activity.class);
                        intent.putExtra("title","5");
                        startActivity(intent);
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        Intent intent=new Intent(activity,text_show_activity.class);
                        intent.putExtra("title","5");
                        startActivity(intent);
                        super.onAdClosed();
                    }
                });


            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        // Check the LogCat to get your test device ID
                        .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                        .build();

                // Load ads into Interstitial Ads
                mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        Intent intent=new Intent(activity,text_show_activity.class);
                        intent.putExtra("title","6");
                        startActivity(intent);
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        Intent intent=new Intent(activity,text_show_activity.class);
                        intent.putExtra("title","6");
                        startActivity(intent);
                        super.onAdClosed();
                    }
                });


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


//TODO: FOR INTERSTIALAD
        mInterstitialAd = new InterstitialAd(this);
        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.Interstitial_info_activity));

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

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
}

