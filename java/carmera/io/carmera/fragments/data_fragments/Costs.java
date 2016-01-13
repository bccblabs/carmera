package carmera.io.carmera.fragments.data_fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.parceler.Parcels;

import java.util.ArrayList;

import carmera.io.carmera.CarmeraApp;
import carmera.io.carmera.R;
import carmera.io.carmera.cards.StaggeredCardTwoLines;
import carmera.io.carmera.utils.Constants;
import carmera.io.carmera.utils.Util;
import it.gmariotti.cardslib.library.extra.staggeredgrid.internal.CardGridStaggeredArrayAdapter;
import it.gmariotti.cardslib.library.extra.staggeredgrid.view.CardGridStaggeredView;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by bski on 11/11/15.
 */
public class Costs extends Fragment {

    public static Costs newInstance () {
        return new Costs();
    }

    @Override
    public void onCreate (Bundle savedBundle) {
        super.onCreate(savedBundle);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.staggered_cards_fragment, container, false);
        carmera.io.carmera.models.car_data_subdocuments.Costs src_data = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_COSTS));
        ArrayList <Card> cards = new ArrayList<>();

        if (src_data.depreciation != null && src_data.depreciation > 0) {
            StaggeredCardTwoLines StaggeredCardTwoLines = new StaggeredCardTwoLines(getActivity(), getString(R.string.depreciation), Util.formatCurrency(src_data.depreciation) , R.drawable.card_bgd0);
            cards.add(StaggeredCardTwoLines);
        }
        if (src_data.repairs != null && src_data.repairs > 0) {
            StaggeredCardTwoLines StaggeredCardTwoLines = new StaggeredCardTwoLines(getActivity(), getString(R.string.repairs), Util.formatCurrency(src_data.repairs) , R.drawable.card_bgd0);
            cards.add(StaggeredCardTwoLines);
        }
        if (src_data.maintenance != null && src_data.maintenance > 0) {
            StaggeredCardTwoLines StaggeredCardTwoLines = new StaggeredCardTwoLines(getActivity(), getString(R.string.maintenance), Util.formatCurrency(src_data.maintenance) , R.drawable.card_bgd0);
            cards.add(StaggeredCardTwoLines);
        }
        if (src_data.insurance != null && src_data.insurance > 0) {
            StaggeredCardTwoLines StaggeredCardTwoLines = new StaggeredCardTwoLines(getActivity(), getString(R.string.insurance), Util.formatCurrency(src_data.insurance) , R.drawable.card_bgd0);
            cards.add(StaggeredCardTwoLines);
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
