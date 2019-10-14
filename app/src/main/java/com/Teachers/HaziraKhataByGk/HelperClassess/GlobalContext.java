package com.Teachers.HaziraKhataByGk.HelperClassess;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;
import android.support.v7.view.ContextThemeWrapper;

import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.Model.ClassItem;
import com.Teachers.HaziraKhataByGk.Model.Student;
import com.Teachers.HaziraKhataByGk.R;

import java.lang.ref.WeakReference;

public class GlobalContext extends MultiDexApplication {

    private static Application application;
    public static WeakReference<Activity> mActivity = null;
    public static Context getWeakActivity(){
        if(mActivity.get()!=null){
            mActivity.get().getTheme().applyStyle(android.R.style.Theme_Dialog, true);
            return mActivity.get();
        } else return getAppContext();
    }

    public void onCreate() {
        super.onCreate();

        getTheme().applyStyle(android.R.style.Theme_Dialog, true);

        FirebaseCaller.initializationFirebase();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                mActivity= new WeakReference<>(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if(mActivity!=null)
                mActivity.clear();
            }
        });

        GlobalContext.application =this;
    }



    public static Context getAppContext() {
        return  new ContextThemeWrapper(GlobalContext.application,android.R.style.Theme_Dialog);
    }

}
