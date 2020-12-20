package com.Teachers.HaziraKhataByGk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.Teachers.HaziraKhataByGk.HelperClasses.ViewUtils.BaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MonthWiseClassDataActivity extends BaseActivity {
    TextView textView;


    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_month_wise_class_data);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        textView = (TextView) findViewById(R.id.text);
        text = getIntent().getStringExtra("month");
        textView.setText(text);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, text);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "ডাটাসমূহ শেয়ার,প্রিন্ট অথবা সেভ করতে রাখতে পারেন অন্য কোথাও"));
            }
        });


    }


    @Override
    public void onResume() {
        textView.setText(text);

        super.onResume();
    }


}
