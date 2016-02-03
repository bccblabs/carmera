package carmera.io.carmera.cards;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.ButtonFloatSmall;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import carmera.io.carmera.R;
import carmera.io.carmera.fragments.data_fragments.CarHighlightsFragment;
import carmera.io.carmera.fragments.search_fragments.CarFilterFragment;
import carmera.io.carmera.listeners.OnAddModelListener;
import carmera.io.carmera.listeners.OnClickModelInfoListener;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.models.queries.ModelQuery;
import carmera.io.carmera.utils.Constants;
import carmera.io.carmera.utils.Util;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by bski on 1/13/16.
 */
public class StaggeredImageButtonCard extends Card {
    protected String title_str, subtitle_str, image_url, desc0, desc1;
    protected ImageView image_holder;
    protected TextView title, subtitle, desc_0, desc_1;
    protected ModelQuery modelQuery;
    protected ButtonFloatSmall info_btn;
    public OnClickModelInfoListener onClickModelInfoListener;

    private Context cxt;

    public StaggeredImageButtonCard (Context cxt_,
                               @Nullable String title,
                               @Nullable String subtitle,
                               @Nullable String desc_0_str,
                               @Nullable String desc_1_str,
                               @Nullable String imageUrl,
                               @Nullable ModelQuery modelQuery,
                               OnClickModelInfoListener onClickModelInfoListener) {

        super (cxt_, R.layout.card_staggered_image_layout);
        this.cxt = cxt_;
        this.title_str = title;
        this.subtitle_str = subtitle;
        this.image_url = imageUrl;
        this.desc0 = desc_0_str;
        this.desc1 = desc_1_str;
        this.modelQuery = modelQuery;
        this.onClickModelInfoListener = onClickModelInfoListener;
    }

    @Override
    public void setupInnerViewElements (ViewGroup parent, View view) {
        setBackgroundResourceId(R.drawable.card_bgd0);
        title = (TextView) view.findViewById(R.id.staggered_card_title);
        subtitle = (TextView) view.findViewById(R.id.staggered_card_subtitle);
        desc_0 = (TextView) view.findViewById(R.id.staggered_card_desc0);
        desc_1 = (TextView) view.findViewById(R.id.staggered_card_desc1);
        image_holder = (ImageView) view.findViewById(R.id.staggered_image_holder);
        info_btn = (ButtonFloatSmall) view.findViewById(R.id.info_btn);
        try {
            Picasso.with(getContext())
                    .load (Constants.EdmundsMedia + image_url.replace("_150.", "_175."))
                    .into(image_holder);
        } catch (Exception e) {
            image_holder.setVisibility(View.GONE);
        }
        if (modelQuery != null) {
//            info_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String hp = modelQuery.powerDesc.replace("hp ", "hp\n"),
//                        mpg = modelQuery.mpgDesc.replace(" /", "\n");
//
//                    MaterialDialog dialog = new MaterialDialog.Builder(cxt)
//                            .title(modelQuery.model)
//                            .content (String.format("%d recalls\n%s\n%s",
//                                    modelQuery.recallCnt,
//                                    modelQuery.powerDesc,
//                                    modelQuery.mpgDesc))
//                            .positiveText("Got It!")
//                            .show();
//
//                }
//            });
            info_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickModelInfoListener.onClickModelInfo(modelQuery);
                }
            });

        } else {
            info_btn.setVisibility(View.GONE);
        }

        Util.setText(title, this.title_str);
        Util.setText(subtitle, this.subtitle_str);
        Util.setText(desc_0, this.desc0);
        Util.setText(desc_1, this.desc1);
    }
}
