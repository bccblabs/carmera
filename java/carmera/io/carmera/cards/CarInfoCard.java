package carmera.io.carmera.cards;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import carmera.io.carmera.R;
import carmera.io.carmera.utils.Util;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by bski on 11/12/15.
 */
public class CarInfoCard extends Card {
    protected int BgdResId;
    protected String desc0, desc1;
    protected TextView desc0_tv, desc1_tv;

    public CarInfoCard (Context cxt, String desc0, @Nullable String desc1, int background) {
        super (cxt, R.layout.card_tags_content);
        this.BgdResId = background;
        this.desc0 = desc0;
        this.desc1 = desc1;
    }
    @Override
    public void setupInnerViewElements (ViewGroup parent, View view) {
        if (view == null)
            return;
        this.setBackgroundResourceId(BgdResId);
        desc0_tv = (TextView) view.findViewById(R.id.desc_line0);
        desc1_tv = (TextView) view.findViewById(R.id.desc_line1);
        Util.setText(desc0_tv, this.desc0);
        Util.setText(desc1_tv, this.desc1);
    }
}