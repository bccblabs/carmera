package veme.cario.com.CARmera.util;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.ArrayAdapter;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.UserModels.Favorites;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.view.SellerInfoDialog;
import veme.cario.com.CARmera.view.VehicleInfoDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.parse.ParseImageView;
import com.parse.ParseUser;

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

        TextView likesCountView;
        LinearLayout likesOverlay;

        TextView refererInfo;

        ParseImageView photo;

        ImageButton favoriteButton;
        ImageButton seeListingsBtn;
        ImageButton contactSellerBtn;
        ImageButton detailsBtn;
        ImageButton shareBtn;
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

        if (view == null) {
            view = inflater.inflate(R.layout.list_item_vehicle, parent, false);
            // Cache view components into the view holder
            holder = new ViewHolder();
            holder.vehicleItemLayout = (RelativeLayout) view
                    .findViewById(R.id.vehicle_item);
            holder.vehicleInfoView = (TextView) view.findViewById(R.id.vehicle_info_view);
            holder.sellerInfoView = (TextView) view.findViewById(R.id.seller_info_view);
            holder.priceInfoView = (TextView) view.findViewById(R.id.price_info_view);

            holder.refererInfo = (TextView) view.findViewById(R.id.referer_view);
            holder.likesCountView = (TextView) view.findViewById(R.id.likes_view);
            holder.likesOverlay = (LinearLayout) view
                    .findViewById(R.id.likes_overlay);

            holder.photo = (ParseImageView) view
                    .findViewById(R.id.tagged_photo);

            /* set the object to be "favorite", for querying */
            holder.favoriteButton = (ImageButton) view
                    .findViewById(R.id.favorite_button);

            /* display the dialog window with seller information */
            holder.contactSellerBtn = (ImageButton) view
                    .findViewById(R.id.contact_seller_btn);

            /* only show when it's not a listing, send vehicle yr, mk, model to listing screen */
            holder.seeListingsBtn = (ImageButton) view
                    .findViewById(R.id.see_listings_btn);

            /* display the dialog window with the vehicle information, and more photos */
            holder.detailsBtn = (ImageButton) view
                    .findViewById(R.id.details_btn);

            holder.shareBtn = (ImageButton) view
                    .findViewById(R.id.share_btn);

            // Tag for lookup later
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        /* vehicle: object for the entire getView function */
        final TaggedVehicle taggedVehicle = getItem(position);

        /* View item: Different relative layout button for favorite cars */
        RelativeLayout vehicleLayout = holder.vehicleItemLayout;
        if (isFavorites) {
            vehicleLayout.setBackgroundColor(GREEN_BACKGROUND);
        }
        /* View item: Vehicle Year, Make, Model Information */
        final TextView vehicle_info_tv = holder.vehicleInfoView;
        vehicle_info_tv.setText(taggedVehicle.getMake() + " "
                + taggedVehicle.getMake() + " "
                + taggedVehicle.getModel());

        /* View item: Referer */
        ParseUser referer = taggedVehicle.getReferer();
        TextView referer_info_tv = holder.refererInfo;
        if ( referer != null ) {
            referer_info_tv.setText(referer.getUsername());
        } else {
            referer_info_tv.setVisibility(View.GONE);
        }

        /* View item: likes count */
        TextView likes_cnt_tv = holder.likesCountView;
        final LinearLayout likes_overlay = holder.likesOverlay;
        likes_cnt_tv.setText(taggedVehicle.getLikesCnt());
        likes_cnt_tv.setClickable(true);
        likes_cnt_tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taggedVehicle.isLikedByMe()) {
                    taggedVehicle.removeLiker(ParseUser.getCurrentUser().getObjectId());
                    likes_overlay.setBackgroundColor(GREEN_BACKGROUND);
                } else {
                    taggedVehicle.addLiker(ParseUser.getCurrentUser().getObjectId());
                    likes_overlay.setBackgroundColor(0xFFFFFF);
                }
            }
        });

        /* View item: Vehicle Image */
        final ParseImageView photo = holder.photo;
        photo.setParseFile(taggedVehicle.getTagPhoto());
        photo.loadInBackground();

        /* View item: Vehicle Favorite Button */
        final ImageButton favoriteButton = holder.favoriteButton;
        if (Favorites.get().contains(taggedVehicle)) {
            if (isFavorites) {
                favoriteButton.setImageResource(R.drawable.x);
            } else {
                favoriteButton
                        .setImageResource(R.drawable.tagged_favorite);
            }
        } else {
            favoriteButton
                    .setImageResource(R.drawable.tagged_favorite);
        }

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
                        favoriteButton.setImageResource(R.drawable.x);
                    } else {
                        favoriteButton
                                .setImageResource(R.drawable.tagged_favorite);
                    }
                }
                favorites.save(getContext());
            }
        });
        favoriteButton.setFocusable(false);

        /*
            View item:
                Vehicle price information
                Vehicle seller information (Show Email and Phone in the dialog)
                Vehicle contact seller button
        */
        TextView price_info_tv = holder.priceInfoView;
        final TextView seller_info_tv = holder.sellerInfoView;
        final ImageButton contact_seller_btn = holder.contactSellerBtn;
        final ImageButton see_listings_btn = holder.seeListingsBtn;
        final ImageButton share_btn = holder.shareBtn;
        final ImageButton details_btn = holder.detailsBtn;

        /* details btn */
        details_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = ((FragmentActivity)getContext()).getSupportFragmentManager();
                Bundle args = new Bundle();
                args.putString ("vehicle_year", taggedVehicle.getYear());
                args.putString ("vehicle_make", taggedVehicle.getMake());
                args.putString ("vehicle_model", taggedVehicle.getModel());
                VehicleInfoDialog vehicleInfoDialog = new VehicleInfoDialog();
                vehicleInfoDialog.setArguments(args);
                vehicleInfoDialog.show(fm, "vehicleInfoOverlay");
            }
        });

        /* share btn */
        share_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /* add to my tagged posts */
                /* set acl to just the user to be shared, or public */
                /* able to add users */
            }
        });

        /* listing related view and action control:
            1. Vehicle Price View
            2. Vehicle Seller Information Button
            3. Contact Vehicle Seller Button
         */
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

            see_listings_btn.setVisibility(View.GONE);


        } else {
            /* See (search) vehicle listings Button */
            see_listings_btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* start a fragment from activity */
                }
            });
            price_info_tv.setVisibility(View.GONE);
            seller_info_tv.setVisibility(View.GONE);
            contact_seller_btn.setVisibility(View.GONE);
        }
        return view;
    }

}
