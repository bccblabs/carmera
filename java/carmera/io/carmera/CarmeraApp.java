package carmera.io.carmera;

import android.app.Application;
import android.graphics.Typeface;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseObject;
import com.parse.ParseUser;

import carmera.io.carmera.models.ParseSavedListings;
import carmera.io.carmera.models.ParseSavedModels;
import carmera.io.carmera.models.ParseSavedSearch;

public class CarmeraApp extends Application {
    private static final String CANARO_EXTRA_BOLD_PATH = "fonts/canaro_extra_bold.otf";
    public static Typeface canaroExtraBold;

    @Override
    public void onCreate() {
        super.onCreate();
        ParseCrashReporting.enable(this);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);
        ParseObject.registerSubclass(ParseSavedSearch.class);
        ParseObject.registerSubclass(ParseSavedListings.class);
        ParseObject.registerSubclass(ParseSavedModels.class);
        canaroExtraBold = Typeface.createFromAsset(getAssets(), CANARO_EXTRA_BOLD_PATH);
    }
}
