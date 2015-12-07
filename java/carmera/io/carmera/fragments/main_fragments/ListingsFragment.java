package carmera.io.carmera.fragments.main_fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.parceler.Parcels;
import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carmera.io.carmera.ListingDetails;
import carmera.io.carmera.R;
import carmera.io.carmera.adapters.BetterRecyclerAdapter;
import carmera.io.carmera.adapters.ListingsAdapter;
import carmera.io.carmera.fragments.search_fragments.FilterFragment;
import carmera.io.carmera.fragments.search_fragments.SortFragment;
import carmera.io.carmera.listeners.OnResearchListener;
import carmera.io.carmera.models.Listing;
import carmera.io.carmera.models.Listings;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.models.queries.ApiQuery;
import carmera.io.carmera.requests.FranchiseListings;
import carmera.io.carmera.requests.ListingsRequest;
import carmera.io.carmera.utils.Constants;
import carmera.io.carmera.utils.EndlessRecyclerOnScrollListener;
import carmera.io.carmera.utils.ScrollingLinearLayoutManager;

/**
 * Created by bski on 7/13/15.
 */
public class ListingsFragment extends Fragment implements OnResearchListener {


    private ListingsAdapter listingsAdapter;
    private String TAG = getClass().getCanonicalName();
    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);
    private ListingsQuery listingsQuery;
    private String server_address;


    @Bind (R.id.filter_sort_btn) public View filter_sort_btn;

    @Bind(R.id.loading_container) public View loading_container;

    @Bind(R.id.listings_recylcer) public RecyclerView listings_recycler;

    @Bind(R.id.sort_btn) public FloatingActionButton sort_btn;

    @Bind(R.id.filter_btn) public FloatingActionButton filter_btn;

    @Bind(R.id.more_cars) public ButtonRectangle more_cars;
    @OnClick (R.id.more_cars)
    public void search_more_cars () {
        filter_sort_btn.setVisibility(View.INVISIBLE);
        listings_recycler.setVisibility(View.INVISIBLE);
        more_cars.setVisibility(View.INVISIBLE);
        loading_container.setVisibility(View.VISIBLE);
        spiceManager.execute(new ListingsRequest(listingsQuery, server_address), new ListingsRequestListener());
    }



    @OnClick(R.id.sort_btn)
    public void onSort () {
        SortFragment sortFragment = SortFragment.newInstance();
        Bundle args = new Bundle();
        if (listingsQuery.car != null) {
            listingsQuery.car.remaining_ids = new ArrayList<>();
            listingsQuery.car.tags = new ArrayList<>();
        }
        args.putParcelable(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
        sortFragment.setArguments(args);
        sortFragment.setTargetFragment(this, 1);
        sortFragment.show(getChildFragmentManager(), "sort_dialog");
    }

    @OnClick(R.id.filter_btn)
    public void onFilter () {
        FilterFragment filterFragment = new FilterFragment();
        Log.i(this.getClass().getCanonicalName(), new Gson().toJson(listingsQuery, ListingsQuery.class));
        Bundle args = new Bundle();
        if (listingsQuery.car != null) {
            listingsQuery.car.tags = new ArrayList<>();
            listingsQuery.car.remaining_ids = new ArrayList<>();
        }
        args.putParcelable(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
        filterFragment.setArguments(args);
        filterFragment.setTargetFragment(this, 0);
        filterFragment.show(getChildFragmentManager(), "filter_dialog");
    }




    @Override
    public void onResearchCallback (ListingsQuery listingsQuery) {
        listingsAdapter.clear();
        listingsAdapter.notifyDataSetChanged();
        filter_sort_btn.setVisibility(View.INVISIBLE);
        listings_recycler.setVisibility(View.INVISIBLE);
        more_cars.setVisibility(View.INVISIBLE);
        loading_container.setVisibility(View.VISIBLE);
        if (listingsQuery.car != null && listingsQuery.car.remaining_ids != null && listingsQuery.car.remaining_ids.size() > 0)
            listingsQuery.car.remaining_ids = new ArrayList<>();
        if (listingsQuery.api.franchiseId == null)
            spiceManager.execute(new ListingsRequest(listingsQuery, server_address), new ResearchListener());
        else
            spiceManager.execute(new FranchiseListings(listingsQuery, server_address), new ListingsRequestListener());
    }

    private final class ListingsRequestListener implements RequestListener<Listings> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onRequestSuccess (Listings result) {
            try {
                if (ListingsFragment.this.isAdded()) {
                    loading_container.setVisibility(View.INVISIBLE);
                    filter_sort_btn.setVisibility(View.VISIBLE);
                    listings_recycler.setVisibility(View.VISIBLE);
                    listings_recycler.setOnScrollListener(new EndlessRecyclerOnScrollListener(new ScrollingLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false, 3000)) {
                        @Override
                        public void onLoadMore(int current_page) {
                            if (listingsQuery.car != null && listingsQuery.car.remaining_ids != null && listingsQuery.car.remaining_ids.size() > 0)
                                more_cars.setVisibility(View.VISIBLE);
                            else
                                more_cars.setVisibility(View.INVISIBLE);
                        }
                    });

                    listingsQuery = result.getListingsQuery();
                    if (result.listings == null ||  result.listings.size() < 1)
                        return;
                    listingsAdapter.addAll(result.getListings());
                    listingsAdapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            Toast.makeText(getActivity(), "Listings Adapter length: " + listingsAdapter.getItemCount(), Toast.LENGTH_SHORT).show();
        }
    }

    private final class ResearchListener implements RequestListener<Listings> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onRequestSuccess (Listings result) {
            try {
                if (ListingsFragment.this.isAdded()) {
                    loading_container.setVisibility(View.INVISIBLE);
                    filter_sort_btn.setVisibility(View.VISIBLE);
                    listings_recycler.setVisibility(View.VISIBLE);
                    listings_recycler.setOnScrollListener(new EndlessRecyclerOnScrollListener(new ScrollingLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false, 3000)) {
                        @Override
                        public void onLoadMore(int current_page) {
                            if (listingsQuery.car != null && listingsQuery.car.remaining_ids != null && listingsQuery.car.remaining_ids.size() > 0)
                                more_cars.setVisibility(View.VISIBLE);
                            else
                                more_cars.setVisibility(View.INVISIBLE);

                        }
                    });
                    listingsQuery = result.getListingsQuery();
                    if (result.listings == null ||  result.listings.size() < 1)
                        return;
                    listingsAdapter.addAll(result.getListings());
                    listingsAdapter.notifyDataSetChanged();
                    listings_recycler.smoothScrollToPosition(0);
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

//    public static ListingsFragment newInstance () {
//        return new ListingsFragment();
//    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        server_address = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("pref_key_server_addr", Constants.ServerAddr).trim();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.listings, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated (View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        ScrollingLinearLayoutManager scrollingLinearLayoutManager = new ScrollingLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false, 3000);
        listings_recycler.setLayoutManager(scrollingLinearLayoutManager);
        listingsAdapter = new ListingsAdapter();
        listingsAdapter.setOnItemClickListener(new BetterRecyclerAdapter.OnItemClickListener<Listing>() {
            @Override
            public void onItemClick(View v, Listing item, int position) {
                Intent i = new Intent(getActivity(), ListingDetails.class);
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
                if (listingsQuery.car != null && listingsQuery.car.remaining_ids != null && listingsQuery.car.remaining_ids.size() > 0)
                    more_cars.setVisibility(View.VISIBLE);
                else
                    more_cars.setVisibility(View.INVISIBLE);

            }
        });
        sort_btn.setIcon(R.drawable.ic_sort_white_24dp);
        filter_btn.setIcon(R.drawable.ic_filter_list_white_24dp);

        Listings listings = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_LISTINGS_DATA));
        if (listings != null) {
            Toast.makeText(getActivity(), "listings parcelable received", Toast.LENGTH_SHORT).show();
            filter_sort_btn.setVisibility(View.VISIBLE);
            loading_container.setVisibility(View.INVISIBLE);
            listings_recycler.setVisibility(View.VISIBLE);
            listingsQuery = listings.getListingsQuery();
            listingsQuery.car.remaining_ids = new ArrayList<>();

            if (listings.listings == null ||  listings.listings.size() < 1)
                return;
            listingsAdapter.addAll(listings.getListings());
            listingsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStart () {
        super.onStart();
        spiceManager.start(getActivity());
        Parcelable query_data = getArguments().getParcelable(Constants.EXTRA_LISTING_QUERY);
        if (query_data != null) {
            listingsQuery = Parcels.unwrap(query_data);
            spiceManager.execute(new ListingsRequest(listingsQuery, server_address), new ListingsRequestListener());
        } else {
            String franchiseId = getArguments().getString(Constants.EXTRA_FRANCHISEID);
            if (franchiseId != null) {
                listingsQuery = new ListingsQuery();
                listingsQuery.api = new ApiQuery();
                listingsQuery.api.franchiseId = franchiseId;
                listingsQuery.api.pagenum = Integer.parseInt(Constants.PAGENUM_DEFAULT);
                listingsQuery.api.pagesize = Constants.PAGESIZE_DEFAULT;
                spiceManager.execute(new FranchiseListings(listingsQuery, server_address), new ListingsRequestListener());
            }
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

}