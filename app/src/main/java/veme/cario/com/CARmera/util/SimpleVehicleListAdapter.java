package veme.cario.com.CARmera.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.parse.ParseImageView;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;

/**
 * Created by bski on 12/10/14.
 */
public class SimpleVehicleListAdapter extends ArrayAdapter<TaggedVehicle> {

    private static String TAG = "Dialog Vehicle Adapter";
    private LayoutInflater inflater;

    private class ViewHolder {
        ParseImageView vehile_image_view;
        TextView vehicle_yr_mk_model_view;
        TextView tag_timestamp_view;
        Button show_listings_btn;
    }

    public SimpleVehicleListAdapter(Context context) {
        super(context, 0);
        inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item_vehicle_simple, parent, false);
            holder = new ViewHolder();
            holder.vehile_image_view = (ParseImageView) view.findViewById(R.id.tagged_vehicle_image_view);
            holder.vehicle_yr_mk_model_view = (TextView) view.findViewById(R.id.tagged_vehicle_yr_mk_model_view);
            holder.tag_timestamp_view = (TextView) view.findViewById(R.id.tagged_vehicle_timestamp_view);
            holder.show_listings_btn = (Button) view.findViewById(R.id.tagged_vehicle_see_listings_btn);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final TaggedVehicle taggedVehicle = getItem(position);
        ParseImageView vehicle_image_view = holder.vehile_image_view;
        TextView vehicle_yr_mk_model_tv = holder.vehicle_yr_mk_model_view;
        TextView tag_ts_tv = holder.tag_timestamp_view;
        Button show_listings_btn = holder.show_listings_btn;

        vehicle_image_view.setParseFile(taggedVehicle.getTagPhoto());
        vehicle_yr_mk_model_tv.setText(taggedVehicle.toString());
        tag_ts_tv.setText(taggedVehicle.getCreatedAt().toString());

        show_listings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* start nearby listings with the tagged vehicle information */
            }
        });
        return view;
    }
}
