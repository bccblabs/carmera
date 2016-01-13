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

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
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
import carmera.io.carmera.cards.StaggeredCardTwoLines;
import carmera.io.carmera.cards.StaggeredImageCard;
import carmera.io.carmera.comparator.ModelComparator;
import carmera.io.carmera.listeners.OnAddModelListener;
import carmera.io.carmera.listeners.OnSeeModelListingsListener;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.models.queries.ModelQuery;
import carmera.io.carmera.utils.Constants;
import it.gmariotti.cardslib.library.extra.staggeredgrid.internal.CardGridStaggeredArrayAdapter;
import it.gmariotti.cardslib.library.extra.staggeredgrid.view.CardGridStaggeredView;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by bski on 12/18/15.
 */
public class ModelsActivity extends AppCompatActivity implements OnAddModelListener,
                                                                 OnSeeModelListingsListener {

    private List<ModelQuery> models;
    private SharedPreferences sharedPreferences;
    private ListingsQuery listingsQuery;


    @Override
    public void OnSeeModels (List<Integer> styleIds) {
        Intent i = new Intent(ModelsActivity.this, Base.class);
        Bundle args = new Bundle();
        ListingsQuery listingsQuery = new ListingsQuery();
        listingsQuery.car.remaining_ids = styleIds;
        listingsQuery.api.pagenum = 1;
        listingsQuery.api.pagesize = Constants.PAGESIZE_DEFAULT;
        listingsQuery.api.zipcode = sharedPreferences.getString("pref_key_zipcode", Constants.ZIPCODE_DEFAULT).trim();
        listingsQuery.api.radius = sharedPreferences.getString("pref_key_radius", Constants.RADIUS_DEFAULT).trim();
        args.putParcelable(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(listingsQuery));
        i.putExtras(args);
        startActivity(i);
    }

    @Override
    public void onModelAddedCallback (String name) {
        new MaterialDialog.Builder(this)
                .title("Search Filters")
                .content(name + " addeded")
                .theme(Theme.LIGHT)
                .positiveText("OK")
                .show();
        listingsQuery.car.models.add (name);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search_image_grid);
        ButterKnife.bind(this);
        models = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_MODELS_INFO));
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        ArrayList <Card> cards = new ArrayList<>();
        for (final ModelQuery model: models) {
            StaggeredImageCard staggeredImageCard = new StaggeredImageCard(this,model.yearDesc,  model.model, model.imageUrl);
            staggeredImageCard.setOnClickListener(new Card.OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Intent i = new Intent(ModelsActivity.this, Base.class);
                    Bundle args = new Bundle();
                    ListingsQuery listingsQuery = new ListingsQuery();
                    listingsQuery.car.remaining_ids = model.styleIds;
                    listingsQuery.api.pagenum = 1;
                    listingsQuery.api.pagesize = Constants.PAGESIZE_DEFAULT;
                    listingsQuery.api.zipcode = sharedPreferences.getString("pref_key_zipcode", Constants.ZIPCODE_DEFAULT).trim();
                    listingsQuery.api.radius = sharedPreferences.getString("pref_key_radius", Constants.RADIUS_DEFAULT).trim();
                    args.putParcelable(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(listingsQuery));
                    i.putExtras(args);
                    startActivity(i);
                }
            });
            cards.add(staggeredImageCard);
        }
        CardGridStaggeredArrayAdapter cardGridStaggeredArrayAdapter = new CardGridStaggeredArrayAdapter(this, cards);
        CardGridStaggeredView cardGridStaggeredView = (CardGridStaggeredView) findViewById(R.id.data_staggered_grid_view);
        cardGridStaggeredArrayAdapter.notifyDataSetChanged();
        if (cardGridStaggeredView != null) {
            cardGridStaggeredView.setAdapter(cardGridStaggeredArrayAdapter);
        }

//        fab_toolbar.setFab(sort_filter_search);
        listingsQuery = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_LISTING_QUERY));
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
//    @Override
//    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
//        if (scrollState == ScrollState.UP) {
//            fab_toolbar.slideOutFab();
//        } else if (scrollState == ScrollState.DOWN) {
//            fab_toolbar.slideInFab();
//        }
//    }
//
//    @Override
//    public void onScrollChanged(int i, boolean b, boolean b1) {}
//
//    @Override
//    public void onDownMotionEvent() {}
//
//    @OnClick (R.id.sort_filter_search) void show() {
//        fab_toolbar.expandFab();
//    }
}
