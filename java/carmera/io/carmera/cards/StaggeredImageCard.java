package carmera.io.carmera.cards;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import carmera.io.carmera.R;
import carmera.io.carmera.utils.Constants;
import carmera.io.carmera.utils.Util;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by bski on 1/13/16.
 */
public class StaggeredImageCard extends Card {
    private Context cxt;
    protected String title_str, subtitle_str, image_url;
    protected ImageView image_holder;
    protected TextView title;
//    , subtitle;

    public StaggeredImageCard (Context cxt_, @Nullable String title, @Nullable String subtitle, String imageUrl) {
        super (cxt_, R.layout.card_initial_search_item);
        this.cxt = cxt_;
        this.title_str = title;
        this.subtitle_str = subtitle;
        this.image_url = imageUrl;
    }

    @Override
    public void setupInnerViewElements (ViewGroup parent, View view) {
        title = (TextView) view.findViewById(R.id.staggered_card_title);
//        subtitle = (TextView) view.findViewById(R.id.staggered_card_subtitle);
        image_holder = (ImageView) view.findViewById(R.id.staggered_image_holder);
        try {
            Picasso.with(cxt)
                    .load (Constants.EdmundsMedia + image_url.replace("_150.", "_175."))
                    .into(image_holder);
        } catch (Exception e) {
            image_holder.setVisibility(View.GONE);
        }

        Util.setText(title, this.title_str + "\n" + this.subtitle_str);
//        Util.setText(subtitle, this.subtitle_str);
    }
}