package veme.cario.com.CARmera;

import android.os.Bundle;

import veme.cario.com.CARmera.fragment.ActivityFragment.SettingsFragment;

/**
 * Created by bski on 11/23/14.
 */
public class SettingsActivity extends BaseActivity {
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawer_listview.setItemChecked(drawer_pos, true);
        setTitle("Settings");


        getFragmentManager().beginTransaction()
                            .replace(android.R.id.content, new SettingsFragment())
                            .commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        // AppEventsLogger.deactivateApp(this);

    }

}
