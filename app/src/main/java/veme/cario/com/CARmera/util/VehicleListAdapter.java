package veme.cario.com.CARmera.util;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.ArrayAdapter;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.view.SellerInfoDialog;
import veme.cario.com.CARmera.view.VehicleInfoDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseImageView;

public class VehicleListAdapter extends ArrayAdapter<TaggedVehicle> {

    private static int GREEN_BACKGROUND = 0x008000;
    private static int WHITE_BACKGROUND = 0xFFFFFF;
    private LayoutInflater inflater;

    private static String TAG = "Tagged Vehicle Adapter";

    private static class ViewHolder {
        LinearLayout vehicleItemLayout;
        TextView vehicleInfoView;
        TextView sellerInfoView;
        TextView priceInfoView;

//        TextView likesCountView;
//        LinearLayout likesOverlay;

//        TextView refererInfo;

        ParseImageView photo;

        Button favoriteButton;
        Button seeListingsBtn;
        Button contactSellerBtn;
        Button detailsBtn;
        Button shareBtn;
    }

    public VehicleListAdapter (Context context) {
        super(context, 0);
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
            holder.vehicleItemLayout = (LinearLayout) view
                    .findViewById(R.id.vehicle_item);
            holder.vehicleInfoView = (TextView) view.findViewById(R.id.vehicle_info_view);
            holder.sellerInfoView = (TextView) view.findViewById(R.id.seller_info_view);
            holder.priceInfoView = (TextView) view.findViewById(R.id.price_info_view);

//            holder.refererInfo = (TextView) view.findViewById(R.id.referer_view);
//            holder.likesCountView = (TextView) view.findViewById(R.id.likes_view);
//            holder.likesOverlay = (LinearLayout) view
//                    .findViewById(R.id.likes_overlay);

            holder.photo = (ParseImageView) view
                    .findViewById(R.id.tagged_photo);

            /* set the object to be "favorite", for querying */
            holder.favoriteButton = (Button) view
                    .findViewById(R.id.favorite_button);

            /* display the dialog window with seller information */
            holder.contactSellerBtn = (Button) view
                    .findViewById(R.id.contact_seller_btn);

            /* only show when it's not a listing, send vehicle yr, mk, model to listing screen */
            holder.seeListingsBtn = (Button) view
                    .findViewById(R.id.see_listings_btn);

            /* display the dialog window with the vehicle information, and more photos */
            holder.detailsBtn = (Button) view
                    .findViewById(R.id.details_btn);

            holder.shareBtn = (Button) view
                    .findViewById(R.id.share_btn);

            // Tag for lookup later
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        /* vehicle: object for the entire getView function */
        final TaggedVehicle taggedVehicle = getItem(position);

        /* View item: Different relative layout button for favorite cars */
        LinearLayout vehicleLayout = holder.vehicleItemLayout;
        if (taggedVehicle.isFavorites()) {
            vehicleLayout.setBackgroundColor(GREEN_BACKGROUND);
        }
        /* View item: Vehicle Year, Make, Model Information */
        final TextView vehicle_info_tv = holder.vehicleInfoView;
        vehicle_info_tv.setText(taggedVehicle.getYear() + " "
                + taggedVehicle.getMake() + " "
                + taggedVehicle.getModel());

        /* View item: Referer */
//        ParseUser referer = taggedVehicle.getReferer();
//        TextView referer_info_tv = holder.refererInfo;
//        if ( referer != null ) {
//            referer_info_tv.setText(referer.getUsername());
//        } else {
//            referer_info_tv.setVisibility(View.GONE);
//        }

        /* View item: likes count */
//        TextView likes_cnt_tv = holder.likesCountView;
//        final LinearLayout likes_overlay = holder.likesOverlay;
//        likes_cnt_tv.setText(taggedVehicle.getLikesCnt());
//        likes_cnt_tv.setClickable(true);
//        likes_cnt_tv.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (taggedVehicle.isLikedByMe()) {
//                    taggedVehicle.removeLiker(ParseUser.getCurrentUser().getObjectId());
//                    likes_overlay.setBackgroundColor(GREEN_BACKGROUND);
//                } else {
//                    taggedVehicle.addLiker(ParseUser.getCurrentUser().getObjectId());
//                    likes_overlay.setBackgroundColor(WHITE_BACKGROUND);
//                }
//            }
//        });

        /* View item: Vehicle Image */
        final ParseImageView photo = holder.photo;
        photo.setParseFile(taggedVehicle.getTagPhoto());
        photo.loadInBackground();

//        /* View item: Vehicle Favorite Button */
//        final Button favoriteButton = holder.favoriteButton;
//        if (SavedListingsList.get().contains(taggedVehicle)) {
//            if (taggedVehicle.isFavorites()) {
//                favoriteButton.setImageResource(R.drawable.x);
//            } else {
//                favoriteButton
//                        .setImageResource(R.drawable.tagged_favorite);
//            }
//        } else {
//            favoriteButton
//                    .setImageResource(R.drawable.tagged_favorite);
//        }

//        favoriteButton.setOnClickListener(new OnClickListener() {
//            public void onClick(View v) {
//                SavedListingsList savedListingsList = SavedListingsList.get();
//                if (savedListingsList.contains(taggedVehicle)) {
//                    savedListingsList.remove(taggedVehicle);
//                    favoriteButton
//                            .setImageResource(R.drawable.tagged_not_favorite);
//                } else {
//                    savedListingsList.add(taggedVehicle);
//                    if (taggedVehicle.isFavorites()) {
//                        favoriteButton.setImageResource(R.drawable.x);
//                    } else {
//                        favoriteButton
//                                .setImageResource(R.drawable.tagged_favorite);
//                    }
//                }
//                savedListingsList.save(getContext());
//            }
//        });
//        favoriteButton.setFocusable(false);

        /*
            View item:
                Vehicle price information
                Vehicle seller information (Show Email and Phone in the dialog)
                Vehicle contact seller button
        */
        TextView price_info_tv = holder.priceInfoView;
        final TextView seller_info_tv = holder.sellerInfoView;
        final Button contact_seller_btn = holder.contactSellerBtn;
        final Button see_listings_btn = holder.seeListingsBtn;
        final Button share_btn = holder.shareBtn;
        final Button details_btn = holder.detailsBtn;

        /* details btn */
        details_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
                Bundle args = new Bundle();
                try {
                    byte[] imageData = taggedVehicle.getTagPhoto().getData();
                    args.putString("dialog_type", "vehicle_info");
                    args.putString("vehicle_id", taggedVehicle.getTrimId());
                    args.putString("vehicle_name", taggedVehicle.toString());
                    args.putString("vehicle_trim_name", taggedVehicle.getTrimName());
                    args.putByteArray("imageData", imageData);

                } catch (ParseException e) {
                    Log.i(TAG, " - gettnig parse file raw data err: " + e.getMessage());
                }
                    VehicleInfoDialog vehicleInfoDialog = new VehicleInfoDialog();
                    vehicleInfoDialog.setArguments(args);
                    vehicleInfoDialog.show(fm, "vehicleInfoOverlay");
            }
        });

        /* share btn */
        share_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /* TODO: add to my tagged posts */
                /* set acl to just the user to be shared, or public */
                /* able to add users */
            }
        });

        /* listing related view and action control:
            1. Vehicle Price View
            2. Vehicle Seller Information Button
            3. Contact Vehicle Seller Button
         */
        if (taggedVehicle.isListing()) {
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
            see_listings_btn.setVisibility(View.GONE);
        }
        return view;
    }

}
