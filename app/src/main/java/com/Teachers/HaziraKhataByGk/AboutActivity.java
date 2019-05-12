package com.Teachers.HaziraKhataByGk;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;

/**
 * Created by uy on 12/2/2017.
 */

public class AboutActivity extends AppCompatActivity {

    Button contact;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        contact=(Button)findViewById(R.id.contact);

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UtilsCommon.openWithFaceBook("https://www.facebook.com/comrate.lenin.7",AboutActivity.this);

            }
        });
    }



}
