package com.Teachers.HaziraKhataByGk.Widget;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.nineoldandroids.view.ViewHelper;

public class MyPageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View view, float position) {

        if (position < 0) {
            ViewHelper.setAlpha(view, position + 1);

        } else if (position < 1) {
            view.setTranslationX(view.getMeasuredWidth() * -position);
        }

    }
}
