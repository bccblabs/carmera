package veme.cario.com.CARmera;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * Created by bski on 11/23/14.
 */
public class SettingsFragment extends PreferenceFragment
                                implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String KEY_PREF_USER_NAME = "pref_key_user_name";
    public static final String KEY_PREF_USER_PHONE = "pref_key_user_phone";
    public static final String KEY_PREF_USER_EMAIL = "pref_key_user_email";
    public static final String KEY_PREF_USER_ALLOW_CONTACT = "pref_key_user_allow_contact";
    public static final String KEY_PREF_SEARCH_RADIUS = "pref_key_search_radius";

    EditTextPreference name_pref;
    EditTextPreference phone_pref;
    EditTextPreference email_pref;
    ListPreference dist_pref;

    @Override
    public void onCreate (Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        addPreferencesFromResource(R.xml.preferences);
        name_pref = (EditTextPreference) findPreference(KEY_PREF_USER_NAME);
        phone_pref = (EditTextPreference) findPreference(KEY_PREF_USER_PHONE);
        email_pref = (EditTextPreference) findPreference(KEY_PREF_USER_EMAIL);
        dist_pref = (ListPreference) findPreference(KEY_PREF_SEARCH_RADIUS);
        name_pref.setSummary(name_pref.getText());
        phone_pref.setSummary(phone_pref.getText());
        email_pref.setSummary(email_pref.getText());
        dist_pref.setSummary(dist_pref.getEntry() + " miles");
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference pref = findPreference(key);
        if (pref instanceof EditTextPreference) {
            EditTextPreference editPref = (EditTextPreference) pref;
            editPref.setSummary(editPref.getText());
        }

        if (pref instanceof ListPreference) {
            ListPreference listPref = (ListPreference) pref;
            pref.setSummary(listPref.getEntry() + " miles");
        }
    }

}
