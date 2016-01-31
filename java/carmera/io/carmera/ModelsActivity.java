package carmera.io.carmera;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.cards.StaggeredImageCard;
import carmera.io.carmera.listeners.FavoriteBtnClickListener;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.models.ParseSavedModels;
import carmera.io.carmera.models.queries.ModelQuery;
import carmera.io.carmera.utils.Constants;
import it.gmariotti.cardslib.library.extra.staggeredgrid.internal.CardGridStaggeredArrayAdapter;
import it.gmariotti.cardslib.library.extra.staggeredgrid.view.CardGridStaggeredView;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by bski on 12/18/15.
 */
public class ModelsActivity extends AppCompatActivity {

    private List<ModelQuery> models;

    private SharedPreferences sharedPreferences;

    private ListingsQuery listingsQuery;

    @Bind (R.id.makes_title_tv) TextView title_text;

    @Bind (R.id.makes_search_toolbar) Toolbar toolbar;

    @Bind(R.id.loading_sign) View loading;

    @Bind (R.id.data_staggered_grid_view) View staggered_grid_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_models_grid);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        models = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_MODELS_INFO));
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().show();
            String make_string = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_MAKE_STR));
            if (make_string != null) {
                title_text.setText(make_string);
                getSupportActionBar().setTitle("");
                getSupportActionBar().show();
            }
        }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        ArrayList <Card> cards = new ArrayList<>();
        Collections.sort(models, new Comparator<ModelQuery>() {
            @Override
            public int compare(ModelQuery lhs, ModelQuery rhs) {
                if (lhs.getModel().equals(rhs.getModel()))
                    return lhs.yearDesc.compareTo(rhs.yearDesc);
                else
                    return lhs.getModel().compareTo(rhs.getModel());
            }
        });
        for (final ModelQuery model: models) {
            final StaggeredImageCard staggeredImageCard = new StaggeredImageCard(this,
                    model.model,
                    model.yearDesc,
                    null,
                    null,
                    model.imageUrl,
                    model);

            staggeredImageCard.setOnClickListener(new Card.OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Intent i = new Intent(ModelsActivity.this, SearchActivity.class);
                    Bundle args = new Bundle();
                    ListingsQuery listingsQuery = new ListingsQuery();
                    listingsQuery.car.remaining_ids = model.styleIds;
                    listingsQuery.api.pagenum = 1;
                    listingsQuery.api.pagesize = Constants.PAGESIZE_DEFAULT;
                    listingsQuery.api.zipcode = sharedPreferences.getString("pref_key_zipcode", Constants.ZIPCODE_DEFAULT).trim();
                    listingsQuery.api.radius = sharedPreferences.getString("pref_key_radius", Constants.RADIUS_DEFAULT).trim();
                    args.putParcelable(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(listingsQuery));
                    args.putString(Constants.EXTRA_MODEL_NAME, model.model);
                    i.putExtras(args);
                    startActivity(i);
                }
            });
            cards.add(staggeredImageCard);
        }
        loading.setVisibility(View.GONE);
        staggered_grid_view.setVisibility(View.VISIBLE);

        CardGridStaggeredArrayAdapter cardGridStaggeredArrayAdapter = new CardGridStaggeredArrayAdapter(this, cards);
        CardGridStaggeredView cardGridStaggeredView = (CardGridStaggeredView) findViewById(R.id.data_staggered_grid_view);
        cardGridStaggeredArrayAdapter.notifyDataSetChanged();
        if (cardGridStaggeredView != null) {
            cardGridStaggeredView.setAdapter(cardGridStaggeredArrayAdapter);
        }
        listingsQuery = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_LISTING_QUERY));
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
                Intent fav = new Intent(ModelsActivity.this, FavoritesActivity.class);
                startActivity(fav);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
