package carmera.io.carmera.cards;

/**
 * Created by bski on 11/12/15.
 */

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import carmera.io.carmera.R;
import carmera.io.carmera.models.car_data_subdocuments.Engine;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by bski on 11/12/15.
 */
public class EngineCard extends Card {
    protected int BgdResId;
    protected Engine engine;

    public EngineCard (Context cxt, Engine engine, int background) {
        super (cxt, R.layout.engine_card);
        this.BgdResId = background;
        this.engine = engine;
    }

    @Override
    public void setupInnerViewElements (ViewGroup parent, View view) {
        if (view == null)
            return;
        this.setBackgroundResourceId(BgdResId);
        if (engine.displacement != null) {
            TextView displacement  = (TextView) view.findViewById(R.id.displacement);
            displacement.setText(String.format("%.0f", engine.displacement));
        } else {
            view.findViewById(R.id.displacement_row).setVisibility(View.GONE);
        }

        if (engine.compressorType != null) {
            TextView compressor = (TextView) view.findViewById(R.id.compressor);
            compressor.setText(engine.compressorType);
        } else {
            view.findViewById(R.id.compressor_row).setVisibility(View.GONE);
        }

        if (engine.horsepower != null) {
            String desc = String.format("%d", engine.horsepower);
            if (engine.rpm != null && engine.rpm.horsepower != null)
                desc = String.format("%s @ %d rpm", desc, engine.rpm.horsepower);
            TextView hp = (TextView) view.findViewById(R.id.horsepower);
            hp.setText(desc);
        } else {
            view.findViewById(R.id.hp_row).setVisibility(View.GONE);
        }

        if (engine.torque != null) {
            String desc = String.format("%d", engine.torque);
            if (engine.rpm != null && engine.rpm.torque != null)
                desc = String.format("%s @ %d rpm", desc, engine.rpm.torque);
            TextView hp = (TextView) view.findViewById(R.id.torque);
            hp.setText(desc);
        } else {
            view.findViewById(R.id.torque).setVisibility(View.GONE);
        }
        
        if (engine.totalValves != null) {
            TextView valves = (TextView) view.findViewById(R.id.valves);
            valves.setText(String.format("%d", engine.totalValves));
        } else {
            view.findViewById(R.id.valves_row).setVisibility(View.GONE);
        }
    }
}