package com.Teachers.HaziraKhataByGk.HelperClassess;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;

import java.lang.ref.WeakReference;

public class GlobalContext extends MultiDexApplication {

    private static Application application;
    public static WeakReference<Activity> mActivity = null;

    public static Activity getWeakActivity(){
        return mActivity.get();
    }
    public void onCreate() {
        super.onCreate();
        GlobalContext.application =this;

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                mActivity=new WeakReference<Activity>(activity);
            }

            @Override
            public void onActivityResumed(Activity activity) {

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
                mActivity.clear();
            }
        });

    }



    public static Context getAppContext() {
        return GlobalContext.application.getApplicationContext();
    }

}
