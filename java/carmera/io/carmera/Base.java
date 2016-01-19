package carmera.io.carmera;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.ButtonFlat;
import com.google.gson.Gson;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
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
import carmera.io.carmera.listeners.OnEditBodyTypes;
import carmera.io.carmera.listeners.OnEditCompressors;
import carmera.io.carmera.listeners.OnEditDriveTrain;
import carmera.io.carmera.listeners.OnEditHp;
import carmera.io.carmera.listeners.OnEditMakes;
import carmera.io.carmera.listeners.OnEditMpg;
import carmera.io.carmera.listeners.OnEditTags;
import carmera.io.carmera.listeners.OnEditTorque;
import carmera.io.carmera.listeners.OnResearchListener;
import carmera.io.carmera.listeners.OnSearchFragmentVisible;
import carmera.io.carmera.models.Listings;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.models.queries.ImageQuery;
import carmera.io.carmera.requests.ClassifyRequest;
import carmera.io.carmera.requests.ListingsRequest;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 6/3/15.
 */
public class Base extends ActionBarActivity implements CaptureFragment.OnCameraResultListener,
                                                        OnSearchFragmentVisible,
                                                        OnResearchListener,
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

    @Bind (R.id.ic_filter) ButtonFlat ic_filter;
    @Bind (R.id.ic_search) ButtonFlat ic_search;

    @Bind (R.id.fab_toolbar) View fab_toolbar;

    @Bind(R.id.toolbar) Toolbar toolbar;

    @Bind (R.id.loading) View loading;

    private ListingsFragment listingsFragment;

    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);

    private String server_address;

    private SharedPreferences sharedPreferences;


    private Drawer result = null;


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
        setContentView(R.layout.base);
        ButterKnife.bind(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        server_address = sharedPreferences.getString("pref_key_server_addr", Constants.ServerAddr).trim();

        setSupportActionBar(toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withTranslucentStatusBar(false)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.search).withIcon(getResources().getDrawable(R.drawable.ic_search_black_24dp)).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.favorites).withIcon(getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp)).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.settings).withIcon(getResources().getDrawable(R.drawable.ic_settings_black_24dp)).withIdentifier(3)
                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            if (drawerItem.getIdentifier() == 1) {
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.content_frame, SearchContainer.newInstance())
                                        .commitAllowingStateLoss();
                            } else if (drawerItem.getIdentifier() == 3) {
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.content_frame, new SettingsFragment())
                                        .commitAllowingStateLoss();
                            } else if (drawerItem.getIdentifier() == 2) {

                            }
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();


        ListingsQuery listingsQuery = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_LISTING_QUERY));
        if (listingsQuery != null) {
            loading.setVisibility(View.VISIBLE);
            spiceManager.execute(new ListingsRequest(listingsQuery, server_address), new NoHistListingsRequestListener());
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, SearchContainer.newInstance())
                    .commit();
        }

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
        this.listingsQuery.car.bodyTypes.add(bt);
    }

    @Override
    public void OnEditBodyTypesCallback (String[] bt) {
        this.listingsQuery.car.bodyTypes.addAll(Arrays.asList(bt));
    }

    @Override
    public void OnEditDriveTrainCallback (String val) {
        this.listingsQuery.car.drivenWheels.add(val);
    }

    @Override
    public void OnEditMakesCallback (String[] val) {
        this.listingsQuery.car.makes.addAll(Arrays.asList(val));
        Log.i(TAG, "On Edit Makes: " + new Gson().toJson(this.listingsQuery.car));
    }

    @Override
    public void OnEditMakeCallback (String val) {
        this.listingsQuery.car.makes.add(val);
    }

    @Override
    public void OnEditMpgCallback (Integer val) {
        this.listingsQuery.car.minMpg = val;
    }

    @Override
    public void OnEditHpCallback (Integer val) {
        this.listingsQuery.car.minHp = val;
    }

    @Override
    public void OnEditTorqueCallback (Integer val) {
        this.listingsQuery.car.minTq = val;
    }

    @Override
    public void OnEditTagCallback (String val) {
        this.listingsQuery.car.tags.add(val);

    }

    @Override
    public void OnEditTagsCallback (String[] val) {
        this.listingsQuery.car.tags.addAll(Arrays.asList(val));
    }

    @Override
    public void OnEditCompressorsCallback (String[] val) {
        this.listingsQuery.car.compressors.addAll(Arrays.asList(val));
    }

    @Override
    public void OnEditCompressorCallback (String val) {
        this.listingsQuery.car.compressors.add(val);
    }

    @Override
    public void onResearchCallback (ListingsQuery listingsQuery) {
        this.listingsQuery = listingsQuery;
        Log.i (TAG, "On Research Callback: " + new Gson().toJson(this.listingsQuery.car));
        Intent i = new Intent(this, MakesSearchActivity.class);
        i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, this.listingsQuery));
        startActivityForResult(i, 1);
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


    @OnClick (R.id.clear_btn)
    void onClear () {
        this.listingsQuery = new ListingsQuery();
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .content("Search Cleared!")
                .positiveText("Got It!")
                .show();
    }


    @Override
    public void SetFabInvisible () {
        fab_toolbar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void SetFabVisible () {
        fab_toolbar.setVisibility(View.VISIBLE);
    }
}

