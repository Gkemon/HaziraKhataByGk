package com.emon.haziraKhata;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emon.haziraKhata.Adapter.BlogAdapter;
import com.emon.haziraKhata.Firebase.FirebaseCaller;
import com.emon.haziraKhata.HelperClasses.UtilsCommon;
import com.emon.haziraKhata.HelperClasses.ViewUtils.BaseActivity;
import com.emon.haziraKhata.Listener.RecyclerItemClickListener;
import com.emon.haziraKhata.Model.BlogItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import static com.emon.haziraKhata.HelperClasses.UtilsCommon.isBlogBookmarked;
import static com.emon.haziraKhata.HelperClasses.UtilsCommon.loveBlog;
import static com.emon.haziraKhata.R.id.ClickerForBlog;
import static com.emon.haziraKhata.R.id.SaveClicker;
import static com.emon.haziraKhata.R.id.ShareClicker;
import static com.emon.haziraKhata.R.id.loveClicker;

public class BlogActivity extends BaseActivity implements RecyclerItemClickListener {
    public RecyclerView blogRecycle;
    public Context context;
    public Activity activity;
    public ArrayList<BlogItem> blogItemsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        createBlogList();
    }

    public void createBlogList() {

        blogRecycle = (RecyclerView) findViewById(R.id.blogRecycle);
        FirebaseCaller.getFirebaseDatabase().child("Blog").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                ArrayList<BlogItem> blog_items_temp = new ArrayList<BlogItem>();
                for (DataSnapshot blogData : dataSnapshot.getChildren()) {
                    BlogItem blog;
                    blog = blogData.getValue(BlogItem.class);
                    blog_items_temp.add(blog);
                }
                blogItemsList = blog_items_temp;
                if (blogItemsList.size() > 0 && blogRecycle != null) {
                    Collections.reverse(blogItemsList);
                    BlogAdapter BlogAdapter = new BlogAdapter(blogItemsList);
                    BlogAdapter.setOnItemClickListener(BlogActivity.this);
                    LinearLayoutManager MyLayoutManager = new LinearLayoutManager(BlogActivity.this);
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
    public void onItemClick(int position, View view) {
        BlogItem BlogItem = blogItemsList.get(position);
        switch (view.getId()) {


            case ClickerForBlog:
                String url = blogItemsList.get(position).getURL();
                UtilsCommon.openWithFaceBook(url, BlogActivity.this);
                break;

            case ShareClicker:
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_SEND);
                String heading = blogItemsList.get(position).getHeading();
                String url1 = blogItemsList.get(position).getURL();
                String name = blogItemsList.get(position).getWriter();
                intent1.putExtra(Intent.EXTRA_TEXT, "#শিক্ষক_কথন \n\n" + heading + "\n\nলেখন :" + name + "\nলিংক :" + url1 + "\n\n সংগ্রহে : #হাজিরা_খাতা \n বাংলাভাষায় প্রথম ক্লাস ম্যানেজমেন্ট মোবাইল এপ ।\n ডাউনলোড লিংক : https://play.google.com/store/apps/details?id=com.Teachers.HaziraKhataByGk");
                intent1.setType("text/plain");
                startActivity(Intent.createChooser(intent1, "শেয়ার করুন।"));
                break;


            case SaveClicker:

                ImageView savedIcon = (ImageView) view.findViewById(R.id.SaveClickerIcon);

                if (isBlogBookmarked(BlogItem, BlogActivity.this)) {
                    savedIcon.setImageResource(R.drawable.ic_saved_icon);
                    Toast.makeText(this, "সেভ হয়েছে", Toast.LENGTH_SHORT).show();
                } else {
                    savedIcon.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                    Toast.makeText(this, "সেভ রিমুভ হয়েছে", Toast.LENGTH_SHORT).show();
                }
                break;


            case loveClicker:
                loveBlog(BlogItem, this);
                ImageView loveIcon = view.findViewById(R.id.lovedIcon);

                if (UtilsCommon.isBlogLove(BlogItem, BlogActivity.this)) {
                    loveIcon.setImageResource(R.drawable.ic_love_icon);
                    Toast.makeText(BlogActivity.this, "পছন্দ", Toast.LENGTH_SHORT).show();
                } else {
                    loveIcon.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    Toast.makeText(BlogActivity.this, "অপছন্দ", Toast.LENGTH_SHORT).show();
                }
                break;
        }


    }

    @Override
    public void onItemLongPressed(int position, View view) {

    }

}
