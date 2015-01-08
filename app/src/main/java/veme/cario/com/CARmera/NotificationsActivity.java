package veme.cario.com.CARmera;

import android.os.Bundle;

import com.facebook.AppEventsLogger;

public class NotificationsActivity extends BaseActivity {

    @Override
    public void onPause() {
        super.onPause();
        // AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void onCreate (Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        getLayoutInflater().inflate(R.layout.activity_notifications, frame_layout);
        drawer_listview.setItemChecked(drawer_pos, true);
        setTitle("Notifications");

    }
}
