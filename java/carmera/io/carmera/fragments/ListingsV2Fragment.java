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
import android.view.WindowManager;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.adapters.BetterRecyclerAdapter;
import carmera.io.carmera.adapters.ListingsV2Adapter;
import carmera.io.carmera.models.DataEntryFloat;
import carmera.io.carmera.models.GenQuery;
import carmera.io.carmera.models.ListingV2;
import carmera.io.carmera.models.Listings;
import carmera.io.carmera.models.ListingsV2;
import carmera.io.carmera.requests.ListingsRequest;
import carmera.io.carmera.utils.InMemorySpiceService;
import carmera.io.carmera.ListingDetailsViewer;
import carmera.io.carmera.utils.Util;
import carmera.io.carmera.widgets.SquareImageView;

public class ListingsV2Fragment extends Fragment {

    public static final String EXTRA_LISTING_QUERY = "extra_listing_query";
    public static final String EXTRA_LISTING_DATA = "extra_listing_data";
    public static final String EXTRA_LISTINGS_STAT = "extra_listing_stat";

    @Bind(R.id.listings_recylcer)
    RecyclerView listings_recycler;

    @Bind(R.id.loading_container)
    View loading_container;

    @Bind (R.id.carmera_holder)
    SquareImageView carmera_holder;

    @Bind (R.id.loading_text)
    ShimmerTextView loading_text;


    private Context context;
    private Shimmer shimmer = new Shimmer();
    private List<DataEntryFloat> listings_stats;
    private ListingsV2Adapter listingsV2Adapter;
    private String TAG = getClass().getCanonicalName();
    private SpiceManager spiceManager = new SpiceManager(InMemorySpiceService.class);
    private GenQuery listingsQuery;

    private final class ListingsRequestListener implements RequestListener<ListingsV2> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            Toast.makeText(context, "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onRequestSuccess (ListingsV2 result) {
            if (result != null) {
                listingsV2Adapter.addAll(result.getListings());
                listingsV2Adapter.notifyDataSetChanged();
                loading_container.setVisibility(View.GONE);
                listings_recycler.setVisibility(View.VISIBLE);
                listings_stats = result.getStats();
            }
        }
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Bundle args = getArguments();
        this.listingsQuery = Parcels.unwrap(args.getParcelable(EXTRA_LISTING_QUERY));
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.listings, container, false);
        ButterKnife.bind(this, v);

        Picasso.with(context).load(R.drawable.carmera).fit().centerCrop().into(carmera_holder);
        shimmer.setDuration(2000);
        shimmer.start(loading_text);
        return v;
    }

    @Override
    public void onViewCreated (View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        listingsQuery.setPagenum(1);
        listingsQuery.setPagesize(40);
        listingsQuery.setMax_dist(200);
        listingsQuery.setCoordinates(Util.getCoordinatesFromZip(92612));
        ListingsRequest listingsDataRequest = new ListingsRequest(listingsQuery);
        spiceManager.execute (listingsDataRequest, new Date().toString(), DurationInMillis.ALWAYS_RETURNED, new ListingsRequestListener());
        listings_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        listingsV2Adapter = new ListingsV2Adapter();
        listingsV2Adapter.setOnItemClickListener(new BetterRecyclerAdapter.OnItemClickListener<ListingV2>() {
            @Override
            public void onItemClick(View v, ListingV2 item, int position) {
                Intent i = new Intent(getActivity(), ListingDetailsViewer.class);
                Bundle args = new Bundle();
                Parcelable listing_data = Parcels.wrap(item);
                args.putParcelable(EXTRA_LISTING_DATA, listing_data);
                args.putParcelable (EXTRA_LISTINGS_STAT, Parcels.wrap(listings_stats));
                i.putExtras(args);
                startActivity(i);
            }
        });
        listings_recycler.setAdapter(listingsV2Adapter);
        listings_recycler.setHasFixedSize(false);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), listings_recycler, null);
    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        context = activity;
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

    public static ListingsV2Fragment newInstance() {
        return new ListingsV2Fragment();
    }


}
