package veme.cario.com.foshop.util;

import android.widget.ArrayAdapter;

import veme.cario.com.foshop.model.TaggedVehicle;

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

/*
    Design issues:
        why do we want to have explicit favorite class?
        - implment listeners, so that we can tell users more on favs

    To-do:
        1. incorporate dialog box
        2. incorporate instagram/facebook/wechat shares
        3. wire up the dialog box
 */

public class VehicleListAdapter extends ArrayAdapter<TaggedVehicle> {

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
        ImageButton shareButton;
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
            holder.shareButton = (ImageButton) view
                    .findViewById(R.id.share_button);
            // Tag for lookup later
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        /* vehicle: object for the entire getView function */
        final TaggedVehicle taggedVehicle = getItem(position);
        LinearLayout vehicleLayout = holder.vehicleItemLayout;
        if (isFavoritesView) {
            vehicleLayout.setBackgroundColor(GREEN_BACKGROUND);
        }
        vehicleLayout.setClickable(true);
        vehicleLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /* create a new dialog */
            }
        });


        TextView timeView = holder.timeView;
        timeView.setText(taggedVehicle.getTagTimestamp());

        TextView yearView = holder.yearView;
        yearView.setText(taggedVehicle.getYear());

        TextView makeView = holder.makeView;
        makeView.setText(taggedVehicle.getMake());

        TextView modelView = holder.modelView;
        modelView.setText(taggedVehicle.getModel());

        final ParseImageView photo = holder.photo;

        photo.loadInBackground();


        /* Favorite Button: Visible no matter in normal, or favorites view */
        final ImageButton favoriteButton = holder.favoriteButton;
        if (Favorites.get().contains(taggedVehicle)) {
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

        favoriteButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Favorites favorites = Favorites.get();
                if (favorites.contains(taggedVehicle)) {
                    favorites.remove(taggedVehicle);

                    favoriteButton
                            .setImageResource(R.drawable.light_rating_not_important);
                } else {
                    favorites.add(taggedVehicle);
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
                if (favorites.contain(taggedVehicle)) {
                    favorites.remove(taggedVehicle);
                }
                taggedVehicle.deleteInBackground();
                favorites.save(getContext());
                /* do we really need this? */
                notifyDataSetChanged();

            }
        });

        /* Share Button - to implement once have social authentication implemented*/
        final ImageButton share_button = holder.shareButton;
        share_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

}
