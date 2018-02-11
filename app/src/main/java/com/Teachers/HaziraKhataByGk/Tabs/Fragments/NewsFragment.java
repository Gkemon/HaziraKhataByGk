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
import com.Teachers.HaziraKhataByGk.adapter.newsAdapter;
import com.Teachers.HaziraKhataByGk.listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.model.news_item;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import static com.Teachers.HaziraKhataByGk.MainActivity.News_list;
import static com.Teachers.HaziraKhataByGk.MainActivity.databaseReference;
import static com.Teachers.HaziraKhataByGk.R.id.ClickerForNews;
import static com.Teachers.HaziraKhataByGk.R.id.SaveClicker;
import static com.Teachers.HaziraKhataByGk.R.id.ShareClicker;
import static com.Teachers.HaziraKhataByGk.R.id.loveClicker;


public class NewsFragment extends Fragment implements RecyclerItemClickListener {

    public static RecyclerView NewsRecycle;
    InterstitialAd mInterstitialAd;
    View view;
//    String NewTitles[] = {"বেসরকারি শিক্ষক নিবন্ধন পরীক্ষা, সিলেবাস ও আবেদনের পদ্ধতি ।","বিগত শিক্ষক নিবন্ধন প্রশ্ন ব্যাংক","মডেল টেস্ট"};
//    String URLS[] = {"বেসরকারি শিক্ষক নিবন্ধন পরীক্ষা, সিলেবাস ও আবেদনের পদ্ধতি ।","বিগত শিক্ষক নিবন্ধন প্রশ্ন ব্যাংক","মডেল টেস্ট"};
//    String Dates[] = {"বেসরকারি শিক্ষক নিবন্ধন পরীক্ষা, সিলেবাস ও আবেদনের পদ্ধতি ।","বিগত শিক্ষক নিবন্ধন প্রশ্ন ব্যাংক","মডেল টেস্ট"};
//    int  Images[] = {R.drawable.ic_bookmark_border_black_24dp,R.drawable.ic_favorite_border_black_24dp,R.drawable.ic_share_black_24dp};

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.news_fragment, container, false);
        NewsRecycle = (RecyclerView) view.findViewById(R.id.newsRecycle);

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


        databaseReference.child("News").addValueEventListener(new ValueEventListener() {
            @Override
            public  void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("GK","NewsLoadingFromServer is called NewsLoadingFromServer");


                ArrayList<news_item> news_items=new ArrayList<news_item>();
                for(DataSnapshot NewsData:dataSnapshot.getChildren()){
                    news_item single_news_item=new news_item();
                    single_news_item=NewsData.getValue(news_item.class);
                    news_items.add(single_news_item);
                }
                News_list=news_items;
                if(News_list.size()>0&&NewsRecycle!=null) {
                    Collections.reverse(News_list);
                    newsAdapter newsAdapter = new newsAdapter(News_list);
                    newsAdapter.setOnItemClickListener(NewsFragment.this);
                    LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
                    MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    NewsRecycle.setAdapter(newsAdapter);
                    NewsRecycle.setLayoutManager(MyLayoutManager);
                    Log.d("GK","newsList is not null");
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        Log.d("GK","from onActivity of news fragment");
        return view;
    }




    @Override
    public void onItemClick(int position, View view) {

        switch (view.getId()) {


            case ClickerForNews:

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
                        openInAppBrowser(News_list.get(pos).getURL(),News_list.get(pos));
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        openInAppBrowser(News_list.get(pos).getURL(),News_list.get(pos));
                        super.onAdClosed();
                    }
                });

                break;


            case ShareClicker:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,News_list.get(position).getHeading()+"\n"+"লিংক :"+News_list.get(position).getURL()+"\n\n সংগ্রহ : #হাজিরা_খাতা \n বাংলাভাষায় প্রথম ক্লাস ম্যানেজমেন্ট মোবাইল এপ ।\n ডাউনলোড লিংক : https://play.google.com/store/apps/details?id=com.teachers.HaziraKhataByGk");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent,"শেয়ার করুন।"));

                break;


            case SaveClicker:
//                for(int i=0;i<MainActivity.saved_news_item_for_main.size();i++){
//                    if(News_list.get(position).getURL().equals(MainActivity.saved_news_item_for_main.get(i).getURL())&&News_list.get(position).getDate().equals(MainActivity.saved_news_item_for_main.get(i).getDate())&&News_list.get(position).getHeading().equals(MainActivity.saved_news_item_for_main.get(i).getHeading())){
//
//                        Query query = MainActivity.databaseReference.child("Saved_news").orderByChild("url").equalTo(News_list.get(position).getURL());
//            query.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        snapshot.getRef().child("url").removeValue();
//                        snapshot.getRef().child("date").removeValue();
//                        snapshot.getRef().child("heading").removeValue();
//                        Log.d("GK","exis");
//                    }
//                }
//                @Override
//                public void onCancelled(DatabaseError databaseError) {}
//            });
//                        break;
//                    }
//                }
                MainActivity.saveNews(MainActivity.context,News_list.get(position));
                ImageView savedIcon=(ImageView)view.findViewById(R.id.SaveClickerIcon);
//
//                Query query=databaseReference.child("Saved_news");
//                query.getRef().addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        ArrayList<news_item> news_item=new ArrayList<news_item>();
//                        for(DataSnapshot classData:dataSnapshot.getChildren()){
//                            news_item news_item1;
//                            news_item1=classData.getValue(news_item.class_room);
//                            news_item.add(news_item1);
//                        }
//                        MainActivity.saved_news_item_for_main=new ArrayList<news_item>();
//                        MainActivity.saved_news_item_for_main=news_item;
//                        Log.d("GK","QUERY");
//
//
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                    }
//                });
                if(MainActivity.isNewsBookmarked(News_list.get(position))){
                    savedIcon.setImageResource(R.drawable.ic_saved_icon);
                    Toast.makeText(MainActivity.activity, "সেভ হয়েছে", Toast.LENGTH_SHORT).show();
                }
                else{
                    savedIcon.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                    Toast.makeText(MainActivity.activity, "সেভ রিমুভ হয়েছে", Toast.LENGTH_SHORT).show();
                }
               break;


            case loveClicker:

                MainActivity.NewsLoved(MainActivity.context,News_list.get(position).getURL(),News_list.get(position).getHeading(),News_list.get(position).getDate());
                ImageView loveIcon=(ImageView)view.findViewById(R.id.lovedIcon);

                if(MainActivity.isNewsLoved(MainActivity.context,News_list.get(position).getURL(),News_list.get(position).getHeading(),News_list.get(position).getDate())){
                    loveIcon.setImageResource(R.drawable.ic_love_icon);
                    Toast.makeText(MainActivity.activity, "পছন্দ", Toast.LENGTH_SHORT).show();
                }
                else {
                    loveIcon.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    Toast.makeText(MainActivity.activity, "অপছন্দ", Toast.LENGTH_SHORT).show();
                }
                break;
        }



    }

    @Override
    public void onItemLongPressed(int position, View view) {

    }

    private void openInAppBrowser(String url,news_item news_item) {
//        Intent intent = new Intent(MainActivity.activity, BrowsingActivity.class_room);
//        intent.putExtra("URL", url);
//        intent.putExtra("NEWS",news_item);
//        intent.putExtra("TAG","NEWS");
//        Log.d("GK","passing news "+news_item.getClass());
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
