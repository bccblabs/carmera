package carmera.io.carmera.fragments.main_fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.parceler.Parcels;
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
import carmera.io.carmera.requests.ListingsRequest;
import carmera.io.carmera.utils.Constants;
import carmera.io.carmera.utils.InMemorySpiceService;

/**
 * Created by bski on 7/13/15.
 */
public class ListingsFragment extends Fragment implements OnResearchListener {


    @Bind (R.id.filter_sort_btn) View filter_sort_btn;

    @Bind(R.id.loading_container) View loading_container;

    @Bind(R.id.listings_recylcer) RecyclerView listings_recycler;

    @Bind(R.id.sort_btn) FloatingActionButton sort_btn;

    @Bind(R.id.filter_btn) FloatingActionButton filter_btn;



    @OnClick(R.id.sort_btn)
    public void onSort () {
        SortFragment sortFragment = new SortFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
        sortFragment.setArguments(args);
        sortFragment.setTargetFragment(this, 1);
        sortFragment.show(getChildFragmentManager(), "sort_dialog");
    }

    @OnClick(R.id.filter_btn)
    public void onFilter () {
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
        FilterFragment filterFragment = new FilterFragment();
        filterFragment.setArguments(args);
        filterFragment.setTargetFragment(this, 0);
        filterFragment.show(getChildFragmentManager(), "filter_dialog");
    }

    @Override
    public void onResearchCallback (ListingsQuery listingsQuery) {
        filter_sort_btn.setVisibility(View.INVISIBLE);
        ListingsRequest listingsRequest = new ListingsRequest(listingsQuery);
        spiceManager.execute (listingsRequest, listingsQuery.hashCode(), DurationInMillis.ALWAYS_RETURNED, new ListingsRequestListener());
        listingsAdapter.clear();
    }


    final Context context = getActivity();
    private ListingsAdapter listingsAdapter;
    private String TAG = getClass().getCanonicalName();
    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);
    private ListingsQuery listingsQuery;

    private final class ListingsRequestListener implements RequestListener<Listings> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onRequestSuccess (Listings result) {
            try {
                filter_sort_btn.setVisibility(View.VISIBLE);
                loading_container.setVisibility(View.GONE);
                listings_recycler.setVisibility(View.VISIBLE);
                listingsAdapter.addAll(result.getListings());
                listingsAdapter.notifyDataSetChanged();


                listingsQuery = result.getListingsQuery();
                Log.i (getClass().getCanonicalName(), new Gson().toJson(listingsQuery));
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            Toast.makeText(getActivity(), "Listings Adapter length: " + listingsAdapter.getItemCount(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Bundle args = getArguments();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        listingsQuery = (Parcels.unwrap(args.getParcelable(Constants.EXTRA_LISTING_QUERY)));
        listingsQuery.api.pagenum = 1;
        listingsQuery.api.pagesize = Constants.PAGESIZE_DEFAULT;
        listingsQuery.api.zipcode = sharedPreferences.getString("pref_key_zipcode", Constants.ZIPCODE_DEFAULT).trim();
        listingsQuery.api.radius = sharedPreferences.getString("pref_key_radius", Constants.RADIUS_DEFAULT).trim();

        Log.i (TAG, "Queries to go: " + this.listingsQuery.toString());
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.listings, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated (View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        ListingsRequest listingsRequest = new ListingsRequest(listingsQuery);
        spiceManager.execute (listingsRequest, listingsQuery.hashCode(), DurationInMillis.ALWAYS_RETURNED, new ListingsRequestListener());
        listings_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        sort_btn.setIcon(R.drawable.ic_sort_white_24dp);
        filter_btn.setIcon(R.drawable.ic_filter_list_white_24dp);
    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onStart () {
        super.onStart();
        spiceManager.start (getActivity());
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

    public static ListingsFragment newInstance() {
        return new ListingsFragment();
    }


}