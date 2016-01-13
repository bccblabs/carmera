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
 * Created by bski on 1/11/16.
 */
public class CarInfoDetailsCard extends Card {

    protected int BgdResId;
    protected String title_str, subtitle_str0;
    protected TextView title, subtitle0;
    protected View card_main_layout;
    public CarInfoDetailsCard (Context cxt, @Nullable String desc0, @Nullable String desc1, int background) {
        super (cxt, R.layout.card_car_info_details);
        this.BgdResId = background;
        this.title_str = desc0;
        this.subtitle_str0 = desc1;
    }

    @Override
    public void setupInnerViewElements (ViewGroup parent, View view) {
        if (view == null)
            return;
        this.setBackgroundResourceId(BgdResId);
        card_main_layout = view.findViewById(R.id.card_main_layout);
        card_main_layout.setBackgroundResource(BgdResId);
        title = (TextView) view.findViewById(R.id.staggered_card_title);
        subtitle0 = (TextView) view.findViewById(R.id.staggered_card_subtitle);
        Util.setText(title, this.title_str);
        Util.setText(subtitle0, this.subtitle_str0);
    }
}
