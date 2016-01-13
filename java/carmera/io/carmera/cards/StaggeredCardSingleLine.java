package carmera.io.carmera.cards;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import carmera.io.carmera.R;
import carmera.io.carmera.utils.Util;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by bski on 1/12/16.
 */
public class StaggeredCardSingleLine extends Card {
    protected int BgdResId;
    protected String title_str;
    protected TextView title;
    protected View card_main_layout;

    public StaggeredCardSingleLine(Context cxt, @Nullable String title, int background) {
        super(cxt, R.layout.staggered_card_single_line);
        this.BgdResId = background;
        this.title_str = title;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        title = (TextView) view.findViewById(R.id.staggered_card_title);
        card_main_layout = view.findViewById(R.id.card_main_layout);
        card_main_layout.setBackgroundResource(BgdResId);
        Util.setText(title, this.title_str);
    }
}