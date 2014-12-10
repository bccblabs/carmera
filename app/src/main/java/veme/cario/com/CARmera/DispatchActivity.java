package veme.cario.com.CARmera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.AppEventsLogger;
import com.parse.ParseUser;

/**
 * Created by bski on 11/5/14.
 */
public class DispatchActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ParseUser.getCurrentUser() != null) {
            startActivity(new Intent(this, NearbyActivity.class));
        } else {
            startActivity(new Intent(this, WelcomeActivity.class));

        }
    }
    @Override
    public void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }
}