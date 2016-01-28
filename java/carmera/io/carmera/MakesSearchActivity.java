package carmera.io.carmera;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carmera.io.carmera.cards.StaggeredImageCard;
import carmera.io.carmera.fragments.search_fragments.FilterFragment;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.models.ParseSavedSearch;
import carmera.io.carmera.models.queries.MakeQueries;
import carmera.io.carmera.models.queries.MakeQuery;
import carmera.io.carmera.requests.MakesQueryRequest;
import carmera.io.carmera.utils.Constants;
import it.gmariotti.cardslib.library.extra.staggeredgrid.internal.CardGridStaggeredArrayAdapter;
import it.gmariotti.cardslib.library.extra.staggeredgrid.view.CardGridStaggeredView;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by bski on 12/18/15.
 */
public class MakesSearchActivity extends AppCompatActivity {

    private String server_address;
    private ListingsQuery listingsQuery;
    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);
    private ArrayList <Card> cards = new ArrayList<>();
    private CardGridStaggeredArrayAdapter cardGridStaggeredArrayAdapter;
    private int matching_models_count = 0;
    private FloatingActionButton favorites;

    @Bind (R.id.makes_title_tv) TextView title_text;

    @Bind (R.id.makes_search_toolbar) Toolbar toolbar;

    @Bind (R.id.fab_toolbar) View fab_toolbar;

    @Bind (R.id.loading_sign) View loading;

    @Bind (R.id.data_staggered_grid_view) View staggered_grid_view;

    @Bind (R.id.emptyview) View emptyView;

    private final class MakesQueryListener implements RequestListener<MakeQueries> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            loading.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle("");
            getSupportActionBar().show();
            title_text.setText("FOUND 0 MAKES");
        }

        @Override
        public void onRequestSuccess(MakeQueries result) {
            loading.setVisibility(View.GONE);
            if (result == null) {
                emptyView.setVisibility(View.VISIBLE);
            }
            else if (result.makesCount < 1) {
                emptyView.setVisibility(View.VISIBLE);
            } else {
                staggered_grid_view.setVisibility(View.VISIBLE);
            }
            cardGridStaggeredArrayAdapter.clear();
            cardGridStaggeredArrayAdapter.notifyDataSetChanged();
            getSupportActionBar().setTitle("");
            getSupportActionBar().show();
            title_text.setText("FOUND " + result.makesCount + " MAKES");
            if (result.makesCount < 1) {

            } else {
                MakesSearchActivity.this.listingsQuery = result.query;
                MakesSearchActivity.this.listingsQuery.car.models = new ArrayList<>();

                for (final MakeQuery make : result.makes) {
                    StaggeredImageCard staggeredImageCard = new StaggeredImageCard(MakesSearchActivity.this,
                            make.make,
                            null,
                            make.numModels + " models",
                            null,
                            make.imageUrl,
                            null);
                    matching_models_count += make.numModels;
                    staggeredImageCard.setOnClickListener(new Card.OnCardClickListener() {
                        @Override
                        public void onClick(Card card, View view) {
                            Intent i = new Intent(MakesSearchActivity.this, ModelsActivity.class);
                            Bundle args = new Bundle();
                            args.putParcelable(Constants.EXTRA_MODELS_INFO, Parcels.wrap(make.models));
                            args.putParcelable(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(listingsQuery));
                            args.putParcelable(Constants.EXTRA_MAKE_STR, Parcels.wrap(make.make));
                            i.putExtras(args);
                            startActivity(i);
                        }
                    });
                    cards.add(staggeredImageCard);
                }
                cardGridStaggeredArrayAdapter.notifyDataSetChanged();
                fab_toolbar.setVisibility(View.VISIBLE);

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makes_grid);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");
            getSupportActionBar().show();
        }

        cardGridStaggeredArrayAdapter = new CardGridStaggeredArrayAdapter(this, this.cards);
        server_address = PreferenceManager.getDefaultSharedPreferences(this).getString("pref_key_server_addr", Constants.ServerAddr).trim();
        listingsQuery = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_LISTING_QUERY));
        CardGridStaggeredView cardGridStaggeredView = (CardGridStaggeredView) findViewById(R.id.data_staggered_grid_view);
        if (cardGridStaggeredView != null) {
            cardGridStaggeredView.setAdapter(cardGridStaggeredArrayAdapter);
        }
    }


    @Override
    public void onStart () {
        super.onStart();
        if (!spiceManager.isStarted())
            spiceManager.start(this);
        if (listingsQuery != null && cardGridStaggeredArrayAdapter.getCount() < 1)
            spiceManager.execute(new MakesQueryRequest(listingsQuery, server_address), new MakesQueryListener());
    }

    @Override
    public void onStop () {
        super.onStop();
    }

    @Override
    public void onDestroy () {
        ButterKnife.unbind(this);
        if (spiceManager.isStarted()) {
            spiceManager.cancelAllRequests();
            spiceManager.shouldStop();
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_makes_models, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorites:
                Intent fav = new Intent(MakesSearchActivity.this, FavoritesActivity.class);
                startActivity(fav);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.save_btn)
    void saveSearch () {
        Toast.makeText(MakesSearchActivity.this, "search saved!", Toast.LENGTH_SHORT).show();
        ParseSavedSearch parseSavedSearch = new ParseSavedSearch();
        parseSavedSearch.setBodyTypes(listingsQuery.car.bodyTypes);
        parseSavedSearch.setCompressors(listingsQuery.car.compressors);
        parseSavedSearch.setConditions(listingsQuery.api.conditions);
        parseSavedSearch.setDrivetrains(listingsQuery.car.drivenWheels);
        parseSavedSearch.setMakes(listingsQuery.car.makes);
        parseSavedSearch.setMatchingModelsCnt(MakesSearchActivity.this.matching_models_count);
        parseSavedSearch.setMaxMileage(Integer.parseInt(listingsQuery.max_mileage));
        parseSavedSearch.setMaxPrice(Integer.parseInt(listingsQuery.max_price));
        parseSavedSearch.setMinHp(listingsQuery.car.minHp);
        parseSavedSearch.setMinMpg(listingsQuery.car.minMpg);
        parseSavedSearch.setMinTq(listingsQuery.car.minTq);
        parseSavedSearch.setModels(listingsQuery.car.main_models);
        parseSavedSearch.setMinYr(listingsQuery.car.minYr);
        parseSavedSearch.setSortBy(listingsQuery.sortBy);
        parseSavedSearch.setTags(listingsQuery.car.tags);
        parseSavedSearch.setZip(listingsQuery.api.zipcode);
        parseSavedSearch.setMinCylinders(listingsQuery.car.minCylinders);
        parseSavedSearch.setUser(ParseUser.getCurrentUser());
        parseSavedSearch.setSavedName(String.format("%d found", listingsQuery.num_matching_models));
        parseSavedSearch.saveInBackground();
    }
}
