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

import carmera.io.carmera.R;
import carmera.io.carmera.cards.CarInfoCard;
import carmera.io.carmera.utils.Constants;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * Created by bski on 11/11/15.
 */
public class Comments extends Fragment {

    public static Comments newInstance () {
        return new Comments();
    }

    @Override
    public void onCreate (Bundle savedBundle) {
        super.onCreate(savedBundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cards_recycler, container, false);
        Context cxt = getActivity();
        CardRecyclerView cardRecyclerView = (CardRecyclerView) v.findViewById(R.id.cards_recycler);
        cardRecyclerView.setHasFixedSize(true);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(cxt));
        List<Card> cards = new ArrayList<>();
        List<String> comments = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_REVIEW));
        if (comments == null)
            comments = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_FAV));
        if (comments == null)
            comments = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_IMPR));

        for (int i = 0; i < comments.size(); i++) {
            String txt = comments.get(i);
            if (i % 5 == 0) {
                cards.add(new CarInfoCard(cxt, null, txt, null, R.drawable.card_select0));
            } else if (i % 5 == 1) {
                cards.add(new CarInfoCard(cxt, null, txt, null, R.drawable.card_select1));
            } else if (i % 5 == 2) {
                cards.add(new CarInfoCard(cxt, null, txt, null, R.drawable.card_select2));
            } else if (i % 5 == 3) {
                cards.add(new CarInfoCard(cxt, null, txt, null, R.drawable.card_select3));
            } else if (i % 5 == 4) {
                cards.add(new CarInfoCard(cxt, null, txt, null, R.drawable.card_select4));
            }
        }
        cardRecyclerView.setAdapter(new CardArrayRecyclerViewAdapter(cxt, cards));
        return v;
    }
}
