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

import java.util.HashMap;
import java.util.Map;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.UserModels.SavedSearch;

/**
 * Created by bski on 1/8/15.
 */
public class CreateSearchFragment extends Fragment {
    private Spinner year_spnr, make_spnr, model_spnr, body_style_spnr;
    private Spinner min_price_spnr, max_price_spnr, location_spnr, origin_spnr, color_spnr, equipment_spnr;
    private Spinner txn_spnr, cylin_spnr, compress_spnr;
    private ButtonRectangle search_btn, save_search_btn;
    private ListingSearchCreatedListener listingsCallback = null;
    private SavedSearch savedSearch;
    private Map<String, Integer> searchEntryMap;

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
        searchEntryMap = new HashMap<String, Integer>();
        searchEntryMap.put ("Acura", R.array.acura_md);
        searchEntryMap.put ("Aston Martin", R.array.astonmartin_md);
        searchEntryMap.put ("Audi", R.array.audi_md);
        searchEntryMap.put ("Bentley", R.array.bentley_md);
        searchEntryMap.put ("BMW", R.array.bmw_md);
        searchEntryMap.put ("Buick", R.array.buick_md);
        searchEntryMap.put ("Cadillac", R.array.cadillac_md);
        searchEntryMap.put ("Chevrolet", R.array.chevrolet_md);
        searchEntryMap.put ("Chrysler", R.array.chrysler_md);
        searchEntryMap.put ("Dodge", R.array.dodge_md);
        searchEntryMap.put ("Ferrari", R.array.ferrari_md);
        searchEntryMap.put ("Fiat", R.array.fiat_md);
        searchEntryMap.put ("Fisker", R.array.fisker_md);
        searchEntryMap.put ("Ford", R.array.ford_md);
        searchEntryMap.put ("GMC", R.array.gmc_md);
        searchEntryMap.put ("Honda", R.array.honda_md);
        searchEntryMap.put ("Hyundai", R.array.hyundai_md);
        searchEntryMap.put ("Infiniti", R.array.infiniti_md);
        searchEntryMap.put ("Jaguar", R.array.jaguar_md);
        searchEntryMap.put ("Jeep", R.array.jeep_md);
        searchEntryMap.put ("Kia", R.array.kia_md);
        searchEntryMap.put ("Lamborghini", R.array.lamboghini_md);
        searchEntryMap.put ("Land Rover", R.array.lr_md);
        searchEntryMap.put ("Lexus", R.array.lexux_md);
        searchEntryMap.put ("Lincoln", R.array.lincoln_md);
        searchEntryMap.put ("Lotus", R.array.lotus_md);
        searchEntryMap.put ("Mazda", R.array.mazda_md);
        searchEntryMap.put ("Mercedes-Benz", R.array.mercedes_md);
        searchEntryMap.put ("Mini", R.array.mini_md);
        searchEntryMap.put ("Mitsubishi", R.array.mistubishi_md);
        searchEntryMap.put ("Nissan", R.array.nissan_md);
        searchEntryMap.put ("Porsche", R.array.porsche_md);
        searchEntryMap.put ("Scion", R.array.scion_md);
        searchEntryMap.put ("Smart", R.array.smart_md);
        searchEntryMap.put ("Subaru", R.array.subaru_md);
        searchEntryMap.put ("Suzuki", R.array.suzuki_md);
        searchEntryMap.put ("Toyota", R.array.toyota_md);
        searchEntryMap.put ("Volkswagen", R.array.volkswagen_md);
        searchEntryMap.put ("Volvo", R.array.volvo_md);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_listings_search, container, false);

        ArrayAdapter<CharSequence> year_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.years_array,
                R.layout.spinner_item);
        year_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        ArrayAdapter<CharSequence> makes_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.makes_array,
                R.layout.spinner_item);
        makes_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        ArrayAdapter<CharSequence> dist_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.pref_search_radius,
                R.layout.spinner_item);
        dist_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        ArrayAdapter<CharSequence> price_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.price_array,
                R.layout.spinner_item);
        price_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        ArrayAdapter<CharSequence> origin_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.origin_array,
                R.layout.spinner_item);
        price_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        ArrayAdapter<CharSequence> body_style_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.body_style_array,
                R.layout.spinner_item);
        price_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        ArrayAdapter<CharSequence> color_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.color_array,
                R.layout.spinner_item);
        price_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        ArrayAdapter<CharSequence> equipment_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.equipment_array,
                R.layout.spinner_item);
        price_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        ArrayAdapter<CharSequence> cylin_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.cylinder_array,
                R.layout.spinner_item);
        price_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        ArrayAdapter<CharSequence> txn_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.txn_array,
                R.layout.spinner_item);
        price_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        ArrayAdapter<CharSequence> compressor_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.compressor_array,
                R.layout.spinner_item);
        price_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

//
//        ArrayAdapter<CharSequence> models_adapter = ArrayAdapter.createFromResource(getActivity(),
//                R.array.model_array,
//                R.layout.spinner_item);
//        models_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
//
//
//
//
        year_spnr = (Spinner) view.findViewById(R.id.year_spinner);
        year_spnr.setAdapter(year_adapter);
//
        make_spnr = (Spinner) view.findViewById(R.id.make_spinner);
        make_spnr.setAdapter(makes_adapter);
//
//        model_spnr = (Spinner) view.findViewById(R.id.model_spinner);
//        model_spnr.setAdapter(models_adapter);

        min_price_spnr = (Spinner) view.findViewById(R.id.min_price_spinner);
        min_price_spnr.setAdapter(price_adapter);
//
        max_price_spnr = (Spinner) view.findViewById(R.id.max_price_spinner);
        max_price_spnr.setAdapter(price_adapter);
//
        location_spnr = (Spinner) view.findViewById(R.id.location_spinner);
        location_spnr.setAdapter(dist_adapter);

//        origin_spnr = (Spinner) view.findViewById(R.id.origin_spinner);
//        origin_spnr.setAdapter(origin_adapter);

        body_style_spnr = (Spinner) view.findViewById(R.id.body_style_spinner);
        body_style_spnr.setAdapter(body_style_adapter);

//        color_spnr = (Spinner) view.findViewById(R.id.color_spinner);
//        color_spnr.setAdapter(color_adapter);
//
//        txn_spnr = (Spinner) view.findViewById(R.id.transmission_spinner);
//        txn_spnr.setAdapter(txn_adapter);
//
//        cylin_spnr = (Spinner) view.findViewById(R.id.cylinder_spinner);
//        cylin_spnr.setAdapter(cylin_adapter);
//
//        compress_spnr = (Spinner) view.findViewById(R.id.compressor_spinner);
//        compress_spnr.setAdapter(compressor_adapter);

        savedSearch = new SavedSearch();

        save_search_btn = (ButtonRectangle) view.findViewById(R.id.create_listings_save_search_btn);
        save_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* create a dialog box */

//                savedSearch.setSearchName("blah");
//                savedSearch.saveInBackground();
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