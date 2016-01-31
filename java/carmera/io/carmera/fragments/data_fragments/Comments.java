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

import carmera.io.carmera.R;
import carmera.io.carmera.cards.StaggeredSingleLineCard;
import carmera.io.carmera.utils.Constants;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * Created by bski on 11/11/15.
 */
public class Comments extends Fragment {

    public static Comments newInstance () {
        return new Comments();
    }

    @Override
    public void onCreate (Bundle savedBundle) {
        super.onCreate(savedBundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_cards_recycler, container, false);
        Context cxt = getActivity();
        CardRecyclerView cardRecyclerView = (CardRecyclerView) v.findViewById(R.id.cards_recycler);
        cardRecyclerView.setHasFixedSize(true);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(cxt));
        List<Card> cards = new ArrayList<>();
        List<String> comments = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_REVIEW));
        if (comments == null)
            comments = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_FAV));
        if (comments == null)
            comments = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_IMPR));

        for (int i = 0; i < comments.size(); i++) {
            String txt = comments.get(i);
            cards.add(new StaggeredSingleLineCard(cxt, txt, R.drawable.card_select0));
        }
        cardRecyclerView.setAdapter(new CardArrayRecyclerViewAdapter(cxt, cards));
        return v;
    }
}
