package veme.cario.com.CARmera;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by bski on 11/23/14.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate (Bundle savedBundleInstance) {
        addPreferencesFromResource(R.xml.preferences);
    }
}
