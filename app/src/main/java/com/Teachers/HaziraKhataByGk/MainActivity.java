package com.Teachers.HaziraKhataByGk;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.HelperClassess.ViewUtils.BaseActivity;
import com.Teachers.HaziraKhataByGk.Home.MainViewModel;
import com.Teachers.HaziraKhataByGk.Home.SettingsActivity;
import com.Teachers.HaziraKhataByGk.Login.LoginActivity;
import com.Teachers.HaziraKhataByGk.Model.JobItems;
import com.Teachers.HaziraKhataByGk.Tabs.BlogFragment;
import com.Teachers.HaziraKhataByGk.Tabs.ClassRoomFragment;
import com.Teachers.HaziraKhataByGk.Tabs.Fragments.TextBookFragment;
import com.Teachers.HaziraKhataByGk.Tabs.JobFragment;
import com.Teachers.HaziraKhataByGk.Tabs.NibondhonFragment;
import com.Teachers.HaziraKhataByGk.Tabs.TotthojhuriFragment;
import com.Teachers.HaziraKhataByGk.routine.RoutineUtils;
import com.Teachers.HaziraKhataByGk.routine.RoutineViewModel;
import com.Teachers.HaziraKhataByGk.service.GenericEventShowingService;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {


    private MainViewModel mainViewModel;
    public static ArrayList<JobItems> Job_list;
    public Activity activity;
    public String mProfileHeader;
    Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout drawer;
    private RoutineViewModel routineViewModel;

    public void setUpDrawer() {

        //create default navigation drawer toggle
        drawer = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);


        FirebaseCaller.getFirebaseDatabase().keepSynced(true);


        if (FirebaseCaller.getCurrentUser() != null) {

            if (UtilsCommon.isValideString(FirebaseCaller.getCurrentUser().getEmail()))
                mProfileHeader = FirebaseCaller.getCurrentUser().getEmail();
            else if (UtilsCommon.isValideString(FirebaseCaller.getCurrentUser().getPhoneNumber()))
                mProfileHeader = FirebaseCaller.getCurrentUser().getPhoneNumber();

        } else {
            mProfileHeader = "এখনো একাউন্ট খুলেননি।খুলতে এখানে ক্লিক করুন";
        }


        View headerView = navigationView.getHeaderView(0);
        RelativeLayout nav_header = headerView.findViewById(R.id.user_pro_pic);
        TextView emailText = nav_header.findViewById(R.id.user_email);


        emailText.setText(mProfileHeader);


        nav_header.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.putExtra("FLAG", "INSIDE");
            startActivity(intent);
            DrawerLayout drawer = findViewById(R.id.drawerLayout);
            drawer.closeDrawer(GravityCompat.START);
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel=new ViewModelProvider(this).get(MainViewModel.class);

        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);

        //If activity is launched from routine service
        if(getIntent()!=null&&getIntent().getExtras()!=null&&
                getIntent().getExtras().getParcelableArrayList(GenericEventShowingService.TRIGGERED_ROUTINES)!=null){
            mainViewModel.setTriggeredRoutines(getIntent().getExtras().getParcelableArrayList(GenericEventShowingService.TRIGGERED_ROUTINES));
        }

        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        OneSignal.startInit(this).inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification).
                unsubscribeWhenNotificationsAreDisabled(true).init();


        setupRoutine();
        setUpDrawer();
        setupViewPager();
        setupTabIcons();
    }

    private void setupRoutine(){
        routineViewModel = new ViewModelProvider(this).get(RoutineViewModel.class);


        if(UtilsCommon.isRoutineNotificationEnable(this))
        {
            routineViewModel.getAllLiveRoutines().observe(this, routineItems -> {
                RoutineUtils.startEventShowingService(this,routineItems);
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();


        FirebaseAuth.AuthStateListener authListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                startActivity(new Intent (MainActivity.this,LoginActivity.class));
            }
        };

        FirebaseCaller.getAuth().addAuthStateListener(authListener);

        super.onResume();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.privacy_policy:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/document/d/1Yj7XyuCfGIkJ5S0Qqi0f9Xo1z6a0j19uVP_n0k_tnCE/edit?usp=sharing")));
                break;

            case R.id.instruction:
                UtilsCommon.openWithFaceBook("https://www.facebook.com/notes/%E0%A6%B9%E0%A6%BE%E0%A6%9C%E0%A6%BF%E0%A6%B0%E0%A6%BE-%E0%A6%96%E0%A6%BE%E0%A6%A4%E0%A6%BE-%E0%A6%B6%E0%A6%BF%E0%A6%95%E0%A7%8D%E0%A6%B7%E0%A6%95-%E0%A6%B8%E0%A6%BE%E0%A6%AA%E0%A7%8B%E0%A6%B0%E0%A7%8D%E0%A6%9F-%E0%A6%95%E0%A6%AE%E0%A6%BF%E0%A6%89%E0%A6%A8%E0%A6%BF%E0%A6%9F%E0%A6%BF/%E0%A6%B9%E0%A6%BE%E0%A6%9C%E0%A6%BF%E0%A6%B0%E0%A6%BE-%E0%A6%96%E0%A6%BE%E0%A6%A4%E0%A6%BE-%E0%A6%8F%E0%A6%AA%E0%A7%87%E0%A6%B0-%E0%A6%AC%E0%A7%8D%E0%A6%AF%E0%A6%AC%E0%A6%B9%E0%A6%BE%E0%A6%B0%E0%A6%AC%E0%A6%BF%E0%A6%A7%E0%A6%BF/2045598845687496/", this);

                break;

            case R.id.questuon_answer:

                UtilsCommon.openWithFaceBook(
                        "https://www.facebook.com/groups/2035798976667483/permalink/2045734145673966/", this);

                break;

            case R.id.review:

                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setMessage("এপটি এখনো ডেভেলপিং দশায় রয়েছে । " +
                        "সুতরাং শিক্ষক হিসাবে আপনার মূল্যবান মতামত প্রদান করে ডেভেলপারকে সাহায্য করুন।");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "মতামত দিন",
                        (dialog, which) -> UtilsCommon.openInAppBrowser(
                                "https://www.facebook.com/groups/2035798976667483/permalink/2066665843580796/",
                                MainActivity.this));
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "বাদ দিন", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();

                break;
            case R.id.setting:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.share_the_app:

                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, "#হাজিরা_খাতা \n ( বাংলাভাষায় প্রথম ক্লাস ম্যানেজমেন্ট মোবাইল এপ )\n ডাউনলোড লিংক : https://play.google.com/store/apps/details?id=com.Teachers.HaziraKhataByGk");
                i.setType("text/plain");
                startActivity(Intent.createChooser(i, "শেয়ার করুন।"));

                break;

            case R.id.rating:

                final String appPackageNameRating = getPackageName(); // getPackageName() fromTime Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageNameRating)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageNameRating)));
                }
                break;

            case R.id.facebook_community_Title:
                UtilsCommon.openWithFaceBook("https://www.facebook.com/groups/2035798976667483/permalink/2045342365713144/", this);

                break;

            case R.id.about:
                Intent intent1 = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent1);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setupViewPager() {

        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ClassRoomFragment(), "শ্রেণী কার্যক্রম");
        adapter.addFrag(new NibondhonFragment(), "শিক্ষক নিবন্ধন কর্নার");
        adapter.addFrag(new TotthojhuriFragment(), "তথ্য ঝুড়ি");
        adapter.addFrag(new JobFragment(), "শিক্ষক নিয়োগ");
        adapter.addFrag(new BlogFragment(), "শিক্ষক কথন");
        adapter.addFrag(new TextBookFragment(), "পাঠ্যবই");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());
    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText(" শ্রেণী কার্যক্রম");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_class_fragment, 0, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText(" শিক্ষক নিবন্ধন কর্নার");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_nibondhon, 0, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);


        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText(" তথ্য ঝুড়ি");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_tottho, 0, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabFour);

        TextView tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFive.setText(" শিক্ষক নিয়োগ");
        tabFive.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_job, 0, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFive);

        TextView tabSix = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabSix.setText(" শিক্ষক কথন");
        tabSix.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_shikkhok_kothon, 0, 0, 0);
        tabLayout.getTabAt(4).setCustomView(tabSix);

        TextView tabSeven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabSeven.setText(" পাঠ্যবই");
        tabSeven.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_text_book, 0, 0, 0);
        tabLayout.getTabAt(5).setCustomView(tabSeven);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

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

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}

