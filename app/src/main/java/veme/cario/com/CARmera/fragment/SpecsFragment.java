package veme.cario.com.CARmera.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import veme.cario.com.CARmera.R;

/**
 * Created by bski on 11/10/14.
 */
public class SpecsFragment extends Fragment {
    private LinearLayout mpg_overlay;
    private LinearLayout price_overlay;
    private LinearLayout performance_overlay;

    /* "name" */
    private TextView vehicle_name;

    /* vehicle Tagged Image */
    private ImageView vehicle_img;

    /*
    private TextView vehicle_rating;

    /* "price" */
    private TextView base_msrp; //baseMSRP
    private TextView used_tmv_retail; // usedTmvRetail
    private TextView used_private_party; // usedPrivateParty
    private TextView used_trade_in; // usedTradeIn

    /* "MPG" */
    private TextView hw_mpg; // highway
    private TextView city_mpg; // city

    /* "engine" */
    private TextView vehicle_engine; // name
    private TextView engine_hp;    // horsepower
    private TextView engine_torque; // torque
    private TextView engine_cyl; // cylinder
    private TextView engine_config; // configuration
    private TextView engine_fuelType; // fuelType (gas, diesel, etc)
    private TextView engine_compressor; // compressorType
    private TextView engine_valves; // totalValves

    /* transmission */
    private TextView transmission_type; //transmissionType
    private TextView transmission_speed; // numberOfSpeeds

    /* drivenWheels */
    private TextView drive_train;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_specs, container, false);
    }
}
