package carmera.io.carmera.fragments.data_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import carmera.io.carmera.R;
import carmera.io.carmera.cards.StaggeredCardSingleLine;
import carmera.io.carmera.utils.Constants;
import it.gmariotti.cardslib.library.extra.staggeredgrid.internal.CardGridStaggeredArrayAdapter;
import it.gmariotti.cardslib.library.extra.staggeredgrid.view.CardGridStaggeredView;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by bski on 11/23/15.
 */
public class Features extends Fragment {

    public static Features newInstance() {
        return new Features();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.staggered_cards_fragment, container, false);
        ArrayList <Card> cards = new ArrayList<>();
        List<String> comments = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_FEATURES));

        for (int i = 0; i < comments.size(); i++) {
            String txt = comments.get(i);
            StaggeredCardSingleLine StaggeredCardSingleLine = new StaggeredCardSingleLine(getActivity(), txt, R.drawable.card_bgd1);
            cards.add(StaggeredCardSingleLine);
        }
        CardGridStaggeredArrayAdapter cardGridStaggeredArrayAdapter = new CardGridStaggeredArrayAdapter(getActivity(), cards);
        CardGridStaggeredView cardGridStaggeredView = (CardGridStaggeredView) v.findViewById(R.id.data_staggered_grid_view);
        cardGridStaggeredArrayAdapter.notifyDataSetChanged();
        if (cardGridStaggeredView != null) {
            cardGridStaggeredView.setAdapter(cardGridStaggeredArrayAdapter);
        }

        return v;

    }

}
