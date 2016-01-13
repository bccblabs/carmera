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

import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.cards.CompositeHeader;
import carmera.io.carmera.cards.RecallCard;
import carmera.io.carmera.models.car_data_subdocuments.Recall;
import carmera.io.carmera.utils.Constants;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * Created by bski on 11/11/15.
 */
public class Recalls extends Fragment {

    public static Recalls newInstance () {
        return new Recalls();
    }

    @Override
    public void onCreate (Bundle savedBundle) {
        super.onCreate(savedBundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.cards_recycler, container, false);
        ButterKnife.bind(this, v);
        CardRecyclerView cardRecyclerView = (CardRecyclerView) v.findViewById(R.id.cards_recycler);
        cardRecyclerView.setHasFixedSize(true);

        Context cxt = getActivity();
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(cxt));
        List<Card> cards = new ArrayList<>();
        carmera.io.carmera.models.car_data_subdocuments.Recalls recalls = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_RECALLS));
        List<Recall> recalls_list = recalls.recalls;

        if (recalls_list != null) {
            for (int i = 0; i < recalls_list.size(); i++) {
                RecallCard recallCard = new RecallCard(cxt, recalls_list.get(i), R.drawable.card_select0);
                CompositeHeader hdr = new CompositeHeader (cxt,
                        recalls_list.get(i).component + " recall",
                        recalls_list.get(i).numberOfVehiclesAffected + " cars affected",
                        null);
                recallCard.addCardHeader(hdr);
                cards.add(recallCard);
            }
            cardRecyclerView.setAdapter(new CardArrayRecyclerViewAdapter(cxt, cards));
        }
        return v;
    }

}
