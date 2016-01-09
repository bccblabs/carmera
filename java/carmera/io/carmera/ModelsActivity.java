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

import com.bowyer.app.fabtransitionlayout.FooterLayout;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carmera.io.carmera.adapters.BetterRecyclerAdapter;
import carmera.io.carmera.adapters.ModelsAdapter;
import carmera.io.carmera.comparator.ModelComparator;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.models.queries.ModelQuery;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 12/18/15.
 */
public class ModelsActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {

    private List<ModelQuery> models;
    private ModelsAdapter modelsAdapter;
    private SharedPreferences sharedPreferences;
    @Bind(R.id.makes_recycler) RecyclerView makes_recycler;
    @Bind(R.id.loading_container) public View loading_container;

    @Bind (R.id.sort_filter_search) FloatingActionButton sort_filter_search;
    @Bind (R.id.fab_toolbar) FooterLayout fab_toolbar;

    @Bind (R.id.filter) View filter;
    @Bind (R.id.sort) View sort;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makes_search);
        ButterKnife.bind(this);
        sort.setVisibility(View.GONE);
        filter.setVisibility(View.GONE);
        loading_container.setVisibility(View.GONE);
        makes_recycler.setVisibility(View.VISIBLE);
        makes_recycler.setLayoutManager(new LinearLayoutManager(this));
        models = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_MODELS_INFO));
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        modelsAdapter = new ModelsAdapter();
        modelsAdapter.addAll (models);
        modelsAdapter.sort(new ModelComparator());
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