package com.emon.haziraKhata.HelperClasses;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.multidex.MultiDexApplication;

import com.emon.haziraKhata.Firebase.FirebaseCaller;
import com.google.firebase.FirebaseApp;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.lang.ref.WeakReference;

public class GlobalContext extends MultiDexApplication {

    public static WeakReference<Activity> mActivity = null;
    private static Application application;

    public static Context getWeakActivity() {
        if (mActivity.get() != null) {
            mActivity.get().getTheme().applyStyle(android.R.style.Theme_Dialog, true);
            return mActivity.get();
        } else return getAppContext();
    }

    public static Context getAppContext() {
        return new ContextThemeWrapper(GlobalContext.application, android.R.style.Theme_Dialog);
    }

    public void onCreate() {
        super.onCreate();

        getTheme().applyStyle(android.R.style.Theme_Dialog, true);

        FirebaseCaller.initializationFirebase();

        //TODO: Maybe not post the handled crash by reportException() if the below line is not mentioned.
        FirebaseApp.initializeApp(this);
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                mActivity = new WeakReference<>(activity);
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
                if (mActivity != null)
                    mActivity.clear();
            }
        });

        GlobalContext.application = this;
    }

}
