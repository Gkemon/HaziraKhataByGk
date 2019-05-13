package com.Teachers.HaziraKhataByGk.HelperClassess;

import android.app.Application;
import android.content.Context;

public class GlobalContext extends Application {

    private static Application application;

    public void onCreate() {
        super.onCreate();
        GlobalContext.application =this;
    }

    public static Application getApplication(){
        return application;
    }
    public static Context getAppContext() {
        return GlobalContext.application.getApplicationContext();
    }
}
