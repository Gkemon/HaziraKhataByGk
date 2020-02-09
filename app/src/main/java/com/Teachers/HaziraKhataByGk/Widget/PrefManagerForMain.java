package com.Teachers.HaziraKhataByGk.Widget;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by uy on 11/30/2017.
 */

public class PrefManagerForMain {
    // Shared preferences file name
    private static final String PREF_NAME = "welcome_hazira_khata_main";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTime";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    // shared pref mode
    int PRIVATE_MODE = 0;

    public PrefManagerForMain(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }


}
