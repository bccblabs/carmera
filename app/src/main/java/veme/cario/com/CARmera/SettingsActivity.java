package veme.cario.com.CARmera;

import android.os.Bundle;

/**
 * Created by bski on 11/23/14.
 */
public class SettingsActivity extends BaseActivity {
    @Override
    public void onCreate (Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        getFragmentManager().beginTransaction()
                            .replace(android.R.id.content, new SettingsFragment())
                            .commit();
    }
}
