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
import carmera.io.carmera.cards.CompositeHeader;
import carmera.io.carmera.cards.EngineCard;
import carmera.io.carmera.cards.PowertrainMetrics;
import carmera.io.carmera.cards.TransmissionCard;
import carmera.io.carmera.models.car_data_subdocuments.DataEntryFloat;
import carmera.io.carmera.models.car_data_subdocuments.Powertrain;
import carmera.io.carmera.utils.Constants;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * Created by bski on 11/11/15.
 */
public class Performance extends Fragment {
    @Bind(R.id.cards_recycler)
    CardRecyclerView cardRecyclerView;

    private Powertrain powertrain;
    private List<Card> cards;
    private CardArrayRecyclerViewAdapter cardArrayRecyclerViewAdapter;
    private Context cxt;

    @Override
    public void onCreate (Bundle savedBundle) {
        super.onCreate(savedBundle);
        powertrain = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_POWERTRAIN));
        cxt = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate (R.layout.cards_recycler, container, false);
        ButterKnife.bind(this, v);
        cardRecyclerView.setHasFixedSize(true);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(cxt));
        cards = new ArrayList<>();

        if (powertrain.engine != null) {
            EngineCard engineCard = new EngineCard(cxt, this.powertrain.engine, R.drawable.card_bgd1);
            CompositeHeader hdr = new CompositeHeader (cxt, this.powertrain.engine.desc,
                                                            null,  "Engine Parameters");
            engineCard.addCardHeader(hdr);
            cards.add(engineCard);
        }
        if (powertrain.transmission != null) {
            TransmissionCard transmissionCard = new TransmissionCard(cxt,
                                                                    this.powertrain.transmission,
                                                                    this.powertrain.drivenWheels,
                                                                    R.drawable.card_bgd2);
            CompositeHeader hdr = new CompositeHeader (cxt, null, null,  "Drivetrain");
            transmissionCard.addCardHeader(hdr);
            cards.add(transmissionCard);
        }

        if ((powertrain.mpg != null && powertrain.mpg.desc != null) || powertrain.zero_sixty != null ||
                powertrain.turning_diameter != null || powertrain.fuel_capacity != null) {
            PowertrainMetrics card = new PowertrainMetrics(cxt, powertrain.mpg.desc,
                    powertrain.zero_sixty, powertrain.fuel_capacity, powertrain.turning_diameter, R.drawable.card_bgd3);
            CompositeHeader hdr = new CompositeHeader (cxt, null, null,  "More Specs");
            card.addCardHeader(hdr);
            cards.add(card);
        }
        cardArrayRecyclerViewAdapter = new CardArrayRecyclerViewAdapter(cxt, cards);
        cardRecyclerView.setAdapter(cardArrayRecyclerViewAdapter);
        return v;

    }
}
