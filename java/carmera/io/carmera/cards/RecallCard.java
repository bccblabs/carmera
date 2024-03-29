package carmera.io.carmera.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import carmera.io.carmera.R;
import carmera.io.carmera.models.car_data_subdocuments.Recall;
import carmera.io.carmera.utils.Util;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by bski on 11/12/15.
 */
public class RecallCard extends Card {
    protected int BgdResId;
    private Recall recall;

    public RecallCard (Context cxt, Recall recall, int background) {
        super (cxt, R.layout.card_recall_layout);
        this.recall = recall;
        this.BgdResId = background;
    }

    @Override
    public void setupInnerViewElements (ViewGroup parent, View view) {
        if (view == null)
            return;
        this.setBackgroundResourceId(BgdResId);
        if (recall.component != null) {
            TextView tv = (TextView) view.findViewById(R.id.component_name);
            tv.setText(recall.component);
        }
        if (recall.numberOfVehiclesAffected != null) {
            TextView tv = (TextView) view.findViewById(R.id.vehicles_recalled);
            tv.setText(Util.formatCurrency(recall.numberOfVehiclesAffected).replaceAll("\\$", "") + " recalled");
        }
        if (recall.recallNumber != null) {
            TextView tv  = (TextView) view.findViewById(R.id.recall_num);
            tv.setText(recall.recallNumber);
        } else {
            view.findViewById(R.id.recall_num_row).setVisibility(View.GONE);
        }

        if (recall.ownerNotificationDate != null) {
            TextView tv = (TextView) view.findViewById(R.id.notification_date);
            tv.setText(recall.ownerNotificationDate);
        } else {
            view.findViewById(R.id.notification_row).setVisibility(View.GONE);
        }
        if (recall.subcomponent != null) {
            TextView tv = (TextView) view.findViewById(R.id.recall_subcomponent);
            tv.setText(recall.subcomponent);
        } else {
            view.findViewById(R.id.subcomponent_row).setVisibility(View.GONE);
        }
        if (recall.manufacturedFrom != null) {
            TextView tv = (TextView) view.findViewById(R.id.manu_from);
            tv.setText(recall.manufacturedFrom);
        } else {
            view.findViewById(R.id.manu_from_row).setVisibility(View.GONE);
        }

        if (recall.manufacturedTo != null) {
            TextView tv = (TextView) view.findViewById(R.id.manu_to);
            tv.setText(recall.manufacturedTo);
        } else {
            view.findViewById(R.id.manu_to_row).setVisibility(View.GONE);
        }

        if (recall.defectConsequence != null) {
            TextView tv = (TextView) view.findViewById(R.id.defect_consequence);
            tv.setText(Util.joinStrings(recall.defectConsequence, "\n"));
        }

        if (recall.defectDescription != null) {
            TextView tv = (TextView) view.findViewById(R.id.defect_description);
            tv.setText(Util.joinStrings(recall.defectDescription, "\n"));
        }    }
}
