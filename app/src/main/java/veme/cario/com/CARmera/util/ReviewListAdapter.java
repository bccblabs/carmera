package veme.cario.com.CARmera.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.Json.CustomerReview;

/**
 * Created by bski on 11/25/14.
 */
public class ReviewListAdapter extends ArrayAdapter<CustomerReview> {
    private LayoutInflater inflater;
    private static class ViewHolder {
        TextView reviewer_name_textview;
        TextView review_title_textview;
        TextView review_text_textview;
        TextView review_fav_features_textview;
        TextView review_sug_improvements_textview;
    }

    public ReviewListAdapter(Context context) {
        super(context, 0);
        inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.list_item_review, parent, false);
            holder = new ViewHolder();
            holder.reviewer_name_textview = (TextView) view.findViewById(R.id.reviewer_name_textview);
            holder.review_title_textview = (TextView) view.findViewById(R.id.review_title_textview);
            holder.review_text_textview = (TextView) view.findViewById(R.id.review_text_textview);
            holder.review_fav_features_textview = (TextView) view.findViewById(R.id.review_favorite_features);
            holder.review_sug_improvements_textview = (TextView) view.findViewById(R.id.review_suggested_improvements);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final CustomerReview review = getItem(position);
        TextView reviewer_name_tv = holder.reviewer_name_textview;
        TextView review_title_tv = holder.review_title_textview;
        TextView review_text_tv = holder.review_text_textview;
        TextView review_ff_tv = holder.review_fav_features_textview;
        TextView review_si_tv = holder.review_sug_improvements_textview;

        reviewer_name_tv.setText(review.getAuthor().getAuthorName());
        review_title_tv.setText(review.getTitle());
        review_text_tv.setText(review.getText());
        review_ff_tv.setText(review.getFavoriteFeatures());
        review_si_tv.setText(review.getSuggestedImprovements());

        return view;
    }
}
