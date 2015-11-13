package carmera.io.carmera.cards;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import carmera.io.carmera.R;
import carmera.io.carmera.utils.Util;
import it.gmariotti.cardslib.library.internal.CardHeader;

/**
 * Created by bski on 11/12/15.
 */
public class CompositeHeader extends CardHeader {
    protected TextView price_tv, mileage_tv, header_tv;
    protected String text0, text1, title;
    public CompositeHeader (Context cxt, @Nullable String text0, @Nullable String text1, String title) {
        super (cxt, R.layout.listing_info_header);
        this.text0 = text0;
        this.text1 = text1;
        this.title = title;
    }
    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        if (view != null) {
            header_tv = (TextView) view.findViewById(R.id.header_text);
            price_tv = (TextView) parent.findViewById(R.id.listing_price);
            mileage_tv = (TextView) parent.findViewById(R.id.listing_mileage);
            Util.setText(price_tv, this.text0);
            Util.setText(mileage_tv, this.text1);
            Util.setText(header_tv, this.title);
        }
    }
}