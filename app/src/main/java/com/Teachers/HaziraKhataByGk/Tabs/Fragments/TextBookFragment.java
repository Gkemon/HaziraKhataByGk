package com.Teachers.HaziraKhataByGk.Tabs.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.Teachers.HaziraKhataByGk.MainActivity;
import com.Teachers.HaziraKhataByGk.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class TextBookFragment extends Fragment {
    InterstitialAd mInterstitialAd;
    public TextBookFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.text_books, container, false);
        Button button1,button2,button3,button4,button5,button6,button7,button8,button9,button10,
                button11,button12,button13,button14,button15,button16,button17,button18,button19,button20,
        button21,button22,button23,button24,button25,button26;

//TODO: FOR INTERSTIALAD
        if(MainActivity.context==null&&getActivity()==null){
            mInterstitialAd = new InterstitialAd(container.getContext());
            // set the ad unit ID
            mInterstitialAd.setAdUnitId("ca-app-pub-8499573931707406/1629454676");
        }
        else {
            mInterstitialAd = new InterstitialAd(getActivity());
            // set the ad unit ID
            mInterstitialAd.setAdUnitId("ca-app-pub-8499573931707406/1629454676");
        }

        button1=(Button)view.findViewById(R.id.btn_dialog_1);
        button2=(Button)view.findViewById(R.id.btn_dialog_2);
        button3=(Button)view.findViewById(R.id.btn_dialog_3);
        button4=(Button)view.findViewById(R.id.btn_dialog_4);
        button5=(Button)view.findViewById(R.id.btn_dialog_5);
        button6=(Button)view.findViewById(R.id.btn_dialog_6);
        button7=(Button)view.findViewById(R.id.btn_dialog_7);
        button8=(Button)view.findViewById(R.id.btn_dialog_8);
        button9=(Button)view.findViewById(R.id.btn_dialog_9);
        button10=(Button)view.findViewById(R.id.btn_dialog_10);
        button11=(Button)view.findViewById(R.id.btn_dialog_11);
        button12=(Button)view.findViewById(R.id.btn_dialog_12);
        button13=(Button)view.findViewById(R.id.btn_dialog_13);
        button14=(Button)view.findViewById(R.id.btn_dialog_14);
        button15=(Button)view.findViewById(R.id.btn_dialog_15);
        button16=(Button)view.findViewById(R.id.btn_dialog_16);
        button17=(Button)view.findViewById(R.id.btn_dialog_17);
        button18=(Button)view.findViewById(R.id.btn_dialog_18);
        button19=(Button)view.findViewById(R.id.btn_dialog_19);
        button20=(Button)view.findViewById(R.id.btn_dialog_20);
        button21=(Button)view.findViewById(R.id.btn_dialog_21);
        button22=(Button)view.findViewById(R.id.btn_dialog_22);
        button23=(Button)view.findViewById(R.id.btn_dialog_23);
        button24=(Button)view.findViewById(R.id.btn_dialog_24);
        button25=(Button)view.findViewById(R.id.btn_dialog_25);
        button26=(Button)view.findViewById(R.id.btn_dialog_26);
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

                        openInAppBrowser("https://www.edutubebd.com/nctb/index.php");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        openInAppBrowser("https://www.edutubebd.com/nctb/index.php");
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
                        openInAppBrowser("http://digitalcontent.ictd.gov.bd/index.php/tutorial/digitalcontent");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        openInAppBrowser("http://digitalcontent.ictd.gov.bd/index.php/tutorial/digitalcontent");
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
                        openInAppBrowser("http://www.nctb.gov.bd/site/page/56324be4-3c4b-46fe-aab9-d552a5b9eb8b/%E0%A6%AA%E0%A7%8D%E0%A6%B0%E0%A6%A5%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF-");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        openInAppBrowser("http://www.nctb.gov.bd/site/page/56324be4-3c4b-46fe-aab9-d552a5b9eb8b/%E0%A6%AA%E0%A7%8D%E0%A6%B0%E0%A6%A5%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF-");
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
                        openInAppBrowser("http://www.nctb.gov.bd/site/page/31b9c154-efe6-4bec-8272-f954678dbf32/%E0%A6%A6%E0%A7%8D%E0%A6%AC%E0%A6%BF%E0%A6%A4%E0%A7%80%E0%A7%9F-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF-");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        openInAppBrowser("http://www.nctb.gov.bd/site/page/31b9c154-efe6-4bec-8272-f954678dbf32/%E0%A6%A6%E0%A7%8D%E0%A6%AC%E0%A6%BF%E0%A6%A4%E0%A7%80%E0%A7%9F-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF-");
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
                        openInAppBrowser("http://www.nctb.gov.bd/site/page/1ad75766-47ae-4a22-8acc-86392c7bd41f/%E0%A6%A4%E0%A7%83%E0%A6%A4%E0%A7%80%E0%A7%9F-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF-");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        openInAppBrowser("http://www.nctb.gov.bd/site/page/1ad75766-47ae-4a22-8acc-86392c7bd41f/%E0%A6%A4%E0%A7%83%E0%A6%A4%E0%A7%80%E0%A7%9F-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF-");
                        super.onAdClosed();
                    }
                });

            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
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
                        openInAppBrowser("http://www.nctb.gov.bd/site/page/3ccca3f3-975d-438d-8dbe-78fc5a834130/%E0%A6%9A%E0%A6%A4%E0%A7%81%E0%A6%B0%E0%A7%8D%E0%A6%A5-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF-");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        openInAppBrowser("http://www.nctb.gov.bd/site/page/3ccca3f3-975d-438d-8dbe-78fc5a834130/%E0%A6%9A%E0%A6%A4%E0%A7%81%E0%A6%B0%E0%A7%8D%E0%A6%A5-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF-");
                        super.onAdClosed();
                    }
                });

            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
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

                        openInAppBrowser("http://www.nctb.gov.bd/site/page/aba8bf27-e6a8-4453-9ae1-5f2de6111f20/%E0%A6%AA%E0%A6%9E%E0%A7%8D%E0%A6%9A%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF-");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        openInAppBrowser("http://www.nctb.gov.bd/site/page/aba8bf27-e6a8-4453-9ae1-5f2de6111f20/%E0%A6%AA%E0%A6%9E%E0%A7%8D%E0%A6%9A%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF-");
                        super.onAdClosed();
                    }
                });
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
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
                        openInAppBrowser("http://www.nctb.gov.bd/site/page/ef5209a4-230b-42ce-ab0f-c49e873a9593/%E0%A6%AA%E0%A7%8D%E0%A6%B0%E0%A6%A5%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        openInAppBrowser("http://www.nctb.gov.bd/site/page/ef5209a4-230b-42ce-ab0f-c49e873a9593/%E0%A6%AA%E0%A7%8D%E0%A6%B0%E0%A6%A5%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdClosed();
                    }
                });

            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
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

                        openInAppBrowser("http://www.nctb.gov.bd/site/page/d8ebcaeb-c98d-48a3-adf8-e78020748801/%E0%A6%A6%E0%A7%8D%E0%A6%AC%E0%A6%BF%E0%A6%A4%E0%A7%80%E0%A7%9F-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        openInAppBrowser("http://www.nctb.gov.bd/site/page/d8ebcaeb-c98d-48a3-adf8-e78020748801/%E0%A6%A6%E0%A7%8D%E0%A6%AC%E0%A6%BF%E0%A6%A4%E0%A7%80%E0%A7%9F-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdClosed();
                    }
                });
            }
        });
        button10.setOnClickListener(new View.OnClickListener() {
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

                        openInAppBrowser("http://www.nctb.gov.bd/site/page/4e98af28-53a3-48ff-adfc-f66377d882ac/%E0%A6%A4%E0%A7%83%E0%A6%A4%E0%A7%80%E0%A7%9F-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        openInAppBrowser("http://www.nctb.gov.bd/site/page/4e98af28-53a3-48ff-adfc-f66377d882ac/%E0%A6%A4%E0%A7%83%E0%A6%A4%E0%A7%80%E0%A7%9F-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdClosed();
                    }
                });
            }
        });
        button11.setOnClickListener(new View.OnClickListener() {
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

                        openInAppBrowser("http://www.nctb.gov.bd/site/page/6f3bb8bb-0c12-48b9-98b2-dc4e9e7359d2/%E0%A6%9A%E0%A6%A4%E0%A7%81%E0%A6%B0%E0%A7%8D%E0%A6%A5-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        openInAppBrowser("http://www.nctb.gov.bd/site/page/6f3bb8bb-0c12-48b9-98b2-dc4e9e7359d2/%E0%A6%9A%E0%A6%A4%E0%A7%81%E0%A6%B0%E0%A7%8D%E0%A6%A5-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdClosed();
                    }
                });
            }
        });
        button12.setOnClickListener(new View.OnClickListener() {
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

                        openInAppBrowser("http://www.nctb.gov.bd/site/page/62a71d08-1cd4-4fbb-bfd8-6c9469698162/%E0%A6%AA%E0%A6%9E%E0%A7%8D%E0%A6%9A%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");

                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        openInAppBrowser("http://www.nctb.gov.bd/site/page/62a71d08-1cd4-4fbb-bfd8-6c9469698162/%E0%A6%AA%E0%A6%9E%E0%A7%8D%E0%A6%9A%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");

                        super.onAdClosed();
                    }
                });}
        });
        button13.setOnClickListener(new View.OnClickListener() {
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

                        openInAppBrowser("http://www.nctb.gov.bd/site/page/941298d0-22df-4948-ac94-dd9cd2dd366b/%E0%A6%B7%E0%A6%B7%E0%A7%8D%E0%A6%A0-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        openInAppBrowser("http://www.nctb.gov.bd/site/page/941298d0-22df-4948-ac94-dd9cd2dd366b/%E0%A6%B7%E0%A6%B7%E0%A7%8D%E0%A6%A0-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdClosed();
                    }
                });
            }
        });
        button14.setOnClickListener(new View.OnClickListener() {
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

                        openInAppBrowser("http://www.nctb.gov.bd/site/page/14950c2f-3867-4633-8f8a-3515f3acb7b1/%E0%A6%B8%E0%A6%AA%E0%A7%8D%E0%A6%A4%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        openInAppBrowser("http://www.nctb.gov.bd/site/page/14950c2f-3867-4633-8f8a-3515f3acb7b1/%E0%A6%B8%E0%A6%AA%E0%A7%8D%E0%A6%A4%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdClosed();
                    }
                });
            }
        });
        button15.setOnClickListener(new View.OnClickListener() {
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

                        openInAppBrowser("http://www.nctb.gov.bd/site/page/f536e59b-2310-416d-bbce-fe9068193c6f/%E0%A6%85%E0%A6%B7%E0%A7%8D%E0%A6%9F%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        openInAppBrowser("http://www.nctb.gov.bd/site/page/f536e59b-2310-416d-bbce-fe9068193c6f/%E0%A6%85%E0%A6%B7%E0%A7%8D%E0%A6%9F%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdClosed();
                    }
                });
            }
        });
        button16.setOnClickListener(new View.OnClickListener() {
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
                        openInAppBrowser("http://www.nctb.gov.bd/site/page/079828a6-18e6-44c1-9a2d-59f8d223199c/%E0%A6%A8%E0%A6%AC%E0%A6%AE-%E0%A6%A6%E0%A6%B6%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        openInAppBrowser("http://www.nctb.gov.bd/site/page/079828a6-18e6-44c1-9a2d-59f8d223199c/%E0%A6%A8%E0%A6%AC%E0%A6%AE-%E0%A6%A6%E0%A6%B6%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdClosed();
                    }
                });

            }
        });
        button17.setOnClickListener(new View.OnClickListener() {
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
                        openInAppBrowser("http://www.nctb.gov.bd/site/page/56324be4-3c4b-46fe-aab9-d552a5b9eb8b/nolink/%E0%A6%8F%E0%A6%95%E0%A6%BE%E0%A6%A6%E0%A6%B6-%E0%A6%A6%E0%A7%8D%E0%A6%AC%E0%A6%BE%E0%A6%A6%E0%A6%B6-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        openInAppBrowser("http://www.nctb.gov.bd/site/page/56324be4-3c4b-46fe-aab9-d552a5b9eb8b/nolink/%E0%A6%8F%E0%A6%95%E0%A6%BE%E0%A6%A6%E0%A6%B6-%E0%A6%A6%E0%A7%8D%E0%A6%AC%E0%A6%BE%E0%A6%A6%E0%A6%B6-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdClosed();
                    }
                });
            }
        });
        button18.setOnClickListener(new View.OnClickListener() {
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

                        openInAppBrowser("http://www.nctb.gov.bd/site/page/3dabcc83-6655-4517-893e-6f183ea2667c/%E0%A6%AA%E0%A7%8D%E0%A6%B0%E0%A6%A5%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        openInAppBrowser("http://www.nctb.gov.bd/site/page/3dabcc83-6655-4517-893e-6f183ea2667c/%E0%A6%AA%E0%A7%8D%E0%A6%B0%E0%A6%A5%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdClosed();
                    }
                });
            }
        });
        button19.setOnClickListener(new View.OnClickListener() {
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
                        openInAppBrowser("http://www.nctb.gov.bd/site/page/99144a1d-d66a-426e-a473-6583be497a65/%E0%A6%A6%E0%A7%8D%E0%A6%AC%E0%A6%BF%E0%A6%A4%E0%A7%80%E0%A7%9F-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF-");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        openInAppBrowser("http://www.nctb.gov.bd/site/page/99144a1d-d66a-426e-a473-6583be497a65/%E0%A6%A6%E0%A7%8D%E0%A6%AC%E0%A6%BF%E0%A6%A4%E0%A7%80%E0%A7%9F-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF-");
                        super.onAdClosed();
                    }
                });

            }
        });
        button20.setOnClickListener(new View.OnClickListener() {
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

                        openInAppBrowser("http://www.nctb.gov.bd/site/page/9370195a-4b3e-4410-bcfa-b4118879429d/%E0%A6%A4%E0%A7%83%E0%A6%A4%E0%A7%80%E0%A7%9F-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF-");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        openInAppBrowser("http://www.nctb.gov.bd/site/page/9370195a-4b3e-4410-bcfa-b4118879429d/%E0%A6%A4%E0%A7%83%E0%A6%A4%E0%A7%80%E0%A7%9F-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF-");
                        super.onAdClosed();
                    }
                });
            }
        });
        button21.setOnClickListener(new View.OnClickListener() {
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
                        openInAppBrowser("http://www.nctb.gov.bd/site/page/d5fb758c-8d6f-4045-86b2-37c818322c29/%E0%A6%AA%E0%A6%9E%E0%A7%8D%E0%A6%9A%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF-");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        openInAppBrowser("http://www.nctb.gov.bd/site/page/d5fb758c-8d6f-4045-86b2-37c818322c29/%E0%A6%AA%E0%A6%9E%E0%A7%8D%E0%A6%9A%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF-");
                        super.onAdClosed();
                    }
                });
            }
        });
        button22.setOnClickListener(new View.OnClickListener() {
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
                        openInAppBrowser("http://www.nctb.gov.bd/site/page/d5fb758c-8d6f-4045-86b2-37c818322c29/%E0%A6%AA%E0%A6%9E%E0%A7%8D%E0%A6%9A%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF-");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        openInAppBrowser("http://www.nctb.gov.bd/site/page/d5fb758c-8d6f-4045-86b2-37c818322c29/%E0%A6%AA%E0%A6%9E%E0%A7%8D%E0%A6%9A%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF-");
                        super.onAdClosed();
                    }
                });

            }
        });
        button23.setOnClickListener(new View.OnClickListener() {
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

                        openInAppBrowser("http://www.nctb.gov.bd/site/page/56324be4-3c4b-46fe-aab9-d552a5b9eb8b/nolink/%E0%A6%B7%E0%A6%B7%E0%A7%8D%E0%A6%A0-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        openInAppBrowser("http://www.nctb.gov.bd/site/page/56324be4-3c4b-46fe-aab9-d552a5b9eb8b/nolink/%E0%A6%B7%E0%A6%B7%E0%A7%8D%E0%A6%A0-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdClosed();
                    }
                });
            }
        });
        button24.setOnClickListener(new View.OnClickListener() {
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

                        openInAppBrowser("http://www.nctb.gov.bd/site/page/56324be4-3c4b-46fe-aab9-d552a5b9eb8b/nolink/%E0%A6%B8%E0%A6%AA%E0%A7%8D%E0%A6%A4%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        openInAppBrowser("http://www.nctb.gov.bd/site/page/56324be4-3c4b-46fe-aab9-d552a5b9eb8b/nolink/%E0%A6%B8%E0%A6%AA%E0%A7%8D%E0%A6%A4%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdClosed();
                    }
                });
            }
        });
        button25.setOnClickListener(new View.OnClickListener() {
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
                        openInAppBrowser("http://www.nctb.gov.bd/nolink/%E0%A6%A8%E0%A6%AC%E0%A6%AE-%E0%A6%A6%E0%A6%B6%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        openInAppBrowser("http://www.nctb.gov.bd/nolink/%E0%A6%A8%E0%A6%AC%E0%A6%AE-%E0%A6%A6%E0%A6%B6%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");
                        super.onAdClosed();
                    }
                });

            }
        });
        button26.setOnClickListener(new View.OnClickListener() {
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
                        openInAppBrowser("http://www.nctb.gov.bd/nolink/%E0%A6%A8%E0%A6%AC%E0%A6%AE-%E0%A6%A6%E0%A6%B6%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");

                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        openInAppBrowser("http://www.nctb.gov.bd/nolink/%E0%A6%A8%E0%A6%AC%E0%A6%AE-%E0%A6%A6%E0%A6%B6%E0%A6%AE-%E0%A6%B6%E0%A7%8D%E0%A6%B0%E0%A7%87%E0%A6%A3%E0%A6%BF");

                        super.onAdClosed();
                    }
                });
               }
        });


        return view;
    }

    private void openInAppBrowser(String url) {
//        Intent intent = new Intent(MainActivity.activity, BrowsingActivity.class_room);
//        intent.putExtra("URL", url);
//        startActivity(intent);

        //TODO: for opening default browser
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);

    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

}
