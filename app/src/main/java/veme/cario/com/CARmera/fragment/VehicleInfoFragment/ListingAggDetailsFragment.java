package veme.cario.com.CARmera.fragment.VehicleInfoFragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gc.materialdesign.views.ProgressBarDetermininate;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.List;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.util.AnimatedExpandableListView;
import veme.cario.com.CARmera.util.EdmundsRatingAdapter;

/**
 * Created by bski on 3/21/15.
 */
public class ListingAggDetailsFragment extends Fragment {

    private TextView dealerName;
    private TextView dealerAddress;
    private TextView dealerPhone;

    private ProgressBar price_pct_overall, mileage_pct_overall,
            hp_overall, torque_overall, zero_sixty_overall, combined_mpg_overall,
            repair_overall, depr_overall, insurance_overall, model_price_pct, model_mileage_pct;


    private TextView comfort_rating_agg;
    private TextView fun_to_drive_agg;
    private TextView build_quality_agg;
    private TextView reliability_agg;

    private TextView combined_mpg_agg;
    private TextView city_mpg_agg;
    private TextView hwy_mpg_agg;
    private TextView fuel_cost_agg;
    private TextView depr_cost_agg;
    private TextView insurance_cost_agg;
    private TextView repair_cost_agg;
    private TextView maintenance_cost_agg;

    private TextView hp_agg;
    private TextView torque_agg;
    private TextView zero_sixty_agg;
    private TextView quartermile_agg;

    private TextView price_md_pct, mileage_md_pct;

    private ProgressBar price_md, mileage_md;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* set fragment to be retained across Activity recreation */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.listing_agg_details_fragment, container, false);
        price_md_pct = (TextView) v.findViewById(R.id.style_price);
        mileage_md_pct = (TextView) v.findViewById(R.id.style_mileage);

        price_md = (ProgressBar) v.findViewById(R.id.price_pct_md);
        mileage_md = (ProgressBar) v.findViewById(R.id.mileage_pct_md);

        hp_agg = (TextView) v.findViewById(R.id.engine_horsepower);
        torque_agg = (TextView) v.findViewById(R.id.engine_torque);
        zero_sixty_agg = (TextView) v.findViewById(R.id.zero_sixty);
        quartermile_agg = (TextView) v.findViewById(R.id.quarter_mile);


        dealerName = (TextView) v.findViewById(R.id.dealer_name);
        dealerAddress = (TextView) v.findViewById(R.id.dealer_addr);
        dealerPhone = (TextView) v.findViewById(R.id.dealer_phone);

        price_pct_overall = (ProgressBar) v.findViewById(R.id.price_pct_overall);
        torque_overall = (ProgressBar) v.findViewById(R.id.torque_overall);
        hp_overall = (ProgressBar) v.findViewById(R.id.hp_overall);
        zero_sixty_overall = (ProgressBar) v.findViewById(R.id.zero_sixty_overall);
        combined_mpg_overall = (ProgressBar) v.findViewById(R.id.combined_mpg_overall);
        mileage_pct_overall = (ProgressBar) v.findViewById(R.id.mileage_pct_overall);
        depr_overall = (ProgressBar) v.findViewById(R.id.depr_overall);
        insurance_overall = (ProgressBar) v.findViewById(R.id.insurance_overall);
        repair_overall = (ProgressBar) v.findViewById(R.id.repair_overall);



        combined_mpg_agg = (TextView) v.findViewById(R.id.combined_mpg_agg);
        city_mpg_agg = (TextView) v.findViewById(R.id.city_mpg_agg);
        hwy_mpg_agg = (TextView) v.findViewById(R.id.hwy_mpg_agg);
        fuel_cost_agg = (TextView) v.findViewById(R.id.fuel_cost_agg);
        depr_cost_agg = (TextView) v.findViewById(R.id.depr_cost_agg);
        insurance_cost_agg = (TextView) v.findViewById(R.id.insurance_cost_agg);
        repair_cost_agg = (TextView) v.findViewById(R.id.repair_cost_agg);
        maintenance_cost_agg = (TextView) v.findViewById(R.id.maintenance_cost_agg);

        comfort_rating_agg = (TextView) v.findViewById(R.id.comfort_rating_agg);
        fun_to_drive_agg = (TextView) v.findViewById(R.id.fun_to_drive_agg);
        build_quality_agg = (TextView) v.findViewById(R.id.build_quality_agg);
        reliability_agg = (TextView) v.findViewById(R.id.reliability_agg);

        return v;
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUIComponents();
    }

    private void initUIComponents () {
        Typeface ar = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");
        /* pct */
        Bundle vehicle_agg_info = getArguments();
        price_pct_overall.setIndeterminate(false);
        price_pct_overall.setProgress((int)vehicle_agg_info.getFloat("overall_price_pct"));

        mileage_pct_overall.setIndeterminate(false);
        mileage_pct_overall.setProgress((int) vehicle_agg_info.getFloat("overall_mileage_pct"));

        hp_overall.setIndeterminate(false);
        hp_overall.setProgress((int)vehicle_agg_info.getFloat("hp_pct"));

        torque_overall.setIndeterminate(false);
        torque_overall.setProgress((int) vehicle_agg_info.getFloat("torque_pct"));

        combined_mpg_overall.setIndeterminate(false);
        combined_mpg_overall.setProgress((int)vehicle_agg_info.getFloat("mpg_pct"));

        zero_sixty_overall.setIndeterminate(false);
        zero_sixty_overall.setProgress((int) vehicle_agg_info.getFloat("zero_sixty_pct"));

        depr_overall.setIndeterminate(false);
        depr_overall.setProgress((int)vehicle_agg_info.getFloat("depr_pct"));

        repair_overall.setIndeterminate(false);
        repair_overall.setProgress((int)vehicle_agg_info.getFloat("repair_pct"));

        insurance_overall.setIndeterminate(false);
        insurance_overall.setProgress((int) vehicle_agg_info.getFloat("insurance_pct"));


        price_md.setIndeterminate(false);
        price_md.setProgress((int)vehicle_agg_info.getFloat("md_price_pct"));

        mileage_md.setIndeterminate(false);
        mileage_md.setProgress((int) vehicle_agg_info.getFloat("md_mileage_pct"));

        price_md_pct.setText(vehicle_agg_info.getString ("styleName") );
        mileage_md_pct.setText(vehicle_agg_info.getString ("styleName") );

        /* dealer */
        dealerAddress.setText(vehicle_agg_info.getString ("dealerAddress"));
        dealerPhone.setText(vehicle_agg_info.getString ("dealerPhone"));
        dealerName.setText(vehicle_agg_info.getString ("dealerName"));

        dealerAddress.setTypeface(ar);
        dealerPhone.setTypeface(ar);
        dealerName.setTypeface(ar);

        /* rating */
        comfort_rating_agg.setText(String.format("%.2f / 5", vehicle_agg_info.getFloat("rating_comfort")));
        build_quality_agg.setText(String.format("%.2f / 5", vehicle_agg_info.getFloat("rating_build_quality")));
        fun_to_drive_agg.setText(String.format("%.2f / 5", vehicle_agg_info.getFloat("rating_fun_to_drive")));
        reliability_agg.setText(String.format("%.2f / 5", vehicle_agg_info.getFloat("rating_reliability")));

        comfort_rating_agg.setTypeface(ar);
        build_quality_agg.setTypeface(ar);
        fun_to_drive_agg.setTypeface(ar);
        reliability_agg.setTypeface(ar);

        String avg_fuel_str = "N/A";
        if (vehicle_agg_info.getFloat("avg_fuel_cost") > 0 ) {
            avg_fuel_str = String.format ("%.2f $", vehicle_agg_info.getFloat("avg_fuel_cost")/52);
        }
        fuel_cost_agg.setText(avg_fuel_str);
        fuel_cost_agg.setTypeface(ar);

        String avg_insurance_str = "N/A";
        if (vehicle_agg_info.getFloat("avg_insurance_cost") > 0 ) {
            avg_insurance_str = String.format ("%.2f $", vehicle_agg_info.getFloat("avg_insurance_cost")/12);
        }
        insurance_cost_agg.setText(avg_insurance_str);
        insurance_cost_agg.setTypeface(ar);

        String avg_dep_str = "N/A";
        if (vehicle_agg_info.getFloat("avg_depreciation") > 0 ) {
            avg_dep_str = String.format ("%.2f $", vehicle_agg_info.getFloat("avg_depreciation"));
        }
        depr_cost_agg.setText(avg_dep_str);
        depr_cost_agg.setTypeface(ar);

        String avg_repair_str = "N/A";
        if (vehicle_agg_info.getFloat("avg_repairs_cost") > 0 ) {
            avg_repair_str = String.format ("%.2f $", vehicle_agg_info.getFloat("avg_repairs_cost")/12);
        }
        repair_cost_agg.setText(avg_repair_str);
        repair_cost_agg.setTypeface(ar);

        String avg_maintenance_str = "N/A";
        if (vehicle_agg_info.getFloat("avg_maintenance_cost") > 0 ) {
            avg_maintenance_str = String.format ("%.2f $", vehicle_agg_info.getFloat("avg_maintenance_cost"));
        }
        maintenance_cost_agg.setText(avg_maintenance_str);
        maintenance_cost_agg.setTypeface(ar);

        combined_mpg_agg.setText(vehicle_agg_info.getInt("combinedMpg") + " MPG");
        city_mpg_agg.setText(vehicle_agg_info.getInt("cityMpg") + " MPG");
        hwy_mpg_agg.setText(vehicle_agg_info.getInt("hwyMpg") + " MPG");

        combined_mpg_agg.setTypeface(ar);
        city_mpg_agg.setTypeface(ar);
        hwy_mpg_agg.setTypeface(ar);
        depr_cost_agg.setTypeface(ar);
        combined_mpg_agg.setTypeface(ar);
        insurance_cost_agg.setTypeface(ar);
        repair_cost_agg.setTypeface(ar);
        maintenance_cost_agg.setTypeface(ar);

        hp_agg.setText(vehicle_agg_info.getInt("horsepower") + " hp");
        hp_agg.setTypeface(ar);

        torque_agg.setText(vehicle_agg_info.getInt("torque") + " lb/ft");
        torque_agg.setTypeface(ar);


        zero_sixty_agg.setText(vehicle_agg_info.getFloat("zerosixty") + " s");
        zero_sixty_agg.setTypeface(ar);
        quartermile_agg.setText(vehicle_agg_info.getFloat("quartermile") + " s");
        quartermile_agg.setTypeface(ar);
    }

}
