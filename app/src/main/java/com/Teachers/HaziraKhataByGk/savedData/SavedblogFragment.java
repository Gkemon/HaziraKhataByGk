package com.Teachers.HaziraKhataByGk.savedData;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.Teachers.HaziraKhataByGk.BottomNavigationActivity;
import com.Teachers.HaziraKhataByGk.MainActivity;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.adapter.blogAdapter;
import com.Teachers.HaziraKhataByGk.blog;
import com.Teachers.HaziraKhataByGk.listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.model.blog_item;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.view.View.GONE;
import static com.Teachers.HaziraKhataByGk.BottomNavigationActivity.Saved_Blog_list;
import static com.Teachers.HaziraKhataByGk.MainActivity.databaseReference;
import static com.Teachers.HaziraKhataByGk.MainActivity.mUserId;
import static com.Teachers.HaziraKhataByGk.R.id.ClickerForBlog;
import static com.Teachers.HaziraKhataByGk.R.id.SaveClicker;
import static com.Teachers.HaziraKhataByGk.R.id.ShareClicker;
import static com.Teachers.HaziraKhataByGk.R.id.loveClicker;


public class SavedblogFragment extends Fragment implements RecyclerItemClickListener {
    View rootView;
    InterstitialAd mInterstitialAd;
    public static RecyclerView savedBlogRecycle;
    public FloatingActionButton delete_all;
    public SavedblogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        //TODO: FOR INTERSTIALAD
        mInterstitialAd = new InterstitialAd(BottomNavigationActivity.context);
        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.Interstitial_info_activity));
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //THIS MAKES THE EMPTY IMAGE AND EMPTY DESCRIPTION
        if(BottomNavigationActivity.isSavedBloglistEmpty){
            BottomNavigationActivity.viewforSavedBlog.setVisibility(View.VISIBLE);
            BottomNavigationActivity.view1forSavedBlog.setVisibility(View.VISIBLE);
            savedBlogRecycle.setVisibility(View.GONE);
            delete_all.setVisibility(GONE);

        }
        else {
            BottomNavigationActivity.viewforSavedBlog.setVisibility(View.GONE);
            BottomNavigationActivity.view1forSavedBlog.setVisibility(View.GONE);
            savedBlogRecycle.setVisibility(View.VISIBLE);
            delete_all.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.saved_blog, container, false);
        rootView.setPadding(0,40,0,0);
        savedBlogRecycle = (RecyclerView) rootView.findViewById(R.id.savedblogRecycle);
        delete_all = (FloatingActionButton) rootView.findViewById(R.id.delete_all_button);
        BottomNavigationActivity.viewforSavedBlog=rootView.findViewById(R.id.EmptyImage);
        BottomNavigationActivity.view1forSavedBlog=rootView.findViewById(R.id.EmptyText);


        //THIS MAKES THE EMPTY IMAGE AND EMPTY DESCRIPTION
        if(BottomNavigationActivity.isSavedBloglistEmpty){
            BottomNavigationActivity.viewforSavedBlog.setVisibility(View.VISIBLE);
            BottomNavigationActivity.view1forSavedBlog.setVisibility(View.VISIBLE);
            savedBlogRecycle.setVisibility(View.GONE);
            delete_all.setVisibility(GONE);

        }
        else {
            BottomNavigationActivity.viewforSavedBlog.setVisibility(View.GONE);
            BottomNavigationActivity.view1forSavedBlog.setVisibility(View.GONE);
            savedBlogRecycle.setVisibility(View.VISIBLE);
            delete_all.setVisibility(View.VISIBLE);
        }


        //TODO: delete all saved news
        delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteDialog();
            }
        });



        databaseReference.child("Users").child(mUserId).child("Saved_blog").addValueEventListener(new ValueEventListener() {
            @Override
            public  void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<blog_item> blogItemlist=new ArrayList<blog_item>();
                for(DataSnapshot blogData:dataSnapshot.getChildren()){
                    blog_item Single_blog_Items;
                    Single_blog_Items=blogData.getValue(blog_item.class);
                    blogItemlist.add(Single_blog_Items);
                }
                Saved_Blog_list=new ArrayList<blog_item>();
                Saved_Blog_list=blogItemlist;



        //TODO: IT MAKES THE INSTRUCTION ON saved job FRAGMENT WHEN THERE IS NO saved job For loading saved job from Server
                Query queryReforSeeTheDataIsEmptyOrNotForsavedBlog = MainActivity.databaseReference.child("Users").child(mUserId).child("Saved_blog");
                queryReforSeeTheDataIsEmptyOrNotForsavedBlog.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            BottomNavigationActivity.isSavedBloglistEmpty=false;}
                        else
                        {BottomNavigationActivity.isSavedBloglistEmpty=true;}

                        //THIS MAKES THE EMPTY IMAGE AND EMPTY DESCRIPTION
                        if(BottomNavigationActivity.isSavedBloglistEmpty){
                            BottomNavigationActivity.viewforSavedBlog.setVisibility(View.VISIBLE);
                            BottomNavigationActivity.view1forSavedBlog.setVisibility(View.VISIBLE);
                            savedBlogRecycle.setVisibility(View.GONE);
                            delete_all.setVisibility(GONE);

                        }
                        else {
                            BottomNavigationActivity.viewforSavedBlog.setVisibility(View.GONE);
                            BottomNavigationActivity.view1forSavedBlog.setVisibility(View.GONE);
                            savedBlogRecycle.setVisibility(View.VISIBLE);
                            delete_all.setVisibility(View.VISIBLE);
                        }



                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}});


                if(Saved_Blog_list.size()>0&& savedBlogRecycle !=null){
                    blogAdapter blogAdapter = new blogAdapter(Saved_Blog_list);
                    blogAdapter.setOnItemClickListener(SavedblogFragment.this);
                    LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
                    MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    savedBlogRecycle.setAdapter(blogAdapter);
                    savedBlogRecycle.setLayoutManager(MyLayoutManager);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return rootView;
    }



    @Override
    public void onItemClick(int position, View view) {
        blog_item blog_item=Saved_Blog_list.get(position);
        switch (view.getId()) {


            case ClickerForBlog:

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
                        String url=Saved_Blog_list.get(pos).getURL();
                        //TODO: for opening default browser
                        if (!url.startsWith("http://") && !url.startsWith("https://"))
                            url = "http://" + url;
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdClosed() {
                        String url=Saved_Blog_list.get(pos).getURL();
                        //TODO: for opening default browser
                        if (!url.startsWith("http://") && !url.startsWith("https://"))
                            url = "http://" + url;
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                        super.onAdClosed();
                    }
                });

                break;


            case ShareClicker:
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_SEND);
                String heading=Saved_Blog_list.get(position).getHeading();
                String url1=Saved_Blog_list.get(position).getURL();
                String name=Saved_Blog_list.get(position).getWriter();
                intent1.putExtra(Intent.EXTRA_TEXT,"#শিক্ষক_কথন \n\n"+heading+"\n\nলেখন :"+name+"\nলিংক :"+url1+"\n\n সংগ্রহে : #হাজিরা_খাতা \n বাংলাভাষায় প্রথম ক্লাস ম্যানেজমেন্ট মোবাইল এপ ।\n ডাউনলোড লিংক : https://play.google.com/store/apps/details?id=com.Teachers.HaziraKhataByGk");
                intent1.setType("text/plain");
                startActivity(Intent.createChooser(intent1,"শেয়ার করুন।"));
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
                blog.saveBlog(blog.context,blog_item);
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
                if(blog.isBlogBookmarked(blog_item)){
                    savedIcon.setImageResource(R.drawable.ic_saved_icon);
                    Toast.makeText(BottomNavigationActivity.activity, "সেভ হয়েছে", Toast.LENGTH_SHORT).show();
                }
                else{
                    savedIcon.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                    Toast.makeText(BottomNavigationActivity.activity, "সেভ রিমুভ হয়েছে", Toast.LENGTH_SHORT).show();
                }
                break;


            case loveClicker:
                blog.loveBlog(blog_item);
                ImageView loveIcon=(ImageView)view.findViewById(R.id.lovedIcon);

                if(blog.isBlogLove(blog_item)){
                    loveIcon.setImageResource(R.drawable.ic_love_icon);
                    Toast.makeText(blog.context, "পছন্দ", Toast.LENGTH_SHORT).show();
                }
                else {
                    loveIcon.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    Toast.makeText(blog.context, "অপছন্দ", Toast.LENGTH_SHORT).show();
                }
                break;
        }



    }
    @Override
    public void onItemLongPressed(int position, View view) {

    }
    public void DeleteDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(BottomNavigationActivity.context);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_delete_dialauge, null);
        dialogBuilder.setView(dialogView);
        final EditText edt = (EditText) dialogView.findViewById(R.id.custom_delete_dialauge_text);

        dialogBuilder.setIcon(R.drawable.warnig_for_delete);
        dialogBuilder.setTitle("সেভ করা শিক্ষক কথন ডিলিট করতে চান?");
        dialogBuilder.setMessage("নিশ্চিত হতে ইংরেজীতে \"DELETE\" শব্দটি লিখুন।");
        dialogBuilder.setPositiveButton("ডিলিট করুন", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(edt.getText().toString().equals("DELETE")){


                    MainActivity.databaseReference.child("Users").child(mUserId).child("Saved_blog").removeValue();
                    SharedPreferences pref = BottomNavigationActivity.context.getSharedPreferences("teacher_blog_saved", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.clear();
                    editor.commit();
//                    Intent intent =new Intent(BottomNavigationActivity.context,BottomNavigationActivity.class_room);
//                    startActivity(intent);
                }
            }
        });
        dialogBuilder.setNegativeButton("বাদ দিন", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }


    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }





}
