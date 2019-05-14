package com.Teachers.HaziraKhataByGk.Tabs;

import android.content.Context;
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

import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.MainActivity;
import com.Teachers.HaziraKhataByGk.Model.NewsItem;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.Adapter.NewsAdapter;
import com.Teachers.HaziraKhataByGk.Listener.RecyclerItemClickListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import static com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon.openInAppBrowser;
import static com.Teachers.HaziraKhataByGk.MainActivity.NewsList;
import static com.Teachers.HaziraKhataByGk.MainActivity.databaseReference;
import static com.Teachers.HaziraKhataByGk.R.id.ClickerForNews;
import static com.Teachers.HaziraKhataByGk.R.id.SaveClicker;
import static com.Teachers.HaziraKhataByGk.R.id.ShareClicker;
import static com.Teachers.HaziraKhataByGk.R.id.loveClicker;


public class NewsFragment extends Fragment implements RecyclerItemClickListener {

    public static RecyclerView NewsRecycle;

    public Context context;
    View view;
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







        FirebaseCaller.getFirebaseDatabase().child("News").addValueEventListener(new ValueEventListener() {
            @Override
            public  void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("GK","NewsLoadingFromServer is called NewsLoadingFromServer");


                ArrayList<NewsItem> NewsItems =new ArrayList<NewsItem>();
                for(DataSnapshot NewsData:dataSnapshot.getChildren()){
                    NewsItem single_newsItem =new NewsItem();
                    single_newsItem =NewsData.getValue(NewsItem.class);
                    NewsItems.add(single_newsItem);
                }
                NewsList = NewsItems;
                if(NewsList.size()>0&&NewsRecycle!=null) {
                    Collections.reverse(NewsList);
                    NewsAdapter NewsAdapter = new NewsAdapter(NewsList);
                    NewsAdapter.setOnItemClickListener(NewsFragment.this);
                    LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
                    MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    NewsRecycle.setAdapter(NewsAdapter);
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
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Nullable
    @Override
    public Context getContext() {
        return  context;
    }

    @Override
    public void onItemClick(int position, View view) {

        switch (view.getId()) {


            case ClickerForNews:

              final   int pos=position;

                openInAppBrowser(NewsList.get(pos).getURL(), context);

                break;


            case ShareClicker:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, NewsList.get(position).getHeading()+"\n"+"লিংক :"+ NewsList.get(position).getURL()+"\n\n সংগ্রহ : #হাজিরা_খাতা \n বাংলাভাষায় প্রথম ক্লাস ম্যানেজমেন্ট মোবাইল এপ ।\n ডাউনলোড লিংক : https://play.google.com/store/apps/details?id=com.teachers.HaziraKhataByGk");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent,"শেয়ার করুন।"));

                break;


            case SaveClicker:
                UtilsCommon.saveNews(getContext(), NewsList.get(position));
                ImageView savedIcon=(ImageView)view.findViewById(R.id.SaveClickerIcon);

                if(UtilsCommon.isNewsBookmarked(NewsList.get(position),context)){
                    savedIcon.setImageResource(R.drawable.ic_saved_icon);
                    Toast.makeText(getContext(), "সেভ হয়েছে", Toast.LENGTH_SHORT).show();
                }
                else{
                    savedIcon.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                    Toast.makeText(context, "সেভ রিমুভ হয়েছে", Toast.LENGTH_SHORT).show();
                }
               break;


            case loveClicker:

                UtilsCommon.NewsLoved(context, NewsList.get(position).getURL(), NewsList.get(position).getHeading(), NewsList.get(position).getDate());
                ImageView loveIcon=(ImageView)view.findViewById(R.id.lovedIcon);

                if(UtilsCommon.isNewsLoved(context, NewsList.get(position).getURL(), NewsList.get(position).getHeading(), NewsList.get(position).getDate())){
                    loveIcon.setImageResource(R.drawable.ic_love_icon);
                    Toast.makeText(context, "পছন্দ", Toast.LENGTH_SHORT).show();
                }
                else {
                    loveIcon.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    Toast.makeText(context, "অপছন্দ", Toast.LENGTH_SHORT).show();
                }
                break;
        }



    }

    @Override
    public void onItemLongPressed(int position, View view) {

    }








}
