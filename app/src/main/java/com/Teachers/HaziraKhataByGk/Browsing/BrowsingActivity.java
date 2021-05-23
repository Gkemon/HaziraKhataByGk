package com.Teachers.HaziraKhataByGk.Browsing;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.Teachers.HaziraKhataByGk.HelperClasses.ViewUtils.BaseActivity;
import com.Teachers.HaziraKhataByGk.Model.BlogItem;
import com.Teachers.HaziraKhataByGk.Model.JobItems;
import com.Teachers.HaziraKhataByGk.Model.NewsItem;
import com.Teachers.HaziraKhataByGk.R;

/**
 * Created by GK on 10/29/2017.
 */

public class BrowsingActivity extends BaseActivity {
    public static Activity activity;
    CoordinatorLayout coordinatorLayout;
    private String url, TAG, title;
    private WebView webView;
    private ProgressBar progressBar;
    private float m_downX;
    private NewsItem NewsItem;
    private JobItems jobItems;
    private BlogItem BlogItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("");
        Intent i = getIntent();
        url = getIntent().getStringExtra("URL");
        TAG = getIntent().getStringExtra("TAG");
        activity = this;

        if (TAG != null) {
            if (TAG.equals("NEWS")) {
                Log.d("GK", "TAG IS NEWS");
                NewsItem = new NewsItem();
                NewsItem = i.getParcelableExtra("NEWS");
                title = NewsItem.getHeading();
            } else if (TAG.equals("JOB")) {
                Log.d("GK", "TAG IS +NEWS");
                jobItems = new JobItems();
                jobItems = i.getParcelableExtra("JOB");
                title = jobItems.getPost();
            } else if (TAG.equals("BLOG")) {
                BlogItem = new BlogItem();
                BlogItem = i.getParcelableExtra("BLOG");
                title = BlogItem.getHeading();
            } else if (TAG.equals("OTHER")) {
                title = webView.getTitle();
            }
        }


        // if no url is passed, close the activity
        if (TextUtils.isEmpty(url)) {
            finish();
        }

        // webView = (WebView) findViewById(R.id.webView);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);


        //DON'T FORGET TO USE http

        //TODO:This is for webview
        //initWebView();
        //webView.loadUrl(url);

        //TODO:This is for default browser
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);


    }
//    private void initWebView() {
//        webView.setWebChromeClient(new MyWebChromeClient(this));
//        webView.setWebViewClient(new WebViewClient() {
//                                     @Override
//                                     public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                                         super.onPageStarted(view, url, favicon);
//                                         progressBar.setVisibility(View.VISIBLE);
//                                         invalidateOptionsMenu();
//                                     }
//
//                                     @Override
//                                     public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                                         if (!url.startsWith("http://") && !url.startsWith("https://"))
//                                             url = "http://" + url;
//
//                                         if (url.startsWith("https://drive.google.com/file/")) {
//                                             Intent i = new Intent(Intent.ACTION_VIEW);
//                                             i.setData(Uri.parse(url));
//                                             startActivity(i);
//                                             return true;
//                                         } else {
//                                             webView.loadUrl(url);
//                                             return true;
//                                         }
//
//
//                                     }
//
//                                 });

//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                progressBar.setVisibility(View.GONE);
//                invalidateOptionsMenu();
//            }
//
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                super.onReceivedError(view, request, error);
//                progressBar.setVisibility(View.GONE);
//                invalidateOptionsMenu();
//            }
//        });
//        webView.clearCache(true);
//        webView.clearHistory();
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setHorizontalScrollBarEnabled(false);
//        webView.setOnTouchListener(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//
//                if (event.getPointerCount() > 1) {
//                    //Multi touch detected
//                    return true;
//                }
//
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN: {
//                        // save the x
//                        m_downX = event.getX();
//                    }
//                    break;
//
//                    case MotionEvent.ACTION_MOVE:
//                    case MotionEvent.ACTION_CANCEL:
//                    case MotionEvent.ACTION_UP: {
//                        // set x so that it doesn't move
//                        event.setLocation(m_downX, event.getY());
//                    }
//                    break;
//                }
//
//                return false;
//            }
//        });
//    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items toTime the action bar if it is present.
//        getMenuInflater().inflate(R.menu.browser, menu);
//
//        tintMenuIcon(getApplicationContext(), menu.getItem(0), android.R.color.white);
// if (isBookmarked(this, webView.getUrl())) {
//        // change icon color
//        tintMenuIcon(getApplicationContext(), menu.getItem(1), R.color.colorAccent);
//    } else {
//        tintMenuIcon(getApplicationContext(), menu.getItem(1), android.R.color.white);
//    }
//        tintMenuIcon(getApplicationContext(), menu.getItem(2), android.R.color.white);
//        tintMenuIcon(getApplicationContext(), menu.getItem(3), android.R.color.white);
//        tintMenuIcon(getApplicationContext(), menu.getItem(4), android.R.color.white);
//
//        return true;
//}
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        if (item.getItemId() == android.R.id.home) {
//            Intent intent=new Intent(BrowsingActivity.activity,MainActivity.class_room);
//            intent.putExtra("FLAG","TWO");
//            startActivity(intent);
//            finish();
//        }
//
//        if (item.getItemId() == R.id.action_bookmark) {
//
//            //TODO: For getting web subjectName
//            if(!webView.getUrl().equals(url)){
//                subjectName=webView.getTitle();
//                url=webView.getUrl();
//                TAG="OTHER";
//                Log.d("GK","TAG is other now "+url);
//            }
//            // bookmark / unbookmark the url
//            bookmarkUrl(this, url);
//            Log.d("GK","URL is "+url);
//
//            String msg = isBookmarked(this, webView.getUrl()) ?
//                    "লিংকটি সেভ হয়েছে" :
//                    webView.getTitle() + "লিংকটির সেভ রিমুভ হয়েছে";
//            tintMenuIcon(getApplicationContext(), item, android.R.color.white);
//            if (isBookmarked(this, webView.getUrl())) {
//                // change icon color
//                tintMenuIcon(getApplicationContext(), item, R.color.colorAccent);
//            } else {
//                tintMenuIcon(getApplicationContext(), item, android.R.color.white);
//            }
//
//            Snackbar snackbar = Snackbar
//                    .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
//            snackbar.show();
//
//            // refresh the toolbar icons, so that bookmark icon color changes
//            invalidateOptionsMenu();
//        }
//        if(item.getItemId()==R.id.action_copy_URL){
//            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//            CharSequence URL=webView.getUrl();
//            CharSequence massegeToUser="এই সাইটের লিংকটি কপি করা হয়েছে।";
//            ClipData clip = ClipData.newPlainText(massegeToUser, URL);
//            clipboard.setPrimaryClip(clip);
//            Snackbar snackbar = Snackbar
//                    .make(coordinatorLayout, massegeToUser, Snackbar.LENGTH_LONG);
//            snackbar.show();
//        }
//        if (item.getItemId() == R.id.action_back) {
//            back();
//        }
//
//        if (item.getItemId() == R.id.action_forward) {
//            forward();
//        }
//        if(item.getItemId()==R.id.action_share){
//            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_SEND);
//
//            //TODO: For getting web subjectName
//            if(!webView.getUrl().equals(url)){
//                subjectName=webView.getTitle();
//                url=webView.getUrl();
//            }
//
//            intent.putExtra(Intent.EXTRA_TEXT,subjectName+"\n"+"লিংক :"+url+"\n\n সংগ্রহ : #হাজিরা_খাতা \n বাংলাভাষায় প্রথম ক্লাস ম্যানেজমেন্ট এপ ।\n ডাউনলোড লিংক : ");
//            intent.setType("text/plain");
//            startActivity(Intent.createChooser(intent,"শেয়ার করুন।"));
//
//
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//    // backward the browser navigation
//    private void back() {
//        if (webView.canGoBack()) {
//            webView.goBack();
//        }
//    }
//
//    // forward the browser navigation
//    private void forward() {
//        if (webView.canGoForward()) {
//            webView.goForward();
//        }
//    }
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//
//        // menu item 0-index is bookmark icon
//
//        // enable - disable the toolbar navigation icons
//
//        if (!webView.canGoBack()) {
//            menu.getItem(2).setEnabled(false);
//            menu.getItem(2).getIcon().setAlpha(100);
//        } else {
//            menu.getItem(2).setEnabled(true);
//            menu.getItem(2).getIcon().setAlpha(255);
//        }
//
//        if (!webView.canGoForward()) {
//            menu.getItem(3).setEnabled(false);
//            menu.getItem(3).getIcon().setAlpha(100);
//        } else {
//            menu.getItem(3).setEnabled(true);
//            menu.getItem(3).getIcon().setAlpha(255);
//        }
//
//        return true;
//    }
//    private class_room MyWebChromeClient extends WebChromeClient {
//        Context context;
//
//        public MyWebChromeClient(Context context) {
//            super();
//            this.context = context;
//        }
//    }
//
//    public static void tintMenuIcon(Context context, MenuItem item, int color) {
//        Drawable drawable = item.getIcon();
//        if (drawable != null) {
//            // If we don't mutate the drawable, then all drawable's with this id will have a color
//            // filter applied toTime it.
//            drawable.mutate();
//            drawable.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_ATOP);
//        }
//    }
//
//
//
//    public void bookmarkUrl(Context context, String url) {
//
//        if(TAG.equals("NEWS")){
//            MainActivity.saveNews(getContext(),NewsItem);
//        }
//        else if(TAG.equals("JOB"))
//        {
//            MainActivity.saveJob(getContext(),jobItems.getPost(),jobItems.getInstitute(),jobItems.getPlace(),jobItems.getURL());
//
//        }
//        else if(TAG.equals("BLOG"))
//        {
//
//        }
//        else if(TAG.equals("OTHER"))
//        {
//            others_data others_data=new others_data();
//            others_data.setTitle(webView.getTitle());
//            others_data.setUrl(webView.getUrl());
//            MainActivity.databaseReference.child("Saved_others").push().setValue(others_data);
//        }
//
//        SharedPreferences pref = context.getSharedPreferences("HaziraKhata_others", 0); // 0 - for private mode
//        SharedPreferences.Editor editor = pref.edit();
//
//        // if url is already bookmarked, unbookmark it
//        if (pref.getBoolean(url, false)){
//
//            Query query = MainActivity.databaseReference.child("Saved_news").orderByChild("url").equalTo(url);
//            query.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        snapshot.getRef().child("url").removeValue();
//                        snapshot.getRef().child("date").removeValue();
//                        snapshot.getRef().child("heading").removeValue();
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//
//            editor.putBoolean(url, false);
//        } else {
//            editor.putBoolean(url, true);
//        }
//
//        editor.apply();
//        editor.commit();
//    }
//
//    public static boolean isBookmarked(Context context, String url) {
//        SharedPreferences pref = context.getSharedPreferences("HaziraKhata_others", 0);
//        return pref.getBoolean(url, false);
//    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent=new Intent(BrowsingActivity.activity,MainActivity.class_room);
//        intent.putExtra("FLAG","TWO");
//        startActivity(intent);
//    }

}