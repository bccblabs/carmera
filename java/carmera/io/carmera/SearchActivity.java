package carmera.io.carmera;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import org.parceler.Parcels;
import java.util.Arrays;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carmera.io.carmera.adapters.DealersAdapter;
import carmera.io.carmera.fragments.main_fragments.CaptureFragment;
import carmera.io.carmera.fragments.main_fragments.ListingsFragment;
import carmera.io.carmera.fragments.search_fragments.CarFilterFragment;
import carmera.io.carmera.fragments.search_fragments.SearchContainerFragment;
import carmera.io.carmera.listeners.OnEditBodyTypes;
import carmera.io.carmera.listeners.OnEditCompressors;
import carmera.io.carmera.listeners.OnEditCylinders;
import carmera.io.carmera.listeners.OnEditDriveTrain;
import carmera.io.carmera.listeners.OnEditHp;
import carmera.io.carmera.listeners.OnEditMakes;
import carmera.io.carmera.listeners.OnEditMpg;
import carmera.io.carmera.listeners.OnEditSort;
import carmera.io.carmera.listeners.OnEditTags;
import carmera.io.carmera.listeners.OnEditTorque;
import carmera.io.carmera.listeners.OnEditTransmission;
import carmera.io.carmera.listeners.OnResearchListener;
import carmera.io.carmera.listeners.OnSearchFragmentVisible;
import carmera.io.carmera.models.Dealers;
import carmera.io.carmera.models.Listings;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.models.queries.ImageQuery;
import carmera.io.carmera.requests.ClassifyRequest;
import carmera.io.carmera.requests.ListingsRequest;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 6/3/15.
 */
public class SearchActivity extends AppCompatActivity implements
                                                        CaptureFragment.OnCameraResultListener,
                                                        OnSearchFragmentVisible,
                                                        OnResearchListener,
                                                        OnEditBodyTypes,
                                                        OnEditCompressors,
                                                        OnEditCylinders,
                                                        OnEditDriveTrain,
                                                        OnEditHp,
                                                        OnEditMakes,
                                                        OnEditMpg,
                                                        OnEditSort,
                                                        OnEditTags,
                                                        OnEditTorque,
                                                        OnEditTransmission {

    private final String TAG = getClass().getCanonicalName();
    private ListingsQuery listingsQuery = new ListingsQuery();

    @Bind (R.id.fab_toolbar) View fab_toolbar;
    @Bind (R.id.toolbar) Toolbar toolbar;
    @Bind (R.id.loading) View loading;
    @Bind (R.id.search_title) TextView search_title;

    private ListingsFragment listingsFragment;

    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);

    private String server_address;

    private SharedPreferences sharedPreferences;

    private final class ListingsRequestListener implements RequestListener<Listings> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            search_title.setText("0 listings");
            listingsQuery = new ListingsQuery();
            SearchActivity.this.loading.setVisibility(View.GONE);
        }
        @Override
        public void onRequestSuccess (Listings result) {
            try {
                SearchActivity.this.fab_toolbar.setVisibility(View.GONE);
                SearchActivity.this.loading.setVisibility(View.GONE);
                search_title.setText(result.getListings().size() + " listings");
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
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        server_address = sharedPreferences.getString("pref_key_server_addr", Constants.ServerAddr).trim();

        setSupportActionBar(toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        ListingsQuery listingsQuery = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_LISTING_QUERY));
        if (listingsQuery != null) {
            String model_name = getIntent().getExtras().getString(Constants.EXTRA_MODEL_NAME);
            if (model_name == null)
                search_title.setText("Search");
            else
                search_title.setText(model_name);
            fab_toolbar.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
            spiceManager.execute(new ListingsRequest(listingsQuery, server_address), new ListingsRequestListener());
        } else {
            search_title.setText(getResources().getString(R.string.search));
            this.listingsQuery = new ListingsQuery();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, SearchContainerFragment.newInstance())
                    .commit();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        spiceManager.start(SearchActivity.this);
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
    public void addTransmissionType (String transmissionType) {
        this.listingsQuery.car.transmissionTypes.add(transmissionType);
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
    public void onSetMinCylinders (Integer minCylinders) {
        this.listingsQuery.car.minCylinders = minCylinders;
    }

    @Override
    public void onEditSortString (String sort) {
        this.listingsQuery.sortBy = sort;
    }

    @Override
    public void onResearchCallback (ListingsQuery listingsQuery) {
        this.listingsQuery = listingsQuery;
        Intent i = new Intent(this, MakesSearchActivity.class);
        i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, this.listingsQuery));
        startActivityForResult(i, 1);
    }

    @OnClick(R.id.ic_filter)
    public void onFilter () {
        CarFilterFragment filterFragment = CarFilterFragment.newInstance();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorites:
                Intent i = new Intent(SearchActivity.this, FavoritesActivity.class);
                startActivity(i);
                break;
            case R.id.action_settings:
                Intent pref = new Intent(SearchActivity.this, AppPreference.class);
                startActivity(pref);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

