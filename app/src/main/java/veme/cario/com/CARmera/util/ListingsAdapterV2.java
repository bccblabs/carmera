package veme.cario.com.CARmera.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.widget.FacebookDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.parse.ParseImageView;

import java.io.InputStream;
import java.util.ArrayList;

import veme.cario.com.CARmera.BaseActivity;
import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.Json.ListingV2;

/**
 * Created by bski on 1/1/15.
 */
public class ListingsAdapterV2 extends ArrayAdapter <ListingV2>{

    private LayoutInflater inflater;
    private static String TAG = "ListingsAdapter";

    private static class ViewHolder {
        TextView vehicleInfoView;
        TextView sellerInfoView;
        TextView priceInfoView;
        ImageView photo;
        FloatingActionButton heart_btn, info_btn, contact_btn, share_btn;
    }

    public ListingsAdapterV2 (Context context) {
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
            holder.photo = (ParseImageView) view
                    .findViewById(R.id.listing_photo_view);
            holder.heart_btn = (FloatingActionButton) view.findViewById(R.id.heart_btn);
            holder.info_btn = (FloatingActionButton) view.findViewById(R.id.info_btn);
            holder.contact_btn = (FloatingActionButton) view.findViewById(R.id.listings_contact_btn);
            holder.share_btn = (FloatingActionButton) view.findViewById(R.id.listings_share_btn);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        /* vehicle: object for the entire getView function */
        final ListingV2 listing = getItem(position);

        /* View item: Vehicle Year, Make, Model Information */
        TextView vehicle_info_tv = holder.vehicleInfoView;
        if (listing.getMake() == null) {
            vehicle_info_tv.setText("Unlabeled");
        } else {
            vehicle_info_tv.setText(listing.getYear() + " "
                    + listing.getMake() + " "
                    + listing.getModel());
        }
        Typeface ar = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");
        vehicle_info_tv.setTypeface(ar);

        TextView price_info_tv = holder.priceInfoView;
        price_info_tv.setText("$" + listing.getDealerOfferPrice());
        price_info_tv.setTypeface(ar);

        final TextView seller_info_tv = holder.sellerInfoView;
        seller_info_tv.setText(listing.getDealerName());
        seller_info_tv.setTypeface(ar);

        /* View item: Vehicle Image */
        final ImageView photo = holder.photo;
        new DownloadImageTask(photo).execute (listing.getF34PhotoUrlE());

        /* details btn */
        holder.info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle vehicle_agg_info = new Bundle ();
                vehicle_agg_info.putString ("styleName", listing.getStyleName());
                vehicle_agg_info.putInt ("mileage", listing.getMileage());
                vehicle_agg_info.putString ("automaticType", listing.getAutomaticType());
                vehicle_agg_info.putString ("driveTrain", listing.getDriveTrain());
                vehicle_agg_info.putInt("combinedMpg" , listing.getCombinedMpg());
                vehicle_agg_info.putInt("cityMpg" , listing.getCityMpg());
                vehicle_agg_info.putInt("hwyMpg" , listing.getHwyMpg());
                vehicle_agg_info.putInt("horsepower" , listing.getHorsepower());
                vehicle_agg_info.putInt("torque" , listing.getTorque());
                vehicle_agg_info.putInt("cylinder" , listing.getCylinder());
                vehicle_agg_info.putString("engineType" , listing.getEngineType());
                vehicle_agg_info.putString("compressorType" , listing.getCompressorType());
                vehicle_agg_info.putString("exteriorGenericColor" , listing.getExteriorGenericColor());
                vehicle_agg_info.putString("interiorGenericColor" , listing.getInteriorGenericColor());
                vehicle_agg_info.putFloat("zerosixty" , listing.getZerosixty());
                vehicle_agg_info.putFloat("quartermile" , listing.getQuartermile());
                vehicle_agg_info.putFloat("avg_insurance_cost" , listing.getAvg_insurance_cost());
                vehicle_agg_info.putFloat("avg_repairs_cost" , listing.getAvg_repairs_cost());
                vehicle_agg_info.putFloat("avg_depreciation" , listing.getAvg_depreciation());
                vehicle_agg_info.putFloat("avg_maintenance_cost" , listing.getAvg_maintenance_cost());
                vehicle_agg_info.putFloat("rating_comfort" , listing.getRating_comfort());
                vehicle_agg_info.putFloat("rating_fun_to_drive" , listing.getRating_fun_to_drive());
                vehicle_agg_info.putFloat("avg_depreciation" , listing.getAvg_depreciation());
                vehicle_agg_info.putFloat("rating_build_quality" , listing.getRating_build_quality());
                vehicle_agg_info.putFloat("rating_reliability" , listing.getRating_reliability());
                vehicle_agg_info.putString("f34PhotoUrlT" , listing.getF34PhotoUrlT());
                vehicle_agg_info.putString("f34PhotoUrlST" , listing.getF34PhotoUrlST());
                vehicle_agg_info.putString("f34PhotoUrlE" , listing.getF34PhotoUrlE());
                vehicle_agg_info.putString("dealerName" , listing.getDealerName());
                vehicle_agg_info.putString("dealerAddress" , listing.getDealerAddress());
                vehicle_agg_info.putString ("dealerPhone", listing.getDealerPhone());
                vehicle_agg_info.putStringArrayList("carfaxes", (ArrayList)listing.getCarfaxes());
                vehicle_agg_info.putStringArrayList("features", (ArrayList)listing.getFeatures());
                vehicle_agg_info.putStringArrayList("smallPhotoUrls", (ArrayList)listing.getSmallPhotoUrls());
                vehicle_agg_info.putStringArrayList("largePhotoUrls", (ArrayList)listing.getLargePhotoUrls());
                vehicle_agg_info.putString ("dialog_type", "listingV2DetailOverlay");

                ((BaseActivity) getContext()).OnListingV2SelectedCallback(vehicle_agg_info);
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
                LinearLayout phone_layout = (LinearLayout) seller_info_view.findViewById(R.id.phone_overlay);
                TextView phone_tv = (TextView) seller_info_view.findViewById(R.id.seller_phone_textview);
                phone_tv.setText(listing.getDealerPhone());
                phone_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String phone_num = listing.getDealerPhone().replaceAll("[^0-9|\\+]", "");
                        Intent i = new Intent (Intent.ACTION_DIAL, Uri.fromParts("tel", phone_num, null));
                        getContext().startActivity(i);
                    }
                });
            }
        });

        return view;
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error" , e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
