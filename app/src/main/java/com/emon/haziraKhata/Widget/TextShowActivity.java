package com.emon.haziraKhata.Widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.emon.haziraKhata.HelperClasses.ViewUtils.BaseActivity;
import com.emon.haziraKhata.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Created by GK on 10/21/2017.
 */

public class TextShowActivity extends BaseActivity {
    String title;
    FloatingActionButton shareButton;
    TextView mainContent, heading;

    public static void start(Context context) {
        Intent intent = new Intent(context, TextShowActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.text_content);

        shareButton = (FloatingActionButton) findViewById(R.id.share_content);
        mainContent = (TextView) findViewById(R.id.description_textview);

        mainContent.setText(R.string.nibondhon_details);


        //Initialize toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.text_toolbar);
        title = getIntent().getStringExtra("subjectName");
        Log.d("GK", title + " subjectName");
        // heading.setText(subjectName);
        //Set custom subjectName


        if (title != null) {
            if (title.equals("1")) {
                toolbar.setTitle("নিবন্ধন সমাচার");
                mainContent.setText(R.string.nibondhon_details);
            } else if (title.equals("2")) {
                toolbar.setTitle("১৪ তম শিক্ষক নিবন্ধন(স্কুল পর্যায়)");
                mainContent.setText(R.string.n1);
            } else if (title.equals("3")) {
                toolbar.setTitle("১৪ তম শিক্ষক নিবন্ধন(কলেজ পর্যায়)");
                mainContent.setText(R.string.n2);
            } else if (title.equals("4")) {
                toolbar.setTitle("১৩ তম শিক্ষক নিবন্ধন");
                mainContent.setText(R.string.n3);
            } else if (title.equals("5")) {
                toolbar.setTitle("১২ তম শিক্ষক নিবন্ধন");
                mainContent.setText(R.string.n4);
            } else if (title.equals("6")) {
                toolbar.setTitle("১০ম শিক্ষক নিবন্ধন");
                mainContent.setText(R.string.n5);
            }
        }


        setSupportActionBar(toolbar);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);

                if (title != null) {
                    if (title.equals("1")) {
                        intent.putExtra(Intent.EXTRA_TEXT, "বেসরকারি শিক্ষক নিবন্ধন পরীক্ষার সমাচার" + "\n\nবিস্তারিত জানতে ডাউনলোড করুন #হাজিরা_খাতা \n (বাংলাভাষায় প্রথম ক্লাস ম্যানেজমেন্ট মোবাইল এপ।)\n ডাউনলোড লিংক : https://play.google.com/store/apps/details?id=com.Teachers.HaziraKhataByGk");

                    } else if (title.equals("2")) {
                        intent.putExtra(Intent.EXTRA_TEXT, "১৪ তম শিক্ষক নিবন্ধনের প্রশ্ন এবং উত্তর (স্কুল পর্যায়)" + "\n\nবিস্তারিত জানতে ডাউনলোড করুন #হাজিরা_খাতা \n (বাংলাভাষায় প্রথম ক্লাস ম্যানেজমেন্ট মোবাইল এপ।)\n ডাউনলোড লিংক : https://play.google.com/store/apps/details?id=com.Teachers.HaziraKhataByGk");

                    } else if (title.equals("3")) {
                        intent.putExtra(Intent.EXTRA_TEXT, "১৪ তম শিক্ষক নিবন্ধনের প্রশ্ন এবং উত্তর (কলেজ পর্যায়)" + "\n\nবিস্তারিত জানতে ডাউনলোড করুন #হাজিরা_খাতা \n (বাংলাভাষায় প্রথম ক্লাস ম্যানেজমেন্ট মোবাইল এপ।)\n ডাউনলোড লিংক : https://play.google.com/store/apps/details?id=com.Teachers.HaziraKhataByGk");


                    } else if (title.equals("4")) {
                        intent.putExtra(Intent.EXTRA_TEXT, "১৩ তম নিবন্ধনের প্রশ্ন এবং উত্তর" + "\n\nবিস্তারিত জানতে ডাউনলোড করুন #হাজিরা_খাতা \n (বাংলাভাষায় প্রথম ক্লাস ম্যানেজমেন্ট মোবাইল এপ।)\n ডাউনলোড লিংক : https://play.google.com/store/apps/details?id=com.Teachers.HaziraKhataByGk");

                    } else if (title.equals("5")) {
                        intent.putExtra(Intent.EXTRA_TEXT, "১২ তম শিক্ষক নিবন্ধনের প্রশ্ন এবং উত্তর" + "\n\nবিস্তারিত জানতে ডাউনলোড করুন #হাজিরা_খাতা \n (বাংলাভাষায় প্রথম ক্লাস ম্যানেজমেন্ট মোবাইল এপ।)\n ডাউনলোড লিংক : https://play.google.com/store/apps/details?id=com.Teachers.HaziraKhataByGk");

                    } else if (title.equals("6")) {
                        intent.putExtra(Intent.EXTRA_TEXT, "১০ম শিক্ষক নিবন্ধনের প্রশ্ন এবং উত্তর" + "\n\nবিস্তারিত জানতে ডাউনলোড করুন #হাজিরা_খাতা \n (বাংলাভাষায় প্রথম ক্লাস ম্যানেজমেন্ট মোবাইল এপ।)\n ডাউনলোড লিংক : https://play.google.com/store/apps/details?id=com.Teachers.HaziraKhataByGk");

                    }
                }

                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "শেয়ার করুন।"));

            }
        });


    }
}


