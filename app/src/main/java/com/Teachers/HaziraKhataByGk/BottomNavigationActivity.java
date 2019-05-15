package com.Teachers.HaziraKhataByGk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.Model.JobItems;
import com.Teachers.HaziraKhataByGk.Model.NewsItem;
import com.Teachers.HaziraKhataByGk.Model.BlogItem;
import com.Teachers.HaziraKhataByGk.SavedData.SavedNewsFragment;
import com.Teachers.HaziraKhataByGk.SavedData.SavedBlogFragment;
import com.Teachers.HaziraKhataByGk.SavedData.SavedJobFragment;
import com.Teachers.HaziraKhataByGk.Widget.BottomNavigationViewHelper;
import com.Teachers.HaziraKhataByGk.Widget.MyPageTransformer;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class BottomNavigationActivity extends AppCompatActivity {
    public static ArrayList<NewsItem> Saved_News_list;
    public static ArrayList<JobItems> Saved_job_list;
    public static ArrayList<BlogItem> Saved_Blog_list;

    public static boolean isSavedNewsListEmpty;
    public static boolean isSavedJoblistEmpty;
    public static boolean isSavedBloglistEmpty;

    public static ViewPager viewPager;
    public static View view,view1;//for saved news empty view
    public static View viewforSavedJob,view1forSavedJob;//for saved news empty view
    public static View viewforSavedBlog,view1forSavedBlog;//for saved news empty view


    public LinearLayout adlayout;
    public AdView mAdView;


    private BottomNavigationView navigation;
    public static Context context;
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //HIDING NOTIFICATION BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_bottom_navigation);
        context=this;
        activity=this;
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        initView();

    }

    private void initView() {

        viewPager = (ViewPager) findViewById(R.id.view_pager_bottom_navigation);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new SavedNewsFragment());
        adapter.addFrag(new SavedJobFragment());
        adapter.addFrag(new SavedBlogFragment());
        //adapter.addFrag(new SavedOtherDataFragment());
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new MyPageTransformer());
        viewPager.addOnPageChangeListener(pageChangeListener);
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // If BottomNavigationView has more than 3 items, using reflection to disable shift mode
        BottomNavigationViewHelper.disableShiftMode(navigation);
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    navigation.setSelectedItemId(R.id.savedNews);
                    break;
                case 1:
                    navigation.setSelectedItemId(R.id.savedJobs);
                    break;
                case 2:
                    navigation.setSelectedItemId(R.id.savedBlogs);
                    break;
//                case 3:
//                    navigation.setSelectedItemId(R.id.other_links);
//                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.savedNews:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.savedJobs:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.savedBlogs:
                    viewPager.setCurrentItem(2);
                    return true;
//                case R.id.other_links:
//                    viewPager.setCurrentItem(3);
//                    return true;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(BottomNavigationActivity.activity,MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
               super.onResume();

        if (mAdView != null) {
            mAdView.resume();
        }

        //TODO: IT MAKES THE INSTRUCTION ON saved news FRAGMENT WHEN THERE IS NO news For loading saved news from Server
        Query queryReforSeeTheDataIsEmptyOrNot = FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Saved_news");
        queryReforSeeTheDataIsEmptyOrNot.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){isSavedNewsListEmpty=false;}
                else
                {isSavedNewsListEmpty=true;}}
            @Override
            public void onCancelled(DatabaseError databaseError) {}});



        //TODO: IT MAKES THE INSTRUCTION ON saved job FRAGMENT WHEN THERE IS NO saved job For loading saved job from Server
        Query queryReforSeeTheDataIsEmptyOrNotForsavedJob = FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Saved_jobs");
        queryReforSeeTheDataIsEmptyOrNotForsavedJob.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    BottomNavigationActivity.isSavedJoblistEmpty=false;}
                else
                {BottomNavigationActivity.isSavedJoblistEmpty=true;}
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}});




        //TODO: IT MAKES THE INSTRUCTION ON saved job FRAGMENT WHEN THERE IS NO saved job For loading saved job from Server
        Query queryReforSeeTheDataIsEmptyOrNotForsavedBlog = FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Saved_blog");
        queryReforSeeTheDataIsEmptyOrNotForsavedBlog.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    BottomNavigationActivity.isSavedBloglistEmpty=false;}
                else
                {BottomNavigationActivity.isSavedBloglistEmpty=true;}
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}});



    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment) {
            mFragmentList.add(fragment);
        }
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
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

}

