package carmera.io.carmera.cards;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFlat;
import com.squareup.picasso.Picasso;

import carmera.io.carmera.R;
import carmera.io.carmera.utils.Util;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by bski on 1/13/16.
 */
public class StaggeredImageButtonCard extends Card {
    private Context cxt;
    protected String title_str;
    protected int bgd_res_id;
    protected ImageView image_holder;
    protected TextView title;
    protected ButtonFlat add_to_search_btn;

    public StaggeredImageButtonCard (Context cxt_, @Nullable String title, int BgdResId) {
        super (cxt_, R.layout.card_initial_search_item);
        this.cxt = cxt_;
        this.title_str = title;
        this.bgd_res_id = BgdResId;
    }

    @Override
    public void setupInnerViewElements (ViewGroup parent, View view) {
        title = (TextView) view.findViewById(R.id.staggered_card_title);
        image_holder = (ImageView) view.findViewById(R.id.staggered_image_holder);
        add_to_search_btn = (ButtonFlat) view.findViewById(R.id.add_to_search);

        try {
            Picasso.with(cxt)
                    .load (bgd_res_id)
                    .resizeDimen(R.dimen.ed_img_sm_w, R.dimen.ed_img_sm_h)
                    .into(image_holder);
        } catch (Exception e) {
            image_holder.setVisibility(View.GONE);
        }
        Util.setText(title, this.title_str);
    }


}
