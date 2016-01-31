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
import carmera.io.carmera.cards.StaggeredSingleLineCard;
import carmera.io.carmera.utils.Constants;
import it.gmariotti.cardslib.library.extra.staggeredgrid.internal.CardGridStaggeredArrayAdapter;
import it.gmariotti.cardslib.library.extra.staggeredgrid.view.CardGridStaggeredView;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by bski on 11/23/15.
 */
public class Options extends Fragment {

    public static Options newInstance () {
        return new Options();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.fragment_staggered_cards, container, false);
        ArrayList <Card> cards = new ArrayList<>();
        List<String> comments = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_OPTIONS));

        for (int i = 0; i < comments.size(); i++) {
            String txt = comments.get(i);
            StaggeredSingleLineCard staggeredCard = new StaggeredSingleLineCard(getActivity(), txt, R.drawable.card_bgd0);
            cards.add(staggeredCard);
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
