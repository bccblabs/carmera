package carmera.io.carmera.fragments.search_fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import org.parceler.Parcels;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carmera.io.carmera.BasicSearchActivity;
import carmera.io.carmera.ConditionSearchActivity;
import carmera.io.carmera.EquipmentSearchActivity;
import carmera.io.carmera.MechanicalSearchActivity;
import carmera.io.carmera.R;
import carmera.io.carmera.ReliabilitySearchActivity;
import carmera.io.carmera.models.Listings;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.models.queries.ApiQuery;
import carmera.io.carmera.models.queries.CarQuery;
import carmera.io.carmera.requests.FranchiseListings;
import carmera.io.carmera.requests.ListingsRequest;
import carmera.io.carmera.requests.SearchRequest;
import carmera.io.carmera.utils.Constants;
import carmera.io.carmera.utils.EndlessRecyclerOnScrollListener;
import carmera.io.carmera.utils.ScrollingLinearLayoutManager;

/**
 * Created by bski on 7/20/15.
 */
public class SearchContainer extends Fragment {

    public String TAG = getClass().getCanonicalName();

    @Bind (R.id.basic_container) View basic_container;
    @Bind (R.id.condition_container) View condition_container;
    @Bind (R.id.equipment_container) View equipment_container;
    @Bind (R.id.reliability_container) View reliability_container;
    @Bind (R.id.mechanical_container) View mechanical_container;
    @OnClick(R.id.search_listings_base)
    public void search_listings() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        listingsQuery.api.pagenum = 1;
        listingsQuery.api.pagesize = Constants.PAGESIZE_DEFAULT;
        listingsQuery.api.zipcode = sharedPreferences.getString("pref_key_zipcode", Constants.ZIPCODE_DEFAULT).trim();
        listingsQuery.api.radius = sharedPreferences.getString("pref_key_radius", Constants.RADIUS_DEFAULT).trim();
        search_callback.OnSearchListings(Parcels.wrap(ListingsQuery.class, listingsQuery));
    }

    private OnSearchVehiclesListener search_callback = null;
    private String server_address;
    private ListingsQuery listingsQuery = new ListingsQuery();
    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);

    public interface OnSearchVehiclesListener {
        void OnSearchListings (Parcelable car_query);
    }

    public static SearchContainer newInstance () {
        SearchContainer fragment = new SearchContainer();
        return fragment;
    }


    private final class SearchQueryListener implements RequestListener<ListingsQuery> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onRequestSuccess (ListingsQuery result) {
            try {
                if (SearchContainer.this.isAdded()) {
                    if (result.submodelCount > 0) {
                        SearchContainer.this.listingsQuery = result;
                        Toast.makeText(getActivity(), "Found " + result.submodelCount + " submodels matching your criteria", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "No cars matching your criteria :( please change your search!", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.search_container, container, false);
        ButterKnife.bind(this, v);
        basic_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), BasicSearchActivity.class);
                i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                startActivityForResult(i, 1);
            }
        });

        condition_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ConditionSearchActivity.class);
                i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                startActivityForResult(i, 1);
            }
        });

        equipment_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), EquipmentSearchActivity.class);
                i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                startActivityForResult(i, 1);
            }
        });

        reliability_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ReliabilitySearchActivity.class);
                i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                startActivityForResult(i, 1);
            }
        });

        mechanical_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MechanicalSearchActivity.class);
                i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                startActivityForResult(i, 1);
            }
        });

        return v;
    }

    @Override
    public void onViewCreated (View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

    }

    @Override
    public void onStart () {
        super.onStart();
        spiceManager.start(getActivity());
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
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        try {
            search_callback = (OnSearchVehiclesListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    ": needs to implement CameraResultListener" );
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ListingsQuery newQuery = Parcels.unwrap((Parcelable) data.getExtras().get(Constants.EXTRA_LISTING_QUERY));
            Gson gson = new Gson();
            if (!gson.toJson(newQuery).equals(gson.toJson(this.listingsQuery))) {
                this.listingsQuery = newQuery;
            }
//            spiceManager.execute(new SearchRequest(this.listingsQuery, server_address), new SearchQueryListener());
        }
    }

    @Override
    public void onCreate (Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        server_address = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("pref_key_server_addr", Constants.ServerAddr).trim();
    }
}
