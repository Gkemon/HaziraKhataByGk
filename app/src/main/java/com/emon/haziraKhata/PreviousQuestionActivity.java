package com.emon.haziraKhata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.emon.haziraKhata.HelperClasses.ViewUtils.BaseActivity;
import com.emon.haziraKhata.Widget.TextShowActivity;

/**
 * Created by GK on 12/1/2017.
 */

public class PreviousQuestionActivity extends BaseActivity {
    Activity activity;
    Button button1, button2, button3, button4, button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;

        setContentView(R.layout.previous_year_questions);
        button1 = (Button) findViewById(R.id.no1);
        button2 = (Button) findViewById(R.id.no2);
        button3 = (Button) findViewById(R.id.no3);
        button4 = (Button) findViewById(R.id.no4);
        button5 = (Button) findViewById(R.id.no5);


        button1.setOnClickListener(v -> {
            Intent intent = new Intent(activity, TextShowActivity.class);
            intent.putExtra("subjectName", "2");
            startActivity(intent);
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, TextShowActivity.class);
                intent.putExtra("subjectName", "3");
                startActivity(intent);


            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(activity, TextShowActivity.class);
                intent.putExtra("subjectName", "4");
                startActivity(intent);


            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(activity, TextShowActivity.class);
                intent.putExtra("subjectName", "5");
                startActivity(intent);


            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(activity, TextShowActivity.class);
                intent.putExtra("subjectName", "6");
                startActivity(intent);


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

