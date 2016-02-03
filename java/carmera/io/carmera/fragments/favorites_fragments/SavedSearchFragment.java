package carmera.io.carmera.fragments.favorites_fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.ocpsoft.pretty.time.PrettyTime;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.activities.SearchActivity;
import carmera.io.carmera.cards.TagsCard;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.models.ParseSavedSearch;
import carmera.io.carmera.utils.Constants;
import carmera.io.carmera.utils.Util;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * Created by bski on 1/22/16.
 */
public class SavedSearchFragment extends Fragment {

    private Context context;

    private CardRecyclerView cardRecyclerView;

    private SharedPreferences sharedPreferences;

    @Bind (R.id.loading) View loading;
    @Bind (R.id.emptyview) View empty;
    @Bind (R.id.cards_recycler) View recycler;

    public static SavedSearchFragment newInstance () {
        return  new SavedSearchFragment();
    }

    @Override
    public void onCreate (Bundle savedBundle) {
        super.onCreate(savedBundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_saved_search, container, false);
        ButterKnife.bind(this, v);
        context = getContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        cardRecyclerView = (CardRecyclerView) v.findViewById(R.id.cards_recycler);
        cardRecyclerView.setHasFixedSize(true);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        ParseQuery<ParseSavedSearch> query = ParseSavedSearch.getQuery();
        query.findInBackground(new FindCallback<ParseSavedSearch>() {
            @Override
            public void done(List<ParseSavedSearch> list, ParseException e) {
                if (e != null) {
                    loading.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "Error Loading Saved Searches, Please Try Again", Toast.LENGTH_SHORT).show();

                } else {
                    List<Card> cards = new ArrayList<>();
                    cardRecyclerView.setAdapter(new CardArrayRecyclerViewAdapter(context, cards));
                    for (final ParseSavedSearch saved_search : list) {
                        List<String> all_tags = new ArrayList<String>();
                        Util.addAllIfNotNull(all_tags, saved_search.getMakes());
                        Util.addAllIfNotNull(all_tags, saved_search.getModels());
                        Util.addAllIfNotNull(all_tags, saved_search.getBodyTypes());
                        Util.addAllIfNotNull(all_tags, saved_search.getConditions());
                        Util.addAllIfNotNull(all_tags, saved_search.getDrivetrains());
                        Util.addAllIfNotNull(all_tags, saved_search.getCompressors());
                        Util.addAllIfNotNull(all_tags, saved_search.getTags());
                        Util.addIfNotZero(all_tags, saved_search.getMinHp(), " HP");
                        Util.addIfNotZero(all_tags, saved_search.getMinTq(), " LB/FT");
                        Util.addIfNotZero(all_tags, saved_search.getMinMpg(), " MPG");
                        Util.addIfNotZero(all_tags, saved_search.getMinCylinders(), " cyl.");

                        if (saved_search.getMaxPrice() != null && saved_search.getMaxPrice() > 0)
                            all_tags.add("less than " + Util.formatCurrency(saved_search.getMaxPrice()));

                        if (saved_search.getMaxMileage() != null && saved_search.getMaxMileage() > 0)
                            all_tags.add("less than " + Util.formatCurrency(saved_search.getMaxMileage()) + " miles");

                        if (saved_search.getMinYr() != null && saved_search.getMinYr() > 0)
                            all_tags.add("newer than " + saved_search.getMinYr());

                        TagsCard saved_search_card = new TagsCard(context,
                                all_tags,
                                String.format("%d matching models", saved_search.getMatchingModelsCnt()),
                                String.format("Searched %s", new PrettyTime().format(saved_search.getCreatedAt())),
                                R.drawable.card_bgd0);

                        saved_search_card.setOnClickListener(new Card.OnCardClickListener() {
                            @Override
                            public void onClick(Card card, View view) {
                                ListingsQuery query = new ListingsQuery();
                                query.car.makes = saved_search.getMakes();
                                query.car.main_models = saved_search.getModels();
                                query.car.bodyTypes = saved_search.getBodyTypes();
                                query.car.compressors = saved_search.getCompressors();
                                query.car.drivenWheels = saved_search.getDrivetrains();
                                query.car.minTq = saved_search.getMinTq();
                                query.car.minHp = saved_search.getMinHp();
                                query.car.minMpg = saved_search.getMinMpg();
                                query.car.minCylinders = saved_search.getMinCylinders();
                                query.car.minYr = saved_search.getMinYr();
                                query.car.minTq = saved_search.getMinTq();
                                query.car.tags = saved_search.getTags();

                                query.api.conditions = saved_search.getConditions();
                                query.api.pagenum = Integer.parseInt(Constants.PAGENUM_DEFAULT);
                                query.api.pagesize = Constants.PAGESIZE_DEFAULT;
                                query.api.zipcode = sharedPreferences.getString("pref_key_zipcode", Constants.ZIPCODE_DEFAULT).trim();
                                query.api.radius = sharedPreferences.getString("pref_key_radius", Constants.RADIUS_DEFAULT).trim();

                                query.sortBy = saved_search.getSortBy();
                                query.max_mileage = Integer.toString(saved_search.getMaxMileage());
                                query.max_price = Integer.toString(saved_search.getMaxPrice());

                                Intent i = new Intent(context, SearchActivity.class);
                                Bundle args = new Bundle();
                                args.putParcelable(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(query));
                                i.putExtras(args);
                                startActivity(i);
                            }
                        });
                        cards.add(saved_search_card);
                        if (loading != null)
                            loading.setVisibility(View.GONE);
                        if (recycler != null)
                            recycler.setVisibility(View.VISIBLE);

                    }
                    cardRecyclerView.setAdapter(new CardArrayRecyclerViewAdapter(context, cards));

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
