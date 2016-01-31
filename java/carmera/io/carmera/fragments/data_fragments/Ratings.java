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
import carmera.io.carmera.utils.Constants;
import it.gmariotti.cardslib.library.extra.staggeredgrid.internal.CardGridStaggeredArrayAdapter;
import it.gmariotti.cardslib.library.extra.staggeredgrid.view.CardGridStaggeredView;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by bski on 11/11/15.
 */
public class Ratings extends Fragment {

    public static Ratings newInstance() {
        return new Ratings();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_staggered_cards, container, false);
        carmera.io.carmera.models.car_data_subdocuments.Ratings src_data = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_RATINGS));
        ArrayList <Card> cards = new ArrayList<>();

        if (src_data.rating_fun_to_drive != null && src_data.rating_fun_to_drive > 0) {
            StaggeredTwoLinesCard StaggeredTwoLinesCard = new StaggeredTwoLinesCard(getActivity(), getString(R.string.rating_fun_to_drive), src_data.rating_fun_to_drive + " / 5", R.drawable.card_bgd0);
            cards.add(StaggeredTwoLinesCard);
        }
        if (src_data.rating_build_quality != null && src_data.rating_build_quality > 0) {
            StaggeredTwoLinesCard StaggeredTwoLinesCard = new StaggeredTwoLinesCard(getActivity(), getString(R.string.rating_build_quality), src_data.rating_build_quality + " / 5", R.drawable.card_bgd0);
            cards.add(StaggeredTwoLinesCard);
        }
        if (src_data.rating_comfort != null && src_data.rating_comfort > 0) {
            StaggeredTwoLinesCard StaggeredTwoLinesCard = new StaggeredTwoLinesCard(getActivity(), getString(R.string.rating_comfort), src_data.rating_comfort + " / 5", R.drawable.card_bgd0);
            cards.add(StaggeredTwoLinesCard);
        }
        if (src_data.rating_performance != null && src_data.rating_performance > 0) {
            StaggeredTwoLinesCard StaggeredTwoLinesCard = new StaggeredTwoLinesCard(getActivity(), getString(R.string.rating_performance), src_data.rating_performance + " / 5", R.drawable.card_bgd0);
            cards.add(StaggeredTwoLinesCard);
        }
        if (src_data.rating_reliability != null && src_data.rating_reliability > 0) {
            StaggeredTwoLinesCard StaggeredTwoLinesCard = new StaggeredTwoLinesCard(getActivity(), getString(R.string.rating_reliability), src_data.rating_reliability + " / 5", R.drawable.card_bgd0);
            cards.add(StaggeredTwoLinesCard);
        }
        if (src_data.rating_int != null && src_data.rating_int > 0) {
            StaggeredTwoLinesCard StaggeredTwoLinesCard = new StaggeredTwoLinesCard(getActivity(), getString(R.string.rating_int), src_data.rating_int + " / 5", R.drawable.card_bgd0);
            cards.add(StaggeredTwoLinesCard);
        }

        if (src_data.rating_ext != null && src_data.rating_ext > 0) {
            StaggeredTwoLinesCard StaggeredTwoLinesCard = new StaggeredTwoLinesCard(getActivity(), getString(R.string.rating_ext), src_data.rating_ext + " / 5", R.drawable.card_bgd0);
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
