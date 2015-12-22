package carmera.io.carmera;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bowyer.app.fabtransitionlayout.FooterLayout;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.yalantis.guillotine.animation.GuillotineAnimation;
import org.parceler.Parcels;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carmera.io.carmera.fragments.main_fragments.CaptureFragment;
import carmera.io.carmera.fragments.main_fragments.ListingsFragment;
import carmera.io.carmera.fragments.main_fragments.SettingsFragment;
import carmera.io.carmera.fragments.search_fragments.FilterFragment;
import carmera.io.carmera.fragments.search_fragments.SearchContainer;
import carmera.io.carmera.fragments.search_fragments.SortFragment;
import carmera.io.carmera.listeners.OnEditBodyTypes;
import carmera.io.carmera.listeners.OnEditCompressors;
import carmera.io.carmera.listeners.OnEditDriveTrain;
import carmera.io.carmera.listeners.OnEditHp;
import carmera.io.carmera.listeners.OnEditMakes;
import carmera.io.carmera.listeners.OnEditMpg;
import carmera.io.carmera.listeners.OnEditTags;
import carmera.io.carmera.listeners.OnEditTorque;
import carmera.io.carmera.listeners.OnResearchListener;
import carmera.io.carmera.models.Listings;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.models.queries.ImageQuery;
import carmera.io.carmera.requests.ClassifyRequest;
import carmera.io.carmera.requests.ListingsRequest;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 6/3/15.
 */
public class Base extends AppCompatActivity implements CaptureFragment.OnCameraResultListener,
                                                        OnResearchListener,
                                                        ObservableScrollViewCallbacks,
                                                        OnEditBodyTypes,
                                                        OnEditDriveTrain,
                                                        OnEditHp,
                                                        OnEditMakes,
                                                        OnEditMpg,
                                                        OnEditTags,
                                                        OnEditTorque,
                                                        OnEditCompressors {

    private final String TAG = getClass().getCanonicalName();
    private ListingsQuery listingsQuery = new ListingsQuery();

    @Bind (R.id.ic_filter) TextView ic_filter;
    @Bind (R.id.ic_clear) View ic_clear;

    @Bind (R.id.sort_filter_search) FloatingActionButton sort_filter_search;

    @Bind (R.id.fab_toolbar) FooterLayout fab_toolbar;

    @Bind(R.id.toolbar) Toolbar toolbar;

    @Bind(R.id.root) FrameLayout root;

    @Bind(R.id.content_hamburger) View contentHamburger;

    @Bind (R.id.loading) View loading;

    @Bind (R.id.text_search_view) MaterialSearchView searchView;

    private ListingsFragment listingsFragment;

    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);

    private String server_address;

    private SharedPreferences sharedPreferences;

    private final class ListingsRequestListener implements RequestListener<Listings> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            listingsQuery = new ListingsQuery();
            Toast.makeText(Base.this, "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onRequestSuccess (Listings result) {
            try {
                Base.this.fab_toolbar.setVisibility(View.GONE);
                listingsQuery = new ListingsQuery();
                listingsFragment = ListingsFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.EXTRA_LISTINGS_DATA, Parcels.wrap(Listings.class, result));
                listingsFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, listingsFragment)
                        .addToBackStack("listings_fragment")
                        .commitAllowingStateLoss();

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private final class NoHistListingsRequestListener implements RequestListener<Listings> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            listingsQuery = new ListingsQuery();
            Toast.makeText(Base.this, "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onRequestSuccess (Listings result) {
            try {
                listingsQuery = new ListingsQuery();
                Base.this.fab_toolbar.setVisibility(View.GONE);
                listingsFragment = ListingsFragment.newInstance();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View search, carmera, saved, settings;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
        }

        setContentView(R.layout.base);
        ButterKnife.bind(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        server_address = sharedPreferences.getString("pref_key_server_addr", Constants.ServerAddr).trim();

        fab_toolbar.setFab(sort_filter_search);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        ListingsQuery listingsQuery = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_LISTING_QUERY));
        if (listingsQuery != null) {
            loading.setVisibility(View.VISIBLE);
            spiceManager.execute(new ListingsRequest(listingsQuery, server_address), new NoHistListingsRequestListener());
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, SearchContainer.newInstance())
                    .commit();

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
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, CaptureFragment.newInstance())
                        .addToBackStack("capture_fragment")
                        .commit();
                guillotineAnimation.close();
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
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.carmera_capture:
//                try {
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.content_frame, CaptureFragment.newInstance())
//                            .addToBackStack("capture_fragment")
//                            .commit();
//                } catch (Exception e) {
//                    Log.i (TAG, e.getMessage());
//                }
//
//        }
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
            spiceManager.cancelAllRequests();
            spiceManager.shouldStop();
        }
        super.onStop();
    }

    @Override
    public void onDestroy () {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void OnCameraResult (ImageQuery imageQuery) {
        spiceManager.execute(new ClassifyRequest(imageQuery, server_address), new ListingsRequestListener());
    }


    @Override
    public void OnEditBodyTypeCallback (String bt) {
        setFabVisibility();
        this.listingsQuery.car.bodyTypes.add(bt);
    }

    @Override
    public void OnEditBodyTypesCallback (String[] bt) {
        setFabVisibility();
        this.listingsQuery.car.bodyTypes.addAll(Arrays.asList(bt));
    }

    @Override
    public void OnEditDriveTrainCallback (String val) {
        setFabVisibility();
        this.listingsQuery.car.drivenWheels.add(val);
    }

    @Override
    public void OnEditMakesCallback (String[] val) {
        setFabVisibility();
        this.listingsQuery.car.makes.addAll(Arrays.asList(val));
    }

    @Override
    public void OnEditMakeCallback (String val) {
        setFabVisibility();
        this.listingsQuery.car.makes.add(val);
    }

    @Override
    public void OnEditMpgCallback (Integer val) {
        setFabVisibility();
        this.listingsQuery.car.minMpg = val;
    }

    @Override
    public void OnEditHpCallback (Integer val) {
        setFabVisibility();
        this.listingsQuery.car.minHp = val;
    }

    @Override
    public void OnEditTorqueCallback (Integer val) {
        setFabVisibility();
        this.listingsQuery.car.minTq = val;
    }

    @Override
    public void OnEditTagCallback (String val) {
        setFabVisibility();
        this.listingsQuery.car.tags.add(val);

    }


    @Override
    public void OnEditTagsCallback (String[] val) {
        setFabVisibility();
        this.listingsQuery.car.tags.addAll(Arrays.asList(val));
    }

    @Override
    public void OnEditCompressorsCallback (String[] val) {
        setFabVisibility();
        this.listingsQuery.car.compressors.addAll(Arrays.asList(val));
    }

    @Override
    public void OnEditCompressorCallback (String val) {
        setFabVisibility();
        this.listingsQuery.car.compressors.add(val);
    }

    private void setFabVisibility () {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        if (scrollState == ScrollState.UP) {
            fab_toolbar.slideOutFab();
        } else if (scrollState == ScrollState.DOWN) {
            fab_toolbar.slideInFab();
        }
    }

    @Override
    public void onScrollChanged(int i, boolean b, boolean b1) {}

    @Override
    public void onDownMotionEvent() {}
    @OnClick(R.id.sort_filter_search) void show() {
        fab_toolbar.expandFab();
    }


    @Override
    public void onResearchCallback (ListingsQuery listingsQuery) {
        this.listingsQuery = listingsQuery;
    }

    @OnClick (R.id.ic_search)
    public void search () {
        Intent i = new Intent(this, MakesSearchActivity.class);
        i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
        startActivityForResult(i, 1);
    }

    @OnClick(R.id.ic_filter)
    public void onFilter () {
        FilterFragment filterFragment = FilterFragment.newInstance();
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
        filterFragment.setArguments(args);
        filterFragment.show(getSupportFragmentManager(), "filter_dialog");
    }

    @OnClick(R.id.ic_clear)
    public void onSort () {
        this.listingsQuery = new ListingsQuery();
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .content("Search Cleared!")
                .positiveText("Got It!")
                .show();
    }


}

