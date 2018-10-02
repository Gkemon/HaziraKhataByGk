package com.Teachers.HaziraKhataByGk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class modelTestChoose extends AppCompatActivity {
    public static Context context;
    Button min15,min30,min60;
    public LinearLayout adlayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //HIDING NOTIFICATION BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.model_test_choose);
        context=this;
        min15=(Button)findViewById(R.id.min15);
        min30=(Button)findViewById(R.id.min30);
        min60=(Button)findViewById(R.id.min60);


        min15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(modelTestChoose.context,model_test.class);
                intent.putExtra("time","15");
                startActivity(intent);
            }
        });

        min30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(modelTestChoose.context,model_test.class);
                intent.putExtra("time","30");
                startActivity(intent);
            }
        });

        min60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(modelTestChoose.context,model_test.class);
                intent.putExtra("time","60");
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
