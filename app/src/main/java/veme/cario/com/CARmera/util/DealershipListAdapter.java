package veme.cario.com.CARmera.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.APIModels.VehicleDealership;

/**
 * Created by bski on 11/25/14.
 */
public class DealershipListAdapter extends ArrayAdapter<VehicleDealership> {
    private LayoutInflater inflater;

    public DealershipListAdapter(Context context) {
        super(context, 0);
        inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.list_item_dealership, parent, false);
            // Cache view components into the view holder
            holder = new ViewHolder();
            holder.dealership_item_layout = (LinearLayout) view
                    .findViewById(R.id.dealership_item_layout);
            holder.dealership_name_textview = (TextView) view.findViewById(R.id.dealership_name_textview);
            holder.dealership_contact_textview = (TextView) view.findViewById(R.id.dealership_contact_textview);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final VehicleDealership vehicleDealership = getItem(position);
        TextView dealership_name_tv = holder.dealership_name_textview;
        TextView dealership_contact_tv = holder.dealership_contact_textview;
        dealership_name_tv.setText(vehicleDealership.toString());
        dealership_contact_tv.setText(vehicleDealership.toString());
        return view;
    }

    private static class ViewHolder {
        LinearLayout dealership_item_layout;
        TextView dealership_name_textview;
        TextView dealership_contact_textview;
    }
}
