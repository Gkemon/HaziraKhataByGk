package com.emon.haziraKhata;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.emon.haziraKhata.HelperClasses.UtilsCommon;
import com.emon.haziraKhata.HelperClasses.ViewUtils.BaseActivity;

/**
 * Created by GK on 12/2/2017.
 */

public class AboutActivity extends BaseActivity {

    Button contact;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        contact = findViewById(R.id.contact);

        contact.setOnClickListener(v -> UtilsCommon.openWithFaceBook("https://www.facebook.com/comrate.lenin.7", AboutActivity.this));
    }


}
