package com.Teachers.HaziraKhataByGk.Tabs.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.Teachers.HaziraKhataByGk.MainActivity;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.adapter.JobListAdapter;
import com.Teachers.HaziraKhataByGk.listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.model.JobItems;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import static com.Teachers.HaziraKhataByGk.MainActivity.Job_list;
import static com.Teachers.HaziraKhataByGk.MainActivity.databaseReference;
import static com.Teachers.HaziraKhataByGk.R.id.clickerForJob;
import static com.Teachers.HaziraKhataByGk.R.id.loveClickerForjob;
import static com.Teachers.HaziraKhataByGk.R.id.saveClickerForjob;
import static com.Teachers.HaziraKhataByGk.R.id.shareClickerForjob;

public class jobFragment extends Fragment implements RecyclerItemClickListener {
    public static RecyclerView jobRecycle;
    InterstitialAd mInterstitialAd;
    View view;
    public jobFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        jobRecycle = (RecyclerView) view.findViewById(R.id.jobFragmentRecycle);


        databaseReference.child("Jobs").addValueEventListener(new ValueEventListener() {
            @Override
            public  void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("GK","Jobfragment retriving data from server");
                ArrayList<JobItems> JobItemlist=new ArrayList<JobItems>();
                for(DataSnapshot JobItemsData:dataSnapshot.getChildren()){
                    JobItems Single_JobItems;
                    Single_JobItems=JobItemsData.getValue(JobItems.class);
                    JobItemlist.add(Single_JobItems);
                }
                Job_list=JobItemlist;
                if(Job_list.size()>0&&jobRecycle!=null){
                    Collections.reverse(Job_list);
                    JobListAdapter jobListAdapter = new JobListAdapter(Job_list);
                    jobListAdapter.setOnItemClickListener(jobFragment.this);
                    LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
                    MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    jobRecycle.setAdapter(jobListAdapter);
                    jobRecycle.setLayoutManager(MyLayoutManager);
                    Log.d("GK","job list is not null");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TODO: FOR INTERSTIALAD
        if(MainActivity.context==null&&getActivity()==null){
            mInterstitialAd = new InterstitialAd(container.getContext());
            // set the ad unit ID
            mInterstitialAd.setAdUnitId(getString(R.string.Interstitial_info_activity));
        }
        else {

            mInterstitialAd = new InterstitialAd(getActivity());
            // set the ad unit ID
            mInterstitialAd.setAdUnitId(getString(R.string.Interstitial_info_activity));

        }

        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.job_fragment, container, false);
        return view;
    }
    @Override
    public void onItemClick(int position, View view) {
        switch (view.getId()) {
            case shareClickerForjob:

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,"পদ : "+Job_list.get(position).getPost()+"\nপ্রতিষ্ঠান : "+Job_list.get(position).getInstitute()+"\nস্থান : "+Job_list.get(position).getPlace()+"\n"+"যোগাযোগ :"+Job_list.get(position).getURL()+"\n\n সংগ্রহ : #হাজিরা_খাতা \n বাংলাভাষায় প্রথম ক্লাস ম্যানেজমেন্ট মোবাইল এপ ।\n ডাউনলোড লিংক : https://play.google.com/store/apps/details?id=com.teachers.HaziraKhataByGk");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent,"শেয়ার করুন।"));
                break;


            case saveClickerForjob:

                MainActivity.saveJob(MainActivity.context,Job_list.get(position).getPost(),Job_list.get(position).getInstitute(),Job_list.get(position).getPlace(),Job_list.get(position).getURL());
                ImageView savedIcon=(ImageView)view.findViewById(R.id.saveClickerIconForJob);

                if(MainActivity.isJobSaved(MainActivity.context,Job_list.get(position).getPost(),Job_list.get(position).getInstitute(),Job_list.get(position).getPlace(),Job_list.get(position).getURL())){
                    savedIcon.setImageResource(R.drawable.ic_saved_icon);
                    Toast.makeText(MainActivity.activity, "সেভ হয়েছে", Toast.LENGTH_SHORT).show();
                }
                else{
                    savedIcon.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                    Toast.makeText(MainActivity.activity, "সেভ রিমুভ হয়েছে", Toast.LENGTH_SHORT).show();
                }

                break;



            case loveClickerForjob:


                MainActivity.LoveJob(MainActivity.context,Job_list.get(position).getPost(),Job_list.get(position).getInstitute(),Job_list.get(position).getPlace(),Job_list.get(position).getURL());
                ImageView loveIcon=(ImageView)view.findViewById(R.id.lovedIconForJob);

                if(MainActivity.isJobLoved(MainActivity.context,Job_list.get(position).getPost(),Job_list.get(position).getInstitute(),Job_list.get(position).getPlace(),Job_list.get(position).getURL())){
                    loveIcon.setImageResource(R.drawable.ic_love_icon);
                    Toast.makeText(MainActivity.activity, "পছন্দ", Toast.LENGTH_SHORT).show();
                }
                else {
                    loveIcon.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    Toast.makeText(MainActivity.activity, "অপছন্দ", Toast.LENGTH_SHORT).show();
                }
                break;



            case clickerForJob:
                final   int pos=position;
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
                        openInAppBrowser(Job_list.get(pos).getURL(),Job_list.get(pos));
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        openInAppBrowser(Job_list.get(pos).getURL(),Job_list.get(pos));
                        super.onAdClosed();
                    }
                });
                break;

        }
    }

    @Override
    public void onItemLongPressed(int position, View view) {

    }

    private void openInAppBrowser(String url,JobItems jobItems) {
//        Intent intent = new Intent(MainActivity.activity, BrowsingActivity.class_room);
//        intent.putExtra("URL", url);
//        intent.putExtra("JOB",jobItems);
//        intent.putExtra("TAG","JOB");
//        Log.d("GK","passing job"+ jobItems.getClass());
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
