package carmera.io.carmera;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.parse.ParseUser;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.zip.Inflater;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.fragments.main_fragments.ListingsFragment;
import carmera.io.carmera.fragments.main_fragments.SettingsFragment;
import carmera.io.carmera.fragments.search_fragments.SearchContainer;
import carmera.io.carmera.fragments.main_fragments.CaptureFragment;
import carmera.io.carmera.models.Listings;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.models.queries.ApiQuery;
import carmera.io.carmera.models.queries.CarQuery;
import carmera.io.carmera.models.queries.ImageQuery;
import carmera.io.carmera.utils.Constants;
import carmera.io.carmera.utils.Util;

/**
 * Created by bski on 6/3/15.
 */
public class Base extends AppCompatActivity implements CaptureFragment.OnCameraResultListener,
                                                       SearchContainer.OnSearchVehiclesListener {

    private final String TAG = getClass().getCanonicalName();

    private static final long RIPPLE_DURATION = 250;

    private static GuillotineAnimation guillotineAnimation;

    @Bind(R.id.toolbar) Toolbar toolbar;

    @Bind(R.id.root) FrameLayout root;

    @Bind(R.id.content_hamburger) View contentHamburger;

    private View search, carmera, saved, settings;

    private Socket socket;

    private String socket_addr;

    private SharedPreferences sharedPreferences;


    @Override
    public void OnSearchListings (Parcelable query) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_LISTING_QUERY, query);
        ListingsFragment listingsFragment = new ListingsFragment();
        listingsFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, listingsFragment)
                .addToBackStack("LISTINGS")
                .commitAllowingStateLoss();
    }

    @Override
    public void OnCameraResult (Parcelable query) {
        ImageQuery imageQuery = Parcels.unwrap(query);
        Util.getUploadSocket(socket_addr).emit("clz_data", new Gson().toJson(imageQuery));
        Util.getUploadSocket(socket_addr).on("listings", OnListings);
        Base.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Base.this, "emiting data...", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        socket_addr = sharedPreferences.getString("pref_key_server_addr", Constants.ServerAddr).trim();
        socket = Util.getUploadSocket(socket_addr).connect();
        socket.on("register", OnRegister);
        Toast.makeText(this, "socket connected", Toast.LENGTH_LONG).show();
        setContentView(R.layout.base);
        SearchContainer searchFragment = SearchContainer.newInstance();
        ButterKnife.bind(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, searchFragment)
                .commit();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);
        search = guillotineMenu.findViewById(R.id.car_search);
        carmera = guillotineMenu.findViewById(R.id.carmera);
        saved = guillotineMenu.findViewById(R.id.saved_listings);
        settings = guillotineMenu.findViewById(R.id.settings);
        guillotineAnimation = new GuillotineAnimation.GuillotineBuilder(guillotineMenu,
                guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .build();
        guillotineAnimation.close();

        search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, SearchContainer.newInstance())
                        .commit();
                guillotineAnimation.close();
                return false;
            }
        });

        carmera.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, CaptureFragment.newInstance())
                        .commit();
                guillotineAnimation.close();
                return false;
            }
        });

        settings.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new SettingsFragment())
                        .commit();
                guillotineAnimation.close();
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.carmera_capture:
                CaptureFragment captureFragmentFragment = CaptureFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, captureFragmentFragment)
                        .addToBackStack("CAPTURE")
                        .commit();
        }
        return super.onOptionsItemSelected(item);
    }


    private Emitter.Listener OnRegister = new Emitter.Listener() {
        @Override
        public void call (final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String data = (String) args[0];
                    Log.i(TAG, "connection socket id: " + data);
                }
            });
        }
    };

    private Emitter.Listener OnListings = new Emitter.Listener() {
        @Override
        public void call (final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Gson gson = new Gson();
                    Log.i (TAG, gson.toJson (args[0]));
                    Listings listings = new Gson().fromJson((String)args[0], Listings.class);
                    Parcelable listings_pclb = Parcels.wrap(Listings.class, listings);
                    Bundle args = new Bundle();
                    args.putParcelable(Constants.EXTRA_LISTINGS_DATA, listings_pclb);
                    ListingsFragment listingsFragment = new ListingsFragment();
                    listingsFragment.setArguments(args);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, listingsFragment)
                            .addToBackStack("LISTINGS")
                            .commitAllowingStateLoss();
                        }
            });
        }
    };


    @Override
    public void onBackPressed() {
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
        Log.i(TAG, "[socket] disconnects");
        Util.disconnectSocket();
    }

}
