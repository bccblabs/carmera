package carmera.io.carmera;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bowyer.app.fabtransitionlayout.FooterLayout;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carmera.io.carmera.adapters.BetterRecyclerAdapter;
import carmera.io.carmera.adapters.ModelsAdapter;
import carmera.io.carmera.fragments.search_fragments.FilterFragment;
import carmera.io.carmera.listeners.OnResearchListener;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.models.queries.MakeQueries;
import carmera.io.carmera.models.queries.ModelQuery;
import carmera.io.carmera.requests.MakesQueryRequest;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 12/18/15.
 */
public class ModelsActivity extends AppCompatActivity
                            implements ObservableScrollViewCallbacks, OnResearchListener {

    private String server_address;
    private ListingsQuery listingsQuery;
    private List<ModelQuery> models;
    private ModelsAdapter modelsAdapter;
    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);
    private SharedPreferences sharedPreferences;
    @Bind(R.id.makes_recycler) RecyclerView makes_recycler;
    @Bind(R.id.loading_container) public View loading_container;

    @Bind (R.id.sort_filter_search) FloatingActionButton sort_filter_search;
    @Bind (R.id.fab_toolbar) FooterLayout fab_toolbar;

    @Bind (R.id.ic_filter) TextView ic_filter;
    @Bind (R.id.sort) View sort;
    @Bind (R.id.ic_search) TextView ic_search;

    @OnClick(R.id.ic_filter)
    public void onFilter () {
        FilterFragment filterFragment = FilterFragment.newInstance();
        Bundle args = new Bundle();
        if (listingsQuery.car != null) {
            listingsQuery.car.tags = new ArrayList<>();
            listingsQuery.car.remaining_ids = new ArrayList<>();
        }
        args.putParcelable(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
        filterFragment.setArguments(args);
        filterFragment.show(getSupportFragmentManager(), "filter_dialog");
    }

    @OnClick(R.id.ic_search)
    public void onSearch () {
        Intent i = new Intent(ModelsActivity.this, Base.class);
        Bundle args = new Bundle();
        ListingsQuery listingsQuery = new ListingsQuery();
        listingsQuery.car.remaining_ids = new ArrayList<>();
        for (ModelQuery model : models) {
            listingsQuery.car.remaining_ids.addAll(model.styleIds);
        }
        listingsQuery.api.pagenum = 1;
        listingsQuery.api.pagesize = Constants.PAGESIZE_DEFAULT;
        listingsQuery.api.zipcode = sharedPreferences.getString("pref_key_zipcode", Constants.ZIPCODE_DEFAULT).trim();
        listingsQuery.api.radius = sharedPreferences.getString("pref_key_radius", Constants.RADIUS_DEFAULT).trim();
        args.putParcelable(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(listingsQuery));
        i.putExtras(args);
        startActivity(i);
    }

    private final class ModelsQueryListener implements RequestListener<MakeQueries> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(ModelsActivity.this, "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess(MakeQueries result) {
            if (result.makesCount < 1) {
                Toast.makeText(ModelsActivity.this, "No cars matching your criteria :( please change your search!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ModelsActivity.this, "Found " + result.makesCount + " makes matching your criteria", Toast.LENGTH_SHORT).show();
                loading_container.setVisibility(View.INVISIBLE);
                modelsAdapter.clear();
                makes_recycler.setVisibility(View.VISIBLE);

                models = result.makes.get(0).getModels();
                modelsAdapter.addAll(models);
                modelsAdapter.notifyDataSetChanged();
                ModelsActivity.this.listingsQuery = result.query;
            }
        }
    }


    @Override
    public void onResearchCallback (ListingsQuery listingsQuery) {
        modelsAdapter.clear();
        modelsAdapter.notifyDataSetChanged();
        sort_filter_search.setVisibility(View.INVISIBLE);
        loading_container.setVisibility(View.VISIBLE);
        makes_recycler.setVisibility(View.GONE);
        spiceManager.execute(new MakesQueryRequest(listingsQuery, server_address), new ModelsQueryListener());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makes_search);
        ButterKnife.bind(this);
        loading_container.setVisibility(View.GONE);
        makes_recycler.setVisibility(View.VISIBLE);
        sort.setVisibility(View.GONE);

        server_address = PreferenceManager.getDefaultSharedPreferences(this).getString("pref_key_server_addr", Constants.ServerAddr).trim();
        makes_recycler.setLayoutManager(new LinearLayoutManager(this));
        models = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_MODELS_INFO));
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        listingsQuery = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_LISTING_QUERY));
        listingsQuery.car.makes = new ArrayList<>();
        listingsQuery.car.makes.add (getIntent().getStringExtra(Constants.EXTRA_MAKE_STR));
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
        fab_toolbar.setFab(sort_filter_search);
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
    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        if (scrollState == ScrollState.UP) {
            fab_toolbar.slideOutFab();
        } else if (scrollState == ScrollState.DOWN) {
            fab_toolbar.slideInFab();
        }
    }

    @Override
    public void onScrollChanged(int i, boolean b, boolean b1) {}

    @Override
    public void onDownMotionEvent() {}

    @OnClick (R.id.sort_filter_search) void show() {
        fab_toolbar.expandFab();
    }

}
