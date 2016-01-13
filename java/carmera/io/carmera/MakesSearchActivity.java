package carmera.io.carmera;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
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
import carmera.io.carmera.adapters.MakesAdapter;
import carmera.io.carmera.cards.StaggeredImageCard;
import carmera.io.carmera.comparator.MakeComparator;
import carmera.io.carmera.fragments.search_fragments.FilterFragment;
import carmera.io.carmera.listeners.OnMakeSelectedListener;
import carmera.io.carmera.listeners.OnResearchListener;
import carmera.io.carmera.listeners.OnSeeAllModelsListener;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.models.queries.MakeQueries;
import carmera.io.carmera.models.queries.MakeQuery;
import carmera.io.carmera.models.queries.ModelQuery;
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
                MakesSearchActivity.this.listingsQuery = result.query;
                MakesSearchActivity.this.listingsQuery.car.models = new ArrayList<>();

                for (final MakeQuery make : result.makes) {
                    StaggeredImageCard staggeredImageCard = new StaggeredImageCard(MakesSearchActivity.this,
                            make.numModels + " models found",
                            make.make,
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
        setContentView(R.layout.layout_search_image_grid);
        ButterKnife.bind(this);
        cardGridStaggeredArrayAdapter = new CardGridStaggeredArrayAdapter(this, this.cards);
        server_address = PreferenceManager.getDefaultSharedPreferences(this).getString("pref_key_server_addr", Constants.ServerAddr).trim();
        listingsQuery = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_LISTING_QUERY));
        listingsQuery.car.makes = new ArrayList<>();
        listingsQuery.car.years = new ArrayList<>();
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
        if (spiceManager.isStarted()) {
            spiceManager.cancelAllRequests();
            spiceManager.shouldStop();
        }
        super.onStop();
    }

    @Override
    public void onDestroy () {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

}
