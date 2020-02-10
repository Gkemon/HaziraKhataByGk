package com.Teachers.HaziraKhataByGk.HelperClassess.ViewUtils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import com.Teachers.HaziraKhataByGk.R;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class UtilsView {

    public static void swapView(ViewGroup currentParentView, ViewGroup targetParentView, View targetView) {
        currentParentView.removeAllViews();
        targetParentView.removeAllViews();
        showInvisibleAnimation(targetView);
        targetParentView.addView(targetView);
        showVisibileAnimation(targetView);
    }

    public static RequestOptions getLoadingOptionForGlide(Context context) {
        return new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.ic_profile_pic)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();
    }

    public static void showInvisibleAnimation(View v) {
        v.animate()
                .alpha(0.0f)
                .setDuration(1500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        v.setVisibility(View.GONE);
                    }
                });
    }

    public static void showVisibileAnimation(View v) {
        v.animate()
                .alpha(1.0f)
                .setDuration(1500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        v.setVisibility(View.VISIBLE);
                    }
                });
    }
}
