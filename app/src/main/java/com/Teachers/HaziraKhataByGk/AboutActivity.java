package com.Teachers.HaziraKhataByGk;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
        contact = findViewById(R.id.contact);

        contact.setOnClickListener(v -> UtilsCommon.openWithFaceBook("https://www.facebook.com/comrate.lenin.7", AboutActivity.this));
    }


}
