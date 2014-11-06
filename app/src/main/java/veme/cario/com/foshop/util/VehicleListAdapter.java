package veme.cario.com.foshop.util;

import android.widget.ArrayAdapter;

import veme.cario.com.foshop.model.Vehicle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseImageView;

/**
 * Created by bski on 11/5/14.
 */
public class VehicleListAdapter extends ArrayAdapter<Vehicle> {

    private static int GREEN_BACKGROUND = 0x008000;
    private boolean isFavoritesView = false;
    private LayoutInflater inflater;

    private static class ViewHolder {
        LinearLayout vehicleItemLayout;
        TextView timeView;
        TextView yearView;
        TextView makeView;
        TextView modelView;
        ParseImageView photo;
        ImageButton favoriteButton;
        ImageButton deleteButton;
    }




    public VehicleListAdapter (Context context, boolean isFavorites) {
        super(context, 0);
        isFavoritesView = isFavorites;
        inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        // If a view hasn't been provided inflate on
        if (view == null) {
            view = inflater.inflate(R.layout.list_item_vehicle, parent, false);
            // Cache view components into the view holder
            holder = new ViewHolder();
            holder.vehicleItemLayout = (LinearLayout) view
                    .findViewById(R.id.vehicle_item);
            holder.timeView = (TextView) view.findViewById(R.id.time_view);
            holder.yearView = (TextView) view.findViewById(R.id.year_view);
            holder.makeView = (TextView) view.findViewById(R.id.make_view);
            holder.modelView = (TextView) view.findViewById(R.id.model_view);
            holder.photo = (ParseImageView) view
                    .findViewById(R.id.tagged_photo);
            holder.favoriteButton = (ImageButton) view
                    .findViewById(R.id.favorite_button);
            holder.deleteButton = (ImageButton) view
                    .findViewById(R.id.delete_button);
            // Tag for lookup later
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        /* vehicle: object for the entire getView function */
        final Vehicle vehicle = getItem(position);

        if (isFavoritesView) {
            LinearLayout vehicleLayout = holder.vehicleItemLayout;
            vehicleLayout.setBackgroundColor(GREEN_BACKGROUND);
        }

        TextView timeView = holder.timeView;
        timeView.setText(vehicle.getTagTimestamp());

        TextView yearView = holder.yearView;
        yearView.setText(vehicle.getYear());

        TextView makeView = holder.makeView;
        makeView.setText(vehicle.getMake());

        TextView modelView = holder.modelView;
        modelView.setText(vehicle.getModel());

        final ParseImageView photo = holder.photo;

        photo.loadInBackground();


        final ImageButton favoriteButton = holder.favoriteButton;
        if (Favorites.get().contains(vehicle)) {
            if (isFavoritesView) {
                favoriteButton.setImageResource(R.drawable.x);
            } else {
                favoriteButton
                        .setImageResource(R.drawable.light_rating_important);
            }
        } else {
            favoriteButton
                    .setImageResource(R.drawable.light_rating_not_important);
        }

        /* Favorite Button: Visible no matter in normal, or favorites view */
        favoriteButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Favorites favorites = Favorites.get();
                if (favorites.contains(vehicle)) {
                    favorites.remove(vehicle);

                    favoriteButton
                            .setImageResource(R.drawable.light_rating_not_important);
                } else {
                    favorites.add(vehicle);
                    if (isFavoritesView) {
                        favoriteButton.setImageResource(R.drawable.x);
                    } else {
                        favoriteButton
                                .setImageResource(R.drawable.light_rating_important);
                    }
                }
                favorites.save(getContext());
            }
        });
        favoriteButton.setFocusable(false);

        /* Delete Button: Visible no matter in normal, or favorites view */
        final ImageButton delete_button = holder.deleteButton;
        delete_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorites favorites = Favorites.get();
                if (favorites.contain(vehicle)) {
                    favorites.remove(vehicle);
                }
                vehicle.deleteInBackground();
                favorites.save(getContext());
                /* do we really need this? */
                notifyDataSetChanged();

            }
        });
        return view;
    }

}
