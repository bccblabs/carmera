package carmera.io.carmera;
import android.Manifest;
import android.content.Intent;
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
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.parse.ParseUser;
import com.yalantis.guillotine.animation.GuillotineAnimation;
import org.parceler.Parcels;
import org.parceler.apache.commons.lang.RandomStringUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.fragments.main_fragments.ListingsFragment;
import carmera.io.carmera.fragments.main_fragments.SettingsFragment;
import carmera.io.carmera.fragments.search_fragments.SearchContainer;
import carmera.io.carmera.models.Listings;
import carmera.io.carmera.models.queries.ImageQuery;
import carmera.io.carmera.requests.ClassifyRequest;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 6/3/15.
 */
public class Base extends AppCompatActivity implements SearchContainer.OnSearchVehiclesListener {

    private final String TAG = getClass().getCanonicalName();

    @Bind(R.id.toolbar) Toolbar toolbar;

    @Bind(R.id.root) FrameLayout root;

    @Bind(R.id.content_hamburger) View contentHamburger;

    @Bind (R.id.loading) View loading;

    private File root_dir;

    private ListingsFragment listingsFragment;

    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);

    private String server_address;


    private final class ListingsRequestListener implements RequestListener<Listings> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            Toast.makeText(Base.this, "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onRequestSuccess (Listings result) {
            try {
                listingsFragment = new ListingsFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.EXTRA_LISTINGS_DATA, Parcels.wrap(Listings.class, result));
                listingsFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, listingsFragment)
                        .commitAllowingStateLoss();

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    @Override
    public void OnSearchListings (Parcelable query) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_LISTING_QUERY, query);
        listingsFragment = new ListingsFragment();
        listingsFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, listingsFragment)
                .commitAllowingStateLoss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View search, carmera, saved, settings;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
        }

        root_dir = new File(Environment.getExternalStorageDirectory(), "MaterialCamera");
        root_dir.mkdirs();

        setContentView(R.layout.base);
        ButterKnife.bind(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, SearchContainer.newInstance())
                .commit();

        server_address = PreferenceManager.getDefaultSharedPreferences(this).getString("pref_key_server_addr", Constants.ServerAddr).trim();

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
    public void onStart () {
        super.onStart();
        spiceManager.start(Base.this);
    }

    @Override
    public void onStop () {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }


    @Override
    public void onDestroy () {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onActivityResult (final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.IMAGE_RESULT:
                try {
                    System.gc();
                    if (resultCode == RESULT_OK) {
                        try {
                            InputStream is = getContentResolver().openInputStream(data.getData());
                            Bitmap bitmap = BitmapFactory.decodeStream(is);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, Constants.BITMAP_QUALITY, baos); //bm is the bitmap object
                            ImageQuery imageQuery = new ImageQuery(ParseUser.getCurrentUser().getUsername(),
                                            Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT));
                            spiceManager.execute(new ClassifyRequest(imageQuery, server_address), new ListingsRequestListener());
                            is.close();
                        } catch (Exception e) {
                            Log.e (TAG, e.getMessage());
                        }
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
        }
    }
}

