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
import carmera.io.carmera.models.car_data_subdocuments.Incentive;
import carmera.io.carmera.utils.Constants;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * Created by bski on 11/24/15.
 */
public class Incentives extends Fragment {
    @Bind(R.id.cards_recycler)
    CardRecyclerView cardRecyclerView;

    public static Incentives newInstance () {
        return new Incentives();
    }

    @Override
    public void onCreate (Bundle savedBundle) {
        super.onCreate(savedBundle);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.cards_recycler, container, false);
        ButterKnife.bind(this, v);
        Context cxt = getActivity();
        cardRecyclerView.setHasFixedSize(true);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(cxt));
        carmera.io.carmera.models.car_data_subdocuments.Incentives incentives_doc = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_INCENTIVES));
        List<Incentive> incentives = incentives_doc.incentiveHolder;
        List<Card> cards = new ArrayList<>();

        for (int i = 0; i < incentives.size(); i++) {
            Incentive incentive = incentives.get(i);
            String date_range = String.format("Valid From %s to %s", incentive.getStartDate(), incentive.getEndDate());
            String content = "";
            if (incentive.getTermMonths() != null && incentive.getApr() != null) {
                content = String.format ("%d Months at %.1f APR", incentive.termMonths, incentive.apr);
            } else if (incentive.getRebateAmount() != null) {
                content = String.format("$%d Cash Rebate", incentive.rebateAmount);
            } else if (incentive.getMonthlyPayment() != null &&
                        incentive.getRequiredDownPayment() != null &&
                        incentive.getAnnualMileage() != null &&
                        incentive.getTermMonths() != null) {
                content = String.format("$%d Per Month with %d Down\n%d Months Lease, %d Miles / Year",
                        incentive.getMonthlyPayment(), incentive.getRequiredDownPayment(),
                        incentive.getTermMonths(), incentive.getAnnualMileage());
            }
            if (content.length() > 0) {
                CompositeHeader hdr = new CompositeHeader(cxt, null, incentive.name, date_range);
                if (i % 5 == 0) {
                    CarInfoCard carInfoCard = new CarInfoCard(cxt, null, content, incentive.restrictions, R.drawable.card_select0);
                    carInfoCard.addCardHeader(hdr);
                    cards.add(carInfoCard);
                } else if (i % 5 == 1) {
                    CarInfoCard carInfoCard = new CarInfoCard(cxt, null, content, incentive.restrictions, R.drawable.card_select1);
                    carInfoCard.addCardHeader(hdr);
                    cards.add(carInfoCard);
                } else if (i % 5 == 2) {
                    CarInfoCard carInfoCard = new CarInfoCard(cxt, null, content, incentive.restrictions, R.drawable.card_select2);
                    carInfoCard.addCardHeader(hdr);
                    cards.add(carInfoCard);
                } else if (i % 5 == 3) {
                    CarInfoCard carInfoCard = new CarInfoCard(cxt, null, content, incentive.restrictions, R.drawable.card_select3);
                    carInfoCard.addCardHeader(hdr);
                    cards.add(carInfoCard);
                } else if (i % 5 == 4) {
                    CarInfoCard carInfoCard = new CarInfoCard(cxt, null, content, incentive.restrictions, R.drawable.card_select4);
                    carInfoCard.addCardHeader(hdr);
                    cards.add(carInfoCard);
                }
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
