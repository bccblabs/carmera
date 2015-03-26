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
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;

import org.codehaus.jackson.JsonNode;
import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.APIModels.Hit;
import veme.cario.com.CARmera.model.Json.ListingV2;
import veme.cario.com.CARmera.model.ListingAgg;
import veme.cario.com.CARmera.util.ListingsAdapterV2;

public class VehicleFilterFragment extends Fragment
        implements FloatingActionsMenu.OnFloatingActionsMenuUpdateListener {

    private static String TAG = VehicleFilterFragment.class.getCanonicalName();

    private ListingsAdapterV2 listingsAdapter;
    private ListView listings_lv;
    private FloatingActionsMenu floatingActionsMenu;

    private HashSet <String> modelSet = new HashSet<String>();

    private String query_string, res_string;

    private FloatingActionButton filter_btn, zero_sixty_sort, hp_sort, filter_model, price_sort, mileage_sort, mpg_sort;

    private OnListngV2SelectedListener onlistingselectedcallback;

    private OnFilterPressedListener onFilterPressedListener;

    public VehicleFilterFragment() {
    }

    @Override
    public void onMenuExpanded() {
        WindowManager.LayoutParams lparams = getActivity().getWindow().getAttributes();
        lparams.dimAmount=0.3f;
        getActivity().getWindow().setAttributes(lparams);
    }

    @Override
    public void onMenuCollapsed() {
        WindowManager.LayoutParams lparams = getActivity().getWindow().getAttributes();
        lparams.dimAmount=0.0f;
        getActivity().getWindow().setAttributes(lparams);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onlistingselectedcallback = (OnListngV2SelectedListener) activity;
            onFilterPressedListener = (OnFilterPressedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " has to implement the OnListingSelectedListener interface");
        }
    }

    public interface OnFilterPressedListener {
        public abstract void OnFilterPressed (String query_string);
    }


    public interface OnListngV2SelectedListener {
        public abstract void OnListingV2SelectedCallback(Bundle vehicle_agg_info);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.layout_vehicle_filter, container, false);
        listings_lv = (ListView) v.findViewById(R.id.listings_view_v2);
        listingsAdapter = new ListingsAdapterV2(inflater.getContext());
        listings_lv.setAdapter(listingsAdapter);
        listings_lv.setEmptyView(v.findViewById(R.id.no_listings_found));
        floatingActionsMenu = (FloatingActionsMenu) v.findViewById(R.id.sort_options_menu);

        filter_btn = (FloatingActionButton) v.findViewById(R.id.filter_search);
        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFilterPressedListener.OnFilterPressed(query_string);
            }
        });

        zero_sixty_sort = (FloatingActionButton) v.findViewById(R.id.zero_sixty_sort);
        zero_sixty_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .forceStacking(true)
                        .title ("Sort By Zero-Sixty Time")
                        .positiveText("Fastest to Slowest")
                        .negativeText("Slowest to Fastest")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                if (query_string != null) {
                                    JsonObject query_obj = new JsonParser().parse(VehicleFilterFragment.this.query_string).getAsJsonObject();
                                    String sort_str = "[\n" +
                                            "        {\n" +
                                            "            \"zerosixty\": {\n" +
                                            "                \"order\": \"asc\",\n" +
                                            "                \"missing\": \"_last\",\n" +
                                            "                \"ignore_unmapped\": true\n" +
                                            "            }\n" +
                                            "        }\n" +
                                            "    ]";
                                    JsonArray sort_obj = new JsonParser().parse (sort_str).getAsJsonArray();
                                    query_obj.add("sort", sort_obj);
                                    new SortTask().execute(query_obj.toString());
                                }
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                if (query_string != null) {
                                    JsonObject query_obj = new JsonParser().parse(VehicleFilterFragment.this.query_string).getAsJsonObject();
                                    String sort_str = "[\n" +
                                            "        {\n" +
                                            "            \"zerosixty\": {\n" +
                                            "                \"order\": \"desc\",\n" +
                                            "                \"missing\": \"_last\",\n" +
                                            "                \"ignore_unmapped\": true\n" +
                                            "            }\n" +
                                            "        }\n" +
                                            "    ]";
                                    JsonArray sort_obj = new JsonParser().parse (sort_str).getAsJsonArray();
                                    query_obj.add("sort", sort_obj);
                                    new SortTask().execute(query_obj.toString());
                                }
                            }
                        })
                        .show();
            }
        });

        hp_sort = (FloatingActionButton) v.findViewById(R.id.hp_sort);
        hp_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .forceStacking(true)
                        .title ("Sort By Horsepower")
                        .positiveText("Highest to Lowest")
                        .negativeText("Lowest to Highest")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                if (query_string != null) {
                                    JsonObject query_obj = new JsonParser().parse(VehicleFilterFragment.this.query_string).getAsJsonObject();
                                    String sort_str = "[\n" +
                                            "        {\n" +
                                            "            \"horsepower\": {\n" +
                                            "                \"order\": \"desc\",\n" +
                                            "                \"missing\": \"_last\",\n" +
                                            "                \"ignore_unmapped\": true\n" +
                                            "            }\n" +
                                            "        }\n" +
                                            "    ]";
                                    JsonArray sort_obj = new JsonParser().parse (sort_str).getAsJsonArray();
                                    query_obj.add("sort", sort_obj);
                                    new SortTask().execute(query_obj.toString());
                                }
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                if (query_string != null) {
                                    JsonObject query_obj = new JsonParser().parse(VehicleFilterFragment.this.query_string).getAsJsonObject();
                                    String sort_str = "[\n" +
                                            "        {\n" +
                                            "            \"horsepower\": {\n" +
                                            "                \"order\": \"asc\",\n" +
                                            "                \"missing\": \"_last\",\n" +
                                            "                \"ignore_unmapped\": true\n" +
                                            "            }\n" +
                                            "        }\n" +
                                            "    ]";
                                    JsonArray sort_obj = new JsonParser().parse (sort_str).getAsJsonArray();
                                    query_obj.add("sort", sort_obj);
                                    new SortTask().execute(query_obj.toString());
                                }
                            }
                        })
                        .show();
            }
        });

        filter_model = (FloatingActionButton) v.findViewById(R.id.filter_model);
        filter_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .forceStacking(true)
                        .title ("Filter By Model")
                        .positiveText("Highest to Lowest")
                        .negativeText("Lowest to Highest")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                if (query_string != null) {
                                    JsonObject query_obj = new JsonParser().parse(VehicleFilterFragment.this.query_string).getAsJsonObject();
                                    String sort_str = "[\n" +
                                            "        {\n" +
                                            "            \"torque\": {\n" +
                                            "                \"order\": \"desc\",\n" +
                                            "                \"missing\": \"_last\",\n" +
                                            "                \"ignore_unmapped\": true\n" +
                                            "            }\n" +
                                            "        }\n" +
                                            "    ]";
                                    JsonArray sort_obj = new JsonParser().parse (sort_str).getAsJsonArray();
                                    query_obj.add("sort", sort_obj);
                                    new SortTask().execute(query_obj.toString());
                                }
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                if (query_string != null) {
                                    JsonObject query_obj = new JsonParser().parse(VehicleFilterFragment.this.query_string).getAsJsonObject();
                                    String sort_str = "[\n" +
                                            "        {\n" +
                                            "            \"torque\": {\n" +
                                            "                \"order\": \"asc\",\n" +
                                            "                \"missing\": \"_last\",\n" +
                                            "                \"ignore_unmapped\": true\n" +
                                            "            }\n" +
                                            "        }\n" +
                                            "    ]";
                                    JsonArray sort_obj = new JsonParser().parse (sort_str).getAsJsonArray();
                                    query_obj.add("sort", sort_obj);
                                    new SortTask().execute(query_obj.toString());
                                }
                            }
                        })
                        .show();

            }
        });

        mileage_sort = (FloatingActionButton) v.findViewById(R.id.mileage_sort);
        mileage_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .forceStacking(true)
                        .title ("Sort By Mileage")
                        .positiveText("Highest to Lowest")
                        .negativeText("Lowest to Highest")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                if (query_string != null) {
                                    JsonObject query_obj = new JsonParser().parse(VehicleFilterFragment.this.query_string).getAsJsonObject();
                                    String sort_str = "[\n" +
                                            "        {\n" +
                                            "            \"mileage\": {\n" +
                                            "                \"order\": \"desc\",\n" +
                                            "                \"missing\": \"_last\",\n" +
                                            "                \"ignore_unmapped\": true\n" +
                                            "            }\n" +
                                            "        }\n" +
                                            "    ]";
                                    JsonArray sort_obj = new JsonParser().parse (sort_str).getAsJsonArray();
                                    query_obj.add("sort", sort_obj);
                                    new SortTask().execute(query_obj.toString());
                                }
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                if (query_string != null) {
                                    JsonObject query_obj = new JsonParser().parse(VehicleFilterFragment.this.query_string).getAsJsonObject();
                                    String sort_str = "[\n" +
                                            "        {\n" +
                                            "            \"mileage\": {\n" +
                                            "                \"order\": \"asc\",\n" +
                                            "                \"missing\": \"_last\",\n" +
                                            "                \"ignore_unmapped\": true\n" +
                                            "            }\n" +
                                            "        }\n" +
                                            "    ]";
                                    JsonArray sort_obj = new JsonParser().parse (sort_str).getAsJsonArray();
                                    query_obj.add("sort", sort_obj);
                                    new SortTask().execute(query_obj.toString());
                                }
                            }
                        })
                        .show();


            }
        });

        price_sort = (FloatingActionButton) v.findViewById(R.id.price_sort);
        price_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .forceStacking(true)
                        .title ("Sort By Dealer Offer Price")
                        .positiveText("Highest to Lowest")
                        .negativeText("Lowest to Highest")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                if (query_string != null) {
                                    JsonObject query_obj = new JsonParser().parse(VehicleFilterFragment.this.query_string).getAsJsonObject();
                                    String sort_str = "[\n" +
                                            "        {\n" +
                                            "            \"dealerOfferPrice\": {\n" +
                                            "                \"order\": \"desc\",\n" +
                                            "                \"missing\": \"_last\",\n" +
                                            "                \"ignore_unmapped\": true\n" +
                                            "            }\n" +
                                            "        }\n" +
                                            "    ]";
                                    JsonArray sort_obj = new JsonParser().parse (sort_str).getAsJsonArray();
                                    query_obj.add("sort", sort_obj);
                                    new SortTask().execute(query_obj.toString());
                                }
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                if (query_string != null) {
                                    JsonObject query_obj = new JsonParser().parse(VehicleFilterFragment.this.query_string).getAsJsonObject();
                                    String sort_str = "[\n" +
                                            "        {\n" +
                                            "            \"dealerOfferPrice\": {\n" +
                                            "                \"order\": \"asc\",\n" +
                                            "                \"missing\": \"_last\",\n" +
                                            "                \"ignore_unmapped\": true\n" +
                                            "            }\n" +
                                            "        }\n" +
                                            "    ]";
                                    JsonArray sort_obj = new JsonParser().parse (sort_str).getAsJsonArray();
                                    query_obj.add("sort", sort_obj);
                                    new SortTask().execute(query_obj.toString());
                                }
                            }
                        })
                        .show();
            }
        });

        mpg_sort = (FloatingActionButton) v.findViewById(R.id.mpg_sort);
        mpg_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .forceStacking(true)
                        .title ("Sort By Combined Mileage")
                        .positiveText("Highest to Lowest")
                        .negativeText("Lowest to Highest")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                if (query_string != null) {
                                    JsonObject query_obj = new JsonParser().parse(VehicleFilterFragment.this.query_string).getAsJsonObject();
                                    String sort_str = "[\n" +
                                            "        {\n" +
                                            "            \"combinedMpg\": {\n" +
                                            "                \"order\": \"desc\",\n" +
                                            "                \"missing\": \"_last\",\n" +
                                            "                \"ignore_unmapped\": true\n" +
                                            "            }\n" +
                                            "        }\n" +
                                            "    ]";
                                    JsonArray sort_obj = new JsonParser().parse (sort_str).getAsJsonArray();
                                    query_obj.add("sort", sort_obj);
                                    new SortTask().execute(query_obj.toString());
                                }
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                JsonObject query_obj = new JsonParser().parse(VehicleFilterFragment.this.query_string).getAsJsonObject();
                                String sort_str = "[\n" +
                                        "        {\n" +
                                        "            \"combinedMpg\": {\n" +
                                        "                \"order\": \"asc\",\n" +
                                        "                \"missing\": \"_last\",\n" +
                                        "                \"ignore_unmapped\": true\n" +
                                        "            }\n" +
                                        "        }\n" +
                                        "    ]";
                                JsonArray sort_obj = new JsonParser().parse (sort_str).getAsJsonArray();
                                query_obj.add("sort", sort_obj);
                                new SortTask().execute(query_obj.toString());
                            }
                        })
                        .show();
            }
        });
        Bundle args = getArguments();
        query_string = args.getString("query_string");
        res_string = args.getString ("res_string");
        if (query_string!=null) {
            new SortTask().execute(query_string);
//            Log.i (TAG, "Executing Sort Task with Query String: " + query_string);
        } else {
            new PopulateListTask().execute(res_string);
//            Log.i (TAG, "Executing Populate Task with Res: " + res_string);
        }
        return v;
    }

    class SortTask extends AsyncTask<String, Void, JestResult> {

        private Exception exception;
        private JestResult result;
        private Search search;


        protected JestResult doInBackground(String... indexName) {
            try {
                DroidClientConfig clientConfig = new DroidClientConfig
                        .Builder("http://429cab6e1c887ea7d28923ebd5a56704-us-east-1.foundcluster.com:9200")
                        .build();
                JestClientFactory factory = new JestClientFactory();
                factory.setDroidClientConfig(clientConfig);
                JestClient jestClient = factory.getObject();
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
                        new PopulateListTask().execute(res_node.toString());
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

    class PopulateListTask extends AsyncTask <String, Void, String> {
        List <Float> mpg_pcts, horsepower_pcts, torque_pcts,
                mileage_pcts, safety_pcts, price_pcts, zerosixty_pcts, insurance_pcts, repair_pcts, depr_pcts;

        private Map<Integer, List<Float>> model_price_map = new HashMap<Integer, List<Float>>();
        private Map<Integer, List<Float>> model_mileage_map = new HashMap<Integer, List<Float>>();
        private float getPct (List<Float> pct_list, float value) {
            float[] arr = {1,6,11,16,21,26,31,36,41,46,51,56,61,66,71,76,81,86,91,96,97,98,99};
            for (int i = 0; i < pct_list.size(); i++) {
                if (value < pct_list.get(i)) {
                    return arr[i];
                }
            }
            return 99;
        }



        protected String doInBackground(String... indexName) {
            try {
                JsonObject res_node = new JsonParser().parse(indexName[0]).getAsJsonObject();
                JsonArray hits = res_node.getAsJsonObject("hits").getAsJsonArray("hits");
                JsonObject aggs = res_node.getAsJsonObject("aggregations");
                JsonArray style_id_agg = aggs.getAsJsonObject("styleIds").getAsJsonArray("buckets");



                Type typeOfHashMap = new TypeToken<Map<String, Float>>() { }.getType();

                mpg_pcts = new ArrayList<Float>();
                horsepower_pcts = new ArrayList<Float>();
                torque_pcts = new ArrayList<Float>();
                mileage_pcts = new ArrayList<Float>();
                safety_pcts = new ArrayList<Float>();
                price_pcts = new ArrayList<Float>();
                zerosixty_pcts = new ArrayList<Float>();
                depr_pcts = new ArrayList<Float>();
                repair_pcts = new ArrayList<Float>();
                insurance_pcts = new ArrayList<Float>();

                JsonObject mpg_elem = aggs.getAsJsonObject("pct_combinedMpg").getAsJsonObject("values"),
                           hp_elem = aggs.getAsJsonObject("pct_hp").getAsJsonObject("values"),
                           torque_elem = aggs.getAsJsonObject("pct_torque").getAsJsonObject("values"),
                           mileage_elem = aggs.getAsJsonObject("pct_mileage").getAsJsonObject("values"),
                           overall_elem = aggs.getAsJsonObject("pct_overall").getAsJsonObject("values"),
                           price_elem = aggs.getAsJsonObject("pct_price").getAsJsonObject("values"),
                           zerosixty_elem = aggs.getAsJsonObject("pct_zerosixty").getAsJsonObject("values"),
                           insurance_elem = aggs.getAsJsonObject("pct_insurance_cost").getAsJsonObject("values"),
                           repair_elem = aggs.getAsJsonObject("pct_repair_cost").getAsJsonObject("values"),
                           depr_elem = aggs.getAsJsonObject("pct_depr_cost").getAsJsonObject("values");



                Map<String, Float> mpg_map = new Gson().fromJson(mpg_elem, typeOfHashMap);
                for (Map.Entry<String, Float> entry : mpg_map.entrySet()) {
                    mpg_pcts.add(entry.getValue());
                }

                mpg_map = new Gson().fromJson(hp_elem, typeOfHashMap);
                for (Map.Entry<String, Float> entry : mpg_map.entrySet()) {
                    horsepower_pcts.add(entry.getValue());
                }
                mpg_map = new Gson().fromJson(torque_elem, typeOfHashMap);
                for (Map.Entry<String, Float> entry : mpg_map.entrySet()) {
                    torque_pcts.add(entry.getValue());
                }
                mpg_map = new Gson().fromJson(mileage_elem, typeOfHashMap);
                for (Map.Entry<String, Float> entry : mpg_map.entrySet()) {
                    mileage_pcts.add(entry.getValue());
                }
                mpg_map = new Gson().fromJson(overall_elem, typeOfHashMap);
                for (Map.Entry<String, Float> entry : mpg_map.entrySet()) {
                    safety_pcts.add(entry.getValue());
                }
                mpg_map = new Gson().fromJson(price_elem, typeOfHashMap);
                for (Map.Entry<String, Float> entry : mpg_map.entrySet()) {
                    price_pcts.add(entry.getValue());
                }
                mpg_map = new Gson().fromJson(zerosixty_elem, typeOfHashMap);
                for (Map.Entry<String, Float> entry : mpg_map.entrySet()) {
                    zerosixty_pcts.add(entry.getValue());
                }
                mpg_map = new Gson().fromJson(depr_elem, typeOfHashMap);
                for (Map.Entry<String, Float> entry : mpg_map.entrySet()) {
                    depr_pcts.add(entry.getValue());
                }
                mpg_map = new Gson().fromJson(repair_elem, typeOfHashMap);
                for (Map.Entry<String, Float> entry : mpg_map.entrySet()) {
                    repair_pcts.add(entry.getValue());
                }
                mpg_map = new Gson().fromJson(insurance_elem, typeOfHashMap);
                for (Map.Entry<String, Float> entry : mpg_map.entrySet()) {
                    insurance_pcts.add(entry.getValue());
                }


                for (JsonElement style_id_elem : style_id_agg) {
                    final JsonObject style_id_obj = style_id_elem.getAsJsonObject();
                    int style_id = style_id_obj.get("key").getAsInt();  /* 20151231 */
                    List<Float> mileage_value = new ArrayList<Float>(),
                                      price_value = new ArrayList<Float>();
                    final JsonObject model_price_percentile_values = style_id_obj.getAsJsonObject("model_price_percentile").getAsJsonObject("values");
                    final JsonObject model_mileage_percentile = style_id_obj.getAsJsonObject("model_mileage_percentile").getAsJsonObject("values");

                    Map<String, Float> price_percentile_map = new Gson().fromJson(model_price_percentile_values, typeOfHashMap);
                    for (Map.Entry<String, Float> entry : price_percentile_map.entrySet()) {
                        price_value.add(entry.getValue());
                    }

                    Map<String, Float> mileage_percentile_map = new Gson().fromJson(model_mileage_percentile, typeOfHashMap);
                    for (Map.Entry<String, Float> entry : mileage_percentile_map.entrySet()) {
                        mileage_value.add (entry.getValue());
                    }
                    model_price_map.put (style_id, price_value);
                    model_mileage_map.put (style_id, mileage_value);
                }
                Gson gson = new Gson();
                final Hit[] hit_list = gson.fromJson (hits, Hit[].class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listingsAdapter.clear();
                        modelSet.clear();
                        for (Hit hit : hit_list) {
                            final ListingV2 listingV2 = hit.get_source();
                            ListingAgg listingAgg = new ListingAgg();


                            listingAgg.setAvg_depreciation(listingV2.getAvg_depreciation());
                            listingAgg.setAvg_fuel_cost(listingV2.getAvg_fuel_cost());
                            listingAgg.setAvg_insurance_cost(listingV2.getAvg_insurance_cost());
                            listingAgg.setAvg_maintenance_cost(listingV2.getAvg_maintenance_cost());
                            listingAgg.setAvg_repairs_cost(listingV2.getAvg_repairs_cost());

                            listingAgg.setZerosixty(listingV2.getZerosixty());
                            listingAgg.setQuartermile(listingV2.getQuartermile());

                            listingAgg.setRating_performance(listingV2.getRating_performance());
                            listingAgg.setRating_build_quality(listingV2.getRating_build_quality());
                            listingAgg.setRating_comfort(listingV2.getRating_comfort());
                            listingAgg.setRating_fun_to_drive(listingV2.getRating_fun_to_drive());
                            listingAgg.setRating_reliability(listingV2.getRating_reliability());

                            listingAgg.setYear(listingV2.getYear());
                            listingAgg.setModel(listingV2.getModel());
                            listingAgg.setMake(listingV2.getMake());
                            listingAgg.setStyleName(listingV2.getStyleName());
                            listingAgg.setStyleId(listingV2.getStyleId());
                            listingAgg.setMileage(listingV2.getMileage());

                            listingAgg.setF34PhotoUrlE(listingV2.getF34PhotoUrlE());
                            listingAgg.setF34PhotoUrlST(listingV2.getF34PhotoUrlST());
                            listingAgg.setF34PhotoUrlT(listingV2.getF34PhotoUrlT());
                            listingAgg.setSmallPhotoUrls(listingV2.getSmallPhotoUrls());
                            listingAgg.setLargePhotoUrls(listingV2.getLargePhotoUrls());

                            listingAgg.setFeatures(listingV2.getFeatures());

                            listingAgg.setAutomaticType(listingV2.getAutomaticType());
                            listingAgg.setDriveTrain(listingV2.getDriveTrain());

                            listingAgg.setCombinedMpg(listingV2.getCombinedMpg());
                            listingAgg.setCityMpg(listingV2.getCityMpg());
                            listingAgg.setHwyMpg(listingV2.getHwyMpg());


                            listingAgg.setHorsepower(listingV2.getHorsepower());
                            listingAgg.setTorque(listingV2.getTorque());
                            listingAgg.setCylinder(listingV2.getCylinder());
                            listingAgg.setEngineType(listingV2.getEngineType());
                            listingAgg.setCompressorType(listingV2.getCompressorType());
                            listingAgg.setEngineType(listingV2.getEngineType());

                            listingAgg.setOverall(listingV2.getOverall());
                            listingAgg.setDealerAddress(listingV2.getDealerAddress());
                            listingAgg.setDealerName(listingV2.getDealerName());
                            listingAgg.setDealerPhone(listingV2.getDealerPhone());
                            listingAgg.setDealerOfferPrice(listingV2.getDealerOfferPrice());

                            listingAgg.setHorsepower(listingV2.getHorsepower());
                            listingAgg.setTorque(listingV2.getTorque());
                            listingAgg.setZerosixty(listingV2.getZerosixty());
                            listingAgg.setQuartermile(listingV2.getQuartermile());


                            listingAgg.setOverall_price(getPct (price_pcts, listingV2.getDealerOfferPrice()));
                            listingAgg.setOverall_mileage(getPct(mileage_pcts, listingV2.getDealerOfferPrice()));
                            listingAgg.setOverall_horsepower(getPct(horsepower_pcts, (float) listingV2.getHorsepower()));
                            listingAgg.setOverall_torque(getPct(torque_pcts, (float) listingV2.getTorque()));
                            listingAgg.setOverall_combined_mpg(getPct(mpg_pcts, (float) listingV2.getCombinedMpg()));

                            if (model_price_map.containsKey(listingV2.getStyleId()))
                                listingAgg.setModel_price_pct(getPct(model_price_map.get(listingV2.getStyleId()), listingV2.getDealerOfferPrice()));
                            if (model_mileage_map.containsKey(listingV2.getStyleId()))
                                listingAgg.setModel_mileage_pct(getPct(model_mileage_map.get(listingV2.getStyleId()), listingV2.getMileage()));

                            listingAgg.setOverall_depr(getPct(depr_pcts, listingV2.getAvg_depreciation()));
                            listingAgg.setOverall_repair(getPct(repair_pcts, (float) listingV2.getAvg_repairs_cost()));
                            listingAgg.setOverall_insurance(getPct(insurance_pcts, (float) listingV2.getAvg_insurance_cost()));


                            if (listingV2.getOverall()!= null) {
                                listingAgg.setOverall_safety(getPct(safety_pcts, Float.parseFloat(listingV2.getOverall())));
                            }
                            if (listingV2.getZerosixty() > 0)
                                listingAgg.setOverall_zerosixty(getPct(zerosixty_pcts, listingV2.getZerosixty()));
                            listingsAdapter.add(listingAgg);



                            modelSet.add (hit.get_source().getMake() + " " + hit.get_source().getModel());
                        }
                        listingsAdapter.notifyDataSetChanged();
                    }
                });
            } catch (Exception e) {
                Log.e (VehicleFilterFragment.class.getSimpleName(), ": err parsing json");
                e.printStackTrace();
            }
            return "done";
        }

        protected void onPostExecute(String feed) {
            Log.i (TAG, "loading task feed: " + feed);
        }

    }

}


