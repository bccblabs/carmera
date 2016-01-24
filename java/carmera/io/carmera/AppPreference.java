package carmera.io.carmera;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by bski on 7/24/15.
 */
public class AppPreference extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
