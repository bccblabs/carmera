package veme.cario.com.CARmera.util;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.ArrayAdapter;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.UserModels.Favorites;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.view.SellerInfoDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.parse.ParseImageView;

/*
    Design issues:
        why do we want to have explicit favorite class?
        - implement listeners, so that we can tell users more on favorites

    To-do:
        1. incorporate dialog box
        2. incorporate Ins/facebook/WeChat shares
        3. wire up the dialog box
 */

public class VehicleListAdapter extends ArrayAdapter<TaggedVehicle> {

    private static int GREEN_BACKGROUND = 0x008000;
    private boolean isFavorites = false;
    private boolean isListing = false;
    private LayoutInflater inflater;

    private static String TAG = "SELLER_INFO_DIALOG";

    private static class ViewHolder {
        RelativeLayout vehicleItemLayout;
        TextView vehicleInfoView;
        TextView sellerInfoView;
        TextView priceInfoView;
        ParseImageView photo;
        ImageButton favoriteButton;
        ImageButton seeListingsBtn;
        ImageButton contactSellerBtn;
    }

    public VehicleListAdapter (Context context, boolean isFavorites_, boolean isListing_) {
        super(context, 0);
        isFavorites = isFavorites_;
        isListing = isListing_;
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
            holder.vehicleItemLayout = (RelativeLayout) view
                    .findViewById(R.id.vehicle_item);
            holder.vehicleInfoView = (TextView) view.findViewById(R.id.vehicle_info_view);
            holder.sellerInfoView = (TextView) view.findViewById(R.id.seller_info_view);
            holder.priceInfoView = (TextView) view.findViewById(R.id.price_info_view);

            holder.photo = (ParseImageView) view
                    .findViewById(R.id.tagged_photo);

            holder.favoriteButton = (ImageButton) view
                    .findViewById(R.id.favorite_button);
            holder.contactSellerBtn = (ImageButton) view
                    .findViewById(R.id.contact_seller_btn);
            holder.seeListingsBtn = (ImageButton) view
                    .findViewById(R.id.see_listings_btn);

            // Tag for lookup later
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        /* vehicle: object for the entire getView function */
        final TaggedVehicle taggedVehicle = getItem(position);
        RelativeLayout vehicleLayout = holder.vehicleItemLayout;

        if (isFavorites) {
            vehicleLayout.setBackgroundColor(GREEN_BACKGROUND);
        }


        TextView vehicle_info_tv = holder.vehicleInfoView;
        vehicle_info_tv.setText(taggedVehicle.getMake() + " "
                + taggedVehicle.getMake() + " "
                + taggedVehicle.getModel());

        final ParseImageView photo = holder.photo;
        photo.loadInBackground();

        final ImageButton favoriteButton = holder.favoriteButton;
        if (Favorites.get().contains(taggedVehicle)) {
            if (isFavorites) {
//                favoriteButton.setImageResource(R.drawable.x);
            } else {
                favoriteButton
                        .setImageResource(R.drawable.tagged_favorite);
            }
        } else {
            favoriteButton
                    .setImageResource(R.drawable.tagged_favorite);
        }
        /* Favorite Button: Visible no matter in normal, or favorites view */

        favoriteButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Favorites favorites = Favorites.get();
                if (favorites.contains(taggedVehicle)) {
                    favorites.remove(taggedVehicle);

                    favoriteButton
                            .setImageResource(R.drawable.tagged_not_favorite);
                } else {
                    favorites.add(taggedVehicle);
                    if (isFavorites) {
//                        favoriteButton.setImageResource(R.drawable.x);
                    } else {
                        favoriteButton
                                .setImageResource(R.drawable.tagged_favorite);
                    }
                }
                favorites.save(getContext());
            }
        });
        favoriteButton.setFocusable(false);


        TextView price_info_tv = holder.priceInfoView;
        final TextView seller_info_tv = holder.sellerInfoView;
        final ImageButton contact_seller_btn = holder.contactSellerBtn;
        if (isListing) {
            price_info_tv.setText(taggedVehicle.getPrice());
            seller_info_tv.setText(taggedVehicle.getSellerInfo());
            contact_seller_btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    SellerInfoDialog sellerInfoDialog = new SellerInfoDialog();
                    /* pass info to the dialog */
                    Bundle args = new Bundle();
                    String sellerEmail = taggedVehicle.getSellerEmail();
                    String sellerPhone = taggedVehicle.getSellerPhone();
                    try {
                        byte[] seller_thumbnail = taggedVehicle.getSellerThumbnail().getData();
                        if (seller_thumbnail != null) {
                            args.putByteArray("seller_thumbnail", seller_thumbnail);
                        }
                    } catch (com.parse.ParseException e) {
                        Log.d(TAG, e.getMessage());
                    }

                    if (sellerEmail != null) {
                        args.putString("seller_email", taggedVehicle.getSellerEmail());
                    }
                    if (sellerPhone != null) {
                        args.putString("seller_phone", taggedVehicle.getSellerPhone());
                    }
                    args.putString("seller_info", taggedVehicle.getSellerInfo());


                    /* starts the dialog */
                    FragmentManager fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
                    sellerInfoDialog.setArguments(args);
                    sellerInfoDialog.show(fm, "sellerInfoDialog");
                }
            });

        } else {
            price_info_tv.setVisibility(View.GONE);
            seller_info_tv.setVisibility(View.GONE);
            contact_seller_btn.setVisibility(View.GONE);
        }
        return view;
    }

}
