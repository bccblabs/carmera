package veme.cario.com.CARmera.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.parse.ParseException;
import com.parse.ParseImageView;

import veme.cario.com.CARmera.BaseActivity;
import veme.cario.com.CARmera.ListingsActivity;
import veme.cario.com.CARmera.NearbyActivity;
import veme.cario.com.CARmera.ProfileActivity;
import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.view.SellerInfoDialog;

/**
 * Created by bski on 1/1/15.
 */
public class ListingsAdapter extends ArrayAdapter <TaggedVehicle>{

    private LayoutInflater inflater;
    private static String TAG = "ListingsAdapter";

    private static class ViewHolder {
        TextView vehicleInfoView;
        TextView sellerInfoView;
        TextView priceInfoView;
        TextView listing_date_view;
        ParseImageView photo;
        FloatingActionButton heart_btn, info_btn, contact_btn, share_btn, incentives_btn;
    }

    public ListingsAdapter (Context context) {
        super(context, 0);
        inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.list_item_listing, parent, false);
            holder = new ViewHolder();
            holder.sellerInfoView = (TextView) view.findViewById(R.id.listing_seller_info_view);
            holder.priceInfoView = (TextView) view.findViewById(R.id.listing_price_view);
            holder.vehicleInfoView = (TextView) view.findViewById(R.id.listing_yr_mk_md_view);
            holder.listing_date_view = (TextView) view.findViewById(R.id.listing_post_date);
            holder.photo = (ParseImageView) view
                    .findViewById(R.id.listing_photo_view);

            holder.heart_btn = (FloatingActionButton) view.findViewById(R.id.heart_btn);
            holder.info_btn = (FloatingActionButton) view.findViewById(R.id.info_btn);
            holder.contact_btn = (FloatingActionButton) view.findViewById(R.id.listings_contact_btn);
            holder.share_btn = (FloatingActionButton) view.findViewById(R.id.listings_share_btn);
            holder.incentives_btn = (FloatingActionButton) view.findViewById(R.id.listings_incentives_btn);

            // Tag for lookup later
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        /* vehicle: object for the entire getView function */
        final TaggedVehicle taggedVehicle = getItem(position);

        /* View item: Vehicle Year, Make, Model Information */
        TextView vehicle_info_tv = holder.vehicleInfoView;
        vehicle_info_tv.setText(taggedVehicle.getYear() + " "
                + taggedVehicle.getMake() + " "
                + taggedVehicle.getModel());
        Typeface ar = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");
        vehicle_info_tv.setTypeface(ar);

        TextView price_info_tv = holder.priceInfoView;
//        price_info_tv.setText(taggedVehicle.getPrice());
        price_info_tv.setTypeface(ar);

        TextView seller_info_tv = holder.sellerInfoView;
//        seller_info_tv.setText(taggedVehicle.getSellerInfo());
        seller_info_tv.setTypeface(ar);

        TextView listing_date_view = holder.listing_date_view;
//        listing_date_view.setText(taggedVehicle.getCreatedAt().toString());
        listing_date_view.setTypeface(ar);

        /* View item: Vehicle Image */
        final ParseImageView photo = holder.photo;
        photo.setParseFile(taggedVehicle.getThumbnail());
        photo.loadInBackground();

        if (taggedVehicle.isFavorites()) {
            holder.heart_btn.setIcon(R.drawable.ic_action_heart_mid);
        } else {
            holder.heart_btn.setIcon(R.drawable.ic_action_heart_white);
        }
        holder.heart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taggedVehicle.isFavorites()) {
                    holder.heart_btn.setIcon(R.drawable.ic_action_heart_white);
                    taggedVehicle.setFavorite(false);
                } else {
                    holder.heart_btn.setIcon(R.drawable.ic_action_heart_mid);
                    taggedVehicle.setFavorite(true);
                }
            }
        });

        /* details btn */
        holder.info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    byte[] imageData = taggedVehicle.getTagPhoto().getData();
                    ((BaseActivity) getContext()).onDetailsSelected(imageData,
                            taggedVehicle.getYear(),
                            taggedVehicle.getMake(),
                            taggedVehicle.getModel());
                } catch (ParseException e) {
                    Log.i(TAG, " - getting parse file raw data err: " + e.getMessage());
                }
            }
        });
//
//        contact_seller_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    SellerInfoDialog sellerInfoDialog = new SellerInfoDialog();
//                    /* pass info to the dialog */
//                    Bundle args = new Bundle();
////                    String sellerEmail = taggedVehicle.getSellerEmail();
////                    String sellerPhone = taggedVehicle.getSellerPhone();
////                    try {
////                        byte[] seller_thumbnail = taggedVehicle.getSellerThumbnail().getData();
////                        if (seller_thumbnail != null) {
////                            args.putByteArray("seller_thumbnail", seller_thumbnail);
////                        }
////                    } catch (com.parse.ParseException e) {
////                        Log.d(TAG, e.getMessage());
////                    }
//
////                    if (sellerEmail != null) {
////                        args.putString("seller_email", taggedVehicle.getSellerEmail());
////                    }
//
////                    if (sellerPhone != null) {
////                        args.putString("seller_phone", taggedVehicle.getSellerPhone());
////                    }
//
////                    args.putString("seller_info", taggedVehicle.getSellerInfo());
//
//
//                    /* starts the dialog */
//                    FragmentManager fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
//                    sellerInfoDialog.setArguments(args);
//                    sellerInfoDialog.show(fm, "sellerInfoDialog");
//                }
//            });
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
        return view;
    }

}
