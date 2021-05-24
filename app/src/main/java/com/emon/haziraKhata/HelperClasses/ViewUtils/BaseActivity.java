package com.emon.haziraKhata.HelperClasses.ViewUtils;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.emon.haziraKhata.BuildConfig;
import com.emon.haziraKhata.HelperClasses.UtilsCommon;

/**
 * Created by Gk Emon on 7/3/2020.
 */
public class BaseActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!BuildConfig.DEBUG)
        Thread.setDefaultUncaughtExceptionHandler((paramThread, paramThrowable) -> {
            UtilsCommon.handleError(paramThrowable);
            finish();
        });
    }
}
