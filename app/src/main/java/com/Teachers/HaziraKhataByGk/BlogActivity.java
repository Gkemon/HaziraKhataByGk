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

import com.Teachers.HaziraKhataByGk.Adapter.BlogAdapter;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.Listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.Model.BlogItem;
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

import static com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon.isBlogBookmarked;
import static com.Teachers.HaziraKhataByGk.MainActivity.saved_blogItem_for_main;
import static com.Teachers.HaziraKhataByGk.R.id.ClickerForBlog;
import static com.Teachers.HaziraKhataByGk.R.id.SaveClicker;
import static com.Teachers.HaziraKhataByGk.R.id.ShareClicker;
import static com.Teachers.HaziraKhataByGk.R.id.loveClicker;

public class BlogActivity extends AppCompatActivity implements RecyclerItemClickListener {
    public static RecyclerView blogRecycle;
    public static Context context;
    public static Activity activity;
    public static ArrayList<BlogItem> blog_items_list;

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



                ArrayList<BlogItem> blog_items_temp=new ArrayList<BlogItem>();
                for(DataSnapshot blogData:dataSnapshot.getChildren()){
                    BlogItem blog;
                    blog=blogData.getValue(BlogItem.class);
                    blog_items_temp.add(blog);
                }
                blog_items_list =blog_items_temp;
                if(blog_items_list.size()>0&&blogRecycle!=null) {
                    Collections.reverse(blog_items_list);
                    BlogAdapter BlogAdapter = new BlogAdapter(blog_items_list);
                    BlogAdapter.setOnItemClickListener(BlogActivity.this);
                    LinearLayoutManager MyLayoutManager = new LinearLayoutManager(BlogActivity.context);
                    MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    blogRecycle.setAdapter(BlogAdapter);
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






    //TODO: love BlogActivity
    public static void loveBlog (BlogItem BlogItem){


        SharedPreferences pref = context.getSharedPreferences("teacher_blog_loved", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        String URL,title,name,date;
        URL= BlogItem.getURL();
        title= BlogItem.getHeading();
        name= BlogItem.getWriter();
        date= BlogItem.getDate();
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











    //TODO: save news
    public static void saveBlog(Context context, BlogItem blogItem) {
        BlogItem blog;
        blog=blogItem;
        boolean exist=false;

        //TODO: this forloop for ensuring the data is exist both shared preference and firebase database
        for(int i = 0; i< saved_blogItem_for_main.size(); i++){
            if(blog.getURL().equals(saved_blogItem_for_main.get(i).getURL())&&blog.getDate().equals(saved_blogItem_for_main.get(i).getDate())&&blog.getHeading().equals(saved_blogItem_for_main.get(i).getHeading())&&blog.getWriter().equals(saved_blogItem_for_main.get(i).getWriter())){
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

        editor.apply();
    }


    @Override
    public void onItemClick(int position, View view) {
        BlogItem BlogItem =blog_items_list.get(position);
        switch (view.getId()) {


            case ClickerForBlog:
                String url=blog_items_list.get(position).getURL();
                UtilsCommon.openWithFaceBook(url,BlogActivity.this);
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

                BlogActivity.saveBlog(BlogActivity.context, BlogItem);
                ImageView savedIcon=(ImageView)view.findViewById(R.id.SaveClickerIcon);

                if(isBlogBookmarked(BlogItem,BlogActivity.this)){
                    savedIcon.setImageResource(R.drawable.ic_saved_icon);
                    Toast.makeText(this, "সেভ হয়েছে", Toast.LENGTH_SHORT).show();
                }
                else{
                    savedIcon.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                    Toast.makeText(this, "সেভ রিমুভ হয়েছে", Toast.LENGTH_SHORT).show();
                }
                break;


            case loveClicker:
                BlogActivity.loveBlog(BlogItem);
                ImageView loveIcon=(ImageView)view.findViewById(R.id.lovedIcon);

                if(UtilsCommon.isBlogLove(BlogItem,BlogActivity.this)){
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

}
