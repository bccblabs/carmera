package veme.cario.com.CARmera;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import org.apache.lucene.queryparser.xml.builders.TermsFilterBuilder;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;

import java.io.InputStream;

import veme.cario.com.CARmera.fragment.ListingWizard.ListingsSearchInputFragment;
import veme.cario.com.CARmera.fragment.ListingWizard.VehicleFilterFragment;


public class ListingsActivity_v2 extends BaseActivity implements ListingsSearchInputFragment.OnSearchBtnClickedListener, VehicleFilterFragment.OnFilterPressedListener{

    private static String TAG = ListingsActivity_v2.class.getSimpleName();

    @Override
    public void onCreate (Bundle savedBundleInst) {
        super.onCreate(savedBundleInst);

        getLayoutInflater().inflate(R.layout.activity_listings_v2, frame_layout);
        drawer_listview.setItemChecked(drawer_pos, true);
        setTitle("Discarvr");

        Bundle args = getIntent().getExtras();
        try {
            if (args != null && args.getString("vehicle_year") != null) {

                ObjectMapper mapper = new ObjectMapper();
                String agg_query = loadJsonFromAsset();
                JsonNode agg_json = mapper.readTree(agg_query);
                JsonNode aggs = agg_json.path("aggs");
                if (agg_query == null) {
                    throw new Exception("agg_query loading err");
                }


                BoolFilterBuilder boolFilterBuilder = FilterBuilders.boolFilter();
                boolFilterBuilder
                        .must (FilterBuilders.termFilter("make", args.getString("vehicle_make"))
                        )
                        .must (FilterBuilders.termFilter("model", args.getString("vehicle_model"))
                        )
                        .cache(true);

                String mk_md_yr_qsl = "{\n" +
                        "    \"query\": {\n" +
                        "        \"filtered\" : {\n" +
                        "           \"filter\":" +
                        boolFilterBuilder.toString() +
                        "        }\n" +
                        "    },\n" +
                        "   \"aggs\":"  + aggs.toString() +
                        "}";

                Bundle arg = new Bundle();
                arg.putString ("query_string", mk_md_yr_qsl);
                Fragment listings_fragment = new VehicleFilterFragment();
                listings_fragment.setArguments(arg);

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.add (R.id.fragment_container, listings_fragment);
                ft.commit();
            } else {
                Fragment searchfragment = new ListingsSearchInputFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.fragment_container, searchfragment);
                ft.commit();
            }

        } catch (Exception e) {
            Log.i(TAG, "error");
        }

    }

    @Override
    public void OnFilterPressed (String query_string) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment search_result_fragment = new ListingsSearchInputFragment();
        Bundle arg = new Bundle();
        arg.putString ("query_string", query_string);
        search_result_fragment.setArguments(arg);
        ft.replace (R.id.fragment_container, search_result_fragment);
        ft.commit();
    }

    @Override
    public void OnSearchButtonClicked (String str, String query_str) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment search_result_fragment = new VehicleFilterFragment();
        Bundle arg = new Bundle();
        arg.putString ("res_string", str);
        arg.putString ("query_string", query_str);
        search_result_fragment.setArguments(arg);
        ft.replace (R.id.fragment_container, search_result_fragment);
        ft.commit();
    }

    private String loadJsonFromAsset () {
        String json = null;
        try {
            InputStream is = getAssets().open("request.json");
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

}

