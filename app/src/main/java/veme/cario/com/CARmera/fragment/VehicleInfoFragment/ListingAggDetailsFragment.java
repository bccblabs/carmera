package veme.cario.com.CARmera.fragment.VehicleInfoFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.util.AnimatedExpandableListView;
import veme.cario.com.CARmera.util.EdmundsRatingAdapter;

/**
 * Created by bski on 3/21/15.
 */
public class ListingAggDetailsFragment extends Fragment {
//    private

    private TextView styleName;
    private TextView mileage;
    private TextView bodyType;

    private TextView automaticType;
    private TextView driveTrain;



    private TextView combinedMpg;
    private TextView cityMpg;
    private TextView hwyMpg;
    private TextView horsepower;
    private TextView torque;
    private TextView cylinder;

    private TextView engineType;
    private TextView compressorType;
    private TextView exteriorGenericColor;
    private TextView TextVieweriorGenericColor;


    private TextView zerosixty;
    private TextView quartermile;
    private TextView avg_fuel_cost;
    private TextView avg_insurance_cost;
    private TextView avg_maTextViewenance_cost;
    private TextView avg_repairs_cost;
    private TextView avg_depreciation;
    private TextView rating_performance;
    private TextView rating_comfort;
    private TextView rating_fun_to_drive;
    private TextView rating_build_quality;
    private TextView rating_reliability;
    private TextView f34PhotoUrlT;
    private TextView f34PhotoUrlST;
    private TextView f34PhotoUrlE;
    private TextView dealerOfferPrice;
    private TextView dealerName;
    private TextView dealerAddress;
    private TextView dealerPhone;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* set fragment to be retained across Activity recreation */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.listing_agg_details_fragment, container, false);
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUIComponents();
    }

    private void initUIComponents () {
    }

}
