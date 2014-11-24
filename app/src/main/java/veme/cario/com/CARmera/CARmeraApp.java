package veme.cario.com.CARmera;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by bski on 11/5/14.
 */
public class CARmeraApp extends Application {
    public static final String edmunds_app_key = "d442cka8a6mvgfnjcdt5fbns";

    public static final String edmunds_app_secret = "tVsB2tChr7wXqk47ZZMQneKq";

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this,
                getString(R.string.parse_app_id),
                getString(R.string.parse_app_key));
    }
}