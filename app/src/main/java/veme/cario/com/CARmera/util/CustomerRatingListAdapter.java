package veme.cario.com.CARmera.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.Json.CustomerRating;


/**
 * Created by bski on 11/25/14.
 */
public class CustomerRatingListAdapter extends ArrayAdapter<CustomerRating> {
    private LayoutInflater inflater;

    public CustomerRatingListAdapter(Context context) {
        super(context, 0);
        inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.list_item_customer_rating, parent, false);
            // Cache view components into the view holder
            holder = new ViewHolder();
            holder.customer_rating_layout = (LinearLayout) view
                    .findViewById(R.id.customer_rating_layout);
            holder.customer_rating_type_textview = (TextView) view.findViewById(R.id.customer_rating_type_textview);
            holder.customer_rating_value_textview = (TextView) view.findViewById(R.id.customer_rating_value_textview);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final CustomerRating customerRating = getItem(position);
        TextView customer_rating_type_tv = holder.customer_rating_type_textview;
        TextView customer_rating_value_tv = holder.customer_rating_value_textview;
        customer_rating_type_tv.setText(customerRating.getType());
        customer_rating_value_tv.setText(customerRating.getValue());
        return view;
    }

    private static class ViewHolder {
        LinearLayout customer_rating_layout;
        TextView customer_rating_type_textview;
        TextView customer_rating_value_textview;
    }

}
