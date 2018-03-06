package com.Teachers.HaziraKhataByGk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.Teachers.HaziraKhataByGk.adapter.blogAdapter;
import com.Teachers.HaziraKhataByGk.listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.model.blog_item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import static com.Teachers.HaziraKhataByGk.MainActivity.saved_blog_item_for_main;
import static com.Teachers.HaziraKhataByGk.R.id.ClickerForBlog;
import static com.Teachers.HaziraKhataByGk.R.id.SaveClicker;
import static com.Teachers.HaziraKhataByGk.R.id.ShareClicker;
import static com.Teachers.HaziraKhataByGk.R.id.loveClicker;

public class BlogActivity extends AppCompatActivity implements RecyclerItemClickListener {
    public static RecyclerView blogRecycle;
    public static Context context;
    public static Activity activity;
    public static ArrayList<blog_item> blog_items_list;

    public static FirebaseAuth auth;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public static String mUserId;
    public static FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        blogRecycle=(RecyclerView) findViewById(R.id.blogRecycle);
        context=this;

        //TODO:DATABASE CONNECTION
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();

        //TODO: USER (for FB logic auth throw null pointer exception)
        auth = FirebaseAuth.getInstance();
        mFirebaseUser = auth.getCurrentUser();
        databaseReference.keepSynced(true);
        mUserId=mFirebaseUser.getUid();

        MainActivity.databaseReference=databaseReference;
        MainActivity.mUserId=mUserId;

        databaseReference.child("Blog").addValueEventListener(new ValueEventListener() {
            @Override
            public  void onDataChange(DataSnapshot dataSnapshot) {



                ArrayList<blog_item> blog_items_temp=new ArrayList<blog_item>();
                for(DataSnapshot blogData:dataSnapshot.getChildren()){
                    blog_item blog;
                    blog=blogData.getValue(blog_item.class);
                    blog_items_temp.add(blog);
                }
                blog_items_list =blog_items_temp;
                if(blog_items_list.size()>0&&blogRecycle!=null) {
                    Collections.reverse(blog_items_list);
                    blogAdapter blogAdapter = new blogAdapter(blog_items_list);
                    blogAdapter.setOnItemClickListener(BlogActivity.this);
                    LinearLayoutManager MyLayoutManager = new LinearLayoutManager(BlogActivity.context);
                    MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    blogRecycle.setAdapter(blogAdapter);
                    blogRecycle.setLayoutManager(MyLayoutManager);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onStart() {
        context=this;
        super.onStart();
    }

    //TODO: check news as if loved or not
    public static boolean isBlogLove(blog_item blog_item){
        SharedPreferences pref = context.getSharedPreferences("teacher_blog_loved", 0);

        if(pref.getBoolean(blog_item.getURL(), false) && pref.getBoolean(blog_item.getHeading(), false) && pref.getBoolean(blog_item.getDate(), false)&&pref.getBoolean(blog_item.getWriter(), false) ){
            return true;
        }
        return false;
    }





    //TODO: love BlogActivity
    public static void loveBlog (blog_item blog_item){
//        for(int i=0;i<saved_news_item_for_main.size();i++){
//            if(news_item.getURL().equals(saved_news_item_for_main.get(i).getURL())&&news_item.getDate().equals(saved_news_item_for_main.get(i).getDate())&&news_item.getHeading().equals(saved_news_item_for_main.get(i).getHeading())){
//                return true;
//            }
//        }

        SharedPreferences pref = context.getSharedPreferences("teacher_blog_loved", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        String URL,title,name,date;
        URL=blog_item.getURL();
        title=blog_item.getHeading();
        name=blog_item.getWriter();
        date=blog_item.getDate();
        // if url is already bookmarked, unbookmark it
        if (pref.getBoolean(URL, false)&&pref.getBoolean(title, false)&&pref.getBoolean(name, false)&&pref.getBoolean(date, false)){
            editor.putBoolean(URL, false);
            editor.putBoolean(title, false);
            editor.putBoolean(name, false);
            editor.putBoolean(date, false);
        } else {
            editor.putBoolean(URL, true);
            editor.putBoolean(title, true);
            editor.putBoolean(name, true);
            editor.putBoolean(date, true);

        }
        editor.apply();
        editor.commit();
    }







    //TODO: check news
    public static boolean isBlogBookmarked(blog_item blog_item){
//        for(int i=0;i<saved_news_item_for_main.size();i++){
//            if(news_item.getURL().equals(saved_news_item_for_main.get(i).getURL())&&news_item.getDate().equals(saved_news_item_for_main.get(i).getDate())&&news_item.getHeading().equals(saved_news_item_for_main.get(i).getHeading())){
//                return true;
//            }
//        }

        if(context==null){
            context= BottomNavigationActivity.context;
        }
        SharedPreferences pref = context.getSharedPreferences("teacher_blog_saved", 0);

        if(pref.getBoolean(blog_item.getURL(), false) && pref.getBoolean(blog_item.getHeading(), false) && pref.getBoolean(blog_item.getDate(), false)&&pref.getBoolean(blog_item.getWriter(), false) ){
            return true;
        }
        return false;
    }






    //TODO: save news
    public static void saveBlog(Context context,blog_item blogItem) {
        blog_item blog;
        blog=blogItem;
        boolean exist=false;

        //TODO: this forloop for ensuring the data is exist both shared preference and firebase database
        for(int i = 0; i< saved_blog_item_for_main.size(); i++){
            if(blog.getURL().equals(saved_blog_item_for_main.get(i).getURL())&&blog.getDate().equals(saved_blog_item_for_main.get(i).getDate())&&blog.getHeading().equals(saved_blog_item_for_main.get(i).getHeading())&&blog.getWriter().equals(saved_blog_item_for_main.get(i).getWriter())){
                exist=true;
            }
        }

        SharedPreferences pref = context.getSharedPreferences("teacher_blog_saved", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        //TODO: for bookmark in browser activity
//        SharedPreferences pref1 = context.getSharedPreferences("HaziraKhata_others", 0); // 0 - for private mode
//        SharedPreferences.Editor editor1 = pref1.edit();


        // if url is already bookmarked, unbookmark it
        if ((pref.getBoolean(blog.getURL(), false) && pref.getBoolean(blog.getHeading(), false) && pref.getBoolean(blog.getDate(), false)&&pref.getBoolean(blog.getWriter(), false))||exist) {

            editor.putBoolean(blog.getURL(), false);
            editor.putBoolean(blog.getHeading(), false);
            editor.putBoolean(blog.getDate(), false);
            editor.putBoolean(blog.getWriter(), false);

            Query query =
                    MainActivity.databaseReference.child("Users").child(mUserId).child("Saved_blog").orderByChild("url").equalTo(blog.getURL());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        snapshot.getRef().child("url").removeValue();
                        snapshot.getRef().child("date").removeValue();
                        snapshot.getRef().child("heading").removeValue();
                        snapshot.getRef().child("writer").removeValue();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        } else {
            editor.putBoolean(blog.getURL(), true);
            editor.putBoolean(blog.getHeading(), true);
            editor.putBoolean(blog.getDate(), true);
            editor.putBoolean(blog.getWriter(), true);
            MainActivity.databaseReference.child("Users").child(mUserId).child("Saved_blog").push().setValue(blog);

        }

        editor.commit();
    }


    @Override
    public void onItemClick(int position, View view) {
        blog_item blog_item=blog_items_list.get(position);
        switch (view.getId()) {


            case ClickerForBlog:
                String url=blog_items_list.get(position).getURL();
                //TODO: for opening default browser
                if (!url.startsWith("http://") && !url.startsWith("https://"))
                    url = "http://" + url;
                Intent intent = getFacebookIntent(url);
               // intent.setData(Uri.parse(url));
                startActivity(intent);
                break;

            case ShareClicker:
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_SEND);
                String heading=blog_items_list.get(position).getHeading();
                String url1=blog_items_list.get(position).getURL();
                String name=blog_items_list.get(position).getWriter();
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

                BlogActivity.saveBlog(BlogActivity.context,blog_item);
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
                if(BlogActivity.isBlogBookmarked(blog_item)){
                    savedIcon.setImageResource(R.drawable.ic_saved_icon);
                    Toast.makeText(MainActivity.activity, "সেভ হয়েছে", Toast.LENGTH_SHORT).show();
                }
                else{
                    savedIcon.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                    Toast.makeText(MainActivity.activity, "সেভ রিমুভ হয়েছে", Toast.LENGTH_SHORT).show();
                }
                break;


            case loveClicker:
                BlogActivity.loveBlog(blog_item);
                ImageView loveIcon=(ImageView)view.findViewById(R.id.lovedIcon);

                if(BlogActivity.isBlogLove(blog_item)){
                    loveIcon.setImageResource(R.drawable.ic_love_icon);
                    Toast.makeText(BlogActivity.context, "পছন্দ", Toast.LENGTH_SHORT).show();
                }
                else {
                    loveIcon.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    Toast.makeText(BlogActivity.context, "অপছন্দ", Toast.LENGTH_SHORT).show();
                }
                break;
        }



    }

    @Override
    public void onItemLongPressed(int position, View view) {

    }
    public Intent getFacebookIntent(String url) {

        PackageManager pm = MainActivity.context.getPackageManager();
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

}
