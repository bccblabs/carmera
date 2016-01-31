package carmera.io.carmera.fragments.data_fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import java.util.ArrayList;

import carmera.io.carmera.R;
import carmera.io.carmera.cards.StaggeredTwoLinesCard;
import carmera.io.carmera.models.car_data_subdocuments.ComplaintDetails;
import carmera.io.carmera.utils.Constants;
import it.gmariotti.cardslib.library.extra.staggeredgrid.internal.CardGridStaggeredArrayAdapter;
import it.gmariotti.cardslib.library.extra.staggeredgrid.view.CardGridStaggeredView;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by bski on 11/11/15.
 */
public class Complaints extends Fragment {

    public static Complaints newInstance () {
        return new Complaints();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_staggered_cards, container, false);
        carmera.io.carmera.models.car_data_subdocuments.Complaints src_data = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_CMPL));
        ArrayList <Card> cards = new ArrayList<>();

        for (ComplaintDetails entry : src_data.details) {
            String a = entry.component, b = Integer.toString(entry.count) + " reports";
            StaggeredTwoLinesCard StaggeredTwoLinesCard = new StaggeredTwoLinesCard(getActivity(), a, b, R.drawable.card_bgd0);
            cards.add(StaggeredTwoLinesCard);
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
