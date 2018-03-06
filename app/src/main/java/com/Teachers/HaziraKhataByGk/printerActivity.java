package com.Teachers.HaziraKhataByGk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.Teachers.HaziraKhataByGk.model.AttendenceData;
import com.Teachers.HaziraKhataByGk.model.student;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

import static com.Teachers.HaziraKhataByGk.AttendanceActivity.perStudentTotalAttendenceData;
import static com.Teachers.HaziraKhataByGk.AttendanceActivity.studentListForPrintActiviyFromAttendenceActivity;
import static com.Teachers.HaziraKhataByGk.AttendanceActivity.year;

public class printerActivity extends AppCompatActivity {

    //AD
    InterstitialAd mInterstitialAd;
    private Boolean isInterstitalAdEnable;

   public static String  jan,fab,mar,apr,may,june,july,aug,sep,oct,nov,dec;
   public Button janButton,febButton,marButton,aprButton,mayButton,juneButton,julyButton,augButton,sepButton,octButton,novButton,decButton;

    public LinearLayout adlayout,emptyView;
    public AdView mAdView;


    int janTotal=0,febTotal=0,marTotal=0,aprTotal=0,mayTotal=0,juneTotal=0,julyTotal=0,augTotal=0,sepTotal=0,octTotal=0,novTotal=0,decTotal=0;
    int janAttended=0,febAttended=0,marAttended=0,aprAttended=0,mayAttended=0,juneAttended=0,julyAttended=0,augAttended=0,sepAttended=0,novAttended=0,decAttended=0,octAttended=0;

 public  int JanAverage=0,FebAverage=0,MarAverage=0,AprAverage=0,MayAverage=0,JuneAverage=0,JulyAverage=0,AugAverage=0,SepAverage=0,OctAverage=0,NovAverage=0,DeceAverage=0,TotalStudent=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //HIDING NOTIFICATION BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_month_data);
        student student;

        emptyView=findViewById(R.id.emptyViewOnPrintActivity);
        janButton=findViewById(R.id.jan);
        febButton=findViewById(R.id.fab);
        marButton=findViewById(R.id.mar);
        aprButton=findViewById(R.id.apr);
        mayButton=findViewById(R.id.may);
        juneButton=findViewById(R.id.june);
        julyButton=findViewById(R.id.july);
        augButton=findViewById(R.id.aug);
        sepButton=findViewById(R.id.sep);
        octButton=findViewById(R.id.oct);
        novButton=findViewById(R.id.nov);
        decButton=findViewById(R.id.dec);

        JanAverage=0;
        FebAverage=0;
        MarAverage=0;
        AprAverage=0;
        MayAverage=0;
        JuneAverage=0;
        JulyAverage=0;
        AugAverage=0;
        SepAverage=0;
        OctAverage=0;
        NovAverage=0;
        DeceAverage=0;


        jan="জানুয়ারি ("+year+") মাসের রেকর্ড \n\n";fab ="ফেব্রুয়ারি ("+year+") মাসের রেকর্ড \n\n";mar="মার্চ ("+year+") মাসের রেকর্ড \n\n";apr="এপ্রিল ("+year+") মাসের রেকর্ড \n\n" ;may ="মে ("+year+") মাসের রেকর্ড \n\n";june ="জুন ("+year+") মাসের রেকর্ড \n\n";july="জুলাই ("+year+") মাসের রেকর্ড \n\n" ;aug="আগষ্ট ("+year+") মাসের রেকর্ড \n\n";sep="সেপ্টেম্বর ("+year+") মাসের রেকর্ড \n\n";oct =" অক্টোবর ("+year+") মাসের রেকর্ড \n\n";nov ="নভেম্বর ("+year+") মাসের রেকর্ড \n\n";dec="ডিসেম্বর ("+year+") মাসের রেকর্ড \n\n";

        //TODO: For debugging



//        Log.d("GK",studentListForPrintActiviyFromAttendenceActivity.size()+ " student list name size" );
//        for(int i=0;i<studentListForPrintActiviyFromAttendenceActivity.size();i++){
//            Log.d("GK", " student list name "+studentListForPrintActiviyFromAttendenceActivity.get(i).getId()+ " \n Id:"+studentListForPrintActiviyFromAttendenceActivity.get(i).getId() );
//
//student student1=studentListForPrintActiviyFromAttendenceActivity.get(i);
//            Log.d("GK", "perStudentTotalAttendenceData size  "+perStudentTotalAttendenceData.get(student1.getId()).size());
//
//        }
TotalStudent=studentListForPrintActiviyFromAttendenceActivity.size();
        for (int i = 0; i < studentListForPrintActiviyFromAttendenceActivity.size(); i++) {

            student = studentListForPrintActiviyFromAttendenceActivity.get(i);

           // Log.d("GK",student.getStudentName()+ " student name");
            ArrayList<AttendenceData> attendenceDatalist=new ArrayList<>();

            attendenceDatalist=perStudentTotalAttendenceData.get(student.getId());
           // Log.d("GK",attendenceDatalist.size()+ " attendenceDatalist.size()");

            long temp1=0,attendClass=0,totalAttendPersenten=0,totalClass=0;
            totalClass=attendenceDatalist.size();

            janTotal=0;
            febTotal=0;
            marTotal=0;
            aprTotal=0;
            mayTotal=0;
            juneTotal=0;
            julyTotal=0;
            augTotal=0;
            sepTotal=0;
            octTotal=0;
            novTotal=0;
            decTotal=0;

            janAttended=0;
            febAttended=0;
            marAttended=0;
            aprAttended=0;
            mayAttended=0;
            juneAttended=0;
            julyAttended=0;
            augAttended=0;
            sepAttended=0;
            novAttended=0;
            decAttended=0;
            octAttended=0;


            for (int j=0;j<attendenceDatalist.size();j++) {
                AttendenceData attendenceData;
                attendenceData = attendenceDatalist.get(j);
                String time=attendenceData.getDate();
                String yearWithMonth=time.substring(Math.max(time.length() - 8, 0));
                String month=yearWithMonth.substring(0,Math.min(yearWithMonth.length(), 3));
                String year=year = time.substring(Math.max(time.length() - 4, 0));

                Log.d("GK",month + " month");

                if(month.equals("Jan")){
                    janTotal++;
                    if(attendenceData.getStatus()){
                        janAttended++;
                    }
                    //jan=jan+"\n\n"+
                }
                else if (month.equals("Feb")){
                    febTotal++;
                    if(attendenceData.getStatus()){
                        febAttended++;
                    }
                }
                else if (month.equals("Mar")){
                    marTotal++;
                    if(attendenceData.getStatus()){
                        marAttended++;
                    }
                }
                else if (month.equals("Apr")){
                    aprTotal++;
                    if(attendenceData.getStatus()){
                        aprAttended++;
                    }
                }
                else if (month.equals("May")){
                    mayTotal++;
                    if(attendenceData.getStatus()){
                        mayAttended++;
                    }
                }
                else if (month.equals("Jun")){
                    juneTotal++;
                    if(attendenceData.getStatus()){
                        juneAttended++;
                    }
                }
                else if (month.equals("Jul")){
                    julyTotal++;
                    if(attendenceData.getStatus()){
                        julyAttended++;
                    }
                }
                else if (month.equals("Aug")){
                    augTotal++;
                    if(attendenceData.getStatus()){
                        augAttended++;
                    }
                }
                else if (month.equals("Sep")){
                    sepTotal++;
                    if(attendenceData.getStatus()){
                        sepAttended++;
                    }
                }
                else if (month.equals("Oct")){
                    octTotal++;
                    if(attendenceData.getStatus()){
                        octAttended++;
                    }
                }
                else if (month.equals("Nov")){
                    novTotal++;
                    if(attendenceData.getStatus()){
                        novAttended++;
                    }
                }
                else if (month.equals("Dec")){
                    decTotal++;
                    if(attendenceData.getStatus()){
                        decAttended++;
                    }
                }


            }

            if(janTotal!=0){
                int totalPercentage=(janAttended*100)/janTotal;

                jan=jan + "\n\n" + student.getStudentName() + ".( রোল :" + student.getId() + ")" + "\n" + " মোট ক্লাস :" + janTotal + " উপস্থিতি :"
                        + janAttended + " শতকরা :" + totalPercentage + "%\n";
                JanAverage=JanAverage+totalPercentage;

                //FOR Printing last line
                if(i==(studentListForPrintActiviyFromAttendenceActivity.size()-1)&&TotalStudent!=0){
                    jan=jan+"\n\n এই মাসের গড় উপস্থিতি "+JanAverage/TotalStudent+" %";
                }
            }
            if(febTotal!=0){
                int totalPercentage=(febAttended*100)/febTotal;

                fab=fab + "\n\n" + student.getStudentName() + ".( রোল :" + student.getId() + ")" + "\n" + " মোট ক্লাস :" + febTotal + " উপস্থিতি :"
                        + febAttended + " শতকরা :" + totalPercentage + "%\n";
                FebAverage=FebAverage+totalPercentage;

                if(i==(studentListForPrintActiviyFromAttendenceActivity.size()-1)&&TotalStudent!=0){
                    fab=fab+"\n\n এই মাসের গড় উপস্থিতি "+FebAverage/TotalStudent+" %";
                    Log.d("GK",String.valueOf(FebAverage));
                    Log.d("GK","\n\n"+String.valueOf(TotalStudent));
                }

            }

            if(marTotal!=0){
                int totalPercentage=(marAttended*100)/marTotal;

                mar=mar + "\n\n" + student.getStudentName() + ".( রোল :" + student.getId() + ")" + "\n" + " মোট ক্লাস :" + marTotal + " উপস্থিতি :"
                        + marAttended + " শতকরা :" + totalPercentage + "%\n";
                MarAverage=MarAverage+totalPercentage;

                if(i==studentListForPrintActiviyFromAttendenceActivity.size()-1&&TotalStudent!=0){
                    mar=mar+"\n\n এই মাসের গড় উপস্থিতি "+MarAverage/TotalStudent+" %";
                }

            }

            if(aprTotal!=0){
                int totalPercentage=(aprAttended*100)/aprTotal;

                apr=apr + "\n\n" + student.getStudentName() + ".( রোল :" + student.getId() + ")" + "\n" + " মোট ক্লাস :" + aprTotal + " উপস্থিতি :"
                        + aprAttended + " শতকরা :" + totalPercentage + "%\n";
                AprAverage=AprAverage+totalPercentage;

                if(i==studentListForPrintActiviyFromAttendenceActivity.size()-1&&TotalStudent!=0){
                    apr=apr+"\n\n এই মাসের গড় উপস্থিতি "+AprAverage/TotalStudent+" %";
                }

            }

            if(mayTotal!=0){
                int totalPercentage=(mayAttended*100)/mayTotal;

                may=may + "\n\n" + student.getStudentName() + ".( রোল :" + student.getId() + ")" + "\n" + " মোট ক্লাস :" + mayTotal + " উপস্থিতি :"
                        + mayAttended + " শতকরা :" + totalPercentage + "%\n";
                MayAverage=MayAverage+totalPercentage;

                if(i==studentListForPrintActiviyFromAttendenceActivity.size()-1&&TotalStudent!=0){
                    may=may+"\n\n এই মাসের গড় উপস্থিতি "+MayAverage/TotalStudent+" %";
                }

            }

            if(juneTotal!=0){

                int totalPercentage=(juneAttended*100)/juneTotal;

                june= june+ "\n\n" + student.getStudentName() + ".( রোল :" + student.getId() + ")" + "\n" + " মোট ক্লাস :" + juneTotal + " উপস্থিতি :"
                        + juneAttended + " শতকরা :" + totalPercentage + "%\n";

                JuneAverage=JuneAverage+totalPercentage;

                if(i==studentListForPrintActiviyFromAttendenceActivity.size()-1&&TotalStudent!=0){
                    june=june+"\n\n এই মাসের গড় উপস্থিতি "+JuneAverage/TotalStudent+" %";
                }

            }

            if(julyTotal!=0){
                int totalPercentage=(julyAttended*100)/julyTotal;

                july=july + "\n\n" + student.getStudentName() + ".( রোল :" + student.getId() + ")" + "\n" + " মোট ক্লাস :" + julyTotal + " উপস্থিতি :"
                        + julyAttended + " শতকরা :" + totalPercentage + "%\n";

                JulyAverage=JulyAverage+totalPercentage;

                if(i==studentListForPrintActiviyFromAttendenceActivity.size()-1&&TotalStudent!=0){
                    july=july+"\n\n এই মাসের গড় উপস্থিতি "+JulyAverage/TotalStudent+" %";
                }

            }

            if(augTotal!=0){
                int totalPercentage=(augAttended*100)/augTotal;

                aug=aug + "\n\n" + student.getStudentName() + ".( রোল :" + student.getId() + ")" + "\n" + " মোট ক্লাস :" + augTotal + " উপস্থিতি :"
                        + augAttended + " শতকরা :" + totalPercentage + "%\n";

                AugAverage=AugAverage+totalPercentage;

                if(i==studentListForPrintActiviyFromAttendenceActivity.size()-1&&TotalStudent!=0){
                    aug=aug+"\n\n এই মাসের গড় উপস্থিতি "+AugAverage/TotalStudent+" %";
                }

            }

            if(sepTotal!=0){
                int totalPercentage=(sepAttended*100)/sepTotal;

                sep=sep + "\n\n" + student.getStudentName() + ".( রোল :" + student.getId() + ")" + "\n" + " মোট ক্লাস :" + sepTotal + " উপস্থিতি :"
                        + sepAttended + " শতকরা :" + totalPercentage + "%\n";

                SepAverage=SepAverage+totalPercentage;

                if(i==studentListForPrintActiviyFromAttendenceActivity.size()-1&&TotalStudent!=0){
                    sep=sep+"\n\n এই মাসের গড় উপস্থিতি "+SepAverage/TotalStudent+" %";
                }

            }

            if(octTotal!=0){
                int totalPercentage=(octAttended*100)/octTotal;

                oct=oct + "\n\n" + student.getStudentName() + ".( রোল :" + student.getId() + ")" + "\n" + " মোট ক্লাস :" + octTotal + " উপস্থিতি :"
                        + octAttended + " শতকরা :" + totalPercentage + "%\n";
                OctAverage=OctAverage+totalPercentage;

                if(i==studentListForPrintActiviyFromAttendenceActivity.size()-1&&TotalStudent!=0){
                    oct=oct+"\n\n এই মাসের গড় উপস্থিতি "+OctAverage/TotalStudent+" %";
                }



            }

            if(novTotal!=0){
                int totalPercentage=(novAttended*100)/novTotal;

                nov=nov + "\n\n" + student.getStudentName() + ".( রোল :" + student.getId() + ")" + "\n" + " মোট ক্লাস :" + novTotal + " উপস্থিতি :"
                        + novAttended + " শতকরা :" + totalPercentage + "%\n";

                NovAverage=NovAverage+totalPercentage;

                if(i==studentListForPrintActiviyFromAttendenceActivity.size()-1&&TotalStudent!=0){
                    nov=nov+"\n\n এই মাসের গড় উপস্থিতি "+NovAverage/TotalStudent+" %";
                }

            }

            if(decTotal!=0){
                int totalPercentage=(decAttended*100)/decTotal;

                dec=dec + "\n\n" + student.getStudentName() + ".( রোল :" + student.getId() + ")" + "\n" + " মোট ক্লাস :" + decTotal + " উপস্থিতি :"
                        + decAttended + " শতকরা :" + totalPercentage + "%\n";

                DeceAverage=DeceAverage+totalPercentage;

                if(i==studentListForPrintActiviyFromAttendenceActivity.size()-1&&TotalStudent!=0){
                    dec=dec+"\n\n এই মাসের গড় উপস্থিতি "+DeceAverage/TotalStudent+" %";
                }
            }
        }

        Log.d("GK",jan);


        if(janTotal==0){
            janButton.setVisibility(View.GONE);
        }
        if(febTotal==0){
            febButton.setVisibility(View.GONE);
        }
        if(marTotal==0){
            marButton.setVisibility(View.GONE);
        }
        if(aprTotal==0){
            aprButton.setVisibility(View.GONE);
        }
        if(mayTotal==0){
            mayButton.setVisibility(View.GONE);
        }
        if(juneTotal==0){
            juneButton.setVisibility(View.GONE);
        }
        if(julyTotal==0){
            julyButton.setVisibility(View.GONE);
        }
        if(augTotal==0){
            augButton.setVisibility(View.GONE);
        }
        if(sepTotal==0){
            sepButton.setVisibility(View.GONE);
        }
        if(octTotal==0){
            octButton.setVisibility(View.GONE);
        }
        if(novTotal==0){
            novButton.setVisibility(View.GONE);
        }
        if(decTotal==0){
            decButton.setVisibility(View.GONE);
        }

        //FOR EMPTY VIEW
        if(janTotal==0&&febTotal==0&&marTotal==0&& aprTotal==0&& mayTotal==0&& juneTotal==0 &&julyTotal==0&& augTotal==0
                &&sepTotal==0 &&octTotal==0&& novTotal==0&& decTotal==0)
            emptyView.setVisibility(View.VISIBLE);
        else emptyView.setVisibility(View.GONE);



        janButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        // Check the LogCat to get your test device ID
                        .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                        .build();

                Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                intent.putExtra("month",jan);
                startActivity(intent);


                // Load ads into Interstitial Ads
               // mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {

                        Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                        intent.putExtra("month",jan);
                        startActivity(intent);
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                        intent.putExtra("month",jan);
                        startActivity(intent);
                        super.onAdClosed();
                    }
                });



            }
        });

        febButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        // Check the LogCat to get your test device ID
                        .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                        .build();


                Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                intent.putExtra("month",fab);
                startActivity(intent);
                // Load ads into Interstitial Ads
               // mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {

                        Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                        intent.putExtra("month",fab);
                        startActivity(intent);
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                        intent.putExtra("month",fab);
                        startActivity(intent);
                        super.onAdClosed();
                    }
                });

//                Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
//                intent.putExtra("month",fab);
//                startActivity(intent);
            }
        });

        marButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        // Check the LogCat to get your test device ID
                        .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                        .build();


                Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                intent.putExtra("month",mar);
                startActivity(intent);
                // Load ads into Interstitial Ads
               // mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {

                        Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                        intent.putExtra("month",mar);
                        startActivity(intent);
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                        intent.putExtra("month",mar);
                        startActivity(intent);
                        super.onAdClosed();
                    }
                });


//
//                Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
//                intent.putExtra("month",mar);
//                startActivity(intent);
            }
        });

        aprButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        // Check the LogCat to get your test device ID
                        .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                        .build();


                Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                intent.putExtra("month",apr);
                startActivity(intent);
                // Load ads into Interstitial Ads
              //  mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {

                        Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                        intent.putExtra("month",apr);
                        startActivity(intent);
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                        intent.putExtra("month",apr);
                        startActivity(intent);
                        super.onAdClosed();
                    }
                });

//
//                Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
//                intent.putExtra("month",apr);startActivity(intent);
            }
        });

        mayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        // Check the LogCat to get your test device ID
                        .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                        .build();

                // Load ads into Interstitial Ads
                //mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {

                        Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                        intent.putExtra("month",may);
                        startActivity(intent);
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                        intent.putExtra("month",may);
                        startActivity(intent);
                        super.onAdClosed();
                    }
                });


                Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                intent.putExtra("month",may);startActivity(intent);
            }
        });

        juneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        // Check the LogCat to get your test device ID
                        .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                        .build();

                // Load ads into Interstitial Ads
                //mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {

                        Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                        intent.putExtra("month",june);
                        startActivity(intent);
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                        intent.putExtra("month",june);
                        startActivity(intent);
                        super.onAdClosed();
                    }
                });

                Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                intent.putExtra("month",june);startActivity(intent);
            }
        });

        julyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        // Check the LogCat to get your test device ID
                        .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                        .build();

                // Load ads into Interstitial Ads
              //  mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {

                        Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                        intent.putExtra("month",july);
                        startActivity(intent);
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                        intent.putExtra("month",july);
                        startActivity(intent);
                        super.onAdClosed();
                    }
                });

                Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                intent.putExtra("month",july);startActivity(intent);
            }
        });

        augButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        // Check the LogCat to get your test device ID
                        .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                        .build();

                // Load ads into Interstitial Ads
              //  mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {

                        Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                        intent.putExtra("month",aug);
                        startActivity(intent);
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                        intent.putExtra("month",aug);
                        startActivity(intent);
                        super.onAdClosed();
                    }
                });


                Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                intent.putExtra("month",aug);startActivity(intent);
            }
        });

        sepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        // Check the LogCat to get your test device ID
                        .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                        .build();

                // Load ads into Interstitial Ads
             //   mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {

                        Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                        intent.putExtra("month",sep);
                        startActivity(intent);
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                        intent.putExtra("month",sep);
                        startActivity(intent);
                        super.onAdClosed();
                    }
                });



                Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                intent.putExtra("month",sep);startActivity(intent);
            }
        });
        octButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        // Check the LogCat to get your test device ID
                        .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                        .build();

                // Load ads into Interstitial Ads
                //mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {

                        Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                        intent.putExtra("month",oct);
                        startActivity(intent);
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                        intent.putExtra("month",oct);
                        startActivity(intent);
                        super.onAdClosed();
                    }
                });


                Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                intent.putExtra("month",oct);startActivity(intent);
            }
        });

        novButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        // Check the LogCat to get your test device ID
                        .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                        .build();

                // Load ads into Interstitial Ads
                //mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {

                        Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                        intent.putExtra("month",nov);
                        startActivity(intent);
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                        intent.putExtra("month",nov);
                        startActivity(intent);
                        super.onAdClosed();
                    }
                });
//

                Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                intent.putExtra("month",nov);startActivity(intent);
            }
        });

        decButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        // Check the LogCat to get your test device ID
                        .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                        .build();

                // Load ads into Interstitial Ads
               // mInterstitialAd.loadAd(adRequest);
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                        intent.putExtra("month",dec);
                        startActivity(intent);
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {

                        Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                        intent.putExtra("month",dec);
                        startActivity(intent);
                        super.onAdClosed();
                    }
                });



                Intent intent =new Intent(printerActivity.this,MonthWiseClassDataActivity.class);
                intent.putExtra("month",dec);startActivity(intent);
            }
        });

//TODO: FOR INTERSTIALAD
        mInterstitialAd = new InterstitialAd(this);
        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.Interstitial_info_activity));


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

    private void showInterstitial() {
//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//        }
    }


}
