package carmera.io.carmera.fragments.data_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.cards.CarInfoCard;
import carmera.io.carmera.utils.Constants;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * Created by bski on 11/23/15.
 */
public class Features extends Fragment {
    @Bind(R.id.cards_recycler)
    CardRecyclerView cardRecyclerView;

    private List<String> comments;
    private List<Card> cards;
    private CardArrayRecyclerViewAdapter cardArrayRecyclerViewAdapter;
    private Context cxt;

    @Override
    public void onCreate (Bundle savedBundle) {
        super.onCreate(savedBundle);
        comments = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_FEATURES));
        cxt = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.cards_recycler, container, false);
        ButterKnife.bind(this, v);
        cardRecyclerView.setHasFixedSize(true);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(cxt));
        cards = new ArrayList<>();

        for (int i = 0; i < comments.size(); i++) {
            String txt = comments.get(i);
            if (i % 5 == 0) {
                CarInfoCard carInfoCard = new CarInfoCard(cxt, null, txt, null, R.drawable.card_select0);
                cards.add(carInfoCard);
            } else if (i % 5 == 1) {
                CarInfoCard carInfoCard = new CarInfoCard(cxt, null, txt, null, R.drawable.card_select1);
                cards.add(carInfoCard);
            } else if (i % 5 == 2) {
                CarInfoCard carInfoCard = new CarInfoCard(cxt, null, txt, null, R.drawable.card_select2);
                cards.add(carInfoCard);
            } else if (i % 5 == 3) {
                CarInfoCard carInfoCard = new CarInfoCard(cxt, null, txt, null, R.drawable.card_select3);
                cards.add(carInfoCard);
            } else if (i % 5 == 4) {
                CarInfoCard carInfoCard = new CarInfoCard(cxt, null, txt, null, R.drawable.card_select4);
                cards.add(carInfoCard);
            }
        }

        cardArrayRecyclerViewAdapter = new CardArrayRecyclerViewAdapter(cxt, cards);
        cardRecyclerView.setAdapter(cardArrayRecyclerViewAdapter);
        return v;

    }
}
