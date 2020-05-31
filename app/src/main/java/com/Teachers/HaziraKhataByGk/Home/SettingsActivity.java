package com.Teachers.HaziraKhataByGk.Home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.service.BaseForeGroundService;
import com.Teachers.HaziraKhataByGk.service.GenericEventShowingService;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
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
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            preRoutineRemindTimeSetup();
            routineNotificationSetup();

        }
        private void routineNotificationSetup(){
            SwitchPreferenceCompat spcNotification=
                    findPreference("notificationPreference");
            if(spcNotification!=null)
            spcNotification.setOnPreferenceChangeListener((preference, newValue) -> {

                Intent serviceIntent = new Intent(preference.getContext(), GenericEventShowingService.class);

                if((Boolean)newValue)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        preference.getContext().startForegroundService(serviceIntent);
                    }
                    else preference.getContext().startService(serviceIntent);
                }else {
                    preference.getContext().stopService(serviceIntent);
                }
                return true;
            });
        }

        private void preRoutineRemindTimeSetup() {

            // Pre routine remind time.
            EditTextPreference preRoutineTime = findPreference("routineReminderTimeBefore");
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