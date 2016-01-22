package carmera.io.carmera;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carmera.io.carmera.cards.StaggeredImageCard;
import carmera.io.carmera.fragments.search_fragments.FilterFragment;
import carmera.io.carmera.listeners.OnResearchListener;
import carmera.io.carmera.models.ListingsQuery;
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
public class MakesSearchActivity extends AppCompatActivity
    implements OnResearchListener {

    private String server_address;
    private ListingsQuery listingsQuery;
    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);
    private ArrayList <Card> cards = new ArrayList<>();
    private CardGridStaggeredArrayAdapter cardGridStaggeredArrayAdapter;

    private FloatingActionButton favorites;

    @Bind (R.id.makes_title_tv) TextView title_text;

    @Bind (R.id.makes_search_toolbar) Toolbar toolbar;

    @Bind (R.id.loading_sign) View loading;

    @Bind (R.id.data_staggered_grid_view) View staggered_grid_view;

    @Bind (R.id.emptyview) View emptyView;

    @Bind (R.id.fab_toolbar) View edit_search_bar;

    @OnClick(R.id.ic_filter)
    public void onFilter () {
        FilterFragment filterFragment = FilterFragment.newInstance();
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class,
                MakesSearchActivity.this.listingsQuery));
        filterFragment.setArguments(args);
        filterFragment.show(getSupportFragmentManager(), "filter_dialog");
    }

    @Override
    public void onResearchCallback (ListingsQuery listingsQuery) {
        Intent i = new Intent(this, MakesSearchActivity.class);
        i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
        startActivityForResult(i, 1);
    }


    private final class MakesQueryListener implements RequestListener<MakeQueries> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            edit_search_bar.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle("");
            getSupportActionBar().show();
            title_text.setText("FOUND 0 MAKES");
            FilterFragment filterFragment = FilterFragment.newInstance();
            Bundle args = new Bundle();
            args.putParcelable(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
            filterFragment.setArguments(args);
            filterFragment.show(getSupportFragmentManager(), "filter_dialog");
        }

        @Override
        public void onRequestSuccess(MakeQueries result) {
            loading.setVisibility(View.GONE);
            edit_search_bar.setVisibility(View.VISIBLE);
            if (result == null || result.makesCount < 1) {
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
                FilterFragment filterFragment = FilterFragment.newInstance();
                Bundle args = new Bundle();
                args.putParcelable(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                filterFragment.setArguments(args);
                filterFragment.show(getSupportFragmentManager(), "filter_dialog");


            } else {
                MakesSearchActivity.this.listingsQuery = result.query;
                MakesSearchActivity.this.listingsQuery.car.models = new ArrayList<>();

                for (final MakeQuery make : result.makes) {
                    StaggeredImageCard staggeredImageCard = new StaggeredImageCard(MakesSearchActivity.this,
                            make.make + "\n\n",
                            make.numModels + " models",
                            make.imageUrl);

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

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makes_models_grid);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");
            getSupportActionBar().show();
        }

        favorites = (FloatingActionButton) findViewById(R.id.fab_favorites_btn);
        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MakesSearchActivity.this, FavoritesActivity.class);
                startActivity(i);
            }
        });


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

}
