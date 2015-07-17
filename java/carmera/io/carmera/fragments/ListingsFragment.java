package carmera.io.carmera.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.parceler.Parcels;

import javax.xml.datatype.Duration;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.adapters.BetterRecyclerAdapter;
import carmera.io.carmera.adapters.ListingsAdapter;
import carmera.io.carmera.models.Listing;
import carmera.io.carmera.models.Listings;
import carmera.io.carmera.models.Predictions;
import carmera.io.carmera.models.VehicleQueries;
import carmera.io.carmera.models.VehicleQuery;
import carmera.io.carmera.requests.ListingsDataRequest;
import carmera.io.carmera.utils.InMemorySpiceService;
import carmera.io.carmera.utils.RecyclerUtils;
import carmera.io.carmera.ListingDetailsViewer;

/**
 * Created by bski on 7/13/15.
 */
public class ListingsFragment extends Fragment {

    public static final String EXTRA_LISTING_QUERY = "extra_listing_query";
    public static final String EXTRA_LISTING_DATA = "extra_listing_data";

    @Bind(R.id.listings_recylcer)
    RecyclerView listings_recycler;

    final Context context = getActivity();
    private ListingsAdapter listingsAdapter;
    private String TAG = getClass().getCanonicalName();
    private SpiceManager spiceManager = new SpiceManager(InMemorySpiceService.class);
    private VehicleQueries vehicleQueries;

    private final class ListingsRequestListener implements RequestListener<Listings> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onRequestSuccess (Listings result) {
            Toast.makeText(getActivity(), "Listings Adapter length: " + listingsAdapter.getItemCount(), Toast.LENGTH_SHORT).show();
            listingsAdapter.addAll(result.getListings());
            listingsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        this.vehicleQueries = Parcels.unwrap(args.getParcelable(EXTRA_LISTING_QUERY));
        Toast.makeText(getActivity(), "Queries to go: " + this.vehicleQueries.getQueries().size(), Toast.LENGTH_SHORT).show();
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
        for (VehicleQuery vehicleQuery : this.vehicleQueries.getQueries()) {
            vehicleQuery.setPagenum(1);
            vehicleQuery.setPagesize(5);
            vehicleQuery.setRadius(500);
            vehicleQuery.setZipcode(92612);
            Toast.makeText(getActivity(), "Query params: " + vehicleQuery.get_query_params(), Toast.LENGTH_SHORT).show();
            ListingsDataRequest listingsDataRequest = new ListingsDataRequest(vehicleQuery);
            spiceManager.execute (listingsDataRequest, vehicleQuery.getTrim(), DurationInMillis.ALWAYS_RETURNED, new ListingsRequestListener());
        }
        listings_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        listingsAdapter = new ListingsAdapter();
        listingsAdapter.setOnItemClickListener(new BetterRecyclerAdapter.OnItemClickListener<Listing>() {
            @Override
            public void onItemClick(View v, Listing item, int position) {
                Intent i = new Intent(getActivity(), ListingDetailsViewer.class);
                Bundle args = new Bundle();
                Parcelable listing_data = Parcels.wrap(item);
                args.putParcelable(EXTRA_LISTING_DATA, listing_data);
                i.putExtras(args);
                startActivity(i);
            }
        });
        listings_recycler.setAdapter(listingsAdapter);
        listings_recycler.setHasFixedSize(false);
//        MaterialViewPagerHelper.registerRecyclerView(getActivity(), listings_recycler, null);
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
