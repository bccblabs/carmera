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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;

import org.codehaus.jackson.JsonNode;
import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.APIModels.Hit;
import veme.cario.com.CARmera.util.ListingsAdapterV2;

public class VehicleFilterFragment extends Fragment
        implements FloatingActionsMenu.OnFloatingActionsMenuUpdateListener {

    private static String TAG = VehicleFilterFragment.class.getCanonicalName();

    private ListingsAdapterV2 listingsAdapter;
    private ListView listings_lv;
    private FloatingActionsMenu floatingActionsMenu;


    private String query_string, res_string;

    private FloatingActionButton filter_btn, zero_sixty_sort, hp_sort, torque_sort, price_sort, mileage_sort, mpg_sort;

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

        torque_sort = (FloatingActionButton) v.findViewById(R.id.torque_sort);
        torque_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .title ("Sort By Torque")
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
        new PopulateListTask().execute(res_string);
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
                        JsonArray hits = res_node.getAsJsonObject("hits").getAsJsonArray("hits");
                        JsonObject aggs = res_node.getAsJsonObject("aggregations");

                        Log.i (TAG, "hits: " + hits.toString());

                        Gson gson = new Gson();
                        final Hit[] hit_list = gson.fromJson (hits, Hit[].class);
                        Log.i (TAG, "Size of listings: " + hit_list.length);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listingsAdapter.clear();
                                for (Hit hit : hit_list) {
                                    Log.i(TAG, "Price: " + hit.get_source().getDealerOfferPrice());
                                    listingsAdapter.add(hit.get_source());
                                }
                                listingsAdapter.notifyDataSetChanged();
                            }
                        });
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

        protected String doInBackground(String... indexName) {
            try {
                JsonObject res_node = new JsonParser().parse(indexName[0]).getAsJsonObject();
                JsonArray hits = res_node.getAsJsonObject("hits").getAsJsonArray("hits");
                JsonObject aggs = res_node.getAsJsonObject("aggregations");
                Gson gson = new Gson();
                final Hit[] hit_list = gson.fromJson (hits, Hit[].class);
                Log.i (TAG, "Size of listings: " + hit_list.length);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listingsAdapter.clear();
                        for (Hit hit : hit_list) {
                            Log.i(TAG, "Price: " + hit.get_source().getDealerOfferPrice());
                            listingsAdapter.add(hit.get_source());
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


