package carmera.io.carmera;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;

import org.parceler.Parcels;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.adapters.BetterRecyclerAdapter;
import carmera.io.carmera.adapters.MakesAdapter;
import carmera.io.carmera.adapters.ModelsAdapter;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.models.queries.MakeQuery;
import carmera.io.carmera.models.queries.ModelQuery;
import carmera.io.carmera.requests.MakesQueryRequest;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 12/18/15.
 */
public class ModelsActivity extends AppCompatActivity {
    private String server_address;
    private ModelsAdapter modelsAdapter;
    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);
    private SharedPreferences sharedPreferences;
    @Bind(R.id.makes_recycler) RecyclerView makes_recycler;
    @Bind(R.id.loading_container) public View loading_container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makes_search);
        ButterKnife.bind(this);
        loading_container.setVisibility(View.GONE);
        makes_recycler.setVisibility(View.VISIBLE);
        server_address = PreferenceManager.getDefaultSharedPreferences(this).getString("pref_key_server_addr", Constants.ServerAddr).trim();
        makes_recycler.setLayoutManager(new LinearLayoutManager(this));
        List<ModelQuery> models = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_MODELS_INFO));
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        modelsAdapter = new ModelsAdapter();
        modelsAdapter.addAll (models);
        modelsAdapter.setOnItemClickListener(new BetterRecyclerAdapter.OnItemClickListener<ModelQuery>() {
            @Override
            public void onItemClick(View v, ModelQuery item, int position) {
                Intent i = new Intent(ModelsActivity.this, Base.class);
                Bundle args = new Bundle();
                ListingsQuery listingsQuery = new ListingsQuery();
                listingsQuery.car.remaining_ids = item.styleIds;
                listingsQuery.api.pagenum = 1;
                listingsQuery.api.pagesize = Constants.PAGESIZE_DEFAULT;
                listingsQuery.api.zipcode = sharedPreferences.getString("pref_key_zipcode", Constants.ZIPCODE_DEFAULT).trim();
                listingsQuery.api.radius = sharedPreferences.getString("pref_key_radius", Constants.RADIUS_DEFAULT).trim();
                args.putParcelable(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(listingsQuery));
                i.putExtras(args);
                startActivity(i);
            }
        });
        makes_recycler.setAdapter(modelsAdapter);
        makes_recycler.setHasFixedSize(false);
    }


    @Override
    public void onStart () {
        super.onStart();
        if (!spiceManager.isStarted())
            spiceManager.start(this);
    }

    @Override
    public void onStop () {
        if (spiceManager.isStarted()) {
            spiceManager.cancelAllRequests();
            spiceManager.shouldStop();
        }
        super.onStop();
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
