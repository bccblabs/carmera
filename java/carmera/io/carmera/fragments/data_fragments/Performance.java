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
import carmera.io.carmera.cards.StaggeredCardTwoLines;
import carmera.io.carmera.models.car_data_subdocuments.Powertrain;
import carmera.io.carmera.utils.Constants;
import it.gmariotti.cardslib.library.extra.staggeredgrid.internal.CardGridStaggeredArrayAdapter;
import it.gmariotti.cardslib.library.extra.staggeredgrid.view.CardGridStaggeredView;
import it.gmariotti.cardslib.library.internal.Card;
/**
 * Created by bski on 11/11/15.
 */
public class Performance extends Fragment {

    public static Performance newInstance () {
        return new Performance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate (R.layout.staggered_cards_fragment, container, false);
        ArrayList <Card> cards = new ArrayList<>();

        Powertrain powertrain = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_POWERTRAIN));
        if (powertrain.engine != null) {
            StaggeredCardTwoLines descCard = new StaggeredCardTwoLines(getActivity(), "Engine" ,powertrain.engine.desc , R.drawable.card_bgd0);
            cards.add(descCard);

            if (powertrain.engine.displacement != null) {
                StaggeredCardTwoLines staggeredCardTwoLines = new StaggeredCardTwoLines(getActivity(), "Displacement", String.format("%.0f", powertrain.engine.displacement) , R.drawable.card_bgd0);
                cards.add(staggeredCardTwoLines);
            }
            if (powertrain.engine.compressorType != null) {
                StaggeredCardTwoLines staggeredCardTwoLines = new StaggeredCardTwoLines(getActivity(), "Compressor", powertrain.engine.compressorType, R.drawable.card_bgd0);
                cards.add(staggeredCardTwoLines);
            }
            if (powertrain.engine.horsepower != null) {
                String desc = String.format("%d", powertrain.engine.horsepower);
                if (powertrain.engine.rpm != null && powertrain.engine.rpm.horsepower != null)
                    desc = String.format("%s @ %d rpm", desc, powertrain.engine.rpm.horsepower);
                StaggeredCardTwoLines staggeredCardTwoLines = new StaggeredCardTwoLines(getActivity(), "Horsepower", desc, R.drawable.card_bgd0);
                cards.add(staggeredCardTwoLines);
            }
            if (powertrain.engine.torque != null) {
                String desc = String.format("%d", powertrain.engine.torque);
                if (powertrain.engine.rpm != null && powertrain.engine.rpm.torque != null)
                    desc = String.format("%s @ %d rpm", desc, powertrain.engine.rpm.torque);
                StaggeredCardTwoLines staggeredCardTwoLines = new StaggeredCardTwoLines(getActivity(), "Torque", desc , R.drawable.card_bgd0);
                cards.add(staggeredCardTwoLines);
            }
            if (powertrain.engine.totalValves != null) {
                StaggeredCardTwoLines staggeredCardTwoLines = new StaggeredCardTwoLines(getActivity(), "Values Count", Integer.toString(powertrain.engine.totalValves) , R.drawable.card_bgd0);
                cards.add(staggeredCardTwoLines);
            }

        }
        if (powertrain.transmission != null) {
            if (powertrain.transmission.numberOfSpeeds != null &&
                    powertrain.transmission.transmissionType != null) {
                String txn_desc = String.format("%s-speed %s", powertrain.transmission.numberOfSpeeds, powertrain.transmission.transmissionType);
                StaggeredCardTwoLines staggeredCardTwoLines = new StaggeredCardTwoLines(getActivity(), "Transmission", txn_desc, R.drawable.card_bgd0);
                cards.add(staggeredCardTwoLines);
            }
        }
        if (powertrain.drivenWheels != null) {
            StaggeredCardTwoLines staggeredCardTwoLines = new StaggeredCardTwoLines(getActivity(), "Drivetrain", powertrain.drivenWheels , R.drawable.card_bgd0);
            cards.add(staggeredCardTwoLines);
        }

        if (powertrain.mpg != null) {
            StaggeredCardTwoLines staggeredCardTwoLines = new StaggeredCardTwoLines(getActivity(), "MPG", powertrain.mpg.desc , R.drawable.card_bgd0);
            cards.add(staggeredCardTwoLines);
        }
        if (powertrain.zero_sixty != null) {
            StaggeredCardTwoLines staggeredCardTwoLines = new StaggeredCardTwoLines(getActivity(), "Zero-To-Sixty", String.format("%.1f", powertrain.zero_sixty) , R.drawable.card_bgd0);
            cards.add(staggeredCardTwoLines);
        }
        if (powertrain.fuel_capacity != null) {
            StaggeredCardTwoLines staggeredCardTwoLines = new StaggeredCardTwoLines(getActivity(), "Fuel Capacity", String.format("%.1f", powertrain.fuel_capacity) , R.drawable.card_bgd0);
            cards.add(staggeredCardTwoLines);
        }
        if (powertrain.turning_diameter != null) {
            StaggeredCardTwoLines staggeredCardTwoLines = new StaggeredCardTwoLines(getActivity(), "Turning Diameter", String.format("%.1f", powertrain.turning_diameter) , R.drawable.card_bgd0);
            cards.add(staggeredCardTwoLines);
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
