package veme.cario.com.CARmera.util;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.ArrayAdapter;

import veme.cario.com.CARmera.ProfileActivity;
import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.CarInfoFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.ImageFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.SelectStyleFragment;
import veme.cario.com.CARmera.model.APIModels.Vehicle;
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

public class VehicleListAdapter extends ArrayAdapter<TaggedVehicle>  {

    private LayoutInflater inflater;
    private static String TAG = "TaggedVehicleAdapter";

    private static class ViewHolder {
        TextView vehicleInfoView;
        ParseImageView photo;
        Button favoriteButton;
        Button seeListingsBtn;
        Button detailsBtn;
//        Button commentBtn;
//        TextView likesCountView;
//        LinearLayout likesOverlay;
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
            holder = new ViewHolder();
            holder.vehicleInfoView = (TextView) view.findViewById(R.id.vehicle_info_view);

            holder.photo = (ParseImageView) view
                    .findViewById(R.id.tagged_photo);

            /* set the object to be "favorite", for querying */
            holder.favoriteButton = (Button) view
                    .findViewById(R.id.favorite_button);

            /* only show when it's not a listing, send vehicle yr, mk, model to listing screen */
            holder.seeListingsBtn = (Button) view
                    .findViewById(R.id.see_listings_btn);

            /* display the dialog window with the vehicle information, and more photos */
            holder.detailsBtn = (Button) view
                    .findViewById(R.id.details_btn);

            // Tag for lookup later
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        /* vehicle: object for the entire getView function */
        final TaggedVehicle taggedVehicle = getItem(position);

        /* View item: Vehicle Year, Make, Model Information */
        final TextView vehicle_info_tv = holder.vehicleInfoView;
        vehicle_info_tv.setText(taggedVehicle.getYear() + " "
                + taggedVehicle.getMake() + " "
                + taggedVehicle.getModel());

        Typeface ar = Typeface.createFromAsset(getContext().getAssets(), "android-robot.ttf");

        vehicle_info_tv.setTypeface(ar);



        /* View item: Vehicle Image */
        final ParseImageView photo = holder.photo;
        photo.setParseFile(taggedVehicle.getTagPhoto());
        photo.loadInBackground();


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
        final Button see_listings_btn = holder.seeListingsBtn;
        final Button details_btn = holder.detailsBtn;

        /* details btn */
        details_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    byte[] imageData = taggedVehicle.getTagPhoto().getData();
                    ((ProfileActivity) getContext()).onRecognitionResult(imageData, taggedVehicle.getYear(),
                            taggedVehicle.getMake(),
                            taggedVehicle.getModel());
                } catch (ParseException e) {
                    Log.i(TAG, " - getting parse file raw data err: " + e.getMessage());
                }
            }
        });

        see_listings_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ProfileActivity) getContext()).OnSeeListingsSelected(taggedVehicle.getYear(),
                                                                        taggedVehicle.getMake(),
                                                                        taggedVehicle.getModel());
            }
        });
        return view;
    }

}
