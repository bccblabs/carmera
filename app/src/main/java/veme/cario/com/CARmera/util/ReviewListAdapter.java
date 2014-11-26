package veme.cario.com.CARmera.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.Json.CustomerRating;
import veme.cario.com.CARmera.model.Json.Review;

/**
 * Created by bski on 11/25/14.
 */
public class ReviewListAdapter extends ArrayAdapter<Review> {
    private LayoutInflater inflater;
    private CustomerRatingListAdapter customerRatingListAdapter = null;

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
            // Cache view components into the view holder
            holder = new ViewHolder();
            holder.review_item_layout = (LinearLayout) view
                    .findViewById(R.id.review_item_layout);
            holder.reviewer_name_textview = (TextView) view.findViewById(R.id.reviewer_name_textview);
            holder.review_title_textview = (TextView) view.findViewById(R.id.review_title_textview);
            holder.review_text_textview = (TextView) view.findViewById(R.id.review_text_textview);
            holder.customer_rating_listview = (ListView) view.findViewById(R.id.customer_rating_listview);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Review review = getItem(position);
        TextView reviewer_name_tv = holder.reviewer_name_textview;
        TextView review_title_tv = holder.review_title_textview;
        TextView review_text_tv = holder.review_text_textview;
        ListView customer_rating_lv = holder.customer_rating_listview;

        reviewer_name_tv.setText(review.getAuthor().getAuthorName());
        review_title_tv.setText(review.getTitle());
        review_text_tv.setText(review.getText());

        customerRatingListAdapter = new CustomerRatingListAdapter();
        customerRatingListAdapter.clear();
        for (CustomerRating rating : review.getRatings()) {
            customerRatingListAdapter.add(rating);
        }
        customer_rating_lv.setAdapter(customerRatingListAdapter);

        return view;
    }


    private static class ViewHolder {
        LinearLayout review_item_layout;
        TextView reviewer_name_textview;
        TextView review_title_textview;
        TextView review_text_textview;
        ListView customer_rating_listview;
    }
}
