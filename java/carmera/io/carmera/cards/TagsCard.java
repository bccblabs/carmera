package carmera.io.carmera.cards;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import carmera.io.carmera.R;
import carmera.io.carmera.utils.Util;
import it.gmariotti.cardslib.library.internal.Card;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by bski on 1/25/16.
 */
public class TagsCard extends Card {
    protected List<String> tags;
    protected int BgdResId;
    protected String desc0, desc1;
    protected TextView desc0_tv, desc1_tv;

    public TagsCard (Context cxt, @Nullable List<String> tags, String desc0, @Nullable String desc1, int background) {
        super (cxt, R.layout.tags_card_content);
        this.tags = tags;
        this.BgdResId = background;
        this.desc0 = desc0;
        this.desc1 = desc1;
    }

    @Override
    public void setupInnerViewElements (ViewGroup parent, View view) {
        if (view == null)
            return;
        this.setBackgroundResourceId(BgdResId);
        TagGroup tagGroup = (TagGroup) view.findViewById(R.id.tags);
        if (this.tags != null && this.tags.size() > 0) {
            if (this.tags.size() < 10)
                tagGroup.setTags(this.tags);
            else {
                int tags_len = this.tags.size();
                this.tags = this.tags.subList(0,10);
                this.tags.add ("... and " + (tags_len - 10) + " more");
                tagGroup.setTags(this.tags);
            }
        } else {
            tagGroup.setVisibility(View.GONE);
        }
        desc0_tv = (TextView) view.findViewById(R.id.desc_line0);
        desc1_tv = (TextView) view.findViewById(R.id.desc_line1);
        Util.setText(desc0_tv, this.desc0);
        Util.setText(desc1_tv, this.desc1);
    }
}
