package veme.cario.com.foshop;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by bski on 11/5/14.
 */
public class FoShopApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this,
                getString(R.string.parse_app_id),
                getString(R.string.parse_app_key));

    }
}