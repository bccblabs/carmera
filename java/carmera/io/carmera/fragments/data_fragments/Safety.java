package carmera.io.carmera.fragments.data_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import java.util.ArrayList;

import carmera.io.carmera.R;
import carmera.io.carmera.cards.StaggeredTwoLinesCard;
import carmera.io.carmera.models.car_data_subdocuments.CategoryValue;
import carmera.io.carmera.models.car_data_subdocuments.CategoryValuePair;
import carmera.io.carmera.models.car_data_subdocuments.DataEntry;
import carmera.io.carmera.utils.Constants;
import it.gmariotti.cardslib.library.extra.staggeredgrid.internal.CardGridStaggeredArrayAdapter;
import it.gmariotti.cardslib.library.extra.staggeredgrid.view.CardGridStaggeredView;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by bski on 11/11/15.
 */
public class Safety extends Fragment {

    @Override
    public void onCreate (Bundle savedBundle) {
        super.onCreate(savedBundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_staggered_cards, container, false);
        ArrayList <Card> cards = new ArrayList<>();
        carmera.io.carmera.models.car_data_subdocuments.Safety safety = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_SAFETY));

        if (safety.iihs != null && safety.iihs.size() > 0) {
            for (CategoryValuePair entry: safety.iihs) {
                StaggeredTwoLinesCard StaggeredTwoLinesCard = new StaggeredTwoLinesCard(getActivity(), "IIHS " + entry.getCategory(), entry.getValue() , R.drawable.card_bgd0);
                cards.add(StaggeredTwoLinesCard);
            }
        }

        if (safety.nhtsa != null && safety.nhtsa.overall != null) {
            String overall = String.format("%s / 5", safety.nhtsa.overall);
            if (overall != null) {
                StaggeredTwoLinesCard StaggeredTwoLinesCard = new StaggeredTwoLinesCard(getActivity(), "NHTSA Overall Rating", overall , R.drawable.card_bgd0);
                cards.add(StaggeredTwoLinesCard);
            }
        }
        if (safety.nhtsa != null && safety.nhtsa.categories.size() > 0) {
            for (CategoryValue categoryValue : safety.nhtsa.categories) {
                if (categoryValue.options.size() < 1)
                    continue;
                if (categoryValue.overall != null) {
                    StaggeredTwoLinesCard StaggeredTwoLinesCard = new StaggeredTwoLinesCard(getActivity(), "NHTSA " + categoryValue.category , String.format("Overall: %s / 5", categoryValue.overall) , R.drawable.card_bgd0);
                    cards.add(StaggeredTwoLinesCard);
                }
                for (DataEntry entry : categoryValue.options) {
                    if (entry.name != null && entry.value != null) {
                        if (entry.name.toLowerCase().contains("risk of rollover")) {
                            StaggeredTwoLinesCard entryStaggeredTwoLinesCard = new StaggeredTwoLinesCard(getActivity(), "NHTSA " + entry.name , entry.value + " %" , R.drawable.card_bgd0);
                            cards.add(entryStaggeredTwoLinesCard);
                        } else if (entry.value.toLowerCase().contains("not tested") || entry.value.toLowerCase().contains("tip")) {
                            StaggeredTwoLinesCard entryStaggeredTwoLinesCard = new StaggeredTwoLinesCard(getActivity(), "NHTSA " + entry.name , entry.value, R.drawable.card_bgd0);
                            cards.add(entryStaggeredTwoLinesCard);
                        } else {
                            StaggeredTwoLinesCard entryStaggeredTwoLinesCard = new StaggeredTwoLinesCard(getActivity(), "NHTSA " + entry.name , entry.value + " / 5" , R.drawable.card_bgd0);
                            cards.add(entryStaggeredTwoLinesCard);
                        }
                    }
                }
            }
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
