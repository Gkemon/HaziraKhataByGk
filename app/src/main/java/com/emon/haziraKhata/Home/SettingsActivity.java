package com.emon.haziraKhata.Home;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

import com.emon.haziraKhata.HelperClasses.UtilsCommon;
import com.emon.haziraKhata.HelperClasses.ViewUtils.BaseActivity;
import com.emon.haziraKhata.R;
import com.emon.haziraKhata.routine.RoutineUtils;
import com.emon.haziraKhata.service.GenericEventShowingService;
import com.emon.haziraKhata.service.ServiceUtils;

public class SettingsActivity extends BaseActivity {

    public static final String ROUTINE_REMINDER_TIME_BEFORE="routineReminderTimeBefore";
    public static final String IS_NOTIFICATION_ENABLED="notificationPreference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

            setPreferencesFromResource(R.xml.root_preferences,rootKey);
            preRoutineRemindTimeSetup();
            routineNotificationSetup();

        }
        private void routineNotificationSetup(){
            SwitchPreferenceCompat spcNotification=
                    findPreference(SettingsActivity.IS_NOTIFICATION_ENABLED);

            if (spcNotification != null) {

                //1st step: check service is running or not.
                spcNotification.setChecked(ServiceUtils.
                        isServiceRunning(GenericEventShowingService.class,getContext()));

                //2nd step: Because notification is set true by default.
                if(UtilsCommon.isRoutineNotificationEnable(getContext())){
                    spcNotification.setChecked(true);
                    RoutineUtils.startEventShowingService(getContext(),
                            RoutineUtils.getTotalRoutineItems(getContext()));
                }

                spcNotification.setOnPreferenceChangeListener((preference, newValue) -> {

                    Intent serviceIntent = new Intent(preference.getContext(), GenericEventShowingService.class);

                    if((Boolean)newValue)
                    {
                        RoutineUtils.startEventShowingService(getContext(),
                                RoutineUtils.getTotalRoutineItems(getContext()));
                    }else {
                        preference.getContext().stopService(serviceIntent);
                    }
                    return true;
                });
            }

        }

        private void preRoutineRemindTimeSetup() {

            // Pre routine remind time.
       EditTextPreference preRoutineTime = findPreference(ROUTINE_REMINDER_TIME_BEFORE);
            if (preRoutineTime != null) {
                preRoutineTime.setSummaryProvider((Preference.SummaryProvider<EditTextPreference>) preference -> {
                    String text = preference.getText();
                    if (TextUtils.isEmpty(text)) {
                        return "এখনো আপনি সেট করেননি";
                    }
                    return text+ " মিনিট আগে আপনাকে এলার্ম দিয়ে জানানো হবে।";
                });

                preRoutineTime.setOnBindEditTextListener(
                        editText -> {
                            editText.setHint("মিনিট ইনপুট দিন");
                            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        });

            }
            // Pre routine remind time.

        }
    }


}