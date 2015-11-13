package carmera.io.carmera.cards;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import carmera.io.carmera.R;
import carmera.io.carmera.models.car_data_subdocuments.Engine;
import carmera.io.carmera.models.car_data_subdocuments.Transmission;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by bski on 11/12/15.
 */
public class TransmissionCard extends Card {
    protected int BgdResId;
    protected Transmission txn;
    protected String drivetrain;

    public TransmissionCard(Context cxt, Transmission txn, @Nullable String drivetrain, int background) {
        super(cxt, R.layout.transmission_card);
        this.BgdResId = background;
        this.txn = txn;
        this.drivetrain = drivetrain;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        if (view == null)
            return;
        this.setBackgroundResourceId(BgdResId);
        if (txn.numberOfSpeeds != null) {
            TextView txn_speeds = (TextView) view.findViewById(R.id.txn_speeds);
            txn_speeds.setText(txn.numberOfSpeeds);
        } else {
            view.findViewById(R.id.txn_speeds_row).setVisibility(View.GONE);
        }

        if (txn.transmissionType != null) {
            TextView txn_type = (TextView) view.findViewById(R.id.txn_type);
            txn_type.setText(txn.transmissionType);
        } else {
            view.findViewById(R.id.txn_type_row).setVisibility(View.GONE);
        }

        if (this.drivetrain != null) {
            TextView drivetrain_tv = (TextView) view.findViewById(R.id.drivetrain);
            drivetrain_tv.setText(this.drivetrain);
        } else {
            view.findViewById(R.id.drivetrain_row).setVisibility(View.GONE);
        }
    }

}