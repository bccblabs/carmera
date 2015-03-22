package veme.cario.com.CARmera.fragment.ListingWizard;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.appyvet.rangebar.RangeBar;
import com.gc.materialdesign.views.ButtonRectangle;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.ImageFragment;
import veme.cario.com.CARmera.model.APIModels.Hit;
import veme.cario.com.CARmera.util.ListingsAdapterV2;
import veme.cario.com.CARmera.util.MultiSelectSpinner;


public class ListingsSearchInputFragment extends Fragment {
    private static String TAG = ListingsSearchInputFragment.class.getCanonicalName();

    private String hits_node_str;

    private String aggs_node_str;

    private String filter_qsl;

    private String query_string;

    private Spinner vehicle_state_spnr;

    private MultiSelectSpinner make_spnr, model_spnr, transmission_spnr, drivetrain_spnr, ext_spnr,
            int_spnr, compressor_spnr;

    private RangeBar hp_rb, torque_rb, combined_mpg_range_bar, transmission_speed_range_bar,
            year_range_bar, mileage_range_bar, radius_range_bar, repair_cost_range_bar,
            maintenance_cost_range_bar, insurance_cost_range_bar,depreciation_cost_range_bar, fuel_cost_range_bar;


    private ButtonRectangle start_search_lising_req_btn;
    private JestClient jestClient;

    public ListingsSearchInputFragment () {}

    public OnSearchBtnClickedListener onSearchBtnClickedListener;

    public interface OnSearchBtnClickedListener {
        public abstract void OnSearchButtonClicked (String responseString, String queryString);
    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        try {
            onSearchBtnClickedListener = (OnSearchBtnClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + ": "
                    + " needs to implement the Listeners!");
        }
    }

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

        start_search_lising_req_btn = (ButtonRectangle) v.findViewById(R.id.start_search_lising_req_btn);
        start_search_lising_req_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_query();
            }
        });
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

        /* range bars */
        hp_rb = (RangeBar) v.findViewById(R.id.horsepower_bar);
        torque_rb = (RangeBar) v.findViewById(R.id.torque_bar);
        combined_mpg_range_bar = (RangeBar) v.findViewById(R.id.combined_mpg_range_bar);
        transmission_speed_range_bar = (RangeBar) v.findViewById(R.id.transmission_speed_range_bar);
        year_range_bar = (RangeBar) v.findViewById(R.id.year_range_bar);
        mileage_range_bar = (RangeBar) v.findViewById(R.id.mileage_range_bar);
        radius_range_bar = (RangeBar) v.findViewById(R.id.radius_range_bar);
        repair_cost_range_bar = (RangeBar) v.findViewById(R.id.repair_cost_range_bar);
        maintenance_cost_range_bar = (RangeBar) v.findViewById(R.id.maintenance_cost_range_bar);
        insurance_cost_range_bar = (RangeBar) v.findViewById(R.id.insurance_cost_range_bar);
        depreciation_cost_range_bar = (RangeBar) v.findViewById(R.id.depreciation_cost_range_bar);
        fuel_cost_range_bar = (RangeBar) v.findViewById(R.id.fuel_cost_range_bar);
        Bundle args = getArguments();
        if (args != null)
            query_string = getArguments().getString("query_string");
        return v;
    }

    private void save_query () {
        BoolFilterBuilder boolFilterBuilder = FilterBuilders.boolFilter();
        boolFilterBuilder
                .must(FilterBuilders.rangeFilter("avg_fuel_cost")
                                .from(fuel_cost_range_bar.getLeftIndex() * fuel_cost_range_bar.getTickInterval() + fuel_cost_range_bar.getTickStart())
                                .to(fuel_cost_range_bar.getRightIndex() * fuel_cost_range_bar.getTickInterval() + fuel_cost_range_bar.getTickStart())
                )
                .must(FilterBuilders.rangeFilter("horsepower")
                                .from(hp_rb.getLeftIndex()  * hp_rb.getTickInterval() + hp_rb.getTickStart())
                                .to(hp_rb.getRightIndex() * hp_rb.getTickInterval() + hp_rb.getTickStart())
                )
                .must(FilterBuilders.rangeFilter("torque")
                                .from(torque_rb.getLeftIndex() * torque_rb.getTickInterval() + torque_rb.getTickStart())
                                .to(torque_rb.getRightIndex() * torque_rb.getTickInterval() + torque_rb.getTickStart())
                )
                .must(FilterBuilders.rangeFilter("combinedMpg")
                                .from(combined_mpg_range_bar.getLeftIndex() * combined_mpg_range_bar.getTickInterval() + combined_mpg_range_bar.getTickStart())
                                .to(combined_mpg_range_bar.getRightIndex() * combined_mpg_range_bar.getTickInterval() + combined_mpg_range_bar.getTickStart())
                )
//                .must(FilterBuilders.rangeFilter("numberOfSpeeds")
//                                .from(transmission_speed_range_bar.getLeftIndex() * transmission_speed_range_bar.getTickInterval() + transmission_speed_range_bar.getTickStart())
//                                .to(transmission_speed_range_bar.getRightIndex() * transmission_speed_range_bar.getTickInterval() + transmission_speed_range_bar.getTickStart())
//                )
                .must(FilterBuilders.rangeFilter("year")
                                .from(year_range_bar.getLeftIndex() * year_range_bar.getTickInterval() + year_range_bar.getTickStart())
                                .to(year_range_bar.getRightIndex() * year_range_bar.getTickInterval() + year_range_bar.getTickStart())
                )
                .must(FilterBuilders.rangeFilter("mileage")
                                .from(mileage_range_bar.getLeftIndex() * mileage_range_bar.getTickInterval() + mileage_range_bar.getTickStart())
                                .to(mileage_range_bar.getRightIndex() * mileage_range_bar.getTickInterval() + mileage_range_bar.getTickStart())
                )
                .must(FilterBuilders.rangeFilter("avg_repairs_cost")
                                .from(repair_cost_range_bar.getLeftIndex() * repair_cost_range_bar.getTickInterval() + repair_cost_range_bar.getTickStart())
                                .to(repair_cost_range_bar.getRightIndex() * repair_cost_range_bar.getTickInterval() + repair_cost_range_bar.getTickStart())
                )
                .must(FilterBuilders.rangeFilter("avg_maintenance_cost")
                                .from(maintenance_cost_range_bar.getLeftIndex() * maintenance_cost_range_bar.getTickInterval() + maintenance_cost_range_bar.getTickStart())
                                .to(maintenance_cost_range_bar.getRightIndex() * maintenance_cost_range_bar.getTickInterval() + maintenance_cost_range_bar.getTickStart())
                )
                .must(FilterBuilders.rangeFilter("avg_insurance_cost")
                                .from(insurance_cost_range_bar.getLeftIndex() * insurance_cost_range_bar.getTickInterval() + insurance_cost_range_bar.getTickStart())
                                .to(insurance_cost_range_bar.getRightIndex() * insurance_cost_range_bar.getTickInterval() + insurance_cost_range_bar.getTickStart())
                )
                .must(FilterBuilders.rangeFilter("avg_depreciation")
                                .from(depreciation_cost_range_bar.getLeftIndex() * depreciation_cost_range_bar.getTickInterval() + depreciation_cost_range_bar.getTickStart())
                                .to(depreciation_cost_range_bar.getRightIndex() * depreciation_cost_range_bar.getTickInterval() + depreciation_cost_range_bar.getTickStart())
                )
                .cache(true);


        try {
            ObjectMapper mapper = new ObjectMapper();
            String agg_query = loadJsonFromAsset();
            JsonNode agg_json = mapper.readTree (agg_query);
            JsonNode aggs = agg_json.path("aggs");
            if (agg_query == null) {
                throw new Exception("agg_query loading err");
            }
            filter_qsl = "{\n" +
                    "    \"query\": {\n" +
                    "        \"filtered\" : {\n" +
                    "           \"filter\":" +
                    boolFilterBuilder.toString() +
                    "        }\n" +
                    "    },\n" +
                    "   \"aggs\":"  + aggs.toString() +
                    "}";
            DroidClientConfig clientConfig = new DroidClientConfig
                    .Builder("http://429cab6e1c887ea7d28923ebd5a56704-us-east-1.foundcluster.com:9200")
                    .build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(clientConfig);
            jestClient = factory.getObject();
            new FilterTask().execute(filter_qsl);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String loadJsonFromAsset () {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("request.json");
            int size = is.available();
            byte[] buf = new byte[size];
            is.read(buf);
            is.close();
            json = new String (buf);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    class FilterTask extends AsyncTask<String, Void, JestResult> {

        private Exception exception;
        private JestResult result;
        private Search search;
        private String query_callback_string;

        protected JestResult doInBackground(String... indexName) {
            try {
                query_callback_string = indexName[0];
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
                    try {
                        JsonObject res_node = result.getJsonObject();
                        hits_node_str = res_node.getAsJsonObject("hits").getAsJsonArray("hits").toString();
                        aggs_node_str = res_node.getAsJsonObject("aggregations").toString();
                        if (hits_node_str != null)
                            Log.i (TAG, "hits: " + "hits: " + hits_node_str);
                        if (aggs_node_str != null)
                            Log.i (TAG, "hits: " + "aggs: " + aggs_node_str);
                        Log.i (TAG, "query_cb_str: " + query_callback_string);
                        onSearchBtnClickedListener.OnSearchButtonClicked(res_node.toString(), query_callback_string);

                        WindowManager.LayoutParams lparams = getActivity().getWindow().getAttributes();
                        lparams.dimAmount=0.0f;
                        getActivity().getWindow().setAttributes(lparams);
                    } catch (Exception e) {
                        Log.e (VehicleFilterFragment.class.getSimpleName(), ": err parsing json");
                        e.printStackTrace();
                    }
                } else {
                    Log.i("Queries no good", result.getJsonString());
                }
            } else {
                Log.i("Exception occurred", exception.getMessage());
            }
        }
    }

}
