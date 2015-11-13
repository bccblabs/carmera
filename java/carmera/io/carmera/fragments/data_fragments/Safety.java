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
import carmera.io.carmera.cards.IIHSCard;
import carmera.io.carmera.cards.NHTSA;
import carmera.io.carmera.models.car_data_subdocuments.CategoryValue;
import carmera.io.carmera.utils.Constants;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * Created by bski on 11/11/15.
 */
public class Safety extends Fragment {
    @Bind(R.id.cards_recycler) CardRecyclerView cardRecyclerView;

    private carmera.io.carmera.models.car_data_subdocuments.Safety safety;
    private List<Card> cards;
    private CardArrayRecyclerViewAdapter cardArrayRecyclerViewAdapter;
    private Context cxt;


    @Override
    public void onCreate (Bundle savedBundle) {
        super.onCreate(savedBundle);
        safety = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_SAFETY));
        cxt = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cards_recycler, container, false);
        ButterKnife.bind(this, v);
        cardRecyclerView.setHasFixedSize(true);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(cxt));
        cards = new ArrayList<>();

        if (safety.iihs != null && safety.iihs.size() > 0) {
            IIHSCard iihs = new IIHSCard(cxt, null, null, "IIHS Ratings", safety.iihs);
            iihs.init();
            iihs.setBackgroundColorResourceId(R.color.selected_item_color);
            cards.add(iihs);
        }

        if (safety.nhtsa != null && safety.nhtsa.overall != null) {
            String overall = String.format("Overall: %s / 5", safety.nhtsa.overall);
            if (overall != null) {
                CarInfoCard nhtsa_rating = new CarInfoCard(cxt, null, overall, null, R.drawable.card_bgd1);
                cards.add(nhtsa_rating);
            }
        }
        if (safety.nhtsa != null && safety.nhtsa.categories.size() > 0) {
            for (CategoryValue categoryValue : safety.nhtsa.categories) {
                if (categoryValue.options.size() < 1)
                    continue;
                String overall = null;
                if (categoryValue.overall != null)
                    overall = String.format("Overall: %s / 5", categoryValue.overall);
                NHTSA nhtsa = new NHTSA(cxt, null, overall, categoryValue.category, categoryValue.options);
                nhtsa.init();
                nhtsa.setBackgroundColorResourceId(R.color.selected_item_color);
                cards.add(nhtsa);
            }
        }
        if (safety.equipments != null && safety.equipments.size() > 0) {
            CarInfoCard equipments = new CarInfoCard(cxt, safety.equipments, null, null, R.color.selected_item_color);
            CompositeHeader hdr = new CompositeHeader(cxt, null, null, "Equipments");
            equipments.addCardHeader(hdr);
            cards.add(equipments);
        }

        cardArrayRecyclerViewAdapter = new CardArrayRecyclerViewAdapter(cxt, cards);
        cardRecyclerView.setAdapter(cardArrayRecyclerViewAdapter);
        return v;
    }
}
