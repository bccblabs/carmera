package veme.cario.com.CARmera.fragment.ListingWizard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.appyvet.rangebar.RangeBar;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;
//import org.elasticsearch.search.aggregations.AggregationBuilders;
//import org.elasticsearch.search.aggregations.metrics.MetricsAggregation;
//import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.searchbox.client.JestClient;
import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.util.MultiSelectSpinner;

/**
 * Created by bski on 3/17/15.
 */
public class ListingsSearchInputFragment extends WizardStep {

    @ContextVariable
    private JestClient jestClient;

//    private MetricsAggregation aggregation;
//    private SearchSourceBuilder searchSourceBuilder;
//    private AggregationBuilders aggregationBuilders;
//    private FilterBuilders filterBuilders;

    @ContextVariable
    private Map<String, String> rangeBarMap;

    private Spinner vehicle_state_spnr;
    private MultiSelectSpinner make_spnr, model_spnr, transmission_spnr, drivetrain_spnr, ext_spnr,
            int_spnr, compressor_spnr;

    private RangeBar hp_rb, torque_rb, combined_mpg_range_bar, transmission_speed_range_bar,
            year_range_bar, mileage_range_bar, radius_range_bar, repair_cost_range_bar,
            maintenance_cost_range_bar, insurance_cost_range_bar,depreciation_cost_range_bar;

    public ListingsSearchInputFragment () {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.layout_create_search, container, false);

        ArrayAdapter<CharSequence> state_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.car_state_array,
                R.layout.spinner_item);
        state_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        vehicle_state_spnr = (Spinner) v.findViewById(R.id.car_state_spinner);
        vehicle_state_spnr.setAdapter(state_adapter);

        make_spnr = (MultiSelectSpinner) v.findViewById (R.id.mk_multi_spnr);
        make_spnr.setItems(getResources().getStringArray(R.array.makes_array));

        model_spnr = (MultiSelectSpinner) v.findViewById (R.id.md_multi_spnr);

        transmission_spnr = (MultiSelectSpinner) v.findViewById (R.id.transmission_type);
        transmission_spnr.setItems(getResources().getStringArray(R.array.txn_array));

        drivetrain_spnr = (MultiSelectSpinner) v.findViewById (R.id.drivetrain_type_multi);
        drivetrain_spnr.setItems(getResources().getStringArray(R.array.drivetrain_array));

        compressor_spnr = (MultiSelectSpinner) v.findViewById (R.id.engine_compressor_type);
        compressor_spnr.setItems(getResources().getStringArray(R.array.compressor_array));

        ext_spnr = (MultiSelectSpinner) v.findViewById (R.id.ext_color_spinner);
        ext_spnr.setItems(getResources().getStringArray(R.array.color_array));

        int_spnr = (MultiSelectSpinner) v.findViewById(R.id.int_color_spinner);
        int_spnr.setItems(getResources().getStringArray(R.array.color_array));

        vehicle_state_spnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Selected Make(s)", ((Spinner) parent).getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        make_spnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Selected Make(s)", ((MultiSelectSpinner) parent).getSelectedItemsAsString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (make_spnr.getSelectedStrings().size() > 0) {
            List<String> makes_selected = make_spnr.getSelectedStrings(), model_names_list = new ArrayList<String>();
            for (String make : makes_selected) {
                if (make.equals("Acura")) {
                    addModelsToArray (model_names_list, R.array.acura_md);
                } else if (make.equals("Aston Martin")) {
                    addModelsToArray (model_names_list, R.array.astonmartin_md);
                }
            }
            String[] model_names_arr = new String[model_names_list.size()];
            model_names_arr = model_names_list.toArray(model_names_arr);
            Log.i("ListingSearchInputFragment", makes_selected.toString());
            for (String model : model_names_arr) {
                Log.i("ListingSearchInputFragment", model);
            }
            if (model_names_arr.length >0)
                model_spnr.setItems(model_names_arr);

        }

        /* range bars */
        hp_rb = (RangeBar) v.findViewById(R.id.horsepower_bar);
        hp_rb.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i2, String s, String s2) {
//                rangeBarMap.put("hp_l", s);
//                rangeBarMap.put("hp_h", s2);
            }
        });

        torque_rb = (RangeBar) v.findViewById(R.id.torque_bar);
        torque_rb.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i2, String s, String s2) {
                rangeBarMap.put("tq_l", s);
                rangeBarMap.put("tq_h", s2);
            }
        });

        combined_mpg_range_bar = (RangeBar) v.findViewById(R.id.combined_mpg_range_bar);
        combined_mpg_range_bar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i2, String s, String s2) {
                rangeBarMap.put("mpg_l", s);
                rangeBarMap.put("mpg_h", s2);

            }
        });

        transmission_speed_range_bar = (RangeBar) v.findViewById(R.id.transmission_speed_range_bar);
        transmission_speed_range_bar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i2, String s, String s2) {
                rangeBarMap.put("txn_l", s);
                rangeBarMap.put("txn_h", s2);
            }
        });

        year_range_bar = (RangeBar) v.findViewById(R.id.year_range_bar);
        year_range_bar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i2, String s, String s2) {
                rangeBarMap.put("yr_l", s);
                rangeBarMap.put("yr_h", s2);
            }
        });

        mileage_range_bar = (RangeBar) v.findViewById(R.id.mileage_range_bar);
        mileage_range_bar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i2, String s, String s2) {
                rangeBarMap.put("mileage_l", s);
                rangeBarMap.put("mileage_h", s2);
            }
        });

        radius_range_bar = (RangeBar) v.findViewById(R.id.radius_range_bar);
        radius_range_bar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i2, String s, String s2) {
                rangeBarMap.put("radius_l", s);
                rangeBarMap.put("radius_h", s2);
            }
        });

        repair_cost_range_bar = (RangeBar) v.findViewById(R.id.repair_cost_range_bar);
        repair_cost_range_bar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i2, String s, String s2) {
                rangeBarMap.put("repair_l", s);
                rangeBarMap.put("repair_h", s2);
            }
        });

        maintenance_cost_range_bar = (RangeBar) v.findViewById(R.id.maintenance_cost_range_bar);
        maintenance_cost_range_bar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i2, String s, String s2) {
                rangeBarMap.put("maintenance_l", s);
                rangeBarMap.put("maintenance_h", s2);
            }
        });

        insurance_cost_range_bar = (RangeBar) v.findViewById(R.id.insurance_cost_range_bar);
        insurance_cost_range_bar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i2, String s, String s2) {
                rangeBarMap.put("insurance_l", s);
                rangeBarMap.put("insurance_h", s2);
            }
        });

        depreciation_cost_range_bar = (RangeBar) v.findViewById(R.id.depreciation_cost_range_bar);
        depreciation_cost_range_bar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i2, String s, String s2) {

            }
        });


        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        /* initialize elastic search client */

        DroidClientConfig clientConfig = new DroidClientConfig
                .Builder("http://429cab6e1c887ea7d28923ebd5a56704-us-east-1.foundcluster.com:9200")
                .build();

        JestClientFactory factory = new JestClientFactory();
        factory.setDroidClientConfig(clientConfig);
        jestClient = factory.getObject();


    }

    private void addModelsToArray (List<String> models_array, int res_id) {
        String [] models = getResources().getStringArray(res_id);
        for (String model : models) {
            models_array.add(model);
        }
    }
    @Override
    public void onExit(int exitCode) {
        switch (exitCode) {
            case WizardStep.EXIT_NEXT:
                populate_query_obj();
            break;
            case WizardStep.EXIT_PREVIOUS:
            break;
        }
    }

    private void populate_query_obj () {

    }
}
