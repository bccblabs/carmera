package veme.cario.com.CARmera.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.parse.ParseImageView;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;

public class VehicleGridAdapter extends ArrayAdapter<TaggedVehicle> {
    private LayoutInflater inflater;
    private boolean is_compact;


    private static class ViewHolder {
        ParseImageView photo;
    }
    public VehicleGridAdapter (Context cxt, boolean compact) {
        super(cxt, 0);
        is_compact = compact;
        inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            if (is_compact) {
                view = inflater.inflate(R.layout.grid_item_small, parent, false);
                holder.photo = (ParseImageView) view.findViewById(R.id.vehicle_image_grid_item_compact);
            } else {
                view = inflater.inflate(R.layout.grid_item, parent, false);
                holder.photo = (ParseImageView) view.findViewById(R.id.vehicle_image_grid_item);
            }
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }


        TaggedVehicle vehicle = getItem(position);
        holder.photo.setParseFile(vehicle.getThumbnail());
        holder.photo.loadInBackground();
        return view;
    }
}