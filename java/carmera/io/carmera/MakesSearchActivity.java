package carmera.io.carmera;

import android.content.Intent;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carmera.io.carmera.adapters.BetterRecyclerAdapter;
import carmera.io.carmera.adapters.MakesAdapter;
import carmera.io.carmera.fragments.search_fragments.FilterFragment;
import carmera.io.carmera.listeners.OnResearchListener;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.models.queries.MakeQueries;
import carmera.io.carmera.models.queries.MakeQuery;
import carmera.io.carmera.requests.MakesQueryRequest;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 12/18/15.
 */
public class MakesSearchActivity extends AppCompatActivity
                                 implements ObservableScrollViewCallbacks, OnResearchListener {

    private ListingsQuery listingsQuery;
    private String server_address;
    private MakesAdapter makesAdapter;
    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);

    @Bind (R.id.makes_recycler) RecyclerView makes_recycler;
    @Bind (R.id.loading_container) public View loading_container;

    @Bind (R.id.sort_filter_search) FloatingActionButton sort_filter_search;
    @Bind (R.id.fab_toolbar) FooterLayout fab_toolbar;
    @Bind (R.id.ic_filter) TextView ic_filter;
    @Bind (R.id.sort) View sort;
    @Bind (R.id.search) View search;


    @OnClick(R.id.ic_filter)
    public void onFilter () {
        FilterFragment filterFragment = FilterFragment.newInstance();
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
        filterFragment.setArguments(args);
        filterFragment.show(getSupportFragmentManager(), "filter_dialog");
    }


    @Override
    public void onResearchCallback (ListingsQuery listingsQuery) {
        makesAdapter.clear();
        makesAdapter.notifyDataSetChanged();
        sort_filter_search.setVisibility(View.INVISIBLE);
        loading_container.setVisibility(View.VISIBLE);
        makes_recycler.setVisibility(View.GONE);
        spiceManager.execute(new MakesQueryRequest(listingsQuery, server_address), new MakesQueryListener());
    }


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
                makesAdapter.clear();
                makes_recycler.setVisibility(View.VISIBLE);
                makesAdapter.addAll(result.makes);
                makesAdapter.notifyDataSetChanged();
                MakesSearchActivity.this.listingsQuery = result.query;
                MakesSearchActivity.this.listingsQuery.car.models = new ArrayList<>();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makes_search);
        ButterKnife.bind(this);
        server_address = PreferenceManager.getDefaultSharedPreferences(this).getString("pref_key_server_addr", Constants.ServerAddr).trim();
        listingsQuery = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_LISTING_QUERY));
        fab_toolbar.setFab(sort_filter_search);
        makes_recycler.setLayoutManager(new LinearLayoutManager(this));
        makesAdapter = new MakesAdapter();
        makesAdapter.setOnItemClickListener(new BetterRecyclerAdapter.OnItemClickListener<MakeQuery>() {
            @Override
            public void onItemClick(View v, MakeQuery item, int position) {
                Intent i = new Intent(MakesSearchActivity.this, ModelsActivity.class);
                Bundle args = new Bundle();
                args.putParcelable(Constants.EXTRA_MODELS_INFO, Parcels.wrap(item.models));
                args.putParcelable(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(listingsQuery));
                args.putParcelable(Constants.EXTRA_MAKE_STR, Parcels.wrap(item.make));
                i.putExtras(args);
                startActivity(i);
            }
        });
        makes_recycler.setAdapter(makesAdapter);
        makes_recycler.setHasFixedSize(false);
        sort.setVisibility(View.GONE);
        search.setVisibility(View.GONE);
    }


    @Override
    public void onStart () {
        super.onStart();
        if (!spiceManager.isStarted())
            spiceManager.start(this);
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
