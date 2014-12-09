package veme.cario.com.CARmera;

import android.os.Bundle;

import com.facebook.AppEventsLogger;

import veme.cario.com.CARmera.fragment.SettingsFragment;

/**
 * Created by bski on 11/23/14.
 */
public class SettingsActivity extends BaseActivity {
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                            .replace(android.R.id.content, new SettingsFragment())
                            .commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

}
