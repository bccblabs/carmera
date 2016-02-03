package carmera.io.carmera.fragments.data_fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import carmera.io.carmera.R;
import carmera.io.carmera.cards.StaggeredSingleLineCard;
import carmera.io.carmera.cards.StaggeredTwoLinesCard;
import carmera.io.carmera.models.queries.ModelQuery;
import carmera.io.carmera.utils.Constants;
import it.gmariotti.cardslib.library.extra.staggeredgrid.internal.CardGridStaggeredArrayAdapter;
import it.gmariotti.cardslib.library.extra.staggeredgrid.view.CardGridStaggeredView;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by bski on 2/2/16.
 */
public class CarHighlightsFragment extends DialogFragment {

    private StaggeredTwoLinesCard staggeredSingleLineCard;

    public static CarHighlightsFragment newInstance () {
        return new CarHighlightsFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_car_highlights, null);
        builder.setView(v);
        ArrayList<Card> cards = new ArrayList<>();
        ModelQuery modelQuery = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRAS_MODEL_HIGHLIGHTS));
        View headerView = getActivity().getLayoutInflater().inflate(R.layout.model_header, null);

        TextView header_txt = (TextView)headerView.findViewById(R.id.model_highlights_header_tv);
        header_txt.setText(modelQuery.getModel());
        builder.setCustomTitle(headerView);

        if (modelQuery.getPowerDesc() != null)
            staggeredSingleLineCard = new StaggeredTwoLinesCard(getActivity(),
                    "Powertrain",
                    modelQuery.getPowerDesc().toLowerCase().replaceAll("hp ", "hp\n\n"), R.drawable.card_bgd0);
        else
            staggeredSingleLineCard = new StaggeredTwoLinesCard(getActivity(),
                    "Powertrain",
                    "N/A Hp\n\nN/A Lb/ft",
                    R.drawable.card_bgd0);
        cards.add (staggeredSingleLineCard);

        if (modelQuery.getMpgDesc() != null)
            staggeredSingleLineCard = new StaggeredTwoLinesCard(getActivity(),
                    "MPG",
                    modelQuery.getMpgDesc()
                    .toLowerCase()
                    .replaceAll (" city / ", "\ncity\n\n").replaceAll("city /", "\ncity\n\n"),
                    R.drawable.card_bgd0);
        else
            staggeredSingleLineCard = new StaggeredTwoLinesCard(getActivity(),
                    "Fuel Economy",
                    "N/A City\n\nN/A HIGHWAY",
                    R.drawable.card_bgd0);
        cards.add (staggeredSingleLineCard);

        if (modelQuery.getRecallCnt() != null)
            staggeredSingleLineCard = new StaggeredTwoLinesCard(getActivity(),
                    "Recalls",
                    modelQuery.getRecallCnt() + " Recalls",
                    R.drawable.card_bgd0);
        else
            staggeredSingleLineCard = new StaggeredTwoLinesCard(getActivity(),
                    "Recalls",
                    "N/A Recalls",
                    R.drawable.card_bgd0);

        cards.add (staggeredSingleLineCard);
        CardGridStaggeredArrayAdapter cardGridStaggeredArrayAdapter = new CardGridStaggeredArrayAdapter(getActivity(), cards);
        CardGridStaggeredView cardGridStaggeredView = (CardGridStaggeredView) v.findViewById(R.id.data_staggered_grid_view);
        cardGridStaggeredArrayAdapter.notifyDataSetChanged();
        if (cardGridStaggeredView != null) {
            cardGridStaggeredView.setAdapter(cardGridStaggeredArrayAdapter);
        }

        ImageView model_image_holder = (ImageView) v.findViewById(R.id.model_image_holder);
        try {
            Picasso.with(getContext()).load(Constants.EdmundsMedia + modelQuery.imageUrl.replace("_150.", "_300.")).centerCrop().into(model_image_holder);
        } catch (Exception e) {
            model_image_holder.setVisibility(View.GONE);
        }
        return builder.create();
    }

}
