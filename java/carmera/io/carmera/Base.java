package carmera.io.carmera;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.commonsware.cwac.cam2.CameraActivity;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.parse.ParseUser;
import com.yalantis.guillotine.animation.GuillotineAnimation;
import org.parceler.Parcels;
import org.parceler.apache.commons.lang.RandomStringUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.fragments.main_fragments.ListingsFragment;
import carmera.io.carmera.fragments.main_fragments.LoadingFragment;
import carmera.io.carmera.fragments.main_fragments.SettingsFragment;
import carmera.io.carmera.fragments.search_fragments.SearchContainer;
import carmera.io.carmera.models.Listings;
import carmera.io.carmera.models.queries.ImageQuery;
import carmera.io.carmera.utils.Constants;
import carmera.io.carmera.utils.Util;

/**
 * Created by bski on 6/3/15.
 */
public class Base extends AppCompatActivity implements SearchContainer.OnSearchVehiclesListener {

    private final String TAG = getClass().getCanonicalName();

    @Bind(R.id.toolbar) Toolbar toolbar;

    @Bind(R.id.root) FrameLayout root;

    @Bind(R.id.content_hamburger) View contentHamburger;

    @Bind (R.id.loading) View loading;

    private Socket socket;

    private String socket_addr;

    private File root_dir;

    private ListingsFragment listingsFragment;

    @Override
    public void OnSearchListings (Parcelable query) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_LISTING_QUERY, query);
        listingsFragment = ListingsFragment.newInstance();
        listingsFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, listingsFragment)
                .commitAllowingStateLoss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View search, carmera, saved, settings;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
        }

        root_dir = new File(Environment.getExternalStorageDirectory(), "MaterialCamera");
        root_dir.mkdirs();

        socket_addr = sharedPreferences.getString("pref_key_server_addr", Constants.ServerAddr).trim();
        socket = Util.getUploadSocket(socket_addr).connect();
        Toast.makeText(this, "socket connected", Toast.LENGTH_LONG).show();
        setContentView(R.layout.base);
        ButterKnife.bind(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, SearchContainer.newInstance())
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
        final GuillotineAnimation guillotineAnimation = new GuillotineAnimation.GuillotineBuilder(guillotineMenu,
                guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(Constants.GUILLOTINE_ANIMATION_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .build();
        guillotineAnimation.close();

        search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, SearchContainer.newInstance())
                        .commitAllowingStateLoss();
                guillotineAnimation.close();
                return false;
            }
        });

        carmera.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
            try {

                guillotineAnimation.close();
                String file_name = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(5), ".jpg");
                Intent i= new CameraActivity.IntentBuilder(Base.this)
                        .skipConfirm()
                        .facing(CameraActivity.Facing.BACK)
                        .to (new File (root_dir, file_name))
                        .build();
                startActivityForResult(i, Constants.IMAGE_RESULT);
            } catch (Exception e) {
                Log.i (TAG, e.getMessage());
            }
                return false;
            }
        });

        settings.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                guillotineAnimation.close();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new SettingsFragment())
                        .commitAllowingStateLoss();
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
                try {
                    String file_name = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(5), "jpg");
                    Intent i=new CameraActivity.IntentBuilder(Base.this)
                            .skipConfirm()
                            .facing(CameraActivity.Facing.BACK)
                            .to(new File(root_dir, file_name))
                            .updateMediaStore()
                            .build();
                    startActivityForResult(i, Constants.IMAGE_RESULT);
                } catch (Exception e) {
                    Log.i (TAG, e.getMessage());
                }

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroy () {
        super.onDestroy();
        Log.i(TAG, "[socket] disconnects");
        ButterKnife.unbind(this);
        Util.disconnectSocket();
    }

    @Override
    public void onActivityResult (final int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {
            case Constants.IMAGE_RESULT:
//                getFragmentManager().beginTransaction().replace(R.id.content_frame, LoadingFragment.newInstance()).commitAllowingStateLoss();
                try {
                    System.gc();
                    if (resultCode == RESULT_OK) {
                        new Runnable() {
                            @Override
                            public void run () {
                                try {
                                    InputStream is = getContentResolver().openInputStream(data.getData());
                                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                                    is.close();
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, Constants.BITMAP_QUALITY, baos); //bm is the bitmap object
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddhh:mm:ss.mmm", Locale.US);
                                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                                    Util.getUploadSocket(socket_addr).emit("clz_data", new Gson().toJson(
                                            new ImageQuery(ParseUser.getCurrentUser().getUsername(),
                                                    Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT),
                                                    sdf.format(new Date()))
                                    ));
                                    baos = null;
                                    Util.getUploadSocket(socket_addr).on("listings", new Emitter.Listener() {
                                        @Override
                                        public void call (final Object... args) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        listingsFragment = ListingsFragment.newInstance();
                                                        Bundle bundle = new Bundle();
                                                        bundle.putParcelable(Constants.EXTRA_LISTINGS_DATA, Parcels.wrap(Listings.class, new Gson().fromJson((String) args[0], Listings.class)));
                                                        listingsFragment.setArguments(bundle);
                                                        getSupportFragmentManager().beginTransaction()
                                                                .replace(R.id.content_frame, listingsFragment)
                                                                .commitAllowingStateLoss();

                                                    } catch (Exception e) {
                                                        Log.e(TAG, e.getMessage());
                                                    }
                                                }
                                            });
                                        }
                                    });
                                } catch (Exception e) {
                                    Log.e (TAG, e.getMessage());
                                }
                            }
                        }.run();
                    } else {
                        Toast.makeText(this, "Something happened", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    Log.e (TAG, e.getMessage());
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            // Sample was denied WRITE_EXTERNAL_STORAGE permission
        }
    }
}

