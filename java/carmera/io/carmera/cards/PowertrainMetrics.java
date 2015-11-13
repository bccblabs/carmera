package carmera.io.carmera.cards;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import carmera.io.carmera.R;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by bski on 11/12/15.
 */
public class PowertrainMetrics extends Card {
    protected int BgdResId;
    protected String mpg;
    protected Float zero_sixty, fuel_capacity, turning_diameter;

    public PowertrainMetrics(Context cxt, String mpg, Float zs, Float fc, Float td, @DrawableRes int background) {
        super(cxt, R.layout.metrics_card);
        this.BgdResId = background;
        this.mpg = mpg;
        this.zero_sixty = zs;
        this.fuel_capacity = fc;
        this.turning_diameter = td;
    }


    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        if (view == null)
            return;
        this.setBackgroundResourceId(BgdResId);
        if (this.mpg != null) {
            TextView mpg = (TextView) view.findViewById(R.id.mpg);
            mpg.setText(this.mpg);
        } else {
            view.findViewById(R.id.mpg_row).setVisibility(View.GONE);
        }

        if (this.zero_sixty != null) {
            TextView zs = (TextView) view.findViewById(R.id.zero_sixty);
            zs.setText(String.format("%.1f", zero_sixty));
        } else {
            view.findViewById(R.id.zs_row).setVisibility(View.GONE);
        }

        if (this.fuel_capacity != null) {
            TextView fc = (TextView) view.findViewById(R.id.fuel_capacity);
            fc.setText(String.format("%.1f", fuel_capacity));
        } else {
            view.findViewById(R.id.fc_row).setVisibility(View.GONE);
        }

        if (this.turning_diameter != null) {
            TextView td = (TextView) view.findViewById(R.id.turning_diameter);
            td.setText(String.format("%.1f", turning_diameter));
        } else {
            view.findViewById(R.id.td_row).setVisibility(View.GONE);
        }
    }

}
