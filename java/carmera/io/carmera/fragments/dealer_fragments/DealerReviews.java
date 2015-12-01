package carmera.io.carmera.fragments.dealer_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.cards.CarInfoCard;
import carmera.io.carmera.cards.CompositeHeader;
import carmera.io.carmera.models.car_data_subdocuments.SalesReviews;
import carmera.io.carmera.requests.DealerReviewsRequest;
import carmera.io.carmera.utils.Constants;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * Created by bski on 11/30/15.
 */
public class DealerReviews extends Fragment {

    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);
    private Context cxt;
    public final String TAG = getClass().getCanonicalName();

    @Bind (R.id.cards_recycler) public CardRecyclerView cardRecyclerView ;
    @Bind (R.id.loading_container) public View loadingContainer;

    private final class DealerReviewRequestListener implements
            RequestListener<carmera.io.carmera.models.car_data_subdocuments.DealerReviews> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onRequestSuccess (carmera.io.carmera.models.car_data_subdocuments.DealerReviews result) {
            Log.i (TAG, new Gson().toJson (result));
            try {
                if (DealerReviews.this.isAdded()) {
                    loadingContainer.setVisibility(View.GONE);
                    cardRecyclerView.setVisibility(View.VISIBLE);
                    List<Card> cards = new ArrayList<>();
                    List<SalesReviews> salesReviews = result.salesReviews;

                    if (salesReviews != null) {
                        for (int i = 0; i < salesReviews.size(); i++) {
                            SalesReviews txt = salesReviews.get(i);
                            CompositeHeader hdr = new CompositeHeader(cxt,
                                    String.format("Rating %.1f/5", txt.totalRating),
                                    null, txt.date);

                            if (i % 5 == 0) {
                                CarInfoCard card = new CarInfoCard(cxt, null, txt.title,
                                                                        txt.reviewBody,
                                                                        R.drawable.card_select0);
                                card.addCardHeader(hdr);
                                cards.add(card);
                            } else if (i % 5 == 1) {
                                CarInfoCard card = new CarInfoCard(cxt, null, txt.title,
                                                                        txt.reviewBody,
                                                                        R.drawable.card_select1);
                                card.addCardHeader(hdr);
                                cards.add(card);
                            } else if (i % 5 == 2) {
                                CarInfoCard card = new CarInfoCard(cxt, null, txt.title,
                                                                        txt.reviewBody,
                                                                        R.drawable.card_select2);
                                card.addCardHeader(hdr);
                                cards.add(card);
                            } else if (i % 5 == 3) {
                                CarInfoCard card = new CarInfoCard(cxt, null, txt.title,
                                                                        txt.reviewBody,
                                                                        R.drawable.card_select3);
                                card.addCardHeader(hdr);
                                cards.add(card);
                            } else if (i % 5 == 4) {
                                CarInfoCard card = new CarInfoCard(cxt, null, txt.title,
                                                                        txt.reviewBody,
                                                                        R.drawable.card_select4);
                                card.addCardHeader(hdr);
                                cards.add(card);
                            }
                        }

                    }
                    cardRecyclerView.setAdapter(new CardArrayRecyclerViewAdapter(cxt, cards));

                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    public static DealerReviews newInstance () {
        return new DealerReviews();
    }

    @Override
    public void onCreate (Bundle savedBundle) {
        super.onCreate(savedBundle);
        cxt = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,   @Nullable ViewGroup container,
                                                        @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dealer_reviews, container, false);
        ButterKnife.bind(this, v);
        cxt = getActivity();
        cardRecyclerView.setHasFixedSize(true);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(cxt));
        return v;
    }



    @Override
    public void onStart () {
        super.onStart();
        spiceManager.start(cxt);
        String query_data = getArguments().getString(Constants.EXTRA_DEALERID);
        if (query_data != null) {
            spiceManager.execute(new DealerReviewsRequest(query_data),
                                new DealerReviewRequestListener());
        }
    }



    @Override
    public void onStop () {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }
    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }
}
