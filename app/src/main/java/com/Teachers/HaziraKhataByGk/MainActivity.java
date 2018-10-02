package com.Teachers.HaziraKhataByGk;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Teachers.HaziraKhataByGk.FirebaseWorks.MyFirebaseMessagingService;
import com.Teachers.HaziraKhataByGk.Scheduler.StoreRetrieveData;
import com.Teachers.HaziraKhataByGk.Scheduler.ToDoItem;
import com.Teachers.HaziraKhataByGk.Scheduler.scheduleActivity;
import com.Teachers.HaziraKhataByGk.Tabs.Fragments.NewsFragment;
import com.Teachers.HaziraKhataByGk.Tabs.Fragments.NibondhonFragment;
import com.Teachers.HaziraKhataByGk.Tabs.Fragments.TextBookFragment;
import com.Teachers.HaziraKhataByGk.Tabs.Fragments.blogFragment;
import com.Teachers.HaziraKhataByGk.Tabs.Fragments.classRoomFragments;
import com.Teachers.HaziraKhataByGk.Tabs.Fragments.jobFragment;
import com.Teachers.HaziraKhataByGk.Tabs.Fragments.totthojhuri;
import com.Teachers.HaziraKhataByGk.model.JobItems;
import com.Teachers.HaziraKhataByGk.model.blog_item;
import com.Teachers.HaziraKhataByGk.model.class_item;
import com.Teachers.HaziraKhataByGk.model.news_item;
import com.Teachers.HaziraKhataByGk.widget.PrefManagerForMain;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

//TODO: Tutorial for navigation drawer with tablayout:
// 1)http://www.devexchanges.info/2016/05/android-basic-training-course-combining.html


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static ArrayList<class_item> TotalClassItems;
    public static ArrayList<news_item> News_list;
    public static ArrayList<JobItems> Job_list;
    public static View view,view1;//for class_room empty view
    public static Activity activity;
    public static boolean isClassListEmpty;
    public static ArrayList<news_item> saved_news_item_for_main;
    public static ArrayList<blog_item> saved_blog_item_for_main;
    public static ArrayList<ToDoItem> toDoItemsFromMainActivity;
    public static StoreRetrieveData storeRetrieveData;
    public static Context context;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout drawer;

    public static FirebaseAuth auth;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public static boolean calledAlready = false;
    public static String mUserId,mEmail;
    public static FirebaseUser mFirebaseUser;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener authListener;

    public PrefManagerForMain prefManagerForMain;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        context = this;

        Logger.addLogAdapter(new AndroidLogAdapter());

        //HIDING NOTIFICATION BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //VIEWS
        setContentView(R.layout.activity_main);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //ADMOB



//        //TABTARGETVIEW
//        TapTargetView.showFor(this,TapTarget.forView(findViewById(R.drawable.ic_schedule), "This is a target", "We have the best targets, believe me"));

//NOTIFICATION
        OneSignal.startInit(this) .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification) .unsubscribeWhenNotificationsAreDisabled(true) .init();

        //create default navigation drawer toggle
        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);



//TODO: for schedule hints
        // Checking for first time launch - before calling setContentView()
        prefManagerForMain = new PrefManagerForMain(this);
        if (prefManagerForMain.isFirstTimeLaunch()) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage("আপনার সকল শিডিউল কাজগুলো মনে করিয়ে দিবে এই এপটি।শিডিউল রিমাইন্ডার পেতে হলে উপরের ঘড়ি চিহ্নটিতে ক্লিক করুন।");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"ওকে",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            prefManagerForMain.setFirstTimeLaunch(false);

        }



        //TODO:DATABASE CONNECTION

        if(!calledAlready) {
            //avoid setPersistenceEnabled twich click
            try {
                firebaseDatabase = FirebaseDatabase.getInstance();
                firebaseDatabase.setPersistenceEnabled(true);
                databaseReference = firebaseDatabase.getReference();
                calledAlready = true;
            }catch (Exception e){
                recreate();
            }

        }
            firebaseDatabase=FirebaseDatabase.getInstance();
            databaseReference=firebaseDatabase.getReference();


        //TODO: USER (for FB logic auth throw null pointer exception)
        auth = FirebaseAuth.getInstance();
        mFirebaseUser = auth.getCurrentUser();
        databaseReference.keepSynced(true);
        mUserId=mFirebaseUser.getUid();
        mEmail=mFirebaseUser.getEmail();



        View headerView = navigationView.getHeaderView(0);
        RelativeLayout nav_header = (RelativeLayout) headerView.findViewById(R.id.user_pro_pic);
        ImageView profile = (ImageView)nav_header.findViewById(R.id.img_profile);
        TextView userName=(TextView)nav_header.findViewById(R.id.name);
        TextView emailText=(TextView)nav_header.findViewById(R.id.website);


        emailText.setText(mEmail);


        nav_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("FLAG","INSIDE");
                startActivity(intent);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });




        //Tabs
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);





        setupTabIcons();
    }


    @Override
    protected void onResume() {
        super.onResume();


        //TODO:DATABASE CONNECTION
        if(!calledAlready) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            try {
                firebaseDatabase.setPersistenceEnabled(true);
            }catch (Exception e){
                calledAlready=true;
            }

            databaseReference = firebaseDatabase.getReference();
            calledAlready = true;
        }
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();


        //TODO: USER (for FB logic auth throw null pointer exception)
        auth = FirebaseAuth.getInstance();
        mFirebaseUser = auth.getCurrentUser();
        databaseReference.keepSynced(true);
        mUserId=mFirebaseUser.getUid();
        mEmail=mFirebaseUser.getEmail();


//        //FOR ADS
//        if (mAdView != null) {
//            mAdView.resume();
//        }
        context = this;

//        IT MAKES THE INSTRUCTION ON CLASS FRAGMENT WHEN THERE IS NO CLASS
//        For loading class_room from Server
        Query queryReforSeeTheDataIsEmptyOrNot = databaseReference.child("Users").child(mUserId).child("Class");
        queryReforSeeTheDataIsEmptyOrNot.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){MainActivity.isClassListEmpty=false;}
                else
                {MainActivity.isClassListEmpty=true;}
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}});


        //FOR SCHEDULES
        toDoItemsFromMainActivity =new ArrayList<>();
        storeRetrieveData = new StoreRetrieveData(this, scheduleActivity.FILENAME);
        toDoItemsFromMainActivity= StoreRetrieveData.loadFromFile();



        databaseReference.child("Users").child(mUserId).child("Saved_news").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<news_item> news_item=new ArrayList<news_item>();
                for(DataSnapshot classData:dataSnapshot.getChildren()){
                    news_item news_item1;
                    news_item1=classData.getValue(news_item.class);
                    news_item.add(news_item1);
                }
                MainActivity.saved_news_item_for_main=new ArrayList<news_item>();
                MainActivity.saved_news_item_for_main=news_item;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        //TODO: To saved load teachers post
        databaseReference.child("Users").child(mUserId).child("Saved_blog").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<blog_item> blog_item_temp=new ArrayList<blog_item>();
                for(DataSnapshot blogData:dataSnapshot.getChildren()){
                    blog_item blog;
                    blog=blogData.getValue(blog_item.class);
                    blog_item_temp.add(blog);
                }
                saved_blog_item_for_main =new ArrayList<blog_item>();
                saved_blog_item_for_main =blog_item_temp;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        //FOR Nofitification to News Fragment
        if(getIntent().getStringExtra("FLAG")!=null) {
            if (getIntent().getStringExtra("FLAG").equals("TWO")) {
                viewPager.setCurrentItem(2);
                MyFirebaseMessagingService.FLAG=null;
            } else if (getIntent().getStringExtra("FLAG").equals("FOUR")) {
                viewPager.setCurrentItem(4);
                MyFirebaseMessagingService.FLAG=null;
            }
        }

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
        super.onResume();
    }
   public void onStart(){
       context = this;
        //ADMOB
//       AdRequest adRequest = new AdRequest.Builder()
//               .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//               // Check the LogCat to get your test device ID
//               .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
//               .build();
//       adlayout=findViewById(R.id.ads);
//       mAdView = (AdView) findViewById(R.id.adViewInHome);
//       mAdView.loadAd(adRequest);
//       mAdView.setAdListener(new AdListener() {
//           @Override
//           public void onAdLoaded() {
//           }
//
//           @Override
//           public void onAdClosed() {
//               // Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
//           }
//
//           @Override
//           public void onAdFailedToLoad(int errorCode) {
//               adlayout.setVisibility(View.GONE);
//               // Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
//           }
//           @Override
//           public void onAdLeftApplication() {
//               // Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
//           }
//
//           @Override
//           public void onAdOpened() {
//               super.onAdOpened();
//           }
//       });




       authListener = new FirebaseAuth.AuthStateListener() {
           @Override
           public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               FirebaseUser user = firebaseAuth.getCurrentUser();
               if (user == null) {
                   // user auth state is changed - user is null
                   // launch login activity
                   startActivity(new Intent(MainActivity.this, LoginActivity.class));
                   finish();
               }
           }
       };

       super.onStart();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.schedule_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //TODO: TOOLBAR AND MENUS ARE DIFFERENT.Toolbar are the bar where we can set the menus items.

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                Intent intent = new Intent(this,scheduleActivity.class);
                this.startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent = new Intent();

        switch (item.getItemId()) {
            case R.id.privacy_policy:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/document/d/1Yj7XyuCfGIkJ5S0Qqi0f9Xo1z6a0j19uVP_n0k_tnCE/edit?usp=sharing")));
                break;
            case R.id.savedUrl:
                intent.setClass(MainActivity.this, BottomNavigationActivity.class);
                startActivity(intent);
                break;

            case R.id.instruction:
                Intent intent4 =  getFacebookIntent("https://www.facebook.com/notes/%E0%A6%B9%E0%A6%BE%E0%A6%9C%E0%A6%BF%E0%A6%B0%E0%A6%BE-%E0%A6%96%E0%A6%BE%E0%A6%A4%E0%A6%BE-%E0%A6%B6%E0%A6%BF%E0%A6%95%E0%A7%8D%E0%A6%B7%E0%A6%95-%E0%A6%B8%E0%A6%BE%E0%A6%AA%E0%A7%8B%E0%A6%B0%E0%A7%8D%E0%A6%9F-%E0%A6%95%E0%A6%AE%E0%A6%BF%E0%A6%89%E0%A6%A8%E0%A6%BF%E0%A6%9F%E0%A6%BF/%E0%A6%B9%E0%A6%BE%E0%A6%9C%E0%A6%BF%E0%A6%B0%E0%A6%BE-%E0%A6%96%E0%A6%BE%E0%A6%A4%E0%A6%BE-%E0%A6%8F%E0%A6%AA%E0%A7%87%E0%A6%B0-%E0%A6%AC%E0%A7%8D%E0%A6%AF%E0%A6%AC%E0%A6%B9%E0%A6%BE%E0%A6%B0%E0%A6%AC%E0%A6%BF%E0%A6%A7%E0%A6%BF/2045598845687496/");

                try {
                    startActivity(intent4);
                }
                catch (Exception e){
                    Toast.makeText(this,"ERROR "+e.getMessage(),Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.questuon_answer:

                Intent intent3 =  getFacebookIntent("https://www.facebook.com/groups/2035798976667483/permalink/2045734145673966/");

                try {
                    startActivity(intent3);
                }
                catch (Exception e){
                    Toast.makeText(this,"ERROR "+e.getMessage(),Toast.LENGTH_LONG).show();
                }

                //openInAppBrowser("https://www.facebook.com/groups/2035798976667483/permalink/2039659689614745/");
//                intent.setClass(this, FullscreenActivity.class_room);
//                startActivity(intent);
                break;

            case R.id.review:

                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setMessage("এপটি এখনো ডেভেলপিং দশায় রয়েছে । সুতরাং শিক্ষক হিসাবে আপনার মূল্যবান মতামত প্রদান করে ডেভেলপারকে সাহায্য করুন।");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"মতামত দিন",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent5 =  getFacebookIntent("https://www.facebook.com/groups/2035798976667483/permalink/2066665843580796/");

                                try {
                                    startActivity(intent5);
                                }
                                catch (Exception e){
                                    Toast.makeText(MainActivity.this,"ERROR "+e.getMessage(),Toast.LENGTH_LONG).show();
                                }


                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"বাদ দিন",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();



//                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
//                try {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//                } catch (android.content.ActivityNotFoundException anfe) {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//                }
                break;
            case R.id.FourG:
// getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" +"binarygeek.a4gbangladeshByGkEmon")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" +"cbinarygeek.a4gbangladeshByGkEmon")));
                }
                break;
            case R.id.nav_tutor_finder:
                // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" +"com.tariqulislam.tutorfinder")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" +"com.tariqulislam.tutorfinder")));
                }
                break;
            case R.id.share_the_app:

                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT,"#হাজিরা_খাতা \n ( বাংলাভাষায় প্রথম ক্লাস ম্যানেজমেন্ট মোবাইল এপ )\n ডাউনলোড লিংক : https://play.google.com/store/apps/details?id=com.Teachers.HaziraKhataByGk");
                i.setType("text/plain");
                startActivity(Intent.createChooser(i,"শেয়ার করুন।"));

//                intent.setClass(this, SettingsActivity.class_room);
//                startActivity(intent);
                break;

            case R.id.rating:

                final String appPackageNameRating = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageNameRating)));
                }
                 catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageNameRating)));
                }
//                intent.setClass(this, AboutActivity.class_room);
//                startActivity(intent);
                break;

            case R.id.facebook_community_Title:
                Intent intent2 =  getFacebookIntent("https://www.facebook.com/groups/2035798976667483/permalink/2045342365713144/");
                startActivity(intent2);

             //   openInAppBrowser("https://www.facebook.com/groups/2035798976667483/permalink/2039656582948389/");


//                intent.setClass(this, DonateActivity.class_room);
//                startActivity(intent);
                break;

            case R.id.about:
                Intent intent1=new Intent(MainActivity.this,about_activity.class);
                startActivity(intent1);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new classRoomFragments(), "শ্রেণী কার্যক্রম");
        adapter.addFrag(new NibondhonFragment(), "শিক্ষক নিবন্ধন কর্নার");
        adapter.addFrag(new NewsFragment(), "শিক্ষা খবর");
        adapter.addFrag(new totthojhuri(), "তথ্য ঝুড়ি");
        adapter.addFrag(new jobFragment(), "শিক্ষক নিয়োগ");
        adapter.addFrag(new blogFragment(), "শিক্ষক কথন");
        adapter.addFrag(new TextBookFragment(), "পাঠ্যবই");
        viewPager.setAdapter(adapter);
    }
    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText(" শ্রেণী কার্যক্রম");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_class_fragment,0, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText(" শিক্ষক নিবন্ধন কর্নার");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_nibondhon,0, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText(" শিক্ষা খবর");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_news,0, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText(" তথ্য ঝুড়ি");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_tottho,0, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);

        TextView tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFive.setText(" শিক্ষক নিয়োগ");
        tabFive.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_job,0, 0, 0);
        tabLayout.getTabAt(4).setCustomView(tabFive);

        TextView tabSix = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabSix.setText(" শিক্ষক কথন");
        tabSix.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_shikkhok_kothon,0, 0, 0);
        tabLayout.getTabAt(5).setCustomView(tabSix);

        TextView tabSeven = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabSeven.setText(" পাঠ্যবই");
        tabSeven.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_text_book,0, 0, 0);
        tabLayout.getTabAt(6).setCustomView(tabSeven);

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




        //TODO: save news
    public static void saveNews(Context context,news_item news_item1) {
                news_item news_item;
                news_item=news_item1;
                boolean exist=false;

                //TODO: this forloop for ensuring the data is exist both shared preference and firebase database
                for(int i=0;i<saved_news_item_for_main.size();i++){
                    if(news_item.getURL().equals(saved_news_item_for_main.get(i).getURL())&&news_item.getDate().equals(saved_news_item_for_main.get(i).getDate())&&news_item.getHeading().equals(saved_news_item_for_main.get(i).getHeading())){
                       exist=true;
                    }
                }



        SharedPreferences pref = context.getSharedPreferences("HaziraKhata", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        //TODO: for bookmark in browser activity
//        SharedPreferences pref1 = context.getSharedPreferences("HaziraKhata_others", 0); // 0 - for private mode
//        SharedPreferences.Editor editor1 = pref1.edit();


        // if url is already bookmarked, unbookmark it
        if ((pref.getBoolean(news_item1.getURL(), false) && pref.getBoolean(news_item1.getHeading(), false) && pref.getBoolean(news_item1.getDate(), false))||exist) {


            editor.putBoolean(news_item1.getURL(), false);
            editor.putBoolean(news_item1.getHeading(), false);
            editor.putBoolean(news_item1.getDate(), false);
           // editor1.putBoolean(news_item1.getURL(), false);


            Query query =
                    MainActivity.databaseReference.child("Users").child(mUserId).child("Saved_news").orderByChild("url").equalTo(news_item1.getURL());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        snapshot.getRef().child("url").removeValue();
                        snapshot.getRef().child("date").removeValue();
                        snapshot.getRef().child("heading").removeValue();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        } else {
            editor.putBoolean(news_item1.getURL(), true);
            editor.putBoolean(news_item1.getHeading(), true);
            editor.putBoolean(news_item1.getDate(), true);
            //editor1.putBoolean(news_item1.getURL(), true);
            MainActivity.databaseReference.child("Users").child(mUserId).child("Saved_news").push().setValue(news_item1);

        }

        //editor1.commit();
        editor.commit();
    }

    //TODO: check news
    public static boolean isNewsBookmarked(news_item news_item){
//        for(int i=0;i<saved_news_item_for_main.size();i++){
//            if(news_item.getURL().equals(saved_news_item_for_main.get(i).getURL())&&news_item.getDate().equals(saved_news_item_for_main.get(i).getDate())&&news_item.getHeading().equals(saved_news_item_for_main.get(i).getHeading())){
//                return true;
//            }
//        }
        SharedPreferences pref = context.getSharedPreferences("HaziraKhata", 0);

        if(pref.getBoolean(news_item.getURL(), false) && pref.getBoolean(news_item.getHeading(), false) && pref.getBoolean(news_item.getDate(), false)){
            return true;
        }
       return false;
    }

    //TODO: love news
    public static void NewsLoved(Context context, String url,String heading,String Date) {
        SharedPreferences pref = context.getSharedPreferences("HaziraKhata_loved_news", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        // if url is already bookmarked, unbookmark it
        if (pref.getBoolean(url, false) && pref.getBoolean(heading, false) && pref.getBoolean(Date, false)) {
            editor.putBoolean(url, false);
            editor.putBoolean(heading, false);
            editor.putBoolean(Date, false);

        } else {
            editor.putBoolean(heading, true);
            editor.putBoolean(Date, true);
            editor.putBoolean(url, true);
        }
        editor.apply();
        editor.commit();
    }

    //TODO: check love news
    public static boolean isNewsLoved(Context context,String url,String heading,String Date) {
        SharedPreferences pref = context.getSharedPreferences("HaziraKhata_loved_news", 0);
        if(pref.getBoolean(url, false)&&pref.getBoolean(heading, false)&&pref.getBoolean(Date, false)){
            return true;
        }
        return false;
    }


    //TODO: save job
    public static void saveJob(Context context,String post, String institute , String place ,String URL) {
        SharedPreferences pref = context.getSharedPreferences("HaziraKhata_save_job", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        // if url is already bookmarked, unbookmark it
        if (pref.getBoolean(URL, false)&&pref.getBoolean(post, false)&&pref.getBoolean(institute, false)&&pref.getBoolean(place, false)){
            editor.putBoolean(URL, false);
            editor.putBoolean(post, false);
            editor.putBoolean(institute, false);
            editor.putBoolean(place, false);

            Query query=MainActivity.databaseReference.child("Users").child(mUserId).child("Saved_jobs").orderByChild("url").equalTo(URL);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        snapshot.getRef().child("url").removeValue();
                        snapshot.getRef().child("post").removeValue();
                        snapshot.getRef().child("institute").removeValue();
                        snapshot.getRef().child("place").removeValue();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } else {

            editor.putBoolean(URL, true);
            editor.putBoolean(post, true);
            editor.putBoolean(institute, true);
            editor.putBoolean(place, true);

            Query query=MainActivity.databaseReference.child("Users").child(mUserId).child("Saved_jobs").orderByChild("url").equalTo(URL);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        snapshot.getRef().child("url").removeValue();
                        snapshot.getRef().child("post").removeValue();
                        snapshot.getRef().child("institute").removeValue();
                        snapshot.getRef().child("place").removeValue();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            JobItems JobItems=new JobItems();
            JobItems.setInstitute(institute);
            JobItems.setPlace(place);
            JobItems.setURL(URL);
            JobItems.setPost(post);
            MainActivity.databaseReference.child("Users").child(mUserId).child("Saved_jobs").push().setValue(JobItems);
        }

        editor.apply();
        editor.commit();
    }


    //TODO: check jobs
    public static boolean isJobSaved(Context context,String post, String institute , String place ,String URL) {
        SharedPreferences pref = context.getSharedPreferences("HaziraKhata_save_job", 0);
        if(pref.getBoolean(URL, false)&&pref.getBoolean(post, false)&&pref.getBoolean(institute, false)&&pref.getBoolean(place, false)){
            return true;
        }
        return false;
    }



    //TODO: love job
    public static void LoveJob(Context context,String post, String institute , String place ,String URL) {
        SharedPreferences pref = context.getSharedPreferences("HaziraKhata_love_job", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        // if url is already bookmarked, unbookmark it
        if (pref.getBoolean(URL, false)&&pref.getBoolean(post, false)&&pref.getBoolean(institute, false)&&pref.getBoolean(place, false)){
            editor.putBoolean(URL, false);
            editor.putBoolean(post, false);
            editor.putBoolean(institute, false);
            editor.putBoolean(place, false);


        } else {
            editor.putBoolean(URL, true);
            editor.putBoolean(post, true);
            editor.putBoolean(institute, true);
            editor.putBoolean(place, true);

        }

        editor.apply();
        editor.commit();
    }


    //TODO: check love jobs
    public static boolean isJobLoved(Context context,String post, String institute , String place ,String URL) {
        SharedPreferences pref = context.getSharedPreferences("HaziraKhata_love_job", 0);
        if(pref.getBoolean(URL, false)&&pref.getBoolean(post, false)&&pref.getBoolean(institute, false)&&pref.getBoolean(place, false)){
            return true;
        }
        return false;
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
    //TODO: for opeing in fb app if it is installed.
    public static Intent getFacebookIntent(String url) {


        PackageManager pm;

            pm = MainActivity.context.getPackageManager();


        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        }
        catch (PackageManager.NameNotFoundException ignored) {

        }

        return new Intent(Intent.ACTION_VIEW, uri);
    }


    @Override
    public void onPause() {
//        if (mAdView != null) {
//            mAdView.pause();
//        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
//        if (mAdView != null) {
//            mAdView.destroy();
//        }
        super.onDestroy();
    }

    //CHECK INTERNET CONNECTIVITY
    public  boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm!=null&&cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnected();
    }



}

