package veme.cario.com.CARmera;

import android.os.Bundle;

import com.facebook.AppEventsLogger;

/**
 * Created by bski on 11/23/14.
 */
public class NotificationsActivity extends BaseActivity {

    @Override
    public void onPause() {
        super.onPause();
        // AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void onCreate (Bundle savedBundleInstance) {
        getLayoutInflater().inflate(R.layout.activity_notifications, frame_layout);
        drawer_listview.setItemChecked(drawer_pos, true);
        setTitle("Notifications");

    }
}
