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
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidviewhover.BlurLayout;
import com.facebook.widget.FacebookDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
        TextView vehicleInfoView, priceInfoView,mileageInfoView, dealer_sales_review, dealer_service_review, dealer_name_view, mpg_view, hp_view, safety_view;
        ImageView photo;
        FloatingActionButton info_btn, contact_btn, share_btn;
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

            view = inflater.inflate(R.layout.list_item_listing_v2, parent, false);
            holder = new ViewHolder();

            BlurLayout.setGlobalDefaultDuration(450);
            BlurLayout sampleLayout = (BlurLayout) view.findViewById(R.id.blurred_image_layout);
            View hover = LayoutInflater.from(getContext()).inflate(R.layout.hover, null);
            sampleLayout.setHoverView(hover);
            sampleLayout.addChildAppearAnimator(hover, R.id.text_overlay, Techniques.FadeIn, 550, 0);
            sampleLayout.addChildDisappearAnimator(hover, R.id.text_overlay, Techniques.FadeIn, 550, 500);


            holder.vehicleInfoView = (TextView) hover.findViewById(R.id.listing_yr_mk_md_view);
            holder.priceInfoView = (TextView) hover.findViewById(R.id.listing_price_view);
            holder.mileageInfoView = (TextView) hover.findViewById(R.id.listing_mileage_view);

            holder.dealer_name_view = (TextView) hover.findViewById(R.id.listing_dealer_name);
            holder.dealer_service_review = (TextView) hover.findViewById(R.id.listing_dealer_service_rating);
            holder.dealer_sales_review = (TextView) hover.findViewById(R.id.listing_dealer_sales_rating);

            holder.hp_view = (TextView) hover.findViewById(R.id.listing_horsepower_view);
            holder.safety_view = (TextView) hover.findViewById(R.id.listing_safety_overall);
            holder.mpg_view = (TextView) hover.findViewById(R.id.listing_mpg_view);


            holder.info_btn = (FloatingActionButton) hover.findViewById(R.id.info_btn);
            holder.contact_btn = (FloatingActionButton) hover.findViewById(R.id.listings_contact_btn);
            holder.share_btn = (FloatingActionButton) hover.findViewById(R.id.listings_share_btn);

            holder.photo = (ImageView) view
                    .findViewById(R.id.listing_photo_v2);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        /* vehicle: object for the entire getView function */
        final ListingV2 listing = getItem(position);

        /* View item: Vehicle Year, Make, Model Information */
        if (listing.getMake() == null) {
            holder.vehicleInfoView.setText("UNKNOWN");
        } else {
            holder.vehicleInfoView.setText(listing.getYear() + " "
                    + listing.getMake() + " "
                    + listing.getModel());
        }
        Typeface ar = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");
        holder.vehicleInfoView.setTypeface(ar);

        holder.priceInfoView.setText("PRICE:\t$" + listing.getDealerOfferPrice());
        holder.priceInfoView.setTypeface(ar);

        holder.mileageInfoView.setText("MILEAGE:\t" + listing.getMileage() + "\tMILES");
        holder.mileageInfoView.setTypeface(ar);




        holder.hp_view.setText("HORSEPOWER:\t" + listing.getHorsepower() + " HP");
        holder.hp_view.setTypeface(ar);

        holder.mpg_view.setText("COMBINED MPG:\t" + listing.getCombinedMpg());
        holder.mpg_view.setTypeface(ar);

        if (listing.getOverall() == null)
            holder.safety_view.setText("OVERALL SAFETY RATING:\tN/A");
        else
            holder.safety_view.setText("OVERALL SAFETY RATING:\t" + listing.getOverall());
        holder.safety_view.setTypeface(ar);




        holder.dealer_sales_review.setText("SALES RATING:\t" + listing.getDealerSaleRating());
        holder.dealer_sales_review.setTypeface(ar);

        holder.dealer_name_view.setText(listing.getDealerName());
        holder.dealer_name_view.setTypeface(ar);

        holder.dealer_service_review.setText("SERVICE RATING:\t" + listing.getDealerServiceRating());
        holder.dealer_service_review.setTypeface(ar);


        /* View item: Vehicle Image */
        final ImageView photo = holder.photo;
        List<String> image_urls = new ArrayList<>();
        image_urls.add (listing.getF34PhotoUrlE());
        image_urls.add (listing.getF34PhotoUrlST());
        image_urls.add (listing.getF34PhotoUrlT());
        image_urls.addAll(listing.getLargePhotoUrls());
        boolean found = false;
        for (String url : image_urls) {
            Log.i (TAG, "url is: " + url);
            if (url != null && url.contains("http")) {
                new DownloadImageTask(photo).execute(url);
                found = true;
                break;
            }
        }
        if (!found) {
            photo.setImageResource(R.drawable.edmunds_logo);
        }

        image_urls.clear();
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
                TextView dealer_name_tv = (TextView) seller_info_view.findViewById(R.id.seller_info_textview);

                dealer_name_tv.setText(listing.getDealerName());
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
