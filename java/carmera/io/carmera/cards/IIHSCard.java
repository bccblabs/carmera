package carmera.io.carmera.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import carmera.io.carmera.R;
import carmera.io.carmera.models.car_data_subdocuments.CategoryValuePair;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.prototypes.CardWithList;

/**
 * Created by bski on 11/13/15.
 */
public class IIHSCard extends CardWithList {

    private String text0, text1, title;
    private List<CategoryValuePair> values;
    private Context context;
    public IIHSCard (Context cxt, String txt0, String txt1, String title,
                     List<CategoryValuePair> values) {
        super (cxt);
        this.context = cxt;
        this.text0 = txt0;
        this.text1 = txt1;
        this.title = title;
        this.values = values;
    }

    @Override
    protected CardHeader initCardHeader() {
        CompositeHeader hdr = new CompositeHeader (context, text0, text1, title);
        return hdr;
    }

    @Override
    protected void initCard() {
        setSwipeable(false);
    }

    @Override
    protected List<ListObject> initChildren() {
        if (values.size() < 1) return null;
        List<ListObject> items = new ArrayList<ListObject>();
        for (CategoryValuePair kv : values) {
            items.add (new CatetoryRating(this, kv.category, kv.value));
        }
        return items;
    }

    @Override
    public int getChildLayoutId () {
        return R.layout.safety_rating_item;
    }

    @Override
    public View setupChildView(int childPosition, ListObject object, View convertView, ViewGroup parent) {

        TextView key = (TextView) convertView.findViewById(R.id.key);
        TextView value = (TextView) convertView.findViewById(R.id.value);

        CatetoryRating catetoryRating= (CatetoryRating) object;
        key.setText(catetoryRating.name);
        value.setText(catetoryRating.value);
        return  convertView;
    }

    public class CatetoryRating extends DefaultListObject {
        public String name;
        public String value;
        public CatetoryRating (Card parentCard, String name, String value) {
            super (parentCard);
            this.name = name;
            this.value = value;
        }
    }
}
