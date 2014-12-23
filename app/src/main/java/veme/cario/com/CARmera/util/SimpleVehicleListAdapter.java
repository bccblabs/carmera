package veme.cario.com.CARmera.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseImageView;

import veme.cario.com.CARmera.NearbyActivity;
import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;

/**
 * Created by bski on 12/10/14.
 */
public class SimpleVehicleListAdapter extends ArrayAdapter<TaggedVehicle> {

    private static String TAG = "Dialog Vehicle Adapter";
    private LayoutInflater inflater;

    private static class ViewHolder {
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
        view = inflater.inflate(R.layout.list_item_vehicle_simple, parent, false);
        holder = new ViewHolder();
        holder.vehile_image_view = (ParseImageView) view.findViewById(R.id.tagged_vehicle_image_view);
        holder.vehicle_yr_mk_model_view = (TextView) view.findViewById(R.id.tagged_vehicle_yr_mk_model_view);
        holder.tag_timestamp_view = (TextView) view.findViewById(R.id.tagged_vehicle_timestamp_view);
        holder.show_listings_btn = (Button) view.findViewById(R.id.tagged_vehicle_see_listings_btn);

        final TaggedVehicle taggedVehicle = getItem(position);

        TextView vehicle_yr_mk_model_tv = holder.vehicle_yr_mk_model_view;
        final TextView tag_ts_tv = holder.tag_timestamp_view;
        Button show_listings_btn = holder.show_listings_btn;

        ParseImageView vehicle_image_view = holder.vehile_image_view;
        vehicle_image_view.setParseFile(taggedVehicle.getTagPhoto());
        vehicle_image_view.loadInBackground();

        vehicle_yr_mk_model_tv.setText(taggedVehicle.toString());
        tag_ts_tv.setText(taggedVehicle.getCreatedAt().toString());
        show_listings_btn.setText("See Listings");
        show_listings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("vehicle_year", taggedVehicle.getYear());
                args.putString("vehicle_make", taggedVehicle.getMake());
                args.putString("vehicle_model", taggedVehicle.getModel());
                Intent intent = new Intent(getContext(), NearbyActivity.class);
                intent.putExtras(args);
                getContext().startActivity(intent);
            }
        });
        return view;
    }
}
