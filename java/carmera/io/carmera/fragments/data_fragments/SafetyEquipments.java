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
import carmera.io.carmera.models.car_data_subdocuments.*;
import carmera.io.carmera.utils.Constants;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * Created by bski on 11/23/15.
 */
public class SafetyEquipments extends Fragment {
    @Bind(R.id.cards_recycler)
    CardRecyclerView cardRecyclerView;

    public static SafetyEquipments newInstance() {
        return new SafetyEquipments();
    }

    @Override
    public void onCreate (Bundle savedBundle) {
        super.onCreate(savedBundle);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.cards_recycler, container, false);
        ButterKnife.bind(this, v);
        List<Card> cards = new ArrayList<>();
        Context cxt = getActivity();
        cardRecyclerView.setHasFixedSize(true);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(cxt));
        carmera.io.carmera.models.car_data_subdocuments.Safety safety = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_SAFETY));
        List<String> comments = safety.equipments;
        for (int i = 0; i < comments.size(); i++) {
            String txt = comments.get(i);
            if (i % 5 == 0) {
                cards.add(new CarInfoCard(cxt, null, txt, null, R.drawable.card_select0));
            } else if (i % 5 == 1) {
                cards.add(new CarInfoCard(cxt, null, txt, null, R.drawable.card_select1));
            } else if (i % 5 == 2) {
                cards.add(new CarInfoCard(cxt, null, txt, null, R.drawable.card_select2));
            } else if (i % 5 == 3) {
                cards.add(new CarInfoCard(cxt, null, txt, null, R.drawable.card_select3));
            } else if (i % 5 == 4) {
                cards.add(new CarInfoCard(cxt, null, txt, null, R.drawable.card_select4));
            }
        }
        cardRecyclerView.setAdapter(new CardArrayRecyclerViewAdapter(cxt, cards));
        return v;

    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
