package com.Teachers.HaziraKhataByGk.HelperClassess;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.Teachers.HaziraKhataByGk.BlogActivity;
import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.MainActivity;
import com.Teachers.HaziraKhataByGk.Model.BlogItem;
import com.Teachers.HaziraKhataByGk.Model.JobItems;
import com.Teachers.HaziraKhataByGk.Model.NewsItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;

import static com.Teachers.HaziraKhataByGk.MainActivity.saved_newsItem_for_main;

/**
 * Created by uy on 6/18/2018.
 */

public class UtilsCommon {
    public static void HideNotifiationBar(Activity activity){
        //HIDING NOTIFICATION BAR
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    //TODO: save news
    public static void saveNews(Context context, NewsItem newsItem1) {
        NewsItem NewsItem;
        NewsItem = newsItem1;
        boolean exist=false;

        //TODO: this forloop for ensuring the data is exist both shared preference and firebase database
        for(int i = 0; i< saved_newsItem_for_main.size(); i++){
            if(NewsItem.getURL().equals(saved_newsItem_for_main.get(i).getURL())&& NewsItem.getDate().equals(saved_newsItem_for_main.get(i).getDate())&& NewsItem.getHeading().equals(saved_newsItem_for_main.get(i).getHeading())){
                exist=true;
            }
        }



        SharedPreferences pref = context.getSharedPreferences("HaziraKhata", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        //TODO: for bookmark in browser activity

        if ((pref.getBoolean(newsItem1.getURL(), false) && pref.getBoolean(newsItem1.getHeading(), false) && pref.getBoolean(newsItem1.getDate(), false))||exist) {


            editor.putBoolean(newsItem1.getURL(), false);
            editor.putBoolean(newsItem1.getHeading(), false);
            editor.putBoolean(newsItem1.getDate(), false);
            // editor1.putBoolean(newsItem1.getURL(), false);


            Query query =
                    MainActivity.databaseReference.child("Users").child(FirebaseCaller.getUserID()).child("Saved_news").orderByChild("url").equalTo(newsItem1.getURL());
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
            editor.putBoolean(newsItem1.getURL(), true);
            editor.putBoolean(newsItem1.getHeading(), true);
            editor.putBoolean(newsItem1.getDate(), true);
            //editor1.putBoolean(newsItem1.getURL(), true);
            MainActivity.databaseReference.child("Users").child(FirebaseCaller.getUserID()).child("Saved_news").push().setValue(newsItem1);

        }

        //editor1.commit();
        editor.apply();
    }

    //TODO: check news
    public static boolean isNewsBookmarked(NewsItem NewsItem,Context context){

        SharedPreferences pref = context.getSharedPreferences("HaziraKhata", 0);

        if(pref.getBoolean(NewsItem.getURL(), false) && pref.getBoolean(NewsItem.getHeading(), false) && pref.getBoolean(NewsItem.getDate(), false)){
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

            Query query=MainActivity.databaseReference.child("Users").child(FirebaseCaller.getUserID()).child("Saved_jobs").orderByChild("url").equalTo(URL);
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

            Query query=MainActivity.databaseReference.child("Users").child(FirebaseCaller.getUserID()).child("Saved_jobs").orderByChild("url").equalTo(URL);
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
            MainActivity.databaseReference.child("Users").child(FirebaseCaller.getUserID()).child("Saved_jobs").push().setValue(JobItems);
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


    //TODO: check news as if loved or not
    public  static boolean isBlogLove(BlogItem BlogItem,Context context){
        SharedPreferences pref = context.getSharedPreferences("teacher_blog_loved", 0);

        return pref.getBoolean(BlogItem.getURL(), false) && pref.getBoolean(BlogItem.getHeading(), false) && pref.getBoolean(BlogItem.getDate(), false) && pref.getBoolean(BlogItem.getWriter(), false);
    }


    //TODO: check news
    public static boolean isBlogBookmarked(BlogItem BlogItem, Context context){


        SharedPreferences pref = context.getSharedPreferences("teacher_blog_saved", 0);

        if(pref.getBoolean(BlogItem.getURL(), false) && pref.getBoolean(BlogItem.getHeading(), false) && pref.getBoolean(BlogItem.getDate(), false)&&pref.getBoolean(BlogItem.getWriter(), false) ){
            return true;
        }
        return false;
    }


    //TODO: check love jobs
    public static boolean isJobLoved(Context context,String post, String institute , String place ,String URL) {
        SharedPreferences pref = context.getSharedPreferences("HaziraKhata_love_job", 0);
        if(pref.getBoolean(URL, false)&&pref.getBoolean(post, false)&&pref.getBoolean(institute, false)&&pref.getBoolean(place, false)){
            return true;
        }
        return false;
    }


    //TODO: for opeing in fb app if it is installed.
    public static void openWithFaceBook(String url,Context context) {


        PackageManager pm;

        pm = context.getPackageManager();


        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        }
        catch (PackageManager.NameNotFoundException ignored) {

        }

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(intent);
        }
        catch (Exception e){
            Toast.makeText(context,"ERROR "+e.getMessage(),Toast.LENGTH_LONG).show();
        }

        return ;
    }

    public static void openInAppBrowser(String url, Context context) {

        //TODO: for opening webview in App
//        Intent intent = new Intent(MainActivity.activity, BrowsingActivity.class_room);
//        intent.putExtra("URL", url);
//        startActivity(intent);
        //TODO: for opening default browser
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);

    }
    public static void logObject(Object object){
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(object);
        Logger.json(gson.toJson(object));
    }

    public static void logString(String object){
        Logger.d("GK",object);

    }

    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}
