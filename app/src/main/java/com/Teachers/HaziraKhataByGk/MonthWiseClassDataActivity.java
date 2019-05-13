package com.Teachers.HaziraKhataByGk;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MonthWiseClassDataActivity extends AppCompatActivity {
TextView textView;


     String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_month_wise_class_data);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        textView=(TextView)findViewById(R.id.text);
        text=getIntent().getStringExtra("month");
        textView.setText(text);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,text);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent,"ডাটাসমূহ শেয়ার,প্রিন্ট অথবা সেভ করতে রাখতে পারেন অন্য কোথাও"));
            }
        });





    }



    @Override
    public void onResume() {
        textView.setText(text);

        super.onResume();
    }



}
