package veme.cario.com.CARmera;

import com.facebook.AppEventsLogger;

/**
 * Created by bski on 11/23/14.
 */
public class NotificationsActivity extends BaseActivity {

    @Override
    public void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

}
