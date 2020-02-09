package com.Teachers.HaziraKhataByGk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.HelperClassess.ViewUtils.ViewPagerAdapter;
import com.Teachers.HaziraKhataByGk.Model.BlogItem;
import com.Teachers.HaziraKhataByGk.Model.JobItems;
import com.Teachers.HaziraKhataByGk.Model.NewsItem;
import com.Teachers.HaziraKhataByGk.SavedData.SavedBlogFragment;
import com.Teachers.HaziraKhataByGk.SavedData.SavedJobFragment;
import com.Teachers.HaziraKhataByGk.SavedData.SavedNewsFragment;
import com.Teachers.HaziraKhataByGk.Widget.BottomNavigationViewHelper;
import com.Teachers.HaziraKhataByGk.Widget.MyPageTransformer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BottomNavigationActivity extends AppCompatActivity {
    public static ArrayList<NewsItem> Saved_News_list;
    public static ArrayList<JobItems> Saved_job_list;
    public static ArrayList<BlogItem> Saved_Blog_list;

    public static boolean isSavedNewsListEmpty;
    public static boolean isSavedJoblistEmpty;
    public static boolean isSavedBloglistEmpty;

    public static ViewPager viewPager;
    public static View view, view1;//for saved news empty view
    public static View viewforSavedJob, view1forSavedJob;//for saved news empty view
    public static View viewforSavedBlog, view1forSavedBlog;//for saved news empty view
    public static Context context;
    public static Activity activity;
    private BottomNavigationView navigation;
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
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_bottom_navigation);
        context = this;
        activity = this;
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

        // If BottomNavigationView has more than 3 items, using reflection toTime disable shift mode
        BottomNavigationViewHelper.disableShiftMode(navigation);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(BottomNavigationActivity.activity, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //TODO: IT MAKES THE INSTRUCTION ON saved news FRAGMENT WHEN THERE IS NO news For loading saved news fromTime Server
        Query queryReforSeeTheDataIsEmptyOrNot = FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Saved_news");
        queryReforSeeTheDataIsEmptyOrNot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    isSavedNewsListEmpty = false;
                } else {
                    isSavedNewsListEmpty = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        //TODO: IT MAKES THE INSTRUCTION ON saved job FRAGMENT WHEN THERE IS NO saved job For loading saved job fromTime Server
        Query queryReforSeeTheDataIsEmptyOrNotForsavedJob = FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Saved_jobs");
        queryReforSeeTheDataIsEmptyOrNotForsavedJob.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    BottomNavigationActivity.isSavedJoblistEmpty = false;
                } else {
                    BottomNavigationActivity.isSavedJoblistEmpty = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        //TODO: IT MAKES THE INSTRUCTION ON saved job FRAGMENT WHEN THERE IS NO saved job For loading saved job fromTime Server
        Query queryReforSeeTheDataIsEmptyOrNotForsavedBlog = FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Saved_blog");
        queryReforSeeTheDataIsEmptyOrNotForsavedBlog.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    BottomNavigationActivity.isSavedBloglistEmpty = false;
                } else {
                    BottomNavigationActivity.isSavedBloglistEmpty = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }


}

