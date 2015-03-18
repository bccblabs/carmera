package veme.cario.com.CARmera.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.widget.FacebookDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.parse.ParseException;
import com.parse.ParseImageView;
import com.parse.ParseUser;

import veme.cario.com.CARmera.BaseActivity;
import veme.cario.com.CARmera.CARmeraApp;
import veme.cario.com.CARmera.ListingsActivity;
import veme.cario.com.CARmera.NearbyActivity;
import veme.cario.com.CARmera.ProfileActivity;
import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.UserModels.SavedSearch;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.model.UserModels.UserActivity;
import veme.cario.com.CARmera.view.SellerInfoDialog;
import veme.cario.com.CARmera.view.VehicleInfoDialog;

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
        FloatingActionButton heart_btn, info_btn, contact_btn, share_btn;
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

            // Tag for lookup later
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        /* vehicle: object for the entire getView function */
        final TaggedVehicle taggedVehicle = getItem(position);

        /* View item: Vehicle Year, Make, Model Information */
        TextView vehicle_info_tv = holder.vehicleInfoView;
        if (taggedVehicle.getMake() == null) {
            vehicle_info_tv.setText("Unlabeled");
        } else {
            vehicle_info_tv.setText(taggedVehicle.getYear() + " "
                    + taggedVehicle.getMake() + " "
                    + taggedVehicle.getModel());
        }
        Typeface ar = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");
        vehicle_info_tv.setTypeface(ar);

        TextView price_info_tv = holder.priceInfoView;
        price_info_tv.setText("$" + taggedVehicle.getPrice());
        price_info_tv.setTypeface(ar);

        final TextView seller_info_tv = holder.sellerInfoView;
        seller_info_tv.setText(taggedVehicle.getSellerInfo());
        seller_info_tv.setTypeface(ar);

        TextView listing_date_view = holder.listing_date_view;
        listing_date_view.setText(taggedVehicle.getCreatedAt().toString());
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
                taggedVehicle.saveInBackground();
            }
        });

        /* details btn */
        holder.info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ((BaseActivity) getContext()).OnListingSelectedCallback(taggedVehicle.getObjectId(), taggedVehicle.getStyleId());
            }
        });
        holder.contact_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog contact_seller_dialog = new MaterialDialog.Builder(getContext())
                        .title("Contact Seller")
                        .customView(R.layout.seller_info_dialog)
                        .neutralText("Got it!")
                        .build();
                contact_seller_dialog.show();
                View seller_info_view = contact_seller_dialog.getCustomView();
                FloatingActionButton phone_btn = (FloatingActionButton) seller_info_view.findViewById(R.id.contact_seller_phone_btn);
                FloatingActionButton email_btn = (FloatingActionButton) seller_info_view.findViewById(R.id.contact_seller_email_btn);
                LinearLayout phone_layout = (LinearLayout) seller_info_view.findViewById(R.id.phone_overlay);
                LinearLayout email_layout = (LinearLayout) seller_info_view.findViewById(R.id.email_overlay);
                TextView phone_tv = (TextView) seller_info_view.findViewById(R.id.seller_phone_textview);
                TextView email_tv = (TextView) seller_info_view.findViewById(R.id.seller_email_textview);
                phone_tv.setText(taggedVehicle.getSellerPhone());
                email_tv.setText(taggedVehicle.getSellerEmail());
                phone_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String phone_num = taggedVehicle.getSellerPhone().replaceAll("[^0-9|\\+]", "");
                        Intent i = new Intent (Intent.ACTION_DIAL, Uri.fromParts("tel", phone_num, null));
                        getContext().startActivity(i);
                    }
                });
                email_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email_uri = "mailto:" + Uri.encode(taggedVehicle.getSellerEmail()) +
                                           "?subject=" + Uri.encode(taggedVehicle.toString()) +
                                           "&body=" + Uri.encode("Hello \n\nI am interested in your " + taggedVehicle.toString() +
                                                                "\nPlease let me know more, thanks! \n\n" + " -" + CARmeraApp.userName +
                                                                "\n\nReferred from Carmera.io");
                        Uri uri = Uri.parse(email_uri);
                        Intent i = new Intent(Intent.ACTION_SENDTO);
                        i.setData(uri);
                        getContext().startActivity(i);
                    }
                });
            }
        });

        holder.share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FacebookDialog.canPresentMessageDialog(getContext())) {
                    FacebookDialog.MessageDialogBuilder builder = new FacebookDialog.MessageDialogBuilder((Activity) getContext());
                    builder.setLink("http://www.carmera.io")
                            .setName(taggedVehicle.toString())
                            .setPicture(taggedVehicle.getTagPhoto().getUrl());
                    builder.build().present();
                } else {
                    // The user doesn't have the Facebook Messenger app for Android app installed.
                }
                UserActivity userActivity = new UserActivity();
                userActivity.setSrcName("I");
                userActivity.setDataId(taggedVehicle.getObjectId());
                userActivity.setDestName("Kai");
                userActivity.setType("taggedVehicle");
                userActivity.saveInBackground();
            }
        });

        return view;
    }

}
