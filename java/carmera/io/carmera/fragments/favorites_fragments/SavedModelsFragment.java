package carmera.io.carmera.fragments.favorites_fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.SearchActivity;
import carmera.io.carmera.cards.StaggeredImageCard;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.models.ParseSavedModels;
import carmera.io.carmera.utils.Constants;
import it.gmariotti.cardslib.library.extra.staggeredgrid.internal.CardGridStaggeredArrayAdapter;
import it.gmariotti.cardslib.library.extra.staggeredgrid.view.CardGridStaggeredView;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by bski on 1/25/16.
 */
public class SavedModelsFragment extends Fragment {

    private SharedPreferences sharedPreferences;

    @Bind (R.id.emptyview) View emptyView;

    @Bind(R.id.loading_sign) View loading;

    @Bind(R.id.data_staggered_grid_view) CardGridStaggeredView staggered_grid_view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_models_grid, container, false);
        ButterKnife.bind(this, v);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final ArrayList<Card> cards = new ArrayList<>();
        ParseQuery<ParseSavedModels> query = ParseSavedModels.getQuery();
        query.findInBackground(new FindCallback<ParseSavedModels>() {
            @Override
            public void done(List<ParseSavedModels> list, ParseException e) {
                if (e != null || list.size() < 1) {
                    emptyView.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "0 favorited", Toast.LENGTH_SHORT).show();
                } else {
                    for (final ParseSavedModels model : list) {
                        StaggeredImageCard staggeredImageCard = new StaggeredImageCard(getContext(),
                                model.getYearDesc() + " " + model.getModel(),
                                model.getRecallCnt() + " recalls",
                                model.getPowerDesc(),
                                model.getMpgDesc(),
                                null,
                                null);
                        staggeredImageCard.setOnClickListener(new Card.OnCardClickListener() {
                            @Override
                            public void onClick(Card card, View view) {
                                Intent i = new Intent(getActivity(), SearchActivity.class);
                                Bundle args = new Bundle();
                                ListingsQuery listingsQuery = new ListingsQuery();
                                listingsQuery.car.remaining_ids = model.getStyleIds();
                                listingsQuery.api.pagenum = 1;
                                listingsQuery.api.pagesize = Constants.PAGESIZE_DEFAULT;
                                listingsQuery.api.zipcode = sharedPreferences.getString("pref_key_zipcode", Constants.ZIPCODE_DEFAULT).trim();
                                listingsQuery.api.radius = sharedPreferences.getString("pref_key_radius", Constants.RADIUS_DEFAULT).trim();
                                args.putParcelable(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(listingsQuery));
                                args.putString(Constants.EXTRA_MODEL_NAME, model.getModel());
                                i.putExtras(args);
                                startActivity(i);
                            }
                        });
                        cards.add(staggeredImageCard);
                    }
                    loading.setVisibility(View.GONE);
                    staggered_grid_view.setVisibility(View.VISIBLE);

                    CardGridStaggeredArrayAdapter cardGridStaggeredArrayAdapter = new CardGridStaggeredArrayAdapter(getContext(), cards);
                    Toast.makeText(getContext(), list.size() + " favorited", Toast.LENGTH_SHORT).show();
                    if (staggered_grid_view != null)
                        staggered_grid_view.setAdapter(cardGridStaggeredArrayAdapter);
                    cardGridStaggeredArrayAdapter.notifyDataSetChanged();
                }
            }
        });
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}