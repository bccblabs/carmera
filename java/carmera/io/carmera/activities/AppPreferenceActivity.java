package carmera.io.carmera.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import carmera.io.carmera.R;

/**
 * Created by bski on 7/24/15.
 */
public class AppPreferenceActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
