package carmera.io.carmera.fragments.car_fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.parceler.Parcels;
import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.adapters.ListingsAdapter;
import carmera.io.carmera.models.Listings;
import carmera.io.carmera.requests.ListingsRequest;
import carmera.io.carmera.utils.InMemorySpiceService;

/**
 * Created by bski on 7/13/15.
 */
public class ListingsFragment extends Fragment {

    public static final String EXTRA_LISTING_QUERY = "extra_listing_query";
    public static final String EXTRA_LISTING_DATA = "extra_listing_data";

    @Bind(R.id.loading_container)
    View loading_container;

    @Bind(R.id.listings_recylcer)
    RecyclerView listings_recycler;

    final Context context = getActivity();
    private ListingsAdapter listingsAdapter;
    private String TAG = getClass().getCanonicalName();
    private SpiceManager spiceManager = new SpiceManager(InMemorySpiceService.class);
    private GenQuery vehicleQuery;

    private final class ListingsRequestListener implements RequestListener<Listings> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onRequestSuccess (Listings result) {
            Toast.makeText(getActivity(), "Listings Adapter length: " + listingsAdapter.getItemCount(), Toast.LENGTH_SHORT).show();
            try {
                loading_container.setVisibility(View.GONE);
                listings_recycler.setVisibility(View.VISIBLE);
                listingsAdapter.addAll(result.getListings());
                listingsAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Bundle args = getArguments();
        this.vehicleQuery = Parcels.unwrap(args.getParcelable(EXTRA_LISTING_QUERY));
        Log.i (TAG, "Queries to go: " + this.vehicleQuery.toString());
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
        ListingsRequest listingsRequest = new ListingsRequest(vehicleQuery);
        spiceManager.execute (listingsRequest, vehicleQuery.hashCode(), DurationInMillis.ALWAYS_RETURNED, new ListingsRequestListener());
        listings_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        listingsAdapter = new ListingsAdapter();
//        listingsAdapter.setOnItemClickListener(new BetterRecyclerAdapter.OnItemClickListener<Listing>() {
//            @Override
//            public void onItemClick(View v, Listing item, int position) {
//                Intent i = new Intent(getActivity(), ListingDetailsViewer.class);
//                Bundle args = new Bundle();
//                Parcelable listing_data = Parcels.wrap(item);
//                args.putParcelable(EXTRA_LISTING_DATA, listing_data);
//
//                i.putExtras(args);
//                startActivity(i);
//            }
//        });
        listings_recycler.setAdapter(listingsAdapter);
        listings_recycler.setHasFixedSize(false);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), listings_recycler, null);
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