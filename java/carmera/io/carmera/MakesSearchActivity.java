package carmera.io.carmera;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.adapters.BetterRecyclerAdapter;
import carmera.io.carmera.adapters.MakesAdapter;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.models.queries.MakeQueries;
import carmera.io.carmera.models.queries.MakeQuery;
import carmera.io.carmera.requests.MakesQueryRequest;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 12/18/15.
 */
public class MakesSearchActivity extends AppCompatActivity {

    private ListingsQuery listingsQuery;
    private String server_address;
    private MakesAdapter makesAdapter;
    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);

    @Bind(R.id.makes_recycler) RecyclerView makes_recycler;
    @Bind(R.id.loading_container) public View loading_container;

    private final class MakesQueryListener implements RequestListener<MakeQueries> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(MakesSearchActivity.this, "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess(MakeQueries result) {
            if (result.makesCount < 1) {
                Toast.makeText(MakesSearchActivity.this, "No cars matching your criteria :( please change your search!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MakesSearchActivity.this, "Found " + result.makesCount + " makes matching your criteria", Toast.LENGTH_SHORT).show();
                loading_container.setVisibility(View.INVISIBLE);
                makes_recycler.setVisibility(View.VISIBLE);
                makesAdapter.addAll(result.makes);
                makesAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makes_search);
        ButterKnife.bind(this);
        server_address = PreferenceManager.getDefaultSharedPreferences(this).getString("pref_key_server_addr", Constants.ServerAddr).trim();

        makes_recycler.setLayoutManager(new LinearLayoutManager(this));
        makesAdapter = new MakesAdapter();
        makesAdapter.setOnItemClickListener(new BetterRecyclerAdapter.OnItemClickListener<MakeQuery>() {
            @Override
            public void onItemClick(View v, MakeQuery item, int position) {
                Intent i = new Intent(MakesSearchActivity.this, ModelsActivity.class);
                Bundle args = new Bundle();
                args.putParcelable(Constants.EXTRA_MODELS_INFO, Parcels.wrap(item.models));
                i.putExtras(args);
                startActivity(i);
            }
        });
        makes_recycler.setAdapter(makesAdapter);
        makes_recycler.setHasFixedSize(false);
    }


    @Override
    public void onStart () {
        super.onStart();
        if (!spiceManager.isStarted())
            spiceManager.start(this);
        listingsQuery = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_LISTING_QUERY));
        if (listingsQuery != null) {
            spiceManager.execute(new MakesQueryRequest(listingsQuery, server_address), new MakesQueryListener());
        } else {
            Toast.makeText(this, "Make Query Null, Wharrrrt THEFUCK!", Toast.LENGTH_SHORT).show();
        }
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
