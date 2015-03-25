package veme.cario.com.CARmera.util;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.Json.Style;

/**
 * Created by bski on 12/12/14.
 */
public class VehicleStylesAdapter extends ArrayAdapter<Style> {

    private LayoutInflater layoutInflater;
    private static class ViewHolder {
        LinearLayout style_item_layout;
        TextView style_name_tv, style_price_tv;
    }
    public VehicleStylesAdapter (Context context) {
        super(context, 0);
        layoutInflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView (int pos, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item_vehicle_style, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.style_item_layout = (LinearLayout) view.findViewById(R.id.style_item_overlay);
            viewHolder.style_name_tv = (TextView) view.findViewById(R.id.vehicle_style_name);
            viewHolder.style_price_tv = (TextView) view.findViewById(R.id.starting_price);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final Style style = getItem(pos);
        final LinearLayout style_overlay = viewHolder.style_item_layout;
        style_overlay.setBackgroundColor(0x4169E1);
        Typeface ar = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");
        viewHolder.style_name_tv.setText(style.getName());
        viewHolder.style_name_tv.setTypeface(ar);
        viewHolder.style_price_tv.setText("New MSRP from $" + style.getPrice().getBaseMSRP());
        viewHolder.style_price_tv.setTypeface(ar);
        return view;
    }
}
