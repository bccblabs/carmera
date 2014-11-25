package veme.cario.com.CARmera;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import veme.cario.com.CARmera.model.UserModels.BusinessRequest;
import veme.cario.com.CARmera.model.UserModels.Favorites;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.model.UserModels.UserInfo;

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

        ParseObject.registerSubclass(BusinessRequest.class);
        ParseObject.registerSubclass(TaggedVehicle.class);
        ParseObject.registerSubclass(UserInfo.class);

    }
}