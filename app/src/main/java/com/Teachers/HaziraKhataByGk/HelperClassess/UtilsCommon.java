package com.Teachers.HaziraKhataByGk.HelperClassess;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;

/**
 * Created by uy on 6/18/2018.
 */

public class UtilsCommon {
    public static void HideNotifiationBar(Activity activity){
        //HIDING NOTIFICATION BAR
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void logObject(Object object){
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(object);
        Logger.json(gson.toJson(object));
    }

    public static void logString(String object){
        Logger.d("GK",object);

    }

    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}
