package veme.cario.com.CARmera.fragment.ListingWizard;

import android.os.AsyncTask;
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

import org.apache.lucene.queryparser.xml.FilterBuilder;
import org.apache.lucene.queryparser.xml.builders.RangeFilterBuilder;
import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;

import org.elasticsearch.action.support.QuerySourceBuilder;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.FilterBuilders.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;

//import org.elasticsearch.search.aggregations.AggregationBuilders;
//import org.elasticsearch.search.aggregations.metrics.MetricsAggregation;
//import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.CreateIndex;
import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.Json.ListingV2;
import veme.cario.com.CARmera.util.MultiSelectSpinner;

/**
 * Created by bski on 3/17/15.
 */
public class ListingsSearchInputFragment extends WizardStep {


//    private MetricsAggregation aggregation;
//    private SearchSourceBuilder searchSourceBuilder;
//    private AggregationBuilders aggregationBuilders;
//    private FilterBuilders filterBuilders;

//    @ContextVariable

//    @ContextVariable
    private SearchResponse searchResponse;

//    @ContextVariable
    private JestClient jestClient;

    private BoolFilterBuilder boolFilterBuilder= FilterBuilders.boolFilter();

    @ContextVariable
    private String filter_qsl;

    //    @ContextVariable
    private List<FilterBuilder> filterBuilders_list;

//    @ContextVariable
//    private Map<String, String> // rangeBarMap;

    private Spinner vehicle_state_spnr;
    private MultiSelectSpinner make_spnr, model_spnr, transmission_spnr, drivetrain_spnr, ext_spnr,
            int_spnr, compressor_spnr;

    private RangeBar hp_rb, torque_rb, combined_mpg_range_bar, transmission_speed_range_bar,
            year_range_bar, mileage_range_bar, radius_range_bar, repair_cost_range_bar,
            maintenance_cost_range_bar, insurance_cost_range_bar,depreciation_cost_range_bar, fuel_cost_range_bar;

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

            for (String model : model_names_arr) {
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
                Log.i (ListingsSearchInputFragment.class.getSimpleName(), boolFilterBuilder.toString());
            }
        });
        torque_rb = (RangeBar) v.findViewById(R.id.torque_bar);
        torque_rb.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i2, String s, String s2) {
//                rangeBarMap.put("tq_l", s);
//                rangeBarMap.put("tq_h", s2);
            }
        });
        combined_mpg_range_bar = (RangeBar) v.findViewById(R.id.combined_mpg_range_bar);
        combined_mpg_range_bar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i2, String s, String s2) {
//                rangeBarMap.put("mpg_l", s);
//                rangeBarMap.put("mpg_h", s2);

            }
        });

        transmission_speed_range_bar = (RangeBar) v.findViewById(R.id.transmission_speed_range_bar);
        transmission_speed_range_bar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i2, String s, String s2) {
//                rangeBarMap.put("txn_l", s);
//                rangeBarMap.put("txn_h", s2);
            }
        });

        year_range_bar = (RangeBar) v.findViewById(R.id.year_range_bar);
        year_range_bar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i2, String s, String s2) {
//                rangeBarMap.put("yr_l", s);
//                rangeBarMap.put("yr_h", s2);
            }
        });

        mileage_range_bar = (RangeBar) v.findViewById(R.id.mileage_range_bar);
        mileage_range_bar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i2, String s, String s2) {
//                rangeBarMap.put("mileage_l", s);
//                rangeBarMap.put("mileage_h", s2);
            }
        });

        radius_range_bar = (RangeBar) v.findViewById(R.id.radius_range_bar);
        radius_range_bar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i2, String s, String s2) {
//                rangeBarMap.put("radius_l", s);
//                rangeBarMap.put("radius_h", s2);
//                boolFilterBuilder.must(FilterBuilders.rangeFilter("numberOfSpeeds").from(s).to(s2));
            }
        });

        repair_cost_range_bar = (RangeBar) v.findViewById(R.id.repair_cost_range_bar);
        repair_cost_range_bar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i2, String s, String s2) {
//                rangeBarMap.put("repair_l", s);
//                rangeBarMap.put("repair_h", s2);
            }
        });

        maintenance_cost_range_bar = (RangeBar) v.findViewById(R.id.maintenance_cost_range_bar);
        maintenance_cost_range_bar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i2, String s, String s2) {
//                rangeBarMap.put("maintenance_l", s);
//                rangeBarMap.put("maintenance_h", s2);
            }
        });

        insurance_cost_range_bar = (RangeBar) v.findViewById(R.id.insurance_cost_range_bar);
        insurance_cost_range_bar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i2, String s, String s2) {
//                rangeBarMap.put("insurance_l", s);
//                rangeBarMap.put("insurance_h", s2);
            }
        });

        depreciation_cost_range_bar = (RangeBar) v.findViewById(R.id.depreciation_cost_range_bar);
        depreciation_cost_range_bar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i2, String s, String s2) {
            }
        });

        fuel_cost_range_bar = (RangeBar) v.findViewById(R.id.fuel_cost_range_bar);
        fuel_cost_range_bar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i2, String s, String s2) {
            }
        });


        return v;
    }

    private void addModelsToArray (List<String> models_array, int res_id) {
    }

    @Override
    public void onExit(int exitCode) {
        switch (exitCode) {
            case WizardStep.EXIT_NEXT:
                exec_query();
            break;
            case WizardStep.EXIT_PREVIOUS:
            break;
        }
    }

    private void exec_query () {
        DroidClientConfig clientConfig = new DroidClientConfig
                .Builder("http://429cab6e1c887ea7d28923ebd5a56704-us-east-1.foundcluster.com:9200")
                .build();

        JestClientFactory factory = new JestClientFactory();
        factory.setDroidClientConfig(clientConfig);
        jestClient = factory.getObject();
        boolFilterBuilder
                .must(FilterBuilders.rangeFilter("avg_fuel_cost")
                                .from(fuel_cost_range_bar.getLeftIndex() * fuel_cost_range_bar.getTickInterval() + fuel_cost_range_bar.getTickStart())
                                .to(fuel_cost_range_bar.getRightIndex() * fuel_cost_range_bar.getTickInterval() + fuel_cost_range_bar.getTickStart())
                )
                .must(FilterBuilders.rangeFilter("horsepower")
                                .from(hp_rb.getLeftIndex()  * hp_rb.getTickInterval() + hp_rb.getTickStart())
                                .to(hp_rb.getRightIndex() * hp_rb.getTickInterval() + hp_rb.getTickStart())
                );
//                .must(FilterBuilders.rangeFilter("torque")
//                                .from(torque_rb.getLeftIndex() * torque_rb.getTickInterval() + torque_rb.getTickStart())
//                                .to(torque_rb.getRightIndex() * torque_rb.getTickInterval() + torque_rb.getTickStart())
//                )
//                .must(FilterBuilders.rangeFilter("combinedMpg")
//                                .from(combined_mpg_range_bar.getLeftIndex() * combined_mpg_range_bar.getTickInterval() + combined_mpg_range_bar.getTickStart())
//                                .to(combined_mpg_range_bar.getRightIndex() * combined_mpg_range_bar.getTickInterval() + combined_mpg_range_bar.getTickStart())
//                )
//                .must(FilterBuilders.rangeFilter("numberOfSpeeds")
//                                .from(transmission_speed_range_bar.getLeftIndex() * transmission_speed_range_bar.getTickInterval() + transmission_speed_range_bar.getTickStart())
//                                .to(transmission_speed_range_bar.getRightIndex() * transmission_speed_range_bar.getTickInterval() + transmission_speed_range_bar.getTickStart())
//                )
//                .must(FilterBuilders.rangeFilter("year")
//                                .from(year_range_bar.getLeftIndex() * year_range_bar.getTickInterval() + year_range_bar.getTickStart())
//                                .to(year_range_bar.getRightIndex() * year_range_bar.getTickInterval() + year_range_bar.getTickStart())
//                )
//                .must(FilterBuilders.rangeFilter("mileage")
//                                .from(mileage_range_bar.getLeftIndex() * mileage_range_bar.getTickInterval() + mileage_range_bar.getTickStart())
//                                .to(mileage_range_bar.getRightIndex() * mileage_range_bar.getTickInterval() + mileage_range_bar.getTickStart())
//                )
//                .must(FilterBuilders.rangeFilter("avg_repairs_cost")
//                                .from(repair_cost_range_bar.getLeftIndex() * repair_cost_range_bar.getTickInterval() + repair_cost_range_bar.getTickStart())
//                                .to(repair_cost_range_bar.getRightIndex() * repair_cost_range_bar.getTickInterval() + repair_cost_range_bar.getTickStart())
//                )
//                .must(FilterBuilders.rangeFilter("avg_maintenance_cost")
//                                .from(maintenance_cost_range_bar.getLeftIndex() * maintenance_cost_range_bar.getTickInterval() + maintenance_cost_range_bar.getTickStart())
//                                .to(maintenance_cost_range_bar.getRightIndex() * maintenance_cost_range_bar.getTickInterval() + maintenance_cost_range_bar.getTickStart())
//                )
//                .must(FilterBuilders.rangeFilter("avg_insurance_cost")
//                                .from(insurance_cost_range_bar.getLeftIndex() * insurance_cost_range_bar.getTickInterval() + insurance_cost_range_bar.getTickStart())
//                                .to(insurance_cost_range_bar.getRightIndex() * insurance_cost_range_bar.getTickInterval() + insurance_cost_range_bar.getTickStart())
//                )
//                .must(FilterBuilders.rangeFilter("avg_depreciation")
//                                .from(depreciation_cost_range_bar.getLeftIndex() * depreciation_cost_range_bar.getTickInterval() + depreciation_cost_range_bar.getTickStart())
//                                .to(depreciation_cost_range_bar.getRightIndex() * depreciation_cost_range_bar.getTickInterval() + depreciation_cost_range_bar.getTickStart())
//                )
//                .cache(true);

        String boolFilterString =   "{\n" +
                                    "    \"query\": {\n" +
                                    "        \"filtered\" : {\n" +
                                    "           \"filter\":" + boolFilterBuilder.toString() +
                                    "        }\n" +
                                    "    }\n" +
                                    "}";
        Log.i (ListingsSearchInputFragment.class.getSimpleName(), boolFilterString);

        filter_qsl = boolFilterString;

        new CreateIndexTask().execute(boolFilterString);

//        Search search = new Search.Builder(boolFilterString).addIndex("listings").build();
//
//        try {
//            SearchResult result = jestClient.execute(search);
//            Log.i (ListingsSearchInputFragment.class.getSimpleName(), result.getJsonString());
////            List<ListingV2> listings = result.getSourceAsObjectList(ListingV2.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    class CreateIndexTask extends AsyncTask<String, Void, JestResult> {

        private Exception exception;
        private JestResult result;
        private Search search;

        protected JestResult doInBackground(String... indexName) {
            try {
                search = new Search.Builder(indexName[0]).addIndex("listings").build();
                result = jestClient.execute(search);
                return result;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        protected void onPostExecute(JestResult feed) {
            if (exception == null) {
                if (result.isSucceeded()) {
                    Log.i ("Queries", result.getJsonString());
                } else {
                    Log.i("Queries no good", result.getJsonString());
                }
            } else {
                Log.i("Exception occurred", exception.getMessage());
            }
        }
    }}
