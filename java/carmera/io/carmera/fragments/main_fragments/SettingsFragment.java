package carmera.io.carmera.fragments.main_fragments;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import carmera.io.carmera.R;

/**
 * Created by bski on 7/24/15.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences (Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }

}
