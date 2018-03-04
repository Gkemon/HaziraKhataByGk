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


public class totthojhuri extends Fragment {
    InterstitialAd mInterstitialAd;
    public totthojhuri() {
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
        View view=inflater.inflate(R.layout.thottho_jhuri, container, false);
        Button button1,button2,button3,button4,button5,button6,button7,button8,button9,button10,button11;
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

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        // Check the LogCat to get your test device ID
                        .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                        .build();

                openInAppBrowser("http://www.moedu.gov.bd/");

                // Load ads into Interstitial Ads
              //  mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        openInAppBrowser("http://www.moedu.gov.bd/");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        openInAppBrowser("http://www.moedu.gov.bd/");
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

                openInAppBrowser("http://www.mopme.gov.bd/");

                // Load ads into Interstitial Ads
               // mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        openInAppBrowser("http://www.mopme.gov.bd/");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        openInAppBrowser("http://www.mopme.gov.bd/");
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

                openInAppBrowser("http://www.educationboard.gov.bd/");

                // Load ads into Interstitial Ads
               // mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        openInAppBrowser("http://www.educationboard.gov.bd/");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        openInAppBrowser("http://www.educationboard.gov.bd/");
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

                openInAppBrowser("http://www.dshe.gov.bd/");

                // Load ads into Interstitial Ads
               // mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        openInAppBrowser("http://www.dshe.gov.bd/");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        openInAppBrowser("http://www.dshe.gov.bd/");
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

                openInAppBrowser("http://www.dpe.gov.bd/");

                // Load ads into Interstitial Ads
              //  mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        openInAppBrowser("http://www.dpe.gov.bd/");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        openInAppBrowser("http://www.dpe.gov.bd/");
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
                openInAppBrowser("http://www.dpe.gov.bd/");
                // Load ads into Interstitial Ads
              //  mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        openInAppBrowser("http://www.dpe.gov.bd/");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        openInAppBrowser("http://www.dpe.gov.bd/");
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

                openInAppBrowser("http://www.ntrca.gov.bd/");
                // Load ads into Interstitial Ads
               // mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        openInAppBrowser("http://www.ntrca.gov.bd/");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        openInAppBrowser("http://www.ntrca.gov.bd/");
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

                openInAppBrowser("https://www.teachers.gov.bd/");
                // Load ads into Interstitial Ads
               // mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        openInAppBrowser("https://www.teachers.gov.bd/");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        openInAppBrowser("https://www.teachers.gov.bd/");
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

                openInAppBrowser("http://shikkhok.com/");
                // Load ads into Interstitial Ads
               // mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        openInAppBrowser("http://shikkhok.com/");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        openInAppBrowser("http://shikkhok.com/");
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

                openInAppBrowser("https://bn.khanacademy.org/");
                // Load ads into Interstitial Ads
               // mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        openInAppBrowser("https://bn.khanacademy.org/");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        openInAppBrowser("https://bn.khanacademy.org/");
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
                openInAppBrowser("http://10minuteschool.com/");
                // Load ads into Interstitial Ads
               // mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        openInAppBrowser("http://10minuteschool.com/");
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        openInAppBrowser("http://10minuteschool.com/");
                        super.onAdClosed();
                    }
                });

            }
        });

        return view;
    }
    private void openInAppBrowser(String url) {

        //TODO: for opening webview
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
