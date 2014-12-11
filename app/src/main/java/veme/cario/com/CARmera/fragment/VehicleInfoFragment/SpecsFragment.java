package veme.cario.com.CARmera.fragment.VehicleInfoFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.APIModels.VehicleBaseInfo;
import veme.cario.com.CARmera.requests.VehicleBaseInfoRequest;

public class SpecsFragment extends Fragment {

    private static final String JSON_HASH_KEY = "base_info_json";

    /* "name" */
    private TextView vehicle_name;

    /* "price" */
    private TextView base_msrp; //baseMSRP
    private TextView used_tmv_retail; // usedTmvRetail
    private TextView used_private_party; // usedPrivateParty
    private TextView base_invoice; // invoice

    /* "MPG" */
    private TextView hw_mpg; // highway
    private TextView city_mpg; // city

    /* "engine" */
    private TextView engine_name; // name
    private TextView engine_hp;    // horsepower
    private TextView engine_torque; // torque
    private TextView engine_cyl; // cylinder
    private TextView engine_config; // configuration
    private TextView engine_fuelType; // fuelType (gas, diesel, etc)
    private TextView engine_compressor; // compressorType
    private TextView engine_valves; // totalValves
    private TextView engine_displacement;
    private TextView engine_compression;
    private TextView engine_code;

    /* transmission */
    private TextView transmission_type; //transmissionType
    private TextView transmission_speed; // numberOfSpeeds

    /* drivenWheels */
    private TextView drive_train;

    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);

    private final class VehicleBaseInfoRequestListener implements RequestListener<VehicleBaseInfo> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
            SpecsFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
        }

        @Override
        public void onRequestSuccess (VehicleBaseInfo vehicleBaseInfo) {
            getArguments().putString("vehicle_id", vehicleBaseInfo.getId());

            if (SpecsFragment.this.isAdded()) {
                vehicle_name.setText(vehicleBaseInfo.getName());
                drive_train.setText(vehicleBaseInfo.getDrivenWheels());
                transmission_type.setText(vehicleBaseInfo.getTransmission().getTransmissionType());
                transmission_speed.setText(vehicleBaseInfo.getTransmission().getNumberofSpeeds());

                base_msrp.setText(vehicleBaseInfo.getPrice().getBaseMSRP());
                used_tmv_retail.setText(vehicleBaseInfo.getPrice().getUsedTmvRetail());
                used_private_party.setText(vehicleBaseInfo.getPrice().getUsedPrivateParty());
                base_invoice.setText(vehicleBaseInfo.getPrice().getBaseInvoice());

                engine_hp.setText(vehicleBaseInfo.getEngine().getHorsepower());
                engine_torque.setText(vehicleBaseInfo.getEngine().getTorque());
                engine_fuelType.setText(vehicleBaseInfo.getEngine().getFuelType());
                engine_displacement.setText(vehicleBaseInfo.getEngine().getDisplacement());
                engine_cyl.setText(vehicleBaseInfo.getEngine().getCylinder());
                engine_config.setText(vehicleBaseInfo.getEngine().getConfiguration());
                engine_name.setText(vehicleBaseInfo.getEngine().getName());
                engine_compressor.setText(vehicleBaseInfo.getEngine().getCompressorType());
                engine_compression.setText(vehicleBaseInfo.getEngine().getCompressionRatio());
                engine_valves.setText(vehicleBaseInfo.getEngine().getTotalValves());
                engine_code.setText(vehicleBaseInfo.getEngine().getManufacturerEngineCode());

                city_mpg.setText(vehicleBaseInfo.getMpg().getCity());
                hw_mpg.setText(vehicleBaseInfo.getMpg().getHighway());

                SpecsFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_specs, container, false);
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUIComponents();
    }


    @Override
    public void onStart() {
        super.onStart();
        spiceManager.start(getActivity());
        spiceManager.addListenerIfPending(VehicleBaseInfo.class, JSON_HASH_KEY,
                new VehicleBaseInfoRequestListener());
    }

    @Override
    public void onStop() {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }

    private void initUIComponents () {
        /* Get year, make, model information from bundle */
        vehicle_name = (TextView) getView().findViewById(R.id.name_view);

        drive_train = (TextView) getView().findViewById(R.id.drivetrain);
        transmission_type = (TextView) getView().findViewById(R.id.transmission_type);
        transmission_speed = (TextView) getView().findViewById(R.id.transmission_speed);

        base_invoice = (TextView) getView().findViewById(R.id.invoice);
        used_private_party = (TextView) getView().findViewById(R.id.used_private);
        used_tmv_retail = (TextView) getView().findViewById(R.id.used_tmv);
        base_msrp = (TextView) getView().findViewById(R.id.msrp);

        engine_hp = (TextView) getView().findViewById(R.id.hp);
        engine_torque = (TextView) getView().findViewById(R.id.torque);
        engine_fuelType = (TextView) getView().findViewById(R.id.fueltype);
        engine_displacement = (TextView) getView().findViewById(R.id.displacement);
        engine_cyl = (TextView) getView().findViewById(R.id.cylinder_cnt);
        engine_config = (TextView) getView().findViewById(R.id.configuration);
        engine_name = (TextView) getView().findViewById(R.id.engine_name);
        engine_code = (TextView) getView().findViewById(R.id.engine_code);
        engine_compressor = (TextView) getView().findViewById(R.id.compressor);
        engine_compression = (TextView) getView().findViewById(R.id.compression_ratio);
        engine_valves = (TextView) getView().findViewById(R.id.valve);


        city_mpg = (TextView) getView().findViewById(R.id.city_mpg);
        hw_mpg = (TextView) getView().findViewById(R.id.highway_mpg);


//        performRequest(getArguments().getString("vehicle_yr"),
//                        getArguments().getString("vehicle_mk"),
//                        getArguments().getString("vehicle_model"));
    }

    private void performRequest(String year, String make, String model) {
        SpecsFragment.this.getActivity().setProgressBarIndeterminate(true);
        VehicleBaseInfoRequest vehicleBaseInfoRequest = new VehicleBaseInfoRequest(year, make, model);
        spiceManager.execute(vehicleBaseInfoRequest, JSON_HASH_KEY, DurationInMillis.ALWAYS_RETURNED,
                new VehicleBaseInfoRequestListener());
    }

}
