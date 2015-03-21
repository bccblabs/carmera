package veme.cario.com.CARmera.fragment.ListingWizard;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.APIModels.Hit;
import veme.cario.com.CARmera.util.ListingsAdapterV2;

public class VehicleFilterFragment extends WizardStep
        implements FloatingActionsMenu.OnFloatingActionsMenuUpdateListener {

    private static String TAG = VehicleFilterFragment.class.getCanonicalName();
    private JestClient jestClient;

    private ListingsAdapterV2 listingsAdapter;
    private ListView listings_lv;
    private FloatingActionsMenu floatingActionsMenu;

    @ContextVariable
    private String filter_qsl;

    private OnListngV2SelectedListener onlistingselectedcallback;

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
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " has to implement the OnListingSelectedListener interface");
        }
    }




    public interface OnListngV2SelectedListener {
        public void OnListingV2SelectedCallback(Bundle vehicle_agg_info);
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
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        DroidClientConfig clientConfig = new DroidClientConfig
                .Builder("http://429cab6e1c887ea7d28923ebd5a56704-us-east-1.foundcluster.com:9200")
                .build();

        JestClientFactory factory = new JestClientFactory();
        factory.setDroidClientConfig(clientConfig);
        jestClient = factory.getObject();
        new FilterTask().execute(filter_qsl);
    }

    class FilterTask extends AsyncTask<String, Void, JestResult> {

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
                    try {
                        JsonObject res_node = result.getJsonObject();
                        JsonArray hits = res_node.getAsJsonObject("hits").getAsJsonArray("hits");
                        JsonObject aggs = res_node.getAsJsonObject("aggregations");

                        Log.i (VehicleFilterFragment.class.getSimpleName(), "hits: " + hits.toString());

                        Gson gson = new Gson();
                        Hit[] hit_list = gson.fromJson (hits, Hit[].class);
                        Log.i (TAG, "Size of listings: " + hit_list.length);

                        for (Hit hit : hit_list) {
                            Log.i (TAG, "Price: " + hit.get_source().getDealerOfferPrice());
                            listingsAdapter.add (hit.get_source());
                        }
                        listingsAdapter.notifyDataSetChanged();
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


