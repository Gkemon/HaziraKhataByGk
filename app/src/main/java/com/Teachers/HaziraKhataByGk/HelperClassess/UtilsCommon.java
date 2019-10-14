package com.Teachers.HaziraKhataByGk.HelperClassess;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.Login.LoginActivity;
import com.Teachers.HaziraKhataByGk.Model.AttendenceData;
import com.Teachers.HaziraKhataByGk.Model.BlogItem;
import com.Teachers.HaziraKhataByGk.Model.ClassItem;
import com.Teachers.HaziraKhataByGk.Model.JobItems;
import com.Teachers.HaziraKhataByGk.Model.NewsItem;
import com.Teachers.HaziraKhataByGk.Model.Student;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.SignupActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.Teachers.HaziraKhataByGk.MainActivity.saved_blogItem_for_main;
import static com.Teachers.HaziraKhataByGk.MainActivity.saved_newsItem_for_main;

/**
 * Created by uy on 6/18/2018.
 */

public class UtilsCommon {


    public static boolean isValidPhoneNumber(String phone){

        if(!isValideString(phone))return false;

        String pattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
       return phone.matches(pattern);
    }

    public static Student getCurrentStudent(Context context){
        String studentPref = "currentProfile";
        Gson gson = new Gson();
            SharedPreferenceManager sharedPreferenceManager =
                    new SharedPreferenceManager(context, studentPref);
            String json = sharedPreferenceManager.getValue("item_student", "");
            return gson.fromJson(json, Student.class);
    }

    public static void setCurrentStudent(Student student,Activity activity){
        String studentPref = "currentProfile";
        Gson gson = new Gson();
        String json = gson.toJson(student);
        SharedPreferenceManager sharedPreferenceManager=
                new SharedPreferenceManager(activity,studentPref);
        sharedPreferenceManager.setValue("item_student", json);
    }
    public static ClassItem getCurrentClass(Context context){
        String classPref = "currentClass";
        Gson gson = new Gson();
        SharedPreferenceManager sharedPreferenceManager =
                new SharedPreferenceManager(context, classPref);
        String json = sharedPreferenceManager.getValue("currentClass", "");
        return gson.fromJson(json, ClassItem.class);
    }

    public static void setCurrentClass(ClassItem classItem,Activity activity){
        String classPref = "currentClass";
        Gson gson = new Gson();
        String json = gson.toJson(classItem);
        SharedPreferenceManager sharedPreferenceManager=
                new SharedPreferenceManager(activity,classPref);
        sharedPreferenceManager.setValue("currentClass", json);
    }

    public static void setAllClass(CustomArrayList<ClassItem> classItemList,Activity activity){
        String classPref = "classList";
        Gson gson = new Gson();
        Type baseType = new TypeToken<CustomArrayList<ClassItem>>() {}.getType();

        String json = gson.toJson(classItemList,baseType);
        SharedPreferenceManager sharedPreferenceManager=
                new SharedPreferenceManager(activity,classPref);
        sharedPreferenceManager.setValue("classList", json);
    }

    public static CustomArrayList<ClassItem> getAllClass(Activity activity){
        String classPref = "classList";
        Gson gson = new Gson();
        SharedPreferenceManager sharedPreferenceManager =
                new SharedPreferenceManager(activity, classPref);
        String json = sharedPreferenceManager.getValue("classList", "");
        Type baseType = new TypeToken<CustomArrayList<ClassItem>>() {}.getType();

        return gson.fromJson(json, baseType);
    }



    public static final String DateFormate="EEE, d MMM yyyy";

    public static ArrayList<AttendenceData> getAttendanceListFromSnapshot(DataSnapshot snapshot){

        ArrayList<AttendenceData> attendenceData= new ArrayList<>();

        for(long i=0;i<snapshot.getChildrenCount();i++){
            attendenceData.add(snapshot.getValue(AttendenceData.class));
        }
        return attendenceData;
    }

    public static boolean isValideString(String s){

        if(s==null)return false;
        else return !s.isEmpty();

    }


    //TODO: love BlogActivity
    public  static void loveBlog (BlogItem BlogItem,Context context){


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
    public static boolean saveBlog(Context context, BlogItem blogItem) {
        BlogItem blog;
        blog=blogItem;
        boolean exist=false;

        if(saved_blogItem_for_main==null)
        {
            showDialogForSignUp(context);
            return false;
        }

        //TODO: this forloop for ensuring the data is exist both shared preference and firebase database
        for(int i = 0; i< saved_blogItem_for_main.size(); i++){
            if(blog.getURL().equals(saved_blogItem_for_main.get(i).getURL())&&blog.getDate().equals(saved_blogItem_for_main.get(i).getDate())&&blog.getHeading().equals(saved_blogItem_for_main.get(i).getHeading())&&blog.getWriter().equals(saved_blogItem_for_main.get(i).getWriter())){
                exist=true;
            }
        }

        SharedPreferences pref = context.getSharedPreferences("teacher_blog_saved", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        //TODO: for bookmark in browser activity

        if ((pref.getBoolean(blog.getURL(), false) && pref.getBoolean(blog.getHeading(), false) && pref.getBoolean(blog.getDate(), false)&&pref.getBoolean(blog.getWriter(), false))||exist) {

            editor.putBoolean(blog.getURL(), false);
            editor.putBoolean(blog.getHeading(), false);
            editor.putBoolean(blog.getDate(), false);
            editor.putBoolean(blog.getWriter(), false);

            Query query =
                    FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Saved_blog").orderByChild("url").equalTo(blog.getURL());
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
            FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Saved_blog").push().setValue(blog);

        }

        editor.apply();

        return true;
    }


    public static void hideNotificationStatus(Activity activity){
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    public static void showToast(String msg){
        Toast.makeText(GlobalContext.getAppContext(),msg,Toast.LENGTH_LONG).show();
    }


    public static void showDialogForSignUp(final Context activity){

        android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(activity).create();
        alertDialog.setTitle("আপনি এখনো লগিন করেননি বা একাউন্ট খুলেননি");
        alertDialog.setIcon(R.drawable.warnig_for_delete);
        alertDialog.setMessage("আপনি একাউন্ট না খুলে থাকলে সাইন আপ করুন।আর যদি আপনার আগেই একাউন্ট থেকে থাকে তাহলে লগিন করুন।");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"সাইন আপ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        activity.startActivity(new Intent(activity, SignupActivity.class));
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "লগিন", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    activity.startActivity(new Intent(activity, LoginActivity.class));
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "বাদ দিন", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public static void debugLog(String msg){

        final StackTraceElement stackTrace = new Exception().getStackTrace()[1];

        String fileName = stackTrace.getFileName();
        if (fileName == null) fileName="";  // It is necessary if you want toTime use proguard obfuscation.

        final String info = stackTrace.getMethodName() + " (" + fileName + ":"
                + stackTrace.getLineNumber() + ")";

        Log.d("GK", info + ": " + msg);
    }

    //TODO: save news
    public static boolean saveNews(Context context, NewsItem newsItem1) {
        NewsItem NewsItem;
        NewsItem = newsItem1;
        boolean exist=false;



        //TODO: this forloop for ensuring the data is exist both shared preference and firebase database

        if(saved_newsItem_for_main==null)
        {
            showDialogForSignUp(context);
            return false;
        }
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
                    FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Saved_news").orderByChild("url").equalTo(newsItem1.getURL());
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
            FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Saved_news").push().setValue(newsItem1);

        }

        editor.apply();

        return true;
    }

    //TODO: check news
    public static boolean isNewsBookmarked(NewsItem NewsItem,Context context){

        SharedPreferences pref = context.getSharedPreferences("HaziraKhata", 0);

        return pref.getBoolean(NewsItem.getURL(), false) && pref.getBoolean(NewsItem.getHeading(), false) && pref.getBoolean(NewsItem.getDate(), false);
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

            Query query=FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Saved_jobs").orderByChild("url").equalTo(URL);
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

            Query query=FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Saved_jobs").orderByChild("url").equalTo(URL);
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
            FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Saved_jobs").push().setValue(JobItems);
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

        if(pref.getBoolean(BlogItem.getURL(), false) &&
                pref.getBoolean(BlogItem.getHeading(), false) &&
                pref.getBoolean(BlogItem.getDate(), false)&&pref.getBoolean(BlogItem.getWriter(), false) ){
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
