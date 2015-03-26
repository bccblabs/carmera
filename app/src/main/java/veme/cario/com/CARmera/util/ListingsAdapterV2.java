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
import veme.cario.com.CARmera.model.ListingAgg;

/**
 * Created by bski on 1/1/15.
 */
public class ListingsAdapterV2 extends ArrayAdapter <ListingAgg>{

    private LayoutInflater inflater;
    private static String TAG = "ListingsAdapter";

    private static class ViewHolder {
        TextView vehicleInfoView, priceInfoView,mileageInfoView;
        TextView dealer_name_view, safety_view;
        TextView lising_rating_reliability, listing_rating_build_quality,listing_rating_comfort;
        TextView listing_performance_info, listing_output_info,listing_engine_info;
        TextView listing_avg_fuel, listing_avg_insurance, listing_avg_depreciation;

        ImageView photo;
        FloatingActionButton info_btn, /* contact_btn, */share_btn;
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
            sampleLayout.addChildAppearAnimator(hover, R.id.text_overlay, Techniques.FadeIn, 200, 0);


            holder.vehicleInfoView = (TextView) view.findViewById(R.id.listing_yr_mk_md_view);
            holder.priceInfoView = (TextView) view.findViewById(R.id.listing_price_view);
            holder.mileageInfoView = (TextView) view.findViewById(R.id.listing_mileage_view);


            holder.info_btn = (FloatingActionButton) view.findViewById(R.id.info_btn);
            holder.photo = (ImageView) view
                    .findViewById(R.id.listing_photo_v2);
            holder.lising_rating_reliability = (TextView) hover.findViewById(R.id.listing_rating_reliability);
            holder.listing_rating_build_quality = (TextView) hover.findViewById(R.id.listing_rating_build_quality);
            holder.listing_rating_comfort = (TextView) hover.findViewById(R.id.listing_rating_comfort);
            holder.listing_performance_info = (TextView) hover.findViewById(R.id.listing_performance_info);
            holder.listing_output_info = (TextView) hover.findViewById(R.id.listing_output_info);
            holder.listing_engine_info = (TextView) hover.findViewById(R.id.listing_engine_info);

            holder.listing_avg_fuel = (TextView) hover.findViewById(R.id.listing_avg_fuel);
            holder.listing_avg_insurance = (TextView) hover.findViewById(R.id.listing_avg_insurance);
            holder.listing_avg_depreciation = (TextView) hover.findViewById(R.id.listing_avg_depreciation);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        /* vehicle: object for the entire getView function */
        final ListingAgg listing = getItem(position);

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
        holder.priceInfoView.setText("$\t" + listing.getDealerOfferPrice());
        holder.priceInfoView.setTypeface(ar);
        holder.mileageInfoView.setText(listing.getMileage() + "\t\tMILES");
        holder.mileageInfoView.setTypeface(ar);

        holder.listing_output_info.setText(listing.getHorsepower() + " HP / " + listing.getTorque() + " LB/FT");

        String performance_txt = "0-60 MPH TIME:\n" + listing.getZerosixty() + " s\n";
        if (listing.getQuartermile() != 0 ) {
                    performance_txt = performance_txt + "\nQUARTER MILE:\n" + listing.getQuartermile() +  " s";
        }
        holder.listing_performance_info.setText(performance_txt);

        String engine_txt;
        if (!listing.getCompressorType().toLowerCase().contains("na")) {
            engine_txt = listing.getCompressorType() + " ";
            holder.listing_engine_info.setText(engine_txt + listing.getCylinder() + " cylinders engine");
        } else {
            holder.listing_engine_info.setText(listing.getCylinder() + " cylinders engine");
        }

        holder.listing_rating_comfort.setText("COMFORT:\n" + String.format("%.2f / 5", listing.getRating_comfort()));
        holder.listing_rating_build_quality.setText("BUILD QUALITY:\n" + String.format("%.2f / 5", listing.getRating_build_quality()));
        holder.lising_rating_reliability.setText("RELIABILITY:\n" + String.format("%.2f / 5", listing.getRating_reliability()));

        String avg_fuel_str = "N/A";
        if (listing.getAvg_fuel_cost() > 0 ) {
            avg_fuel_str = String.format ("%.2f $", listing.getAvg_fuel_cost()/52);
        }
        holder.listing_avg_fuel.setText("Weekly fuel cost:\n" + avg_fuel_str);

        String avg_insurance_str = "N/A";
        if (listing.getAvg_insurance_cost() > 0 ) {
            avg_insurance_str = String.format ("%.2f $", listing.getAvg_insurance_cost()/12);
        }
        holder.listing_avg_insurance.setText("Monthly insurance cost:\n" + avg_insurance_str);

        String avg_dep_str = "N/A";
        if (listing.getAvg_depreciation() > 0 ) {
            avg_dep_str = String.format ("%.2f $", listing.getAvg_depreciation());
        }
        holder.listing_avg_depreciation.setText("Yearly Depreciation cost:\n" + avg_dep_str);
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
                vehicle_agg_info.putString("make", listing.getMake());
                vehicle_agg_info.putString("model", listing.getModel());
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
                vehicle_agg_info.putFloat("zerosixty" , listing.getZerosixty());
                vehicle_agg_info.putFloat("quartermile" , listing.getQuartermile());
                vehicle_agg_info.putFloat("avg_insurance_cost" , listing.getAvg_insurance_cost());
                vehicle_agg_info.putFloat("avg_repairs_cost" , listing.getAvg_repairs_cost());
                vehicle_agg_info.putFloat("avg_fuel_cost" , listing.getAvg_fuel_cost());
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
                vehicle_agg_info.putStringArrayList("features", (ArrayList)listing.getFeatures());
                vehicle_agg_info.putStringArrayList("smallPhotoUrls", (ArrayList)listing.getSmallPhotoUrls());
                vehicle_agg_info.putStringArrayList("largePhotoUrls", (ArrayList)listing.getLargePhotoUrls());
                vehicle_agg_info.putString ("dialog_type", "listingV2DetailOverlay");
                vehicle_agg_info.putInt ("year", listing.getYear());

                vehicle_agg_info.putFloat ("md_price_pct", listing.getModel_price_pct());
                vehicle_agg_info.putFloat ("overall_price_pct", listing.getOverall_price());
                vehicle_agg_info.putFloat ("md_mileage_pct", listing.getModel_mileage_pct());
                vehicle_agg_info.putFloat ("overall_mileage_pct", listing.getOverall_mileage());
                vehicle_agg_info.putFloat ("hp_pct", listing.getOverall_horsepower());
                vehicle_agg_info.putFloat ("torque_pct", listing.getOverall_torque());
                vehicle_agg_info.putFloat ("mpg_pct", listing.getOverall_combined_mpg());
                vehicle_agg_info.putFloat ("zero_sixty_pct", listing.getOverall_zerosixty());

                vehicle_agg_info.putFloat ("depr_pct", listing.getOverall_depr());
                vehicle_agg_info.putFloat ("repair_pct", listing.getOverall_repair());
                vehicle_agg_info.putFloat ("insurance_pct", listing.getOverall_insurance());

                ((BaseActivity) getContext()).OnListingV2SelectedCallback(vehicle_agg_info);
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
