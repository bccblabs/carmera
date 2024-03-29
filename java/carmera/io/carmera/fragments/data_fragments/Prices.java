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
import carmera.io.carmera.utils.Util;
import it.gmariotti.cardslib.library.extra.staggeredgrid.internal.CardGridStaggeredArrayAdapter;
import it.gmariotti.cardslib.library.extra.staggeredgrid.view.CardGridStaggeredView;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by bski on 11/11/15.
 */
public class Prices extends Fragment {

    public static Prices newInstance() {
        return new Prices();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_staggered_cards, container, false);
        carmera.io.carmera.models.car_data_subdocuments.Prices src_data = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_PRICES));
        ArrayList <Card> cards = new ArrayList<>();

        if (src_data.baseMSRP != null && src_data.baseMSRP > 0) {
            StaggeredTwoLinesCard StaggeredTwoLinesCard = new StaggeredTwoLinesCard(getActivity(), getString(R.string.msrp), Util.formatCurrency(src_data.baseMSRP) , R.drawable.card_bgd0);
            cards.add(StaggeredTwoLinesCard);
        }
        if (src_data.usedPrivateParty != null && src_data.usedPrivateParty > 0) {
            StaggeredTwoLinesCard StaggeredTwoLinesCard = new StaggeredTwoLinesCard(getActivity(), getString(R.string.private_party), Util.formatCurrency(src_data.usedPrivateParty) , R.drawable.card_bgd0);
            cards.add(StaggeredTwoLinesCard);
        }
        if (src_data.baseInvoice != null && src_data.baseInvoice > 0) {
            StaggeredTwoLinesCard StaggeredTwoLinesCard = new StaggeredTwoLinesCard(getActivity(), getString(R.string.invoice), Util.formatCurrency(src_data.baseInvoice) , R.drawable.card_bgd0);
            cards.add(StaggeredTwoLinesCard);
        }
        if (src_data.usedTmvRetail != null && src_data.usedTmvRetail > 0) {
            StaggeredTwoLinesCard StaggeredTwoLinesCard = new StaggeredTwoLinesCard(getActivity(), getString(R.string.tmv), Util.formatCurrency(src_data.usedTmvRetail) , R.drawable.card_bgd0);
            cards.add(StaggeredTwoLinesCard);
        }
        if (src_data.usedTradeIn != null && src_data.usedTradeIn > 0) {
            StaggeredTwoLinesCard StaggeredTwoLinesCard = new StaggeredTwoLinesCard(getActivity(), getString(R.string.trade_in), Util.formatCurrency(src_data.usedTradeIn) , R.drawable.card_bgd0);
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
