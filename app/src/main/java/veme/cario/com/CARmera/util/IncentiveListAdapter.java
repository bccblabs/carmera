package veme.cario.com.CARmera.util;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.Json.Incentive;

/**
 * Created by bski on 1/14/15.
 */
public class IncentiveListAdapter extends ArrayAdapter<Incentive> {
    public static class ViewHolder {
         TextView name;
         TextView incentive_duration;
         TextView incentive_details;
         TextView incentive_restrictions;
         LinearLayout header_layout;
         LinearLayout details_layout;
    }

    private LayoutInflater inflater;

    public IncentiveListAdapter (Context context) {
        super(context, 0);
        inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView (int pos, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item_incentive, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.incentive_name);
            holder.incentive_duration = (TextView) view.findViewById(R.id.incentive_duration);
            holder.incentive_details = (TextView) view.findViewById(R.id.incentive_details);
            holder.header_layout = (LinearLayout) view.findViewById(R.id.incentive_header_layout);
            holder.details_layout = (LinearLayout) view.findViewById(R.id.incentive_details_layout);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final Incentive incentive = getItem(pos);
        Typeface fa = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");
        holder.name.setText(incentive.getName());
        holder.name.setTypeface(fa);
        holder.incentive_duration.setText("From " + incentive.getStartDate() + " to " + incentive.getEndDate());
        holder.incentive_duration.setTypeface(fa);
//        holder.incentive_restrictions.setText(incentive.getRestrictions());
//        holder.incentive_restrictions.setTypeface(fa);

        String incentive_type = incentive.getContentType();
        if (incentive_type.equals("CUSTOMER_BONUS_CASH")) {
            holder.incentive_details.setText("$"
                    + incentive.getRebateAmount().intValue()
                    + "Customer Bonus Cash ");
        } else if (incentive_type.equals("CUSTOMER_APR")) {
            holder.incentive_details.setText(
                    incentive.getApr() + "% low APR for "
                    + incentive.getTermMonths() + " months "
                    + "(" + incentive.getCreditRatingTier() + ")"
                    + "Customer Bonus Cash ");

        } else if (incentive_type.equals("CUSTOMER_CASH")) {
            holder.incentive_details.setText("$"
                    + incentive.getRebateAmount().intValue()
                    + "Cash Rebate ");

        }
        holder.incentive_details.setTypeface(fa);

        return view;
    }
}
