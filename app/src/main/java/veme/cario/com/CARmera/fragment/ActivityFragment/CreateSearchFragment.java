package veme.cario.com.CARmera.fragment.ActivityFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.gc.materialdesign.views.ButtonRectangle;
import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.UserModels.SavedSearch;

/**
 * Created by bski on 1/8/15.
 */
public class CreateSearchFragment extends Fragment {
    private Spinner year_spnr, make_spnr, model_spnr, dist_spnr, pr_min_spnr, pr_max_spnr;
    private ButtonRectangle search_btn, save_search_btn;
    private ListingSearchCreatedListener listingsCallback = null;
    private SavedSearch savedSearch;

    public interface ListingSearchCreatedListener {
        public abstract void onSearchCreated(SavedSearch savedSearch);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listingsCallback = (ListingSearchCreatedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + ": "
                    + " needs to implement the ListingSearchCreatedListener!");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_listings_search, container, false);
//        /* listings header */
//        ArrayAdapter<CharSequence> year_adapter = ArrayAdapter.createFromResource(getActivity(),
//                R.array.years_array,
//                R.layout.spinner_item);
//        year_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
//
//        ArrayAdapter<CharSequence> makes_adapter = ArrayAdapter.createFromResource(getActivity(),
//                R.array.makes_array,
//                R.layout.spinner_item);
//        makes_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
//
//        ArrayAdapter<CharSequence> models_adapter = ArrayAdapter.createFromResource(getActivity(),
//                R.array.model_array,
//                R.layout.spinner_item);
//        models_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
//
//        ArrayAdapter<CharSequence> dist_adapter = ArrayAdapter.createFromResource(getActivity(),
//                R.array.pref_search_radius,
//                R.layout.spinner_item);
//        dist_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
//
//        ArrayAdapter<CharSequence> price_adapter = ArrayAdapter.createFromResource(getActivity(),
//                R.array.price_array,
//                R.layout.spinner_item);
//        price_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
//
//
//        year_spnr = (Spinner) view.findViewById(R.id.year_spinner);
//        year_spnr.setAdapter(year_adapter);
//
//        make_spnr = (Spinner) view.findViewById(R.id.make_spinner);
//        make_spnr.setAdapter(makes_adapter);
//
//        model_spnr = (Spinner) view.findViewById(R.id.model_spinner);
//        model_spnr.setAdapter(models_adapter);

//        pr_max_spnr = (Spinner) view.findViewById(R.id.range_max_spinner);
//        pr_max_spnr.setAdapter(price_adapter);
//
//        pr_min_spnr = (Spinner) view.findViewById(R.id.range_min_spinner);
//        pr_min_spnr.setAdapter(price_adapter);
//
//        dist_spnr = (Spinner) view.findViewById(R.id.distance_spinner);
//        dist_spnr.setAdapter(dist_adapter);

//        Bundle args = getArguments();
//        if (args != null) {
//            set_spinner_value (year_adapter, year_spnr, args.getString("listings_year"));
//            set_spinner_value (makes_adapter, make_spnr, args.getString("listings_make"));
//            set_spinner_value (models_adapter, model_spnr, args.getString("listings_model"));
//            set_spinner_value (makes_adapter, pr_min_spnr, "0");
//            set_spinner_value (models_adapter, pr_max_spnr, "100,000");
//        }
        savedSearch = new SavedSearch();

        save_search_btn = (ButtonRectangle) view.findViewById(R.id.create_listings_save_search_btn);
        save_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* create a dialog box */
                savedSearch.setSearchName("blah");
                savedSearch.saveInBackground();
            }
        });

        search_btn = (ButtonRectangle) view.findViewById(R.id.search_listings_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listingsCallback.onSearchCreated(savedSearch);
            }
        });
//        setHasOptionsMenu(true);
        return view;
    }

    private void set_spinner_value (ArrayAdapter<CharSequence> adapter, Spinner spinner, String value) {
        int pos = adapter.getPosition(value);
        spinner.setSelection(pos);
    }

}