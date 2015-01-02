package veme.cario.com.CARmera.fragment.ActivityFragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.nirhart.parallaxscroll.views.ParallaxListView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.util.ListingsAdapter;

public class NearbyListingFragment extends Fragment {

    private static final String TAG = "NEABBY_LISTINGS_FRAGMENT";

    /* do a find in background query from this guy's userinfp ? */
    /* see the "favorites implementation */
    private ListingsAdapter listingsAdapter;
    private ListView nearby_listings_listview;
    private LinearLayout no_nearby_listings_overlay;
    private LinearLayout search_header_overlay;

    private Spinner year_spnr, make_spnr, model_spnr, dist_spnr, pr_min_spnr, pr_max_spnr;
    private Button search_btn;

//    private OnNearbyListingSelectedListener nearbyListingsCallback;
    public interface OnNearbyListingSelectedListener {
        public abstract void OnNearbyListingsSelected (int pos);
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
////            nearbyListingsCallback = (OnNearbyListingSelectedListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " has to implement the OnNearbyListingsSelectedListener interface");
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nearby_listings, container, false);

        nearby_listings_listview = (ListView) view.findViewById (R.id.nearby_listings_listview);
        no_nearby_listings_overlay = (LinearLayout) view.findViewById (R.id.no_nearby_listings_overlay);
        search_header_overlay = (LinearLayout) view.findViewById (R.id.search_listings_overlay);

        listingsAdapter = new ListingsAdapter(inflater.getContext());

        nearby_listings_listview.setAdapter(listingsAdapter);
        nearby_listings_listview.setEmptyView(no_nearby_listings_overlay);


        /* listings header */
        ArrayAdapter<CharSequence> year_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.years_array,
                android.R.layout.simple_spinner_item);
        year_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> makes_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.makes_array,
                android.R.layout.simple_spinner_item);
        makes_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> models_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.model_array,
                android.R.layout.simple_spinner_item);
        models_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> dist_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.pref_search_radius,
                android.R.layout.simple_spinner_item);
        dist_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> price_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.price_array,
                android.R.layout.simple_spinner_item);
        price_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        year_spnr = (Spinner) view.findViewById(R.id.year_spinner);
        year_spnr.setAdapter(year_adapter);

        make_spnr = (Spinner) view.findViewById(R.id.make_spinner);
        make_spnr.setAdapter(makes_adapter);

        model_spnr = (Spinner) view.findViewById(R.id.model_spinner);
        model_spnr.setAdapter(models_adapter);

        pr_max_spnr = (Spinner) view.findViewById(R.id.range_max_spinner);
        pr_max_spnr.setAdapter(price_adapter);

        pr_min_spnr = (Spinner) view.findViewById(R.id.range_min_spinner);
        pr_min_spnr.setAdapter(price_adapter);

        dist_spnr = (Spinner) view.findViewById(R.id.distance_spinner);
        dist_spnr.setAdapter(dist_adapter);

        Bundle args = getArguments();
        if (args != null) {
            set_spinner_value (year_adapter, year_spnr, args.getString("listings_year"));
            set_spinner_value (makes_adapter, make_spnr, args.getString("listings_make"));
            set_spinner_value (models_adapter, model_spnr, args.getString("listings_model"));
            set_spinner_value (makes_adapter, pr_min_spnr, "0");
            set_spinner_value (models_adapter, pr_max_spnr, "100,000");
        }
//        /* search logic */
        search_btn = (Button) view.findViewById(R.id.search_listings_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                doQuery(year_spnr.getSelectedItem().toString(), make_spnr.getSelectedItem().toString(),
//                        model_spnr.getSelectedItem().toString(), pr_min_spnr.getSelectedItem().toString(),
//                        pr_max_spnr.getSelectedItem().toString());
////                search_header_overlay.setVisibility(View.GONE);
                doQuery("2014", "bmw", "x3", "0", "100,000");
            }
        });
//        nearby_listings_listview.addHeaderView(search_header_overlay);
        setHasOptionsMenu(true);
        return view;
    }

    private void doQuery(String yr, String mk, String md, String price_min, String price_max) {

        /* TODO: next iteration, go fetch thumbnail versions of the image */
        listingsAdapter.clear();
        ParseQuery<TaggedVehicle> query = ParseQuery.getQuery("TaggedVehicle");
        query.setLimit(5);
        List<String> fields = new ArrayList<String>(Arrays.asList("year", "make", "model", "createdAt", "photo", "price"));

        query.whereEqualTo("year", yr);
        query.whereEqualTo("make", mk);
        query.whereEqualTo("model", md);
//        query.whereGreaterThanOrEqualTo("price", price_min);
//        query.whereLessThanOrEqualTo("price", price_max);

        query.selectKeys(fields);
        query.findInBackground(new FindCallback<TaggedVehicle>() {
            @Override
            public void done(List<TaggedVehicle> taggedVehicles, ParseException e) {
                for (TaggedVehicle vehicle : taggedVehicles) {
                    listingsAdapter.add(vehicle);
                }
            }
        });
        listingsAdapter.notifyDataSetChanged();
    }

    private void set_spinner_value (ArrayAdapter<CharSequence> adapter, Spinner spinner, String value) {
        int pos = adapter.getPosition(value);
        spinner.setSelection(pos);
    }

}
