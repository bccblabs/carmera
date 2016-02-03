package carmera.io.carmera.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.parceler.Parcels;
import org.parceler.apache.commons.collections.functors.ConstantTransformer;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carmera.io.carmera.R;
import carmera.io.carmera.adapters.BetterRecyclerAdapter;
import carmera.io.carmera.adapters.DealersAdapter;
import carmera.io.carmera.adapters.ListingsAdapter;
import carmera.io.carmera.fragments.search_fragments.ListingsFilterFragment;
import carmera.io.carmera.listeners.OnResearchListener;
import carmera.io.carmera.models.Dealers;
import carmera.io.carmera.models.Listing;
import carmera.io.carmera.models.Listings;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.models.queries.ApiQuery;
import carmera.io.carmera.models.queries.ImageQuery;
import carmera.io.carmera.requests.DealershipsRequest;
import carmera.io.carmera.requests.FranchiseListings;
import carmera.io.carmera.requests.ListingsRequest;
import carmera.io.carmera.utils.Constants;
import carmera.io.carmera.utils.EndlessRecyclerOnScrollListener;
import carmera.io.carmera.utils.ScrollingLinearLayoutManager;

/**
 * Created by bski on 2/2/16.
 */
public class ListingsActivity extends AppCompatActivity implements OnResearchListener {

    private ListingsAdapter listingsAdapter;
    private DealersAdapter dealersAdapter;
    @Bind (R.id.toolbar) Toolbar toolbar;

    private String TAG = getClass().getCanonicalName();
    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);
    private ListingsQuery listingsQuery;
    private String server_address, zipcode, radius;

    private ScrollingLinearLayoutManager scrollingLinearLayoutManager;

    @Bind(R.id.loading_container) public View loading_container;
    @Bind (R.id.listings_recylcer) public RecyclerView listings_recycler;
    @Bind (R.id.dealers_recycler) public RecyclerView dealers_recycler;
    @Bind (R.id.dealer_layout) public View dealer_layout;
    @Bind (R.id.search_title)
    TextView search_title;

    @Bind (R.id.fab_toolbar) View fab_toolbar;
    @Bind (R.id.emptyview) View emptyView;

    @OnClick(R.id.ic_filter)
    public void onFilter () {
        ListingsFilterFragment filterFragment = ListingsFilterFragment.newInstance();
        Log.i(this.getClass().getCanonicalName(), new Gson().toJson(listingsQuery, ListingsQuery.class));
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
        filterFragment.setArguments(args);
        filterFragment.show(getSupportFragmentManager(), "listings_filter_dialog");
    }

    @Override
    public void onResearchCallback (ListingsQuery listingsQuery) {
        listingsAdapter.clear();
        listingsAdapter.notifyDataSetChanged();
        listings_recycler.setVisibility(View.INVISIBLE);
        loading_container.setVisibility(View.VISIBLE);
        if (listingsQuery.api.franchiseId == null)
            spiceManager.execute(new ListingsRequest(listingsQuery, server_address), new ListingsRequestListener());
        else
            spiceManager.execute(new FranchiseListings(listingsQuery, server_address), new ListingsRequestListener());
    }

    private final class DealershipsRequestListener implements RequestListener<Dealers> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            loading_container.setVisibility(View.INVISIBLE);
            emptyView.setVisibility(View.VISIBLE);
        }
        @Override
        public void onRequestSuccess (Dealers result) {
            dealersAdapter = new DealersAdapter();
            dealersAdapter.addAll(result.getDealers());
            dealersAdapter.notifyDataSetChanged();

            dealers_recycler.setAdapter(dealersAdapter);
            dealers_recycler.setLayoutManager(new LinearLayoutManager(ListingsActivity.this));
            dealers_recycler.setHasFixedSize(true);

            loading_container.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
            dealer_layout.setVisibility(View.VISIBLE);
        }

    }

    private final class ListingsRequestListener implements RequestListener<Listings> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            search_title.setText("0 listings");
            listings_recycler.setVisibility(View.GONE);
            fab_toolbar.setVisibility(View.GONE);
            spiceManager.execute(new DealershipsRequest(listingsQuery.car.makes.get(0),
                            zipcode,
                            radius),
                    new DealershipsRequestListener());
        }
        @Override
        public void onRequestSuccess (Listings result) {
            try {
                search_title.setText(result.getListings().size() + " listings");
                listingsQuery = result.getListingsQuery();
                if (result.listings == null ||  result.listings.size() < 1) {
                    loading_container.setVisibility(View.VISIBLE);
                    spiceManager.execute(new DealershipsRequest(listingsQuery.car.makes.get(0),
                                    zipcode,
                                    radius),
                            new DealershipsRequestListener());
                    listings_recycler.setVisibility(View.GONE);
                } else {
                    dealer_layout.setVisibility(View.GONE);
                    listings_recycler.setVisibility(View.VISIBLE);
                    listingsAdapter.addAll(result.getListings());
                    listingsAdapter.notifyDataSetChanged();
                    emptyView.setVisibility(View.GONE);
                    fab_toolbar.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }


    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listings);
        ButterKnife.bind(this);
        server_address = PreferenceManager.getDefaultSharedPreferences(ListingsActivity.this).getString("pref_key_server_addr", Constants.ServerAddr).trim();
        zipcode = PreferenceManager.getDefaultSharedPreferences(ListingsActivity.this).getString("pref_key_zipcode", Constants.ZIPCODE_DEFAULT);
        radius = PreferenceManager.getDefaultSharedPreferences(ListingsActivity.this).getString("pref_key_radius", Constants.RADIUS_DEFAULT);



        setSupportActionBar(toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        scrollingLinearLayoutManager = new ScrollingLinearLayoutManager(ListingsActivity.this, LinearLayoutManager.VERTICAL, false, 3000);
        listings_recycler.setLayoutManager(scrollingLinearLayoutManager);
        listingsAdapter = new ListingsAdapter();
        listingsAdapter.setOnItemClickListener(new BetterRecyclerAdapter.OnItemClickListener<Listing>() {
            @Override
            public void onItemClick(View v, Listing item, int position) {
                Intent i = new Intent(ListingsActivity.this, ListingDetailsActivity.class);
                Bundle args = new Bundle();
                Parcelable listing_data = Parcels.wrap(item);
                args.putParcelable(Constants.EXTRA_LISTING_DATA, listing_data);

                i.putExtras(args);
                startActivity(i);
            }
        });
        listings_recycler.setAdapter(listingsAdapter);
        listings_recycler.setHasFixedSize(false);
        listings_recycler.setOnScrollListener(new EndlessRecyclerOnScrollListener(scrollingLinearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
            }
        });

        Listings listings = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_LISTINGS_DATA));
        if (listings != null) {
            loading_container.setVisibility(View.INVISIBLE);
            listingsQuery = listings.getListingsQuery();
            listingsQuery.car.remaining_ids = new ArrayList<>();
            if (listings.listings == null ||  listings.listings.size() < 1) {
                listings_recycler.setVisibility(View.GONE);
                fab_toolbar.setVisibility(View.GONE);
                spiceManager.execute(new DealershipsRequest(listingsQuery.car.makes.get(0),
                                zipcode,
                                radius),
                        new DealershipsRequestListener());
            } else {
                listings_recycler.setVisibility(View.VISIBLE);
                listingsAdapter.addAll(listings.getListings());
                listingsAdapter.notifyDataSetChanged();
            }
        }
//        else {
//            listings_recycler.setVisibility(View.GONE);
//            fab_toolbar.setVisibility(View.GONE);
//            spiceManager.execute(new DealershipsRequest(listingsQuery.car.makes.get(0),
//                            zipcode,
//                            radius),
//                    new DealershipsRequestListener());
//
//        }
    }


    @Override
    public void onStart () {
        super.onStart();
        if (!spiceManager.isStarted())
            spiceManager.start(ListingsActivity.this);
        Parcelable query_data = getIntent().getParcelableExtra(Constants.EXTRA_LISTING_QUERY);
        if (query_data != null) {
            listingsQuery = Parcels.unwrap(query_data);
            spiceManager.execute(new ListingsRequest(listingsQuery, server_address), new ListingsRequestListener());
            return;
        }

        String franchiseId = getIntent().getStringExtra(Constants.EXTRA_FRANCHISEID);
        if (franchiseId != null) {
            listingsQuery = new ListingsQuery();
            listingsQuery.api = new ApiQuery();
            listingsQuery.api.franchiseId = franchiseId;
            listingsQuery.api.pagenum = Integer.parseInt(Constants.PAGENUM_DEFAULT);
            listingsQuery.api.pagesize = Constants.PAGESIZE_DEFAULT;
            spiceManager.execute(new FranchiseListings(listingsQuery, server_address), new ListingsRequestListener());
            return;
        }

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
        ButterKnife.unbind(this);
        if (spiceManager.isStarted()) {
            spiceManager.cancelAllRequests();
            spiceManager.shouldStop();
        }
        super.onDestroy();
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
                Intent i = new Intent(ListingsActivity.this, FavoritesActivity.class);
                startActivity(i);
                break;
            case R.id.action_settings:
                Intent pref = new Intent(ListingsActivity.this, AppPreferenceActivity.class);
                startActivity(pref);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
