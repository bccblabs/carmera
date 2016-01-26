package carmera.io.carmera.cards;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFloatSmall;
import com.squareup.picasso.Picasso;

import carmera.io.carmera.ModelsActivity;
import carmera.io.carmera.R;
import carmera.io.carmera.listeners.FavoriteBtnClickListener;
import carmera.io.carmera.models.queries.ModelQuery;
import carmera.io.carmera.utils.Constants;
import carmera.io.carmera.utils.Util;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by bski on 1/13/16.
 */
public class StaggeredImageCard extends Card {
    private Context cxt;
    protected String title_str, subtitle_str, image_url, desc0, desc1;
    protected ImageView image_holder;
    protected TextView title, subtitle, desc_0, desc_1;
    protected ModelQuery modelQuery;
    protected ButtonFloatSmall favorite_btn;
    protected FavoriteBtnClickListener favoriteBtnClickListener;

    public StaggeredImageCard (Context cxt_,
                               @Nullable String title,
                               @Nullable String subtitle,
                               @Nullable String desc_0_str,
                               @Nullable String desc_1_str,
                               @Nullable String imageUrl,
                               @Nullable ModelQuery modelQuery) {

        super (cxt_, R.layout.staggered_card_image);
        this.cxt = cxt_;
        this.title_str = title;
        this.subtitle_str = subtitle;
        this.image_url = imageUrl;
        this.desc0 = desc_0_str;
        this.desc1 = desc_1_str;
        this.modelQuery = modelQuery;
        if (modelQuery != null) {
            favoriteBtnClickListener = (FavoriteBtnClickListener) this.cxt;
        }
    }

    @Override
    public void setupInnerViewElements (ViewGroup parent, View view) {
        title = (TextView) view.findViewById(R.id.staggered_card_title);
        subtitle = (TextView) view.findViewById(R.id.staggered_card_subtitle);
        desc_0 = (TextView) view.findViewById(R.id.staggered_card_desc0);
        desc_1 = (TextView) view.findViewById(R.id.staggered_card_desc1);
        image_holder = (ImageView) view.findViewById(R.id.staggered_image_holder);
        favorite_btn = (ButtonFloatSmall) view.findViewById(R.id.favorite_btn);
        if (this.modelQuery == null)
            favorite_btn.setVisibility(View.GONE);
        else {
            favorite_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    favoriteBtnClickListener.onSaveModelClicked(StaggeredImageCard.this.modelQuery);
                }
            });
        }

        try {
            Picasso.with(cxt)
                    .load (Constants.EdmundsMedia + image_url.replace("_150.", "_175."))
                    .into(image_holder);
        } catch (Exception e) {
            image_holder.setVisibility(View.GONE);
        }
        Util.setText(title, this.title_str);
        Util.setText(subtitle, this.subtitle_str);
        Util.setText(desc_0, this.desc0);
        Util.setText(desc_1, this.desc1);
    }
}